package org.agilewiki.jactor.basics;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;

/**
 * Test code.
 */
public class Actor5a extends JLPCActor {

    public Actor5a(Mailbox mailbox) {
        super(mailbox);
    }

    protected void processRequest(Parallel request, final RP rp) throws Exception {
        MailboxFactory mf = getMailboxFactory();
        Actor5 a = new Actor5(mf.createAsyncMailbox());
        Actor5 b = new Actor5(mf.createAsyncMailbox());
        Actor5 c = new Actor5(mf.createAsyncMailbox());
        a.delay = 10;
        c.delay = 10;
        a.delay = 10;
        final long t0 = System.currentTimeMillis();
        RP<Object> rc = new RP<Object>() {
            int c = 3;

            @Override
            public void processResponse(Object response) throws Exception {
                c -= 1;
                if (c == 0) {
                    long t1 = System.currentTimeMillis();
                    rp.processResponse((t1 - t0) < 15);
                }
            }
        };
        Delay.req.send(this, a, rc);
        Delay.req.send(this, b, rc);
        Delay.req.send(this, c, rc);
    }
}
