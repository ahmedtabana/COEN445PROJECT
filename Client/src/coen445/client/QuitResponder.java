package coen445.client;

/**
 * Created by Ahmed on 15-12-13.
 */
public class QuitResponder extends BaseResponder {

    @Override
    public void respond() {
        super.respond();
        System.out.println("Quitting");
        Client.saveLocalAgendaToFile();
        System.exit(0);
    }
}
