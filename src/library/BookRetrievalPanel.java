package library;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * ������Ϣ�������Ϊ�����ṩ��ѯ����
 * @author lenovo
 *
 */
public class BookRetrievalPanel extends JPanel{

	protected BookDetails bookDetails;//���ڱ����ѯ����ͼ��Ļ�������Ϣ
	protected BookDetailsDialog bookDetailsDialog; //ͼ����ϸ��Ϣ�Ի���
	protected ArrayList<BookDetails> bookArrayList; //����ͼ���ѯ���
	protected JLabel selectionLabel; //ѡ��ʽ��ǩ
	protected JComboBox fieldComboBox; //����ʽ���
	protected JTextField keywordText;
	protected JPanel topPanel; //�ϲ����
	protected JList bookListBox; //��ʾͼ���ѯ�б�
	protected JScrollPane bookScrollPane;//�������
	protected JButton retrievalButton; //������ť
	protected JButton detailsButton;//���ڲ鿴��ѯ�����ÿ����ݲصİ�ť
	protected JButton delBookButton;
	protected JPanel bottomPanel;//�²����
	protected MainFrame parentFrame; //���ุ����
	protected String retrievalField; //������ʽֵ
	protected LibClient libClient;
	
	public BookRetrievalPanel(MainFrame theParentFrame){
		parentFrame=theParentFrame;
		this.setLayout(new BorderLayout());
		selectionLabel=new JLabel("������ʽ");
		fieldComboBox=new JComboBox();
		fieldComboBox.addItem("��ѡ��");
		fieldComboBox.addItem("����");
		fieldComboBox.addItem("ISBN��");
		fieldComboBox.addItem("����");
		fieldComboBox.addItem("����");
		fieldComboBox.addItemListener(new FieldSelectedListener());
		keywordText=new JTextField("", 20);//�ؼ���
		keywordText.addMouseListener(new KeywordClickedListener());
		keywordText.addKeyListener(new KeywordKeyListener());
		retrievalButton=new JButton("����");
		retrievalButton.addActionListener(new RetrievalActionListener());
		topPanel=new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		keywordText.setSize(topPanel.getWidth()/2,topPanel.getWidth());
		topPanel.add(selectionLabel);
		topPanel.add(fieldComboBox);
		topPanel.add(keywordText);
		topPanel.add(retrievalButton);
		this.add(BorderLayout.NORTH,topPanel);
		bookListBox=new JList();
		bookListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookListBox.addListSelectionListener(new BookSelectionListener());
		bookListBox.addMouseListener(new BookListMouseClickListener());
		bookScrollPane=new JScrollPane(bookListBox);
		this.add(BorderLayout.CENTER,bookScrollPane);
		detailsButton=new JButton("��ϸ...");
		detailsButton.addActionListener(new DetailsActionListener());
		detailsButton.setEnabled(false);
		delBookButton=new JButton("ɾ��");
		delBookButton.addActionListener(new DelBookListener());
		delBookButton.setVisible(false);
		delBookButton.setEnabled(false);
		bottomPanel=new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(detailsButton);
		bottomPanel.add(delBookButton);
		
		this.add(BorderLayout.SOUTH,bottomPanel);
	}
	
	//����Աɾ��ͼ�鰴ť
	public void setManageDel(){
		delBookButton.setVisible(true);
		
	}
	
	//��������ͼ��ɾ����ť
	public void setReaderDel(){
		delBookButton.setVisible(false);
	}
	
	protected class DelBookListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			int option=JOptionPane.showConfirmDialog(BookRetrievalPanel.this, "�Ƿ�Ҫɾ���鼮","��ʾ",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option==0){
				int index=bookListBox.getSelectedIndex();
				bookDetails=(BookDetails)bookArrayList.get(index);
				libClient=MainFrame.globalCilent;
				int result;
				try {
					result = libClient.delBook(bookDetails.getIsbn());
					if(0==result){
						JOptionPane.showMessageDialog(BookRetrievalPanel.this,"���Ѿ������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					}else if(1==result){
						JOptionPane.showMessageDialog(BookRetrievalPanel.this,"ɾ���ɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(BookRetrievalPanel.this,"ɾ��ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * ����ͼ���б����ʾ,��ѯ�����ʾ
	 */
	protected void ProcessBookList(String theField,String theKeyword){
		try{
			bookArrayList=MainFrame.globalCilent.getBookList(theField,theKeyword);
			if(bookArrayList.size()>0){
				Object[]theData=bookArrayList.toArray();
				bookListBox.setListData(theData);
			}else{
				Object[]noData=new Object[0];
				bookListBox.setListData(noData);
				fieldComboBox.setSelectedItem(false);
				detailsButton.setEnabled(false);
				JOptionPane.showMessageDialog(null, "û���ҵ���Ҫ�ҵ�ͼ��");
			}
		}catch(IOException e){
			JOptionPane.showMessageDialog(this, "���紦����"+e, "��������", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ѡ���Ĳ�ѯ�������ʾͼ����ϸ��Ϣ
	 * 
	 */
	private void DisplayBookDetailsDialog(){
		int index=bookListBox.getSelectedIndex();
		bookDetails=(BookDetails)bookArrayList.get(index);
		bookDetailsDialog=new BookDetailsDialog(parentFrame,bookDetails,MainFrame.globalCilent);
		bookDetailsDialog.setVisible(true);
	}
	
	/**
	 * �����������Ĳ�ѯ�����Ƿ���Ч
	 */
	private void RetrievalResults(){
		Hashtable<String,String> bookHashTable=new Hashtable<String,String>();
		bookHashTable.put("����", "name");
		bookHashTable.put("����", "authors");
		bookHashTable.put("������", "publisher");
		bookHashTable.put("ISBN��", "isbn");
		if(retrievalField==null||retrievalField.startsWith("��ѡ��")){
			JOptionPane.showMessageDialog(null, "��ѡ�������ʽ");
			return;
		}
		String keyword=keywordText.getText();
		if(keyword==null||keyword.equals("")){
			JOptionPane.showMessageDialog(null, "�����ؼ��ֲ���Ϊ��");
			return;
		}
		String field=bookHashTable.get(retrievalField);
		ProcessBookList(field,keyword);
	}
	
	/**
	 * ������ʽ������
	 */
	class FieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				retrievalField=(String) fieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	/**
	 * �ؼ����ı�������,˫��ʱȫѡ���ڵ�����
	 */
	class KeywordClickedListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getClickCount()==2){
				keywordText.setSelectionStart(0);
				keywordText.setSelectionEnd(keywordText.getText().length());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * �ؼ����ı��������,�������̻س�ִ��
	 */
	/**
	 * @author lenovo
	 *
	 */
	class KeywordKeyListener extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				RetrievalResults();
			}
		}
		
	}
	
	/**
	 * ������ť�����������������ť������ʱ���¼�
	 */
	class RetrievalActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			RetrievalResults();
		}
		
	}
	
	/**
	 * ��ϸ��ť��������������ϸ��ť��������¼�
	 */
	class DetailsActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			DisplayBookDetailsDialog();
		}
		
	}
	
	/**
	 * ��ѯ����б�����������б�ѡ��������ϸ��ť���ã����򲻿���
	 */
	class BookSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if(bookListBox.isSelectionEmpty()){
				detailsButton.setEnabled(false);
				delBookButton.setEnabled(false);
			}else{
				detailsButton.setEnabled(true);
				delBookButton.setEnabled(true);
			}
		}
		
	}
	
	/**
	 * ��ѯ����б������,����ĳ�����˫��������ʾ����ϸ��Ϣ�Ի���
	 */
	class BookListMouseClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(!detailsButton.isEnabled()){
				return;
			}
			if(e.getClickCount()==2){
				DisplayBookDetailsDialog();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
