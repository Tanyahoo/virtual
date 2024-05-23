package ie.atu.sw;


/**
 * @author Tanya Costello
 * @version 1.0
 * @since JDK21
 * 
 * The {@code Runner} class contains the main method to execute the program.
 * It initiates the application by displaying the main menu and initializing
 * the required components to start the text processing.
 */
public class Runner {

	
/**
 * The main entry point of the program. Initiates the application by
 * displaying the main menu and initializing the necessary components
 * for text processing.
 *
 * @param args The command line arguments passed to the program
 * @throws Exception If an exception occurs during program execution
 */
	public static void main(String[] args) throws Exception {
		new MainMenu().processInput(); // display menu with options 
		ParserConcrete pc = new ParserConcrete();// instance subtype ParserConcrete class
		pc.readLex(); // subtype calls base class's method

	}

}
