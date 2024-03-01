import java.io.Serializable;

public class SubscribeMessage extends BaseMessage implements Serializable {
    public final String variableName;
    public final int rank;

    public SubscribeMessage(String variableName, int rank) {
        this.variableName = variableName;
        this.rank = rank;
    }
}