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

	private Translator translator;

	private FileCharsetDetector detector;

	public FileCharsetTranslator() {
		detector = new FileCharsetDetector();
	}

	/**
	 * 转换GB编码的文件为UTF-8
	 * 
	 * @param file
	 *            被转换的文件
	 */
	public void translateToUTF8(File file) throws TranslateException {
		verifyFile(file);
		Charset charset = detector.charsetDetect(file);
		translator = Translator.getTranslator(Charsets.UTF8, charset);
		InputStream reader = null;
		OutputStream writer = null;
		File tmpFile = getTempFile(file);
		try {
			reader = createReader(file);
			writer = createWriter(tmpFile);
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
				copyTmpFile2FormerFile(file,tmpFile);
				reader.close();
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

	}

	/**
	 * 转换GB编码的文件为UTF-8
	 * 
	 * @param filename
	 *            被转换的文件的文件名
	 */
	public void translateGB2UTF8(String filename) throws TranslateException {
		translateToUTF8(new File(filename));
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

	private File getTempFile(File file) {
		return new File(System.getProperty("java.io.tmpdir") + File.separator
				+ file.getName().substring(0, file.getName().indexOf(".") + 1) + "bak");
	}
	/**
	 * 将临时文件拷贝为原有文件
	 */
	private void copyTmpFile2FormerFile(File formerFile , File tmpFile) throws TranslateException{
		InputStream reader = createReader(tmpFile);
		OutputStream writer = createWriter(new File(formerFile.getAbsolutePath()));
		
		byte[] buf = new byte[1024];
		try {
			while (reader.read(buf) != -1) {
				writeToDisk(writer, translator.tranlate(buf));
			}
		} catch (IOException e) {
			throw new TranslateException(e);
		}finally{
			
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
