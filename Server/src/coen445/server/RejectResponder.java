package coen445.server;
import Messages.*;

import java.util.Set;

/**
 * Created by Ahmed on 15-12-01.
 */
public class RejectResponder extends BaseResponder {

    RejectMessage rejectMessage;

    @Override
    public void respond() {
        super.respond();
        System.out.println("Reject Responder respond method");

        rejectMessage = (RejectMessage) message;
        updateMeetingData();
    }


    private void updateMeetingData() {

        System.out.println("updating RBMS meeting data");

        //get meeting number
        int meetingNumber = rejectMessage.getMeetingNumber();

        //find its associated meeting data
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);

        //add the acceptor IP
        meetingData.addParticipantToSetOfRejected(IPAddress);


        //update map with new meetingData
        Server.meetingNumberToMeetingData.put(meetingNumber,meetingData);


        displayAllMeetingData();


    }

    private void displayAllMeetingData() {
        System.out.println("displaying updated meeting data");
        Set<Integer> mySet1 = Server.meetingNumberToMeetingData.keySet();

        for( int i : mySet1){
            MeetingData myData = Server.meetingNumberToMeetingData.get(i);
            myData.displayMeetingData();
        }
    }
}
