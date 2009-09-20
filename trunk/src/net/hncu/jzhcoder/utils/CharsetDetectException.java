package net.hncu.jzhcoder.utils;

/**
 * 探测字符集时发生的异常
 * 
 * @author vagasnail
 * 
 * 2009-9-20 上午10:51:20
 */
public class CharsetDetectException extends TranslateException {

	private static final long serialVersionUID = -7082510667604618890L;

	public CharsetDetectException() {
		super("Cann't detect charset!");
	}

	public CharsetDetectException(String messge) {
		super(messge);
		TranslateException.message = messge;
	}
	
	public CharsetDetectException(Throwable exception){
		super(exception);
	}
}
