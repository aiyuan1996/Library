package com.yuan;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class ExceedTime extends JFrame implements ActionListener {
	DataBase db;
	private JPanel jP = new JPanel();
	private JLabel jl=new JLabel("请输入您的学号");
	private JTextField jtf=new JTextField();//创建文本框
	private JLabel jl1=new JLabel("请输入您要交的款数");
	private JTextField jtf1=new JTextField();
	//创建按钮
	private JButton jb=new JButton("交费");
	private JButton jb1=new JButton("查询欠费");
	
	public ExceedTime() {
		jP.setLayout(null);
		jP.add(jl);
		jP.add(jl1);
		jP.add(jb);
		jP.add(jb1);
		jP.add(jtf);
		jP.add(jtf1);
		jl.setBounds(50,30,120,20);
		jtf.setBounds(170,30,120,20);
		jl1.setBounds(50,70,120,20);
		jtf1.setBounds(170,70,120,20);
		jb.setBounds(180,110,100,25);
		jb.addActionListener(this);
		jb1.addActionListener(this);
		jb1.setBounds(50,110,100,25);
		//设置窗体的大小位置
		this.add(jP);
        this.setBounds(300,300,600,650);
        this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int day =0;
		try {
			db = new DataBase();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String sno = jtf.getText().trim();
		if(sno.equals("")){
			JOptionPane.showMessageDialog(this, "学号不能为空", "消息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(sno.matches("\\D")){//定义学号格式为数字
			JOptionPane.showMessageDialog(this, "学号只能为数字", "消息", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		String sql = "select DelayTiem from exceedtime where stuno="+Integer.parseInt(sno);
		db.selectDb(sql);
		int flag =0;
		try {
			while(db.resultset.next()){
				flag++;
				day+=db.resultset.getInt("delaytime");
			}
		} catch (Exception e1) {
			// TODO: handle exception
		}
		if(flag == 0){
			JOptionPane.showMessageDialog(this, "您所借的书没有过期", "信息", JOptionPane.INFORMATION_MESSAGE);	
			return;
		}
		if (e.getSource() == jb1) {
			JOptionPane.showMessageDialog(this, "您欠费"+day*0.1+"元！", "信息", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		else if(e.getSource() == jb){
			if(jtf1.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "请输入交款金额", "信息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int k = JOptionPane.showConfirmDialog(this, "是否缴费？","消息",JOptionPane.YES_NO_OPTION);
			if(k == JOptionPane.YES_OPTION){
				int ii = Integer.parseInt(jtf1.getText().trim());
				if(ii < day*0.1){
					sql = "update exceedtime set delaytime = dalaytime-"+ii/0.1+"where stuno ="+Integer.parseInt(sno);
					try {
						db = new DataBase();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int i = db.updateDb(sql);
					if(i == 1){
						JOptionPane.showMessageDialog(this,"你已成功交费"+ii+"元,您还需缴纳"+(day*0.1-ii)+"元",
								"消息",JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					else{//选择"否"，提示缴费失败
						JOptionPane.showMessageDialog(this,"对不起，缴费失败!!!",
											"消息",JOptionPane.INFORMATION_MESSAGE);
					      return;					
					}
				}
				else{
					JOptionPane.showMessageDialog(this, "您已经成功缴费"+day*0.1+"元", "消息", JOptionPane.INFORMATION_MESSAGE);
					jtf.setText("");
					sql = "delete from exceedtime shere stuno = "+Integer.parseInt(sno);//删除超期记录的SQL语句
					db.updateDb(sql);
					sql = "update student set permitted ='是'where stuno =  " + Integer.parseInt(sno);
					db.updateDb(sql);
				}
			}
		}
		db.dbClose();
	}
	public static void main(String args[]){
		new ExceedTime();
	}

}
