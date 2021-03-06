package Messages;

import Messages.DateTime;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Ahmed on 15-12-01.
 */
public class MeetingData implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private int meetingNumber;
    private InetAddress requester;
    private DateTime dateTime;
    private String topic;
    private int requestNumber;


    public MeetingData()
    {

    }

    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public InetAddress getRequester() {
        return requester;
    }

    public void setRequester(InetAddress requester) {
        this.requester = requester;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void displayMeetingData(){

        System.out.println("");
        System.out.println("Displaying Meeting Data:");
        System.out.println("Request Number: " + getRequestNumber());
        System.out.println("Meeting Number: " + getMeetingNumber());
        System.out.println("Requester: " + getRequester());
        System.out.println("Set of Requested Participants:");
        System.out.println("Topic:" + getTopic());
        System.out.println("Date-Time:" + getDateTime());
        System.out.println("");

    }
}

