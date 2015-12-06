package coen445.client;

import Messages.AddedMessage;

/**
 * Created by Ahmed on 15-12-05.
 */
public class AddedResponder extends BaseResponder {

    AddedMessage addedMessage;
    @Override
    public void respond() {
        super.respond();

        addedMessage = (AddedMessage) message;
        addedMessage.displayMessage();

    }
}
