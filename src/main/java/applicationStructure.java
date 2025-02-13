
import java.util.Date;
import java.util.UUID;

/// ServerApplication ///

/*
1. Подключать соединения с клиентами в асинхронным режиме.
2. Отправлять и принимать запрос на изменение или устанавления нового значения.
    2.1 Реализовать аутентификацию исходящего http запроса, по имени клиента, зарегистрированного в базе данных.
    2.2 Реализовать асинхронный прослушиватель на порту сервера, с исходящего http запроса прикладного уровня,
        на обновление, удаление, добавления данных, с последующей исходящей записи в поток транспорного уровня к клиенту.
    2.3 Реализовать на транспортном уровне считывание входящего потока с сохранением обновленнных данныех в базе данных.
3. Реализовать асинхронный функционал на транспортном уровне для добавления, сохранения,
    удаление входящего потока ауди-видео контента.
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
        public String migration;
        public int age;
    }

    class Parameter{
        public int id;
        public java.sql.Date datetime;
        public String name;
        public String codParameter;
        public String lastUpdate;
        public int meaning;
        public int dumaId;// внешний ключь
        public Duma duma  = new Duma();//навигационное свойство
    }
    //----------------------

    class Server{
        // Подключение клиентов в многопоточном режиме.
        public void runServer(){}

        // Закрывает соединение.
        void closeSocket(){}
    }


    class ClientManager implements Runnable{

        // В методе run() определяется весь тот код, который выполняется при запуске потока.
        // После определения объекта Runnable он передаётся в один из конструкторов класса Thread
        @Override
        public void run() {

        }

        // Считывание входящего потока и  десерилизация в объект.
        private Parameter modelDeserialization(){return null;}

        // Запись объекта в поток, сереализуемой модели.
        private void sendModelToClient(Parameter message){}

        // Закрытие входящего потока.
        private void closeEverything(){}

        // Удаление клиента с коллекций масива.
        public void removeClient(){}
    }

// Save model sql

    class SaveModelSql{
        // Сохранение модели в базе данных.
        void saveModel(){}
    }

}

