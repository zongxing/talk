package com.mashensoft.talk;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketDemo {
	//存放已经连接进来的客户
	static SocketThread[] socketThreadArray = new SocketThread[20];
	public static void main(String[] args) {
		System.out.println("服务已启动");
		try {
			ServerSocket serverSocket = new ServerSocket(6688);
			Integer idcard = 0;
			ServerInputThread serverInputThread = new ServerInputThread(System.in);
			serverInputThread.start();
			while(true) {
				//有一个客户连接进来，我们就新建一个线程，这个线程是有身份标识的，idcard就是它标识。第1个进来的是0
				SocketThread socketThread = new SocketThread(serverSocket.accept(), idcard);
				socketThreadArray[idcard]=socketThread;
				idcard++;
				socketThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
