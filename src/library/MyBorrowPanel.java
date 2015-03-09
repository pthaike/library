package library;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MyBorrowPanel extends JPanel{

	private String readerID;//读者编号
	BorrowInfo borrowInfo;//借阅信息
	private ArrayList<BorrowInfo> borrowInfoList; //接收服务器传来的借阅者的借阅信息
	protected JTable borrowInfoTable; //展示读者信息的表格
	protected JScrollPane bookInLibScrollPane; //存放借阅信息面板
	protected JPanel topPanel;
	protected JFrame parentFrame;
	
	public MyBorrowPanel(JFrame parentFrame,String readerID){
		this.parentFrame=parentFrame;
		this.readerID=readerID;
		this.setLayout(new BorderLayout());
		getBorrowInfo();
		buildGUI();
	}
	
	protected void getBorrowInfo(){
		try{
			borrowInfoList=MainFrame.globalCilent.getReaderBorrowInfo(readerID);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 我的借阅主界面
	 */
	protected void buildGUI(){
		topPanel=new JPanel();
		bookInLibScrollPane=new JScrollPane();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(BorderFactory.createTitledBorder("借阅查询选项"));
		JRadioButton currBorrowButton=new JRadioButton("当前借阅");
		JRadioButton oldBorrowButton=new JRadioButton("历史借阅");
		topPanel.add(currBorrowButton);
		topPanel.add(oldBorrowButton);
		currBorrowButton.addActionListener(new CurrentBorrowInfoListener());
		oldBorrowButton.addActionListener(new OldBorrowInfoListener());
		
		/**
		 * 将两个对象放到ButtonGroup()中，实现二选一
		 */
		ButtonGroup buttonGroup1=new ButtonGroup();
		buttonGroup1.add(currBorrowButton);
		buttonGroup1.add(oldBorrowButton);
		this.add(BorderLayout.NORTH,topPanel);
		
		/**
		 * 显示所有的借阅信息
		 */
		Iterator<BorrowInfo> it=borrowInfoList.iterator();
		Vector allBorrowInfoVector=new Vector(); //存放所有的借阅记录
		while(it.hasNext()){
			borrowInfo=(BorrowInfo) it.next();
			Vector rowVector=new Vector(); //存放每次的借阅内容
			rowVector.add(borrowInfo.getBookName());
			rowVector.add(borrowInfo.getBookAuthors());
			rowVector.add(borrowInfo.getPubilisher());
			rowVector.add(borrowInfo.getBorrowDate());
			rowVector.add(borrowInfo.getDueDate());
			rowVector.add(borrowInfo.getReturnDate());
			rowVector.add(borrowInfo.getOverduedays());
			rowVector.add(borrowInfo.getFineMoney());
			allBorrowInfoVector.add(rowVector);
		}
		Vector borrowHead=new Vector(); //存储表头
		borrowHead.add("书名");
		borrowHead.add("作者");
		borrowHead.add("出版");
		borrowHead.add("借阅日期");
		borrowHead.add("应还日期");
		borrowHead.add("归还日期");
		borrowHead.add("超期天数");
		borrowHead.add("罚款金额");
		//生成具有内容和表头的表格
		borrowInfoTable=new JTable(allBorrowInfoVector,borrowHead);
		borrowInfoTable.setEnabled(false);
		borrowInfoTable.setPreferredScrollableViewportSize(new Dimension(0,120));
		bookInLibScrollPane.setViewportView(borrowInfoTable);
		bookInLibScrollPane.setBorder(BorderFactory.createTitledBorder("借阅信息"));
		this.add(BorderLayout.CENTER,bookInLibScrollPane);
		JPanel bottomPanel=new JPanel();
		JButton okButton=new JButton("确定");
		okButton.addActionListener(new OkButtomActionListener());
		bottomPanel.add(okButton);
		this.add(BorderLayout.SOUTH,bottomPanel);
		this.validate();
	}
	
	/**
	 * 当前借阅监听器
	 */
	class CurrentBorrowInfoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			buildCurrentInfoGUI(borrowInfoList);
		}
	}
	
	/**
	 * 历史借阅监听器
	 */
	class OldBorrowInfoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			buildOldInfoGUI(borrowInfoList);
		}
		
	}
	
	/**
	 * 当前借阅内容的显示输出
	 * @param borrowInfoList
	 */
	private void buildCurrentInfoGUI(ArrayList<BorrowInfo>borrowInfoList){
		Iterator<BorrowInfo> it=borrowInfoList.iterator();
		Vector currVector=new Vector();
		while(it.hasNext()){
			borrowInfo=(BorrowInfo) it.next();
			Date returnDate=borrowInfo.getReturnDate();
			if(returnDate==null){//还书日期为空，说明此书还在读者手中
				Vector rowVector=new Vector();
				rowVector.add(borrowInfo.getBookName());
				rowVector.add(borrowInfo.getBookAuthors());
				rowVector.add(borrowInfo.getPubilisher());
				rowVector.add(borrowInfo.getBorrowDate());
				rowVector.add(borrowInfo.getDueDate());
				currVector.add(rowVector);
			}
		}
		Vector borrowHead=new Vector();
		borrowHead.add("书名");
		borrowHead.add("作者");
		borrowHead.add("出版");
		borrowHead.add("借阅日期");
		borrowHead.add("应还日期");
		//生成具体内容和表头的表格
		borrowInfoTable=new JTable(currVector,borrowHead);
		borrowInfoTable.setEnabled(false);
		borrowInfoTable.setPreferredScrollableViewportSize(new Dimension(0,120));
		bookInLibScrollPane.setViewportView(borrowInfoTable);
		this.validate();
	}
	
	/**
	 * 处理历史借阅信息内容的显示输出
	 */
	private void buildOldInfoGUI(ArrayList<BorrowInfo>borrowInfoList){
		Iterator<BorrowInfo> it=borrowInfoList.iterator();
		Vector oldVector=new Vector();
		while(it.hasNext()){
			borrowInfo=(BorrowInfo)it.next();
			Date returnDate=borrowInfo.getReturnDate();
			if(returnDate!=null){
				Vector rowVector=new Vector();
				rowVector.add(borrowInfo.getBookName());
				rowVector.add(borrowInfo.getBookAuthors());
				rowVector.add(borrowInfo.getPubilisher());
				rowVector.add(borrowInfo.getBorrowDate());
				rowVector.add(borrowInfo.getReturnDate());
				rowVector.add(borrowInfo.getOverduedays());
				rowVector.add(borrowInfo.getFineMoney());
				oldVector.add(rowVector);
			}
			}
		Vector borrowHead=new Vector();
		borrowHead.add("书名");
		borrowHead.add("作者");
		borrowHead.add("出版");
		borrowHead.add("借阅日期");
		borrowHead.add("归还日期");
		borrowHead.add("超期天数");
		borrowHead.add("罚款金额");
		borrowInfoTable=new JTable(oldVector,borrowHead);
		borrowInfoTable.setEnabled(false);
		borrowInfoTable.setPreferredScrollableViewportSize(new Dimension(0,120));
		bookInLibScrollPane.setViewportView(borrowInfoTable);
		this.validate();
	}
	
	/**
	 * 确定按钮监听
	 */
	class OkButtomActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
		
	}
}
