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

	private String readerID;//���߱��
	BorrowInfo borrowInfo;//������Ϣ
	private ArrayList<BorrowInfo> borrowInfoList; //���շ����������Ľ����ߵĽ�����Ϣ
	protected JTable borrowInfoTable; //չʾ������Ϣ�ı��
	protected JScrollPane bookInLibScrollPane; //��Ž�����Ϣ���
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
	 * �ҵĽ���������
	 */
	protected void buildGUI(){
		topPanel=new JPanel();
		bookInLibScrollPane=new JScrollPane();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(BorderFactory.createTitledBorder("���Ĳ�ѯѡ��"));
		JRadioButton currBorrowButton=new JRadioButton("��ǰ����");
		JRadioButton oldBorrowButton=new JRadioButton("��ʷ����");
		topPanel.add(currBorrowButton);
		topPanel.add(oldBorrowButton);
		currBorrowButton.addActionListener(new CurrentBorrowInfoListener());
		oldBorrowButton.addActionListener(new OldBorrowInfoListener());
		
		/**
		 * ����������ŵ�ButtonGroup()�У�ʵ�ֶ�ѡһ
		 */
		ButtonGroup buttonGroup1=new ButtonGroup();
		buttonGroup1.add(currBorrowButton);
		buttonGroup1.add(oldBorrowButton);
		this.add(BorderLayout.NORTH,topPanel);
		
		/**
		 * ��ʾ���еĽ�����Ϣ
		 */
		Iterator<BorrowInfo> it=borrowInfoList.iterator();
		Vector allBorrowInfoVector=new Vector(); //������еĽ��ļ�¼
		while(it.hasNext()){
			borrowInfo=(BorrowInfo) it.next();
			Vector rowVector=new Vector(); //���ÿ�εĽ�������
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
		Vector borrowHead=new Vector(); //�洢��ͷ
		borrowHead.add("����");
		borrowHead.add("����");
		borrowHead.add("����");
		borrowHead.add("��������");
		borrowHead.add("Ӧ������");
		borrowHead.add("�黹����");
		borrowHead.add("��������");
		borrowHead.add("������");
		//���ɾ������ݺͱ�ͷ�ı��
		borrowInfoTable=new JTable(allBorrowInfoVector,borrowHead);
		borrowInfoTable.setEnabled(false);
		borrowInfoTable.setPreferredScrollableViewportSize(new Dimension(0,120));
		bookInLibScrollPane.setViewportView(borrowInfoTable);
		bookInLibScrollPane.setBorder(BorderFactory.createTitledBorder("������Ϣ"));
		this.add(BorderLayout.CENTER,bookInLibScrollPane);
		JPanel bottomPanel=new JPanel();
		JButton okButton=new JButton("ȷ��");
		okButton.addActionListener(new OkButtomActionListener());
		bottomPanel.add(okButton);
		this.add(BorderLayout.SOUTH,bottomPanel);
		this.validate();
	}
	
	/**
	 * ��ǰ���ļ�����
	 */
	class CurrentBorrowInfoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			buildCurrentInfoGUI(borrowInfoList);
		}
	}
	
	/**
	 * ��ʷ���ļ�����
	 */
	class OldBorrowInfoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			buildOldInfoGUI(borrowInfoList);
		}
		
	}
	
	/**
	 * ��ǰ�������ݵ���ʾ���
	 * @param borrowInfoList
	 */
	private void buildCurrentInfoGUI(ArrayList<BorrowInfo>borrowInfoList){
		Iterator<BorrowInfo> it=borrowInfoList.iterator();
		Vector currVector=new Vector();
		while(it.hasNext()){
			borrowInfo=(BorrowInfo) it.next();
			Date returnDate=borrowInfo.getReturnDate();
			if(returnDate==null){//��������Ϊ�գ�˵�����黹�ڶ�������
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
		borrowHead.add("����");
		borrowHead.add("����");
		borrowHead.add("����");
		borrowHead.add("��������");
		borrowHead.add("Ӧ������");
		//���ɾ������ݺͱ�ͷ�ı��
		borrowInfoTable=new JTable(currVector,borrowHead);
		borrowInfoTable.setEnabled(false);
		borrowInfoTable.setPreferredScrollableViewportSize(new Dimension(0,120));
		bookInLibScrollPane.setViewportView(borrowInfoTable);
		this.validate();
	}
	
	/**
	 * ������ʷ������Ϣ���ݵ���ʾ���
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
		borrowHead.add("����");
		borrowHead.add("����");
		borrowHead.add("����");
		borrowHead.add("��������");
		borrowHead.add("�黹����");
		borrowHead.add("��������");
		borrowHead.add("������");
		borrowInfoTable=new JTable(oldVector,borrowHead);
		borrowInfoTable.setEnabled(false);
		borrowInfoTable.setPreferredScrollableViewportSize(new Dimension(0,120));
		bookInLibScrollPane.setViewportView(borrowInfoTable);
		this.validate();
	}
	
	/**
	 * ȷ����ť����
	 */
	class OkButtomActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
		
	}
}
