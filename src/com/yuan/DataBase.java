package com.yuan;
import java.beans.Statement;
import java.sql.*;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;


public class DataBase {
	Connection connection = null;//����connection����
	java.sql.Statement statement ;//�����������
	ResultSet resultset ;//�������������
	int count;//���ڼ�¼���¼�¼��
	public static String message = "127.0.0.1:3306";//����һ����̬��Ա����
	public static Login login;//������¼��������
	public DataBase() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");//��������
			connection = DriverManager.getConnection("jdbc:mysql://"+message+"/test","root","aiyuan");//��������
			statement = connection.createStatement();//����statement����
			System.out.println("success");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(login, "�û�IP��˿ڴ��󣡣���","��Ϣ",JOptionPane.INFORMATION_MESSAGE);//������Ϣ��ʾ��
		}
	}
	public ResultSet selectDb(String sql){//���ݿ��ѯ����
		try {
			resultset = ((java.sql.Statement) statement).executeQuery(sql);//ִ�в�ѯ
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
