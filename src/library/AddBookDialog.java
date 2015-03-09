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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddBookDialog extends JDialog{

	protected BookDetails bookDetails;
	
	protected JButton submitButton;
	protected JTextField isbnTextField;
	protected JTextField barcodeTextField;
	protected JTextField booknameTextField;
	protected JTextField seriesTextField;
	protected JTextField authorTextField;
	protected JTextField publisherTextField;
	protected JTextField sizeTextField;
	protected JTextField pageTextField;
	protected JTextField priceTextField;
	protected JTextField introductionTextField;
	protected JTextField clnumTextField;
	
	protected LibClient libClient;
	
	protected MainFrame frame;
	
	public AddBookDialog(MainFrame parentFrame){
		super(parentFrame,"增加图书",true);
		frame=parentFrame;
		Dimension sceensize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(250,500);
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
		JLabel isbnLabel=new JLabel("国际标准书号",JLabel.RIGHT);
		isbnLabel.setForeground(Color.red);
		container.add(isbnLabel,c);
		c.gridx=1;
		c.gridy=0;
		isbnTextField=new JTextField(10);
		isbnTextField.setSize(10,5);
		container.add(isbnTextField,c);
		
		c.gridx=0;
		c.gridy=1;
		JLabel barcodeLabel=new JLabel("条形码",JLabel.RIGHT);
		barcodeLabel.setForeground(Color.red);
		container.add(barcodeLabel,c);
		c.gridx=1;
		c.gridy=1;
		barcodeTextField=new JTextField(10);
		barcodeTextField.setSize(10,5);
		container.add(barcodeTextField,c);
		
		c.gridx=0;
		c.gridy=2;
		JLabel booknameLabel=new JLabel("书名",JLabel.RIGHT);
		booknameLabel.setForeground(Color.red);
		container.add(booknameLabel,c);
		c.gridx=1;
		c.gridy=2;
		booknameTextField=new JTextField(10);
		booknameTextField.setSize(10,5);
		container.add(booknameTextField,c);
		
		c.gridx=0;
		c.gridy=3;
		JLabel seriesLabel=new JLabel("丛书名",JLabel.RIGHT);
		seriesLabel.setForeground(Color.blue);
		container.add(seriesLabel,c);
		c.gridx=1;
		c.gridy=3;
		seriesTextField=new JTextField(10);
		seriesTextField.setSize(10,5);
		container.add(seriesTextField,c);
		
		c.gridx=0;
		c.gridy=4;
		JLabel authorLabel=new JLabel("作者",JLabel.RIGHT);
		authorLabel.setForeground(Color.blue);
		container.add(authorLabel,c);
		c.gridx=1;
		c.gridy=4;
		authorTextField=new JTextField(10);
		authorTextField.setSize(10,5);
		container.add(authorTextField,c);
		
		c.gridx=0;
		c.gridy=5;
		JLabel publisherLabel=new JLabel("出版社",JLabel.RIGHT);
		publisherLabel.setForeground(Color.blue);
		container.add(publisherLabel,c);
		c.gridx=1;
		c.gridy=5;
		publisherTextField=new JTextField(10);
		publisherTextField.setSize(10,5);
		container.add(publisherTextField,c);
		
		c.gridx=0;
		c.gridy=6;
		JLabel sizeLabel=new JLabel("图书开本",JLabel.RIGHT);
		sizeLabel.setForeground(Color.blue);
		container.add(sizeLabel,c);
		c.gridx=1;
		c.gridy=6;
		sizeTextField=new JTextField(10);
		sizeTextField.setSize(10,5);
		container.add(sizeTextField,c);
		
		c.gridx=0;
		c.gridy=7;
		JLabel pageLabel=new JLabel("页数",JLabel.RIGHT);
		pageLabel.setForeground(Color.blue);
		container.add(pageLabel,c);
		c.gridx=1;
		c.gridy=7;
		pageTextField=new JTextField(10);
		pageTextField.setSize(10,5);
		container.add(pageTextField,c);
		
		c.gridx=0;
		c.gridy=8;
		JLabel priceLabel=new JLabel("定价",JLabel.RIGHT);
		priceLabel.setForeground(Color.blue);
		container.add(priceLabel,c);
		c.gridx=1;
		c.gridy=8;
		priceTextField=new JTextField(10);
		priceTextField.setSize(10,5);
		container.add(priceTextField,c);
		
		c.gridx=0;
		c.gridy=9;
		JLabel clnumLabel=new JLabel("图书分类",JLabel.RIGHT);
		clnumLabel.setForeground(Color.blue);
		container.add(clnumLabel,c);
		c.gridx=1;
		c.gridy=9;
		clnumTextField=new JTextField(10);
		clnumTextField.setSize(10,5);
		container.add(clnumTextField,c);
		
		c.gridx=0;
		c.gridy=10;
		JLabel introductionLabel=new JLabel("图书简介",JLabel.RIGHT);
		introductionLabel.setForeground(Color.blue);
		container.add(introductionLabel,c);
		c.gridx=1;
		c.gridy=10;
		introductionTextField=new JTextField(50);
		introductionTextField.setSize(20,40);
		container.add(introductionTextField,c);
		
		c.gridx=1;
		c.gridy=11;
		submitButton=new JButton("提交");
		submitButton.addActionListener(new SubmitListener());
		container.add(submitButton,c);
	}
	
	protected class SubmitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String isbn=null;
			isbn=isbnTextField.getText().trim();
			String bookname=null;
			bookname=booknameTextField.getText().trim();
			String barcode=null;
			barcode=barcodeTextField.getText().trim();
			if(isbn.equals("")||bookname.equals("")||barcode.equals("")){
				JOptionPane.showMessageDialog(AddBookDialog.this,"红色必填","提示",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String series=null;
			series=seriesTextField.getText().trim();
			String author=null;
			author=authorTextField.getText().trim();
			String publisher=null;
			publisher=publisherTextField.getText().trim();
			String size=null;
			size=sizeTextField.getText().trim();
			String page=null;
			page=pageTextField.getText().trim();
			if(!isNum(page)){
				JOptionPane.showMessageDialog(AddBookDialog.this,"页数填数字","提示",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String price=null;
			price=priceTextField.getText().trim();
			if(!isNum(price)){
				JOptionPane.showMessageDialog(AddBookDialog.this,"价格必须填数字","提示",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String introduction=null;
			introduction=introductionTextField.getText().trim();
			String clnum=null;
			clnum=clnumTextField.getText().trim();
			if(isbn.equals("")||bookname.equals("")){
				JOptionPane.showMessageDialog(AddBookDialog.this,"红色必填","提示",JOptionPane.INFORMATION_MESSAGE);
			}else{
				bookDetails=new BookDetails();
				bookDetails.setIsbn(isbn);
				bookDetails.setBarcode(barcode);
				bookDetails.setName(bookname);
				bookDetails.setSeries(series);
				bookDetails.setAuthors(author);
				bookDetails.setPublisher(publisher);
				bookDetails.setSize(size);
				if(!price.equals("")){
					bookDetails.setPrice(Integer.parseInt(price));
				}
				if(!page.equals("")){
					bookDetails.setPage(Integer.parseInt(page));
				}
				bookDetails.setIntroduction(introduction);
				bookDetails.setClnum(clnum);
				bookDetails.setPicture(null);
				System.out.println(bookDetails);
				
				libClient=MainFrame.globalCilent;
				try {
					boolean success=libClient.addBook(bookDetails);
					if(success){
						JOptionPane.showMessageDialog(AddBookDialog.this,"存储成功","提示",JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}else{
						JOptionPane.showMessageDialog(AddBookDialog.this,"存储失败","提示",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		
		public boolean isNum(String str){
			  for (int i = str.length();--i>=0;){   
			   if (!Character.isDigit(str.charAt(i))){
			    return false;
			   }
			  }
			  return true;
			 }
	}
	
	
}
