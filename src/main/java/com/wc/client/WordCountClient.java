package com.wc.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.wc.actor.FileScanner;
import com.wc.event.EventConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.util.HashMap;


public class WordCountClient {
    private static final Logger LOG = LogManager.getLogger(WordCountClient.class);

    public static void main(String[] args) {
        HashMap
        try {
            LOG.debug("WordCount Application started  ");
            ActorSystem system = ActorSystem.create("WordCountStart");
            ActorRef fileScaner = system.actorOf(new Props(FileScanner.class), "fileScaner");
            fileScaner.tell(EventConstants.filePath);
            LOG.debug("WordCount Application ended  ");
            /**
             * shoutdown will be handled after processing all the files in PrintResult
             * Actor
             */
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
