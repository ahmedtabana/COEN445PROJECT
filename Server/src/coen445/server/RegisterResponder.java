package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

/**
 * Created by Ahmed on 15-11-24.
 */
public class RegisterResponder extends BaseResponder {

    RegisterUpdateMessage registerUpdateMessage;

    public RegisterResponder() {

    }


    @Override
    public void respond() {

        System.out.println("this is the RegisterResponder respond method");

        addIpToData(IPAddress, port);
        displayListOfParticipants();

        Set<InetAddress> inetAddresses = Server.ipToData.keySet();

        for(InetAddress i : inetAddresses){

            ParticipantData data = Server.ipToData.get(i);

            UDPMessage udpMessage = new RegisterUpdateMessage();

            try {
                sendData = Server.getBytes(udpMessage);
            } catch (IOException e) {
                System.out.println("error in RegisterResponder getBytes");
                e.printStackTrace();
            }
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getIPAddress(), data.getPort());
            try {
                socket.send(sendPacket);
            } catch (IOException e) {
                System.out.println("error in RegisterResponder sendPacket");

                e.printStackTrace();
            }

        }

    }


    private void addIpToData(InetAddress ipAddress, int port) {
        System.out.println("Adding client to list of registered users");
        ParticipantData data = new ParticipantData(ipAddress,port);


        Server.ipToData.put(ipAddress, data);

        System.out.println("Adding client to list is Successful");

    }

    private void displayListOfParticipants(){

        System.out.println("Displaying the list of registered users:");
        System.out.println("");
        Set<InetAddress> mySet = Server.ipToData.keySet();

        for(InetAddress i : mySet){

            ParticipantData data = Server.ipToData.get(i);

            System.out.println("IPAddress: " + data.getIPAddress());
            System.out.println("Port: " + data.getPort());
            System.out.println(" ");
        }

    }
}

