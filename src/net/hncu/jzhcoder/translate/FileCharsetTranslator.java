package net.hncu.jzhcoder.translate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import net.hncu.jzhcoder.translate.internal.FileCharsetDetector;
import net.hncu.jzhcoder.translate.internal.Translator;
import net.hncu.jzhcoder.utils.TranslateException;

/**
 * �ṩ�ļ�����ת����ת����
 * 
 * @author vagasnail
 * 
 * 2009-9-20 ����09:14:45
 */
public class FileCharsetTranslator {

	public static final int BUF_SIZE = 1024;

	private FileCharsetDetector detector;

	public FileCharsetTranslator() {
		detector = new FileCharsetDetector();
	}

	/**
	 * ת���ļ�ΪUTF-8����
	 * 
	 * @param file
	 *            ��ת�����ļ�
	 */
	public void translateToUTF8(File originalFile, File targetFile)
			throws TranslateException {
		Translator translator = Translator.getTranslator(Charsets.UTF8,
				readyForTranslate(originalFile, targetFile));
		internalTranslate(originalFile, targetFile, translator);

	}

	/**
	 * ת���ļ�ΪUTF-8����
	 * 
	 * @param filename
	 *            ��ת�����ļ����ļ���
	 */
	public void translateToUTF8(String originalFilename, String targetFilename,
			Charset targetCharset) throws TranslateException {
		translateToUTF8(new File(originalFilename), new File(targetFilename));
	}

	/**
	 * ת���ļ�ΪGB18030����
	 * 
	 * @param originalFile
	 * @param targetFile
	 * @throws TranslateException
	 */
	public void translateToGB(File originalFile, File targetFile)
			throws TranslateException {
		Translator translator = Translator.getTranslator(Charsets.GB18030,
				readyForTranslate(originalFile, targetFile));
		internalTranslate(originalFile, targetFile, translator);
	}

	/**
	 * ת���ļ�ΪUTF-8����
	 * 
	 * @param filename
	 *            ��ת�����ļ����ļ���
	 */
	public void translateToGB(String originalFilename, String targetFilename)
			throws TranslateException {
		translateToGB(new File(originalFilename), new File(targetFilename));
	}

	/**
	 * ת���ļ�ΪUTF-16LE����
	 * 
	 * @param originalFile
	 * @param targetFile
	 * @param targetCharset
	 * @throws TranslateException
	 */
	public void translateToUTF16(File originalFile, File targetFile)
			throws TranslateException {
		Translator translator = Translator.getTranslator(Charsets.UTF16LE,
				readyForTranslate(originalFile, targetFile));
		internalTranslate(originalFile, targetFile, translator);
	}

	/**
	 * ת���ļ�ΪUTF-16LE����
	 * 
	 * @param filename
	 *            ��ת�����ļ����ļ���
	 */
	public void translateToUTF16(String originalFilename, String targetFilename)
			throws TranslateException {
		translateToUTF16(new File(originalFilename), new File(targetFilename));
	}

	/**
	 * Ϊת��������׼��
	 * 
	 * @param originalFile
	 *            ԭʼ�ļ�
	 * @param targetFile
	 *            Ŀ���ļ�
	 * @return ����������ԭʼ�ļ���Charset
	 */
	private Charset readyForTranslate(File originalFile, File targetFile) {
		verifyFile(originalFile);
		verifyFile(targetFile);
		return detector.charsetDetect(originalFile);
	}

	/**
	 * �ڲ���ת������
	 * 
	 * @param originalFile
	 *            ԭʼ�ļ�
	 * @param targetFile
	 *            Ŀ���ļ�
	 * @param translator
	 *            ת����
	 */
	private void internalTranslate(File originalFile, File targetFile,
			Translator translator) {
		InputStream reader = null;
		OutputStream writer = null;
		try {
			reader = createReader(originalFile);
			writer = createWriter(targetFile);
			byte[] buf = new byte[BUF_SIZE];
			while (reader.read(buf) != -1) {
				writeToDisk(writer, translator.tranlate(buf));
			}
		} catch (FileNotFoundException e) {
			throw new TranslateException(e);
		} catch (IOException e) {
			throw new TranslateException(e);
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}
	}

	private void verifyFile(File file) throws TranslateException {
		if (file == null && !file.exists()) {
			throw new TranslateException();
		}
	}

	private OutputStream createWriter(File file) throws TranslateException {
		try {
			BufferedOutputStream writer = new BufferedOutputStream(
					new FileOutputStream(file));
			return writer;
		} catch (FileNotFoundException e) {
			throw new TranslateException(e);
		}
	}

	private InputStream createReader(File file) throws TranslateException {
		try {
			BufferedInputStream reader = new BufferedInputStream(
					new FileInputStream(file));
			return reader;
		} catch (FileNotFoundException e) {
			throw new TranslateException(e);
		}
	}

	private void writeToDisk(OutputStream writer, byte[] bytes)
			throws TranslateException {
		try {
			writer.write(bytes);
			writer.flush();
		} catch (IOException e) {
			throw new TranslateException(e);
		}
	}

}
