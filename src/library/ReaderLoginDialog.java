package library;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 读者登录对话框，读者输入账号密码后，激活“我的借阅”选项卡，查看自己的借阅信息
 * @author lenovo
 *
 */
public class ReaderLoginDialog extends JDialog{

	protected JFrame parentFrame;
	protected JTextField readerFieldText; //账号对话框
	protected MyBorrowPanel myBorrowPanel; //我的借阅面板
	protected JPasswordField pswdText; //密码对话框
	protected JButton okButton; //确定按钮
	protected LibDataAccessor libDataAccessor;
	protected MainFrame frame; //父窗口
	protected String readerId; //读者编号
	protected String readerPwd; //读者密码
	protected LibClient libClient;
	
	
	public ReaderLoginDialog(MainFrame parentFrame){
		super(parentFrame,"读者登录",true);
		frame=parentFrame;
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(270,120);
		Dimension framesize=this.getSize();
		int x=(int)screensize.getWidth()/2-(int)framesize.getWidth()/2;
		int y=(int)screensize.getHeight()/2-(int)framesize.getHeight()/2;
		this.setLocation(x,y);
		buildGUI();
	}
	
	/**
	 * 登录界面
	 */
	protected void buildGUI(){
		Container container=this.getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.WEST;
		c.insets=new Insets(5,5,5,10);
		JLabel readerInLabel=new JLabel("账号",JLabel.RIGHT);
		readerInLabel.setForeground(Color.blue);
		container.add(readerInLabel,c);
		c.gridx=1;
		c.gridy=0;
		readerFieldText=new JTextField(10);
		readerFieldText.setSize(10,5);
		container.add(readerFieldText,c);
		c.gridx=0;
		c.gridy=1;
		JLabel pswdLabel=new JLabel("密码",JLabel.RIGHT);
		pswdLabel.setForeground(Color.blue);
		container.add(pswdLabel,c);
		c.gridx=1;
		c.gridy=1;
		pswdText=new JPasswordField(10);
		pswdText.setSize(10,5);
		container.add(pswdText, c);
		c.gridx=2;
		c.gridy=1;
		JButton okButton=new JButton("确定");
		okButton.addActionListener(new LoginActionListener());
		container.add(okButton, c);
	}
	
	/**
	 * 登录监听
	 */
	protected void handleLogin(String reader){
		this.setVisible(false);
		this.dispose();
		frame.setReader();
		frame.tabbedPane.removeAll();
		myBorrowPanel=new MyBorrowPanel(parentFrame,reader);
		frame.tabbedPane.addTab("我的借阅", myBorrowPanel);
	}
	
	/**
	 * 确定监听
	 */
	class LoginActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			readerId=readerFieldText.getText();
			readerPwd=pswdText.getText();
			try {
				if(loginCheck(readerId,readerPwd)){
					handleLogin(readerId);
					System.out.println("login seccess");
				}else{
					JOptionPane.showMessageDialog(ReaderLoginDialog.this,"账号或密码出错","提示",JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 登录验证
	 * @throws IOException 
	 */
	private boolean loginCheck(String readerId,String pswd) throws IOException{
		boolean istrue=false;
		libClient=MainFrame.globalCilent;
		istrue=libClient.CheckUser(readerId, pswd);
		return istrue;
	}
}
