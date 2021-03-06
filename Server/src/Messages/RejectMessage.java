package Messages;

/**
 * Created by Ahmed on 15-11-30.
 */
public class RejectMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;
    private int meetingNumber;

    public RejectMessage(int meetingNumber){

        this.meetingNumber = meetingNumber;
        setType("Reject");
    }

    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    @Override
    public void displayMessage() {

        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Meeting Number: " + meetingNumber);
    }
}
