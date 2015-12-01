package coen445.server;

/**
 * Created by Ahmed on 15-11-30.
 */
public class RejectMessage extends UDPMessage {

    private int meetingNumber;

    public RejectMessage(int meetingNumber){

        this.meetingNumber = meetingNumber;
        setType("Reject");
    }

    @Override
    public void displayMessage() {

        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Meeting Number: " + meetingNumber);
    }
}
