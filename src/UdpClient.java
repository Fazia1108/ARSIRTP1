import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpClient {
    public static void main(String[] args) {
        String serverIp = "127.0.0.1";  // Adresse du serveur
        int serverPort = 12345;  // Port du serveur

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            // Préparer la requête
            String T1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            byte[] sendData = T1.getBytes();
            InetAddress serverAddress = InetAddress.getByName(serverIp);

            // Envoyer la requête au serveur
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);
            System.out.println("Requête envoyée au serveur: " + T1);

            //  Réception de la réponse
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            // Afficher la réponse
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String T2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            String[] timestamps = receivedMessage.split(";");
            long T2_ms = dateFormat.parse(T2).getTime();
            long T1_ms = dateFormat.parse(timestamps[0]).getTime();
            long T1bis_ms = dateFormat.parse(timestamps[1]).getTime();
            long T2bis_ms = dateFormat.parse(timestamps[2]).getTime();

            long delta = (T2_ms - T1_ms) - (T2bis_ms - T1bis_ms);
            long theta = ((T1bis_ms + T2bis_ms) / 2) - ((T1_ms + T2_ms) / 2);

            if (timestamps.length == 3) {
                System.out.println(" T1  (envoyé par le client) : " + timestamps[0]);
                System.out.println(" T'1 (réception par le serveur) : " + timestamps[1]);
                System.out.println(" T'2 (envoi par le serveur) : " + timestamps[2]);
                System.out.println(" T2 (réception par le client fdp) : " + T2);
                System.out.println(" delta: " + delta);
                System.out.println(" theta : " + theta);
            } else {
                System.out.println("⚠ Erreur de format dans la réponse !");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
