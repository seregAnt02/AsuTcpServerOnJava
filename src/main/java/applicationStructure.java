
import duma.asu.models.interfaces.SendDataParameter;

import java.util.Date;
import java.util.UUID;

/// ServerApplication ///

/*
1. Подключить соединения с клиентами в асинхронным режиме.
2. Отправлять и принимать запрос на изменение или устанавления нового значения.
    2.1 Реализовать асинхронный прослушиватель на порту сервера, с входящего http запроса прикладного уровня,
        на обновление, удаление, добавления данных, с последующей исходящей записи в поток транспорного уровня к клиенту.
    2.2 Реализовать на транспортном уровне считывание входящего потока с сохранением обновленнных данныех в базе данных.
3. Реализовать асинхронный функционал входящего потока на транспортном уровне для добавления, сохранения,
    удаление ауди-видео контента.
    3.1 Поднять сервер Nginx для сохранения видео контента.
    3.2 Реализовать функционал транспортного уровня для считывание входящего потока для создания ауди-видео файлов.
    3.3 Сохранить ауди, видео файлы в папке сервера Nginx.
    3.4 Реализовать фукционал считывание xml данных файла dash.mpd и удалению файлов
        на основе значений из метаданных.
* */

class applicationStructure{

    // Сериализуемые модели.
    class Duma{
        public UUID id;
        public String guid;
        public Date datetime;
        public String macAdress;
        public String ipAdress;
        public int port;
        public String status;
        public int number;
    }

    class Parameter {

        private static final long serialVersionUID = 1L;


        private UUID id;
        private java.sql.Date datetime;
        private String name;
        private String codParameter;
        private String lastUpdate;
        private int meaning;
        private int dumaId;// внешний ключь
        //public Duma duma  = new Duma();//навигационное свойство


        public Parameter(String name, String extension) {
            this.name = name;
        }

        public String getName(){ return name; }


        public int getMeaning(){ return meaning; }


        public int setMeaning(int meaning){ return this.meaning = meaning; }


        @Override
        public String toString() {
            return "Parameter{" +
                    "id=" + id +
                    ", datetime=" + datetime +
                    ", name='" + name + '\'' +
                    ", codParameter='" + codParameter + '\'' +
                    ", lastUpdate='" + lastUpdate + '\'' +
                    ", meaning=" + meaning +
                    ", dumaId=" + dumaId +
                    '}';
        }
    }


    class DataFile {

        private static final long serialVersionUID = 2L;


        public UUID id;
        public String nameFile;
        public Date dateTime;
        public Integer filesize;
        public byte[] data;
        public String extension;
        public Integer indexFile;
        public Integer numberFolder;
        public Integer headerSize;


        public DataFile(String nameFile) {
            this.nameFile = nameFile;
        }


        public String getName(){ return this.nameFile; }


        public String setExtension(String extension){
            return this.extension = extension;
        }
    }
    //----------------------


    class Server{
        /*
            1. Подключить соединения с клиентами в асинхронным режиме:
               * Добавить объект ServerSocket, этот класс реализует серверные сокеты,
                   серверный сокет ожидает поступления запросов по сети.
               * В функций метода runServer(), реализовать функцию прослушивателя в виде цикла, с
                   выполнением условия на закрытия сокета, объекта ServerSocket.
               * Внутри цикла добавить прослушивание потока к этому сокету, для этого
                   метод должен блокировать поток до тех пор, пока подключение не будет закрыто.
               * После установления соединения с клиентом передать сокет в аргументе объекта ClientManager
               * Создать объект типа Thread и запустить поток.
               * Пробросить объект типа ClassNotFoundException и реализовать в конструкций try/catch
                 на ошибки ввода-вывода т.е IOException.

        */
        public void runServer(){}

        // Закрывает соединение.
        void closeSocket(){}
    }


    class ClientManager implements Runnable{

        /*
            2. Отправлять и принимать запрос на изменение или устанавления нового значения:
              * Создать объект ClientManager и имплементировать функциональный интерфейс Runnable.
              * Добавить JSSE функциональность для шифрования данных, аутентификации сервера,
                   целостности сообщения и дополнительной аутентификации клиента. Возможно есть
                   возможность через аутентификационные хеш-токены, протокола http/https.
              * Создать обобщенный объект типа ReadWriteStreamAndReturnGenericObject, с одним из методов
                 modelDeserialization(), с возвращаемым обобщенным типом T.
              * Создать интерфейс SendDataParameter, для возврата в методе modelDeserialization
                 десереализованного объекта, т.е. добавить слабасвязанность и универсальность.
              * Создать массив типа Map<String, ClientManager>, для хранения подключенных клиентов.
              * В перегруженном методе run() объекта Runnable, в цикле с условием на
                 подключение с клиентом, организовать чтение входящего потока и во вложенном
                 методе modelDeserialization(), десериализовать в объект типа SendDataParameter.
              * Создать вложенный метод sendModelToClient(SendDataParameter model) и реализовать функцию
                 по поиску в массиве клиента, по ключу имени клиента и оправить клиенту входящий
                 аргумент метода типа SendDataParameter, об успешном подключений нового клиента на сервере.
              * Реализовать в конструкций try/catch на ошибки ввода-вывода т.е IOException,
                при исключений вода-вывода в методе closeEverything() закрыть входящий поток,
                удалить клиента с массива клиентов или вывести стек-трейс консоли при возникновений
                ошибки.
        */

        // В методе run() определяется весь тот код, который выполняется при запуске потока.
        // После определения объекта Runnable он передаётся в один из конструкторов класса Thread
        @Override
        public void run() {

        }

        // Считывание входящего потока и  десерилизация в объект.
        private SendDataParameter modelDeserialization(){return null;}

        // Запись объекта в поток, сереализуемой модели.
        private void sendModelToClient(SendDataParameter model){}

        // Закрытие входящего потока.
        private void closeEverything(){}

        // Удаление клиента с коллекций масива.
        public void removeClient(){}
    }


    class HttpServer{

        /*
            2.1 Реализовать асинхронный прослушиватель на порту сервера, с входящего http запроса прикладного уровня:
                * Реализовать фукционал ServerSocket, для прослушивания подключаемых клиентов.
                * C http Get запроса из последнего сегмента строки, распределить комманды на обновление
                    данных или запуска процесса на клиенте, по созданию ауди-видео контента. Согласно
                    протокола транспортного уровня TCP и UDP.
                * Отправить сериализованный объект типа SendDataParameter к клиенту.
                * Перенаправить пользователя на страницу с обновленными данными или к плееру
                   видеопроигрывателя Dash, библиотеки Video.js, согласно коммандной строки сегмента запроса.
        */

        void httpListener(){}


        void sendObjectToClient(String name){}
    }


// Save model sql

    class SaveModelSql{
        // Сохранение модели в базе данных.
        void saveModel(){}
    }

}

