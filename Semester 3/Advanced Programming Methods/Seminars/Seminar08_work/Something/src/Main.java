import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        P1();
    }

    public static void P1(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);

        System.out.println(numbers.stream()
                .filter(x -> x % 2 == 0 || x % 3 == 0)
                .map(x -> "A" + (x + 1) + "B")
                .reduce((partial, x) -> partial + x).get()
        );
    }

}