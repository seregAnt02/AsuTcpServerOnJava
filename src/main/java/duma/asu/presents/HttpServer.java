package duma.asu.presents;

import duma.asu.models.interfaces.SendDataParameter;
import duma.asu.models.serializableModels.DataFile;
import duma.asu.models.serializableModels.Parameter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static java.lang.System.out;


public class HttpServer {

    private ClientManager clientManager;


    protected HttpServer(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    private void commandSwitch() throws IOException, Exception {

        DataFile dataFile = new DataFile("server_name");

        this.clientManager.sendModelToClient(dataFile);

        this.clientManager.viewReadStreamReturnGenericObject.viewSendModelToClient(dataFile.getName());

        out.print("serialize object... " + dataFile);
    }


    protected void httpListener(){

        try (ServerSocket serverSocket = new ServerSocket(6088)) {
            out.println("Http server on port " +  serverSocket.getLocalPort() + ", started!");

            while (true) {
                // ожидаем подключения
                Socket socket = serverSocket.accept();
                out.println("Client connected!");

                commandSwitch();

                // для подключившегося клиента открываем потоки
                // чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

                    // ждем первой строки запроса
                    while (!input.ready()) ;

                    // считываем и печатаем все что было отправлено клиентом
                    out.println();
                    while (input.ready()) {
                        out.println(input.readLine());
                    }

                    // отправляем ответ
                    try(PrintWriter printWriter = new PrintWriter(socket.getOutputStream())){
                        printWriter.println("HTTP/1.1 200 OK");
                        printWriter.println("Content-Type: text/html; charset=utf-8");
                        printWriter.println();
                        printWriter.println("<p>Привет всем!</p>");
                        printWriter.flush();
                    }catch (IOException ex){
                        out.print(ex.getMessage());
                    }

                    // по окончанию выполнения блока try-with-resources потоки,
                    // а вместе с ними и соединение будут закрыты
                    out.println("Client disconnected!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
