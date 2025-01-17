package duma.asu.server;

import java.io.IOException;

public class structure {

    class Message{
        public String getName(){ return "name"; }

        public String getToName() { return "toName"; }

        public String getMassage() { return "massage"; }

    }

    class Server{
        public void runServer(){}

        void closeSocket(){}
    }


    class ClientManager implements Runnable{
        @Override
        public void run() {

        }

        private Message modelDeserialization() throws IOException, ClassNotFoundException{
            return new Message();
        }

        private void modelSerializable() throws IOException{}

        private String[] parsMessage(String massageToSend){
            return new String[8];
        }

        private void sendMessageToClient(Message message){}

        private void sendMessageToAll(Message message){}

        private void closeEverything(){}

        public void removeClient(){}
    }


    class AsuTcpServerApplication{
        public static void main(){}
    }

}
