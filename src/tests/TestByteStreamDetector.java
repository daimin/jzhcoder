package tests;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import net.hncu.jzhcoder.translate.chardet.ByteStreamDetector;

import org.junit.Test;

public class TestByteStreamDetector {
	@Test
	public void testDetect() throws UnsupportedEncodingException, Exception{
//		String s = new String("哈哈".getBytes(),"GBK");
		Charset charset = ByteStreamDetector.detect("哈哈我是发生发生大幅发生的发生大幅撒旦发生地方撒旦的发生的fsdfsdfsd告诉过舒服的歌飞洒的发生大幅发生地发生大法师的发生但是发生的说的法萨芬爱国的个人个人过法国的法国发生的fdsfd".getBytes("GB18030"));
		System.out.println(charset);
//		System.err.println(Charset.forName("UTF-16LE").displayName());
	}
}
