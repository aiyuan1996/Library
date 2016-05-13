package com.yuan;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.ldap.ManageReferralControl;
import javax.net.ssl.ManagerFactoryParameters;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


public class Root extends JFrame{
	//创建结点数组
	private DefaultMutableTreeNode dmtnRoot = new DefaultMutableTreeNode(new NodeValue("图书馆管理系统"));
	private DefaultMutableTreeNode dmtn1 = 	 new DefaultMutableTreeNode(new NodeValue("学生用户管理"));
	private DefaultMutableTreeNode dmtn2 = 	 new DefaultMutableTreeNode(new NodeValue("图书管理"));
	private DefaultMutableTreeNode dmtn3 = 	 new DefaultMutableTreeNode(new NodeValue("查询图书"));
	private DefaultMutableTreeNode dmtn4 = 	 new DefaultMutableTreeNode(new NodeValue("借阅预约图书"));
	private DefaultMutableTreeNode dmtn5 = 	 new DefaultMutableTreeNode(new NodeValue("归还挂失图书"));
	private DefaultMutableTreeNode dmtn6 = 	 new DefaultMutableTreeNode(new NodeValue("交纳罚款"));
	private DefaultMutableTreeNode dmtn7 = 	 new DefaultMutableTreeNode(new NodeValue("管理员管理"));
	private DefaultMutableTreeNode dmtn8 = 	 new DefaultMutableTreeNode(new NodeValue("退出"));
	DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot);//创建根结点
	JTree jtree = new JTree(dtm);//创建包含dtm树模型的树对象
	JScrollPane jsp = new JScrollPane(jtree);//为JTree创建滚动条
	private JSplitPane jsplr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);//创建分割窗体对象
	private JPanel jP = new JPanel();//创建JPanel对象
	Image image = new ImageIcon("TSGL.JPG").getImage();//加载图片Image对象
	ImageIcon ii = new ImageIcon(image);//得到IMageIcon对象
	private JLabel jlRoot = new JLabel(ii);//创建JLable对象
	private  Manager manager;//登录管理员名
	String mgNo;//管理员ID
	CardLayout cl = new CardLayout();//创建卡片布局对象
	
	public Root(String mgNo) throws SQLException {
		this.mgNo = mgNo;//获得管理员ID
		manager = new Manager(mgNo);//创建管理员管理面板
		
		this.setManager();//设置管理员权限
		this.initJP();//初始化卡片布局面板 
		this.addTreeListener();//为树节点注册事件监听器
		dmtnRoot.add(dmtn1);
		dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);
		dmtnRoot.add(dmtn4);
		dmtnRoot.add(dmtn5);
		dmtnRoot.add(dmtn6);
		dmtnRoot.add(dmtn7);
		dmtnRoot.add(dmtn8);
		jtree.setEditable(false);//设置该树中结点是不可编辑的
		this.add(jsplr);//将包含树的分割窗口添加进窗体
		jsplr.setLeftComponent(jtree);//将包含树的滚动窗口添加进左边的子窗口
		jP.setBounds(200, 50, 600, 500);
		jsplr.setRightComponent(jP);//将jp添加进分割窗体的右边
		jsplr.setDividerLocation(200);//设置分隔条的初始位置
		jsplr.setDividerSize(4);//设置分隔条的宽度
		jlRoot.setFont(new Font("Conrier",Font.PLAIN,30));//设置标签的字体
		jlRoot.setHorizontalAlignment(JLabel.CENTER);//设置水平对齐方式
		jlRoot.setVerticalAlignment(JLabel.CENTER);//设置竖直对齐方式
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗体关闭动作
		Image image = new ImageIcon("ico.gif").getImage();//加载窗体图标对象
		this.setIconImage(image);//设置窗体图标
		this.setTitle("图书馆管理系统");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕尺寸
		int centerX = screenSize.width/2;
		int centerY = screenSize.height/2;
		int w = 800;
		int h = 600;
		this.setBounds(centerX - w/2, centerY - h/2, w, h);//设置窗体出现在屏幕中央
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);//窗体全屏
		this.setVisible(true);
		jtree.setShowsRootHandles(true);
	}

	private void addTreeListener() {
		// TODO Auto-generated method stub
		jtree.addTreeSelectionListener(new TreeSelectionListener() {//为树添加选择模型监听器
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {//当值变化时
				// TODO Auto-generated method stub
				DefaultMutableTreeNode cdmtn = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();//得到选中的结点对象
				NodeValue cnv = (NodeValue) cdmtn.getUserObject();//得到自定义结点对象
				if(cnv.value.equals("图书馆管理系统")){
					cl.show(jP, "root");//显示根节点信息
				}
				if(cnv.value.equals("学生用户管理")){
					cl.show(jP, "stu");
				}
				if(cnv.value.equals("图书管理")){
					cl.show(jP, "bm");
				}
				if(cnv.value.equals("查询图书")){
					cl.show(jP, "sb");
				}
				if(cnv.value.equals("借阅预约图书")){
					cl.show(jP, "bb");
				}
				if(cnv.value.equals("归还挂失图书")){
					cl.show(jP, "rb");
				}
				if(cnv.value.equals("缴纳罚款")){
					cl.show(jP, "et");
				}
				if(cnv.value.equals("管理员管理")){
					cl.show(jP, "Manager");
				}
				if(cnv.value.equals("退出")){
					int i = JOptionPane.showConfirmDialog(Root.this, "是否退出系统？", "消息",JOptionPane.YES_NO_OPTION);
					if(i == JOptionPane.YES_OPTION)
						System.exit(0);
				}
 			}
		});
	}

	private void initJP() {
		jP.setLayout(cl);//设置布局管理器为卡片布局
		jP.add(jlRoot,"root");//添加根结点显示信息
		jP.add(new Student(),"stu");//添加学生管理模块界面
		jP.add(new BookManage(),"bm");//添加图书管理模块界面
		jP.add(new SearchBook(),"sb");//添加查找图书管理界面
		jP.add(new BorrowBook(),"bb");//添加借阅预约图书模块界面
		jP.add(new ReturnBook(),"rb");//添加归还挂失图书界面
		jP.add(new Manager(mgNo),"Manager");//添加管理员管理模块界面
		jP.add(new ExceedTime(),"et");//添加罚款处理界面
		
	}

	

	private void setManager() throws SQLException {
		String str="";
		String sql="select permitted from manager where mgNo='"+mgNo+"'";//搜索管理员权限的SQL语句
		
		try {
			DataBase db = new DataBase();//创建数据库对象
			ResultSet rSet=db.selectDb(sql);//执行查询
			while(rSet.next()){
				 str = rSet.getString("permitted").trim();//得到管理员权限
			}
			
			if(str.equals("1")){
				manager.setFlag(false);//设置管理员权限
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	} 
	public static void main(String args[]) throws SQLException{
		new Root("1001");
	}

}

