package net.hncu.jzhcoder.utils;
/**
 * ת�����׳����쳣����
 * @author vagasnail
 *
   2009-9-20   ����10:01:11
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
