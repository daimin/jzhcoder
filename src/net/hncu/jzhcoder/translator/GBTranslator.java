package net.hncu.jzhcoder.translator;

import java.io.UnsupportedEncodingException;

/**
 * ������������ת����GB�롣 ��ΪGBϵ�б�����GB18030���������е�GBϵ���룬 ������������ֱ��ת����GB18030���롣
 * 
 * @author vagasnail
 * 
 * 2009-9-16 ����10:54:47
 */
public class GBTranslator extends Translator {
	/**
	 * ��Unicode����ת��ΪGBϵ���ַ�����
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] unicode2GBTranslate(byte[] unicodeBytes)
			throws UnsupportedEncodingException {
		return new String(unicodeBytes, Charsets.UTF16LE)
				.getBytes(Charsets.GB18030);
	}

	/**
	 * ��UTF-8����ת����GBϵ�ַ�����
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] utf82GBTranslate(byte[] utf8Bytes)
			throws UnsupportedEncodingException {
		return new String(utf8Bytes, Charsets.UTF8).getBytes(Charsets.GB18030);
	}

}
