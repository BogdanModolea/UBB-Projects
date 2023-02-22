package model.value;

import model.type.StringType;
import model.type.Type;
import java.util.Objects;

public class StringValue implements Value {
    private String string;

    public StringValue(String string) {
        this.string = string;
    }

    public StringValue() {
        this.string = "";
    }

    public String getValue() {
        return this.string;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(string);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        StringValue that = (StringValue) obj;
        return Objects.equals(this.string, that.string);
    }

    @Override
    public String toString() {
        return this.string;
    }
}
