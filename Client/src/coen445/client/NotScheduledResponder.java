package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-02.
 */
public class NotScheduledResponder extends BaseResponder {

    NotScheduledMessage notScheduledMessage;
    @Override
    public void respond() {
        super.respond();
        System.out.println("this is the NotScheduledResponder respond method");
        notScheduledMessage = (NotScheduledMessage) message;
        notScheduledMessage.displayMessage();

        System.out.println("updating local agenda");

    }
}
