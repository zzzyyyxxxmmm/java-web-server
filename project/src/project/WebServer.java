package project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer implements Runnable{
	

	
	
	public static boolean shutdown = false;
	public static int port = 8090;
	public static String serverPath="webapp";

	public static WebServer webServer=null;
	public static ServerSocket severSocket=null;
	public static Socket socket=null;
	public void run() {
		shutdown=ServerApp.shutdown;
		port=ServerApp.PORT;
		serverPath=ServerApp.serverPath;
		main.add("服务器已经启动，监听端口为:"+port);
		//开始监听端口
		try {
				severSocket= new ServerSocket(port);
				while(!shutdown){
					socket= severSocket.accept();
					main.add("接收到请求.....");
					SocketProcess sp=new SocketProcess(socket);
					sp.start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
		}
		
	}
	public synchronized static WebServer GetInstance(){
		if(webServer==null)
			webServer= new WebServer();
		return webServer;
	}
	
	public void startServer(){
		
	}
	
	public static void main(String[] args) {
		new WebServer().startServer();
	}

	
}
