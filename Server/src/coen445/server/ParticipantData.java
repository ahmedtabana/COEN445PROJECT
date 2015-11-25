package coen445.server;

import java.net.InetAddress;

/**
 * Created by Ahmed on 15-11-24.
 */
public class ParticipantData {

    private InetAddress IPAddress;
    private int port;

    public ParticipantData(InetAddress IPAddress, int port){

        this.port = port;
        this.IPAddress = IPAddress;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(InetAddress IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
