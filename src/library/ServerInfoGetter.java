package library;

import java.util.Properties;

public class ServerInfoGetter {

	private String serverHost="127.168.0.1"; //服务器地址
	private int serverPort=8080; //端口号
	public ServerInfoGetter(){
		
	}
	
	public String getHost(){
		return serverHost;
	}
	
	public int getPort(){
		return serverPort;
	}
}
