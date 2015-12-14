package Messages;

import coen445.server.MeetingData;

import java.net.InetAddress;

/**
 * Created by Ahmed on 15-12-13.
 */
public class SuccessMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;



    MeetingData meetingData;

    public SuccessMessage(MeetingData meetingData){

        this.meetingData = meetingData;
        setType("Success");

    }


    public MeetingData getMeetingData() {
        return meetingData;
    }

    public void setMeetingData(MeetingData meetingData) {
        this.meetingData = meetingData;
    }


}
