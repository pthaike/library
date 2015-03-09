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
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ReturnBookDialog extends JDialog{
	protected MainFrame frame;
	protected LenInfo lenInfo;
	protected LibClient libClient;
	
	protected JButton submitButton;
	protected JTextField idTextField;
	protected JTextField bookcodeTextField;
	
	
	public ReturnBookDialog(MainFrame parentFrame){
		super(parentFrame,"归还图书",true);
		frame=parentFrame;
		Dimension sceensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(270,170);
		Dimension framesize=this.getSize();
		int x=(int)sceensize.getWidth()/2-(int)framesize.getWidth()/2;
		int y=(int)sceensize.getHeight()/2-(int)framesize.getHeight()/2;
		this.setLocation(x,y);
		buildGUI();
	}
	
	public void buildGUI(){
		Container container=this.getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.WEST;
		c.insets=new Insets(5, 5, 5, 10);
		JLabel idLabel=new JLabel("读者账号",JLabel.RIGHT);
		idLabel.setForeground(Color.red);
		container.add(idLabel,c);
		c.gridx=1;
		c.gridy=0;
		idTextField=new JTextField(10);
		idTextField.setSize(10,5);
		container.add(idTextField,c);
		
		c.gridx=0;
		c.gridy=1;
		JLabel bookcodeLabel=new JLabel("图书条形码",JLabel.RIGHT);
		bookcodeLabel.setForeground(Color.red);
		container.add(bookcodeLabel,c);
		c.gridx=1;
		c.gridy=1;
		bookcodeTextField=new JTextField(20);
		bookcodeTextField.setSize(10, 5);
		container.add(bookcodeTextField,c);
		
		c.gridx=1;
		c.gridy=2;
		submitButton=new JButton("提交");
		submitButton.addActionListener(new submitListener());
		container.add(submitButton,c);
	}
	
	protected class submitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String id=null;
			id=idTextField.getText().trim();
			String bookcode=null;
			bookcode=bookcodeTextField.getText().trim();
			if(id.equals("")||bookcode.equals("")){
				JOptionPane.showMessageDialog(ReturnBookDialog.this, "请填写完整信息");
				return;
			}
			
			java.util.Date now = new java.util.Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String nowdate = dateFormat.format(now);
			java.sql.Date returndate=java.sql.Date.valueOf(nowdate);
			lenInfo=new LenInfo(id,bookcode);
			lenInfo.setReturndate(returndate);
			libClient=MainFrame.globalCilent;
			try{
				int result=libClient.returnbook(lenInfo);
				if(0==result){
					JOptionPane.showMessageDialog(ReturnBookDialog.this,"该读者不存在","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(1==result){
					JOptionPane.showMessageDialog(ReturnBookDialog.this,"归还成功","提示",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}else{
					JOptionPane.showMessageDialog(ReturnBookDialog.this,"归还失败","提示",JOptionPane.INFORMATION_MESSAGE);
				}
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
	}
}
