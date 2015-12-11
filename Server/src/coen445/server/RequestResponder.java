package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Ahmed on 15-11-24.
 */
public class RequestResponder extends BaseResponder {

    RequestMessage requestMessage;
    int meetingNumber;

    public RequestResponder() {
    }

    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }


    @Override
    public void respond() {

        System.out.println("this is the RequestResponder respond method");
        requestMessage = (RequestMessage) message;

        if (RoomIsUnavailable()) {

            UDPMessage unAvailableMessage = new UnavailableMessage(message.getRequestNumber());
            sendMessage(unAvailableMessage,IPAddress,port);

        } else {

            UDPMessage inviteMessage;
            System.out.println("creating invite message");
            inviteMessage = new InviteMessage(IPAddress,((RequestMessage) message).getDateTime(),((RequestMessage) message).getTopic());
            inviteMessage.displayMessage();
            setMeetingNumber(((InviteMessage) inviteMessage).getMeetingNumber());


            MappingMeetingNumberToMeetingData((InviteMessage) inviteMessage);
            addTimeSlotToReservation(((InviteMessage) inviteMessage).getMeetingNumber());
            //send invite message to only the participants in this message
            for(InetAddress i : ((RequestMessage) message).getSetOfParticipants()){

                ParticipantData data = Server.ipToData.get(i);
                
                sendMessage(inviteMessage,data.getIPAddress(),data.getPort());
            }


            // if not all participants have replied with either reject or accept, wait a specific time
            // current wait period is set to 5 sec
            if(!allParticipantsReplied()){
                waitForResponses();
            }
            else{
                System.out.println("Received responses from all participants...");
            }

            if(numberOfAcceptancesEqualOrHigherThanMinimumParticipants()){

                sendConfirmMessageToParticipantsWhoAccepted();
                sendScheduledMessageToRequester();

            }
            else{

                removeTimeSlotFromReservation();
                sendCancelMessageToParticipantsWhoAccepted();
                sendNotScheduledMessageToRequester();
                //remove mapping?
                //removeMappingFromMeetingNumberToMeetingData();
            }
        }
    }


    private boolean RoomIsUnavailable() {

        for (DateTime dateTime : Server.roomReservationList) {

            boolean conflictFound = dateTime.getDay() == requestMessage.getDay() && dateTime.getMonth() == requestMessage.getMonth() &&
                    dateTime.getYear() == requestMessage.getYear() && dateTime.getTime() == requestMessage.getTime();
            if (conflictFound) {
                System.out.println("conflict found");
                return true;
            }
        }

        System.out.println("no conflict found");

        return false;

    }


    private void MappingMeetingNumberToMeetingData(InviteMessage inviteMessage) {


        System.out.println("Mapping meeting number to meeting data");
        MeetingData meetingData = new MeetingData();
        meetingData.setRequestNumber(requestMessage.getRequestNumber());
        meetingData.setTopic(requestMessage.getTopic());
        meetingData.setDateTime(requestMessage.getDateTime());
        meetingData.setRequester(inviteMessage.getRequester());
        meetingData.setMinimumNumberOfParticipants(requestMessage.getMinimumNumberOfParticipants());
        meetingData.setMeetingNumber(inviteMessage.getMeetingNumber());
        meetingData.setSetOfRequestedParticipants(requestMessage.getSetOfParticipants());
//        meetingData.displayMeetingData();

        Server.meetingNumberToMeetingData.put(inviteMessage.getMeetingNumber(),meetingData);


//        System.out.println("displaying map contents");
//        Set<Integer> mySet1 = Server.meetingNumberToMeetingData.keySet();
//
//        for( int i : mySet1){
//            MeetingData myData = Server.meetingNumberToMeetingData.get(i);
//            myData.displayMeetingData();
//        }

    }

    private void addTimeSlotToReservation(int meetingNumber) {

        System.out.println("adding time slot from RBMS Room reservations");

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);

        DateTime dateTime = meetingData.getDateTime();
        System.out.println("room reservation before add");

        for(DateTime time : Server.roomReservationList){
            System.out.println(time);
        }
        if(!Server.roomReservationList.contains(dateTime)){
            Server.roomReservationList.add(dateTime);
        }
        System.out.println("room reservation after add");

        for(DateTime time : Server.roomReservationList){
            System.out.println(time);
        }
    }

    private boolean allParticipantsReplied() {

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(getMeetingNumber());

        return (meetingData.getNumberOfAcceptedParticipants() + meetingData.getNumberOfRejectedParticipants()) == meetingData.getNumberOfRequestedParticipants();
    }

    private void waitForResponses() {

        System.out.println("Some participants have not replied yet, waiting...");

        try {
            Thread.sleep(Server.WAIT_TIME_MILLIS);
        } catch (InterruptedException e) {
            System.out.println("error in Thread sleep");
            e.printStackTrace();
        }
        System.out.println("Wait period has expired");
    }


    private boolean numberOfAcceptancesEqualOrHigherThanMinimumParticipants() {


        MeetingData meetingData = Server.meetingNumberToMeetingData.get(getMeetingNumber());

        if( meetingData.getSetOfAcceptedParticipants().size() >= meetingData.getMinimumNumberOfParticipants()){
            System.out.println("Number Of Acceptances Equal Or Higher Than Minimum Number Of Participants");
            return true;
        }
        else{
            System.out.println("Number Of Acceptances Less Than Minimum Number Of Participants");
            return false;

        }
    }


    private void sendConfirmMessageToParticipantsWhoAccepted() {

        System.out.println("Sending Confirm Message To Participants Who Accepted The Request");
        UDPMessage udpMessage = new ConfirmMessage(getMeetingNumber());

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(getMeetingNumber());
        CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants = meetingData.getSetOfAcceptedParticipants();

        for(InetAddress address : setOfAcceptedParticipants){

            ParticipantData participantData = Server.ipToData.get(address);

            sendMessage(udpMessage,address,participantData.getPort());
        }

    }



    private void sendScheduledMessageToRequester() {

        System.out.println("Sending Scheduled Message To Requester");
        int meetingNumber = getMeetingNumber();
        //todo what happens if a requester goes offline?
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
        UDPMessage udpMessage = new ScheduledMessage(meetingData.getRequestNumber(),meetingData.getMeetingNumber(),meetingData.getSetOfAcceptedParticipants());

        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());
        sendMessage(udpMessage,meetingData.getRequester(),participantData.getPort());

    }


    private void removeTimeSlotFromReservation() {

        System.out.println("Removing time slot from RBMS Room reservations");
        int meetingNumber = getMeetingNumber();
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

    private void sendCancelMessageToParticipantsWhoAccepted() {

        System.out.println("Sending Cancel Message To Participants Who Accepted The Request");
        UDPMessage udpMessage = new CancelMessage(getMeetingNumber());

        MeetingData meetingData = Server.meetingNumberToMeetingData.get(getMeetingNumber());
        CopyOnWriteArraySet<InetAddress> setOfAcceptedParticipants = meetingData.getSetOfAcceptedParticipants();

        for(InetAddress address : setOfAcceptedParticipants){

            ParticipantData participantData = Server.ipToData.get(address);

            sendMessage(udpMessage,address,participantData.getPort());
        }


    }

    private void sendNotScheduledMessageToRequester() {

        System.out.println("Sending Not-Scheduled Message To Requester");
        int meetingNumber = getMeetingNumber();
        //todo what happens if a requester goes offline?
        MeetingData meetingData = Server.meetingNumberToMeetingData.get(meetingNumber);
        UDPMessage udpMessage = new NotScheduledMessage(meetingData.getRequestNumber(),meetingData.getDateTime(),meetingData.getMinimumNumberOfParticipants(),meetingData.getSetOfAcceptedParticipants(),meetingData.getTopic());
        ParticipantData participantData = Server.ipToData.get(meetingData.getRequester());

        sendMessage(udpMessage,meetingData.getRequester(),participantData.getPort());

    }

    private void removeMappingFromMeetingNumberToMeetingData() {
        System.out.println("removing mapping for meeting#:" + getMeetingNumber());
        Server.meetingNumberToMeetingData.remove(getMeetingNumber());
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