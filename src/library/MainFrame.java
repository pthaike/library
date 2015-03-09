package library;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainFrame extends JFrame{

	protected JTabbedPane tabbedPane;
	protected JMenuItem lrMenu;
	protected JMenu sysMainMenu;
	protected BookRetrievalPanel bookQueryPanel;
	protected ReaderLoginDialog readerLoginDialog;
	protected ReaderManageDialog readerManageDialog;
	protected BookManageDialog bookManageDialog;
	protected ManagerLoginDialog managerLoginDialog;
	protected BookBorrowDialog bookBorrowDialog;
	protected ReturnBookDialog returnBookDialog;
	protected AddBookDialog addBookDialog;
	public static LibClient globalCilent;
	
	public MainFrame(){
		connectToServer();//���ӷ�����
		this.setTitle("ͼ��ݻ�ӭ����");
		
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		
		tabbedPane=new JTabbedPane();
		bookQueryPanel=new BookRetrievalPanel(this);
		tabbedPane.addTab("��Ŀ����", bookQueryPanel);
		container.add(BorderLayout.CENTER,tabbedPane);
		
		//�����˵�
		JMenuBar menuBar=new JMenuBar();
		buildMainMenu(menuBar);
		this.setJMenuBar(menuBar);
		
		//���ô���λ��
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(600,450);
		Dimension framesize=this.getSize();
		int x=(int)screensize.getWidth()/2-(int)framesize.getWidth()/2;
		int y=(int)screensize.getHeight()/2-(int)framesize.getHeight()/2;
		setLocation(x,y);
		this.addWindowListener(new WindowCloser());
		
	}
	
	public void setManage(){
		lrMenu.setVisible(true);
		sysMainMenu.setVisible(true);
		bookQueryPanel.setManageDel();
	}
	
	public void setReader(){
		lrMenu.setVisible(false);
		sysMainMenu.setVisible(false);
		bookQueryPanel.setReaderDel();
	}
	
	protected void connectToServer(){
		try{
			ServerInfoGetter serverInfo=new ServerInfoGetter();
			globalCilent=new LibClient(serverInfo.getHost(),serverInfo.getPort());
		}catch(IOException e){
			System.out.println("����������ʧ��!");
			JOptionPane.showMessageDialog(null, "������i����ʧ��");
			exitSystem();
		}
	}
	
	protected void buildMainMenu(JMenuBar menuBar){
		JMenu fileMenu=new JMenu("�ļ�(F)");
		fileMenu.setMnemonic(KeyEvent.VK_F);//���ļ��˵��������Ǽ�
		JMenuItem exitMenuItem=new JMenuItem("�˳�");
		exitMenuItem.setMnemonic(KeyEvent.VK_F);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));//�趨��ݼ�
		exitMenuItem.addActionListener(new ExitActionListener());  //���˳��˵����Ӽ�����
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		setupBookRetrievalMenu(menuBar);
		setupLendReturnMenu(menuBar);
		setupMaintainMenu(menuBar);
		setupLookAndFeelMenu(menuBar);
		
		JMenu helpMenu=new JMenu("����(H)");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem aboutMenuItem=new JMenuItem("����");
		aboutMenuItem.setMnemonic(KeyEvent.VK_A);
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		aboutMenuItem.addActionListener(new AboutActionListener());
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);
	}
	
	protected void setupBookRetrievalMenu(JMenuBar menuBar){
		JMenu libMenu=new JMenu("�ݲؼ���(B)");
		libMenu.setMnemonic(KeyEvent.VK_B);
		JMenuItem libMenuItem=new JMenuItem("��Ŀ����");
		JMenuItem myBorrowMenuItem =new JMenuItem("�ҵĽ���");
		JMenuItem managerLoginMenu=new JMenuItem("����Ա��¼");
		libMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
		libMenuItem.addActionListener(new BookInLibraryActionListener());
		myBorrowMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.CTRL_MASK));
		myBorrowMenuItem.addActionListener(new MyBorrowActionListener());
		managerLoginMenu.addActionListener(new ManagerLoginListener());
		libMenu.add(libMenuItem);
		libMenu.add(myBorrowMenuItem);
		libMenu.add(managerLoginMenu);
		menuBar.add(libMenu);
	}
	
	/**
	 * ��������˵�����������ͻ���2���˵���
	 */
	protected void setupLookAndFeelMenu(JMenuItem menuBar){
		lrMenu=new JMenu("���黹��(E)");
		lrMenu.setMnemonic(KeyEvent.VK_E);
		JMenuItem lendMenuItem=new JMenuItem("����");
		JMenuItem returnMenuItem=new JMenuItem("����");
		lendMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
		lendMenuItem.addActionListener(new LendActionListener());
		returnMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		returnMenuItem.addActionListener(new ReturnActionListener());
		lrMenu.add(lendMenuItem);
		lrMenu.add(returnMenuItem);
		menuBar.add(lrMenu);
		lrMenu.setVisible(false);
	}
	
	/**
	 * ��������˵�
	 */
	protected void setupLendReturnMenu(JMenuBar menuBar){
		lrMenu=new JMenu("���黹��(E)");
		lrMenu.setMnemonic(KeyEvent.VK_E);
		JMenuItem lendMenuItem=new JMenuItem("����");
		JMenuItem returnMenuItem=new JMenuItem("����");
		lendMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
		lendMenuItem.addActionListener(new LendActionListener());
		returnMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		returnMenuItem.addActionListener(new ReturnActionListener());
		lrMenu.add(lendMenuItem);
		lrMenu.add(returnMenuItem);
		menuBar.add(lrMenu);
		lrMenu.setVisible(false);
	}
	
	/**
	 * ����ϵͳά���˵�������ͼ��ά��������ά��������Աά����ϵͳά��4���˵���
	 */
	protected void setupMaintainMenu(JMenuBar menuBar){
		sysMainMenu=new JMenu("ϵͳά��(M)");
		sysMainMenu.setMnemonic(KeyEvent.VK_M);
		JMenuItem bookMenuItem=new JMenuItem("����ͼ��");
		JMenuItem readerMenuItem=new JMenuItem("����ά��");
		JMenuItem librarianMenuItem=new JMenuItem("����Աά��");
		JMenuItem paraMenuItem=new JMenuItem("ϵͳ����ά��");
		bookMenuItem.addActionListener(new addBookMenuListener());
		readerMenuItem.addActionListener(new readerManageListener());
		librarianMenuItem.addActionListener(new UndefineActionListener());
		paraMenuItem.addActionListener(new UndefineActionListener());
		sysMainMenu.add(bookMenuItem);
		sysMainMenu.add(readerMenuItem);
		//sysMainMenu.add(librarianMenuItem);
		//sysMainMenu.add(paraMenuItem);
		menuBar.add(sysMainMenu);
		sysMainMenu.setVisible(false);
	}
	
	protected class addBookMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addBookDialog=new AddBookDialog(MainFrame.this);
			addBookDialog.setVisible(true);
		}
		
	}
	
	/**
	 * ���߹���˵�������
	 * @author lenovo
	 *
	 */
	protected class readerManageListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			readerManageDialog=new ReaderManageDialog(MainFrame.this);
			readerManageDialog.setVisible(true);
		}
		
	}
	
	/**
	 * 
	 * @author lenovo
	 *
	 */
	protected class bookManageListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			bookManageDialog=new BookManageDialog(MainFrame.this);
			bookManageDialog.setVisible(true);
		}
		
	}
	
	/**
	 * ���������
	 * @author lenovo
	 *
	 */
	protected class ReturnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			returnBookDialog=new ReturnBookDialog(MainFrame.this);
			returnBookDialog.setVisible(true);
		}
		
	}
	
	protected class LendActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			bookBorrowDialog=new BookBorrowDialog(MainFrame.this);
			bookBorrowDialog.setVisible(true);
		}
		
	}
	
	/**
	 * 
	 * @author lenovo
	 *
	 */
	protected class ManagerLoginListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			managerLoginDialog=new ManagerLoginDialog(MainFrame.this);
			managerLoginDialog.setVisible(true);
		}
		
	}
	
	/**
	 * �������ѡ��˵�,���û��������۷��
	 */
	protected void setupLookAndFeelMenu(JMenuBar menuBar){
		UIManager.LookAndFeelInfo[] lookAndFeelInfo=UIManager.getInstalledLookAndFeels();
		JMenu lookAndFeelMenu=new JMenu("���(S)");
		lookAndFeelMenu.setMnemonic(KeyEvent.VK_S);
		lookAndFeelMenu.setMnemonic(KeyEvent.VK_S);
		JMenuItem anItem=null;
		LookAndFeelListener myListener=new LookAndFeelListener();
		try{
			for(int i=0;i<lookAndFeelInfo.length;i++){
				anItem=new JMenuItem(lookAndFeelInfo[i].getName()+"���");
				anItem.setActionCommand(lookAndFeelInfo[i].getClassName());
				anItem.addActionListener(myListener);
				lookAndFeelMenu.add(anItem);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		menuBar.add(lookAndFeelMenu);
	}
	
	/**
	 * �˳�ϵͳ
	 */
	public void exitSystem(){
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	/**
	 * ͼ����������ڶ�̬����ͼ���������
	 */
	public void bookRetrieval(){
		tabbedPane.removeAll();
		bookQueryPanel=new BookRetrievalPanel(this);
		tabbedPane.addTab("��Ŀ����", bookQueryPanel);
	}
	
	/**
	 * ���ߵ�¼�����ڶ��ߵ�¼ϵͳ���Ա��ѯ�Լ��ĸ�����Ϣ
	 */
	public void readerLogin(){
		readerLoginDialog=new ReaderLoginDialog(this);
		
		readerLoginDialog.setVisible(true);
	}
	
	/**
	 * �˳��¼��������������˳��¼�
	 */
	class ExitActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			exitSystem();
		}
		
	}
	
	/**
	 * �رմ��ڼ�����
	 */
	class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			exitSystem();
		}
	}
	
	/**
	 * ���ѡ�������
	 */
	class LookAndFeelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			String className=event.getActionCommand();
			try{
				UIManager.setLookAndFeel(className);
				SwingUtilities.updateComponentTreeUI(MainFrame.this);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * �˵�������
	 */
	class AboutActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String msg="ͼ�����ϵͳ V1.0\nCopyright(C) 2008-2009\n\nBy Sea";
			String title="���ɰٴ�";
			JOptionPane.showMessageDialog(MainFrame.this, msg,title,JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	/**
	 * ��Ŀ����������
	 */
	class BookInLibraryActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			bookRetrieval();
		}
		
	}
	
	/**
	 * �ҵĽ��ļ�����
	 */
	class MyBorrowActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			readerLogin();
		}
		
	}
	
	/**
	 * �����˵���������������ȥ���
	 */
	class UndefineActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String msg="����";
			String title="���ɰٴ�";
			JOptionPane.showMessageDialog(MainFrame.this, msg, title,1);
		}
		
	}
}
