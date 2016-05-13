package com.yuan;
import java.beans.Statement;
import java.sql.*;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;


public class DataBase {
	Connection connection = null;//声明connection引用
	java.sql.Statement statement ;//声明语句引用
	ResultSet resultset ;//声明结果集引用
	int count;//用于记录更新记录数
	public static String message = "127.0.0.1:3306";//声明一个静态成员变量
	public static Login login;//创建登录窗口引用
	public DataBase() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");//加载驱动
			connection = DriverManager.getConnection("jdbc:mysql://"+message+"/test","root","aiyuan");//创建连接
			statement = connection.createStatement();//创建statement对象
			System.out.println("success");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(login, "用户IP或端口错误！！！","信息",JOptionPane.INFORMATION_MESSAGE);//弹出信息提示框
		}
	}
	public ResultSet selectDb(String sql){//数据库查询方法
		try {
			resultset = ((java.sql.Statement) statement).executeQuery(sql);//执行查询
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultset;
	}
	public int updateDb(String sql){
		try {
			sql = new String(sql.getBytes(),"gb2312");
			count = ((java.sql.Statement) statement).executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	public void dbClose(){
		try {
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
