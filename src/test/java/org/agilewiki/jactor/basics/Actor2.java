package org.agilewiki.jactor.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Test code.
 */
public class Actor2 extends JLPCActor implements Greeter {
    public Actor2(Mailbox mailbox) {
        super(mailbox);
    }

    @Override
    public void processRequest(Greet1 request, final RP rp) throws Exception {
        Hi1.req.send(this, getParent(), new RP<String>() {
            @Override
            public void processResponse(String response) throws Exception {
                System.out.println(response);
                rp.processResponse(null);
            }
        });
    }
}
