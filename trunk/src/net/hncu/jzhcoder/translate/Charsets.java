package net.hncu.jzhcoder.translate;

import java.nio.charset.Charset;
/**
 * UNICODE的集合数据结构
 * 注意：这里并未收录UTF-32
 * 关于UTF-16BE和UTF-16LE的文件区别请观察：
 * 如果文件的开头为FFFE为UTF-16LE，
 * 如果为FEFF为UTF-16BE。
 * @author vagasnail
 *
   2009-9-19   下午07:08:33
 */
public abstract class Charsets {

	public static Charset UTF16 = Charset.forName("UTF-16");
	
	public static Charset UTF16BE = Charset.forName("UTF-16BE");
	/**
	 * Windows和Linux的默认Unicode字符集
	 */
	public static Charset UTF16LE = Charset.forName("UTF-16LE");
	public static Charset UTF8 = Charset.forName("UTF-8");
	/**
	 * 关于国标字符集，GB18030完全包含GBK，GBK完全包含GB2312
	 */
	public static Charset GB18030 = Charset.forName("GB18030");
	public static Charset GBK = Charset.forName("GBK");
	public static Charset GB2312 = Charset.forName("GB2312");
	
	public static Charset ASCII = Charset.forName("US-ASCII");
	

	
	

}
