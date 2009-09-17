package net.hncu.jzhcoder.translator;

import java.io.UnsupportedEncodingException;

/**
 * 将GB系列中文字符转换成Unicode字符 但记住Unicode字符集包括几种不同的编码格式，
 * 一般UTF-16是标准Unicode编码，但是我们计算机特别是网络上最常用的 是UTF-8编码。
 * 这里我的思路是：先转换成Unicode码，然后再将其转换为UTF-8格式。
 * 
 * @author vagasnail
 * 
 * 2009-9-16 下午10:49:01
 */
public class UnicodeTranslator extends Translator {
	/**
	 * 传入GB字符编码，转换为Unicode编码
	 * 
	 * @param gbBytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] gb2UnicodeTranslate(byte[] gbBytes)
			throws UnsupportedEncodingException {
		return new String(gbBytes, Translator.GB).getBytes("UTF-16");
	}

	/**
	 * 传入UTF8字符编码，转换成Unicode字符编码。
	 * 
	 * @param utf8Bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] utf82UnicodeTranslate(byte[] utf8Bytes)
			throws UnsupportedEncodingException {
		return new String(utf8Bytes, Translator.UTF_8).getBytes("UTF-16");
	}

}
