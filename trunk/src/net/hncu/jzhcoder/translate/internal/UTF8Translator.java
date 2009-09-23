package net.hncu.jzhcoder.translate.internal;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * 将其它中文编码转换成UTF-8编码
 * 
 * @author vagasnail
 * 
 * 2009-9-17 上午09:50:30
 */
public class UTF8Translator extends Translator{
	UTF8Translator(){
		super();
	}
	/**
	 * 将GB系编码字符转换成UTF-8编码字符
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromGB(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.GB18030).getBytes(Charsets.UTF8);
	}
	/**
	 * UTF-8的自我转换是不必要的，所有不推荐使用。
	 */
	@Deprecated
	public byte[] translateFromUTF8(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF8).getBytes(Charsets.UTF8);
	}
	/**
	 * 将Unicode码转换成UTF-8编码
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromUnicode(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF16LE)
		.getBytes(Charsets.UTF8);
	}
	


}
