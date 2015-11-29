package coen445.server;

import java.io.Serializable;

/**
 * Created by Ahmed on 15-11-10.
 */
public abstract class UDPMessage implements Serializable {

    private String type;
    private static final long serialVersionUID = 1L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRequestNumber() {return -1;}

    public void displayType() {
        System.out.println("Type =" + getType());
    }

    public void displayRequestMessage() {}

    public String toString(){
        String result;
        result = getType();
        return result;
    }
}

