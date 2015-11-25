package coen445.server;

/**
 * Created by Ahmed on 15-11-24.
 */
public class RequestResponder implements Responder {


    @Override
    public void respond() {
        System.out.println("this is the RequestResponder respond method");
    }
}
