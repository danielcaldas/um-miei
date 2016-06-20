package exceptions;

/**
 * @author Jose Francisco
 * @version 1/06/2014
 */

public class UserNaoExisteException extends Exception {

	public UserNaoExisteException() {
		super();
	}

	public UserNaoExisteException(String s) {
		super(s);
	}
}