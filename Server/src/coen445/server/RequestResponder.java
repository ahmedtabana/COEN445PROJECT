package coen445.server;

import coen445.client.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

/**
 * Created by Ahmed on 15-11-24.
 */
public class RequestResponder extends BaseResponder {

    RequestMessage requestMessage;

    public RequestResponder() {
    }


    @Override
    public void respond() {

        System.out.println("this is the RequestResponder respond method");
        requestMessage = (RequestMessage) message;

        if (RoomIsUnavailable()) {

            UDPMessage unAvailableMessage = new UnavailableMessage(message.getRequestNumber());

            try {
                sendData = Server.getBytes(unAvailableMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            UDPMessage inviteMessage;
            System.out.println("creating invite message");
            inviteMessage = new InviteMessage(IPAddress,((RequestMessage) message).getDateTime(),((RequestMessage) message).getTopic());
            inviteMessage.displayMessage();


            MappingMeetingNumberToMeetingData((InviteMessage) inviteMessage);

            //send invite message to only the participants in this message
            for(InetAddress i : ((RequestMessage) message).getSetOfParticipants()){

                ParticipantData data = Server.ipToData.get(i);
                

                try {
                    sendData = Server.getBytes(inviteMessage);
                } catch (IOException e) {
                    System.out.println("error in RequestResponder getBytes");
                    e.printStackTrace();
                }
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getIPAddress(), data.getPort());
                try {
                    socket.send(sendPacket);
                } catch (IOException e) {
                    System.out.println("error in RequestResponder sendPacket");

                    e.printStackTrace();
                }

            }

        }
    }

    private void MappingMeetingNumberToMeetingData(InviteMessage inviteMessage) {


        System.out.println("Mapping meeting number to meeting data");
        MeetingData meetingData = new MeetingData();
        meetingData.setRequester(inviteMessage.getRequester());
        meetingData.setMinimumNumberOfParticipants(requestMessage.getMinimumNumberOfParticipants());
        meetingData.setMeetingNumber(inviteMessage.getMeetingNumber());
        meetingData.setSetOfRequestedParticipants(requestMessage.getSetOfParticipants());
        meetingData.displayMeetingData();

        Server.meetingNumberToMeetingData.put(inviteMessage.getMeetingNumber(),meetingData);


        System.out.println("displaying map contents");
        Set<Integer> mySet1 = Server.meetingNumberToMeetingData.keySet();

        for( int i : mySet1){
            MeetingData myData = Server.meetingNumberToMeetingData.get(i);
            myData.displayMeetingData();
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
}