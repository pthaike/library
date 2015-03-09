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
		connectToServer();//连接服务器
		this.setTitle("图书馆欢迎您！");
		
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		
		tabbedPane=new JTabbedPane();
		bookQueryPanel=new BookRetrievalPanel(this);
		tabbedPane.addTab("书目检索", bookQueryPanel);
		container.add(BorderLayout.CENTER,tabbedPane);
		
		//建立菜单
		JMenuBar menuBar=new JMenuBar();
		buildMainMenu(menuBar);
		this.setJMenuBar(menuBar);
		
		//设置窗口位置
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
			System.out.println("服务器连接失败!");
			JOptionPane.showMessageDialog(null, "服务器i按揭失败");
			exitSystem();
		}
	}
	
	protected void buildMainMenu(JMenuBar menuBar){
		JMenu fileMenu=new JMenu("文件(F)");
		fileMenu.setMnemonic(KeyEvent.VK_F);//给文件菜单定义助记键
		JMenuItem exitMenuItem=new JMenuItem("退出");
		exitMenuItem.setMnemonic(KeyEvent.VK_F);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));//设定快捷键
		exitMenuItem.addActionListener(new ExitActionListener());  //给退出菜单增加监听器
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		setupBookRetrievalMenu(menuBar);
		setupLendReturnMenu(menuBar);
		setupMaintainMenu(menuBar);
		setupLookAndFeelMenu(menuBar);
		
		JMenu helpMenu=new JMenu("帮助(H)");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem aboutMenuItem=new JMenuItem("关于");
		aboutMenuItem.setMnemonic(KeyEvent.VK_A);
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		aboutMenuItem.addActionListener(new AboutActionListener());
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);
	}
	
	protected void setupBookRetrievalMenu(JMenuBar menuBar){
		JMenu libMenu=new JMenu("馆藏检索(B)");
		libMenu.setMnemonic(KeyEvent.VK_B);
		JMenuItem libMenuItem=new JMenuItem("书目检索");
		JMenuItem myBorrowMenuItem =new JMenuItem("我的借阅");
		JMenuItem managerLoginMenu=new JMenuItem("管理员登录");
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
	 * 建立还书菜单，包括借书和还书2个菜单项
	 */
	protected void setupLookAndFeelMenu(JMenuItem menuBar){
		lrMenu=new JMenu("借书还书(E)");
		lrMenu.setMnemonic(KeyEvent.VK_E);
		JMenuItem lendMenuItem=new JMenuItem("借书");
		JMenuItem returnMenuItem=new JMenuItem("还书");
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
	 * 建立还书菜单
	 */
	protected void setupLendReturnMenu(JMenuBar menuBar){
		lrMenu=new JMenu("借书还书(E)");
		lrMenu.setMnemonic(KeyEvent.VK_E);
		JMenuItem lendMenuItem=new JMenuItem("借书");
		JMenuItem returnMenuItem=new JMenuItem("还书");
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
	 * 建立系统维护菜单，包括图书维护，读者维护，管理员维护，系统维护4个菜单项
	 */
	protected void setupMaintainMenu(JMenuBar menuBar){
		sysMainMenu=new JMenu("系统维护(M)");
		sysMainMenu.setMnemonic(KeyEvent.VK_M);
		JMenuItem bookMenuItem=new JMenuItem("增加图书");
		JMenuItem readerMenuItem=new JMenuItem("读者维护");
		JMenuItem librarianMenuItem=new JMenuItem("管理员维护");
		JMenuItem paraMenuItem=new JMenuItem("系统参数维护");
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
	 * 读者管理菜单监听器
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
	 * 还书监听器
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
	 * 建立外观选项菜单,给用户更多的外观风格
	 */
	protected void setupLookAndFeelMenu(JMenuBar menuBar){
		UIManager.LookAndFeelInfo[] lookAndFeelInfo=UIManager.getInstalledLookAndFeels();
		JMenu lookAndFeelMenu=new JMenu("外观(S)");
		lookAndFeelMenu.setMnemonic(KeyEvent.VK_S);
		lookAndFeelMenu.setMnemonic(KeyEvent.VK_S);
		JMenuItem anItem=null;
		LookAndFeelListener myListener=new LookAndFeelListener();
		try{
			for(int i=0;i<lookAndFeelInfo.length;i++){
				anItem=new JMenuItem(lookAndFeelInfo[i].getName()+"外观");
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
	 * 退出系统
	 */
	public void exitSystem(){
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	/**
	 * 图书检索，用于动态加载图书检索界面
	 */
	public void bookRetrieval(){
		tabbedPane.removeAll();
		bookQueryPanel=new BookRetrievalPanel(this);
		tabbedPane.addTab("书目检索", bookQueryPanel);
	}
	
	/**
	 * 读者登录，用于读者登录系统，以便查询自己的个人信息
	 */
	public void readerLogin(){
		readerLoginDialog=new ReaderLoginDialog(this);
		
		readerLoginDialog.setVisible(true);
	}
	
	/**
	 * 退出事件监听器，处理退出事件
	 */
	class ExitActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			exitSystem();
		}
		
	}
	
	/**
	 * 关闭窗口监听器
	 */
	class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			exitSystem();
		}
	}
	
	/**
	 * 外观选择监听器
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
	 * 菜单监听器
	 */
	class AboutActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String msg="图书管理系统 V1.0\nCopyright(C) 2008-2009\n\nBy Sea";
			String title="海纳百川";
			JOptionPane.showMessageDialog(MainFrame.this, msg,title,JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	/**
	 * 书目检索监听器
	 */
	class BookInLibraryActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			bookRetrieval();
		}
		
	}
	
	/**
	 * 我的借阅监听器
	 */
	class MyBorrowActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			readerLogin();
		}
		
	}
	
	/**
	 * 其他菜单监听器，待读者去完成
	 */
	class UndefineActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String msg="待定";
			String title="海纳百川";
			JOptionPane.showMessageDialog(MainFrame.this, msg, title,1);
		}
		
	}
}
