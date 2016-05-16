package com.yuan;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ReturnBook extends JFrame implements ActionListener {
	DataBase db;
	String sql;
	String str;
	//�����ָ��Ϊ���µ�JsplitPane����
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	//����JPanel����
	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	//������ť����
	private JButton jbArray[] = new JButton[]
			{new JButton("��ʧ"),new JButton("�黹"),new JButton("ȷ��")};
	private JLabel jl = new JLabel("ѧ��");
	private JTextField jtxt = new JTextField();
	//��������
	Vector<String>head = new Vector<String>();
	{
		head.add("���");
		head.add("ѧ��");
		head.add("����ʱ��");
		head.add("����ʱ��");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
	}
	Vector<Vector>data=new Vector<Vector>();
	DefaultTableModel dtm = new DefaultTableModel(data,head);//�������ģ��
	JTable jt = new JTable(dtm);//����Jtable����
	//��jTable��װ����������
	JScrollPane jspn = new JScrollPane(jt);
	
	
	public ReturnBook() {
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);
		jpb.setLayout(null);
		//����Label�Ĵ�С��λ��
        jl.setBounds(5,15,100,20);
        //��Jlabel��ӵ�jpt�����
		jpt.add(jl);
		//ΪJTextField���ô�С��λ��
		jtxt.setBounds(105,15,300,20);
	    //��JTextField��ӵ�jpt
		jpt.add(jtxt);
		//����JBuuton�Ĵ�С��λ��
	    jbArray[0].setBounds(5,50,100,20);
        jbArray[1].setBounds(150,50,100,20);
        jbArray[2].setBounds(295,50,100,20);
        //���JButton����������¼�������
        for(int i=0;i<3;i++)
		{
			 jpt.add(jbArray[i]);
			 jbArray[i].addActionListener(this);
		}
        jsp.setTopComponent(jpt);//jpt���õ����洰��
        jsp.setBottomComponent(jspn);
        jsp.setDividerSize(4);
    	this.add(jsp);
    	//����jsp�зָ����ĳ�ʼλ��
    	jsp.setDividerLocation(80);
		//���ô���Ĵ�Сλ�ü��ɼ���
        this.setBounds(10,10,800,600);
        this.setVisible(true); 
        
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jbArray[2]){
			if(jtxt.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "������ѧ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else {
				sql = "select *from record where StuNo = " + jtxt.getText().trim();
				try {
					db = new DataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				db.selectDb(sql);
				Vector<Vector>vtemp = new Vector<Vector>();//��������
				try {
					int k =0;
					while(db.resultset.next()){//���������
						k++;
						Vector<String>v = new Vector<String>();
						for(int i =1;i < 7;i++){
							String str = db.resultset.getString(i);
							str = new String(str.getBytes(),"gb2312");
							v.add(str);
						}
						vtemp.add(v);
						jt.clearSelection();//�����ѡ��
						dtm.setDataVector(vtemp, head);//���ñ������
						jt.updateUI();
						jt.repaint();
					}
					if(k == 0){
						JOptionPane.showMessageDialog(this, "�����˴����ѧ�Ż��ѧ��û�н����¼", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		if(e.getSource() == jbArray[1]){//�黹ͼ��
			
			int row=jt.getSelectedRow();
			if(row<0){//���δѡ���²����е�ĳЩ���ݣ�������ʾ
	   			
				JOptionPane.showMessageDialog(this,"��ѡ��Ҫ�黹����!!!","��Ϣ",
					                              JOptionPane.INFORMATION_MESSAGE);
				return;
   			}
			str = (String) jt.getValueAt(row, 0);
			int sno=Integer.parseInt((String)jt.getValueAt(row,1));//ѧ��
   			int bno=Integer.parseInt(str);//���

   			int flag=checkTime(sno,bno);	//�ж��Ƿ���
   			if(flag==-1){//���ͼ�鳬�ڣ���ȡ����ͬѧ�Ľ���Ȩ��
   				try {
					db=new DataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
   				sql="update student set permitted='��' where StuNO="+sno;
   				db.updateDb(sql);
   				db.dbClose();
   				if(flag==0){return;}//���ͼ��δ���ڣ�����й黹����
   				sql="Delete from RECORD where BookNO="+Integer.parseInt(str);
   	   			try {
					db=new DataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
   	   			db.updateDb(sql);
   	   			sql="update book set borrowed='��' where BookNO="+Integer.parseInt(str);
   	   			db.updateDb(sql);//������ͼ���¼�������ŵ���Ϊ�ɽ�
   	   			db.dbClose();
   				updateTable();
   			}
   			
		}
		if(e.getSource()==jbArray[0]){//��Ҫ��ʧͼ��
			int row=jt.getSelectedRow();
			if(row<0){
				JOptionPane.showMessageDialog(this,"��ѡ��Ҫ��ʧ����!!!","��Ϣ",
					                              JOptionPane.INFORMATION_MESSAGE);
				return;
			}
   			loseBook(row);
   			updateTable();
   		}
		
	}
	private void loseBook(int row) {
		String bname = "";
		int lbno = 0;
		int bno = Integer.parseInt((String) jt.getValueAt(row, 0));//�õ���ʧͼ������
		String sno = (String) jt.getValueAt(row, 1);
		sql = "select bookname from book where Bookno = " + bno;
		try {
			db = new DataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.selectDb(sql);
		try {
			if(db.resultset.next()){//���������
				bname = db.resultset.getString("bookname").trim();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql = "select MAX(lbno) from Losebook ";//�ҵ����Ķ�ʧ��¼�ŵ�SQL���
		db.selectDb(sql);
		try {
			if(db.resultset.next()){
				lbno = db.resultset.getInt("bookNo");
				lbno++;
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql="insert into LOSEBOOK values("+lbno+","+sno+","+bno+",'"+bname+"')";//�����¼���в����¼
		db.selectDb(sql);
		try {
			while(db.resultset.next()){
				sql = "delete from orderreport where bookno = "+bno;db.updateDb(sql);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql = "select bookno form exceedtime where bookno="+bno;
		db.selectDb(sql);
		try {
			while(db.resultset.next()){
				sql = "delete from exteedtime where bookno = " + bno;
				db.updateDb(sql);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql = "delete from book where bookno = "+ bno;
		int i = db.updateDb(sql);
		db.dbClose();
		if(i > 0){
			JOptionPane.showMessageDialog(this, "��ϲ�㣬��ʧ�ɹ�", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		else {
			JOptionPane.showMessageDialog(this, "������˼����ʧʧ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
	}


	public void updateTable(){//ʵ�ֽ����²����ĸ���
		
		sql="select * from RECORD where StuNO="+jtxt.getText().trim();
		try {
			db=new DataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.selectDb(sql);//��ͼ����Ϣ�����ݿ���ȡ��
		Vector<Vector> vtemp = new Vector<Vector>();
		try{
			while(db.resultset.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<7;i++){//��ÿ����ӵ���ʱ����v
					String str=db.resultset.getString(i);
//					str=new String(str.getBytes("ISO-8859-1"),"gb2312");
					v.add(str);
					
				}
				vtemp.add(v);//��������¼��ӵ���ʱ����vtemp
			}
			db.dbClose();	
		}
		catch(Exception ea){ea.printStackTrace();}
		jt.clearSelection();			
		dtm.setDataVector(vtemp,head);//����table	
		jt.updateUI();
		jt.repaint();   		
   	}	


	private int checkTime(int sno, int bno) {
		int day =0;//���ڼ�¼��������
		int flag = 0;
		String bname = "";//����
		Date now = new Date();
		String returntime = "";
		sql = "select returntime *from record where StuNo = " + sno + "and BookNo = " + bno;
		try {
			db = new DataBase();//�ӽ���ʱ����в�ѯĳ�˽��ĳ��Ĺ黹ʱ��
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.selectDb(sql);//ִ�в�ѯ
		try {
			if(db.resultset.next()){
				returntime = db.resultset.getString("returntime");//��ȡ�黹ʱ��
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String[] strday = returntime.split("\\.");//ʹ��������ʽ��֤ʱ��ĸ�ʽ
		int ryear = Integer.parseInt(strday[0].trim());
		int rmonth = Integer.parseInt(strday[1].trim());
		int rday = Integer.parseInt(strday[2].trim());
		day = (now.getYear()+1900-ryear)*365 + (now.getMonth()+1-rmonth)*30 + (now.getDate()-rday);//��������
		System.out.println(day);
		
		return 0;
	}


	public static void main(String args[]){
		new ReturnBook();
	}

}
