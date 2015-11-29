package coen445.server;


import javax.xml.bind.SchemaOutputResolver;
import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;


/**
 * Created by Ahmed on 15-11-16.
 */

public class RequestMessage extends UDPMessage{

    private static int counter = 0;

    private int requestNumber;
    private DateTime dateTime;
    private ArrayList<InetAddress> listOfParticipants;
    private int minimumNumberOfParticipants;
    private String topic;


    public RequestMessage() {

//        requestParticipantList();

        setType("Request");
        counter++;
        setRequestNumber(counter);

        dateTime = new DateTime();
        listOfParticipants = new ArrayList<InetAddress>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(!dateTimeReady(br));
        while(!listOfParticipantsReady());
        while(!minimumNumOfParticipantsReady(br));
        while(!topicReady(br));

        System.out.println("Request Message Created");

    }

    private boolean listOfParticipantsReady() {
        //todo
        return true;
    }

    private boolean minimumNumOfParticipantsReady(BufferedReader br) {
        try {
            System.out.println("Please enter the minimum number of participants");
            setMinimumNumberOfParticipants(Integer.parseInt(br.readLine()));

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

    private boolean topicReady(BufferedReader br) {
        try {
            System.out.println("Please enter the message topic");
            setTopic(br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean dateTimeReady(BufferedReader br) {
        try {

            System.out.println("Please enter the day");
            dateTime.setDay(Integer.parseInt(br.readLine()));
            System.out.println("Please enter the month");
            dateTime.setMonth(Integer.parseInt(br.readLine()));
            System.out.println("Please enter the year");
            dateTime.setYear(Integer.parseInt(br.readLine()));
            System.out.println("Please enter the time");
            dateTime.setTime(Integer.parseInt(br.readLine()));

        } catch (IOException e) {
            e.printStackTrace();
        }catch (NumberFormatException e) {
            System.out.println("Not an Integer");
            return false;
        }
        return true;
    }

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

    public ArrayList<InetAddress> getListOfParticipants() {
        return listOfParticipants;
    }

    public void setListOfParticipants(ArrayList<InetAddress> listOfParticipants) {
        this.listOfParticipants = listOfParticipants;
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



        System.out.println("Minimum number of participants: " + getMinimumNumberOfParticipants());
        System.out.println("Topic: " + getTopic());

    }

}
