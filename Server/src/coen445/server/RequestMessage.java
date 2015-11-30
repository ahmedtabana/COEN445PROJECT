package coen445.server;


import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import coen445.client.*;


/**
 * Created by Ahmed on 15-11-16.
 */

public class RequestMessage extends UDPMessage{

    private static int counter = 0;

    private int requestNumber;
    private DateTime dateTime;
    private CopyOnWriteArraySet<InetAddress> setOfParticipants;
    private int minimumNumberOfParticipants;
    private String topic;

    private CopyOnWriteArraySet<InetAddress> availableParticipantsList = Client.availableParticipantsList;


    public RequestMessage() {

//        requestParticipantList();

        setType("Request");
        counter++;
        setRequestNumber(counter);

        dateTime = new DateTime();
        setOfParticipants = new CopyOnWriteArraySet<InetAddress>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(!dateTimeReady(br));
        displayListOfAvailableParticipants();
        while(!listOfParticipantsReady(br));
        while(!minimumNumOfParticipantsReady(br));
        while(!topicReady(br));

        System.out.println("Request Message Created");

    }

    private void displayListOfAvailableParticipants() {

        System.out.println(" ");
        System.out.println("List of available participants:");

        for( InetAddress address : availableParticipantsList){
            System.out.println(address);
        }
        System.out.println(" ");
    }

    private boolean listOfParticipantsReady(BufferedReader br) {

        int numOfParticipants;

        try {

            System.out.println("Please enter the number of participants you wish to have in your list of participants");
            numOfParticipants = Integer.parseInt(br.readLine());

            if(numOfParticipants > availableParticipantsList.size()){
                System.out.println("Number of participants must be less than the number of participants currently available");
                return false;
            }

            int counter = 1;
            while(counter <= numOfParticipants) {
                if(setOfParticipants.add(getParticipantAddress(br, counter))){
                    counter++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (NumberFormatException e) {
            System.out.println("Not an Integer");
            return false;
        }
        return true;
    }

    private InetAddress getParticipantAddress(BufferedReader br, int counter) {

        InetAddress participantAddress = null;

        boolean isReady = false;
        while(!isReady) {

            System.out.println("Please select an IP address from the list for Participant #" + counter);
            displayListOfAvailableParticipants();

            try {
                participantAddress = InetAddress.getByName(br.readLine());
                System.out.println("You entered InetAddress = " + participantAddress);
                if(!availableParticipantsList.contains(participantAddress)){
                    System.out.println("The IP address you entered is not on the list of available participants");
                    isReady =false;
                }
                else{
                    isReady = true;
                }
            } catch (IOException e) {
                System.out.println("Not an Integer");
                isReady = false;
            }
        }
        return participantAddress;
    }

    private boolean minimumNumOfParticipantsReady(BufferedReader br) {
        try {
            System.out.println("Please enter the minimum number of participants that must attend");
            setMinimumNumberOfParticipants(Integer.parseInt(br.readLine()));
            if(getMinimumNumberOfParticipants() > setOfParticipants.size()){
                System.out.println("minimum number of attendees should be less than or equal to total number on participants ");
                return false;
            }

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
