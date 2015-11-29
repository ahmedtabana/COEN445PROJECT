package coen445.server;

import com.sun.xml.internal.rngom.parse.host.Base;

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
//    IResponder IResponder;
    BaseResponder Responder;
    ResponseThread(UDPMessage message, InetAddress IPAddress, int port, DatagramSocket socket){

        sendData = new byte[Server.BUFFER_SIZE];
        this.IPAddress = IPAddress;
        this.port = port;
        this.socket = socket;
        this.message = message;
//        IResponder = ResponderFactory.createResponder(message.getType());
        Responder = ResponderFactory.createResponder(message.getType());

    }

    @Override
    public void run() {
        Responder.setup( message,  IPAddress,  port,  socket);
        Responder.respond();

//        IResponder.respond();

    }



}

