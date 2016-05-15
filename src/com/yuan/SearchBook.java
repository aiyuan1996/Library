package com.yuan;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

public class SearchBook extends JFrame implements ActionListener{
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
	private JRadioButton[] jrbArray = {new JRadioButton("简单查询",true),new JRadioButton("高级查询")};
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
	JTable jt = new JTable(dtm);//创建JTable对象
	JScrollPane jspn = new JScrollPane(jt);//将table添加到滚动窗口
	//上面代码主要是为查询图书界面的搭建创建标签，按钮，下拉列表，文本框，单选按钮等控件，同时在分割窗口创建表格模型，以便查询成功后将查询结果显示在表格里
	
	public SearchBook() {
		this.setLayout(new GridLayout(1,1));//网格布局
		jpt.setLayout(null);//上面布局为空
		jpb.setLayout(null);//下面布局为空
		
		jpt.add(jcb);//将下拉列表添加到上面板中
		jcb.setBounds(170, 20, 150, 20);
		jcb.addActionListener(this);
		
		jpt.add(jb);//将提交按钮添加到上面板中
		jb.setBounds(600,20,120,20);
		jb.addActionListener(this);
		
		//添加单选按钮到上面板中
		for(int i = 0;i < 2;i++){
			jrbArray[i].setBounds(20, 20+i*40, 100, 20);
			jpt.add(jrbArray[i]);
			jrbArray[i].addActionListener(this);
			bg.add(jrbArray[i]);//将单选按钮添加到按钮组里面
		}
		
		//添加标签,文本框到上面板中
		for(int i = 0;i < 3;i++){
			jlArray[i].setBounds(150+i*200,60,40,20);
			jtxtArray[i].setBounds(200+i*200, 60, 120, 20);
			jtxtArray[i].setEditable(false);//设置文本框为不可用
			jpt.add(jlArray[i]);
			jpt.add(jtxtArray[i]);
		}
		
		jtxtArray[3].setBounds(400, 20, 120, 20);
		jpt.add(jtxtArray[3]);
		
		JSp.setTopComponent(jpt);
		JSp.setBottomComponent(jspn);
		JSp.setDividerSize(4);//设置分隔条的大小
		this.add(JSp);
		
		this.setTitle("查询图书");
		JSp.setDividerLocation(100);
		this.setBounds(3, 10, 800, 500);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(jrbArray[0].isSelected()){//简单查询
			if(jcb.getSelectedIndex() >= 0&&jcb.getSelectedIndex() < 4){//当索引在0-3之间时
				jtxtArray[3].requestFocus();//输入文本框获得焦点
				if(e.getSource() == jb){//如果事件源为提交按钮，则执行检索
					String str = jtxtArray[3].getText().trim();
					if(str.equals("")){//查询条件为空
						JOptionPane.showMessageDialog(this, "请输入必要的信息", "消息", JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					if(jcb.getSelectedIndex() == 0){//根据书名进行查询
						sql = "select *from BOOK where BookName = '"+str+"'";
						jtxtArray[3].setText("");
					}
					else if(jcb.getSelectedIndex() == 1){//根据出版社进行查询
						sql = "select *from BOOK where publishment = '"+str+"'";
						jtxtArray[3].setText("");
					}
					else if(jcb.getSelectedIndex() == 2){//根据作者进行查询
						sql = "select *from BOOK where Author = '"+str+"'";
						jtxtArray[3].setText("");
					}
					else {
						sql = "select *from BOOK where BuyTime = '"+str+"'";
						jtxtArray[3].setText("");
					}
					try {
						db = new DataBase();
						try {
							sql = new String(sql.getBytes(),"gb2312");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					db.selectDb(sql);//执行查询
					
				}
			}
			
			Vector<Vector>vtemp = new Vector<Vector>();//创建变量，用于存放表格数据
			try {
				int flag = 0;
				while(db.resultset.next()){//取得结果集
					flag++;
					Vector<String> v = new Vector<String>();//创建变量，用于存放每一行的数据
					for(int i = 1; i <= 7;i++){
						String str1 = db.resultset.getString(i);
						str1 = new String(str1.getBytes(),"gb2312");
						v.add(str1);//添加到表格数据变量中
						
					}
					vtemp.add(v);//将各条记录添加到vtemp
				}
				if(flag==0){//查询失败，提示
					JOptionPane.showMessageDialog(this,"没有您要查找的内容！！！",
					                      "消息",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			dtm.setDataVector(vtemp, head);//更新table
			jt.updateUI();//提示表格已经更改
			jt.repaint();//重绘表格
			db.dbClose();//关闭数据库连接
		}
		else if (jrbArray[1].isSelected()){//高级查询
			jtxtArray[0].requestFocus();
			jtxtArray[3].setEditable(false);
			for(int i = 0; i < 3;i++){
				jtxtArray[i].setEditable(true);
			}
			if(e.getSource() == jb){
				int bz = this.seniorSearch();//得到标志位
				if(bz != 0)
					return ;
				try {
					db = new DataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				db.selectDb(sql);
				Vector<Vector> vtemp = new Vector<Vector>();//创建向量集合
				try {
					int flag = 0;//记录结果集的条数
					while(db.resultset.next()){
						flag++;
						Vector<String>v = new Vector<String>();
						for(int i = 1;i <= 7 ;i++){
							String str = db.resultset.getString(i);
							str = new String(str.getBytes(),"gb2312");
							v.add(str);
						}
						vtemp.add(v);//将各条记录添加到临时向量vtemp
					}
					if(flag == 0){
						JOptionPane.showMessageDialog(this, "没有您要查找的内容", "消息", JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
				dtm.setDataVector(vtemp, head);//设置表格数据
				jt.updateUI();
				jt.repaint();
				db.dbClose();
			}
		}
		
	}
	private int seniorSearch() {
		int flag = 0;
		String str0 = jtxtArray[0].getText().trim();
		String str1 = jtxtArray[1].getText().trim();
		String str2 = jtxtArray[2].getText().trim();
		if(str0.equals("")&&str1.equals("")&&str2.equals("")){
			JOptionPane.showMessageDialog(this, "请输入必要的信息", "消息", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if(((!str0.equals(""))&&(str1.equals(""))&&(str2.equals("")))
				||((str0.equals(""))&&(!str1.equals(""))&&(str2.equals("")))
				||((str0.equals(""))&&(str1.equals(""))&&(!str2.equals("")))){
			JOptionPane.showMessageDialog(this, "请使用简单查询", "消息", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if((!str0.equals(""))&&(!str1.equals(""))&&(str2.equals(""))){//书名和作者组合
			sql = "select *from book where bookname = '" + str0 + "' and author = '" + str1 + "'";
			jtxtArray[0].setText("");
			jtxtArray[1].setText("");
		}
		if((!str0.equals(""))&&(str1.equals(""))&&(!str2.equals(""))){//书名和出版社组合
			sql = "select *from book where bookname = '" + str0 + "' and publishment = '" + str2 + "'";
			jtxtArray[0].setText("");
			jtxtArray[2].setText("");
		}
		if((str0.equals(""))&&(!str1.equals(""))&&(!str2.equals(""))){//作者和出版社组合
			sql = "select *from book where author = '" + str1 + "' and publishment = '" + str2 + "'";
			jtxtArray[1].setText("");
			jtxtArray[2].setText("");
		}
		if((!str0.equals(""))&&(!str1.equals(""))&&(!str2.equals(""))){//书名、作者和出版社组合
			sql = "select *from book where bookname = '" + str0 + "' and author = '" + str1 + "' and publishment = '" + str2 + "'";
			jtxtArray[0].setText("");
			jtxtArray[1].setText("");
			jtxtArray[2].setText("");
		}
		return flag;
	}

	/*public static void main(String args[]){
		new SearchBook();
		
	}*/

}
