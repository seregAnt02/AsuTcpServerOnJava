package duma.asu.presents;



import duma.asu.models.Message;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

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

        Message message = modelDeserialization();
        clients.put(message.getName(), this);

    }

    @Override
    public void run() {
        while (socket.isConnected()){
            try {
                Message message  = modelDeserialization();
                if(message.getToName().equals("all")) sendMessageToAll(message);
                else sendMessageToClient(message);
            } catch (IOException e) {
                closeEverything();
                out.print("event close! -> " + e.getMessage() + " "  + e.getClass() +  "\r\n");
            } catch (ClassNotFoundException e) {
                closeEverything();
                out.print("event close! -> " + e.getMessage() + " "  + e.getClass() +  "\r\n");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private Message modelDeserialization() throws IOException, ClassNotFoundException {
        Message message = (Message) input.readObject();
        System.out.println("десерилизация объекта " + message.getClass() + ": " + message.getName());
        return message;
    }
    private void modelSerializable() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        Message message = new Message("user", "user1", "test");
        oos.writeObject(message);
        oos.flush();
    }

    private String[] parsMessage(String massageToSend){
        String[] mas = massageToSend.split(":");
        return mas;
    }

    private void sendMessageToClient(Message message){
        for (var client: clients.entrySet()) {
            try {
                if (client.getKey().equals(message.getToName()) && !message.getName().equals(name)) {
                    client.getValue().output.writeObject(message);
                    client.getValue().output.flush();
                }
            } catch (IOException e){
                closeEverything();
            }
        }
    }
    private void sendMessageToAll(Message message) {
        for (var client: clients.entrySet()) {
            try {
                if (!client.getKey().equals(message.getName())) {
                    client.getValue().output.writeObject(message);
                    client.getValue().output.flush();
                }
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
        sendMessageToAll(new Message(name, "all", "SERVER: " + name +" покинул чат."));
    }

}
