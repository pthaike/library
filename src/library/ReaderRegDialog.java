package library;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ReaderRegDialog extends JDialog{

	protected JTextField readerFiledText;
	protected JPasswordField pswdText;
	protected JButton okButton;
	protected MainFrame frame;
	protected ReaderInfo readerInfo;
	
	public ReaderRegDialog(MainFrame parentFrame){
		super(parentFrame,"¶ÁÕß×¢²á",true);
		frame=parentFrame;
		Dimension sceensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(270,120);
		Dimension framesize=this.getSize();
		int x=(int)sceensize.getWidth()/2-(int)framesize.getWidth()/2;
		int y=(int)sceensize.getHeight()/2-(int)framesize.getHeight()/2;
		this.setLocation(x,y);
		buildGUI();
	}
	
	protected void buildGUI(){
		Container container=this.getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.WEST;
		c.insets=new Insets(5, 5, 5, 10);
		JLabel readerIDLabel=new JLabel("ÕËºÅ",JLabel.RIGHT);
		readerIDLabel.setForeground(Color.blue);
		container.add(readerIDLabel,c);
		c.gridx=1;
		c.gridy=0;
		readerFiledText=new JTextField(10);
		readerFiledText.setSize(10,5);
		container.add(readerFiledText,c);
	}
}
