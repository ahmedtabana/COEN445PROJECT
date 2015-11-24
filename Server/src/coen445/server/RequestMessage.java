package coen445.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Ahmed on 15-11-16.
 */

public class RequestMessage extends UDPMessage{

    private static int counter = 0;



    private int requestNumber;
    // private DateTime meetingDateTime;
    private int minimumNumberOfParticipants;
    private ArrayList<InetAddress> listOfParticipants;
    private String topic;


    public RequestMessage() {

        setType("REQUEST");
        counter++;
        setRequestNumber(counter);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean isReady = false;
        while(!isReady) {
            try {
                System.out.println("Please enter the message topic");
                setTopic(br.readLine());
                isReady = true;

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Please enter the minimum number of participants");
                setMinimumNumberOfParticipants(Integer.parseInt(br.readLine()));

            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (NumberFormatException e) {
                System.out.println("Not an Integer");
                isReady = false;
            }


        }
        System.out.println("Request Message Created");


    }

    public int getMinimumNumberOfParticipants() {
        return minimumNumberOfParticipants;
    }

    public void setMinimumNumberOfParticipants(int minimumNumberOfParticipants) {
        this.minimumNumberOfParticipants = minimumNumberOfParticipants;
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

    public void displayRequestMessage(){
        System.out.println("Message type: " + getType());
        System.out.println("Request Number: " + getRequestNumber());
        System.out.println("Date: ");
        System.out.println("Time: ");

        System.out.println("Minimum number of participants: " + getMinimumNumberOfParticipants());
        System.out.println("Topic: " + getTopic());


    }



}
