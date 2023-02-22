import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        P10();
    }

    public static void P1(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz");
        words.stream()
                .forEach(s -> System.out.println("  " + s));
    }

    public static void P2(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz");
        words.stream()
                .forEach(System.out::println);
    }

    public static void P3(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz");
        words.stream()
                .map(s -> s + "!")
                .forEach(System.out::println);
        words.stream()
                .map(s -> s.replace("i", "eye"))
                .forEach(System.out::println);
        words.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    public static void P4(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata");
        words.stream()
                .filter(s -> s.length() < 4)
                .forEach(System.out::println);
        words.stream()
                .filter(s -> s.contains("b"))
                .forEach(System.out::println);
        words.stream()
                .filter(s -> s.length() % 2 == 0)
                .forEach(System.out::println);
    }

    public static void P5() {
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata", "aes", "qwe");
        List<String> words2 = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata", "aes");
//        String result = words.stream()
//                .map(String::toUpperCase)
//                .filter(s -> s.length() < 4)
//                .filter(s -> s.contains("E"))
//                .findFirst()
//                .get();
//
//        System.out.println(result);


        List<String> ans = words2.stream()
                .map(String::toUpperCase)
                .filter(s -> s.length() < 4)
                .collect(Collectors.toList());

        //String resultE =
        ans.stream()
                .filter(s -> s.contains("E"))
                .findFirst()
                //.get();
                .ifPresent(System.out::println);
        //System.out.println(resultE);

        //String resultQ =
        ans.stream()
                .filter(s -> s.contains("Q"))
                .findFirst()
                .orElse("No match :(");
        //System.out.println(resultQ);
    }

    public static void P6(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata", "aes", "qwe");

        //System.out.println(words.stream()
        //        .reduce("", (partial, s) -> partial.toUpperCase() + s.toUpperCase()));

        System.out.println(
                words.stream()
                        .reduce((partial, s) -> partial.toUpperCase() + s.toUpperCase()).get()
        );
    }

    public static void P7(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata", "aes", "qwe");

        System.out.println(words.stream()
                .map(s -> s.toUpperCase())
                .reduce("", (partial, s) -> partial.toUpperCase() + s.toUpperCase())
        );
    }

    public static void P8(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata", "aes", "qwe");

        System.out.println(words.stream()
                .reduce((partial, s) -> partial.toUpperCase() + "," + s.toUpperCase()).get()
        );
    }

    public static void P9(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata", "aes", "qwe");

        System.out.println(words.stream()
                .reduce(0, (sum, s) -> sum + s.length(), Integer::sum)
        );
    }

    public static void P10(){
        List<String> words = Arrays.asList("hi", "hello", "neata", "hatz", "buna dimineata", "aes", "qwe");

        System.out.println(words.stream()
                .reduce(0, (sum, s) -> sum + (s.contains("h") ? 1 : 0), Integer::sum)
        );
    }
}