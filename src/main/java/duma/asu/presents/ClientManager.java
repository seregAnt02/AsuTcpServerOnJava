package duma.asu.presents;

import duma.asu.models.serializableModels.Parameter;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientManager implements Runnable{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String name;
    public static Map<String, ClientManager> clients = new HashMap<>();

    public ClientManager(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());

        Parameter parameter = modelDeserialization();
        clients.put(parameter.getName(), this);

    }

    @Override
    public void run() {
        while (socket.isConnected()){
            try {
                Parameter parameter  = modelDeserialization();
                sendModelToClient(parameter);
            } catch (IOException e) {
                closeEverything();
            } catch (ClassNotFoundException e) {
                closeEverything();
            }
        }
    }
    private Parameter modelDeserialization() throws IOException, ClassNotFoundException {
        Parameter parameter = (Parameter) input.readObject();
        System.out.println("десерилизация объекта " + parameter.getClass() + ": " + parameter.getName());
        return parameter;
    }

    private void sendModelToClient(Parameter parameter){
        for (var client: clients.entrySet()) {
            try {
                //if (client.getKey().equals(parameter.getToName()) && !parameter.getName().equals(name)) {
                    client.getValue().output.writeObject(parameter);
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
