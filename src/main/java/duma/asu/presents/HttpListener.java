package duma.asu.presents;

import duma.asu.models.interfaces.SendDataParameter;
import duma.asu.models.serializableModels.DataFile;
import duma.asu.models.serializableModels.Parameter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static java.lang.System.out;


public class HttpListener {

    private static final long serialVersionUID = 3L;

    private ClientManager clientManager;

    private Logger _logger;

    protected HttpListener(ClientManager clientManager) {

        this.clientManager = clientManager;
        _logger = Logger.getLogger(HttpListener.class.getName());
    }


    private void command_switch_for_client(String urlSegment) throws Exception {

        switch (urlSegment) {
            case "/parameter":
                sendObjectToClient(urlSegment);
                break;
            case "/video":
                sendVideoContentToClient(urlSegment);
                break;
        }
    }



    private void sendVideoContentToClient(String name){

        DataFile dataFile = new DataFile(name);
        dataFile.setChannel(1);
        SendDataParameter sendDataParameter = dataFile;

        this.clientManager.sendDataToClient(sendDataParameter);

        this.clientManager.viewReadStreamReturnGenericObject.viewSendVideoContentToClient(name);
    }

    private void sendObjectToClient(String name) throws IOException, Exception {

        SendDataParameter parameter = new Parameter(name, null);

        this.clientManager.sendDataToClient(parameter);

        this.clientManager.viewReadStreamReturnGenericObject.viewSendModelToClient(parameter.getName());
    }


    protected void createListener(){

        try (ServerSocket serverSocket = new ServerSocket(6088)) {
            out.println("Http server on port " +  serverSocket.getLocalPort() + ", started!");

            while (true) {
                // ожидаем подключения
                Socket socket = serverSocket.accept();
                out.println("Client connected!");

                // для подключившегося клиента открываем потоки
                // чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

                    // ждем первой строки запроса
                    //while (!input.ready()) ;


                    // считываем и печатаем все что было отправлено клиентом
                    out.println();
                    while (input.ready()) {

                        String header = input.readLine();

                        String[] arrayHeader = header.split(" ");
                        if(arrayHeader.length - 1 > 1){
                            command_switch_for_client(arrayHeader[1]);
                        }
                        out.print(header + "\r\n");
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
