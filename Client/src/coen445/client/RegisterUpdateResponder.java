package coen445.client;


import java.net.InetAddress;
import Messages.*;

/**
 * Created by Ahmed on 15-11-29.
 */
public class RegisterUpdateResponder extends BaseResponder {

    private  RegisterUpdateMessage registerUpdateMessage;
    @Override
    public void respond() {

        System.out.println("this is the RegisterUpdateResponder respond method");

        super.respond();
        registerUpdateMessage = (RegisterUpdateMessage) message;

        for(InetAddress address : registerUpdateMessage.getUpdatedListOfParticipants()){

            Client.availableParticipantsList.add(address);
        }

    }
}
