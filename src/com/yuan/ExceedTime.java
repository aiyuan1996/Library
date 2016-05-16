package com.yuan;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class ExceedTime extends JFrame implements ActionListener {
	DataBase db;
	private JPanel jP = new JPanel();
	private JLabel jl=new JLabel("����������ѧ��");
	private JTextField jtf=new JTextField();//�����ı���
	private JLabel jl1=new JLabel("��������Ҫ���Ŀ���");
	private JTextField jtf1=new JTextField();
	//������ť
	private JButton jb=new JButton("����");
	private JButton jb1=new JButton("��ѯǷ��");
	
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
		//���ô���Ĵ�Сλ��
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
			JOptionPane.showMessageDialog(this, "ѧ�Ų���Ϊ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(sno.matches("\\D")){//����ѧ�Ÿ�ʽΪ����
			JOptionPane.showMessageDialog(this, "ѧ��ֻ��Ϊ����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
			JOptionPane.showMessageDialog(this, "���������û�й���", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);	
			return;
		}
		if (e.getSource() == jb1) {
			JOptionPane.showMessageDialog(this, "��Ƿ��"+day*0.1+"Ԫ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		else if(e.getSource() == jb){
			if(jtf1.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "�����뽻����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int k = JOptionPane.showConfirmDialog(this, "�Ƿ�ɷѣ�","��Ϣ",JOptionPane.YES_NO_OPTION);
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
						JOptionPane.showMessageDialog(this,"���ѳɹ�����"+ii+"Ԫ,���������"+(day*0.1-ii)+"Ԫ",
								"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					else{//ѡ��"��"����ʾ�ɷ�ʧ��
						JOptionPane.showMessageDialog(this,"�Բ��𣬽ɷ�ʧ��!!!",
											"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
					      return;					
					}
				}
				else{
					JOptionPane.showMessageDialog(this, "���Ѿ��ɹ��ɷ�"+day*0.1+"Ԫ", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					jtf.setText("");
					sql = "delete from exceedtime shere stuno = "+Integer.parseInt(sno);//ɾ�����ڼ�¼��SQL���
					db.updateDb(sql);
					sql = "update student set permitted ='��'where stuno =  " + Integer.parseInt(sno);
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
