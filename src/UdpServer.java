import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpServer {
    public static void main(String[] args) {
        int port = 12345;  // le serveur écoute sur ce port

        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println(" Serveur UDP en attente sur le port " + port + "...");

            while (true) {
                byte[] buffer = new byte[1024];  // buffer pour stocker le message reçu

                // Recevoir un message du client
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivedPacket);
                String T1bis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

                // Extraire l'adresse du client
                InetAddress clientAddress = receivedPacket.getAddress();
                int clientPort = receivedPacket.getPort();
                String receivedMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
                String T1= receivedMessage ;
                System.out.println("Message reçu de " + clientAddress + ":" + clientPort + " -> " + receivedMessage);

                //  Obtenir l'heure actuelle
                String T2bis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

                String responseMessage = T1 + ';' + T1bis + ';' + T2bis ;
                // Envoyer l'heure au client
                byte[] responseData = responseMessage.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                serverSocket.send(responsePacket);

                System.out.println(" Heure envoyée à " + clientAddress + ":" + clientPort + " -> " + T2bis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //iovbfszoif souvs 
    }
}