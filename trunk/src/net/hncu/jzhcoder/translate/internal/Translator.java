package net.hncu.jzhcoder.translate.internal;

import java.nio.charset.Charset;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * 转换器的接口
 * 
 * @author vagasnail
 * 
 * 2009-9-20 上午10:09:00
 */
public abstract class Translator {

	protected Charset originalCharset = null;

	Translator() {

	}

	public abstract byte[] translateFromGB(byte[] bytes)
			throws TranslateException;

	public abstract byte[] translateFromUnicode(byte[] bytes)
			throws TranslateException;

	public abstract byte[] translateFromUTF8(byte[] bytes)
			throws TranslateException;

	/**
	 * 根据指定的字符集得到相应的转换器
	 * 
	 * @param charset
	 *            指定的字符集
	 * @param originalCharset
	 *            文件的原始字符集
	 * @return 转换器
	 * @throws TranslateException
	 *             如果没找到指定编码的转换器;
	 */
	public static Translator getTranslator(Charset charset,
			Charset originalCharset) throws TranslateException {
		Translator translator = null;
		if (charset.equals(Charsets.GB18030) || charset.equals(Charsets.GBK)
				|| charset.equals(Charsets.GB2312)) {
			translator = new GBTranslator();
		} else if (charset.equals(Charsets.UTF8)) {
			translator = new UTF8Translator();
		} else if (charset.equals(Charsets.UTF16BE)
				|| charset.equals(Charsets.UTF16LE)
				|| charset.equals(Charsets.UTF16)) {
			translator = new UnicodeTranslator();
		} else {
			throw new TranslateException("Cann't found a specify translator!");
		}
		if (translator != null) {
			translator.originalCharset = originalCharset;
			return translator;
		}
		return translator;
	}

	/**
	 * 将给定的Charset的编码转换成本编译器的编码
	 * 
	 * @param charset
	 */
	public byte[] tranlate(byte[] bytes) throws TranslateException {
		if (originalCharset == null) {
			throw new TranslateException();
		}
		if (originalCharset.equals(Charsets.GB18030)
				|| originalCharset.equals(Charsets.GBK)
				|| originalCharset.equals(Charsets.GB2312)) {
			return translateFromGB(bytes);
		} else if (originalCharset.equals(Charsets.UTF8)) {
			return translateFromUTF8(bytes);
		} else if (originalCharset.equals(Charsets.UTF16BE)
				|| originalCharset.equals(Charsets.UTF16LE)
				|| originalCharset.equals(Charsets.UTF16)) {
			return translateFromUnicode(bytes);
		} else {
			throw new TranslateException("Cann't specify original charset!");
		}
	}
}
