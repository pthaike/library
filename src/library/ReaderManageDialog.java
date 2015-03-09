package library;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class ReaderManageDialog extends JDialog{

	protected JButton delButton;
	protected JButton regButton;
	protected MainFrame frame;
	protected AddReaderDialog addReaderDialog;
	protected DeleteReaderDialog deleteReaderDialog;
	
	public ReaderManageDialog(MainFrame parentFrame){
		super(parentFrame,"读者管理",true);
		frame=parentFrame;
		Dimension sceensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(150,200);
		Dimension framesize=this.getSize();
		int x=(int)sceensize.getWidth()/2-(int)framesize.getWidth()/2;
		int y=(int)sceensize.getHeight()/2-(int)framesize.getHeight()/2;
		this.setLocation(x,y);
		buildGUI();
	}
	
	public void buildGUI(){
		Container container=this.getContentPane();
		container.setLayout(new FlowLayout(FlowLayout.CENTER));
		regButton=new JButton("读者注册");
		regButton.addActionListener(new RegListener());
		delButton=new JButton("读者删除");
		delButton.addActionListener(new delListener());
		container.add(regButton);
		container.add(delButton);
	}
	
	protected class RegListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addReaderDialog=new AddReaderDialog(frame);
			addReaderDialog.setVisible(true);
		}
		
	}
	protected class delListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			deleteReaderDialog=new DeleteReaderDialog(frame);
			deleteReaderDialog.setVisible(true);
		}
		
	}
}
