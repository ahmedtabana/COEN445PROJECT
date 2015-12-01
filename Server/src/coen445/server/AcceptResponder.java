package coen445.server;

import java.util.Set;

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

        // todo also check that everybody has replied
        if(numberOfAcceptancesEqualOrHigherThanMinimumParticipants()){

            sendConfirmMessageToParticipantsWhoAccepted();
            sendScheduledMessageToRequester();
        }
        else{

            System.out.println("sending Cancel message");


        }


    }

    private void updateMeetingData() {

        System.out.println("updating RBMS meeting data");

        //get meeting number
        int meetingNumber = acceptMessage.getMeetingNumber();

        //find its associated meeting data
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);

        //add the acceptor IP
        meetingData.addParticipantToSetOfAccepted(IPAddress);


        //update map with new meetingData
        Server.meetingNumberToMeetingData.put(meetingNumber,meetingData);


        System.out.println("displaying updated meeting data");
        Set<Integer> mySet1 = Server.meetingNumberToMeetingData.keySet();

        for( int i : mySet1){
            MeetingData myData = Server.meetingNumberToMeetingData.get(i);
            myData.displayMeetingData();
        }


    }

    private void sendScheduledMessageToRequester() {
        System.out.println("Sending Scheduled Message To Requester");
    }

    private void sendConfirmMessageToParticipantsWhoAccepted() {

        System.out.println("Sending Confirm Message To Participants Who Accepted The Request");

    }

    private boolean numberOfAcceptancesEqualOrHigherThanMinimumParticipants() {


        MeetingData meetingData = Server.meetingNumberToMeetingData.get(acceptMessage.getMeetingNumber());

        if( meetingData.getSetOfAcceptedParticipants().size() >= meetingData.getMinimumNumberOfParticipants()){
            System.out.println("Number Of Acceptances Equal Or Higher Than Minimum Number Of Participants");
            return true;
        }
        else{
            System.out.println("Number Of Acceptances Less Than Minimum Number Of Participants");
            return false;

        }
    }
}
