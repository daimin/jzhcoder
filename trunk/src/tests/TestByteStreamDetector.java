package tests;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import net.hncu.jzhcoder.translate.chardet.ByteStreamDetector;

import org.junit.Test;

public class TestByteStreamDetector {
	@Test
	public void testDetect() throws UnsupportedEncodingException, Exception{
//		String s = new String("����".getBytes(),"GBK");
		Charset charset = ByteStreamDetector.detect("�������Ƿ���������������ķ���������������ط������ķ�����fsdfsdfsd���߹�����ĸ�����ķ�����������ط�����ʦ�ķ������Ƿ�����˵�ķ����Ұ����ĸ��˸��˹������ķ���������fdsfd".getBytes("GB18030"));
		System.out.println(charset);
//		System.err.println(Charset.forName("UTF-16LE").displayName());
	}
}
