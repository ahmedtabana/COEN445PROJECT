package coen445.client;

import Messages.*;

import java.util.Set;

/**
 * Created by Ahmed on 15-12-02.
 */
public class NotScheduledResponder extends BaseResponder {

    NotScheduledMessage notScheduledMessage;
    @Override
    public void respond() {
        super.respond();
        System.out.println("this is the NotScheduledResponder respond method");
        notScheduledMessage = (NotScheduledMessage) message;
        notScheduledMessage.displayMessage();

//        removeTimeSlotFromLocalAgenda();
//        removeMappingFromMeetingNumberToMeetingData();
    }

//    private void removeMappingFromMeetingNumberToMeetingData() {
//
//        Set<Integer> setOfMeetingNumbers = Client.meetingNumberToMeetingData.keySet();
//        MeetingData meetingData;
//        DateTime dateTime;
//        for( int meetingNumber : setOfMeetingNumbers){
//            meetingData = Client.meetingNumberToMeetingData.get(meetingNumber);
//            dateTime = meetingData.getDateTime();
//            if (dateTime.equals(notScheduledMessage.getDateTime())){
//
//            }
//        }
//
//    }
//
//    private void removeTimeSlotFromLocalAgenda() {
//        System.out.println("Freeing up time slot in local agenda");
//        System.out.println("Removing time slot from local agenda");
//
//
//        DateTime dateTime = notScheduledMessage.getDateTime();
//        System.out.println("localAgenda before remove");
//
//        for(DateTime time : Client.localAgenda){
//            System.out.println(time);
//        }
//        if(Client.localAgenda.contains(dateTime)){
//            Client.localAgenda.remove(dateTime);
//        }
//        System.out.println("localAgenda after remove");
//
//        for(DateTime time : Client.localAgenda){
//            System.out.println(time);
//        }
//    }
}
