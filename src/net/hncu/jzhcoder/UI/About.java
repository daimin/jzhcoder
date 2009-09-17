package net.hncu.jzhcoder.UI;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class About extends JInternalFrame {
	private JTextPane jTextPane1;

	private static About instance = null;

	private About() {
		this.title = "关于该软件";
		this.isMaximum = true;
		this.closable = true;
		this.setBounds(0, 0, 780, 580);
		this.setVisible(true);
		getContentPane().setLayout(null);
		{
			jTextPane1 = new JTextPane();
			jTextPane1.setEditable(false);
			getContentPane().add(jTextPane1);
			jTextPane1
					.setText("\u8be5\u8f6f\u4ef6\u662f\u5927\u5b66\u751f\u521b\u65b0\u6027\u7814\u7a76\u8bfe\u9898\u7684\u6210\u679c\uff0c\n\n\u4e3b\u8981\u76ee\u7684\u662f\u4e3a\u4e86\u89e3\u51b3javaweb\u5f00\u53d1\u8fc7\u7a0b\u4e2d\n\n\u51fa\u73b0\u7684\u4e71\u7801\u95ee\u9898\uff0c\u8f6f\u4ef6\u8fd8\u5728\u5236\u4f5c\u5f53\u4e2d\uff0c\n\n\u6709\u4ec0\u4e48\u610f\u89c1\u5e0c\u671b\u60a8\u80fd\u591f\u8054\u7cfb\u6211\u4eec\uff0c\u611f\u8c22\u60a8\n\n\u7684\u5173\u6ce8\uff01\uff01\uff01\nQQ\uff1a156777383");
			jTextPane1.setBounds(17, 30, 600, 300);
		}
	}

	public static About getInstance() {
		if (instance == null) {
			instance = new About();
		}
		return instance;
	}

}
