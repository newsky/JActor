package org.agilewiki.jactor.lpc;

import junit.framework.TestCase;
import org.agilewiki.jactor.*;

/**
 * Test code.
 */
public class SSATest extends TestCase {
    public void test() {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            Actor a = new A(mailboxFactory.createAsyncMailbox());
            Actor s1 = new S(mailboxFactory.createMailbox(), a);
            Actor s2 = new S(mailboxFactory.createMailbox(), s1);
            JAFuture future = new JAFuture();
            System.err.println(SimpleRequest.req.send(future, s2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    class S extends JLPCActor implements SimpleRequestReceiver {
        Actor n;

        S(Mailbox mailbox, Actor n) {
            super(mailbox);
            this.n = n;
        }

        @Override
        public void processRequest(SimpleRequest request, final RP rp) throws Exception {
            System.err.println("S got request");
            send(n, request, new RP() {
                @Override
                public void processResponse(Object response) throws Exception {
                    System.err.println("S got response");
                    rp.processResponse(response);
                }
            });
        }
    }

    class A extends JLPCActor implements SimpleRequestReceiver {

        A(Mailbox mailbox) {
            super(mailbox);
        }

        @Override
        public void processRequest(SimpleRequest request, RP rp) throws Exception {
            System.err.println("A got request");
            rp.processResponse(request);
        }
    }
}
