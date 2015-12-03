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
    private CopyOnWriteArraySet<InetAddress> setOfParticipants;
    private int minimumNumberOfParticipants;
    private String topic;


    public NotScheduledMessage(){
        setType("NotScheduled");
    }
    @Override
    public void displayMessage() {
        super.displayMessage();
    }
}
