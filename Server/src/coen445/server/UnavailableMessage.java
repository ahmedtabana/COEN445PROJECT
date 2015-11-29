package coen445.server;

/**
 * Created by Ahmed on 15-11-28.
 */
public class UnavailableMessage extends UDPMessage {


    private int requestNumber;
    private String unAvailable = " , Room is unavailable";


    UnavailableMessage(int requestNumber){
        this.requestNumber = requestNumber;
        setType("Unavailable");
    }


    @Override
    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("For Request#: " + requestNumber + unAvailable);
    }
}
