package coen445.server;
/**
 * Created by Ahmed on 15-11-16.
 */
public class MessageFactory {

    public UDPMessage createMessage(String newMessageType) {

        UDPMessage newMessage = null;
        try {
            newMessage = (UDPMessage) Class.forName(newMessageType).newInstance();
            return newMessage;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return newMessage;

    }
}

