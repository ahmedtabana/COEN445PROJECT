package coen445.server;

/**
 * Created by Ahmed on 15-11-24.
 */
public class RegisterMessage extends UDPMessage {

    public RegisterMessage(){

        setType("Register");
        System.out.println("Register message created");
    }


}
