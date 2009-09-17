package net.hncu.UI;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
public class JzhcoderUI extends javax.swing.JFrame {
	private JTabbedPane jTabbedPane1;
	private JPanel jPanel1;
	private JLabel jLabel4;
	private JLabel jLabel8;
	private JPanel jPanel5;
	private JButton jButton17;
	private JButton jButton16;
	private JButton jButton15;
	private JPanel jPanel4;
	private JButton jButton14;
	private JButton jButton13;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JComboBox jComboBox5;
	private JComboBox jComboBox4;
	private JCheckBox jCheckBox2;
	private JCheckBox jCheckBox1;
	private JButton jButton12;
	private JButton jButton11;
	private JButton jButton10;
	private JButton jButton9;
	private JButton jButton8;
	private JButton jButton7;
	private JButton jButton6;
	private JButton jButton5;
	private JButton jButton4;
	private JTable jTable2;
	private JLabel jLabel5;
	private JPanel jPanel3;
	private JTable jTable1;
	private JButton jButton3;
	private JButton jButton2;
	private JButton jButton1;
	private JTextField jTextField1;
	private JComboBox jComboBox3;
	private JLabel jLabel3;
	private JComboBox jComboBox2;
	private JLabel jLabel2;
	private JComboBox jComboBox1;
	private JLabel jLabel1;
	private JPanel jPanel2;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JzhcoderUI inst = new JzhcoderUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public JzhcoderUI() {
		super();
		initGUI();
	}

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initGUI() {
		try {
			this.setResizable(false);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this
					.setTitle("java\u4e2d\u6587\u4e71\u7801\u5904\u7406\u8f6f\u4ef6 \u2605Jzhcoder\u2605");
			{
				jTabbedPane1 = new JTabbedPane();
				jTabbedPane1.setBorder(new BevelBorder(BevelBorder.RAISED));
				getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
				{
					jPanel1 = new JPanel();
					jTabbedPane1.addTab("页面转码", null, jPanel1, null);
					jPanel1.setLayout(null);
					{
						jLabel5 = new JLabel();
						jPanel1.add(jLabel5);
						jLabel5
								.setText("\u6587\u4ef6\u5217\u8868(\u6ce8\u610f\u5148\u5907\u4efd)");
						jLabel5.setBounds(17, 12, 176, 15);
					}
					{
						TableModel jTable2Model = new DefaultTableModel(
								new String[][] { { "这里对应文件的路径和文件名" } },
								new String[] { "Column 1" });
						jTable2 = new JTable();
						jPanel1.add(jTable2);
						jTable2.setModel(jTable2Model);
						jTable2.setBounds(12, 39, 622, 340);
					}
					{
						jButton4 = new JButton();
						jPanel1.add(jButton4);
						jButton4.setText("\u5f00\u59cb\u5904\u7406");
						jButton4.setBounds(664, 39, 93, 22);
					}
					{
						jButton5 = new JButton();
						jPanel1.add(jButton5);
						jButton5.setText("\u505c\u6b62\u5904\u7406");
						jButton5.setBounds(664, 73, 93, 22);
					}
					{
						jButton6 = new JButton();
						jPanel1.add(jButton6);
						jButton6.setText("\u6dfb\u52a0\u6587\u4ef6");
						jButton6.setBounds(664, 122, 93, 22);
					}
					{
						jButton7 = new JButton();
						jPanel1.add(jButton7);
						jButton7.setText("\u6dfb\u52a0\u76ee\u5f55");
						jButton7.setBounds(664, 165, 93, 22);
					}
					{
						jButton8 = new JButton();
						jPanel1.add(jButton8);
						jButton8.setText("\u79fb\u9664\u9009\u62e9");
						jButton8.setBounds(664, 204, 93, 22);
					}
					{
						jButton9 = new JButton();
						jPanel1.add(jButton9);
						jButton9.setText("\u53cd\u5411\u9009\u62e9");
						jButton9.setBounds(664, 246, 93, 22);
					}
					{
						jButton10 = new JButton();
						jPanel1.add(jButton10);
						jButton10.setText("\u5168\u90e8\u79fb\u9664");
						jButton10.setBounds(664, 280, 93, 22);
					}
					{
						jButton11 = new JButton();
						jPanel1.add(jButton11);
						jButton11.setText("\u5173\u4e8e");
						jButton11.setBounds(664, 321, 93, 22);
					}
					{
						jButton12 = new JButton();
						jPanel1.add(jButton12);
						jButton12.setText("\u9000\u51fa");
						jButton12.setBounds(664, 355, 93, 22);
					}
					{
						jCheckBox1 = new JCheckBox();
						jPanel1.add(jCheckBox1);
						jCheckBox1
								.setText("\u4fdd\u7559\u6587\u4ef6\u5907\u4efd");
						jCheckBox1.setBounds(23, 421, 120, 27);
					}
					{
						jCheckBox2 = new JCheckBox();
						jPanel1.add(jCheckBox2);
						jCheckBox2.setText("\u66ff\u6362\u5185\u5bb9");
						jCheckBox2.setBounds(23, 463, 90, 28);
					}
					{
						ComboBoxModel jComboBox4Model = new DefaultComboBoxModel(
								new String[] { "UTF-8", "GBK", "GB2312",
										"latin1" });
						jComboBox4 = new JComboBox();
						jPanel1.add(jComboBox4);
						jComboBox4.setModel(jComboBox4Model);
						jComboBox4.setBounds(211, 471, 128, 22);
					}
					{
						ComboBoxModel jComboBox5Model = new DefaultComboBoxModel(
								new String[] { "UTF-8", "GBK", "GB2312",
										"latin1" });
						jComboBox5 = new JComboBox();
						jPanel1.add(jComboBox5);
						jComboBox5.setModel(jComboBox5Model);
						jComboBox5.setBounds(420, 471, 117, 22);
					}
					{
						jLabel6 = new JLabel();
						jPanel1.add(jLabel6);
						jLabel6.setText("\u539f\u6587\u4ef6\u7f16\u7801");
						jLabel6.setBounds(211, 427, 116, 15);
					}
					{
						jLabel7 = new JLabel();
						jPanel1.add(jLabel7);
						jLabel7.setText("\u8f6c\u6362\u540e\u7f16\u7801");
						jLabel7.setBounds(429, 427, 88, 15);
					}
					{
						jButton13 = new JButton();
						jPanel1.add(jButton13);
						jButton13.setText("\u5907\u4efd");
						jButton13.setBounds(211, 8, 72, 22);
					}
					{
						jButton14 = new JButton();
						jPanel1.add(jButton14);
						jButton14.setText("\u8fd8\u539f\u5907\u4efd");
						jButton14.setBounds(319, 8, 101, 22);
					}
					{
						jLabel8 = new JLabel();
						jPanel1.add(jLabel8);
						jLabel8.setBounds(582, 449, 175, 66);
						jLabel8.setIcon(new ImageIcon(getClass()
								.getClassLoader().getResource(
										"res/zchcoder_logo.jpg")));
					}
				}
				{
					jPanel2 = new JPanel();
					jTabbedPane1.addTab("数据转码", null, jPanel2, null);
					jPanel2.setLayout(null);
					{
						jLabel1 = new JLabel();
						jPanel2.add(jLabel1);
						jLabel1.setText("\u6570\u636e\u5e93");
						jLabel1.setBounds(12, 23, 48, 15);
					}
					{
						ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(
								new String[] { "Oracle", "MySQL", "SQL Server",
										"DB2", "ACCESS" });
						jComboBox1 = new JComboBox();
						jPanel2.add(jComboBox1);
						jComboBox1.setModel(jComboBox1Model);
						jComboBox1.setBounds(91, 19, 125, 22);
					}
					{
						jLabel2 = new JLabel();
						jPanel2.add(jLabel2);
						jLabel2.setText("\u539f\u7f16\u7801");
						jLabel2.setBounds(255, 23, 53, 15);
					}
					{
						ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(
								new String[] { "UTF-8", "GBK", "GB2312",
										"latin1" });
						jComboBox2 = new JComboBox();
						jPanel2.add(jComboBox2);
						jComboBox2.setModel(jComboBox2Model);
						jComboBox2.setBounds(340, 19, 114, 22);
					}
					{
						jLabel3 = new JLabel();
						jPanel2.add(jLabel3);
						jLabel3.setText("\u73b0\u7f16\u7801");
						jLabel3.setBounds(501, 23, 52, 15);
					}
					{
						ComboBoxModel jComboBox3Model = new DefaultComboBoxModel(
								new String[] { "UTF-8", "GBK", "GB2312",
										"latin1" });
						jComboBox3 = new JComboBox();
						jPanel2.add(jComboBox3);
						jComboBox3.setModel(jComboBox3Model);
						jComboBox3.setBounds(583, 19, 134, 22);
					}
					{
						jTextField1 = new JTextField();
						jPanel2.add(jTextField1);
						jTextField1.setBounds(144, 102, 391, 22);
					}
					{
						jLabel4 = new JLabel();
						jPanel2.add(jLabel4);
						jLabel4.setText("\u6587\u4ef6\u8def\u5f84");
						jLabel4.setBounds(43, 106, 64, 15);
					}
					{
						jButton1 = new JButton();
						jPanel2.add(jButton1);
						jButton1.setText("\u9009\u62e9");
						jButton1.setBounds(567, 102, 68, 22);
					}
					{
						jButton2 = new JButton();
						jPanel2.add(jButton2);
						jButton2.setText("\u8f6c\u6362");
						jButton2.setBounds(583, 469, 71, 22);
					}
					{
						jButton3 = new JButton();
						jPanel2.add(jButton3);
						jButton3.setText("\u91cd\u7f6e");
						jButton3.setBounds(680, 469, 66, 22);
					}
					{
						TableModel jTable1Model = new DefaultTableModel(
								new String[][] { { "数据库状态，包括一些现有数据库，现在的编码等等" } },
								new String[] { "Column 1" });
						jTable1 = new JTable();
						jPanel2.add(jTable1);
						jTable1.setModel(jTable1Model);
						jTable1.setBounds(72, 208, 594, 234);
					}
				}
				{
					jPanel3 = new JPanel();
					jTabbedPane1.addTab("关于该软件", null, jPanel3, null);
					jPanel3.setLayout(null);
					{
						jPanel4 = new JPanel();
						AnchorLayout jPanel4Layout = new AnchorLayout();
						jPanel4.setLayout(jPanel4Layout);
						jPanel3.add(jPanel4);
						jPanel4.setBounds(0, 0, 783, 61);
						jPanel4.setBorder(BorderFactory
								.createEtchedBorder(BevelBorder.LOWERED));
						{
							jButton17 = new JButton();
							jButton17.addActionListener(new Gfzy());
							jPanel4.add(jButton17, new AnchorConstraint(336,
									511, 696, 386, AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL));
							jButton17.setText("\u9879\u76ee\u4e3b\u9875");
							jButton17.setPreferredSize(new java.awt.Dimension(
									98, 22));
						}
						{
							jButton16 = new JButton();
							jButton16.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									About about = About.getInstance();
									jPanel5.add(about.getInstance());
									try {
										about.setSelected(true);
									} catch (PropertyVetoException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}

							});
							jPanel4.add(jButton16, new AnchorConstraint(336,
									321, 696, 192, AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL));
							jButton16.setText("\u5173\u4e8e\u8be5\u8f6f\u4ef6");
							jButton16.setPreferredSize(new java.awt.Dimension(
									101, 22));
						}
						{
							jButton15 = new JButton();
							jPanel4.add(jButton15, new AnchorConstraint(336,
									146, 696, 27, AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL,
									AnchorConstraint.ANCHOR_REL));
							jButton15.setText("\u5f00\u53d1\u56e2\u961f");
							jButton15.setPreferredSize(new java.awt.Dimension(
									93, 22));
						}
					}
					{
						jPanel5 = new JPanel();
						BorderLayout jPanel5Layout = new BorderLayout();
						jPanel3.add(jPanel5);
						jPanel5.setBounds(0, 73, 783, 455);
						jPanel5
								.setBackground(new java.awt.Color(255, 255, 255));
						jPanel5.setLayout(jPanel5Layout);
					}
				}
			}
			pack();
			this.setSize(800, 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
