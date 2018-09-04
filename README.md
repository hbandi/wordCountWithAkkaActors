# wordCountWithAkkaActors
Using Akka Actors for getting word count in a folder which contains many files and folders
====================
Technical flow and components given as below.
1) Flow will start at the main (WordCountClient.java)
2)Actors::
	Defined following Actors for this Project:
		FileScaner.
			/**
			* Consumed Events::
			* <p>
			* This actor will handle one event.
			* 1) Start Event(START) from the main will raise start event this actor will consumed
			* <p>
			*
			* Sending Event::
			* This Actor will raise two events to FileParser actor .
			* 1) Line event
			* 2) end of the file event.
			* and then pick another file in the given directory.
			*
			* @param message
			* @throws Exception
			*/
		FileParser
		
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
		WordAgregator
		   /**
			* This actor will handle two events.
			* 1) End_OF_File event(This will update the data to result data structure at the end of the file)
			* 2) Print Event
			* <p>
			* 
			* @param message
			* @throws Exception
			*/
		PrintResult.
			/**
			 * This actor will handle PRINT Command.
			 * <p>
			 * And it will raise back print command to wordAggregator
			 *
			 * @param message
			 * @throws Exception
			 */
		
Beans::
		WordAndCount:
			Basic Object which hold (word and count)
		LineNoWordMapper::
			Line No Vs Word map.
				Line No:: This will be created for every line in the file in the directory thats the key.
				Word Map:: which will contains the map of words and counts at the line level.
			At the end of the file event this will be merged to main Data structured.
		WordVsCountReducer::
				This the final data structure to print the result in the console.
Used Libraries::
		akka and log4j2.


				
	

