package library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class LibClient implements LibProtocals{

	protected Socket hostSocket; //������socket
	protected ObjectOutputStream outputToServer; //����������������д����
	protected ObjectInputStream inputFromServer; //�����������ڴӷ�����������
	/**
	 * �����������Ͷ˿ںŵķ���
	 */
	public LibClient(String host,int port) throws IOException{
		try{
			System.out.println("���ӷ�����..."+host+":"+port);
			hostSocket=new Socket(host,port);
			outputToServer=new ObjectOutputStream(hostSocket.getOutputStream());
			inputFromServer=new ObjectInputStream(hostSocket.getInputStream());
			System.out.println("���ӳɹ�");
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	/**
	 * ȡ����ؼ���ƥ������ͼ�鼯��
	 */
	public ArrayList<BookDetails> getBookList(String field,String keyword) throws IOException{
		ArrayList<BookDetails> bookList=null;
		try{
			System.out.println("��������:OP_GET_BOOK_DETAILS");
			outputToServer.writeInt(OP_GET_BOOK_DETAILS);
			outputToServer.writeObject(field);
			outputToServer.writeObject(keyword);
			outputToServer.flush();
			System.out.println("�������ݡ�����");
			bookList=(ArrayList<BookDetails>)inputFromServer.readObject();
			System.out.println("..........."+bookList);
			System.out.println("�յ�"+bookList.size()+"�����ͼ��");
		}catch(ClassNotFoundException exc){
			System.out.println("ȡͼ���쳣:"+exc);
			throw new IOException("�Ҳ��������");
		}
		return bookList;
		
	}
	
	/**
	 * ȡ��ÿ��ͼ��Ĺݲ���Ϣ
	 */
	public ArrayList<BookInLibrary>getBookInLibrary(String isbn)throws IOException{
		ArrayList<BookInLibrary>bookInLibraryInfo=null;
		try{
			System.out.println("��������:OP_GET_BOOKLIBINFO,���="+isbn);
			outputToServer.writeInt(OP_GET_BOOK_LIBINFO);
			outputToServer.writeObject(isbn);
			outputToServer.flush();
			System.out.println("��������");
			bookInLibraryInfo=(ArrayList<BookInLibrary>)inputFromServer.readObject();
			System.out.println("�յ�"+bookInLibraryInfo.size()+" ��ݲؼ�¼");
		}catch(ClassNotFoundException exc){
			System.out.println("ȡ�ݲ���Ϣ�쳣:"+exc);
			throw new IOException("�Ҳ��������");
		}
		return bookInLibraryInfo;
	}
	
	/**
	 * ȡ�û���ͼ��ݵĽ�����Ϣ
	 */
	public ArrayList<BorrowInfo> getReaderBorrowInfo(String readerid) throws IOException{
		ArrayList<BorrowInfo> readerBorrowInfoList=null;
		try{
			System.out.println("��������:OP_GETBORROWINFO,���="+readerid);
			outputToServer.writeInt(OP_GET_BORROINFO);
			outputToServer.writeObject(readerid);
			outputToServer.flush();
			System.out.println("��������....");
			readerBorrowInfoList=(ArrayList<BorrowInfo>)inputFromServer.readObject();
			System.out.println("�յ�"+readerBorrowInfoList.size()+"�������Ϣ");
		}catch(ClassNotFoundException exc){
			System.out.println("ȡ������Ϣ�쳣:"+exc);
			throw new IOException("�Ҳ��������");
		}
		return readerBorrowInfoList;
	}
	
	/**
	 * �û���¼
	 * @throws IOException 
	 */
	public boolean CheckUser(String readerid,String pswd) throws IOException{
		
		boolean is = false;
		System.out.println("��������---->OP_CHECK_USER");
		outputToServer.writeInt(OP_CHECK_USER);
		outputToServer.writeObject(readerid);
		outputToServer.writeObject(pswd);
		outputToServer.flush();
		System.out.println("�������ݷ���������...");
		int rc=inputFromServer.readInt();
		if(rc==OP_SUCCESS){
			is=true;
		}else{
			is=false;
		}
		return is;
	}
	
	/**
	 * 
	 * ���Ӷ���
	 * @param readerInfo
	 * @return
	 */
	public boolean addReader(ReaderInfo readerInfo){
		try{
			outputToServer.writeInt(OP_ADDREADER);
			outputToServer.writeObject(readerInfo);
			int reply=inputFromServer.readInt();
			if(reply==OP_SUCCESS){
				return true;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return false;
	}
	/**
	 * ������
	 * @param bookDetails
	 * @return
	 * @throws IOException
	 */
	public boolean addBook(BookDetails bookDetails) throws IOException{
		boolean success=false;
		outputToServer.writeInt(OP_ADDBOOK);
		outputToServer.writeObject(bookDetails);
		int result=inputFromServer.readInt();
		if(result==OP_SUCCESS){
			success=true;
		}
		return success;
	}
	
	/**
	 * ����Ա��֤
	 * @param id
	 * @param pswd
	 * @return
	 * @throws IOException
	 */
	public boolean checkManage(String id, String pswd) throws IOException{
		System.out.println("op-->"+OP_CHECK_MANAGER);
		outputToServer.writeInt(OP_CHECK_MANAGER);
		outputToServer.writeObject(id);
		outputToServer.writeObject(pswd);
		outputToServer.flush();
		System.out.println("�������ݷ���������...");
		int rc=inputFromServer.readInt();
		if(rc==OP_SUCCESS){
			return true;
		}
		return false;
	}
	
	/**
	 * ����
	 * @param lenInfo
	 * @return
	 * @throws IOException
	 */
	public int borrowbook(LenInfo lenInfo) throws IOException{
		outputToServer.writeInt(OP_LENBOOK);
		outputToServer.writeObject(lenInfo);
		int result=inputFromServer.readInt();
		if(OP_NOTEXIT==result){
			return 0;
		}else if(OP_SUCCESS==result){
			return 1;
		}else if(OP_CANTN==result){
			return 3;
		}else{
			return 2;
		}
	}
	
	/**
	 * ����
	 * @param lenInfo
	 * @return
	 * @throws IOException
	 */
	public int returnbook(LenInfo lenInfo) throws IOException{
		outputToServer.writeInt(OP_RETURNBOOK);
		outputToServer.writeObject(lenInfo);
		int result=inputFromServer.readInt();
		if(OP_NOTEXIT==result){
			return 0;
		}else if(OP_SUCCESS==result){
			return 1;
		}else if(OP_CANTN==result){
			return 3;
		}else{
			return 2;
		}
	}
	
	/**
	 * 
	 * @param isbn
	 * @return
	 * @throws IOException
	 */
	public int delBook(String isbn) throws IOException{
		outputToServer.writeInt(OP_DELBOOK);
		outputToServer.writeObject(isbn);
		int result=inputFromServer.readInt();
		if(result==OP_CANTN){
			return 0;
		}else if(result==OP_SUCCESS){
			return 1;
		}else{
			return 2;
		}
	}
	
	public int deleteReader(String readerid)throws IOException{
		outputToServer.writeInt(OP_DELREADER);
		outputToServer.writeObject(readerid);
		int result=inputFromServer.readInt();
		if(result==OP_NOTEXIT){
			return 0;
		}else if(result==OP_CANTN){
			return 1;
		}else if(result==OP_SUCCESS){
			return 2;
		}else{
			return 3;
		}
	}
}
