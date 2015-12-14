package Messages;

/**
 * Created by Ahmed on 15-12-13.
 */
public class SuccessMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;



    DateTime dateTime;

    public SuccessMessage(DateTime dateTime){

        this.dateTime = dateTime;
        setType("Success");

    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println(dateTime);

    }
}