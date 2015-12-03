package Messages;

/**
 * Created by Ahmed on 15-12-02.
 */
public class CancelMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;


    private int meetingNumber;

    public CancelMessage(int meetingNumber){

        this.meetingNumber = meetingNumber;
        setType("Cancel");

    }
    @Override
    public void displayMessage() {
        super.displayMessage();

    }
}
