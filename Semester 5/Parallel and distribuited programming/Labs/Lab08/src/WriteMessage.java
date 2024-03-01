import java.io.Serializable;

public class WriteMessage extends BaseMessage implements Serializable {
    public final String variableName;
    public final int newValue;

    public WriteMessage(String variableName, int newValue) {
        this.variableName = variableName;
        this.newValue = newValue;
    }
}