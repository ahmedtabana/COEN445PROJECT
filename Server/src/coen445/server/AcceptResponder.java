package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;
import Messages.*;

import java.util.concurrent.CopyOnWriteArraySet;

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

        // if not all participants have replied with either reject or accept, wait a specific time
        // current wait period is set to 5 sec
        if(!allParticipantsReplied()){
            System.out.println("Some participants have not replied yet, waiting...");
            waitForResponses();
            System.out.println("Wait period has expired");

        }
        else{
            System.out.println("Received responses from all participants...");
        }

        if(numberOfAcceptancesEqualOrHigherThanMinimumParticipants()){

            sendConfirmMessageToParticipantsWhoAccepted();
            sendScheduledMessageToRequester();
            //todo book the room for this time
            System.out.println("updating Room reservations");
        }
        else{
            sendCancelMessageToParticipantsWhoAccepted();
            sendNotScheduledMessageToRequester();
        }
    }

    private void waitForResponses() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("error in Thread sleep");
            e.printStackTrace();
        }
    }

    private boolean allParticipantsReplied() {

        int meetingNumber =  acceptMessage.getMeetingNumber();
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);

        return (meetingData.getNumberOfAcceptedParticipants() + meetingData.getNumberOfRejectedParticipants()) == meetingData.getNumberOfRequestedParticipants();
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


    private void sendScheduledMessageToRequester() {

        //todo update RBMS room timeslots
        System.out.println("Sending Scheduled Message To Requester");
        int meetingNumber = acceptMessage.getMeetingNumber();
        //todo what happens if a requester goes offline?
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
        UDPMessage udpMessage = new ScheduledMessage(meetingData.getRequestNumber(),meetingData.getMeetingNumber(),meetingData.getSetOfAcceptedParticipants());

        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());
        sendMessage(udpMessage,meetingData.getRequester(),participantData.getPort());

    }



    private void sendConfirmMessageToParticipantsWhoAccepted() {

        System.out.println("Sending Confirm Message To Participants Who Accepted The Request");
        UDPMessage udpMessage = new ConfirmMessage(acceptMessage.getMeetingNumber());

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(acceptMessage.getMeetingNumber());
        CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants = meetingData.getSetOfAcceptedParticipants();

        for(InetAddress address : setOfAcceptedParticipants){

            ParticipantData participantData = Server.ipToData.get(address);

            sendMessage(udpMessage,address,participantData.getPort());
        }

    }

    private void sendNotScheduledMessageToRequester() {

        System.out.println("Sending Not-Scheduled Message To Requester");
        int meetingNumber = acceptMessage.getMeetingNumber();
        //todo what happens if a requester goes offline?
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
        UDPMessage udpMessage = new NotScheduledMessage(meetingData.getRequestNumber(),meetingData.getDateTime(),meetingData.getMinimumNumberOfParticipants(),meetingData.getSetOfAcceptedParticipants(),meetingData.getTopic());
        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());

        sendMessage(udpMessage,meetingData.getRequester(),participantData.getPort());

    }

    private void sendCancelMessageToParticipantsWhoAccepted() {

        System.out.println("Sending Cancel Message To Participants Who Accepted The Request");
        UDPMessage udpMessage = new CancelMessage(acceptMessage.getMeetingNumber());

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(acceptMessage.getMeetingNumber());
        CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants = meetingData.getSetOfAcceptedParticipants();

        for(InetAddress address : setOfAcceptedParticipants){

            ParticipantData participantData = Server.ipToData.get(address);

            sendMessage(udpMessage,address,participantData.getPort());
        }


    }

    private void sendMessage(UDPMessage udpMessage, InetAddress address, int port) {
        try {
            sendData = Server.getBytes(udpMessage);
        } catch (IOException e) {
            System.out.println("error in getBytes");
            e.printStackTrace();
        }
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,address ,port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            System.out.println("error in sendPacket");

            e.printStackTrace();
        }
    }
}
