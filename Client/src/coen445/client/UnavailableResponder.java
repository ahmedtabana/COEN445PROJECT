package coen445.client;
import Messages.*;

/**
 * Created by Ahmed on 15-11-29.
 */
public class UnavailableResponder extends BaseResponder {

    UnavailableMessage unavailableMessage;
    @Override
    public void respond() {
        super.respond();
        unavailableMessage = (UnavailableMessage) message;
        unavailableMessage.displayMessage();
    }
}

