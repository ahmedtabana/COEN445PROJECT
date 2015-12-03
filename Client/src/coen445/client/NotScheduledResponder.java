package coen445.client;

/**
 * Created by Ahmed on 15-12-02.
 */
public class NotScheduledResponder extends BaseResponder {

    @Override
    public void respond() {
        super.respond();

        System.out.println("this is the NotScheduledResponder respond method");
        System.out.println("updating local agenda");

    }
}
