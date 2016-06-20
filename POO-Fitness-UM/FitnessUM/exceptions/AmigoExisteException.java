package exceptions;

/**
 * @author Jose Francisco
 * @version 1/06/2014
 */


public class AmigoExisteException extends Exception {
	private static final long serialVersionUID = -4704231728982230813L;

	public AmigoExisteException() {
		super();
	}

	public AmigoExisteException(String s) {
		super(s);
	}
}
