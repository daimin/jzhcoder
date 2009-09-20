package net.hncu.jzhcoder.translate.internal;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * 将GB系列中文字符转换成Unicode字符 但记住Unicode字符集包括几种不同的编码格式，
 * 一般UTF-16是标准Unicode编码，但是我们计算机特别是网络上最常用的 是UTF-8编码。
 * 因为Windows和Linux操作系统都是使用的UTF-16LE的Unicode编码，
 * 所有我这里对于Unicode的转码都是转换成UTF-16LE。
 * 这里我的思路是：先转换成Unicode码，然后再将其转换为UTF-8格式。
 * 
 * @author vagasnail
 * 
 * 2009-9-16 下午10:49:01
 */
public class UnicodeTranslator extends Translator{
	
	UnicodeTranslator(){
		super();
	}
	
	/**
	 * 传入GB字符编码，转换为Unicode编码
	 * 
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromGB(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.GB18030).getBytes(Charsets.UTF16LE);
	}
	
	/**
	 * 传入UTF8字符编码，转换成UTF-16LE字符编码。
	 * 
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromUTF8(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF8).getBytes(Charsets.UTF16LE);
	}
	/**
	 * 传入UTF-16BE字符编码，转换成UTF-16LE字符编码。
	 * 
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromUnicode(byte[] bytes) throws TranslateException {
		return new String(bytes,Charsets.UTF16BE).getBytes(Charsets.UTF16LE);
	}

}
