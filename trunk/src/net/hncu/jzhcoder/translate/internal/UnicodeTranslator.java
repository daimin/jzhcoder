package net.hncu.jzhcoder.translate.internal;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * ��GBϵ�������ַ�ת����Unicode�ַ� ����סUnicode�ַ����������ֲ�ͬ�ı����ʽ��
 * һ��UTF-16�Ǳ�׼Unicode���룬�������Ǽ�����ر�����������õ� ��UTF-8���롣
 * ��ΪWindows��Linux����ϵͳ����ʹ�õ�UTF-16LE��Unicode���룬
 * �������������Unicode��ת�붼��ת����UTF-16LE��
 * �����ҵ�˼·�ǣ���ת����Unicode�룬Ȼ���ٽ���ת��ΪUTF-8��ʽ��
 * 
 * @author vagasnail
 * 
 * 2009-9-16 ����10:49:01
 */
public class UnicodeTranslator extends Translator{
	
	UnicodeTranslator(){
		super();
	}
	
	/**
	 * ����GB�ַ����룬ת��ΪUnicode����
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
	 * ����UTF8�ַ����룬ת����UTF-16LE�ַ����롣
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
	 * ����UTF-16BE�ַ����룬ת����UTF-16LE�ַ����롣
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
