package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-03.
 */
public class UnauthorizedResponder extends  BaseResponder {



    UnauthorizedMessage unauthorizedMessage;
    @Override
    public void respond() {
        super.respond();

        unauthorizedMessage = (UnauthorizedMessage) message;
        unauthorizedMessage.displayMessage();
    }
}
