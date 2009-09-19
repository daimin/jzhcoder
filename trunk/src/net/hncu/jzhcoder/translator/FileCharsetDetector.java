package net.hncu.jzhcoder.translator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import java.util.Arrays;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

/**
 * �ļ��ַ�����̽����
 * @author vagasnail
 * 
 * 2009-9-17 ����05:29:58
 */
public class FileCharsetDetector {
	// ��־�ַ����Ƿ��ҵ���Ĭ��Ϊfalse��
	public static boolean found = false;

	private Charset[] probableCharsets;

	private Charset mostProCharset = null;

	private File file;

	public FileCharsetDetector(String filename) {
		file = new File(filename);
	}

	public FileCharsetDetector(File file) {
		this.file = file;
	}

	/**
	 * ̽���ļ����ܵ��ַ���
	 * 
	 * @param filename
	 * @return
	 */
	public Charset charsetDetect(File file) {

		// ��ʼ��nsDetector
		nsDetector det = new nsDetector(nsPSMDetector.CHINESE);

		// ����һ���۲���...
		// ���Notify()������һ��ƥ����ַ������ҵ�ʱ�����á�

		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				FileCharsetDetector.found = true;
				mostProCharset = Charset.forName(charset);
				System.out.println("Most probable charset = " + charset);
			}
		});

		BufferedInputStream imp = null;
		try {
			imp = new BufferedInputStream(new FileInputStream(file));

			byte[] buf = new byte[1024];
			int len;
			boolean done = false;
			boolean isAscii = true;

			while ((len = imp.read(buf, 0, buf.length)) != -1) {

				// ����ֽ����Ƿ������ascii��
				if (isAscii)
					isAscii = det.isAscii(buf, len);

				// �������ascii���һ�û�е���done�����Ļ�������Doit������
				if (!isAscii && !done)
					done = det.DoIt(buf, len, false);
			}

			det.DataEnd();

			if (isAscii) {
				System.out.println("CHARSET = ASCII");
				probableCharsets = new Charset[] { Charsets.ASCII };
//				mostProCharset = Charsets.ASCII;
				found = true;

			}

			if (!found) {
				String prob[] = det.getProbableCharsets();
				internalSetCharsetArray(prob);
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				imp.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		mostProCharset = amendCharset(mostProCharset);
		return mostProCharset;
	}

	private void internalSetCharsetArray(String [] charsets){
		probableCharsets = new Charset[charsets.length];
		for(int i = 0 ; i < charsets.length; i++ ){
			probableCharsets[i] = Charset.forName(charsets[i]);
		}
	}
	
	public Charset charsetDetect() {
		return charsetDetect(this.file);
	}

	public Charset charsetDetect(String filename) {
		File newfile = new File(filename);
		return charsetDetect(newfile);
	}

	public static void main(String argv[]) throws Exception {
		FileCharsetDetector detector = new FileCharsetDetector(
				"D:\\test\\dd.txt");
		System.out.println("Final charset: " + detector.charsetDetect("D:\\test\\fd.txt"));
		System.out.print("properble charset = ");
		System.out.println(Arrays.toString(detector.getProbableCharsets()));
	}

	public Charset[] getProbableCharsets() {
		return probableCharsets;
	}
	
	/**
	 * ���Jchardet�������Ĵ��������
	 * jchardet�������Ĵ���ķ������Ǵ���BUG�������ܹ���ȷ��������ĳЩ���ĵ�UTF-8�ַ����ļ���
	 * ������������ȷ�ķ�����UTF-16LE�������ַ�����������ַ�����������windows-1252
     *
	 * @param charset
	 */
	public Charset amendCharset(Charset charset){
		if(charset == null){
			charset = getProbableCharsets()[0];
		}
		if(charset.displayName().startsWith("windows")){
			charset = Charsets.UTF16LE;
		}

		return charset;
	}

}
