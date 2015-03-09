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

public class DeleteReaderDialog extends JDialog{
	protected MainFrame frame;
	protected LenInfo lenInfo;
	protected LibClient libClient;
	
	protected JButton submitButton;
	protected JTextField idTextField;
	
	public DeleteReaderDialog(MainFrame parentFrame){
		super(parentFrame,"删除读者",true);
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
		idLabel.setForeground(Color.blue);
		container.add(idLabel,c);
		c.gridx=1;
		c.gridy=0;
		idTextField=new JTextField(10);
		idTextField.setSize(10,5);
		container.add(idTextField,c);
		
		c.gridx=1;
		c.gridy=2;
		JButton submitButton=new JButton("提交");
		submitButton.addActionListener(new submitListener());
		container.add(submitButton,c);
	}
	
	protected class submitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String readerid=null;
			readerid=idTextField.getText().trim();
			libClient=MainFrame.globalCilent;
			try{
				int result=libClient.deleteReader(readerid);
				if(0==result){
					JOptionPane.showMessageDialog(DeleteReaderDialog.this,"该读者不存在","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(1==result){
					JOptionPane.showMessageDialog(DeleteReaderDialog.this,"读者有未还的书籍","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(2==result){
					JOptionPane.showMessageDialog(DeleteReaderDialog.this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}else{
					JOptionPane.showMessageDialog(DeleteReaderDialog.this,"删除失败","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
	}
}
