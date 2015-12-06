package Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-12-05.
 */
public class WithdrawMessage extends  UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;
    private int meetingNumber;
    private InetAddress inetAddress;

    public WithdrawMessage(){

        setType("Withdraw");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    }

    public WithdrawMessage(int meetingNumber, InetAddress address){
        this.meetingNumber = meetingNumber;
        this.inetAddress = address;
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
