package Messages;

import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Ahmed on 15-12-02.
 */
public class NotScheduledMessage extends UDPMessage{

    private static final long serialVersionUID = 7526472295622776147L;

    private int requestNumber;
    private DateTime dateTime;
    private int minimumNumberOfParticipants;
    private CopyOnWriteArraySet<InetAddress> setOfConfirmedParticipants;
    private String topic;


    public NotScheduledMessage(int requestNumber, DateTime dateTime, int minimumNumberOfParticipants, CopyOnWriteArraySet<InetAddress> setOfConfirmedParticipants, String topic){

        this.requestNumber = requestNumber;
        this.dateTime = dateTime;
        this.minimumNumberOfParticipants = minimumNumberOfParticipants;
        this.setOfConfirmedParticipants = setOfConfirmedParticipants;
        this.topic = topic;
        setType("NotScheduled");
    }
    @Override
    public void displayMessage() {

        super.displayMessage();

    }
}


