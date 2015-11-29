package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;

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
            inviteMessage = new InviteMessage();
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
    }


    private boolean RoomIsUnavailable() {
        //check inside the server if the date and time given are reserved in the room reservation list
        // if reserved return false
        // otherwise return true
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