package duma.asu.presents;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DeleteFilesInDirectoryNginx extends Thread{

    static Map<Integer, Thread> files_nginx;
    File file = new File(ReceivingDataFromClient.PACKED_VIDEO_FILES.toUri());

    private Logger _log;

    public DeleteFilesInDirectoryNginx() {
       files_nginx = new HashMap<>();
        _log = Logger.getLogger(ReceivingDataFromClient.class.getName());
    }

    @Override
    public void run() {
        try {
            start_app();
            readDirectory();
        }catch (Exception ex){
            _log.info(ex.getMessage());
        }


    }


    private void start_app(){
        try{
            if(files_nginx.entrySet()
                    .stream().anyMatch(n -> n.getKey() == 1)){

                for (Map.Entry<Integer, Thread> run: files_nginx.entrySet()) {
                    if(run.getKey().equals(1)){

                        Thread thread = run.getValue();

                        thread.interrupt();

                        files_nginx.remove(run.getKey());

                        System.out.println("Поток # " + thread + " удален -> " + thread.getState());

                        run = null;
                        thread = null;
                    }
                }
            }

            /*Thread runnable = this;
            runnable.start();*/

            files_nginx.put(1, this);

            //runnable = null;

        }catch (Exception ex){
            _log.info(ex.getMessage());
        }
    }

    private void readDirectory() throws InterruptedException {

        String[] array_list = file.list();


        Thread.sleep(2000);
    }
}
