package library;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class BookManageDialog extends JDialog{
	protected JButton addButton;
	protected JButton delButton;
	protected MainFrame frame;
	protected AddBookDialog addBookDialog;
	
	public BookManageDialog(MainFrame parentFrame){
		super(parentFrame,"ͼ�����",true);
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
		addButton=new JButton("����ͼ��");
		delButton=new JButton("ɾ��ͼ��");
		addButton.addActionListener(new AddBookListener());
		delButton.addActionListener(new DelBookListener());
		container.add(addButton);
		container.add(delButton);
	}
	
	/**
	 * ����ͼ�������
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
