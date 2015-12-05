package coen445.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import Messages.*;

/**
 * Created by Ahmed on 15-11-30.
 */
public class InviteResponder extends BaseResponder {

    InviteMessage inviteMessage;

    @Override
    public void respond() {
        super.respond();

        System.out.println("Invite responder respond method");
        inviteMessage = (InviteMessage) message;
        inviteMessage.displayMessage();

        if (!clientIsAvailable() || !clientHasAccepted()) {

            System.out.println("Sending Reject Message");
            UDPMessage rejectMessage = new RejectMessage(inviteMessage.getMeetingNumber());
            sendMessage(rejectMessage);

        } else {


            addTimeSlotToLocalAgenda();
            addMappingFromMeetingNumberToMeetingData();
            System.out.println("Sending Accept Message");
            UDPMessage acceptMessage = new AcceptMessage(inviteMessage.getMeetingNumber());
            sendMessage(acceptMessage);
        }
    }

    private boolean clientHasAccepted() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Accept / Cancel ?");
            String result = br.readLine();
            if(result.equals("Accept")) {
                System.out.println("accepting");
                return true;

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("rejecting");
            return false;
        }
        System.out.println("rejecting");
        return false;
    }

    private void sendMessage(UDPMessage sendMessage) {
        try {
            sendData = Client.getBytes(sendMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTimeSlotToLocalAgenda() {
        System.out.println("Adding time slot to local Agenda");
        System.out.println("Local Agenda before add");

        for(DateTime time : Client.localAgenda){
            System.out.println(time);
        }
        DateTime dateTime = inviteMessage.getDateTime();
        if(!Client.localAgenda.contains(dateTime)){
            Client.localAgenda.add(dateTime);
        }
        System.out.println("Local Agenda after add");

        for(DateTime time : Client.localAgenda){
            System.out.println(time);
        }

    }

    private void addMappingFromMeetingNumberToMeetingData() {

        System.out.println("adding mapping from meeting number to meeting data");
        MeetingData meetingData = new MeetingData();
        meetingData.setDateTime(inviteMessage.getDateTime());
        meetingData.setMeetingNumber(inviteMessage.getMeetingNumber());
        meetingData.setTopic(inviteMessage.getTopic());
        meetingData.setRequester(inviteMessage.getRequester());
        Client.meetingNumberToMeetingData.put(inviteMessage.getMeetingNumber(),meetingData);

    }

    private boolean clientIsAvailable() {

        System.out.println("Checking the local MS agenda");

        for (DateTime dateTime : Client.localAgenda) {

            boolean conflictFound = dateTime.getDay() == inviteMessage.getDay() && dateTime.getMonth() == inviteMessage.getMonth() &&
                    dateTime.getYear() == inviteMessage.getYear() && dateTime.getTime() == inviteMessage.getTime();
            if (conflictFound) {
                System.out.println("Time slot is not available");
                return false;
            }
        }

        System.out.println("Time slot is Available");


        return true;
    }
}

