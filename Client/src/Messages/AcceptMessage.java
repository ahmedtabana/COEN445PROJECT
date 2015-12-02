package Messages;


/**
 * Created by Ahmed on 15-11-30.
 */
public class AcceptMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;

    private int meetingNumber;

    public AcceptMessage(int meetingNumber){

        this.meetingNumber = meetingNumber;
        setType("Accept");
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
