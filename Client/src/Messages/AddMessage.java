package Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-12-05.
 */
public class AddMessage extends UDPMessage {



    private static final long serialVersionUID = 7526472295622776147L;


    private int meetingNumber;
    private InetAddress inetAddress;

    public AddMessage(){

        setType("Add");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(!meetingNumberReady(br));
    }

    public AddMessage(int meetingNumber, InetAddress address){
        this.meetingNumber = meetingNumber;
        this.inetAddress = address;
    }

    public int getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(int meetingNumber) {
        this.meetingNumber = meetingNumber;
    }


    private boolean meetingNumberReady(BufferedReader br) {

        try {
            System.out.println("Please enter the meeting number to Add to");
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


    @Override
    public void displayMessage() {
        super.displayMessage();
    }
}
