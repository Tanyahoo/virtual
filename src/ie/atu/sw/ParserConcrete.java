package ie.atu.sw;

import static java.lang.System.out;

import java.io.*;
import java.nio.file.*;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;


/**
 * @author Tanya Costello
 * @version 1.0
 * @since JDK21
 *  
 * Concrete implementation of the Goer interface and  extends AbstractParserMap class.
 * Handles parsing and processing of text files, using virtual threads.
 * Takes user interactions via a menu, processes chosen files for sentiment analysis.
 */
public class ParserConcrete extends AbstractParser implements Goable {

	
/**
 * Indicates whether the program should continue running.
 * If true, the program continues execution; otherwise winds down gracefully.
 */
	private boolean keepRunning = true;
	
	
/**
 * Counter for the number of lines processed.
 * Tracks the number of lines processed during text parsing or analysis.
 */
	private int line = 0;
	

/**
 * Collection to store words extracted from text files.
 * Uses a ConcurrentLinkedDeque for thread-safe storage of words.
 */
	private static Collection<String> wordDeq = new ConcurrentLinkedDeque<>();
	

/**
 * Retrieves the collection of words from concurrentDeque.
 *
 * @return Collection containing words extracted from text files
 */
	protected Collection<String> getWordDeq() { // O(n)
		return wordDeq;
	}

	

	
/**
 * Displays the parser menu, presenting options for text file selection.
 * Allows users to choose text files for processing or exit the program.
 *
 * @throws Exception if an error occurs during the menu interaction
 */
	private void menuSource() throws Exception { // user's menu: choice of text to process
		out.println(ConsoleColour.YELLOW);
		out.println("╔══════════════════════════════════════════════════════════════╗");
		out.println("║*                                                            *║");
		out.println("║*                       *PARSER MENU*                        *║");
		out.println("║*               Please Choose the Text File(s)               *║");
		out.println("║*                that You Wish to Process :)                 *║");
		out.println("║*                                                            *║");
		out.println("╚══════════════════════════════════════════════════════════════╝");
		out.println("                                                                ");
		out.print(ConsoleColour.PURPLE);
		// // Output a menu of options and solicit text from the user
		out.println("Please look at the choices below. Select [1-3]>");
		out.println("═══════════════════════════════════════════════════");
		out.print(ConsoleColour.YELLOW);
		out.println("Press [1]> Choose a Lexicon file");
		out.println("");
		out.println("Press [2]> Main Menu");// option to return to Main Menu
		out.println("");
		out.println("Press [3]> Exit");// option for user to exit program
		out.println("");
		out.print(ConsoleColour.PURPLE);
		out.println("═══════════");
		out.println("Enter [1-3]>");
		out.print(ConsoleColour.YELLOW);
		out.println("");
	}

	
	
	
/**
 * Displays the menu for file selection and processes user input to choose files for parsing.
 * It allows the user to select a file, return to the main menu, or exit the program.
 *
 * @throws Exception if an error occurs during file selection or menu interaction
 */
	protected void chooseFile() throws Exception { // use while loop to keep menuSource 'running' until user exits
		menuSource(); // call the menuSource method to display user options
		try (Scanner scanner = new Scanner(System.in)) {// try with resources to prevent leaks
			while (keepRunning) {

				int choice = Integer.parseInt(scanner.next());// get user input assign to variable choice

				// pass user input/choice to extended switch statement to process options
				switch (choice) {
				case 1 -> super.readLex();// calls method from Class AbstractParser, parent class
				case 2 -> {
					out.println("[INFO] Redirecting you to Main Menu...");// user message
					MainMenu m = new MainMenu(); // new Menu class object
					m.processInput();// call processInput method to return to main menu in Menu Class
				}
				case 3 -> {
					keepRunning = false;// changing boolean to 'false' winds the program down gracefully
					// user exits, prints message to console
					out.println("[INFO] You are now exiting... Goodbye!");
					scanner.close();
				}
				default -> {
					out.println("[Error] Invalid Selection!");// Input other than (1-4) outputs "Invalid selection"
					out.println("Please enter a valid selection between [1-5]>");
					chooseFile(); // returns user to menu selection
				}
				}
			}

		}
	}

	
	
/**
 * Implements method from interface Goer
 * Parses the provided Twitter file by distributing its lines to virtual threads for processing.
 * It uses an executor to allocate a virtual thread per task for each line of text.
 *
 * @param tweets The 'Twitter' file to be processed
 */
	@Override
	public void go(String tweets) {// O(n)
		// uses an executor to allocate a virtual thread per task
		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			try {// using streams (seen in course lab), take each line and pass to virtual thread
				Files.lines(Paths.get(tweets)).forEach(text -> pool.execute(() -> { //lambda with logic..
					try {
						process(text, ++line);// ..logic is: pass in each line of text to method 'process'
					} catch (Exception e) {
						e.printStackTrace();
					}
				}));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// out.println(wordDeq); // print to console to check if it's working
		// out.println(wordDeq.size());
	}

	
	
	
	
/**
 * Inherited method from base class AbstractParser ie 'specialisation inheritance'
 * Supplants base class's method implementation, approrpiate annotation.
 * Processes a line of text by splitting it into words, cleansing, and adding them to the word deque.
 * It processes each word from the text by cleansing unwanted characters and converting to lowercase.
 *
 * @param next The text line to be processed
 * @param line The line number being processed
 * @throws Exception if an error occurs during word processing
 */
	@Override
	protected void process(String next, int line) throws Exception {// method takes in string file
		String[] words = next.split("\\s+"); // returns array of strings after splitting file 
		for (String word : words) {// for each word in the array
			addWord(word);// pass the word to method, addWord
		}
	}

	
	
	
/**
 * Adds a word to the word deque after cleansing unwanted characters and converting to lowercase.
 * This method adds words to the concurrent linked deque 'wordDeq'.
 *
 * @param word The word to be added to the deque
 * @throws Exception if an error occurs while adding the word
 */
	private void addWord(String word) throws Exception {
		word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();// O(n)

		if (!word.isEmpty()) {// as long as there's a word
			wordDeq.add(word);// add word to concurrentLinkedDeque 'wordDeq' O(1)
			// out.println(word);
		}
	}

	
	
	
/**
 * Reads a user-chosen Twitter file, processes it, and passes to executor and virtual threads.
 * It reads the user's chosen file, offloads to method to perform sentiment analysis.
 *
 * @throws Exception if an error occurs during file reading or sentiment analysis
 */
	protected void readFile() throws Exception { // O(n)
		out.print(ConsoleColour.PURPLE);
		out.println("Please enter the Twitter file of your choice: ");// user message to input file of choice
		out.print(ConsoleColour.YELLOW);
		try (Scanner scanner = new Scanner(System.in)) { // surround by try with resources, no leaks
			String fileChoice = scanner.nextLine(); // assigns the users file to variable 'fileChoice'
			out.print(ConsoleColour.PURPLE);
			out.print("");
			if (fileChoice == null || fileChoice.trim().isEmpty()) {// if the user's choice of file is null

				out.println("[Error] Invalid Selection!");
				readFile();// recursion, calls method to restart and get valid user input

			} else {
				File chosenFile = new File(fileChoice);

				if (!chosenFile.exists() || !chosenFile.isFile() || !chosenFile.canRead()) {
					out.println("[Error] Unreachable or Invalid File!");

				} else {
					// otherwise load the user's file to method readFile which reads in the file
					go(fileChoice);// pass user input to method to distribute to virtual threads O(n)
					check(wordDeq, super.getHashMap());// calls method for analysis O(n2)
				}
			}
		}

	}

	
	
	// for loop is O(n), iterates n times, so O(n2)
/**
 * Checks words in the word deque against keys in the hash map and calculates a sentiment score.
 * It iterates through words in the deque, matches them with keys in the map.
 * It calculates a score by adding corresponding values in the map of matching keys in the deque.
 * Uses the SaS approach (Score and Sum)
 *
 * @param wordDeque The collection of words to be checked
 * @param hashMap   The map containing words and their corresponding values
 * @throws Exception if an error occurs during sentiment analysis
 */
	protected void check(Collection<String> wordDeque, Map<String, Integer> hashMap) throws Exception {
		ParserConcrete.wordDeq = wordDeque;// pass in Deque
		hashMap = super.getHashMap();// access and pass in map from abstract/super class
		int totalValue = 0; // Initial value
		for (String w : wordDeque) {// for each word in the deque 'wordDeq'
			Integer value = hashMap.get(w); // get 'value' of word in map O(1) 
			if (value != null) { // if value not null..
				// the matching key found in the map, add its value to totalValue
				totalValue += value; // add up all values O(1)
				// out.println(totalValue); // check by printing to console
			} // If the key doesn't exist, it will be ignored (no action taken)

		}
		out.print(""); // print out message and totalvalue to console
		out.println("**** Your Sentiment Analysis score using the SaS scoring system is: " + totalValue + " ****");
		out.print("");
		out.println("════════════════════════════════════════════════════");
		out.print("");
		out.println("Your Sentiment Analysis is now complete! Happy Days!");
		out.println("");

		MainMenu menu = new MainMenu(); // create instance of class MainMenu
		menu.processInput();// call method to return to MainMenu options, user can exit from here O(1)
	}
	
}
