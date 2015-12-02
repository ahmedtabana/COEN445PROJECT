package Messages;

import java.net.InetAddress;

/**
 * Created by Ahmed on 15-11-29.
 */
public class InviteMessage extends UDPMessage {

    private static int counter = 0;
    private static final long serialVersionUID = 7526472295622776147L;

    private InetAddress requester;
    private int meetingNumber;
    private DateTime dateTime;
    private String topic;

    public InviteMessage(InetAddress requester, DateTime dateTime, String topic){


        this.requester = requester;
        this.dateTime = dateTime;
        this.topic = topic;
        setType("Invite");
        counter++;
        setMeetingNumber(counter);
    }

    public InetAddress getRequester() {
        return requester;
    }

    public void setRequester(InetAddress requester) {
        this.requester = requester;
    }

    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDay(){
        return dateTime.getDay();
    }
    public int getMonth(){
        return dateTime.getMonth();
    }
    public int getYear(){
        return dateTime.getYear();
    }
    public int getTime(){
        return dateTime.getTime();
    }

    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Meeting Number: " + getMeetingNumber());
        System.out.println("Day: " + dateTime.getDay());
        System.out.println("Month: " + dateTime.getMonth());
        System.out.println("Year: " + dateTime.getYear());
        System.out.println("Time: " + dateTime.getTime());
        System.out.println("Topic: " + getTopic());
        System.out.println("Requester: " + getRequester());
    }
}
