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
		head.add("����ʱ��");
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
					if(db.resultset.getString("permitted").equals("��")){
						JOptionPane.showMessageDialog(this, "���޴�Ȩ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					}
					else {//����Ȩ�ޣ�����������������Ƿ������Book����
						
						sql = "select *from book where bookNo = " + Integer.parseInt(jtxt1.getText().trim());
						db.selectDb(sql);
						do{
							
							if(!db.resultset.next()){
								JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
							}
							Vector<String>v = new Vector<String>();//��������
							for(int i = 1;i <= 7;i++){
								//˳��õ����ҽ���еĸ����¼
								if(i == 1){
									String str = db.resultset.getString("BookNo").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//��ӵ�������
								}
								
								if(i == 2){
									String str = db.resultset.getString("BookName").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//��ӵ�������
								}
								if(i == 3){
									String str = db.resultset.getString("author").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//��ӵ�������
								}
								if(i == 4){
									String str = db.resultset.getString("publishment").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//��ӵ�������
								}
								if(i == 5){
									String str = db.resultset.getString("buytime").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//��ӵ�������
								}
								if(i == 6){
									String str = db.resultset.getString("borrowed").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//��ӵ�������
								}
								if(i == 7){
									String str = db.resultset.getString("ordered").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//��ӵ�������
								}
							}
							vtemp.add(v);//���½���������
							dtm.setDataVector(vtemp, head);//���ñ����ʾ����
							jt.updateUI();
							jt.repaint();
							
							if(jrbArray[0].isSelected()){//ѡ���˽�ͼ��
								if(db.resultset.getString("Borrowed").trim().equals("��")){
									JOptionPane.showMessageDialog(this, "�����Ѿ�������", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
								}
								else if(db.resultset.getString("ordered").trim().equals("��")){
									JOptionPane.showMessageDialog(this, "�����Ѿ���ԤԼ�����ܽ�", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
								}
								else {
									
									java.util.Date now = new java.util.Date();//��ȡ��ǰ��������¼����ʱ��ͻ���ʱ��
									sql = "update book set borrowed = '��' where bookNo = " + Integer.parseInt(jtxt1.getText().trim());
									db.updateDb(sql);//���±�
									JOptionPane.showMessageDialog(this, "����ɹ�", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
									sql="insert into RECORD values("+Integer.parseInt(jtxt1.getText().trim())+","
										    +Integer.parseInt(jtxt2.getText().trim())+",'"+(now.getYear()+1900)+"."
										    +(now.getMonth()+1)+"."+now.getDate()+"',"+"'"+(now.getYear()+1900)+"."
										    +(now.getMonth()+2)+"."+now.getDate()+"','��','��')";
										db.updateDb(sql);//�������¼����Record����
								}
							}
							if(jrbArray[1].isSelected()){//ѡ����ԤԼͼ��
								if(db.resultset.getString("ordered").trim().equals("��")){
									JOptionPane.showMessageDialog(this, "�����Ѿ���ԤԼ", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
								}
								else {
									sql = "update book set ordered = '��' where bookno = " + Integer.parseInt(jtxt1.getText().trim());
									db.updateDb(sql);//ִ�и��²���
									JOptionPane.showMessageDialog(this, "ԤԼ�ɹ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
									sql="insert into ORDERREPORT values("+Integer.parseInt(jtxt1.getText().trim())
											+",'"+stuName+"','"+classes+"','"+db.resultset.getString("bookname").trim()+"',"
										    +Integer.parseInt(jtxt2.getText().trim())+",'"+db.resultset.getString("author").trim()+"')";
										db.updateDb(sql);
								}
							}
							
							
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
