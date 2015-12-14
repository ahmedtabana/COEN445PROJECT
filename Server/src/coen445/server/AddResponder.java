package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

/**
 * Created by Ahmed on 15-12-05.
 */
public class AddResponder extends BaseResponder {

    AddMessage addMessage;

    @Override
    public void respond() {
        super.respond();
        System.out.println("Add responder respond method");

        addMessage = (AddMessage) message;

        // if the meeting is scheduled
        if(meetingIsScheduled()){

            // check if the participant is in the rejectedList
            if(participantIsInRejectedList()){

                removeParticipantFromRejectedList();
                addParticipantToAcceptedList();
                displayAllMeetingData();
                sendAddSuccessMessageToParticipant();
                sendConfirmMessageToParticipant();
                sendAddedMessageToMeetingOrganizer();
                // update here to save to file

            }
            // if the requester is not in the rejected list,
            // then he is not authorized to request to be added to this meeting
            else {

                sendUnauthorizedMessageToParticipant();
            }
        }
        // if the meeting is not scheduled
        else{

            System.out.println("Meeting is not scheduled");
//            sendCancelMessageToParticipant();
              sendUnauthorizedMessageToParticipant();
        }


    }

    private void sendAddSuccessMessageToParticipant() {
        System.out.println("sending Success message to participant");
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(addMessage.getMeetingNumber());

        UDPMessage successMessage = new SuccessMessage(meetingData);
        sendMessage(successMessage,IPAddress,port);
    }

    private boolean meetingIsScheduled() {

        // check in the server if we have a meeting number that is equal to the one we received from
        // the cancel message. If yes, return true, else return false;
//        return Server.meetingNumberToMeetingData.contains(addMessage.getMeetingNumber());
        return  Server.meetingNumberToMeetingData.containsKey(addMessage.getMeetingNumber());
    }

    private boolean participantIsInRejectedList() {

        //get the meeting data associated with this meeting number from the server
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(addMessage.getMeetingNumber());

        //check the meeting data
        //if the requester is in the rejected list for this meeting, return true
        // otherwise return false
//        return meetingData.getSetOfRejectedParticipants().contains(IPAddress);
        return meetingData.getSetOfRejectedParticipants().contains(IPAddress);

    }

    private void removeParticipantFromRejectedList() {

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(addMessage.getMeetingNumber());
        meetingData.removeParticipantFromRejectedParticipants(IPAddress);
    }

    private void addParticipantToAcceptedList() {
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(addMessage.getMeetingNumber());
        meetingData.addParticipantToSetOfAccepted(IPAddress);
    }

    private void sendConfirmMessageToParticipant() {

        System.out.println("sending Confirm message to participant");
        UDPMessage confirmMessage = new ConfirmMessage(addMessage.getMeetingNumber());
        sendMessage(confirmMessage,IPAddress,port);
    }

    private void sendAddedMessageToMeetingOrganizer() {

        // get the meeting data associated with this meeting number from server
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(addMessage.getMeetingNumber());

        // find the ip address and port for the organizer of this meeting
        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());

        // create an added message with this meeting number and ip address of participant
        // who is being added
        UDPMessage addedMessage = new AddedMessage(addMessage.getMeetingNumber(),IPAddress);

        // send the added message to the organizer of the meeting
        sendMessage(addedMessage,participantData.getIPAddress(),participantData.getPort());

    }


    private void sendUnauthorizedMessageToParticipant() {
        System.out.println("sending Unauthorized message to requester");
        UDPMessage unAuthorizedMessage = new UnauthorizedMessage(addMessage.getMeetingNumber());
        sendMessage(unAuthorizedMessage,IPAddress,port);

    }

    private void sendCancelMessageToParticipant() {
        System.out.println("Sending cancel message to Requester");
        UDPMessage cancelMessage = new CancelMessage(addMessage.getMeetingNumber());

        //IPAddress and port are inherted attributes from the base class UDPMessage
        //they represent the IP address and port number of where this add message came from, respectively
        sendMessage(cancelMessage,IPAddress,port);
    }






    // sendMessage function takes care of sending any type of  message in udp, it takes the udpMessage object to be sent
    // IP address of where this message will be sent and port number to be sent to.
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

    private void displayAllMeetingData() {
        System.out.println("displaying updated meeting data");
        Set<Integer> mySet1 = Server.meetingNumberToMeetingData.keySet();

        for( int i : mySet1){
            MeetingData myData = Server.meetingNumberToMeetingData.get(i);
            myData.displayMeetingData();
        }
    }
}
