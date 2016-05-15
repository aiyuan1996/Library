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
	private JSplitPane JSpl = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);//上下
	private JPanel jp = new JPanel();//创建一个面板
	int flag;
	DataBase db;
	String sql ;
	private JButton button = new JButton("确定");//确定按钮
	private JLabel jlable1 = new JLabel("您要借阅或预约的书号");
	private JLabel jlable2 = new JLabel("请输入您的学号");
	private JTextField jtxt1 = new JTextField();
	private JTextField jtxt2 = new JTextField();
	//设置安萱按钮
	private JRadioButton[] jrbArray ={ new JRadioButton("借阅图书",true),new JRadioButton("预约图书")};
	private ButtonGroup bg = new ButtonGroup();
	Vector<String>head = new Vector<String>();//创建标题
	{
		head.add("书号");
		head.add("书名");
		head.add("作者");
		head.add("出版社");
		head.add("购买时间");
		head.add("是否借阅");
		head.add("是否预约");
	}
	Vector<Vector>data = new Vector<Vector>();//表格数据向量集合
	DefaultTableModel dtm = new DefaultTableModel(data,head);//创建表格模型
	JTable jt = new JTable(dtm);//创建Jtable
	JScrollPane jspn = new JScrollPane(jt);
	
	public BorrowBook() {
		this.setLayout((new GridLayout(1,1)));
		JSpl.setTopComponent(jp);//把jp设置到上面
		JSpl.setBottomComponent(jspn);//把JScrollPane设置到下面
		JSpl.setDividerSize(4);
		JSpl.setDividerLocation(100);
		jp.setLayout(null);
		
		button.setBounds(380,20,100,20);//设置按钮的大小与位置
		jp.add(button);
		button.addActionListener(this);
		
		//设置JLable的坐标
		jlable1.setBounds(80,60,130,20);
    	jlable2.setBounds(330,60,100,20);
    	//把JLable添加进JPanel
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
			if(jtxt2.getText().equals("")){//为输入为空的情况进行处理
				JOptionPane.showMessageDialog(this,"输入不能为空，请重新输入！！！",
				                      "信息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//查询学号文本中所输学号是否存在于STUDENT表中
			String sql = "select *from student where stuNo = " + Integer.parseInt(jtxt2.getText().trim());
			try {
				db = new DataBase();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			db.selectDb(sql);
			Vector<Vector>vtemp = new Vector<Vector>();//创建向量，用于存放表格数据
			try {
				if(!(db.resultset.next())){
					//若学号有误，输出提示对话框
					JOptionPane.showMessageDialog(this, "学号错误", "消息", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					String stuName = db.resultset.getString("stuName").trim();//得到学生姓名
					String classes = db.resultset.getString("class").trim();//得到学生班级
					stuName = new String(stuName.getBytes(),"gb2312");
					classes = new String(classes.getBytes(),"gb2312");
					
					//若学号正确，则检查该学生是否有权限借书或预约
					if(db.resultset.getString("permitted").equals("否")){
						JOptionPane.showMessageDialog(this, "您无此权限", "消息", JOptionPane.INFORMATION_MESSAGE);
					}
					else {//若有权限，则查找所输入的书号是否存在于Book表中
						
						sql = "select *from book where bookNo = " + Integer.parseInt(jtxt1.getText().trim());
						db.selectDb(sql);
						do{
							
							if(!db.resultset.next()){
								JOptionPane.showMessageDialog(this, "没有您要查找的内容", "消息", JOptionPane.INFORMATION_MESSAGE);
							}
							Vector<String>v = new Vector<String>();//创建向量
							for(int i = 1;i <= 7;i++){
								//顺序得到查找结果中的各项记录
								if(i == 1){
									String str = db.resultset.getString("BookNo").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//添加到向量中
								}
								
								if(i == 2){
									String str = db.resultset.getString("BookName").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//添加到向量中
								}
								if(i == 3){
									String str = db.resultset.getString("author").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//添加到向量中
								}
								if(i == 4){
									String str = db.resultset.getString("publishment").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//添加到向量中
								}
								if(i == 5){
									String str = db.resultset.getString("buytime").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//添加到向量中
								}
								if(i == 6){
									String str = db.resultset.getString("borrowed").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//添加到向量中
								}
								if(i == 7){
									String str = db.resultset.getString("ordered").trim();
									str = new String(str.getBytes(),"gb2312");
									v.add(str);//添加到向量中
								}
							}
							vtemp.add(v);//更新结果框的内容
							dtm.setDataVector(vtemp, head);//设置表格显示内容
							jt.updateUI();
							jt.repaint();
							
							if(jrbArray[0].isSelected()){//选择了借图书
								if(db.resultset.getString("Borrowed").trim().equals("是")){
									JOptionPane.showMessageDialog(this, "此书已经被借走", "消息", JOptionPane.INFORMATION_MESSAGE);
								}
								else if(db.resultset.getString("ordered").trim().equals("是")){
									JOptionPane.showMessageDialog(this, "此书已经被预约，不能借", "消息", JOptionPane.INFORMATION_MESSAGE);
								}
								else {
									
									java.util.Date now = new java.util.Date();//获取当前日期来记录借书时间和还书时间
									sql = "update book set borrowed = '是' where bookNo = " + Integer.parseInt(jtxt1.getText().trim());
									db.updateDb(sql);//更新表
									JOptionPane.showMessageDialog(this, "借书成功", "消息", JOptionPane.INFORMATION_MESSAGE);
									sql="insert into RECORD values("+Integer.parseInt(jtxt1.getText().trim())+","
										    +Integer.parseInt(jtxt2.getText().trim())+",'"+(now.getYear()+1900)+"."
										    +(now.getMonth()+1)+"."+now.getDate()+"',"+"'"+(now.getYear()+1900)+"."
										    +(now.getMonth()+2)+"."+now.getDate()+"','否','否')";
										db.updateDb(sql);//将该书记录插入Record表中
								}
							}
							if(jrbArray[1].isSelected()){//选择了预约图书
								if(db.resultset.getString("ordered").trim().equals("是")){
									JOptionPane.showMessageDialog(this, "此书已经被预约", "消息", JOptionPane.INFORMATION_MESSAGE);
								}
								else {
									sql = "update book set ordered = '是' where bookno = " + Integer.parseInt(jtxt1.getText().trim());
									db.updateDb(sql);//执行更新操作
									JOptionPane.showMessageDialog(this, "预约成功","消息",JOptionPane.INFORMATION_MESSAGE);
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
