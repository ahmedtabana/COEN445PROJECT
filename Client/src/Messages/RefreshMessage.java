package Messages;

/**
 * Created by Ahmed on 15-12-02.
 */
public class RefreshMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;
    public RefreshMessage(){
        setType("Refresh");
    }
}
