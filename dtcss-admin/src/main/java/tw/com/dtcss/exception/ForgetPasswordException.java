package tw.com.dtcss.exception;

public class ForgetPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ForgetPasswordException(String message) {
		super(message);
	}

}
