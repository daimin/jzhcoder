package net.hncu.jzhcoder.translator;

import java.io.UnsupportedEncodingException;

/**
 * ��GBϵ�������ַ�ת����Unicode�ַ� ����סUnicode�ַ����������ֲ�ͬ�ı����ʽ��
 * һ��UTF-16�Ǳ�׼Unicode���룬�������Ǽ�����ر�����������õ� ��UTF-8���롣
 * �����ҵ�˼·�ǣ���ת����Unicode�룬Ȼ���ٽ���ת��ΪUTF-8��ʽ��
 * 
 * @author vagasnail
 * 
 * 2009-9-16 ����10:49:01
 */
public class UnicodeTranslator extends Translator {
	/**
	 * ����GB�ַ����룬ת��ΪUnicode����
	 * 
	 * @param gbBytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] gb2UnicodeTranslate(byte[] gbBytes)
			throws UnsupportedEncodingException {
		return new String(gbBytes, Translator.GB).getBytes("UTF-16");
	}

	/**
	 * ����UTF8�ַ����룬ת����Unicode�ַ����롣
	 * 
	 * @param utf8Bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] utf82UnicodeTranslate(byte[] utf8Bytes)
			throws UnsupportedEncodingException {
		return new String(utf8Bytes, Translator.UTF_8).getBytes("UTF-16");
	}

}
