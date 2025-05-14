package duma.asu.presents;

import duma.asu.models.interfaces.SendDataParameter;

import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class CreateFiles extends Thread{

    private SendDataParameter sendDataParameter;

    private boolean running;

    private int header_length;

    private Logger _logger;

    static Path PACKED_VIDEO_FILES;


    public CreateFiles(SendDataParameter sendDataParameter) throws SocketException {
        this.sendDataParameter = sendDataParameter;
        _logger = Logger.getLogger(CreateFiles.class.getName());
        PACKED_VIDEO_FILES = Path.of("/var/www/video/window_0");
        header_length = 4;
    }


    @Override
    public void run() {
        try{
            creates_file();
        }catch (Exception ex){
            _logger.info(ex.getMessage());
        }

    }


    protected void start_and_finish_thread() throws InterruptedException {
        this.start();
        this.join();
        this.interrupt();
    }

    private synchronized void creates_file() throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("create video file: "  + sendDataParameter.getName());
        Thread.sleep(100);
    }

}
