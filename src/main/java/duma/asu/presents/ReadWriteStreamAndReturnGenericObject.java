package duma.asu.presents;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadWriteStreamAndReturnGenericObject<T> {

    private final ObjectInputStream input;


    public ReadWriteStreamAndReturnGenericObject(ObjectInputStream input) {
        this.input = input;
    }


    public T modelDeserialization() throws IOException, ClassNotFoundException {
        T parameter = (T) input.readObject();
        return parameter;
    }
}
