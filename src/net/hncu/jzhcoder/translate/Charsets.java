package net.hncu.jzhcoder.translate;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.hncu.jzhcoder.utils.TranslateException;
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
	private static Log log = LogFactory.getLog(Charsets.class);

	private static Map<String,Charset> supportableCharsets ;
	
	public  final static Charset UTF16 = Charset.forName("UTF-16");
	
	public final static Charset UTF16BE = Charset.forName("UTF-16BE");
	/**
	 * Windows和Linux的默认Unicode字符集
	 */
	public final static Charset UTF16LE = Charset.forName("UTF-16LE");
	public final static Charset UTF8 = Charset.forName("UTF-8");
	/**
	 * 关于国标字符集，GB18030完全包含GBK，GBK完全包含GB2312
	 */
	public final static Charset GB18030 = Charset.forName("GB18030");
	public final static Charset GBK = Charset.forName("GBK");
	public final static Charset GB2312 = Charset.forName("GB2312");
	
	public final static Charset ISO8859_1 = Charset.forName("ISO-8859-1");
	
	public static Charset getSupportableCharset(String charset)throws TranslateException{
		Charset resultCharset = getSupportableCharsets().get(charset);
		if(resultCharset == null){
			log.error("Not a supportable charset!");
			throw new TranslateException("Not a supportable charset!");
		}
		return resultCharset;
	}
	
	public static Charset getSupportableCharset(Charset charset) throws TranslateException{
		return getSupportableCharset(charset.displayName());
	}
	
	public static Map<String,Charset> getSupportableCharsets(){
		if(supportableCharsets == null){
			supportableCharsets = new HashMap<String, Charset>();
			supportableCharsets.put(UTF16.displayName(), UTF16);
			supportableCharsets.put(UTF16BE.displayName(), UTF16BE);
			supportableCharsets.put(UTF16LE.displayName(), UTF16LE);
			supportableCharsets.put(UTF8.displayName(), UTF8);
			supportableCharsets.put(GB18030.displayName(), GB18030);
			supportableCharsets.put(GBK.displayName(), GBK);
			supportableCharsets.put(GB2312.displayName(), GB2312);
			supportableCharsets.put(ISO8859_1.displayName(), ISO8859_1);
		}
		return Collections.unmodifiableMap(supportableCharsets);
		
	}

}
