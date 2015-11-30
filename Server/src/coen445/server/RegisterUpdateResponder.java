package coen445.server;

import coen445.client.Client;

import java.net.InetAddress;

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
