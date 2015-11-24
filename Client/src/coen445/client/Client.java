package coen445.client;

/**
 * Created by Ahmed on 15-10-25.
 */

import coen445.server.MessageFactory;
import coen445.server.UDPMessage;

import java.io.*;
import java.net.*;

public class Client {

    DatagramSocket socket;
    MessageFactory factory = new MessageFactory();

    Client (){
    }

    public void connect(){

        try {

            socket = new DatagramSocket();

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);



           BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

             int serverPort = getServerPort(inFromUser);

            String serverAddress = getServerAddress(inFromUser);
            InetAddress IPAddress = InetAddress.getByName(serverAddress);
            byte[] sendData = new byte[1024];

            UDPMessage message = null;

            message = getMessage();



            sendData = getBytes(message);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);


            while(true) {


                socket.connect(IPAddress, serverPort);

                socket.send(sendPacket);
                socket.receive(receivePacket);
                UDPMessage fromServerMessage = getUdpMessage(receiveData, message);

                System.out.println("Enter Message type:");
                System.out.println("Format: coen445.server.MessageClassName");

                String type2 = inFromUser.readLine();
                message.setType(type2);
                sendData = getBytes(message);
                sendPacket.setData(sendData);

                socket.disconnect();


            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            socket.close();
        }

    }

    private UDPMessage getMessage() {
        UDPMessage message = null;
        String type = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (message == null) {


            System.out.println("Please enter message type");

            try {
                type = br.readLine();
                message = factory.createMessage(type);

            } catch (IOException e) {
                System.out.println("This is not a correct message type");
            }
        }

        message.displayRequestMessage();
        return message;
    }

    private String getMessageType(BufferedReader inFromUser) {
        String messageType = null;

        try {
            messageType= inFromUser.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageType;
    }

    private String getServerAddress(BufferedReader inFromUser) {
        String serverAddress = null;

        System.out.println("Please enter the IP address for the server");

        try {
            serverAddress= inFromUser.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverAddress;
    }

    private UDPMessage getUdpMessage(byte[] receiveData, UDPMessage message) {
        ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
        try {

            ObjectInputStream is = new ObjectInputStream(in);
            message = (UDPMessage) is.readObject();

            System.out.println("UDPMessage object received = "+ message);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    private byte[] getBytes(UDPMessage message) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(message);
        System.out.println("From Client, creating message object:");
        System.out.println(message.toString());
        return outputStream.toByteArray();
    }


    private int getServerPort(BufferedReader inFromUser) throws IOException {
        System.out.println("Please Configure Client");
        System.out.println("Enter the server port number that the client will connect to:");


        int serverPort = Integer.parseInt( inFromUser.readLine() ) ;
        System.out.println("You have entered Server port: " + serverPort);
        return serverPort;
    }


    public static void main(String[] args) throws Exception{

        Client client = new Client();
        client.connect();

    }

}
