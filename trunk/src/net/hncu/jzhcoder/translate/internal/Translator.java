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

	Translator() {

	}

	public abstract byte[] translateFromGB(byte[] bytes)
			throws TranslateException;

	public abstract byte[] translateFromUnicode(byte[] bytes)
			throws TranslateException;

	public abstract byte[] translateFromUTF8(byte[] bytes)
			throws TranslateException;
	
	public  byte[] translateAscii(byte[] bytes){
		return bytes;
	};

	/**
	 * 根据指定的字符集得到相应的转换器
	 * 
	 * @param charset
	 *            指定的字符集
	 * @return 转换器
	 * @throws TranslateException
	 *             如果没找到指定编码的转换器;
	 */
	public static Translator getTranslator(Charset targetCharset) throws TranslateException {
		targetCharset = Charsets.getSupportableCharset(targetCharset);
		Translator translator = null;
		if (targetCharset.equals(Charsets.GB18030)
				|| targetCharset.equals(Charsets.GBK)
				|| targetCharset.equals(Charsets.GB2312)) {
			translator = new GBTranslator();
		} else if (targetCharset.equals(Charsets.UTF8)) {
			translator = new UTF8Translator();
		} else if (targetCharset.equals(Charsets.UTF16BE)
				|| targetCharset.equals(Charsets.UTF16LE)
				|| targetCharset.equals(Charsets.UTF16)) {
			translator = new UnicodeTranslator();
		} else if(targetCharset.equals(Charsets.ASCII)){
			translator = new DefaultTranslator();
		}else{
			throw new TranslateException("Cann't found a specify translator!");
		}
		if (translator != null) {
			return translator;
		}
		return translator;
	}

	/**
	 * 将给定的Charset的编码转换成本编译器的编码
	 * 
	 * @param charset
	 */
	public byte[] tranlate(Charset originalCharset, byte[] bytes) throws TranslateException {

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
		} else if(originalCharset.equals(Charsets.ASCII)){
			return translateAscii(bytes);
		} else{
			throw new TranslateException("Cann't specify original charset!");
		}
	}
}
