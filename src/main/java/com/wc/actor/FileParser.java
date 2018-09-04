package com.wc.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.wc.beans.LineNoWordMapper;
import com.wc.beans.WordAndCount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FileParser extends UntypedActor {
    private static final Logger LOG = LogManager.getLogger(FileParser.class);
    private static LineNoWordMapper lineVsWordMap = new LineNoWordMapper();
    private static int lineNo;

    @Override
    public void preStart() {
        super.preStart();
    }

    /**
     * This actor will handle two events.
     * 1) File Line event. (LINE_EVENT)
     * 2) End of the file event.(END_OF_FILE_EVENT which is -1).
     * <p>
     * For every file this actor gets events for every line and
     * end of the file it will aggregate to the main data structure.
     *
     * @param message
     * @throws Exception
     */
    public void onReceive(Object message) throws Exception {
        try {
            if (message instanceof String) {
                LOG.debug("FileParser:: Line event Received.");
                Map<String, WordAndCount> tempMap = new HashMap<String, WordAndCount>();
                String line = (String) message;
                if (line == null || line.isEmpty()) {
                    return;
                }
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken();
                    if (!tempMap.containsKey(word)) {
                        tempMap.put(word, new WordAndCount(word, 1));
                    } else {
                        WordAndCount wordCount = tempMap.get(word);
                        wordCount.setCount(wordCount.getCount() + 1);
                        tempMap.put(word, wordCount);
                    }
                }
                lineVsWordMap.getLineNoVsWordMap().put(++lineNo, tempMap);
            } else if (message instanceof Integer) {//end of the file passed -1,send event to WordAgregator
                ActorRef wordAggregator = getContext().system().actorOf(new Props(WordAggregator.class));
                wordAggregator.tell(lineVsWordMap);
            } else {
                unhandled(message);
            }
        } catch (Exception ex) {
            LOG.error("FileParser:: Found an error while processing :: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
