import duma.asu.presents.Server;

import java.io.IOException;
import java.net.ServerSocket;

import static java.lang.System.out;

public class AsuTcpServerApplication {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ServerSocket serverSocket = new ServerSocket(1300);
        Server server = new Server(serverSocket);
        server.runServer();
        out.print(System.getProperty("java.class.path"));
    }
}