package coen445.client;

/**
 * Created by Ahmed on 15-10-25.
 */

import Messages.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class Client {

    public static final int BUFFER_SIZE = 1024;

    DatagramSocket socket;
    MessageFactory factory = new MessageFactory();
    public static CopyOnWriteArraySet<InetAddress> availableParticipantsList;
    public static CopyOnWriteArrayList<DateTime> localAgenda;
    public static ConcurrentHashMap<Integer,MeetingData> meetingNumberToMeetingData;



    Client (){
        setupLocalAgenda();
    }

    private void setupLocalAgenda() {

        localAgenda = new CopyOnWriteArrayList<DateTime>();

        DateTime firstSlot = new DateTime();
        firstSlot.setDay(2);
        firstSlot.setMonth(10);
        firstSlot.setYear(2016);
        firstSlot.setTime(10);

        localAgenda.add(firstSlot);
    }

    public void connect(){

        try {

            socket = new DatagramSocket();
            availableParticipantsList = new CopyOnWriteArraySet<InetAddress>();
            meetingNumberToMeetingData = new ConcurrentHashMap<Integer,MeetingData>();

            for(InetAddress address : availableParticipantsList){
                System.out.println("available participant list at client startup");
                System.out.println(address);
            }
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);



           BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            int serverPort = getServerPort(inFromUser);

            InetAddress IPAddress = getServerAddress(inFromUser);

            byte[] sendData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);

            SendRegisterMessage(serverPort, IPAddress, sendPacket);


            socket.receive(receivePacket);

            UDPMessage registerConfirmMessage = getUdpMessage(receiveData);
            BaseResponder registerUpdateResponder = new RegisterUpdateResponder();
            registerUpdateResponder.setup(registerConfirmMessage, receivePacket.getAddress(), receivePacket.getPort(), socket);
            registerUpdateResponder.respond();


            while(true) {




                UDPMessage newMessage = null;
                newMessage = getMessage();
                sendData = getBytes(newMessage);
                sendPacket.setData(sendData);
                socket.send(sendPacket);



                socket.receive(receivePacket);


                UDPMessage fromServerMessage = getUdpMessage(receiveData);


                InetAddress address = receivePacket.getAddress();
                System.out.println(" RECEIVED Address: " + address);
                int port = receivePacket.getPort();
                System.out.println(" RECEIVED Port: " + port);


                ResponseThread myResponseThread = new ResponseThread(fromServerMessage,IPAddress,port, socket);

                Thread t = new Thread(myResponseThread);
                t.start();
                


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

    private void SendRegisterMessage(int serverPort, InetAddress IPAddress, DatagramPacket sendPacket) throws IOException {
        byte[] sendData;
        UDPMessage registerMessage = null;
        try {
            registerMessage = (UDPMessage) Class.forName("Messages.RegisterMessage").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sendData = getBytes(registerMessage);
        sendPacket.setData(sendData);
        socket.connect(IPAddress, serverPort);
        socket.send(sendPacket);
    }

    private UDPMessage getMessage() {
        UDPMessage message = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        message = factory.createMessage(br);
        message.displayMessage();
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

    private InetAddress getServerAddress(BufferedReader inFromUser) {
        InetAddress serverAddress = null;

        boolean isReady = false;
        while(!isReady) {

            System.out.println("Please enter the IP address for the server");

            try {
                serverAddress = InetAddress.getByName(inFromUser.readLine());
                System.out.println("You entered InetAddress = " + serverAddress);

                isReady = true;
            } catch (IOException e) {
                System.out.println("Not Valid IP address");
                isReady = false;
            }
        }
        return serverAddress;
    }

    private UDPMessage getUdpMessage(byte[] receiveData) {
        ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
        UDPMessage message = null;
        try {

            ObjectInputStream is = new ObjectInputStream(in);
            message = (UDPMessage) is.readObject();

            System.out.println("Client received message: "+ message);
            message.displayMessage();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static byte[] getBytes(UDPMessage message) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(message);
        System.out.println("From Client, creating message object: " + message.toString());
        return outputStream.toByteArray();
    }

    //todo must add error handling here
    private int getServerPort(BufferedReader inFromUser) {
        System.out.println("Please Configure Client");
        System.out.println("Enter the server port number that the client will connect to:");


        int serverPort = 0;
        while (serverPort == 0) {


            try {
                serverPort = Integer.parseInt(inFromUser.readLine());
            } catch (IOException e) {
                System.out.println("incorrect number");
            } catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }

        }
        System.out.println("You have entered Server port: " + serverPort);
        return serverPort;

    }


    public static void main(String[] args) throws Exception{

        Client client = new Client();
        client.connect();

    }

}
