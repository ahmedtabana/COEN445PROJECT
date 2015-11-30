package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by Ahmed on 15-11-30.
 */
public class InviteResponder extends BaseResponder {

    @Override
    public void respond() {
        super.respond();
        System.out.println("Invite responder respond method");
        InviteMessage inviteMessage = (InviteMessage) message;
        inviteMessage.displayMessage();

        if (ClientAvailable()) {


            UDPMessage acceptMessage = new AcceptMessage(message.getRequestNumber());

            try {
                sendData = Server.getBytes(acceptMessage);
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

            System.out.println("Client not available");
        }
    }

    private boolean ClientAvailable() {
        System.out.println("Checking the local client");
        return false;
    }
}
