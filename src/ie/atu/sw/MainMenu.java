package ie.atu.sw;

import java.util.*;
import static java.lang.System.*;


/**
 * @author Tanya Costello
 * @version 1.0
 * @since JDK21
 * 
 * The MainMenu class represents the main menu of the Virtual Threaded Sentiment Analyser.
 * It provides a menu of options to the user and handles user input.
 */
public class MainMenu {

	
/**
 * Scanner instance for user input.
 */
	private Scanner scanner;
	
/**
 * Boolean flag allowing graceful exit from the program.
 */
	private boolean keepRunning = true;

/**
 * Constructor for MainMenu, initializes the scanner for user input.
 *
 * @throws Exception If an exception occurs during initialization
 */
	protected MainMenu() throws Exception {
		scanner = new Scanner(System.in);
	}

	
/**
 * Displays the main menu options to the console and solicits  user input
 *
 * @throws Exception If an exception occurs during console output
 */
	public void show() throws Exception {// O(1)

		out.println(ConsoleColour.YELLOW); 
		out.println("╔══════════════════════════════════════════════════════════════╗");
		out.println("║*                                                            *║");
		out.println("║*                     **THE MAIN MENU**                      *║");
		out.println("║*════════════════════════════════════════════════════════════*║");
		out.println("║*      ATU - Dept. Computer Science & Applied Physics        *║");
		out.println("║*                                                            *║");
		out.println("║*             Virtual Threaded Sentiment Analyser            *║");
		out.println("║*                                                            *║");
		out.println("╚══════════════════════════════════════════════════════════════╝");
		out.println("");
		out.print(ConsoleColour.PURPLE);
		out.println("Please Select an Option [1-2]>");
		out.println("═════════════════════════════");
		out.print(ConsoleColour.YELLOW);
		out.println("");
		out.println("(1) Choose your Lexicon / Tweets and get your Analysis Report!");
		out.println("(2) Quit");
		out.println("");
		out.print(ConsoleColour.PURPLE);// set colour for console to purple
		out.println("═══════════");
		out.print("Select [1-2]>");// user instructions
		out.println("");
		out.print(ConsoleColour.YELLOW);
	}

	
	
/**
 * Processes user input and executes corresponding actions based on the selected choice.
 *
 * @throws Exception If an exception occurs during user input or method execution
 */
	protected void processInput() throws Exception {// O(1)
		show();// output menu to console O(1)
		while (keepRunning) {// while boolean is true...

			int choice = Integer.parseInt(scanner.next());// assign the user input to variable choice
			out.print(ConsoleColour.YELLOW);
			// pass user input to extended switch statement
			switch (choice) {
				case 1 -> {
				ParserConcrete p = new ParserConcrete();// make instance of ParserConcrete class
				p.chooseFile();// call method from ParserConcrete class
			}
				case 2 -> {
				keepRunning = false;// changing variable to 'false' we wind the program down gracefully
				out.println("[INFO] Exiting... Goodbye!");// user exits, prints exit message to console
				scanner.close();
			}
				default -> out.println("[Error] Invalid Selection.");// user input other than (1-2) outputs "Invalid selection"

			}
		}
	}

}
