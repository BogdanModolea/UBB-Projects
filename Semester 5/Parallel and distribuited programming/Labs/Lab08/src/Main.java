import mpi.MPI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    public static void process0Behaviour(DSMLibrary dsmLibrary) {
        dsmLibrary.subscribeCurrentProcessToVariable("a");
        dsmLibrary.subscribeCurrentProcessToVariable("b");
        dsmLibrary.subscribeCurrentProcessToVariable("c");
        dsmLibrary.compareAndExchange("a", 1, 11);
        dsmLibrary.compareAndExchange("d", 4, 437);
        dsmLibrary.compareAndExchange("b", 901, 2307);
        dsmLibrary.close();
    }

    public static void process1Behaviour(DSMLibrary dsmLibrary) {
        dsmLibrary.subscribeCurrentProcessToVariable("a");
        dsmLibrary.subscribeCurrentProcessToVariable("d");
    }

    public static void process2Behaviour(DSMLibrary dsmLibrary) {
        dsmLibrary.subscribeCurrentProcessToVariable("b");
        dsmLibrary.compareAndExchange("b", 2, 901);
    }

    public static void main(String[] args) throws InterruptedException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        System.out.println("Current rank = " + rank);
        DSMLibrary dsmLibrary = new DSMLibrary();
        Map<Integer, Consumer<DSMLibrary>> behaviours = new HashMap<>();
        behaviours.put(0, Main::process0Behaviour);
        behaviours.put(1, Main::process1Behaviour);
        behaviours.put(2, Main::process2Behaviour);

        Thread thread = new Thread(new Subscriber(dsmLibrary));
        thread.start();
        behaviours.get(rank).accept(dsmLibrary);
        thread.join();

        MPI.Finalize();
    }
}