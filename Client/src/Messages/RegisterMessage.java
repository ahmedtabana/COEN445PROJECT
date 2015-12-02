package Messages;


/**
 * Created by Ahmed on 15-11-24.
 */
public class RegisterMessage extends UDPMessage {
    private static final long serialVersionUID = 7526472295622776147L;

    public RegisterMessage(){

        setType("Register");
        System.out.println("Register message created");
    }

}
