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

    public ScheduledMessage(){
        setType("Scheduled");
    }
    @Override
    public void displayMessage() {
        super.displayMessage();

        System.out.println("Message type: " + getType());
        System.out.println("Request Number: " + requestNumber);
        System.out.println("Meeting Number: " + meetingNumber);
        System.out.println("List of Confirmed Participants");
        for(InetAddress address : setOfConfirmedParticipants){
            System.out.println(address);
        }
        System.out.println("");

    }
}
