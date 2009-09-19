package net.hncu.jzhcoder.translator;

import java.io.UnsupportedEncodingException;

/**
 * 将其它中文编码转换成UTF-8编码
 * 
 * @author vagasnail
 * 
 * 2009-9-17 上午09:50:30
 */
public class UTF8Translator extends Translator {
	/**
	 * 将Unicode码转换成UTF-8编码
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] unicode2Utf8Translate(byte[] unicodeBytes)
			throws UnsupportedEncodingException {
		return new String(unicodeBytes, Charsets.UTF16LE)
				.getBytes(Charsets.UTF8);
	}

	/**
	 * 将GB系编码字符转换成UTF-8编码字符
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] gb2Utf8Translate(byte[] gbBytes)
			throws UnsupportedEncodingException {
		return new String(gbBytes, Charsets.GB18030).getBytes(Charsets.UTF8);
	}
}
