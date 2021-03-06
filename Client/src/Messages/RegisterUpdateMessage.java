package Messages;



import Messages.*;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Ahmed on 15-11-29.
 */
public class RegisterUpdateMessage extends UDPMessage {

    private ArrayList<InetAddress> updatedListOfParticipants;
    private static final long serialVersionUID = 7526472295622776147L;



    public ArrayList<InetAddress> getUpdatedListOfParticipants() {
        return updatedListOfParticipants;
    }
    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println(getType());
        System.out.println("Displaying updated participant list that will be used");
        for( InetAddress address : updatedListOfParticipants){
            System.out.println(address);
        }

    }
}

