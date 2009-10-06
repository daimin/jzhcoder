/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1998
 * the Initial Developer. All Rights Reserved.
 *
 * This codes modify from jchardet.
 * 
 * jchardet is a java port of the source from mozilla's automatic charset detection algorithm. 
 * The original author is Frank Tang. What is available here is the java port of that code. 
 * The original source in C++ can be found from 
 * http://lxr.mozilla.org/mozilla/source/intl/chardet/ 
 * More information can be found at 
 * http://www.mozilla.org/projects/intl/chardet.html 
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package net.hncu.jzhcoder.translate.chardet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import net.hncu.jzhcoder.translate.Charsets;


/**
 * �ļ��ַ�����̽����
 * ע�⣺��������ΪISO8859-1��ȫ����ASCII��
 * ���������Ҿͽ�ASCII�滻Ϊ��ISO8858-1.
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
	
	public FileCharsetDetector(){
		
	}

	/**
	 * ̽���ļ����ܵ��ַ���
	 * 
	 * @param filename
	 * @return
	 */
	public Charset charsetDetect(File file) {

		// ��ʼ��nsDetector
		nsDetector det = new nsDetector(nsPSMDetector.SIMPLIFIED_CHINESE);

		// ����һ���۲���...
		// ���Notify()������һ��ƥ����ַ������ҵ�ʱ�����á�

		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				FileCharsetDetector.found = true;
				mostProCharset = Charset.forName(charset);
//				System.out.println("Most probable charset = " + charset);
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
//				System.out.println("CHARSET = ASCII");
				probableCharsets = new Charset[] { Charsets.ISO8859_1 };
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

//	public static void main(String argv[]) throws Exception {
//		FileCharsetDetector detector = new FileCharsetDetector(
//				"D:\\test\\dd.txt");
//		System.out.println("Final charset: " + detector.charsetDetect("D:\\test\\fd.txt"));
//		System.out.print("properble charset = ");
//		System.out.println(Arrays.toString(detector.getProbableCharsets()));
//	}

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
	private Charset amendCharset(Charset charset){
		if(charset == null){
			charset = getProbableCharsets()[0];
		}
		if(charset.displayName().startsWith("windows")){
			charset = Charsets.UTF16LE;
		}

		return charset;
	}

}
