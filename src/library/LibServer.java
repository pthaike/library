package library;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LibServer {

	final static int PORT=8080;
	protected LibOpHandler libOpHandler; //图书操作处理器
	protected ServerSocket serverSocket; //服务器套接字
	protected Socket clientSocket; //客户端套接字
	protected boolean done; //判断是否借书监听客户端的请求
	public LibServer(int thePort){
		done=false;
		try{
			log("启动服务器"+thePort);
			serverSocket=new ServerSocket(thePort);
			log("服务器准备就绪!");
		}catch(IOException e){
			log(e);
			System.exit(1);
		}
		while(!done){
			try{
				log("服务器正在等待请求");
				clientSocket=serverSocket.accept(); //等待客户端请求组
				String clientHostName=clientSocket.getInetAddress().getHostName();
				log("接收到来自"+clientHostName+"的连接");
				libOpHandler=new LibOpHandler(clientSocket); //创建操作处理线程
				libOpHandler.start(); //启动线程
			}catch(IOException e){
				log(e);
			}
		}
	}
	protected void log(Object msg){
		System.out.println(CurrDateTime.currDateTime()+"LibServer类"+msg);
	}
	public static void main(String[] args){
		new LibServer(PORT);
	}
}
