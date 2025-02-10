
import java.util.Date;
import java.util.UUID;

/// ServerApplication ///

/*
1. Подключать соединения с клиентами в асинхронным режиме.
2. Отправлять и принимать запрос на изменение или устанавления нового значения.
3. Сохранять данные в базе данных.

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
        public String parameter;
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

