package library;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;

public class BookDetailsDialog extends JDialog{

	protected BookDetails book; //���ڱ���ͼ�����������ߡ������硢����ժҪ����Ϣ
	protected BookInLibrary bookInLibrary=null; //ͼ��ݲ���ϸ��Ϣ���
	protected LibClient libClient;//�����������ͨ�ŵĿͻ�������
	protected ArrayList<BookInLibrary>bookInLibArray; //ͬһISBN�ŵ�ͼ��Ĺݲ���Ϣ
	protected JTable bookInLibTable; //������ʾͼ��ݲ���ϸ���
	protected Frame parent;
	
	public BookDetailsDialog(Frame theParentFrame,BookDetails theBook,LibClient theLibClient){
		this(theParentFrame,"ͼ����ϸ��Ϣ"+theBook.toString(),theBook,theLibClient);
	}
	
	//���öԻ���λ��
	public BookDetailsDialog(Frame theParentFrame,String theTitle, BookDetails theMusicRecording,LibClient theLibClient){
		super(theParentFrame,theTitle,true);
		book=theMusicRecording;
		parent=theParentFrame;
		libClient=theLibClient;
		buildGUI(); //����ͼ�������Ի������
		this.pack();
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize=this.getSize();
		int x=(int)screensize.getWidth()/2-(int)framesize.getWidth()/2;
		int y=(int)screensize.getHeight()/2-(int)framesize.getHeight()/2;
		this.setLocation(x, y);
	}
	
	/**
	 * ���������淽��������ͼ����������
	 */
	private void buildGUI(){
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		JPanel bookDataPanel=new JPanel();
		bookDataPanel.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.WEST;
		c.insets=new Insets(5,5,5,10);
		JLabel nameLabel=new JLabel("����:",JLabel.RIGHT);
		nameLabel.setForeground(Color.blue);
		bookDataPanel.add(nameLabel,c);
		c.gridx=1;
		c.gridy=0;
		JLabel nameValue=new JLabel(book.getName());
		bookDataPanel.add(nameValue,c);
		c.gridx=0;
		c.gridy=1;
		JLabel authorsLabel=new JLabel("����:",JLabel.RIGHT);
		authorsLabel.setForeground(Color.blue);
		bookDataPanel.add(authorsLabel,c);
		c.gridx=1;
		c.gridy=1;
		JLabel authorsValue=new JLabel(book.getAuthors());
		bookDataPanel.add(authorsValue,c);
		c.gridx=0;
		c.gridy=2;
		JLabel seriesLabel=new JLabel("������:",JLabel.RIGHT);
		seriesLabel.setForeground(Color.blue);
		bookDataPanel.add(seriesLabel,c);
		c.gridx=1;
		c.gridy=2;
		JLabel seriesValue=new JLabel(book.getSeries());
		bookDataPanel.add(seriesValue,c);
		c.gridx=0;
		c.gridy=3;
		JLabel publisherLabel=new JLabel("���淢��:",JLabel.RIGHT);
		publisherLabel.setForeground(Color.blue);
		bookDataPanel.add(publisherLabel,c);
		c.gridx=1;
		c.gridy=3;
		JLabel publisherValue=new JLabel(book.getSeries());
		bookDataPanel.add(publisherValue,c);
		c.gridx = 0;
		c.gridy = 4;
		JLabel mediaLabel = new JLabel("������Ϣ:", JLabel.RIGHT);
		mediaLabel.setForeground(Color.blue);
		bookDataPanel.add(mediaLabel, c);

		c.gridx = 1;
		c.gridy = 4;
		JLabel meidaValue = new JLabel(book.getPage() + "ҳ��" + book.getSize()
				+ "��" + new DecimalFormat("#.0").format(book.getPrice()) + "Ԫ");
		bookDataPanel.add(meidaValue, c);
		
		c.gridx = 0;
		c.gridy = 5;
		JLabel clssNumLabel = new JLabel("��ͼ����:", JLabel.RIGHT);
		clssNumLabel.setForeground(Color.blue);
		bookDataPanel.add(clssNumLabel, c);

		c.gridx = 1;
		c.gridy = 5;
		JLabel clssNumValue = new JLabel(book.getClnum());
		bookDataPanel.add(clssNumValue, c);

		c.gridx = 0;
		c.gridy = 6;
		JLabel introLabel = new JLabel("ͼ����:", JLabel.RIGHT);
		introLabel.setForeground(Color.blue);
		bookDataPanel.add(introLabel, c);

		c.gridx = 1;
		c.gridy = 6;
		JTextArea abstractInfo = new JTextArea(book.getIntroduction(), 3, 20);
		abstractInfo.setEditable(false);
		abstractInfo.setLineWrap(true);
		bookDataPanel.add(abstractInfo, c);

		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 8;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(5, 5, 10, 10);
		String imageName = book.getPicture();
		System.out.println("picture--->"+imageName);
		ImageIcon bookPicIcon = null;
		JLabel bookPicLabel = null;
		try{
			if(imageName.trim().length()==0){
				bookPicLabel=new JLabel("ͼƬ�ݲ�����");
				bookPicLabel.setForeground(Color.red);
			}else{
				System.out.println("picture ok");
				bookPicIcon=new ImageIcon("images/"+imageName);
				bookPicLabel.setForeground(Color.red);
			}
		}catch(Exception e){
			bookPicLabel=new JLabel("");
			//bookPicLabel.setForeground(Color.red);
		}
		bookPicLabel.setToolTipText(book.getName());
		bookDataPanel.add(bookPicLabel,c);
		container.add(BorderLayout.NORTH,bookDataPanel);
		try{
			bookInLibArray=libClient.getBookInLibrary(book.getIsbn());
		}catch(IOException e){
			System.out.println("û���ҵ������Ϣ");
		}
		int bookAvailable=0; //Ŀǰ�ɽ��ͼ������
		int bookTotal=bookInLibArray.size(); //ͬһISBN�ŵ�ͼ���������
		String[]bookLendHead={"ͼ��������","ͼ��ݲص�","ͼ��״̬"};
		String[][]bookLibInfo=new String[bookTotal][3];
		Iterator<BookInLibrary> it=bookInLibArray.iterator();
		String bookStatus;
		int i=0;
		while(it.hasNext()){
			bookInLibrary=(BookInLibrary)it.next();
			bookLibInfo[i][0]=bookInLibrary.getBarCode();
			bookLibInfo[i][1]=bookInLibrary.getLocation();
			switch(bookInLibrary.getStatus()){
			case 0:
				bookStatus="�ѽ��,Ӧ������:\n"+bookInLibrary.getDueReturnDate();
				break;
			case 1:
				bookStatus="�ɽ�";
				bookAvailable++;
				break;
				default:
					bookStatus="���ɽ裬�������";
			}
			bookLibInfo[i][2]=bookStatus;
			i++;
		}
		bookInLibTable=new JTable(bookLibInfo,bookLendHead);
		bookInLibTable.setEnabled(false);
		bookInLibTable.setPreferredScrollableViewportSize(new Dimension(0,120));
		JScrollPane bookInLibScrollPane=new JScrollPane(bookInLibTable);
		//����ÿ�еĿ��
		double tableWidth=bookInLibTable.getPreferredSize().getWidth();
		TableColumnModel tcm=bookInLibTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth((int)(tableWidth/6));
		tcm.getColumn(1).setPreferredWidth((int)(tableWidth/3));
		tcm.getColumn(2).setPreferredWidth((int)(tableWidth/2));
		TitledBorder tableTitleBorder=BorderFactory.createTitledBorder("ͼ��ݲ����");
		tableTitleBorder.setTitleColor(Color.black);
		bookInLibScrollPane.setBorder(tableTitleBorder);
		JPanel statisticPanel=new JPanel();
		statisticPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel totalLabel=new JLabel("ͼ��������");
		totalLabel.setForeground(Color.black);
		JLabel totalValueLabel=new JLabel(""+bookTotal);
		totalValueLabel.setForeground(Color.blue);
		statisticPanel.add(totalLabel);
		statisticPanel.add(totalValueLabel);
		JLabel vailLabel=new JLabel("Ŀǰ�ɽ���");
		vailLabel.setForeground(Color.black);
		JLabel vailValueLabel=new JLabel(""+bookAvailable);
		vailValueLabel.setForeground(Color.red);
		statisticPanel.add(vailLabel);
		statisticPanel.add(vailValueLabel);
		JPanel bookInLibraryInfo=new JPanel();
		bookInLibraryInfo.setLayout(new BorderLayout());
		bookInLibraryInfo.add(BorderLayout.CENTER,bookInLibScrollPane);
		bookInLibraryInfo.add(BorderLayout.SOUTH,statisticPanel);
		container.add(BorderLayout.CENTER,bookInLibraryInfo);
		JPanel bottomPanel=new JPanel();
		JButton okButton=new JButton("ȷ��");
		okButton.addActionListener(new OkButtonActionListener());
		bottomPanel.add(okButton);
		container.add(BorderLayout.SOUTH,bottomPanel);
	}
	class OkButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
		
	}
	
}
