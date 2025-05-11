package duma.asu.presents;

import java.beans.Encoder;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReceivingDataFromClient extends Thread{


    private DatagramSocket socket;
    private boolean running;


    private Logger _logger;

    static Path PACKED_VIDEO_FILES;

    public ReceivingDataFromClient() throws SocketException {
        socket = new DatagramSocket(4445);
        _logger = Logger.getLogger(ReceivingDataFromClient.class.getName());
        PACKED_VIDEO_FILES = Path.of("/var/www/video/window_0");
    }


    @Override
    public void run() {
        try{
            new DeleteFilesInDirectoryNginx(); //.start();
            receiving();
        }catch (Exception ex){
            socket.close();
            _logger.info(ex.getMessage());
        }

    }


    private synchronized void creates_file(byte[] data){

        int header_length = 4;
        byte[] name_length_in_byte = new byte[header_length];
        IntStream.range(0, name_length_in_byte.length).forEach(n -> name_length_in_byte[n] = data[n]);
        int length_name = ByteBuffer.wrap(name_length_in_byte).getInt();
        byte[] file_length_in_byte = new byte[header_length];
        IntStream.range(0, file_length_in_byte.length).forEach(n -> file_length_in_byte[n] = data[header_length + n]);
        int length_in_file = ByteBuffer.wrap(file_length_in_byte).getInt();
        byte[] byte_file = read_file(data, (header_length * 2) + length_name , length_in_file);
        String file_name = new String(data, header_length * 2, length_name);
        try (FileOutputStream outputStream = new FileOutputStream(PACKED_VIDEO_FILES + "//" + file_name)) {
            outputStream.write(byte_file);
            System.out.println("Создан файл: " + file_name + " размер: " + byte_file.length);
            Thread.sleep(100);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        byte_file = null;
    }

    private byte[] read_file(byte[] data, int off,  int length_file) {

        byte[] byte_files = new byte[length_file];
        IntStream.range(0, byte_files.length)
                .forEach(n -> byte_files[n] = data[off + n]);
        return byte_files;
    }


    private void receiving(){
        running = true;
        try{

            while (running) {

                byte[] buf = new byte[24500];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                byte[] data  = packet.getData();
                creates_file(data);
                //service.schedule(DeleteFilesInDirectoryNginx::start_app, 2, TimeUnit.SECONDS);
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
