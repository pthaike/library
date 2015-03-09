package library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class LibOpHandler extends Thread implements LibProtocals{

	protected Socket clientSocket;//客户端套接字
	protected ObjectOutputStream outputToClient;//输出流向客户端写数据
	protected ObjectInputStream inputFromClient; //输入流用于读取客户端数据
	protected LibDataAccessor libDataAccrssor;//数据存储器，操作数据库
	protected boolean done;//判断是否停止监听
	public LibOpHandler(Socket theClientSocket) throws IOException{
		clientSocket=theClientSocket;
		outputToClient=new ObjectOutputStream(clientSocket.getOutputStream());
		inputFromClient=new ObjectInputStream(clientSocket.getInputStream());
		libDataAccrssor=new LibDataAccessor();
		done=false;
	}
	public void run(){
		try{
			while(!done){
				log("等待命令");
				int opCode=inputFromClient.readInt();//从客户端读取数据
				log("opCode="+opCode);
				switch(opCode){
				case OP_GET_BOOK_DETAILS:
					opGetBookDetails();//读图书详细信息
					break;
				case OP_GET_BOOK_LIBINFO:
					opGetBookLibInfo();
					break;
				case OP_GET_BORROINFO:
					opGetBorrowInfo();
					break;
				case OP_CHECK_USER:
					CheckHandler();
					break;
				case OP_ADDBOOK:
					opAddBook();
					break;
				case OP_ADDREADER:
					opAddReader();
					break;
				case OP_CHECK_MANAGER:
					opCheckManager();
					break;
				case OP_LENBOOK:
					opBorrowBook();
					break;
				case OP_RETURNBOOK:
					opReturnBook();
					break;
				case OP_DELBOOK:
					opDelBook();
					break;
				case OP_DELREADER:
					opDelReader();
					break;
					default:
						log("错误代码");
				}
			}
		}catch(IOException e){
			try{
				clientSocket.close();
			}catch(Exception e1){
				log("run异常"+e1);
			}
			log(clientSocket.getInetAddress()+"客户离开了");
		}
	}
	/**
	 * 读图书详细信息
	 */
	protected void opGetBookDetails(){
		try{
			log("读图书基本数据");
			String field=(String)inputFromClient.readObject();
			String keyword=(String)inputFromClient.readObject();
			ArrayList<BookDetails>bookList=libDataAccrssor.getBookDetails(field,keyword);
			outputToClient.writeObject(bookList);
			outputToClient.flush();
			log("发出"+bookList.size()+"本图书馆信息到客户端");
		}catch(IOException e){
			log("发生I/O异常"+e);
		}catch(ClassNotFoundException e){
			log("发生找不到的异常"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 读图书馆藏信息
	 */
	protected void opGetBookLibInfo(){
		try{
			log("读图书馆藏情况");
			String isbn=(String)inputFromClient.readObject();
			log("书ISBN号是："+isbn);
			ArrayList<BookInLibrary> bookLibList=libDataAccrssor.getBookLibInfo(isbn);
			outputToClient.writeObject(bookLibList);
			outputToClient.flush();
			log("发出"+bookLibList.size()+"条藏书信息到客户端");
		}catch(IOException e){
			log("发生异常："+e);
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			log("发生异常："+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 读我的借阅信息
	 */
	protected void opGetBorrowInfo(){
		try{
			log("读我的借阅信息");
			String readerid=(String) inputFromClient.readObject();
			log("读者ID"+readerid);
			ArrayList<BorrowInfo> borrowList=libDataAccrssor.getBorrowInfo(readerid);
			System.out.println("=====>"+borrowList);
			outputToClient.writeObject(borrowList);
			outputToClient.flush();
			log("发出"+borrowList.size()+"条借阅信息到客户端");
		}catch(IOException e){
			log("发生异常："+e);
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			log("发生异常："+e);
			e.printStackTrace();
		}
	}
	
	public void setDone(boolean flag){
		done=flag;
	}
	protected void log(Object msg){
		System.out.println(CurrDateTime.currDateTime()+"LibOpHandler类"+msg);
	}
	
	/**
	 * Server端检验读者登录信息处理
	 * @throws IOException 
	 */
	protected void CheckHandler() throws IOException{
		try{
			log("读者验证");
			String name=(String)inputFromClient.readObject();
			String pswd=(String)inputFromClient.readObject();
			boolean is=libDataAccrssor.checkUser(name, pswd);
			if(is){
				outputToClient.writeInt(OP_SUCCESS);
			}else{
				outputToClient.writeInt(OP_FAIL);
			}
			outputToClient.flush();
			log("checkuser---->"+is);
		}catch(ClassNotFoundException e){
			log("发生异常"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 管理员验证
	 * @throws IOException
	 */
	protected void opCheckManager() throws IOException{
		try{
			log("管理员验证");
			String name=(String)inputFromClient.readObject();
			String pswd=(String)inputFromClient.readObject();
			boolean is=libDataAccrssor.checkManager(name, pswd);
			if(is){
				outputToClient.writeInt(OP_SUCCESS);
				log("管理员验证成功");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("管理员验证失败");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("发生异常"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加图书操作
	 * @throws IOException
	 */
	protected void opAddBook() throws IOException{
		try{
			log("增加图书");
			BookDetails bookDetails=(BookDetails) inputFromClient.readObject();
			boolean success=libDataAccrssor.addBook(bookDetails);
			if(success){
				outputToClient.writeInt(OP_SUCCESS);
				log("增加图书成功");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("增加图书失败");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("增加图书异常"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加读者操作
	 */
	protected void opAddReader(){
		try{
			log("增加读者");
			ReaderInfo readerInfo=(ReaderInfo)inputFromClient.readObject();
			log("received-->"+readerInfo);
			boolean success=libDataAccrssor.addReader(readerInfo);
			if(success){
				outputToClient.writeInt(OP_SUCCESS);
				log("增加读者成功");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("增加读者失败");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 借书操作
	 * @throws IOException
	 */
	protected void opBorrowBook() throws IOException{
		try{
			log("借阅图书");
			LenInfo lenInfo=(LenInfo) inputFromClient.readObject();
			int op=libDataAccrssor.borrowBook(lenInfo);
			if(0==op){
				outputToClient.writeInt(OP_NOTEXIT);
				log("图书不存在");
			}else if(1==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("借阅图书成功");
			}else if(3==op){
				outputToClient.writeInt(OP_CANTN);
				log("图书已经被借阅");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("借阅图书失败");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("增加图书异常"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 还书操作
	 * @throws IOException
	 */
	protected void opReturnBook() throws IOException{
		try{
			log("归还图书");
			LenInfo lenInfo=(LenInfo) inputFromClient.readObject();
			int op=libDataAccrssor.returnBook(lenInfo);
			if(0==op){
				outputToClient.writeInt(OP_NOTEXIT);
				log("归还图书不存在");
			}else if(1==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("归还图书成功");
			}else if(3==op){
				outputToClient.writeInt(OP_CANTN);
				log("归还图书");
			}else{
				outputToClient.writeInt(OP_FAIL);
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("归还图书异常"+e);
			e.printStackTrace();
		}
	}
	
	protected void opDelBook() throws IOException{
		try{
			log("删除图书");
			String isbn=(String) inputFromClient.readObject();
			int op=libDataAccrssor.delBook(isbn);
			if(0==op){
				outputToClient.writeInt(OP_CANTN);
				log("删除图书被借");
			}else if(1==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("删除图书成功");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("删除图书失败");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("删除图书异常"+e);
			e.printStackTrace();
		}
	}
	
	protected void opDelReader() throws IOException{
		try{
			log("删除读者");
			String isbn=(String) inputFromClient.readObject();
			int op=libDataAccrssor.delReader(isbn);
			if(0==op){
				outputToClient.writeInt(OP_CANTN);
				log("读者不存在");
			}
			else if(1==op){
				outputToClient.writeInt(OP_CANTN);
				log("读者有未还书");
			}else if(2==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("删除读者成功");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("删除读者失败");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("删除图书异常"+e);
			e.printStackTrace();
		}
	}
}
