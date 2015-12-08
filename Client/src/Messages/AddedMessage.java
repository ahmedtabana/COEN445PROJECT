package Messages;

import java.net.InetAddress;

/**
 * Created by Ahmed on 15-12-05.
 */
public class AddedMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;

    private int meetingNumber;
    private InetAddress addedParticipantAddress;

    public AddedMessage(int meetingNumber, InetAddress addedParticipantAddress){
        setType("Added");

        this.meetingNumber = meetingNumber;
        this.addedParticipantAddress = addedParticipantAddress;
    }
    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Meeting Number: " + meetingNumber);
        System.out.println("IP address" + addedParticipantAddress);
    }
}
