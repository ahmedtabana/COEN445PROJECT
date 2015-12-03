package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by Ahmed on 15-12-02.
 */
public class RefreshResponder extends BaseResponder {

    RefreshMessage refreshMessage;

    @Override
    public void respond() {
        super.respond();

        UDPMessage udpMessage = new RefreshMessage();
        try {
            sendData = Server.getBytes(udpMessage);
        } catch (IOException e) {
            System.out.println("error in getBytes");
            e.printStackTrace();
        }
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress ,port );
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            System.out.println("error in sendPacket");

            e.printStackTrace();
        }

    }
}
