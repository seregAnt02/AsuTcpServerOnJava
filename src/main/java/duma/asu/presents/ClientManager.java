package duma.asu.presents;

import duma.asu.models.interfaces.SendDataParameter;
import duma.asu.models.serializableModels.DataFile;
import duma.asu.models.serializableModels.Parameter;
import duma.asu.views.ViewReadStreamReturnGenericObject;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

//import javax.net.ssl.SSLContext;

public class ClientManager implements Runnable{
    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private String name;
    public static Map<String, ClientManager> clients = new HashMap<>();

    protected ViewReadStreamReturnGenericObject viewReadStreamReturnGenericObject;
    private ReadWriteStreamAndReturnGenericObject<SendDataParameter> readStreamAndReturnGenericObject;

    protected ClientManager(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());

        readStreamAndReturnGenericObject = new ReadWriteStreamAndReturnGenericObject(this.input);


        Runnable task = () -> {

            new HttpListener(this).createListener();
        };
        Thread thread = new Thread(task);
        thread.start();

        viewReadStreamReturnGenericObject = new ViewReadStreamReturnGenericObject();

        SendDataParameter sendDataParameter = (SendDataParameter) readStreamAndReturnGenericObject.InputDeserialization();
        viewReadStreamReturnGenericObject.viewsNameAndClass(sendDataParameter.getClass().toString(),
                sendDataParameter.getName());

        clients.put(sendDataParameter.getName(), this);

    }


    @Override
    public void run() {
        while (socket.isConnected()){
            try {
                SendDataParameter sendDataParameter  = (SendDataParameter) readStreamAndReturnGenericObject.InputDeserialization();
                viewReadStreamReturnGenericObject.viewsNameAndClass(sendDataParameter.getClass().toString(),
                        sendDataParameter.getName());
                commandSwitch(sendDataParameter);
                //sendDataToClient(sendDataParameter);

            } catch (IOException | InterruptedException e) {
                closeEverything();
            } catch (ClassNotFoundException e) {
                closeEverything();
            }
        }
    }


    private void commandSwitch(SendDataParameter sendDataParameter) throws SocketException, InterruptedException {
        if (sendDataParameter instanceof Parameter) {
            System.out.println(Parameter.class.getName());
        }
        if (sendDataParameter instanceof DataFile) {
            DataFile dataFile = (DataFile) sendDataParameter;
            CreateFiles createFiles = new CreateFiles(dataFile);
            createFiles.start_and_finish_thread();
        }
    }

    protected void sendDataToClient(SendDataParameter sendDataParameter){
        for (var client: clients.entrySet()) {
            try {
                //if (client.getKey().equals(parameter.getToName()) && !parameter.getName().equals(name)) {
                    client.getValue().output.writeObject(sendDataParameter);
                    client.getValue().output.flush();
                //}
            } catch (IOException e){
                closeEverything();
            }
        }
    }

    private void closeEverything() {
        removeClient();
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void removeClient(){
        clients.remove(this);
        //sendMessageToAll(new Message(name, "all", "SERVER: " + name +" покинул чат."));
    }

}
