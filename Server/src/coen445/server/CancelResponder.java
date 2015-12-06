package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-12-03.
 */
public class CancelResponder extends BaseResponder {

    CancelMessage cancelMessage;

    @Override
    public void respond() {
        super.respond();


        cancelMessage = (CancelMessage) message;
        System.out.println("Cancel responder respond method");

        int meetingNumber = cancelMessage.getMeetingNumber();


        if(requesterOfMeetingIsCanceling()){
            System.out.println("This user is the requester and has permission for cancellation");
            sendCancelMessageToAllConfirmedParticipants();
            removeTimeSlotFromReservation();
            removeMappingFromMeetingNumberToMeetingData();
        }
        else{

            sendUnauthorizedMessageToRequester();
        }


    }

//    private boolean meetingIsConfirmed() {
//
//        return false;
//    }

    private void sendUnauthorizedMessageToRequester() {
        System.out.println("sending Unauthorized message to requester");
        UDPMessage unAuthorizedMessage = new UnauthorizedMessage(cancelMessage.getMeetingNumber());
        sendMessage(unAuthorizedMessage,IPAddress,port);

    }

    private void sendCancelMessageToAllConfirmedParticipants() {

        System.out.println("Sending cancel message to all confirmed participants");
        int meetingNumber = cancelMessage.getMeetingNumber();
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);

        for(InetAddress address : meetingData.getSetOfAcceptedParticipants()){

            ParticipantData data = Server.ipToData.get(address);

            UDPMessage udpMessage = new CancelMessage(meetingNumber);
            sendMessage(udpMessage,data.getIPAddress(),data.getPort());

        }

    }

    private boolean requesterOfMeetingIsCanceling() {
        int meetingNumber = cancelMessage.getMeetingNumber();
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
        return IPAddress == meetingData.getRequester();

    }

    private void removeTimeSlotFromReservation() {

        System.out.println("Removing time slot from RBMS Room reservations");
        int meetingNumber = cancelMessage.getMeetingNumber();
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
        System.out.println("removing mapping for meeting#:" + cancelMessage.getMeetingNumber());
        Server.meetingNumberToMeetingData.remove(cancelMessage.getMeetingNumber());
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
