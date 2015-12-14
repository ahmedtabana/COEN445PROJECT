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
            DateTime dateTime = successMessage.getDateTime();

            System.out.println(dateTime);
        System.out.println("");
        System.out.println(dateTime.getDay());

            System.out.println("Local Agenda before add");

            for(DateTime time : Client.localAgenda){
                System.out.println(time.getDay());
                System.out.println(time.getMonth());
                System.out.println(time.getYear());
                System.out.println(time.getTime());
            }
                Client.localAgenda.add(dateTime);

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
