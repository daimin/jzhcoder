package net.hncu.jzhcoder.translator;

import java.nio.charset.Charset;

/**
 * 提供中文字符之间的转换。
 * 记住是中文字符。
 * @author vagasnail
 *
   2009-9-16   下午10:56:13
 */
public abstract class Translator {
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final Charset UNICODE = Charset.forName("UTF-16");
	public static final Charset GB = Charset.forName("GB18030");
}
