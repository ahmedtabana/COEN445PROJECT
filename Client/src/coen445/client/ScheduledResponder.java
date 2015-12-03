package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-02.
 */
public class ScheduledResponder extends BaseResponder {

    ScheduledMessage scheduledMessage;
    @Override
    public void respond() {
        super.respond();
        System.out.println("this is the ScheduledResponder respond method");

        scheduledMessage = (ScheduledMessage) message;
        scheduledMessage.displayMessage();

    }
}
