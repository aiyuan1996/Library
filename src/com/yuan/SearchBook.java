package com.yuan;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class SearchBook extends JPanel implements ActionListener{
	int flag ;//������־λ
	String sql;
	DataBase db;
	private JSplitPane JSp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);//�ָ�Ϊ����
	private JPanel jpt = new JPanel();//���������ϲ�JPanel����
	private JPanel jpb = new JPanel();//���������²�JPanel����
	private String[] str = {"����","������","����","����ʱ��"};//���������б����ݵ��ַ�������
	private JComboBox jcb = new JComboBox(str);//���������б�
	private JButton jb = new JButton("�ύ");
	private JLabel[] jlArray = new JLabel[]{new JLabel("����"),new JLabel("����"),new JLabel("������")};//��������ʼ����ǩ����
	//��������ʼ���ı�������
	private JTextField[] jtxtArray = new JTextField[]{new JTextField(),new JTextField(),new JTextField(),new JTextField()};
	private JRadioButton[] jfbArray = {new JRadioButton("�򵥲�ѯ",true),new JRadioButton("�߼���ѯ")};
	private ButtonGroup bg = new ButtonGroup();//������ť��
	Vector<String> head = new Vector<String>();//������ͷ����
	{
		head.add("���");head.add("����");
		head.add("����");head.add("������");
		head.add("����ʱ��");head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
		
	}
	Vector<Vector> data = new Vector<Vector>();//��ż������������Ϣ
	DefaultTableModel dtm = new DefaultTableModel(data,head);//�������ģ��
	JTable jt = new JTable(dtm);
	JScrollPane jspn = new JScrollPane(jt);//��table��ӵ���������
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
