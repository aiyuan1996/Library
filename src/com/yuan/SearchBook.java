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
	int flag ;//声明标志位
	String sql;
	DataBase db;
	private JSplitPane JSp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);//分割为上下
	private JPanel jpt = new JPanel();//创建窗体上部JPanel对象
	private JPanel jpb = new JPanel();//创建窗体下部JPanel对象
	private String[] str = {"书名","出版社","作者","购买时间"};//创建下拉列表数据的字符串数组
	private JComboBox jcb = new JComboBox(str);//创建下啦列表
	private JButton jb = new JButton("提交");
	private JLabel[] jlArray = new JLabel[]{new JLabel("书名"),new JLabel("作者"),new JLabel("出版社")};//创建并初始化标签出租
	//创建并初始化文本框数组
	private JTextField[] jtxtArray = new JTextField[]{new JTextField(),new JTextField(),new JTextField(),new JTextField()};
	private JRadioButton[] jfbArray = {new JRadioButton("简单查询",true),new JRadioButton("高级查询")};
	private ButtonGroup bg = new ButtonGroup();//创建按钮组
	Vector<String> head = new Vector<String>();//创建表头向量
	{
		head.add("书号");head.add("书名");
		head.add("作者");head.add("出版社");
		head.add("购进时间");head.add("是否借阅");
		head.add("是否预约");
		
	}
	Vector<Vector> data = new Vector<Vector>();//存放检索出的书的信息
	DefaultTableModel dtm = new DefaultTableModel(data,head);//创建表格模型
	JTable jt = new JTable(dtm);
	JScrollPane jspn = new JScrollPane(jt);//将table添加到滚动窗口
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
