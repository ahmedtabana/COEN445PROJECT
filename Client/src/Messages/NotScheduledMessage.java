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


    public NotScheduledMessage(){
        setType("NotScheduled");
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public void displayMessage() {

        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Request Number: " + requestNumber);
        System.out.println("Day: " + dateTime.getDay());
        System.out.println("Month: " + dateTime.getMonth());
        System.out.println("Year: " + dateTime.getYear());
        System.out.println("Time: " + dateTime.getTime());
        System.out.println("Minimum: " + minimumNumberOfParticipants);
        System.out.println("List of Confirmed Participants");
        for(InetAddress address : setOfConfirmedParticipants){
            System.out.println(address);
        }
        System.out.println("Topic: " + topic);

    }


}
