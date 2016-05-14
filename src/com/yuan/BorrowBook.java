package com.yuan;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BorrowBook extends  JFrame implements ActionListener{
	private JSplitPane JSpl = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);//����
	private JPanel jp = new JPanel();//����һ�����
	int flag;
	DataBase db;
	String sql ;
	private JButton button = new JButton("ȷ��");//ȷ����ť
	private JLabel jlable1 = new JLabel("��Ҫ���Ļ�ԤԼ�����");
	private JLabel jlable2 = new JLabel("����������ѧ��");
	private JTextField jtxt1 = new JTextField();
	private JTextField jtxt2 = new JTextField();
	//���ð��水ť
	private JRadioButton[] jrbArray ={ new JRadioButton("����ͼ��",true),new JRadioButton("ԤԼͼ��")};
	private ButtonGroup bg = new ButtonGroup();
	Vector<String>head = new Vector<String>();//��������
	{
		head.add("���");
		head.add("����");
		head.add("����");
		head.add("������");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
	}
	Vector<Vector>data = new Vector<Vector>();//���������������
	DefaultTableModel dtm = new DefaultTableModel(data,head);//�������ģ��
	JTable jt = new JTable(dtm);//����Jtable
	JScrollPane jspn = new JScrollPane(jt);
	
	public BorrowBook() {
		this.setLayout((new GridLayout(1,1)));
		JSpl.setTopComponent(jp);//��jp���õ�����
		JSpl.setBottomComponent(jspn);//��JScrollPane���õ�����
		JSpl.setDividerSize(4);
		JSpl.setDividerLocation(100);
		jp.setLayout(null);
		
		button.setBounds(380,20,100,20);//���ð�ť�Ĵ�С��λ��
		jp.add(button);
		button.addActionListener(this);
		
		//����JLable������
		jlable1.setBounds(80,60,130,20);
    	jlable2.setBounds(330,60,100,20);
    	//��JLable��ӽ�JPanel
    	jp.add(jlable1);
    	jp.add(jlable2);
    	
    	jtxt1.setBounds(220,60,100,20);
    	jtxt2.setBounds(430,60,100,20);
    	jp.add(jtxt1);
    	jp.add(jtxt2);
    	
    	for(int i=0;i<2;i++)
    	{
    		jrbArray[i].setBounds(70+i*150,20,150,20);
    		jp.add(jrbArray[i]);
    		bg.add(jrbArray[i]);
    	}
    	
    	this.add(JSpl);
    	this.setBounds(10,10,800,600);
        this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button){
			if(jtxt2.getText().equals("")){//Ϊ����Ϊ�յ�������д���
				JOptionPane.showMessageDialog(this,"���벻��Ϊ�գ����������룡����",
				                      "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//��ѯѧ���ı�������ѧ���Ƿ������STUDENT����
			String sql = "select *from student where stuNo = " + Integer.parseInt(jtxt2.getText().trim());
			try {
				db = new DataBase();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.selectDb(sql);
			Vector<Vector>vtemp = new Vector<Vector>();//�������������ڴ�ű������
			try {
				if(!(db.resultset.next())){
					//��ѧ�����������ʾ�Ի���
					JOptionPane.showMessageDialog(this, "ѧ�Ŵ���", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					String stuName = db.resultset.getString("stuName").trim();//�õ�ѧ������
					String classes = db.resultset.getString("class").trim();//�õ�ѧ���༶
					stuName = new String(stuName.getBytes(),"gb2312");
					classes = new String(classes.getBytes(),"gb2312");
					
					//��ѧ����ȷ�������ѧ���Ƿ���Ȩ�޽����ԤԼ
					if(db.resultset.getString("permitten").equals("��")){
						JOptionPane.showMessageDialog(this, "���޴�Ȩ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						db.selectDb(sql);
						do{
							String borrowed = null;//����book����
							String ordered = null;
							String bookName = null;//���������������Ӧͼ�������
							String author = null;//����������Ŷ�Ӧͼ�������
							
						}while(db.resultset.next());
					}
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}
	public static void main(String args[]){
		new BorrowBook();
	}

}
