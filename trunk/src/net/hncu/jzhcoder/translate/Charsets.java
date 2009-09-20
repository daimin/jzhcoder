package net.hncu.jzhcoder.translate;

import java.nio.charset.Charset;
/**
 * UNICODE�ļ������ݽṹ
 * ע�⣺���ﲢδ��¼UTF-32
 * ����UTF-16BE��UTF-16LE���ļ�������۲죺
 * ����ļ��Ŀ�ͷΪFFFEΪUTF-16LE��
 * ���ΪFEFFΪUTF-16BE��
 * @author vagasnail
 *
   2009-9-19   ����07:08:33
 */
public abstract class Charsets {

	public static Charset UTF16 = Charset.forName("UTF-16");
	
	public static Charset UTF16BE = Charset.forName("UTF-16BE");
	/**
	 * Windows��Linux��Ĭ��Unicode�ַ���
	 */
	public static Charset UTF16LE = Charset.forName("UTF-16LE");
	public static Charset UTF8 = Charset.forName("UTF-8");
	/**
	 * ���ڹ����ַ�����GB18030��ȫ����GBK��GBK��ȫ����GB2312
	 */
	public static Charset GB18030 = Charset.forName("GB18030");
	public static Charset GBK = Charset.forName("GBK");
	public static Charset GB2312 = Charset.forName("GB2312");
	
	public static Charset ASCII = Charset.forName("US-ASCII");
	

	
	

}
