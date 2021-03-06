package Messages;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;
import Messages.*;
/**
 * Created by Ahmed on 15-12-01.
 */
public class MeetingData implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private int meetingNumber;
    private int minimumNumberOfParticipants;
    private InetAddress requester;
    private CopyOnWriteArraySet<InetAddress> setOfRequestedParticipants;
    private CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants;
    private CopyOnWriteArraySet<InetAddress> setOfRejectedParticipants;
    private DateTime dateTime;
    private String topic;
    private int requestNumber;


    public MeetingData()
    {
        setOfRequestedParticipants = new CopyOnWriteArraySet<InetAddress>();
        setOfAcceptedParticipants = new CopyOnWriteArraySet<InetAddress>();
        setOfRejectedParticipants = new CopyOnWriteArraySet<InetAddress>();
    }

    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public int getMinimumNumberOfParticipants() {
        return minimumNumberOfParticipants;
    }

    public void setMinimumNumberOfParticipants(int minimumNumberOfParticipants) {
        this.minimumNumberOfParticipants = minimumNumberOfParticipants;
    }

    public InetAddress getRequester() {
        return requester;
    }

    public void setRequester(InetAddress requester) {
        this.requester = requester;
    }

    public CopyOnWriteArraySet<InetAddress> getSetOfRequestedParticipants() {
        return setOfRequestedParticipants;
    }

    public void setSetOfRequestedParticipants(CopyOnWriteArraySet<InetAddress> setOfRequestedParticipants) {
        this.setOfRequestedParticipants = setOfRequestedParticipants;
    }

    public CopyOnWriteArraySet<InetAddress> getSetOfAcceptedParticipants() {
        return setOfAcceptedParticipants;
    }

    public void setSetOfAcceptedParticipants(CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants) {
        this.setOfAcceptedParticipants = setOfAcceptedParticipants;
    }


    public CopyOnWriteArraySet<InetAddress> getSetOfRejectedParticipants() {
        return setOfRejectedParticipants;
    }

    public void setSetOfRejectedParticipants(CopyOnWriteArraySet<InetAddress> setOfRejectedParticipants) {
        this.setOfRejectedParticipants = setOfRejectedParticipants;
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
        System.out.println("Minimum number of participants: " + getMinimumNumberOfParticipants());
        System.out.println("Requester: " + getRequester());
        System.out.println("Set of Requested Participants:");
        for(InetAddress address : getSetOfRequestedParticipants()){
            System.out.println(address);
        }
        System.out.println("Set of Accepted Participants:");
        for(InetAddress address : getSetOfAcceptedParticipants()){
            System.out.println(address);
        }

        System.out.println("Set of Rejected Participants:");
        for(InetAddress address : getSetOfRejectedParticipants()){
            System.out.println(address);
        }

        System.out.println("Topic:" + getTopic());
        System.out.println("Date-Time:" + getDateTime());
        System.out.println("");


    }

    public void addParticipantToSetOfAccepted(InetAddress ipAddress) {

       if(!setOfAcceptedParticipants.contains(ipAddress)){
           setOfAcceptedParticipants.add(ipAddress);
       }
    }


    public void addParticipantToSetOfRejected(InetAddress ipAddress) {
        if(!setOfRejectedParticipants.contains(ipAddress)){
            setOfRejectedParticipants.add(ipAddress);

        }
    }

    public int getNumberOfAcceptedParticipants(){

        if(setOfAcceptedParticipants.isEmpty()){
            return 0;
        } else{

            return setOfAcceptedParticipants.size();
        }
    }

    public int getNumberOfRequestedParticipants(){

        if(setOfRequestedParticipants.isEmpty()){
            return 0;
        } else{

            return setOfRequestedParticipants.size();
        }
    }


    public int getNumberOfRejectedParticipants(){

        if(setOfRejectedParticipants.isEmpty()){
            return 0;
        } else{

            return setOfRejectedParticipants.size();
        }
    }

    public void removeParticipantFromAcceptedParticipants(InetAddress address){

        if(setOfAcceptedParticipants.remove(address)){
            System.out.println("removed from set of accepted participants");
        }
    }

    public void removeParticipantFromRejectedParticipants(InetAddress address){

        if(setOfRejectedParticipants.remove(address)){
            System.out.println("removed from set of rejected participants");
        }
    }
}

