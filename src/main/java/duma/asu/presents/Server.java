package duma.asu.presents;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer() throws ClassNotFoundException{
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Подключен новый клиент!" + socket);
                ClientManager client = new ClientManager(socket);
                Thread thread = new Thread(client);
                thread.start();
            }
        }catch (IOException e){
            closeSocket();
        }
    }

    void closeSocket(){
        try {
            if(serverSocket != null) serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
