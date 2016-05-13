package com.yuan;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import java.net.PasswordAuthentication;
import java.sql.SQLException;

import javax.swing.*;


public class Login extends JFrame implements ActionListener{
	private JPanel jp = new JPanel();//����JPanel����
	private JLabel[] jlArray = {new JLabel("�û�IP"),new JLabel("�˿ں�"),new JLabel("�û���"),new JLabel("����"),new JLabel("")};//������ǩ��
	private JButton[] jbArray = {new JButton("ѧ����¼"),new JButton("���"),new JButton("����Ա��½")};//������ť����
	private JTextField[] jtxtArray = {new JTextField("127.0.0.1"),new JTextField("3306"),new JTextField("")};//��ʼ���ı�������
	private JPasswordField jpassword = new JPasswordField("");//���������
	String sql;//���ڴ��sql���
	DataBase db;//�������ݿ�������
	
	public Login() {
		jp.setLayout(null);//����JPanel�Ĳ��ֹ�����
		//��ǩ
		for(int i = 0;i < 4;i++){//�Ա�ǩѭ������
			jlArray[i].setBounds(30,20+i*50,80,25);//���ñ�ǩ�Ĵ�С��λ��
			jp.add(jlArray[i]);//����ǩ��ӵ�JPanel������
		}
		//��ť
		for(int i = 0;i < 3;i++){
			jbArray[i].setBounds(10+ i*120,230,	 100, 25);//���ð�ť��С,λ��
			jp.add(jbArray[i]);//����ť��ӵ������
			jbArray[i].addActionListener(this);//ע�ᶯ���¼�������
		}
		//�ı���
		for(int i = 0;i < 3;i++){
			jtxtArray[i].setBounds(80, 20+50*i, 180, 25);//�����ı����С��λ��
			jp.add(jtxtArray[i]);//���ı�����ӵ������
			jtxtArray[i].addActionListener(this);//ע�ᶯ���¼�������	
		}
		jpassword.setBounds(80, 170, 180, 25);//���������Ĵ�С��λ��
		jp.add(jpassword);//���������ӵ�JPanel����
		jpassword.setEchoChar('*');//���������Ļ����ַ�
		jpassword.addActionListener(this);//Ϊ�����ע�������
		jlArray[4].setBounds(10, 280, 300, 25);//����������ʾ��¼״̬�Ĵ�Сλ��
		jp.add(jlArray[4]);//����ǩ��ӽ�JPanel����
		this.add(jp);//�������ӵ�������
		Image image = new ImageIcon("ICO.GIF").getImage();//��logoͼƬ���г�ʼ��
		this.setIconImage(image);//����ͼ��
		this.setTitle("��¼");//���ô��ڱ���
		this.setResizable(false);//ʹ��󻯰�ť������
		this.setBounds(100, 100, 400, 350);//���ô��ڴ�С��λ��
		this.setVisible(true);//���ô��ڿɼ���
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String mgIP = jtxtArray[0].getText().trim();//�õ��û�IP
		String port = jtxtArray[1].getText().trim();//�õ��˿ں�
		String mgno = jtxtArray[2].getText().trim();//�õ���¼��
		String message = mgIP + ":" + port;
		DataBase.message = message;
		DataBase.login = this;//���������� ������Ϊ������ֵ��Database������˽�б���
		if(e.getSource() == jtxtArray[0]){//�¼�ԴΪ�û�IP�ı���
			jtxtArray[1].requestFocus();//�л����뽹�㵽�˿ں��ı���
		}
		if(e.getSource() == jtxtArray[1]){//�¼�ԴΪ�˿��ı���
			jtxtArray[2].requestFocus();//�л����뽹�㵽�û����ı���
		}
		if(e.getSource() == jtxtArray[2]){//�¼�ԴΪ�û����ı���
			jpassword.requestFocus();//�л����뽹�㵽�����
		}
		else if(e.getSource() == jbArray[1]){//�¼�ԴΪ��հ�ť
			jlArray[4].setText("");
			jtxtArray[2].setText("");
			jpassword.setText("");
			jtxtArray[2].requestFocus();//�л����뽹�㵽�û����ı���
		}
		else if(e.getSource() == jbArray[2]){//�¼�ԴΪ����Ա�ĵ�¼��ť
			if(!mgno.matches("\\d+")){//�û�����ʽ����
				JOptionPane.showMessageDialog(this, "�û�����ʽ����","��Ϣ",JOptionPane.INFORMATION_MESSAGE);//������Ϣ��ʾ�Ի���
				return ;
			}
			if(jtxtArray[0].getText().trim().equals("")){//����û�IPΪ��
				JOptionPane.showMessageDialog(this, "�û�IP����Ϊ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(jtxtArray[1].getText().trim().equals("")){//����˿ں�Ϊ��
				JOptionPane.showMessageDialog(this, "�˿ںŲ���Ϊ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql = "select mgNo,password from manager where mgNo = " + Integer.parseInt(mgno);//������Ϣ��SQL���
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
					mgNo = db.resultset.getString("mgNo").trim();//�õ��û���
					password = db.resultset.getString("password").trim();//�õ�����
				}
				if(jtxtArray[2].getText().trim().equals(mgNo)&&String.valueOf(jpassword.getPassword()).equals(password)){
					jlArray[4].setText("��¼�ɹ�");
					new Root(mgNo);//����������
					this.dispose();//����¼��������
				}
				else {
					jlArray[4].setText("��¼ʧ��");
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			db.dbClose();//�ر����ݿ�����
			
		}
		else if(e.getSource() == jbArray[0]){//�¼�ԴΪѧ����¼��ť
			if(!jtxtArray[2].getText().trim().matches("\\d+")){//�û�����ʽ����
				JOptionPane.showMessageDialog(this, "��������ѧ��ֻ��Ϊ����","��Ϣ",JOptionPane.INFORMATION_MESSAGE);//������Ϣ��ʾ�Ի���
				return ;
			}
			if(jtxtArray[0].getText().trim().equals("")){//����û�IPΪ��
				JOptionPane.showMessageDialog(this, "�û�IP����Ϊ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(jtxtArray[1].getText().trim().equals("")){//����˿ں�Ϊ��
				JOptionPane.showMessageDialog(this, "�˿ںŲ���Ϊ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//��ѯѧ���ı�������ѧ���Ƿ������student����
			sql = "select StuNo,Password from student where StuNo = " + Integer.parseInt(jtxtArray[2].getText().trim());
			try {
				
				db = new DataBase();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.selectDb(sql);//ִ�в�ѯ
			try {
				String stuNo = "";
				String password = "";
				
				while(db.resultset.next()){
					stuNo = db.resultset.getString("stuNo").trim();//�õ��û���
					password = db.resultset.getString("password").trim();//�õ�����

				}
				if(jtxtArray[2].getText().trim().equals(stuNo)&&String.valueOf(jpassword.getPassword()).equals(password)){
					jlArray[4].setText("��¼�ɹ�");
					new StudentSystem();//����������
					//this.dispose();//����¼��������
				}
				else {
					jlArray[4].setText("��¼ʧ��");
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

