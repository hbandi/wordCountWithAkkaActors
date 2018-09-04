package com.wc.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.wc.event.EventConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrintResult extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(PrintResult.class);

    /**
     * This actor will handle PRINT Command.
     * <p>
     * And it will raise back print command to wordAggregator
     *
     * @param message
     * @throws Exception
     */

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            LOG.debug("PrintResult:: sending event to Word agregator for print the final result:: ");
            ActorRef aggregatorWorker = getContext().system().actorOf(new Props(WordAggregator.class));
            aggregatorWorker.tell(EventConstants.COMMAND_PRINT);
        }
    }
}
