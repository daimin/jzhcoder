package net.hncu.jzhcoder.translate.internal;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * ������������ת����GB�롣 ��ΪGBϵ�б�����GB18030���������е�GBϵ���룬 ������������ֱ��ת����GB18030���롣
 * 
 * @author vagasnail
 * 
 * 2009-9-16 ����10:54:47
 */
public class GBTranslator extends Translator{

	GBTranslator(){
		super();
	}
	/**
	 * ��������GB����ͳһת��ΪGB18030����
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromGB(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.GB18030).getBytes(Charsets.GB18030);
	}
	/**
	 * ��UTF-8����ת����GBϵ�ַ�����
	 * @param bytes
	 * @return
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromUTF8(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF8).getBytes(Charsets.GB18030);
	}
	/**
	 * ��Unicode����ת��ΪGBϵ���ַ�����
	 * 
	 * @throws TranslateException
	 */
	@Override
	public byte[] translateFromUnicode(byte[] bytes) throws TranslateException {
		return new String(bytes, Charsets.UTF16LE)
		.getBytes(Charsets.GB18030);
	}


}
