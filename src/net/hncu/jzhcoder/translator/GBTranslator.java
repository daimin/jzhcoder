package net.hncu.jzhcoder.translator;

import java.io.UnsupportedEncodingException;

/**
 * 将其它中文码转换成GB码。 因为GB系列编码中GB18030包容了所有的GB系列码， 所以我这里是直接转换成GB18030编码。
 * 
 * @author vagasnail
 * 
 * 2009-9-16 下午10:54:47
 */
public class GBTranslator extends Translator {
	/**
	 * 将Unicode编码转换为GB系列字符编码
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] unicode2GBTranslate(byte[] unicodeBytes)
			throws UnsupportedEncodingException {
		return new String(unicodeBytes, Translator.UNICODE)
				.getBytes(Translator.GB);
	}

	/**
	 * 将UTF-8编码转换成GB系字符编码
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] utf82GBTranslate(byte[] utf8Bytes)
			throws UnsupportedEncodingException {
		return new String(utf8Bytes, Translator.UTF_8).getBytes(Translator.GB);
	}

}
