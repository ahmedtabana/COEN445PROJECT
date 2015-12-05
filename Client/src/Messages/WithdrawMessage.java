package Messages;

/**
 * Created by Ahmed on 15-12-03.
 */
public class WithdrawMessage extends UDPMessage {



    private int meetingNumber;

    public WithdrawMessage(){

        setType("Withdraw");
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

    }
}
