package duma.asu.presents;

import duma.asu.models.interfaces.SendDataParameter;
import duma.asu.models.serializableModels.DataFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class CreateFiles extends Thread{

    private DataFile sendDataParameter;

    private boolean running;

    private int header_length;

    private Logger _logger;


    public CreateFiles(DataFile sendDataParameter) throws SocketException {
        this.sendDataParameter = sendDataParameter;
        _logger = Logger.getLogger(CreateFiles.class.getName());
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
        try(FileOutputStream input = new FileOutputStream( ClientManager.PACKED_VIDEO_FILES + "/" + sendDataParameter.getName())){
            input.write(sendDataParameter.getData(), 0, sendDataParameter.getData().length);
            input.flush();
            System.out.println("create video file: "  + sendDataParameter.getName());
            Thread.sleep(100);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
