package excepciones;

public class NoHayFechaDisposicionAnteriorException extends Exception{

	public NoHayFechaDisposicionAnteriorException() {
		super("No hay disposiciones siguientes!");
		// TODO Auto-generated constructor stub
	}

	public NoHayFechaDisposicionAnteriorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NoHayFechaDisposicionAnteriorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoHayFechaDisposicionAnteriorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoHayFechaDisposicionAnteriorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
