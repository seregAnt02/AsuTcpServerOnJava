package duma.asu.presents;

import duma.asu.views.ViewReadStreamReturnGenericObject;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadStreamReturnGenericObject<T> {

    private final ObjectInputStream input;


    public ReadStreamReturnGenericObject(ObjectInputStream input) {
        this.input = input;
    }


    public T modelDeserialization() throws IOException, ClassNotFoundException {
        T parameter = (T) input.readObject();
        return parameter;
    }
}
