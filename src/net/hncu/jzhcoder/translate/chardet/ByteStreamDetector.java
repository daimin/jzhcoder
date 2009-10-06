package net.hncu.jzhcoder.translate.chardet;

import java.nio.charset.Charset;
import java.util.Arrays;

import net.hncu.jzhcoder.translate.Charsets;
/**
 * 该类侦测十分不准确。
 * @author vagasnail
 *
   2009-9-27   下午09:14:44
 */
@Deprecated
public class ByteStreamDetector {
	public static boolean found = false;

	private static Charset probableCharset = Charsets.ISO8859_1;

	public static Charset detect(byte[] bytes) throws Exception {

		// Initalize the nsDetector() ;
		int lang = nsPSMDetector.SIMPLIFIED_CHINESE;
		nsDetector det = new nsDetector(lang);

		// Set an observer...
		// The Notify() will be called when a matching charset is found.

		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				ByteStreamDetector.found = true;
				System.out.println("REAL CHARSET = " + charset);
				probableCharset = Charsets.getSupportableCharset(charset);
			}
		});

		boolean done = false;
		boolean isAscii = true;

		// Check if the stream is only ascii.
		if (isAscii)
			isAscii = det.isAscii(bytes, bytes.length);

		// DoIt if non-ascii and not done yet.
		if (!isAscii && !done)
			done = det.DoIt(bytes, bytes.length, false);

		det.DataEnd();

		if (isAscii) {
			probableCharset = Charsets.ISO8859_1;
			System.out.println("CHARSET = ASCII");
			found = true;
		}

		if (!found) {
			String prob[] = det.getProbableCharsets();
			System.out.println(Arrays.toString(prob));
			probableCharset = Charsets.getSupportableCharset(prob[0]);
			// for(int i=0; i<prob.length; i++) {
			// System.out.println("Probable Charset = " + prob[i]);
			// }
		}

		System.out.println("probableCharset = " + probableCharset);
		return probableCharset;
	}

}
