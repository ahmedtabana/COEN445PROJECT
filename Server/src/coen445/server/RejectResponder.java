package coen445.server;

/**
 * Created by Ahmed on 15-12-01.
 */
public class RejectResponder extends BaseResponder {

    RejectMessage rejectMessage;

    @Override
    public void respond() {
        super.respond();
        System.out.println("Reject Responder respond method");

        rejectMessage = (RejectMessage) message;
    }
}
