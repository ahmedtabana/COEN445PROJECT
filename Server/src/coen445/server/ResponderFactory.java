package coen445.server;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Ahmed on 15-11-24.
 */
public class ResponderFactory {

    public static Responder createResponder(String messageType) {

        Responder newResponder = null;
        String type = "coen445.server." + messageType + "Responder";
        try {
            newResponder = (Responder) Class.forName(type).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find this responder type");
            e.printStackTrace();
        }


        return newResponder;

    }
}

