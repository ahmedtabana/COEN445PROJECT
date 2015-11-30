package coen445.server;

/**
 * Created by Ahmed on 15-11-30.
 */
public class AcceptMessage extends UDPMessage {


    private int meetingNumber;

    public AcceptMessage(int meetingNumber){
        this.meetingNumber = meetingNumber;
    }
    @Override
    public void displayMessage() {
        super.displayMessage();
    }
}
