import java.io.Serializable;

public class PairMPI<T1, T2> implements Serializable {

    private T1 first;
    private T2 second;

    public PairMPI(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" + "first=" + first + ", second=" + second + '}';
    }
}