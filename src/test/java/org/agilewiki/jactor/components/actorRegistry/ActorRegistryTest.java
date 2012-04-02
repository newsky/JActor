package org.agilewiki.jactor.components.actorRegistry;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.bind.Open;
import org.agilewiki.jactor.components.Include;
import org.agilewiki.jactor.components.JCActor;
import org.agilewiki.jactor.pubsub.actorName.ActorName;
import org.agilewiki.jactor.pubsub.actorName.JActorName;
import org.agilewiki.jactor.pubsub.actorName.SetActorName;

/**
 * Test code.
 */
public class ActorRegistryTest extends TestCase {
    public void test1() {
        System.out.println("test1");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            ActorName a = new JActorName(mailboxFactory.createMailbox());
            (new SetActorName("foo")).call(a);
            JCActor r = new JCActor(mailboxFactory.createMailbox());
            (new Include(ActorRegistry.class)).call(r);
            Open.req.call(r);
            (new RegisterActor(a)).call(r);
            System.out.println((new GetRegisteredActor("abe")).call(r));
            System.out.println((new GetRegisteredActor("foo")).call(r));
            (new UnregisterActor("foo")).call(r);
            System.out.println((new GetRegisteredActor("foo")).call(r));
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    public void test2() {
        System.out.println("test2");
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JCActor pr = new JCActor(mailboxFactory.createMailbox());
            (new Include(ActorRegistry.class)).call(pr);
            Open.req.call(pr);
            ActorName a = new JActorName(mailboxFactory.createMailbox());
            (new SetActorName("foo")).call(a);
            (new RegisterActor(a)).call(pr);
            JCActor r = new JCActor(mailboxFactory.createMailbox());
            r.setParent(pr);
            (new Include(ActorRegistry.class)).call(r);
            Open.req.call(r);
            System.out.println((new GetRegisteredActor("abe")).call(r));
            System.out.println((new GetRegisteredActor("foo")).call(r));
            (new UnregisterActor("foo")).call(r);
            System.out.println((new GetRegisteredActor("foo")).call(r));
            (new UnregisterActor("foo")).call(pr);
            System.out.println((new GetRegisteredActor("foo")).call(r));
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }
}
