package com.yuan;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class SearchBook extends JFrame implements ActionListener{
	int flag ;//������־λ
	String sql;
	DataBase db;
	private JSplitPane JSp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);//�ָ�Ϊ����
	private JPanel jpt = new JPanel();//���������ϲ�JPanel����
	private JPanel jpb = new JPanel();//���������²�JPanel����
	private String[] str = {"����","������","����","����ʱ��"};//���������б����ݵ��ַ�������
	private JComboBox jcb = new JComboBox(str);//���������б�
	private JButton jb = new JButton("�ύ");
	private JLabel[] jlArray = new JLabel[]{new JLabel("����"),new JLabel("����"),new JLabel("������")};//��������ʼ����ǩ����
	//��������ʼ���ı�������
	private JTextField[] jtxtArray = new JTextField[]{new JTextField(),new JTextField(),new JTextField(),new JTextField()};
	private JRadioButton[] jrbArray = {new JRadioButton("�򵥲�ѯ",true),new JRadioButton("�߼���ѯ")};
	private ButtonGroup bg = new ButtonGroup();//������ť��
	Vector<String> head = new Vector<String>();//������ͷ����
	{
		head.add("���");head.add("����");
		head.add("����");head.add("������");
		head.add("����ʱ��");head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
		
	}
	Vector<Vector> data = new Vector<Vector>();//��ż������������Ϣ
	DefaultTableModel dtm = new DefaultTableModel(data,head);//�������ģ��
	JTable jt = new JTable(dtm);//����JTable����
	JScrollPane jspn = new JScrollPane(jt);//��table��ӵ���������
	//���������Ҫ��Ϊ��ѯͼ�����Ĵ������ǩ����ť�������б��ı��򣬵�ѡ��ť�ȿؼ���ͬʱ�ڷָ�ڴ������ģ�ͣ��Ա��ѯ�ɹ��󽫲�ѯ�����ʾ�ڱ����
	
	public SearchBook() {
		this.setLayout(new GridLayout(1,1));//���񲼾�
		jpt.setLayout(null);//���沼��Ϊ��
		jpb.setLayout(null);//���沼��Ϊ��
		
		jpt.add(jcb);//�������б���ӵ��������
		jcb.setBounds(170, 20, 150, 20);
		jcb.addActionListener(this);
		
		jpt.add(jb);//���ύ��ť��ӵ��������
		jb.setBounds(600,20,120,20);
		jb.addActionListener(this);
		
		//��ӵ�ѡ��ť���������
		for(int i = 0;i < 2;i++){
			jrbArray[i].setBounds(20, 20+i*40, 100, 20);
			jpt.add(jrbArray[i]);
			jrbArray[i].addActionListener(this);
			bg.add(jrbArray[i]);//����ѡ��ť��ӵ���ť������
		}
		
		//��ӱ�ǩ,�ı����������
		for(int i = 0;i < 3;i++){
			jlArray[i].setBounds(150+i*200,60,40,20);
			jtxtArray[i].setBounds(200+i*200, 60, 120, 20);
			jtxtArray[i].setEditable(false);//�����ı���Ϊ������
			jpt.add(jlArray[i]);
			jpt.add(jtxtArray[i]);
		}
		
		jtxtArray[3].setBounds(400, 20, 120, 20);
		jpt.add(jtxtArray[3]);
		
		JSp.setTopComponent(jpt);
		JSp.setBottomComponent(jspn);
		JSp.setDividerSize(4);//���÷ָ����Ĵ�С
		this.add(JSp);
		
		this.setTitle("��ѯͼ��");
		JSp.setDividerLocation(100);
		this.setBounds(3, 10, 800, 500);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(jrbArray[0].isSelected()){//�򵥲�ѯ
			if(jcb.getSelectedIndex() >= 0&&jcb.getSelectedIndex() < 4){//��������0-3֮��ʱ
				jtxtArray[3].requestFocus();//�����ı����ý���
				if(e.getSource() == jb){//����¼�ԴΪ�ύ��ť����ִ�м���
					String str = jtxtArray[3].getText().trim();
					if(str.equals("")){//��ѯ����Ϊ��
						JOptionPane.showMessageDialog(this, "�������Ҫ����Ϣ", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					if(jcb.getSelectedIndex() == 0){//�����������в�ѯ
						sql = "select *from BOOK where BookName = '"+str+"'";
						jtxtArray[3].setText("");
					}
					else if(jcb.getSelectedIndex() == 1){//���ݳ�������в�ѯ
						sql = "select *from BOOK where publishment = '"+str+"'";
						jtxtArray[3].setText("");
					}
					else if(jcb.getSelectedIndex() == 2){//�������߽��в�ѯ
						sql = "select *from BOOK where Author = '"+str+"'";
						jtxtArray[3].setText("");
					}
					else {
						sql = "select *from BOOK where BuyTime = '"+str+"'";
						jtxtArray[3].setText("");
					}
					try {
						db = new DataBase();
						try {
							sql = new String(sql.getBytes(),"gb2312");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					db.selectDb(sql);//ִ�в�ѯ
					
				}
			}
			
			Vector<Vector>vtemp = new Vector<Vector>();//�������������ڴ�ű������
			try {
				int flag = 0;
				while(db.resultset.next()){//ȡ�ý����
					flag++;
					Vector<String> v = new Vector<String>();//�������������ڴ��ÿһ�е�����
					for(int i = 1; i <= 7;i++){
						String str1 = db.resultset.getString(i);
						str1 = new String(str1.getBytes(),"gb2312");
						v.add(str1);//��ӵ�������ݱ�����
						
					}
					vtemp.add(v);//��������¼��ӵ�vtemp
				}
				if(flag==0){//��ѯʧ�ܣ���ʾ
					JOptionPane.showMessageDialog(this,"û����Ҫ���ҵ����ݣ�����",
					                      "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			dtm.setDataVector(vtemp, head);//����table
			jt.updateUI();//��ʾ����Ѿ�����
			jt.repaint();//�ػ���
			db.dbClose();//�ر����ݿ�����
		}
		else if (jrbArray[1].isSelected()){//�߼���ѯ
			jtxtArray[0].requestFocus();
			jtxtArray[3].setEditable(false);
			for(int i = 0; i < 3;i++){
				jtxtArray[i].setEditable(true);
			}
			if(e.getSource() == jb){
				int bz = this.seniorSearch();//�õ���־λ
				if(bz != 0)
					return ;
				try {
					db = new DataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				db.selectDb(sql);
				Vector<Vector> vtemp = new Vector<Vector>();//������������
				try {
					int flag = 0;//��¼�����������
					while(db.resultset.next()){
						flag++;
						Vector<String>v = new Vector<String>();
						for(int i = 1;i <= 7 ;i++){
							String str = db.resultset.getString(i);
							str = new String(str.getBytes(),"gb2312");
							v.add(str);
						}
						vtemp.add(v);//��������¼��ӵ���ʱ����vtemp
					}
					if(flag == 0){
						JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
				dtm.setDataVector(vtemp, head);//���ñ������
				jt.updateUI();
				jt.repaint();
				db.dbClose();
			}
		}
		
	}
	private int seniorSearch() {
		int flag = 0;
		String str0 = jtxtArray[0].getText().trim();
		String str1 = jtxtArray[1].getText().trim();
		String str2 = jtxtArray[2].getText().trim();
		if(str0.equals("")&&str1.equals("")&&str2.equals("")){
			JOptionPane.showMessageDialog(this, "�������Ҫ����Ϣ", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if(((!str0.equals(""))&&(str1.equals(""))&&(str2.equals("")))
				||((str0.equals(""))&&(!str1.equals(""))&&(str2.equals("")))
				||((str0.equals(""))&&(str1.equals(""))&&(!str2.equals("")))){
			JOptionPane.showMessageDialog(this, "��ʹ�ü򵥲�ѯ", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if((!str0.equals(""))&&(!str1.equals(""))&&(str2.equals(""))){//�������������
			sql = "select *from book where bookname = '" + str0 + "' and author = '" + str1 + "'";
			jtxtArray[0].setText("");
			jtxtArray[1].setText("");
		}
		if((!str0.equals(""))&&(str1.equals(""))&&(!str2.equals(""))){//�����ͳ��������
			sql = "select *from book where bookname = '" + str0 + "' and publishment = '" + str2 + "'";
			jtxtArray[0].setText("");
			jtxtArray[2].setText("");
		}
		if((str0.equals(""))&&(!str1.equals(""))&&(!str2.equals(""))){//���ߺͳ��������
			sql = "select *from book where author = '" + str1 + "' and publishment = '" + str2 + "'";
			jtxtArray[1].setText("");
			jtxtArray[2].setText("");
		}
		if((!str0.equals(""))&&(!str1.equals(""))&&(!str2.equals(""))){//���������ߺͳ��������
			sql = "select *from book where bookname = '" + str0 + "' and author = '" + str1 + "' and publishment = '" + str2 + "'";
			jtxtArray[0].setText("");
			jtxtArray[1].setText("");
			jtxtArray[2].setText("");
		}
		return flag;
	}

	/*public static void main(String args[]){
		new SearchBook();
		
	}*/

}
