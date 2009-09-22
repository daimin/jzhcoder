package net.hncu.jzhcoder.translate.internal;

import java.nio.charset.Charset;

import net.hncu.jzhcoder.translate.Charsets;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * ת�����Ľӿ�
 * 
 * @author vagasnail
 * 
 * 2009-9-20 ����10:09:00
 */
public abstract class Translator {

	protected Charset originalCharset = null;

	Translator() {

	}

	public abstract byte[] translateFromGB(byte[] bytes)
			throws TranslateException;

	public abstract byte[] translateFromUnicode(byte[] bytes)
			throws TranslateException;

	public abstract byte[] translateFromUTF8(byte[] bytes)
			throws TranslateException;

	/**
	 * ����ָ�����ַ����õ���Ӧ��ת����
	 * 
	 * @param charset
	 *            ָ�����ַ���
	 * @param originalCharset
	 *            �ļ���ԭʼ�ַ���
	 * @return ת����
	 * @throws TranslateException
	 *             ���û�ҵ�ָ�������ת����;
	 */
	public static Translator getTranslator(Charset targetCharset,
			Charset originalCharset) throws TranslateException {
		targetCharset = Charsets.getSupportableCharset(targetCharset);
		originalCharset = Charsets.getSupportableCharset(originalCharset);
		Translator translator = null;
		if (targetCharset.equals(Charsets.GB18030)
				|| targetCharset.equals(Charsets.GBK)
				|| targetCharset.equals(Charsets.GB2312)) {
			translator = new GBTranslator();
		} else if (targetCharset.equals(Charsets.UTF8)) {
			translator = new UTF8Translator();
		} else if (targetCharset.equals(Charsets.UTF16BE)
				|| targetCharset.equals(Charsets.UTF16LE)
				|| targetCharset.equals(Charsets.UTF16)) {
			translator = new UnicodeTranslator();
		} else {
			throw new TranslateException("Cann't found a specify translator!");
		}
		if (translator != null) {
			translator.originalCharset = originalCharset;
			return translator;
		}
		return translator;
	}

	/**
	 * ��������Charset�ı���ת���ɱ��������ı���
	 * 
	 * @param charset
	 */
	public byte[] tranlate(byte[] bytes) throws TranslateException {
		if (originalCharset == null) {
			throw new TranslateException();
		}
		if (originalCharset.equals(Charsets.GB18030)
				|| originalCharset.equals(Charsets.GBK)
				|| originalCharset.equals(Charsets.GB2312)) {
			return translateFromGB(bytes);
		} else if (originalCharset.equals(Charsets.UTF8)) {
			return translateFromUTF8(bytes);
		} else if (originalCharset.equals(Charsets.UTF16BE)
				|| originalCharset.equals(Charsets.UTF16LE)
				|| originalCharset.equals(Charsets.UTF16)) {
			return translateFromUnicode(bytes);
		} else {
			throw new TranslateException("Cann't specify original charset!");
		}
	}
}
