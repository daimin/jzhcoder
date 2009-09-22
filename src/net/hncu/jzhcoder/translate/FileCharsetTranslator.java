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
 * 提供文件编码转换的转换器
 * 
 * @author vagasnail
 * 
 * 2009-9-20 上午09:14:45
 */
public class FileCharsetTranslator {

	public static final int BUF_SIZE = 1024;

	private FileCharsetDetector detector;

	public FileCharsetTranslator() {
		detector = new FileCharsetDetector();
	}

	/**
	 * 转换文件为UTF-8编码
	 * 
	 * @param file
	 *            被转换的文件
	 */
	public void translateToUTF8(File originalFile, File targetFile)
			throws TranslateException {
		Translator translator = Translator.getTranslator(Charsets.UTF8,
				readyForTranslate(originalFile, targetFile));
		internalTranslate(originalFile, targetFile, translator);

	}

	/**
	 * 转换文件为UTF-8编码
	 * 
	 * @param filename
	 *            被转换的文件的文件名
	 */
	public void translateToUTF8(String originalFilename, String targetFilename,
			Charset targetCharset) throws TranslateException {
		translateToUTF8(new File(originalFilename), new File(targetFilename));
	}

	/**
	 * 转换文件为GB18030编码
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
	 * 转换文件为UTF-8编码
	 * 
	 * @param filename
	 *            被转换的文件的文件名
	 */
	public void translateToGB(String originalFilename, String targetFilename)
			throws TranslateException {
		translateToGB(new File(originalFilename), new File(targetFilename));
	}

	/**
	 * 转换文件为UTF-16LE编码
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
	 * 转换文件为UTF-16LE编码
	 * 
	 * @param filename
	 *            被转换的文件的文件名
	 */
	public void translateToUTF16(String originalFilename, String targetFilename)
			throws TranslateException {
		translateToUTF16(new File(originalFilename), new File(targetFilename));
	}

	/**
	 * 为转换所作的准备
	 * 
	 * @param originalFile
	 *            原始文件
	 * @param targetFile
	 *            目标文件
	 * @return 返回侦测出的原始文件的Charset
	 */
	private Charset readyForTranslate(File originalFile, File targetFile) {
		verifyFile(originalFile);
		verifyFile(targetFile);
		return detector.charsetDetect(originalFile);
	}

	/**
	 * 内部的转换代码
	 * 
	 * @param originalFile
	 *            原始文件
	 * @param targetFile
	 *            目标文件
	 * @param translator
	 *            转换器
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
