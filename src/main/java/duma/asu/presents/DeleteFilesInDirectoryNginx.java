package duma.asu.presents;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DeleteFilesInDirectoryNginx extends Thread{

    //private final Object lock = new Object();
    private static Map<Integer, Thread> files_nginx = new HashMap<>();
    File file = new File(ReceivingDataFromClient.PACKED_VIDEO_FILES.toUri());


    private Logger _log;


    public DeleteFilesInDirectoryNginx() {
        this.start();
        _log = Logger.getLogger(ReceivingDataFromClient.class.getName());
    }

    @Override
    public void run() {
        try {
            add_in_arrya_new_thred_and_delete_old();

            while (true) {
                Thread.sleep(10000);
                int number_file_to_delete = new XmlParser(this.file).number_file_to_delete();
                file_delete(number_file_to_delete);
            }
        }catch (Exception ex){
            _log.info(ex.getMessage());
        }


    }


    private void add_in_arrya_new_thred_and_delete_old(){

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
            files_nginx.put(1, this);
            System.out.println("Added new thread: " + this.getName());
    }


    private int file_delete(int number_file_to_delete) {

        File[] array_files = file.listFiles();
        for (int i = 0; i < array_files.length; i++){
            String[] name_file = array_files[i].getName().split("-");
            if(name_file.length == 3){
                String[] file_extension = name_file[2].split("\\.");
                int number_file = Integer.parseInt(file_extension[0]);
                if(number_file_to_delete <= number_file){
                    //if(array_files[i].delete())
                        System.out.println("Файл с номером: " + number_file + " удален.");
                }
                array_files = null;
                file_extension = null;
                name_file = null;
                return  number_file;
            }
        }
        return 0;
    }

}
