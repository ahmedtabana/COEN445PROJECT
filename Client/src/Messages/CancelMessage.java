package Messages;

import coen445.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Ahmed on 15-12-02.
 */
public class CancelMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;




    private int meetingNumber;

    public CancelMessage(){
        setType("Cancel");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(!meetingNumberReady(br));
    }

    private boolean meetingNumberReady(BufferedReader br) {

        try {
            System.out.println("Please enter the meeting number to cancel");
            setMeetingNumber(Integer.parseInt(br.readLine()));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        catch (NumberFormatException e) {
            System.out.println("Not an Integer");
            return false;
        }
        return true;
    }

    public CancelMessage(int meetingNumber){

        this.meetingNumber = meetingNumber;
        setType("Cancel");

    }


    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Meeting Number: " + meetingNumber);

    }
}
