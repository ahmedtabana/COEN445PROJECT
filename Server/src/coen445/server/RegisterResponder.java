package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Set;

/**
 * Created by Ahmed on 15-11-24.
 */
public class RegisterResponder extends BaseResponder {


    public RegisterResponder() {
    }


    @Override
    public void respond() {

        System.out.println("this is the RegisterResponder respond method");

        addIpToData(IPAddress,port);
        displayListOfParticipants();
        processTheMessage(message);
        try {
            sendData = Server.getBytes(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private UDPMessage processTheMessage(UDPMessage message) {
        message.setType("RegisterResponse");
        return message;
    }

    private void addIpToData(InetAddress ipAddress, int port) {
        System.out.println("Adding client to list of registered users");
        ParticipantData data = new ParticipantData(ipAddress,port);


        Server.IpToData.put(ipAddress, data);

        System.out.println("Adding client to list is Successful");

    }

    private void displayListOfParticipants(){

        System.out.println("Displaying the list of registered users:");
        System.out.println("");
        Set<InetAddress> mySet = Server.IpToData.keySet();

        for(InetAddress i : mySet){

            ParticipantData data = Server.IpToData.get(i);

            System.out.println("IPAddress: " + data.getIPAddress());
            System.out.println("Port: " + data.getPort());
            System.out.println(" ");
        }

    }
}

