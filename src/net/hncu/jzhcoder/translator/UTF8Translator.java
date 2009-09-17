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
		return new String(unicodeBytes, Translator.UNICODE)
				.getBytes(Translator.UTF_8);
	}

	/**
	 * ��GBϵ�����ַ�ת����UTF-8�����ַ�
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public byte[] gb2Utf8Translate(byte[] gbBytes)
			throws UnsupportedEncodingException {
		return new String(gbBytes, Translator.GB).getBytes(Translator.UTF_8);
	}
}
