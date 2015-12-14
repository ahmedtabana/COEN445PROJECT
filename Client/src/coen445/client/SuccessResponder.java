package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-13.
 */
public class SuccessResponder extends BaseResponder {

    SuccessMessage successMessage;
    MeetingData meetingData;
    @Override
    public void respond() {
        super.respond();
        successMessage = (SuccessMessage) message;
        meetingData = successMessage.getMeetingData();
        addTimeSlotToLocalAgenda();
        addMappingFromMeetingNumberToMeetingData();
    }



    private void addTimeSlotToLocalAgenda() {

            System.out.println("Adding time slot to local Agenda");
            System.out.println("");
            System.out.println("displaying received time slot");
            DateTime dateTime = meetingData.getDateTime();
            System.out.println(dateTime.toString());


            System.out.println("Local Agenda before add");

            for(DateTime time : Client.localAgenda){
                System.out.println(time.toString());

            }
                Client.localAgenda.add(dateTime);

            System.out.println("");
            System.out.println("Local Agenda after add");

            for(DateTime time : Client.localAgenda){
                System.out.println(time.toString());

            }

        }

    private void addMappingFromMeetingNumberToMeetingData() {

        System.out.println("adding mapping from meeting number to meeting data");

        Client.meetingNumberToMeetingData.put(meetingData.getMeetingNumber(),meetingData);



    }


}
