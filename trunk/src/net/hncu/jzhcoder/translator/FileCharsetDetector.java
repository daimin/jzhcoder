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
 * 文件字符集的探测器
 * 
 * @author vagasnail
 * 
 * 2009-9-17 下午05:29:58
 */
public class FileCharsetDetector {
	// 标志字符集是否被找到，默认为false。
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
	 * 探测文件可能的字符集
	 * 
	 * @param filename
	 * @return
	 */
	public String charsetDetect(File file) {

		// 初始化nsDetector
		nsDetector det = new nsDetector(nsPSMDetector.ALL);

		// 设置一个观察者...
		// 这个Notify()方法在一个匹配的字符集被找到时被调用。

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

				// 检查字节流是否仅仅是ascii。
				if (isAscii)
					isAscii = det.isAscii(buf, len);

				// 如果不是ascii并且还没有调用done方法的话，调用Doit方法，
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
