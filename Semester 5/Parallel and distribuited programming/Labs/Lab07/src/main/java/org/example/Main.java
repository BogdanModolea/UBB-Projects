package org.example;

import java.util.Arrays;


public class Main {
    private static final int MASTER_PROCESS_RANK = 0;

    private static Polynomial buildResult(Object[] results) {
        int degree = ((Polynomial) results[0]).getDegree();
        Polynomial result = new Polynomial(degree + 1);

        for (Object polynomialParts: results) {
            result.addWithoutRemovingLeadingZeroes((Polynomial) polynomialParts);
        }
        result.removeLeadingZeroes();
        return result;
    }

    private static void multiplicationMaster(Polynomial p, Polynomial q, int processCount) {
        long startTime = System.currentTimeMillis();
        int start;
        int finish = 0;
        int sectionLength = (p.getDegree() + 1) / (processCount - 1);

        for (int i = 1; i < processCount; i++) {
            start = finish;
            finish += sectionLength;
            if (i == processCount - 1) {
                finish = p.getDegree() + 1;
            }

            DTO currentSectionDTO = new DTO(p, q, start, finish);
            MPI.COMM_WORLD.Send(new Object[]{currentSectionDTO}, 0, 1, MPI.OBJECT, i, 0);
        }

        Object[] results = new Object[processCount - 1];
        for (int i = 1; i < processCount; i++) {
            COMM_WORLD.Recv(results, i - 1, 1, MPI.OBJECT, i, 0);
        }

        Polynomial result = buildResult(results);
        System.out.println("Result:\n" + result);
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static DTO receiveDTO() {
        Object[] currentSection = new Object[2];
        MPI.COMM_WORLD.Recv(currentSection, 0, 1, MPI.OBJECT, 0, 0);
        return (DTO) currentSection[0];
    }

    private static void multiplyRegularWrapper(int rank) {
        System.out.printf("Worker %d started\n", rank);

        DTO currentSectionDTO = receiveDTO();
        Polynomial result = Multiplication.sectionMultiplication(currentSectionDTO.a, currentSectionDTO.b, currentSectionDTO.begin, currentSectionDTO.end);

        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }

    private static void multiplyKaratsubaWrapper(int rank) {
        System.out.printf("Worker %d started\n", rank);
        DTO currentSectionDTO = receiveDTO();

        for (int i = 0; i < currentSectionDTO.begin; i++) {
            currentSectionDTO.a.getCoefficients().set(i, 0);
        }
        for (int j = currentSectionDTO.end; j < currentSectionDTO.a.getCoefficients().size(); j++) {
            currentSectionDTO.a.getCoefficients().set(j, 0);
        }

        Polynomial result = Multiplication.karatsubaSequentialMultiplication(currentSectionDTO.a, currentSectionDTO.b);

        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }

    public static void main(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (rank == MASTER_PROCESS_RANK) {
            Polynomial x = new Polynomial(Arrays.asList(1, 2, -2, 6, 3)); // 3x^4 + 6x^3 -2x^2 + 2x + 1
            Polynomial y = new Polynomial(Arrays.asList(4, -2, 3, 0, 7, 2, 1)); // x^6 + 2x^5 + 7x^4 + 3x^2 -2x + 4
            // 4X ^ 0 + 6X ^ 1 + -9X ^ 2 + 34X ^ 3 + 1X ^ 4 + 28X ^ 5 + 40X ^ 7 + 31X ^ 8 + 12X ^ 9 + 3X ^ 10
            multiplicationMaster(x, y, size);
        }
        else {
            //multiplyRegularWrapper(rank);
            multiplyKaratsubaWrapper(rank);
        }

        MPI.Finalize();
    }
}