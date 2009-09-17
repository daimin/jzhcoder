package net.hncu.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Gfzy implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		try {
			openURL("http://code.google.com/p/jzhcoder/");
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	public static void openURL(String url) throws RuntimeException, IOException {
		String urlx = null;
		urlx = "rundll32 url.dll,FileProtocolHandler " + url;

		Process p = Runtime.getRuntime().exec("" + urlx);
	}

}
