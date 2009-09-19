package net.hncu.jzhcoder.translator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Arrays;

import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

/**
 * �ļ��ַ�����̽����
 * 
 * @author vagasnail
 * 
 * 2009-9-17 ����05:29:58
 */
public class FileCharsetDetector {
	// ��־�ַ����Ƿ��ҵ���Ĭ��Ϊfalse��
	public static boolean found = false;

	private String[] probableCharsets;

	private String mostProCharset = "";

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
	public String charsetDetect(File file) {

		// ��ʼ��nsDetector
		nsDetector det = new nsDetector(nsPSMDetector.ALL);

		// ����һ���۲���...
		// ���Notify()������һ��ƥ����ַ������ҵ�ʱ�����á�

		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				HtmlCharsetDetector.found = true;
				mostProCharset = charset;
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
				probableCharsets = new String[] { "ASCII" };
				mostProCharset = "ASCII";
				found = true;

			}

			if (!found) {
				String prob[] = det.getProbableCharsets();
				probableCharsets = prob;
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
		return mostProCharset;
	}

	public String charsetDetect() {
		return charsetDetect(this.file);
	}

	public String charsetDetect(String filename) {
		File newfile = new File(filename);
		return charsetDetect(newfile);
	}

	public static void main(String argv[]) throws Exception {
		FileCharsetDetector detector = new FileCharsetDetector(
				"D:\\test\\dd.txt");
		System.out.println(detector.charsetDetect("D:\\test\\gb_t.txt"));
		System.out.print("properble charset = ");
		System.out.println(Arrays.toString(detector.getProbableCharsets()));
	}

	public String[] getProbableCharsets() {
		return probableCharsets;
	}

}
