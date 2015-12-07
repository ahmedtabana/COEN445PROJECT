package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Ahmed on 15-12-05.
 */
public class WithdrawResponder extends BaseResponder {

    WithdrawMessage withdrawMessage;
    @Override
    public void respond() {
        super.respond();
        withdrawMessage = (WithdrawMessage) message;
        withdrawMessage.displayMessage();

        if(meetingExists()){

            System.out.println("This meeting exists");

            if(requesterOfMeetingIsWithdrawing()){
            System.out.println("This user is the requester of meeting and cannot withdraw");
            sendUnauthorizedMessageToRequester();

            }
            else{
                 System.out.println("This user is not the requester of the meeting and can withdraw");
                 informRequesterOfWithdrawal();
                 removeWithdrawerFromAcceptedList();

                 if(numberOfAcceptancesIsBelowMinimum()){

                    sendInviteMessageToRejectedParticipants();
                    addWithdrawerToRejectedList();
                    waitForResponses();

                    if(numberOfAcceptancesIsBelowMinimum()){

                    sendCancelMessageToAllConfirmedParticipants();
                    sendCancelMessageToRequester();
                    removeTimeSlotFromReservation();
                    removeMappingFromMeetingNumberToMeetingData();
                    }
                    else{

                    sendConfirmMessageToNewlyAcceptedParticipants();
                    sendScheduledMessageToRequesterWithUpdatedListOfParticipants();
                    }


                 }

                 else{
                    //todo what to do here?
                    //do nothing?
                 }



            }
        }
        else{
            System.out.println("meeting does not exist");
            //todo inform client of this?
        }


    }

    private boolean meetingExists() {

        return Server.meetingNumberToMeetingData.containsKey(withdrawMessage.getMeetingNumber());
    }


    private boolean requesterOfMeetingIsWithdrawing() {
        int meetingNumber = withdrawMessage.getMeetingNumber();
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
//        return IPAddress == meetingData.getRequester();
        return  IPAddress.equals(meetingData.getRequester());

    }

    private void sendUnauthorizedMessageToRequester() {
        System.out.println("sending Unauthorized message to requester");
        UDPMessage unAuthorizedMessage = new UnauthorizedMessage(withdrawMessage.getMeetingNumber());
        unAuthorizedMessage.displayMessage();
        sendMessage(unAuthorizedMessage,IPAddress,port);

    }
    private void informRequesterOfWithdrawal() {


        MeetingData meetingData = Server.meetingNumberToMeetingData.get(withdrawMessage.getMeetingNumber());
        UDPMessage udpMessage = new WithdrawMessage(withdrawMessage.getMeetingNumber(),IPAddress);
        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());
        sendMessage(udpMessage, participantData.getIPAddress(),participantData.getPort());

    }

    private void removeWithdrawerFromAcceptedList(){

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(withdrawMessage.getMeetingNumber());
        meetingData.removeParticipantFromAcceptedParticipants(IPAddress);


    }

    private boolean numberOfAcceptancesIsBelowMinimum() {

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(withdrawMessage.getMeetingNumber());

        if( meetingData.getSetOfAcceptedParticipants().size() < meetingData.getMinimumNumberOfParticipants()){
            System.out.println("Number Of Acceptances Fell below Minimum Number Of Participants");
            return true;
        }
        else{
            System.out.println("Number Of Acceptances Is Still Above The Minimum Number Of Participants");
            return false;

        }
    }

    private void addWithdrawerToRejectedList(){

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(withdrawMessage.getMeetingNumber());
        meetingData.addParticipantToSetOfRejected(IPAddress);
    }

    private void sendInviteMessageToRejectedParticipants() {

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(withdrawMessage.getMeetingNumber());

        System.out.println("creating invite message ");
        UDPMessage inviteMessage = new InviteMessage(meetingData.getMeetingNumber(),meetingData.getDateTime(),meetingData.getTopic(),meetingData.getRequester());
        inviteMessage.displayMessage();


        //send invite message to only the rejected participants
        for(InetAddress i : meetingData.getSetOfRejectedParticipants()){

            ParticipantData data = Server.ipToData.get(i);

            sendMessage(inviteMessage,data.getIPAddress(),data.getPort());
        }

    }

    private void waitForResponses() {

        System.out.println("Some participants have not replied yet, waiting...");

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            System.out.println("error in Thread sleep");
            e.printStackTrace();
        }
        System.out.println("Wait period has expired");

    }

    private void sendCancelMessageToAllConfirmedParticipants() {

        System.out.println("Sending Cancel Message To Participants Who Accepted The Request");
        UDPMessage udpMessage = new CancelMessage(withdrawMessage.getMeetingNumber());

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(withdrawMessage.getMeetingNumber());
        CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants = meetingData.getSetOfAcceptedParticipants();

        for(InetAddress address : setOfAcceptedParticipants){

            ParticipantData participantData = Server.ipToData.get(address);

            sendMessage(udpMessage,address,participantData.getPort());
        }
    }

    private void sendCancelMessageToRequester() {

        System.out.println("Sending Cancel Message To Requester");
        int meetingNumber = withdrawMessage.getMeetingNumber();
        //todo what happens if a requester goes offline?
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
        UDPMessage udpMessage = new CancelMessage(meetingNumber);

        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());
        sendMessage(udpMessage,meetingData.getRequester(),participantData.getPort());

    }

    private void removeTimeSlotFromReservation() {
        System.out.println("Removing time slot from RBMS Room reservations");
        int meetingNumber = withdrawMessage.getMeetingNumber();
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);

        DateTime dateTime = meetingData.getDateTime();
        System.out.println("room reservation before remove");

        for(DateTime time : Server.roomReservationList){
            System.out.println(time);
        }
        if(Server.roomReservationList.contains(dateTime)){
            Server.roomReservationList.remove(dateTime);
        }
        System.out.println("room reservation after remove");

        for(DateTime time : Server.roomReservationList){
            System.out.println(time);
        }
    }



    private void removeMappingFromMeetingNumberToMeetingData() {
            System.out.println("removing mapping for meeting#:" + withdrawMessage.getMeetingNumber());
            Server.meetingNumberToMeetingData.remove(withdrawMessage.getMeetingNumber());
    }



    private void sendConfirmMessageToNewlyAcceptedParticipants() {
        //todo
        System.out.println("todo: send confirm message to newly accepted participants");

    }

    private void sendScheduledMessageToRequesterWithUpdatedListOfParticipants() {

        System.out.println("Sending Scheduled Message To Requester with updated participants list");
        int meetingNumber = withdrawMessage.getMeetingNumber();
        //todo what happens if a requester goes offline?
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
        UDPMessage udpMessage = new ScheduledMessage(meetingData.getRequestNumber(),meetingData.getMeetingNumber(),meetingData.getSetOfAcceptedParticipants());

        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());
        sendMessage(udpMessage,meetingData.getRequester(),participantData.getPort());

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
