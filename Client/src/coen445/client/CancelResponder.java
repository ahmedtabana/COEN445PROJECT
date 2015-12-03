package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-02.
 */
public class CancelResponder extends BaseResponder {

    CancelMessage cancelMessage;
    @Override
    public void respond() {
        super.respond();

        System.out.println("this is the CancelResponder respond method");
        cancelMessage = (CancelMessage) message;
        cancelMessage.displayMessage();

        System.out.println("Freeing up time slot in local agenda");

    }
}
