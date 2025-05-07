package duma.asu.presents;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DeleteFilesInDirectoryNginx extends Thread{

    private final Object lock = new Object();
    private static Map<Integer, Thread> files_nginx = new HashMap<>();
    File file = new File(ReceivingDataFromClient.PACKED_VIDEO_FILES.toUri());


    private Logger _log;

    private int last_number_file;
    public DeleteFilesInDirectoryNginx() {
        this.start();
        _log = Logger.getLogger(ReceivingDataFromClient.class.getName());
    }

    @Override
    public void run() {
        try {
            add_in_arrya_new_thred_and_delete_old();
            readDirectory();
        }catch (Exception ex){
            _log.info(ex.getMessage());
        }


    }


    private void add_in_arrya_new_thred_and_delete_old(){
        try{
            if(files_nginx.entrySet()
                    .stream().anyMatch(n -> n.getKey() == 1)){

                for (Map.Entry<Integer, Thread> run: files_nginx.entrySet()) {
                        Thread thread = run.getValue();
                        thread.interrupt();

                        files_nginx.remove(run.getKey());

                        System.out.println("Поток # " + thread + " удален -> " + thread.getState());

                        run = null;
                        thread = null;
                }
            }
            //this.start();
            files_nginx.put(1, this);
            System.out.println("Added new thread: " + this.getName());

        }catch (Exception ex){
            _log.info(ex.getMessage());
        }
    }


    private synchronized int file_delete(File[] array_files) {

        for (int i = 0; i < array_files.length; i++){
            String[] name_file = array_files[i].getName().split("-");
            if(name_file.length == 3){
                String[] file_extension = name_file[2].split("\\.");
                int number_file = Integer.parseInt(file_extension[0]);
                if(this.last_number_file <= number_file){
                    if(array_files[i].delete())
                        System.out.println("Файл с номером: " + number_file + " удален.");
                    this.last_number_file = number_file;
                }
                return  number_file;
            }
        }
        return 0;
    }


    private int convert_to_int(String[] split){
        String number = split[2].split("\\.")[0];
        String volume = "";
        for (int i = number.length()-1; i >= 0; i--){
            if(number.charAt(i) != '0')
                volume += number.charAt(i);
        }
        int number_file = Integer.parseInt(volume);
        return  number_file;
    }


    private void readDirectory() throws InterruptedException {

        while (true){
            Thread.sleep(10000);

            File[] array_list = file.listFiles();

            if(Arrays.stream(array_list).anyMatch((n) -> n.getName().equals("dash.mpd"))){
                _log.info("file find successful");
            }

            file_delete(array_list);

            array_list = null;
        }
    }
}
