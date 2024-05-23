package ie.atu.sw;

import static java.lang.System.out;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;


/**
 * @author Tanya Costello
 * @version 1.0
 * @since JDK21
 * 
 * AbstractParserMap represents an <i>abstract class</i> for parsing and handling lexicon files.
 * It provides functionality to populate a thread-safe ConcurrentHashMap from a lexicon file,
 * read user input for file choices, and process text.
 * 
 */
public abstract class AbstractParser {


/**
 * Scanner object to handle user input.
 */
	private Scanner scanner = new Scanner(System.in);
	
/**
 * Thread-safe map to store key-value pairs from the lexicon file.
 */
	private static Map<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
	
	
/**
 * Retrieves the ConcurrentHashMap instance.
 *
 * @return The ConcurrentHashMap containing key-value pairs
 */
	protected Map<String, Integer> getHashMap() {
		return hashMap;
	}


	
	

/**
 * Parses the provided lexicon file, populates key-value pairs into a thread-safe map using virtual threads.
 *
 * @param lexicon The file path of the lexicon to be parsed
 */
    protected void mapLex(String lexicon) {
    	//uses virtual thread executor, individual thread to each task - 'cheap as chips'
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor(); 
        // try with resources, take each line from lexicon using streams
        try (Stream<String> lines = Files.lines(Paths.get(lexicon))) {
        	//takes every line, splits at the comma, filters array in parts that are length of 2, each given to executor
            lines.map(line -> line.split(",")).filter(parts -> parts.length == 2).forEach(parts -> {
                executor.execute(() -> { // lambda passes runnable with the logic: puts 1st part as key after trimming..
                	//..pass 2nd part, converted to Integer and trimming as key into hashMap variable
                    hashMap.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));// O(1) or if constant hashCode collisons O(n2)
                    //  can print within the virtual thread to check
                     //out.println("Added: " + parts[0].trim() + " : " + parts[1].trim()); O(1)
                });
            });
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Shutting down the executor, not sure if 'try with resources' handles this
        }
        // check on console ,iterating through the map (outside the executor) 
       // hashMap.forEach((key, value) -> out.println(key + " : " + value)); // O(n) 
    }


	
    
    
	//gets user input to pass chosen file as parameter to populate hashMap
    // O(n) for the readLex and O(n) for mapLex method called at end, so O(n2) altogether?
/**
 * Reads user input for choosing a lexicon file and invokes mapLex and readFile methods based on the user's choice.
 *
 * @throws Exception Any exception encountered during file reading
 */
	protected void readLex() throws Exception {
		out.print(ConsoleColour.PURPLE);
		out.print("");
		out.println("Please enter the Lexicon file of your choice (preferably bingliu, mpqa or afinn :D)  ");
		out.print("");// user message to input file of choice
		out.print(ConsoleColour.YELLOW);
		String fileChoice = scanner.nextLine(); // assigns user input to variable 'fileChoice'
		out.print("");
		if (fileChoice == null || fileChoice.trim().isEmpty()) {// if the user's choice of file is null
			out.println("[Error] Invalid Selection!");
			readLex();// recursion, calls method to restart and get valid user input

		} else {
			File chosenFile = new File(fileChoice);
			// the file doesn't exists or isn't a file or can't be read
			if (!chosenFile.exists() || !chosenFile.isFile() || !chosenFile.canRead()) {
				out.println("[Error] Unreachable or Invalid File!");

			} else if(chosenFile.canRead())
				// pass the user's file to method mapLex which passes to virtual threads as a task
				mapLex(fileChoice); // passes file to the MapLex method, O(n)
			ParserConcrete p = new ParserConcrete();// instance of ParseConcrete class
			p.readFile();// calls method from class to ParserConcrete, to read file O(n)
			}
		
		//scanner.close();// stop leaks
	}

	
	
	
	// method used in ParserConcrete class O(1)
/**
 * Method to process text, to be overridden by subclasses.
 *
 * @param text The text to be processed
 * @param line The line number of the text
 * @throws Exception Any exception during the process
 */
	protected void process(String text, int line) throws Exception {
		out.println("I'm going to be overriden! Yippee!");
	}  
		
}
