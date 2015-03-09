package library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class LibOpHandler extends Thread implements LibProtocals{

	protected Socket clientSocket;//�ͻ����׽���
	protected ObjectOutputStream outputToClient;//�������ͻ���д����
	protected ObjectInputStream inputFromClient; //���������ڶ�ȡ�ͻ�������
	protected LibDataAccessor libDataAccrssor;//���ݴ洢�����������ݿ�
	protected boolean done;//�ж��Ƿ�ֹͣ����
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
				log("�ȴ�����");
				int opCode=inputFromClient.readInt();//�ӿͻ��˶�ȡ����
				log("opCode="+opCode);
				switch(opCode){
				case OP_GET_BOOK_DETAILS:
					opGetBookDetails();//��ͼ����ϸ��Ϣ
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
						log("�������");
				}
			}
		}catch(IOException e){
			try{
				clientSocket.close();
			}catch(Exception e1){
				log("run�쳣"+e1);
			}
			log(clientSocket.getInetAddress()+"�ͻ��뿪��");
		}
	}
	/**
	 * ��ͼ����ϸ��Ϣ
	 */
	protected void opGetBookDetails(){
		try{
			log("��ͼ���������");
			String field=(String)inputFromClient.readObject();
			String keyword=(String)inputFromClient.readObject();
			ArrayList<BookDetails>bookList=libDataAccrssor.getBookDetails(field,keyword);
			outputToClient.writeObject(bookList);
			outputToClient.flush();
			log("����"+bookList.size()+"��ͼ�����Ϣ���ͻ���");
		}catch(IOException e){
			log("����I/O�쳣"+e);
		}catch(ClassNotFoundException e){
			log("�����Ҳ������쳣"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ͼ��ݲ���Ϣ
	 */
	protected void opGetBookLibInfo(){
		try{
			log("��ͼ��ݲ����");
			String isbn=(String)inputFromClient.readObject();
			log("��ISBN���ǣ�"+isbn);
			ArrayList<BookInLibrary> bookLibList=libDataAccrssor.getBookLibInfo(isbn);
			outputToClient.writeObject(bookLibList);
			outputToClient.flush();
			log("����"+bookLibList.size()+"��������Ϣ���ͻ���");
		}catch(IOException e){
			log("�����쳣��"+e);
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			log("�����쳣��"+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ���ҵĽ�����Ϣ
	 */
	protected void opGetBorrowInfo(){
		try{
			log("���ҵĽ�����Ϣ");
			String readerid=(String) inputFromClient.readObject();
			log("����ID"+readerid);
			ArrayList<BorrowInfo> borrowList=libDataAccrssor.getBorrowInfo(readerid);
			System.out.println("=====>"+borrowList);
			outputToClient.writeObject(borrowList);
			outputToClient.flush();
			log("����"+borrowList.size()+"��������Ϣ���ͻ���");
		}catch(IOException e){
			log("�����쳣��"+e);
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			log("�����쳣��"+e);
			e.printStackTrace();
		}
	}
	
	public void setDone(boolean flag){
		done=flag;
	}
	protected void log(Object msg){
		System.out.println(CurrDateTime.currDateTime()+"LibOpHandler��"+msg);
	}
	
	/**
	 * Server�˼�����ߵ�¼��Ϣ����
	 * @throws IOException 
	 */
	protected void CheckHandler() throws IOException{
		try{
			log("������֤");
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
			log("�����쳣"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * ����Ա��֤
	 * @throws IOException
	 */
	protected void opCheckManager() throws IOException{
		try{
			log("����Ա��֤");
			String name=(String)inputFromClient.readObject();
			String pswd=(String)inputFromClient.readObject();
			boolean is=libDataAccrssor.checkManager(name, pswd);
			if(is){
				outputToClient.writeInt(OP_SUCCESS);
				log("����Ա��֤�ɹ�");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("����Ա��֤ʧ��");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("�����쳣"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ͼ�����
	 * @throws IOException
	 */
	protected void opAddBook() throws IOException{
		try{
			log("����ͼ��");
			BookDetails bookDetails=(BookDetails) inputFromClient.readObject();
			boolean success=libDataAccrssor.addBook(bookDetails);
			if(success){
				outputToClient.writeInt(OP_SUCCESS);
				log("����ͼ��ɹ�");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("����ͼ��ʧ��");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("����ͼ���쳣"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * ���Ӷ��߲���
	 */
	protected void opAddReader(){
		try{
			log("���Ӷ���");
			ReaderInfo readerInfo=(ReaderInfo)inputFromClient.readObject();
			log("received-->"+readerInfo);
			boolean success=libDataAccrssor.addReader(readerInfo);
			if(success){
				outputToClient.writeInt(OP_SUCCESS);
				log("���Ӷ��߳ɹ�");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("���Ӷ���ʧ��");
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
	 * �������
	 * @throws IOException
	 */
	protected void opBorrowBook() throws IOException{
		try{
			log("����ͼ��");
			LenInfo lenInfo=(LenInfo) inputFromClient.readObject();
			int op=libDataAccrssor.borrowBook(lenInfo);
			if(0==op){
				outputToClient.writeInt(OP_NOTEXIT);
				log("ͼ�鲻����");
			}else if(1==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("����ͼ��ɹ�");
			}else if(3==op){
				outputToClient.writeInt(OP_CANTN);
				log("ͼ���Ѿ�������");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("����ͼ��ʧ��");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("����ͼ���쳣"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * �������
	 * @throws IOException
	 */
	protected void opReturnBook() throws IOException{
		try{
			log("�黹ͼ��");
			LenInfo lenInfo=(LenInfo) inputFromClient.readObject();
			int op=libDataAccrssor.returnBook(lenInfo);
			if(0==op){
				outputToClient.writeInt(OP_NOTEXIT);
				log("�黹ͼ�鲻����");
			}else if(1==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("�黹ͼ��ɹ�");
			}else if(3==op){
				outputToClient.writeInt(OP_CANTN);
				log("�黹ͼ��");
			}else{
				outputToClient.writeInt(OP_FAIL);
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("�黹ͼ���쳣"+e);
			e.printStackTrace();
		}
	}
	
	protected void opDelBook() throws IOException{
		try{
			log("ɾ��ͼ��");
			String isbn=(String) inputFromClient.readObject();
			int op=libDataAccrssor.delBook(isbn);
			if(0==op){
				outputToClient.writeInt(OP_CANTN);
				log("ɾ��ͼ�鱻��");
			}else if(1==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("ɾ��ͼ��ɹ�");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("ɾ��ͼ��ʧ��");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("ɾ��ͼ���쳣"+e);
			e.printStackTrace();
		}
	}
	
	protected void opDelReader() throws IOException{
		try{
			log("ɾ������");
			String isbn=(String) inputFromClient.readObject();
			int op=libDataAccrssor.delReader(isbn);
			if(0==op){
				outputToClient.writeInt(OP_CANTN);
				log("���߲�����");
			}
			else if(1==op){
				outputToClient.writeInt(OP_CANTN);
				log("������δ����");
			}else if(2==op){
				outputToClient.writeInt(OP_SUCCESS);
				log("ɾ�����߳ɹ�");
			}else{
				outputToClient.writeInt(OP_FAIL);
				log("ɾ������ʧ��");
			}
			outputToClient.flush();
		}catch(ClassNotFoundException e){
			log("ɾ��ͼ���쳣"+e);
			e.printStackTrace();
		}
	}
}
