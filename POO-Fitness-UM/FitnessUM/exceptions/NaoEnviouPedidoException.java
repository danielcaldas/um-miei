package exceptions;

/**
 * @author Jose Francisco
 * @version 1/06/2014
 */

public class NaoEnviouPedidoException extends Exception {
	private static final long serialVersionUID = -7582775596793810876L;

	public NaoEnviouPedidoException() {
		super();
	}

	public NaoEnviouPedidoException(String s) {
		super(s);
	}
}
