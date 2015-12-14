package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-13.
 */
public class SuccessResponder extends BaseResponder {

    SuccessMessage successMessage;

    @Override
    public void respond() {
        super.respond();
        successMessage = (SuccessMessage) message;
        addTimeSlotToLocalAgenda();
    }



    private void addTimeSlotToLocalAgenda() {

            System.out.println("Adding time slot to local Agenda");
            System.out.println("");
            System.out.println("displaying received time slot");
            System.out.println(successMessage.getDateTime());

            System.out.println("Local Agenda before add");

            for(DateTime time : Client.localAgenda){
                System.out.println(time.getDay());
                System.out.println(time.getMonth());
                System.out.println(time.getYear());
                System.out.println(time.getTime());
            }
            DateTime dateTime = successMessage.getDateTime();
            if(!Client.localAgenda.contains(dateTime)){
                Client.localAgenda.add(dateTime);
            }
            System.out.println("");
            System.out.println("Local Agenda after add");

            for(DateTime time : Client.localAgenda){
                System.out.println(time.getDay());
                System.out.println(time.getMonth());
                System.out.println(time.getYear());
                System.out.println(time.getTime());
            }

        }

}
