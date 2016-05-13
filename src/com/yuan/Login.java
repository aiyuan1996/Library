package com.yuan;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import java.net.PasswordAuthentication;
import java.sql.SQLException;

import javax.swing.*;


public class Login extends JFrame implements ActionListener{
	private JPanel jp = new JPanel();//创建JPanel对象
	private JLabel[] jlArray = {new JLabel("用户IP"),new JLabel("端口号"),new JLabel("用户名"),new JLabel("密码"),new JLabel("")};//创建标签组
	private JButton[] jbArray = {new JButton("学生登录"),new JButton("清空"),new JButton("管理员登陆")};//创建按钮数组
	private JTextField[] jtxtArray = {new JTextField("127.0.0.1"),new JTextField("3306"),new JTextField("")};//初始化文本框数组
	private JPasswordField jpassword = new JPasswordField("");//创建密码框
	String sql;//用于存放sql语句
	DataBase db;//声明数据库类引用
	
	public Login() {
		jp.setLayout(null);//设置JPanel的布局管理器
		//标签
		for(int i = 0;i < 4;i++){//对标签循环控制
			jlArray[i].setBounds(30,20+i*50,80,25);//设置标签的大小和位置
			jp.add(jlArray[i]);//将标签添加到JPanel容器中
		}
		//按钮
		for(int i = 0;i < 3;i++){
			jbArray[i].setBounds(10+ i*120,230,	 100, 25);//设置按钮大小,位置
			jp.add(jbArray[i]);//将按钮添加到面板中
			jbArray[i].addActionListener(this);//注册动作事件监听器
		}
		//文本框
		for(int i = 0;i < 3;i++){
			jtxtArray[i].setBounds(80, 20+50*i, 180, 25);//设置文本框大小和位置
			jp.add(jtxtArray[i]);//将文本框添加到面板中
			jtxtArray[i].addActionListener(this);//注册动作事件监听器	
		}
		jpassword.setBounds(80, 170, 180, 25);//设置密码框的大小，位置
		jp.add(jpassword);//将密码框添加到JPanel容器
		jpassword.setEchoChar('*');//设置密码框的回显字符
		jpassword.addActionListener(this);//为密码框注册监听器
		jlArray[4].setBounds(10, 280, 300, 25);//设置用于显示登录状态的大小位置
		jp.add(jlArray[4]);//将标签添加进JPanel容器
		this.add(jp);//将面板添加到窗体中
		Image image = new ImageIcon("ICO.GIF").getImage();//对logo图片进行初始化
		this.setIconImage(image);//设置图标
		this.setTitle("登录");//设置窗口标题
		this.setResizable(false);//使最大化按钮不可用
		this.setBounds(100, 100, 400, 350);//设置窗口大小及位置
		this.setVisible(true);//设置窗口可见性
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String mgIP = jtxtArray[0].getText().trim();//得到用户IP
		String port = jtxtArray[1].getText().trim();//得到端口号
		String mgno = jtxtArray[2].getText().trim();//得到登录名
		String message = mgIP + ":" + port;
		DataBase.message = message;
		DataBase.login = this;//将本方法的 变量作为参数赋值给Database方法的私有变量
		if(e.getSource() == jtxtArray[0]){//事件源为用户IP文本框
			jtxtArray[1].requestFocus();//切换输入焦点到端口号文本框
		}
		if(e.getSource() == jtxtArray[1]){//事件源为端口文本框
			jtxtArray[2].requestFocus();//切换输入焦点到用户名文本框
		}
		if(e.getSource() == jtxtArray[2]){//事件源为用户名文本框
			jpassword.requestFocus();//切换输入焦点到密码框
		}
		else if(e.getSource() == jbArray[1]){//事件源为清空按钮
			jlArray[4].setText("");
			jtxtArray[2].setText("");
			jpassword.setText("");
			jtxtArray[2].requestFocus();//切换输入焦点到用户名文本框
		}
		else if(e.getSource() == jbArray[2]){//事件源为管理员的登录按钮
			if(!mgno.matches("\\d+")){//用户名格式错误
				JOptionPane.showMessageDialog(this, "用户名格式错误","信息",JOptionPane.INFORMATION_MESSAGE);//弹出信息提示对话框
				return ;
			}
			if(jtxtArray[0].getText().trim().equals("")){//如果用户IP为空
				JOptionPane.showMessageDialog(this, "用户IP不能为空", "信息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(jtxtArray[1].getText().trim().equals("")){//如果端口号为空
				JOptionPane.showMessageDialog(this, "端口号不能为空", "信息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql = "select mgNo,password from manager where mgNo = " + Integer.parseInt(mgno);//搜索信息的SQL语句
			try {
				db = new DataBase();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.selectDb(sql);
			
			try {
				String mgNo = "";
				String password = "";
				
				while(db.resultset.next()){
					mgNo = db.resultset.getString("mgNo").trim();//得到用户名
					password = db.resultset.getString("password").trim();//得到密码
				}
				if(jtxtArray[2].getText().trim().equals(mgNo)&&String.valueOf(jpassword.getPassword()).equals(password)){
					jlArray[4].setText("登录成功");
					new Root(mgNo);//创建主界面
					this.dispose();//将登录窗体隐藏
				}
				else {
					jlArray[4].setText("登录失败");
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			db.dbClose();//关闭数据库连接
			
		}
		else if(e.getSource() == jbArray[0]){//事件源为学生登录按钮
			if(!jtxtArray[2].getText().trim().matches("\\d+")){//用户名格式错误
				JOptionPane.showMessageDialog(this, "输入有误，学号只能为数字","消息",JOptionPane.INFORMATION_MESSAGE);//弹出信息提示对话框
				return ;
			}
			if(jtxtArray[0].getText().trim().equals("")){//如果用户IP为空
				JOptionPane.showMessageDialog(this, "用户IP不能为空", "信息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(jtxtArray[1].getText().trim().equals("")){//如果端口号为空
				JOptionPane.showMessageDialog(this, "端口号不能为空", "信息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//查询学号文本中所输学号是否存在与student表中
			sql = "select StuNo,Password from student where StuNo = " + Integer.parseInt(jtxtArray[2].getText().trim());
			try {
				
				db = new DataBase();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.selectDb(sql);//执行查询
			try {
				String stuNo = "";
				String password = "";
				
				while(db.resultset.next()){
					stuNo = db.resultset.getString("stuNo").trim();//得到用户名
					password = db.resultset.getString("password").trim();//得到密码

				}
				if(jtxtArray[2].getText().trim().equals(stuNo)&&String.valueOf(jpassword.getPassword()).equals(password)){
					jlArray[4].setText("登录成功");
					new StudentSystem();//创建主界面
					//this.dispose();//将登录窗体隐藏
				}
				else {
					jlArray[4].setText("登录失败");
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	public static void main(String args[]){
		new Login();
	}
	
}

