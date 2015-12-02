package Messages;

/**
 * Created by Ahmed on 15-11-30.
 */
public class RejectMessage extends UDPMessage {

    private int meetingNumber;
    private static final long serialVersionUID = 7526472295622776147L;

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
