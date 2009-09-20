package net.hncu.jzhcoder.utils;
/**
 * 转换中抛出的异常对象
 * @author vagasnail
 *
   2009-9-20   上午10:01:11
 */
public class TranslateException extends RuntimeException {

	private static final long serialVersionUID = -65786913056737275L;

	protected static String message = "Cann't to translate";
	
	public TranslateException(){
		super(message);
	}
	
	public TranslateException(String message){
		super(message);
		TranslateException.message = message;
	}
	
	public TranslateException(Throwable exception){
		super(exception);
	}
	
}
