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
 * 读书信息检索面板为读者提供查询界面
 * @author lenovo
 *
 */
public class BookRetrievalPanel extends JPanel{

	protected BookDetails bookDetails;//用于保存查询到的图书的基本呢信息
	protected BookDetailsDialog bookDetailsDialog; //图书详细信息对话框
	protected ArrayList<BookDetails> bookArrayList; //保存图书查询结果
	protected JLabel selectionLabel; //选择方式标签
	protected JComboBox fieldComboBox; //下拉式表框
	protected JTextField keywordText;
	protected JPanel topPanel; //上部面板
	protected JList bookListBox; //显示图书查询列表
	protected JScrollPane bookScrollPane;//滚动面板
	protected JButton retrievalButton; //检索按钮
	protected JButton detailsButton;//用于查看查询结果中每本书馆藏的按钮
	protected JButton delBookButton;
	protected JPanel bottomPanel;//下部面板
	protected MainFrame parentFrame; //该类父窗口
	protected String retrievalField; //检索方式值
	protected LibClient libClient;
	
	public BookRetrievalPanel(MainFrame theParentFrame){
		parentFrame=theParentFrame;
		this.setLayout(new BorderLayout());
		selectionLabel=new JLabel("检索方式");
		fieldComboBox=new JComboBox();
		fieldComboBox.addItem("请选择");
		fieldComboBox.addItem("书名");
		fieldComboBox.addItem("ISBN号");
		fieldComboBox.addItem("作者");
		fieldComboBox.addItem("出版");
		fieldComboBox.addItemListener(new FieldSelectedListener());
		keywordText=new JTextField("", 20);//关键字
		keywordText.addMouseListener(new KeywordClickedListener());
		keywordText.addKeyListener(new KeywordKeyListener());
		retrievalButton=new JButton("检索");
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
		detailsButton=new JButton("详细...");
		detailsButton.addActionListener(new DetailsActionListener());
		detailsButton.setEnabled(false);
		delBookButton=new JButton("删除");
		delBookButton.addActionListener(new DelBookListener());
		delBookButton.setVisible(false);
		delBookButton.setEnabled(false);
		bottomPanel=new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(detailsButton);
		bottomPanel.add(delBookButton);
		
		this.add(BorderLayout.SOUTH,bottomPanel);
	}
	
	//管理员删除图书按钮
	public void setManageDel(){
		delBookButton.setVisible(true);
		
	}
	
	//读者隐藏图书删除按钮
	public void setReaderDel(){
		delBookButton.setVisible(false);
	}
	
	protected class DelBookListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			int option=JOptionPane.showConfirmDialog(BookRetrievalPanel.this, "是否要删除书籍","提示",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option==0){
				int index=bookListBox.getSelectedIndex();
				bookDetails=(BookDetails)bookArrayList.get(index);
				libClient=MainFrame.globalCilent;
				int result;
				try {
					result = libClient.delBook(bookDetails.getIsbn());
					if(0==result){
						JOptionPane.showMessageDialog(BookRetrievalPanel.this,"书已经被借出","提示",JOptionPane.INFORMATION_MESSAGE);
					}else if(1==result){
						JOptionPane.showMessageDialog(BookRetrievalPanel.this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(BookRetrievalPanel.this,"删除失败","提示",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 处理图书列表的显示,查询结果显示
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
				JOptionPane.showMessageDialog(null, "没有找到您要找的图书");
			}
		}catch(IOException e){
			JOptionPane.showMessageDialog(this, "网络处故障"+e, "网络问题", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据选定的查询结果，显示图书详细信息
	 * 
	 */
	private void DisplayBookDetailsDialog(){
		int index=bookListBox.getSelectedIndex();
		bookDetails=(BookDetails)bookArrayList.get(index);
		bookDetailsDialog=new BookDetailsDialog(parentFrame,bookDetails,MainFrame.globalCilent);
		bookDetailsDialog.setVisible(true);
	}
	
	/**
	 * 处理读者输入的查询条件是否有效
	 */
	private void RetrievalResults(){
		Hashtable<String,String> bookHashTable=new Hashtable<String,String>();
		bookHashTable.put("书名", "name");
		bookHashTable.put("作者", "authors");
		bookHashTable.put("出版社", "publisher");
		bookHashTable.put("ISBN号", "isbn");
		if(retrievalField==null||retrievalField.startsWith("请选择")){
			JOptionPane.showMessageDialog(null, "请选择检索方式");
			return;
		}
		String keyword=keywordText.getText();
		if(keyword==null||keyword.equals("")){
			JOptionPane.showMessageDialog(null, "检索关键字不能为空");
			return;
		}
		String field=bookHashTable.get(retrievalField);
		ProcessBookList(field,keyword);
	}
	
	/**
	 * 检索方式监听器
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
	 * 关键字文本监听器,双击时全选框内的文字
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
	 * 关键字文本框监听类,监听键盘回车执行
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
	 * 检索按钮监听器，处理检索按钮被激活时的事件
	 */
	class RetrievalActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			RetrievalResults();
		}
		
	}
	
	/**
	 * 详细按钮监听器，处理详细按钮被激活的事件
	 */
	class DetailsActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			DisplayBookDetailsDialog();
		}
		
	}
	
	/**
	 * 查询结果列表监听器，若有被选择的项，则详细按钮可用，否则不可用
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
	 * 查询结果列表监听器,若对某个结果双击，则显示其详细信息对话框
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
