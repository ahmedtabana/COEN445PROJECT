package coen445.server;

/**
 * Created by Ahmed on 15-11-29.
 */
public class UnavailableResponder extends BaseResponder {

    @Override
    public void respond() {
        super.respond();
        System.out.println("Room is Unavailable");
    }
}
