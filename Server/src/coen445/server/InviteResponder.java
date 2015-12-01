package coen445.server;

import coen445.client.Client;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by Ahmed on 15-11-30.
 */
public class InviteResponder extends BaseResponder {

    InviteMessage inviteMessage;

    @Override
    public void respond() {
        super.respond();

        System.out.println("Invite responder respond method");
        inviteMessage = (InviteMessage) message;
        inviteMessage.displayMessage();

        if (!ClientIsAvailable()) {

            System.out.println("Sending Reject Message");
            UDPMessage rejectMessage = new RejectMessage(inviteMessage.getMeetingNumber());
            sendMessage(rejectMessage);

        } else {

            updateLocalAgenda();
            System.out.println("Sending Accept Message");
            UDPMessage acceptMessage = new AcceptMessage(inviteMessage.getMeetingNumber());
            sendMessage(acceptMessage);
        }
    }

    private void sendMessage(UDPMessage sendMessage) {
        try {
            sendData = Server.getBytes(sendMessage);
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

    private void updateLocalAgenda() {
        System.out.println("Updating local Agenda");
        Client.localAgenda.add(inviteMessage.getDateTime());

    }

    private boolean ClientIsAvailable() {

        System.out.println("Checking the local MS agenda");

        for (DateTime dateTime : Client.localAgenda) {

            boolean conflictFound = dateTime.getDay() == inviteMessage.getDay() && dateTime.getMonth() == inviteMessage.getMonth() &&
                    dateTime.getYear() == inviteMessage.getYear() && dateTime.getTime() == inviteMessage.getTime();
            if (conflictFound) {
                System.out.println("Person is not Available");
                return false;
            }
        }

        System.out.println("Person is Available");

        return true;

    }

}
