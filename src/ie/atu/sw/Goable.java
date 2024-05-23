package ie.atu.sw;


/**
 * @author Tanya Costello
 * @version 1.0
 * @since JDK21
 * 
 * The {@code Goable} interface represents a simple contract for classes.
 * It perform a single abstract method ie SAM, {@code go(String file)}.
 * Classes implementing this interface are expected to provide an implementation
 * for the {@code go} method, which typically involves handling a file or file path.
 */
public interface Goable {

	
/**
 * A method that processes a file or file path.
 *
 * @param file The file to be processed
 */
	public void go(String file);
	

}
