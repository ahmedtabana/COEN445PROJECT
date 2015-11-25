package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-11-03.
 */
public class ResponseThread implements Runnable {

    byte[] sendData;
    InetAddress IPAddress;
    int port;
    UDPMessage message;
    DatagramSocket socket;
    Responder responder;

    ResponseThread(UDPMessage message, InetAddress IPAddress, int port, DatagramSocket socket){

        sendData = new byte[Server.BUFFER_SIZE];
        this.IPAddress = IPAddress;
        this.port = port;
        this.socket = socket;
        this.message = message;
        responder = ResponderFactory.createResponder(message.getType());
    }

    @Override
    public void run() {

        responder.respond();
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
        message.setType("Response");
        return message;
    }

}

