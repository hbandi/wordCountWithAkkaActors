package com.wc.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.wc.event.EventConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileScanner extends UntypedActor {


    private static final Logger LOG = LogManager.getLogger(FileScanner.class);
    List<ActorRef> fileLineParsers;

    @Override
    public void preStart() {
        fileLineParsers = new ArrayList<ActorRef>(EventConstants.default_workers);
        for (int i = 0; i < EventConstants.default_workers; i++) {
            ActorRef worker = getContext().system().actorOf(new Props(FileParser.class));
            fileLineParsers.add(worker);
        }
        super.preStart();
    }

    /**
     * Consumed Events::
     * <p>
     * This actor will handle one event.
     * 1) Start Event(START) from the main will raise start event this actor will consumed
     * <p>
     * Sending Event::
     * This Actor will raise two events to FileParser actor .
     * 1) Line event
     * 2) end of the file event.
     * and then pick another file in the given directory.
     *
     * @param message
     * @throws Exception
     */

    public void onReceive(Object message) throws Exception {
        try {
            if (message instanceof String) {
                String filePath = (String) message;
                LOG.debug("FileScanner:: recived File path Event::  ");
                File file = new File(filePath);
                if (file.listFiles() != null) {
                    for (File child : file.listFiles()) {
                        FileInputStream inputStream = null;
                        Scanner scanner = null;
                        try {
                            if (child.isFile()) {
                                inputStream = new FileInputStream(child);
                                scanner = new Scanner(inputStream, "UTF-8");
                                while (scanner.hasNextLine()) {
                                    String line = scanner.nextLine();
                                    if (line != null || !line.isEmpty()) {
                                        ActorRef worker = fileLineParsers.remove(0);
                                        worker.tell(line);
                                        fileLineParsers.add(worker);
                                    }
                                }
                                //last line completed,send event to fileParser Actor as -1
                                ActorRef aggregatorWorker = getContext().system().actorOf(new Props(FileParser.class));
                                aggregatorWorker.tell(Integer.valueOf(-1));
                            } else {
                                getSender().tell(filePath);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (scanner != null) {
                                scanner.close();
                            }
                        }
                    }
                }
            }

            ActorRef printResultActor = getContext().system().actorOf(new Props(PrintResult.class));
            printResultActor.tell(EventConstants.COMMAND_PRINT);

        } catch (Exception ex) {
            LOG.debug("FileScanner:: Found an Exception while processing  " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
