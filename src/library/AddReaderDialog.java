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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddReaderDialog extends JDialog{

	protected ReaderInfo readerInfo;
	protected JButton submitButton;
	protected JTextField readeridTextField;
	protected JTextField readernameTextField;
	protected JPasswordField readerpswdTextField;
	protected JPasswordField rereaderpswdTextField;
	protected JTextField readergenderTextField;
	protected JTextField readeraddressTextField;
	protected JTextField readertelTextField;
	protected JTextField pageTextField;
	protected JLabel startselectionLabel; //ѡ��ʽ��ǩ
	protected JComboBox startyearfieldComboBox; //����ʽ���
	protected JComboBox startmonthfieldComboBox; //����ʽ���
	protected JComboBox startdayfieldComboBox; //����ʽ���
	protected String startdayField=null;
	protected String startyearField=null;
	protected String startmonthField=null;
	protected JLabel endselectionLabel; //ѡ��ʽ��ǩ
	protected JComboBox endyearfieldComboBox; //����ʽ���
	protected JComboBox endmonthfieldComboBox; //����ʽ���
	protected JComboBox enddayfieldComboBox; //����ʽ���
	protected String enddayField=null;
	protected String endyearField=null;
	protected String endmonthField=null;
	protected JComboBox genderfieldComboBox; //����ʽ���
	protected String genderField=null;
	protected JComboBox typefieldComboBox; //����ʽ���
	protected int typeField=0;
	
	protected LibClient libClient;
	
	protected MainFrame frame;
	
	public AddReaderDialog(MainFrame parentFrame){
		super(parentFrame,"����ע��",true);
		frame=parentFrame;
		Dimension sceensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(350,500);
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
		JLabel idLabel=new JLabel("�˺�",JLabel.RIGHT);
		idLabel.setForeground(Color.red);
		container.add(idLabel,c);
		c.gridx=1;
		c.gridy=0;
		readeridTextField=new JTextField(10);
		readeridTextField.setSize(10,5);
		container.add(readeridTextField,c);
		
		c.gridx=0;
		c.gridy=1;
		JLabel nameLabel=new JLabel("����",JLabel.RIGHT);
		nameLabel.setForeground(Color.red);
		container.add(nameLabel,c);
		c.gridx=1;
		c.gridy=1;
		readernameTextField=new JTextField(30);
		readernameTextField.setSize(15,5);
		container.add(readernameTextField,c);
		
		c.gridx=0;
		c.gridy=2;
		JLabel pswdLabel=new JLabel("����",JLabel.RIGHT);
		pswdLabel.setForeground(Color.red);
		container.add(pswdLabel,c);
		c.gridx=1;
		c.gridy=2;
		readerpswdTextField=new JPasswordField(10);
		readerpswdTextField.setSize(15,5);
		container.add(readerpswdTextField,c);
		
		c.gridx=0;
		c.gridy=3;
		JLabel repswdLabel=new JLabel("������������",JLabel.RIGHT);
		repswdLabel.setForeground(Color.red);
		container.add(repswdLabel,c);
		c.gridx=1;
		c.gridy=3;
		rereaderpswdTextField=new JPasswordField(10);
		rereaderpswdTextField.setSize(15,5);
		container.add(rereaderpswdTextField,c);
		
		c.gridx=0;
		c.gridy=4;
		JLabel genderLabel=new JLabel("�Ա�",JLabel.RIGHT);
		genderLabel.setForeground(Color.red);
		container.add(genderLabel,c);
		c.gridx=1;
		c.gridy=4;
		genderfieldComboBox=new JComboBox();
		genderfieldComboBox.addItem("");
		genderfieldComboBox.addItem("��");
		genderfieldComboBox.addItem("Ů");
		genderfieldComboBox.addItemListener(new genderFieldSelectedListener());
		container.add(genderfieldComboBox,c);
		
		c.gridx=0;
		c.gridy=5;
		JLabel typeLabel=new JLabel("����",JLabel.RIGHT);
		typeLabel.setForeground(Color.red);
		container.add(typeLabel,c);
		c.gridx=1;
		c.gridy=5;
		typefieldComboBox=new JComboBox();
		typefieldComboBox.addItem("");
		typefieldComboBox.addItem("������");
		typefieldComboBox.addItem("�о���");
		typefieldComboBox.addItem("��ʦ");
		typefieldComboBox.addItemListener(new typeFieldSelectedListener());
		container.add(typefieldComboBox,c);
		
		c.gridx=0;
		c.gridy=6;
		JLabel addressLabel=new JLabel("��ַ",JLabel.RIGHT);
		addressLabel.setForeground(Color.blue);
		container.add(addressLabel,c);
		c.gridx=1;
		c.gridy=6;
		readeraddressTextField=new JTextField(10);
		readeraddressTextField.setSize(15,5);
		container.add(readeraddressTextField,c);
		
		c.gridx=0;
		c.gridy=7;
		JLabel telLabel=new JLabel("�绰",JLabel.RIGHT);
		telLabel.setForeground(Color.blue);
		container.add(telLabel,c);
		c.gridx=1;
		c.gridy=7;
		readertelTextField=new JTextField(10);
		readertelTextField.setSize(15,5);
		container.add(readertelTextField,c);
		
		c.gridx=0;
		c.gridy=8;
		JLabel startLabel=new JLabel("��ʼ����",JLabel.RIGHT);
		startLabel.setForeground(Color.red);
		container.add(startLabel,c);
		c.gridx=1;
		c.gridy=8;
		startselectionLabel=new JLabel("���");
		startyearfieldComboBox=new JComboBox();
		startyearfieldComboBox.addItem("");
		startyearfieldComboBox.addItem("2013");
		startyearfieldComboBox.addItem("2014");
		startyearfieldComboBox.addItem("2015");
		startyearfieldComboBox.addItem("2016");
		startyearfieldComboBox.addItem("2017");
		startyearfieldComboBox.addItem("2018");
		startyearfieldComboBox.addItem("2019");
		startyearfieldComboBox.addItem("2020");
		startyearfieldComboBox.addItemListener(new startyearFieldSelectedListener());
		container.add(startyearfieldComboBox,c);
		c.gridx=2;
		c.gridy=8;
		startselectionLabel=new JLabel("��");
		startmonthfieldComboBox=new JComboBox();
		startmonthfieldComboBox.addItem("");
		for(int i=1;i<13;i++){
			startmonthfieldComboBox.addItem(""+i);
		}
		startmonthfieldComboBox.addItemListener(new startmonthFieldSelectedListener());
		container.add(startmonthfieldComboBox,c);
		c.gridx=3;
		c.gridy=8;
		startselectionLabel=new JLabel("��");
		startdayfieldComboBox=new JComboBox();
		startdayfieldComboBox.addItem("");
		for(int i=1;i<32;i++){
			startdayfieldComboBox.addItem(""+i);
		}
		startdayfieldComboBox.addItemListener(new startdayFieldSelectedListener());
		container.add(startdayfieldComboBox,c);
		
		c.gridx=0;
		c.gridy=9;
		JLabel endLabel=new JLabel("��������",JLabel.RIGHT);
		endLabel.setForeground(Color.red);
		container.add(endLabel,c);
		c.gridx=1;
		c.gridy=9;
		endselectionLabel=new JLabel("���");
		endyearfieldComboBox=new JComboBox();
		endyearfieldComboBox.addItem("");
		endyearfieldComboBox.addItem("2013");
		endyearfieldComboBox.addItem("2014");
		endyearfieldComboBox.addItem("2015");
		endyearfieldComboBox.addItem("2016");
		endyearfieldComboBox.addItem("2017");
		endyearfieldComboBox.addItem("2018");
		endyearfieldComboBox.addItem("2019");
		endyearfieldComboBox.addItem("2020");
		endyearfieldComboBox.addItemListener(new endyearFieldSelectedListener());
		container.add(endyearfieldComboBox,c);
		c.gridx=2;
		c.gridy=9;
		endselectionLabel=new JLabel("��");
		endmonthfieldComboBox=new JComboBox();
		endmonthfieldComboBox.addItem("");
		for(int i=1;i<13;i++){
			endmonthfieldComboBox.addItem(""+i);
		}
		endmonthfieldComboBox.addItemListener(new endmonthFieldSelectedListener());
		container.add(endmonthfieldComboBox,c);
		c.gridx=3;
		c.gridy=9;
		endselectionLabel=new JLabel("��");
		enddayfieldComboBox=new JComboBox();
		enddayfieldComboBox.addItem("");
		for(int i=1;i<32;i++){
			enddayfieldComboBox.addItem(""+i);
		}
		enddayfieldComboBox.addItemListener(new enddayFieldSelectedListener());
		container.add(enddayfieldComboBox,c);
		
		c.gridx=1;
		c.gridy=10;
		submitButton=new JButton("�ύ");
		submitButton.addActionListener(new SubmitListener());
		container.add(submitButton,c);
	}
	
	/**
	 * @author lenovo
	 *
	 */
	protected class typeFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				typeField=typefieldComboBox.getSelectedIndex();
				System.out.println("-->"+typeField);
			}
		}
		
	}
	
	
	class genderFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				genderField=(String) genderfieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	class startyearFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				startyearField=(String) startyearfieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	class startmonthFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				startmonthField=(String) startmonthfieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	class startdayFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				startdayField=(String) startdayfieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	class endyearFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				endyearField=(String) endyearfieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	class endmonthFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				endmonthField=(String) endmonthfieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	class enddayFieldSelectedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				enddayField=(String) enddayfieldComboBox.getSelectedItem();
			}
		}
		
	}
	
	protected class SubmitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String id=null;
			id=readeridTextField.getText().trim();
			String name=null;
			name=readernameTextField.getText().trim();
			String pswd=null;
			pswd=readerpswdTextField.getText().trim();
			String repswd=null;
			repswd=rereaderpswdTextField.getText().trim();
			if(id.equals("")||name.equals("")||pswd.equals("")||repswd.equals("")){
				JOptionPane.showMessageDialog(AddReaderDialog.this,"��ɫ����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(!pswd.equals(repswd)){
				JOptionPane.showMessageDialog(AddReaderDialog.this,"�������벻��ͬ","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(null==genderField||genderField.equals("")){
				JOptionPane.showMessageDialog(AddReaderDialog.this,"ѡ���Ա�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(0==typeField){
				JOptionPane.showMessageDialog(AddReaderDialog.this,"ѡ������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String address=null;
			address=readeraddressTextField.getText().trim();
			String tel=null;
			tel=readertelTextField.getText().trim();
			if(null==startyearField||null==startmonthField||null==startdayField||startyearField.equals("")||startmonthField.equals("")||startdayField.equals("")){
				JOptionPane.showMessageDialog(AddReaderDialog.this,"ѡ��ʼ����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(null==endyearField||null==endmonthField||null==enddayField||endyearField.equals("")||endmonthField.equals("")||enddayField.equals("")){
				JOptionPane.showMessageDialog(AddReaderDialog.this,"ѡ���������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			try{
				String start=startyearField+"-"+startmonthField+"-"+startdayField;
				String end=endyearField+"-"+endmonthField+"-"+enddayField;
				java.sql.Date startdate = java.sql.Date.valueOf(start);
				java.sql.Date enddate = java.sql.Date.valueOf(end);
				if(startdate.after(enddate)){
					JOptionPane.showMessageDialog(AddReaderDialog.this,"ѡ����ȷ����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				readerInfo=new ReaderInfo(id, pswd, name, genderField, address, tel, startdate, enddate,typeField);
				libClient=MainFrame.globalCilent;
				boolean success=libClient.addReader(readerInfo);
				if(success){
					JOptionPane.showMessageDialog(AddReaderDialog.this,"��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}else{
					JOptionPane.showMessageDialog(AddReaderDialog.this,"���ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
	}
}
