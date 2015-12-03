package Messages;

import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Ahmed on 15-12-02.
 */
public class ScheduledMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;


    private int requestNumber;
    private int meetingNumber;
    private CopyOnWriteArraySet<InetAddress> setOfConfirmedParticipants;

    public ScheduledMessage(int requestNumber, int meetingNumber, CopyOnWriteArraySet<InetAddress> setOfConfirmedParticipants){

        setType("Scheduled");
        this.requestNumber = requestNumber;
        this.meetingNumber = meetingNumber;
        this.setOfConfirmedParticipants = setOfConfirmedParticipants;
    }
    @Override
    public void displayMessage() {
        super.displayMessage();

    }
}
