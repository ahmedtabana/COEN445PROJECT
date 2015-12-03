package coen445.client;

import java.io.IOException;
import java.net.DatagramPacket;
import Messages.*;

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

            addTimeSlotToLocalAgenda();
            System.out.println("Sending Accept Message");
            UDPMessage acceptMessage = new AcceptMessage(inviteMessage.getMeetingNumber());
            sendMessage(acceptMessage);
        }
    }

    private void sendMessage(UDPMessage sendMessage) {
        try {
            sendData = Client.getBytes(sendMessage);
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

    private void addTimeSlotToLocalAgenda() {
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

