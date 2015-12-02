package Messages;


import coen445.server.RequestParticipantListMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Created by Ahmed on 15-11-16.
 */

public class RequestMessage extends UDPMessage {

    private static int counter = 0;
    private static final long serialVersionUID = 7526472295622776147L;

    private int requestNumber;
    private DateTime dateTime;
    private CopyOnWriteArraySet<InetAddress> setOfParticipants;
    private int minimumNumberOfParticipants;
    private String topic;

//    private CopyOnWriteArraySet<InetAddress> availableParticipantsList = Client.availableParticipantsList;





    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public CopyOnWriteArraySet<InetAddress> getSetOfParticipants() {
        return setOfParticipants;
    }


    public int getDay(){
        return dateTime.getDay();
    }
    public int getMonth(){
        return dateTime.getMonth();
    }
    public int getYear(){
        return dateTime.getYear();
    }
    public int getTime(){
        return dateTime.getTime();
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getMinimumNumberOfParticipants() {
        return minimumNumberOfParticipants;
    }

    public void setMinimumNumberOfParticipants(int minimumNumberOfParticipants) {
        this.minimumNumberOfParticipants = minimumNumberOfParticipants;
    }



    private void requestParticipantList() {

        byte[] sendData;
        byte[] recieveData;

        UDPMessage requestParticipantListMessage = null;

        requestParticipantListMessage = (UDPMessage) new  RequestParticipantListMessage();


        try {
            sendData = getBytes(requestParticipantListMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatagramPacket sendPacket;
//        sendPacket.setData(sendData);
//        socket.connect(IPAddress, serverPort);
//        socket.send(sendPacket);
//        socket.receive();
//
    }

    private byte[] getBytes(UDPMessage message) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(message);
        System.out.println("From Client, creating message object:");
        System.out.println(message.toString());
        return outputStream.toByteArray();
    }

    public void displayMessage(){

        System.out.println("Message type: " + getType());
        System.out.println("Request Number: " + getRequestNumber());

        System.out.println("Day: " + dateTime.getDay());
        System.out.println("Month: " + dateTime.getMonth());
        System.out.println("Year: " + dateTime.getYear());
        System.out.println("Time: " + dateTime.getTime());

        System.out.println("Set of Participants:");
        for(InetAddress address : setOfParticipants){
            System.out.println(address);
        }

        System.out.println("Minimum number of participants: " + getMinimumNumberOfParticipants());
        System.out.println("Topic: " + getTopic());

    }

}
