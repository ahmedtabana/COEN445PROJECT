package Messages;

import coen445.client.Client;

import java.io.*;
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
            System.out.println("");
            System.out.println("");
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
            int day = Integer.parseInt(br.readLine());
            if( day > 31 || day < 1){
                System.out.println("Please enter a correct day");
                return  false;
            }
            dateTime.setDay(day);


            System.out.println("Please enter the month");
            int month = Integer.parseInt(br.readLine());
            if( month > 12 || month < 1){
                System.out.println("Please enter a correct month");
                return  false;
            }
            dateTime.setMonth(month);


            System.out.println("Please enter the year");
            int year = Integer.parseInt(br.readLine());
            if( year < 2015){
                System.out.println("Please enter a year in the future");
                return  false;
            }
            dateTime.setYear(year);


            System.out.println("Please enter the time");
            int time = Integer.parseInt(br.readLine());
            if( time > 24 || time < 0){
                System.out.println("Please enter a correct time");
                return  false;
            }
            dateTime.setTime(time);

            if( month == 2 && day > 28){
                System.out.println("illegal input");
                return false;
            }
            if( year == 2015){
                if(month < 12){
                    System.out.println("illegal input");
                    return false;


                }else if(day < 14){
                        System.out.println("illegal input");
                        return false;
                    }
                 else if(time < 15){
                    System.out.println("illegal input");
                    return false;
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
