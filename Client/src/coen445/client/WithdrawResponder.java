package coen445.client;

import Messages.*;

/**
 * Created by Ahmed on 15-12-11.
 */
public class WithdrawResponder extends BaseResponder {

    WithdrawMessage withdrawMessage;
    @Override
    public void respond() {
        super.respond();
        withdrawMessage = (WithdrawMessage) message;
    }
}
