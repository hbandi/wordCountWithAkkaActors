package com.wc.actor;

import akka.actor.UntypedActor;
import com.wc.beans.LineNoWordMapper;
import com.wc.beans.WordAndCount;
import com.wc.beans.WordVsCountReducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WordAggregator extends UntypedActor {

    private static final Logger LOG = LogManager.getLogger(WordAggregator.class);
    private static WordVsCountReducer wordVsCountReducer = new WordVsCountReducer();

    @Override
    public void preStart() {
        super.preStart();
    }

    /**
     * This actor will handle two events.
     * 1) End_OF_File event(This will update the data to result data structure at the end of the file)
     * 2) Print Event
     * <p>
     *
     * @param message
     * @throws Exception
     */

    public void onReceive(Object message) throws Exception {

        try {
            if (message instanceof LineNoWordMapper) {
                LineNoWordMapper lineVsWordMap = (LineNoWordMapper) message;
                LOG.debug(" WordAggregator:: received Line No and Word Mapper. ");
                Set<Integer> keySet = lineVsWordMap.getLineNoVsWordMap().keySet();
                Iterator<Integer> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    Map<String, WordAndCount> wordAndCountMap = lineVsWordMap.getLineNoVsWordMap().get(iterator.next());
                    Set<String> wordKeySet = wordAndCountMap.keySet();
                    Iterator<String> wordIterator = wordKeySet.iterator();
                    while (wordIterator.hasNext()) {
                        String word = wordIterator.next();
                        WordAndCount wordCount = wordAndCountMap.get(word);
                        Integer value = wordVsCountReducer.getWordVsCount().get(word);
                        if (value != null) {
                            wordVsCountReducer.getWordVsCount().put(word, value + wordCount.getCount());
                        } else {
                            wordVsCountReducer.getWordVsCount().put(word, wordCount.getCount());
                        }

                    }
                }

            } else if (message instanceof String) {
                System.out.println("Result on Console:: ");
                for (String word : wordVsCountReducer.getWordVsCount().keySet()) {
                    System.out.print(word + " - " + wordVsCountReducer.getWordVsCount().get(word) + " ");
                    System.out.println();
                }
                /**
                 * shout down will be handled here.
                 */
                getContext().system().shutdown();
            } else {
                unhandled(message);
            }

        } catch (Exception ex) {
            LOG.error(" WordAggregator:: Found an error while processing  " + ex.getMessage());
            ex.printStackTrace();
        }

    }
}
