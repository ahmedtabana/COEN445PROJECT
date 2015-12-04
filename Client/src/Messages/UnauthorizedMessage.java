package Messages;

/**
 * Created by Ahmed on 15-12-03.
 */
public class UnauthorizedMessage extends  UDPMessage{

    private static final long serialVersionUID = 7526472295622776147L;
    private int meetingNumber;

    public UnauthorizedMessage(int meetingNumber){
        setType("Unauthorized");
        this.meetingNumber = meetingNumber;
    }

    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Meeting #" + meetingNumber);
        System.out.println("Unauthorized to perform this action");
    }
}
