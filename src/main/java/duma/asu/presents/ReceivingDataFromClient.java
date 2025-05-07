package duma.asu.presents;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.IntStream;

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


    /*public void start_receiving_data(){
        Thread runnable = this;
        runnable.start();

        runnable = null;
    }*/


    private void creates_file(byte[] data){

        byte[] byte_files = file_read_stream_to_array_byte(data);
        String file_name = new String(byte_files, 0, byte_files.length);
        try (FileOutputStream outputStream = new FileOutputStream(PACKED_VIDEO_FILES + "//" + file_name)) {
            outputStream.write(byte_files);
            System.out.println("Создан файл: " + file_name);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private byte[] file_read_stream_to_array_byte(byte[] data) {

        byte[] length_file_in_byte =  new byte[4];
        IntStream.range(0, length_file_in_byte.length).forEach(x -> length_file_in_byte[x] = data[x]);
        byte[] bytes_name = new byte[23];
        IntStream.range(0, bytes_name.length).forEach(x -> bytes_name[x] = data[x + length_file_in_byte.length]);
        int length_file = ByteBuffer.wrap(length_file_in_byte).getInt();
        byte[] byte_files = new byte[length_file];
        IntStream.range(0, byte_files.length)
                .forEach(x -> byte_files[x] = data[x + length_file_in_byte.length + bytes_name.length]);
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


    static void commandSwitch(){

    }
}
