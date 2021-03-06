package org.agilewiki.jactor.pubsub.latency;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.lpc.Request;

/**
 * Test code.
 */
public class Ping extends Request<Object, Sub> {
    public final static Ping req = new Ping();

    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof Sub;
    }
}
