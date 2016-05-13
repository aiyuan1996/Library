package com.yuan;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.ldap.ManageReferralControl;
import javax.net.ssl.ManagerFactoryParameters;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


public class Root extends JFrame{
	//�����������
	private DefaultMutableTreeNode dmtnRoot = new DefaultMutableTreeNode(new NodeValue("ͼ��ݹ���ϵͳ"));
	private DefaultMutableTreeNode dmtn1 = 	 new DefaultMutableTreeNode(new NodeValue("ѧ���û�����"));
	private DefaultMutableTreeNode dmtn2 = 	 new DefaultMutableTreeNode(new NodeValue("ͼ�����"));
	private DefaultMutableTreeNode dmtn3 = 	 new DefaultMutableTreeNode(new NodeValue("��ѯͼ��"));
	private DefaultMutableTreeNode dmtn4 = 	 new DefaultMutableTreeNode(new NodeValue("����ԤԼͼ��"));
	private DefaultMutableTreeNode dmtn5 = 	 new DefaultMutableTreeNode(new NodeValue("�黹��ʧͼ��"));
	private DefaultMutableTreeNode dmtn6 = 	 new DefaultMutableTreeNode(new NodeValue("���ɷ���"));
	private DefaultMutableTreeNode dmtn7 = 	 new DefaultMutableTreeNode(new NodeValue("����Ա����"));
	private DefaultMutableTreeNode dmtn8 = 	 new DefaultMutableTreeNode(new NodeValue("�˳�"));
	DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot);//���������
	JTree jtree = new JTree(dtm);//��������dtm��ģ�͵�������
	JScrollPane jsp = new JScrollPane(jtree);//ΪJTree����������
	private JSplitPane jsplr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);//�����ָ�����
	private JPanel jP = new JPanel();//����JPanel����
	Image image = new ImageIcon("TSGL.JPG").getImage();//����ͼƬImage����
	ImageIcon ii = new ImageIcon(image);//�õ�IMageIcon����
	private JLabel jlRoot = new JLabel(ii);//����JLable����
	private  Manager manager;//��¼����Ա��
	String mgNo;//����ԱID
	CardLayout cl = new CardLayout();//������Ƭ���ֶ���
	
	public Root(String mgNo) throws SQLException {
		this.mgNo = mgNo;//��ù���ԱID
		manager = new Manager(mgNo);//��������Ա�������
		
		this.setManager();//���ù���ԱȨ��
		this.initJP();//��ʼ����Ƭ������� 
		this.addTreeListener();//Ϊ���ڵ�ע���¼�������
		dmtnRoot.add(dmtn1);
		dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);
		dmtnRoot.add(dmtn4);
		dmtnRoot.add(dmtn5);
		dmtnRoot.add(dmtn6);
		dmtnRoot.add(dmtn7);
		dmtnRoot.add(dmtn8);
		jtree.setEditable(false);//���ø����н���ǲ��ɱ༭��
		this.add(jsplr);//���������ķָ����ӽ�����
		jsplr.setLeftComponent(jtree);//���������Ĺ���������ӽ���ߵ��Ӵ���
		jP.setBounds(200, 50, 600, 500);
		jsplr.setRightComponent(jP);//��jp��ӽ��ָ����ұ�
		jsplr.setDividerLocation(200);//���÷ָ����ĳ�ʼλ��
		jsplr.setDividerSize(4);//���÷ָ����Ŀ��
		jlRoot.setFont(new Font("Conrier",Font.PLAIN,30));//���ñ�ǩ������
		jlRoot.setHorizontalAlignment(JLabel.CENTER);//����ˮƽ���뷽ʽ
		jlRoot.setVerticalAlignment(JLabel.CENTER);//������ֱ���뷽ʽ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ô���رն���
		Image image = new ImageIcon("ico.gif").getImage();//���ش���ͼ�����
		this.setIconImage(image);//���ô���ͼ��
		this.setTitle("ͼ��ݹ���ϵͳ");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//�õ���Ļ�ߴ�
		int centerX = screenSize.width/2;
		int centerY = screenSize.height/2;
		int w = 800;
		int h = 600;
		this.setBounds(centerX - w/2, centerY - h/2, w, h);//���ô����������Ļ����
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);//����ȫ��
		this.setVisible(true);
		jtree.setShowsRootHandles(true);
	}

	private void addTreeListener() {
		// TODO Auto-generated method stub
		jtree.addTreeSelectionListener(new TreeSelectionListener() {//Ϊ�����ѡ��ģ�ͼ�����
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {//��ֵ�仯ʱ
				// TODO Auto-generated method stub
				DefaultMutableTreeNode cdmtn = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();//�õ�ѡ�еĽ�����
				NodeValue cnv = (NodeValue) cdmtn.getUserObject();//�õ��Զ��������
				if(cnv.value.equals("ͼ��ݹ���ϵͳ")){
					cl.show(jP, "root");//��ʾ���ڵ���Ϣ
				}
				if(cnv.value.equals("ѧ���û�����")){
					cl.show(jP, "stu");
				}
				if(cnv.value.equals("ͼ�����")){
					cl.show(jP, "bm");
				}
				if(cnv.value.equals("��ѯͼ��")){
					cl.show(jP, "sb");
				}
				if(cnv.value.equals("����ԤԼͼ��")){
					cl.show(jP, "bb");
				}
				if(cnv.value.equals("�黹��ʧͼ��")){
					cl.show(jP, "rb");
				}
				if(cnv.value.equals("���ɷ���")){
					cl.show(jP, "et");
				}
				if(cnv.value.equals("����Ա����")){
					cl.show(jP, "Manager");
				}
				if(cnv.value.equals("�˳�")){
					int i = JOptionPane.showConfirmDialog(Root.this, "�Ƿ��˳�ϵͳ��", "��Ϣ",JOptionPane.YES_NO_OPTION);
					if(i == JOptionPane.YES_OPTION)
						System.exit(0);
				}
 			}
		});
	}

	private void initJP() {
		jP.setLayout(cl);//���ò��ֹ�����Ϊ��Ƭ����
		jP.add(jlRoot,"root");//��Ӹ������ʾ��Ϣ
		jP.add(new Student(),"stu");//���ѧ������ģ�����
		jP.add(new BookManage(),"bm");//���ͼ�����ģ�����
		jP.add(new SearchBook(),"sb");//��Ӳ���ͼ��������
		jP.add(new BorrowBook(),"bb");//��ӽ���ԤԼͼ��ģ�����
		jP.add(new ReturnBook(),"rb");//��ӹ黹��ʧͼ�����
		jP.add(new Manager(mgNo),"Manager");//��ӹ���Ա����ģ�����
		jP.add(new ExceedTime(),"et");//��ӷ�������
		
	}

	

	private void setManager() throws SQLException {
		String str="";
		String sql="select permitted from manager where mgNo='"+mgNo+"'";//��������ԱȨ�޵�SQL���
		
		try {
			DataBase db = new DataBase();//�������ݿ����
			ResultSet rSet=db.selectDb(sql);//ִ�в�ѯ
			while(rSet.next()){
				 str = rSet.getString("permitted").trim();//�õ�����ԱȨ��
			}
			
			if(str.equals("1")){
				manager.setFlag(false);//���ù���ԱȨ��
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	} 
	public static void main(String args[]) throws SQLException{
		new Root("1001");
	}

}

