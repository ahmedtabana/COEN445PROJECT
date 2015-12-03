package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-02.
 */
public class ConfirmResponder extends BaseResponder {

    ConfirmMessage confirmMessage;


    @Override
    public void respond() {
        super.respond();
        System.out.println("this is the ConfirmResponder respond method");

        confirmMessage = (ConfirmMessage) message;
        confirmMessage.displayMessage();
    }
}
