package library;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LibServer {

	final static int PORT=8080;
	protected LibOpHandler libOpHandler; //ͼ�����������
	protected ServerSocket serverSocket; //�������׽���
	protected Socket clientSocket; //�ͻ����׽���
	protected boolean done; //�ж��Ƿ��������ͻ��˵�����
	public LibServer(int thePort){
		done=false;
		try{
			log("����������"+thePort);
			serverSocket=new ServerSocket(thePort);
			log("������׼������!");
		}catch(IOException e){
			log(e);
			System.exit(1);
		}
		while(!done){
			try{
				log("���������ڵȴ�����");
				clientSocket=serverSocket.accept(); //�ȴ��ͻ���������
				String clientHostName=clientSocket.getInetAddress().getHostName();
				log("���յ�����"+clientHostName+"������");
				libOpHandler=new LibOpHandler(clientSocket); //�������������߳�
				libOpHandler.start(); //�����߳�
			}catch(IOException e){
				log(e);
			}
		}
	}
	protected void log(Object msg){
		System.out.println(CurrDateTime.currDateTime()+"LibServer��"+msg);
	}
	public static void main(String[] args){
		new LibServer(PORT);
	}
}
