package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

/**
 * Created by Ahmed on 15-11-24.
 */
public class RequestResponder extends BaseResponder {

    RequestMessage requestMessage;

    public RequestResponder() {
    }


    @Override
    public void respond() {

        System.out.println("this is the RequestResponder respond method");
        requestMessage = (RequestMessage) message;

        if (RoomIsUnavailable()) {

            UDPMessage unAvailableMessage = new UnavailableMessage(message.getRequestNumber());

            try {
                sendData = Server.getBytes(unAvailableMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            UDPMessage inviteMessage;
            inviteMessage = new InviteMessage(IPAddress,((RequestMessage) message).getDateTime(),((RequestMessage) message).getTopic());
            System.out.println("creating invite message");
            inviteMessage.displayMessage();

            //send invite message to only the participants in this message


            for(InetAddress i : ((RequestMessage) message).getSetOfParticipants()){

                ParticipantData data = Server.ipToData.get(i);


                try {
                    sendData = Server.getBytes(inviteMessage);
                } catch (IOException e) {
                    System.out.println("error in RequestResponder getBytes");
                    e.printStackTrace();
                }
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getIPAddress(), data.getPort());
                try {
                    socket.send(sendPacket);
                } catch (IOException e) {
                    System.out.println("error in RequestResponder sendPacket");

                    e.printStackTrace();
                }

            }

//            try {
//                sendData = Server.getBytes(inviteMessage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
//            try {
//                socket.send(sendPacket);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }


    private boolean RoomIsUnavailable() {

        for (DateTime dateTime : Server.roomReservationList) {

            boolean conflictFound = dateTime.getDay() == requestMessage.getDay() && dateTime.getMonth() == requestMessage.getMonth() &&
                    dateTime.getYear() == requestMessage.getYear() && dateTime.getTime() == requestMessage.getTime();
            if (conflictFound) {
                System.out.println("conflict found");
                return true;
            }
        }

        System.out.println("no conflict found");

        return false;

    }
}