package library;

import java.util.Properties;

public class ServerInfoGetter {

	private String serverHost="127.168.0.1"; //��������ַ
	private int serverPort=8080; //�˿ں�
	public ServerInfoGetter(){
		
	}
	
	public String getHost(){
		return serverHost;
	}
	
	public int getPort(){
		return serverPort;
	}
}
