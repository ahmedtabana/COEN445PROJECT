package coen445.server;

import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Ahmed on 15-12-01.
 */
public class MeetingData {


    private int meetingNumber;
    private int minimumNumberOfParticipants;
    private InetAddress requester;
    private CopyOnWriteArraySet<InetAddress> setOfRequestedParticipants;
    private CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants;

    public MeetingData()
    {
        setOfRequestedParticipants = new CopyOnWriteArraySet<InetAddress>();
        setOfAcceptedParticipants = new CopyOnWriteArraySet<InetAddress>();
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

    public void displayMeetingData(){

        System.out.println("");
        System.out.println("Dispalying Meeting Data:");
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
        System.out.println("");


    }

    public void addParticipantToSetOfAccepted(InetAddress ipAddress) {
        setOfAcceptedParticipants.add(ipAddress);
    }
}
