package library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class LibClient implements LibProtocals{

	protected Socket hostSocket; //服务器socket
	protected ObjectOutputStream outputToServer; //输出流用于想服务器写数据
	protected ObjectInputStream inputFromServer; //输入流，用于从服务器读数据
	/**
	 * 接收主机名和端口号的方法
	 */
	public LibClient(String host,int port) throws IOException{
		try{
			System.out.println("连接服务器..."+host+":"+port);
			hostSocket=new Socket(host,port);
			outputToServer=new ObjectOutputStream(hostSocket.getOutputStream());
			inputFromServer=new ObjectInputStream(hostSocket.getInputStream());
			System.out.println("连接成功");
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 取得与关键字匹配的相关图书集合
	 */
	public ArrayList<BookDetails> getBookList(String field,String keyword) throws IOException{
		ArrayList<BookDetails> bookList=null;
		try{
			System.out.println("发送请求:OP_GET_BOOK_DETAILS");
			outputToServer.writeInt(OP_GET_BOOK_DETAILS);
			outputToServer.writeObject(field);
			outputToServer.writeObject(keyword);
			outputToServer.flush();
			System.out.println("接收数据。。。");
			bookList=(ArrayList<BookDetails>)inputFromServer.readObject();
			System.out.println("..........."+bookList);
			System.out.println("收到"+bookList.size()+"本相关图书");
		}catch(ClassNotFoundException exc){
			System.out.println("取图书异常:"+exc);
			throw new IOException("找不到相关类");
		}
		return bookList;
		
	}
	
	/**
	 * 取得每本图书的馆藏信息
	 */
	public ArrayList<BookInLibrary>getBookInLibrary(String isbn)throws IOException{
		ArrayList<BookInLibrary>bookInLibraryInfo=null;
		try{
			System.out.println("发送请求:OP_GET_BOOKLIBINFO,书号="+isbn);
			outputToServer.writeInt(OP_GET_BOOK_LIBINFO);
			outputToServer.writeObject(isbn);
			outputToServer.flush();
			System.out.println("接收数据");
			bookInLibraryInfo=(ArrayList<BookInLibrary>)inputFromServer.readObject();
			System.out.println("收到"+bookInLibraryInfo.size()+" 项馆藏记录");
		}catch(ClassNotFoundException exc){
			System.out.println("取馆藏信息异常:"+exc);
			throw new IOException("找不到相关类");
		}
		return bookInLibraryInfo;
	}
	
	/**
	 * 取用户在图书馆的借阅信息
	 */
	public ArrayList<BorrowInfo> getReaderBorrowInfo(String readerid) throws IOException{
		ArrayList<BorrowInfo> readerBorrowInfoList=null;
		try{
			System.out.println("发送请求:OP_GETBORROWINFO,书号="+readerid);
			outputToServer.writeInt(OP_GET_BORROINFO);
			outputToServer.writeObject(readerid);
			outputToServer.flush();
			System.out.println("接收数据....");
			readerBorrowInfoList=(ArrayList<BorrowInfo>)inputFromServer.readObject();
			System.out.println("收到"+readerBorrowInfoList.size()+"项借阅信息");
		}catch(ClassNotFoundException exc){
			System.out.println("取借阅信息异常:"+exc);
			throw new IOException("找不到相关类");
		}
		return readerBorrowInfoList;
	}
	
	/**
	 * 用户登录
	 * @throws IOException 
	 */
	public boolean CheckUser(String readerid,String pswd) throws IOException{
		
		boolean is = false;
		System.out.println("发送请求---->OP_CHECK_USER");
		outputToServer.writeInt(OP_CHECK_USER);
		outputToServer.writeObject(readerid);
		outputToServer.writeObject(pswd);
		outputToServer.flush();
		System.out.println("接收数据服务器数据...");
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
	 * 增加读者
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
	 * 增加书
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
	 * 管理员验证
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
		System.out.println("接收数据服务器数据...");
		int rc=inputFromServer.readInt();
		if(rc==OP_SUCCESS){
			return true;
		}
		return false;
	}
	
	/**
	 * 借书
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
	 * 还书
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
