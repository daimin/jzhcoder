package net.hncu.jzhcoder.translator;

import java.io.UnsupportedEncodingException;

/**
 * ���������ı���ת����UTF-8����
 * 
 * @author vagasnail
 * 
 * 2009-9-17 ����09:50:30
 */
public class UTF8Translator extends Translator {
	/**
	 * ��Unicode��ת����UTF-8����
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] unicode2Utf8Translate(byte[] unicodeBytes)
			throws UnsupportedEncodingException {
		return new String(unicodeBytes, Charsets.UTF16LE)
				.getBytes(Charsets.UTF8);
	}

	/**
	 * ��GBϵ�����ַ�ת����UTF-8�����ַ�
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] gb2Utf8Translate(byte[] gbBytes)
			throws UnsupportedEncodingException {
		return new String(gbBytes, Charsets.GB18030).getBytes(Charsets.UTF8);
	}
}
