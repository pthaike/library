package library;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import library.BookManageDialog.AddBookListener;
import library.BookManageDialog.DelBookListener;

public class ManageDialog extends JDialog{

	protected JButton delreaderButton;
	protected JButton regButton;
	protected JButton addbookButton;
	protected JButton delbookButton;
	protected MainFrame frame;
	protected AddBookDialog addBookDialog;
	protected AddReaderDialog addReaderDialog;
	
	public ManageDialog(MainFrame parentFrame){
		super(parentFrame,"读者管理",true);
		frame=parentFrame;
		Dimension sceensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(250,250);
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
		JLabel readerLabel=new JLabel("读者管理",JLabel.RIGHT);
		readerLabel.setForeground(Color.blue);
		container.add(readerLabel,c);
		c.gridx=1;
		c.gridy=0;
		JLabel bookLabel=new JLabel("图书管理",JLabel.RIGHT);
		bookLabel.setForeground(Color.blue);
		container.add(bookLabel,c);
		
		c.gridx=0;
		c.gridy=1;
		regButton=new JButton("读者注册");
		regButton.addActionListener(new RegListener());
		container.add(regButton,c);
		c.gridx=0;
		c.gridy=2;
		delreaderButton=new JButton("读者注册");
		delreaderButton.addActionListener(new RegListener());
		container.add(delreaderButton,c);
		
		c.gridx=1;
		c.gridy=1;
		addbookButton=new JButton("增加图书");
		addbookButton.addActionListener(new AddBookListener());
		container.add(addbookButton,c);
		
		c.gridx=1;
		c.gridy=2;
		delbookButton=new JButton("删除图书");
		delbookButton.addActionListener(new DelBookListener());
		container.add(delbookButton,c);
	}
	
	protected class RegListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addReaderDialog=new AddReaderDialog(frame);
			addReaderDialog.setVisible(true);
		}
		
	}
	
	/**
	 * 增加图书监听器
	 * @author lenovo
	 *
	 */
	protected class AddBookListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addBookDialog=new AddBookDialog(frame);
			addBookDialog.setVisible(true);
		}
		
	}
	
	protected class DelBookListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
