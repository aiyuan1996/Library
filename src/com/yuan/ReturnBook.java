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
	//创建分割方向为上下的JsplitPane对象
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	//创建JPanel对象
	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	//创建按钮数组
	private JButton jbArray[] = new JButton[]
			{new JButton("挂失"),new JButton("归还"),new JButton("确定")};
	private JLabel jl = new JLabel("学号");
	private JTextField jtxt = new JTextField();
	//创建标题
	Vector<String>head = new Vector<String>();
	{
		head.add("书号");
		head.add("学号");
		head.add("借阅时间");
		head.add("还书时间");
		head.add("是否过期");
		head.add("是否预约");
	}
	Vector<Vector>data=new Vector<Vector>();
	DefaultTableModel dtm = new DefaultTableModel(data,head);//创建表格模型
	JTable jt = new JTable(dtm);//创建Jtable对象
	//将jTable封装到滚动窗格
	JScrollPane jspn = new JScrollPane(jt);
	
	
	public ReturnBook() {
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);
		jpb.setLayout(null);
		//设置Label的大小及位置
        jl.setBounds(5,15,100,20);
        //将Jlabel添加到jpt面板上
		jpt.add(jl);
		//为JTextField设置大小及位置
		jtxt.setBounds(105,15,300,20);
	    //把JTextField添加到jpt
		jpt.add(jtxt);
		//设置JBuuton的大小及位置
	    jbArray[0].setBounds(5,50,100,20);
        jbArray[1].setBounds(150,50,100,20);
        jbArray[2].setBounds(295,50,100,20);
        //添加JButton并给其添加事件监听器
        for(int i=0;i<3;i++)
		{
			 jpt.add(jbArray[i]);
			 jbArray[i].addActionListener(this);
		}
        jsp.setTopComponent(jpt);//jpt设置到上面窗格
        jsp.setBottomComponent(jspn);
        jsp.setDividerSize(4);
    	this.add(jsp);
    	//设置jsp中分割条的初始位置
    	jsp.setDividerLocation(80);
		//设置窗体的大小位置及可见性
        this.setBounds(10,10,800,600);
        this.setVisible(true); 
        
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jbArray[2]){
			if(jtxt.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "请输入学号", "消息", JOptionPane.INFORMATION_MESSAGE);
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
				Vector<Vector>vtemp = new Vector<Vector>();//创建向量
				try {
					int k =0;
					while(db.resultset.next()){//遍历结果集
						k++;
						Vector<String>v = new Vector<String>();
						for(int i =1;i < 7;i++){
							String str = db.resultset.getString(i);
							str = new String(str.getBytes(),"gb2312");
							v.add(str);
						}
						vtemp.add(v);
						jt.clearSelection();//清空所选行
						dtm.setDataVector(vtemp, head);//设置表格数据
						jt.updateUI();
						jt.repaint();
					}
					if(k == 0){
						JOptionPane.showMessageDialog(this, "输入了错误的学号或该学生没有借书记录", "消息", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		if(e.getSource() == jbArray[1]){//归还图书
			
			int row=jt.getSelectedRow();
			if(row<0){//如果未选中下部表中的某些内容，进行提示
	   			
				JOptionPane.showMessageDialog(this,"请选择要归还的书!!!","消息",
					                              JOptionPane.INFORMATION_MESSAGE);
				return;
   			}
			str = (String) jt.getValueAt(row, 0);
			int sno=Integer.parseInt((String)jt.getValueAt(row,1));//学号
   			int bno=Integer.parseInt(str);//书号

   			int flag=checkTime(sno,bno);	//判断是否超期
   			if(flag==-1){//如果图书超期，则将取消该同学的借书权限
   				try {
					db=new DataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
   				sql="update student set permitted='否' where StuNO="+sno;
   				db.updateDb(sql);
   				db.dbClose();
   				if(flag==0){return;}//如果图书未超期，则进行归还操作
   				sql="Delete from RECORD where BookNO="+Integer.parseInt(str);
   	   			try {
					db=new DataBase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
   	   			db.updateDb(sql);
   	   			sql="update book set borrowed='否' where BookNO="+Integer.parseInt(str);
   	   			db.updateDb(sql);//更新了图书记录，设该书号的数为可借
   	   			db.dbClose();
   				updateTable();
   			}
   			
		}
		if(e.getSource()==jbArray[0]){//需要挂失图书
			int row=jt.getSelectedRow();
			if(row<0){
				JOptionPane.showMessageDialog(this,"请选择要挂失的书!!!","消息",
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
		int bno = Integer.parseInt((String) jt.getValueAt(row, 0));//得到丢失图书的书号
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
			if(db.resultset.next()){//遍历结果集
				bname = db.resultset.getString("bookname").trim();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql = "select MAX(lbno) from Losebook ";//找到最大的丢失记录号的SQL语句
		db.selectDb(sql);
		try {
			if(db.resultset.next()){
				lbno = db.resultset.getInt("bookNo");
				lbno++;
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		sql="insert into LOSEBOOK values("+lbno+","+sno+","+bno+",'"+bname+"')";//向丢书记录表中插入记录
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
			JOptionPane.showMessageDialog(this, "恭喜你，挂失成功", "消息", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		else {
			JOptionPane.showMessageDialog(this, "不好意思，挂失失败", "消息", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
	}


	public void updateTable(){//实现界面下部表格的更新
		
		sql="select * from RECORD where StuNO="+jtxt.getText().trim();
		try {
			db=new DataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.selectDb(sql);//将图书信息从数据库中取出
		Vector<Vector> vtemp = new Vector<Vector>();
		try{
			while(db.resultset.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<7;i++){//将每列添加到临时数组v
					String str=db.resultset.getString(i);
//					str=new String(str.getBytes("ISO-8859-1"),"gb2312");
					v.add(str);
					
				}
				vtemp.add(v);//将各条记录添加到临时数组vtemp
			}
			db.dbClose();	
		}
		catch(Exception ea){ea.printStackTrace();}
		jt.clearSelection();			
		dtm.setDataVector(vtemp,head);//更新table	
		jt.updateUI();
		jt.repaint();   		
   	}	


	private int checkTime(int sno, int bno) {
		int day =0;//用于记录超期天数
		int flag = 0;
		String bname = "";//书名
		Date now = new Date();
		String returntime = "";
		sql = "select returntime *from record where StuNo = " + sno + "and BookNo = " + bno;
		try {
			db = new DataBase();//从借书时间表中查询某人借的某书的归还时间
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.selectDb(sql);//执行查询
		try {
			if(db.resultset.next()){
				returntime = db.resultset.getString("returntime");//获取归还时间
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String[] strday = returntime.split("\\.");//使用正则表达式验证时间的格式
		int ryear = Integer.parseInt(strday[0].trim());
		int rmonth = Integer.parseInt(strday[1].trim());
		int rday = Integer.parseInt(strday[2].trim());
		day = (now.getYear()+1900-ryear)*365 + (now.getMonth()+1-rmonth)*30 + (now.getDate()-rday);//超期天数
		System.out.println(day);
		
		return 0;
	}


	public static void main(String args[]){
		new ReturnBook();
	}

}
