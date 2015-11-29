package coen445.server;

/**
 * Created by Ahmed on 15-11-24.
 */
public class CancelMessage extends UDPMessage {


    CancelMessage(){

        setType("Cancel");
    }
}
