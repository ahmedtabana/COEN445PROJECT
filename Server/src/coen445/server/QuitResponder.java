package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

/**
 * Created by Ahmed on 15-12-05.
 */
public class QuitResponder extends BaseResponder {

    RegisterUpdateMessage registerUpdateMessage;

    @Override
    public void respond() {
        super.respond();
        System.out.println("this is the QuitResponder respond method");

        removeIpToData(IPAddress, port);
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

    private void removeIpToData(InetAddress ipAddress, int port) {
        System.out.println("removing client to list of registered users");


        Server.ipToData.remove(ipAddress);

        System.out.println("Removing client from list is Successful");

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
