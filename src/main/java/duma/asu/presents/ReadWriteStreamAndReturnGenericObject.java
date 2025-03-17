package duma.asu.presents;

import duma.asu.models.interfaces.SendDataParameter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ReadWriteStreamAndReturnGenericObject<T> {

    private  ObjectInputStream input;

    private  ObjectOutputStream output;


    protected ReadWriteStreamAndReturnGenericObject(ObjectInputStream input) {
        this.input = input;
    }


    protected ReadWriteStreamAndReturnGenericObject(ObjectOutputStream output) {
        this.output = output;
    }

    protected T InputDeserialization() throws IOException, ClassNotFoundException {
        T parameter = (T) input.readObject();
        return parameter;
    }


    protected void outSerialization(T sendDataParameter) throws IOException, ClassNotFoundException {
        output.writeObject(sendDataParameter);
        output.flush();
    }

}
