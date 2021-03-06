package coen445.server;

import java.util.Set;
import Messages.*;

/**
 * Created by Ahmed on 15-12-01.
 */
public class AcceptResponder extends BaseResponder {

    AcceptMessage acceptMessage;

    @Override
    public void respond() {
        super.respond();
        System.out.println("Accept responder respond method");
        acceptMessage = (AcceptMessage) message;
        updateMeetingData();


    }
// todo


    private void updateMeetingData() {

        System.out.println("updating RBMS meeting data");

        //get meeting number
        int meetingNumber = acceptMessage.getMeetingNumber();

        //find its associated meeting data
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);

        //add the acceptor IP
        meetingData.addParticipantToSetOfAccepted(IPAddress);

        // if the acceptor was previously on the reject list, remove him from that list
        if(meetingData.getSetOfRejectedParticipants().contains(IPAddress)){
            meetingData.getSetOfRejectedParticipants().remove(IPAddress);
        }

        //update map with new meetingData
        Server.meetingNumberToMeetingData.put(meetingNumber,meetingData);


        System.out.println("displaying updated meeting data");
        Set<Integer> mySet1 = Server.meetingNumberToMeetingData.keySet();

        for( int i : mySet1){
            MeetingData myData = Server.meetingNumberToMeetingData.get(i);
            myData.displayMeetingData();
        }


    }

}
