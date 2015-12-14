package coen445.client;

import Messages.*;

import java.util.Set;

/**
 * Created by Ahmed on 15-12-02.
 */
public class CancelResponder extends BaseResponder {

    CancelMessage cancelMessage;
    @Override
    public void respond() {
        super.respond();

        System.out.println("this is the CancelResponder respond method");
        cancelMessage = (CancelMessage) message;

        removeTimeSlotFromLocalAgenda();
        removeMappingFromMeetingNumberToMeetingData();

    }

    private void removeMappingFromMeetingNumberToMeetingData() {
        System.out.println("removing mapping for meeting#:" + cancelMessage.getMeetingNumber());
        displayMeetingNumberToMeetingDataContents("Before removing");
        Client.meetingNumberToMeetingData.remove(cancelMessage.getMeetingNumber());
        displayMeetingNumberToMeetingDataContents("After removing");
    }

    private void displayMeetingNumberToMeetingDataContents(String when) {

        System.out.println("Displaying meetingNumberToMeetingData " + when);
        Set<Integer> mySet1 = Client.meetingNumberToMeetingData.keySet();

        for( int i : mySet1){
            MeetingData myData = Client.meetingNumberToMeetingData.get(i);
            myData.displayMeetingData();
        }
    }

    private void removeTimeSlotFromLocalAgenda() {
        System.out.println("Removing time slot from local agenda");
        int meetingNumber = cancelMessage.getMeetingNumber();
        MeetingData meetingData = Client.meetingNumberToMeetingData.get(meetingNumber);

        DateTime dateTime = meetingData.getDateTime();
        System.out.println("localAgenda before remove");
        System.out.println("");

        for(DateTime time : Client.localAgenda){
            System.out.println(time);
        }
        if(Client.localAgenda.contains(dateTime)){
            Client.localAgenda.remove(dateTime);
        }
        System.out.println("localAgenda after remove");
        System.out.println("");

        for(DateTime time : Client.localAgenda){
            System.out.println(time);
        }
        System.out.println("");
    }
}
