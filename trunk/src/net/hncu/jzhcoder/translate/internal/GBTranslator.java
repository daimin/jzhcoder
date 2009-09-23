package net.hncu.jzhcoder.translate.internal;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * 将其它中文码转换成GB码。 因为GB系列编码中GB18030包容了所有的GB系列码， 所以我这里是直接转换成GB18030编码。
 * 
 * @author vagasnail
 * 
 * 2009-9-16 下午10:54:47
 */
public class GBTranslator extends Translator{

	GBTranslator(){
		super();
	}
	/**
	 * 将其它的GB编码统一转换为GB18030编码
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromGB(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.GB18030).getBytes(Charsets.GB18030);
	}
	/**
	 * 将UTF-8编码转换成GB系字符编码
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromUTF8(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF8).getBytes(Charsets.GB18030);
	}
	/**
	 * 将Unicode编码转换为GB系列字符编码
	 * 
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromUnicode(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF16LE)
		.getBytes(Charsets.GB18030);
	}


}
