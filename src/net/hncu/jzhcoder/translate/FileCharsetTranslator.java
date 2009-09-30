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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.hncu.jzhcoder.translate.chardet.FileCharsetDetector;
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
	
	private static Log log = LogFactory.getLog(FileCharsetTranslator.class);
	
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
	public void translateToUTF8(File originalFile, File targetDir)
			throws TranslateException {
		log.info("Translate to utf-8");
		readyForTranslate(targetDir);
		Translator translator = Translator.getTranslator(Charsets.UTF8);
		internalTranslate(originalFile, targetDir, translator);

	}

	/**
	 * ת���ļ�ΪUTF-8����
	 * 
	 * @param filename
	 *            ��ת�����ļ����ļ���
	 */
	public void translateToUTF8(String originalFilename, String targetDirname,
			Charset targetCharset) throws TranslateException {
		translateToUTF8(new File(originalFilename), new File(targetDirname));
	}

	/**
	 * ת���ļ�ΪGB18030����
	 * 
	 * @param originalFile
	 * @param targetFile
	 * @throws TranslateException
	 */
	public void translateToGB(File originalFile, File targetDir)
			throws TranslateException {
		log.info("Translate to gb18030");
		readyForTranslate(targetDir);
		Translator translator = Translator.getTranslator(Charsets.GB18030);
		internalTranslate(originalFile, targetDir, translator);
	}

	/**
	 * ת���ļ�ΪUTF-8����
	 * 
	 * @param filename
	 *            ��ת�����ļ����ļ���
	 */
	public void translateToGB(String originalFilename, String targetDirname)
			throws TranslateException {
		translateToGB(new File(originalFilename), new File(targetDirname));
	}

	/**
	 * ת���ļ�ΪUTF-16LE����
	 * 
	 * @param originalFile
	 * @param targetFile
	 * @throws TranslateException
	 */
	public void translateToUTF16(File originalFile, File targetDir)
			throws TranslateException {
		log.info("Translate to standard unicode utf-16le");
		readyForTranslate(targetDir);
		Translator translator = Translator.getTranslator(Charsets.UTF16LE);
		internalTranslate(originalFile, targetDir, translator);
	}

	/**
	 * ת���ļ�ΪUTF-16LE����
	 * 
	 * @param filename
	 *            ��ת�����ļ����ļ���
	 */
	public void translateToUTF16(String originalFilename, String targetDirname)
			throws TranslateException {
		translateToUTF16(new File(originalFilename), new File(targetDirname));
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
	private void readyForTranslate(File targetDir) throws TranslateException {
		if (!targetDir.exists()) {
			targetDir.mkdir();
		}
		if (targetDir.isFile()) {
			log.error("Target directory must be a directory!");
			throw new TranslateException("Target directory must be a directory!");
		}

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
	private void internalTranslate(File originalFile, File targetDir,
			Translator translator) throws TranslateException {

		if (originalFile == null && !originalFile.exists()) {
			log.fatal("original file don't exists!");
			throw new TranslateException("original file don't exists!");
		}
		if (originalFile.isDirectory()) {
			File[] files = originalFile.listFiles();
			if (files == null || files.length < 1) {
				return;
			}
			for (File f : files) {
				internalTranslate(f, targetDir, translator);
			}
			return;
		}
		fileTranslate(originalFile,targetDir,translator);

	}
	/*
	 * �ļ��ı����ת��
	 */
	private void fileTranslate(File originalFile,File targetDir,Translator translator){
		InputStream reader = null;
		OutputStream writer = null;
		File targetFile = new File(targetDir.getAbsolutePath() + File.separator
				+ originalFile.getName());
		try {
			reader = createReader(originalFile);
			writer = createWriter(targetFile);
			byte[] buf = new byte[BUF_SIZE];
			while (reader.read(buf) != -1) {
				writeToDisk(writer, translator.tranlate(Charsets
						.getSupportableCharset(detector
								.charsetDetect(originalFile)), buf));
			}
		} catch (FileNotFoundException e) {
			log.fatal("original file don't exists!");
			throw new TranslateException(e);
		} catch (IOException e) {
			log.fatal(e);
			throw new TranslateException(e);
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				log.fatal(e);
				throw new RuntimeException(e);
			}

		}
	}

	private OutputStream createWriter(File file) throws TranslateException {
		try {
			BufferedOutputStream writer = new BufferedOutputStream(
					new FileOutputStream(file));
			return writer;
		} catch (FileNotFoundException e) {
			log.fatal(e);
			throw new TranslateException(e);
		}
	}

	private InputStream createReader(File file) throws TranslateException {
		try {
			BufferedInputStream reader = new BufferedInputStream(
					new FileInputStream(file));
			return reader;
		} catch (FileNotFoundException e) {
			log.fatal(e);
			throw new TranslateException(e);
		}
	}

	private void writeToDisk(OutputStream writer, byte[] bytes)
			throws TranslateException {
		try {
			writer.write(bytes);
			writer.flush();
		} catch (IOException e) {
			log.fatal(e);
			throw new TranslateException(e);
		}
	}

}
