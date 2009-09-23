package net.hncu.jzhcoder.translate.internal;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * ���������ı���ת����UTF-8����
 * 
 * @author vagasnail
 * 
 * 2009-9-17 ����09:50:30
 */
public class UTF8Translator extends Translator{
	UTF8Translator(){
		super();
	}
	/**
	 * ��GBϵ�����ַ�ת����UTF-8�����ַ�
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromGB(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.GB18030).getBytes(Charsets.UTF8);
	}
	/**
	 * UTF-8������ת���ǲ���Ҫ�ģ����в��Ƽ�ʹ�á�
	 */
	@Deprecated
	public byte[] translateFromUTF8(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF8).getBytes(Charsets.UTF8);
	}
	/**
	 * ��Unicode��ת����UTF-8����
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
