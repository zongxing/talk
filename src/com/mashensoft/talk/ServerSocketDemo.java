package com.mashensoft.talk;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketDemo {
	//����Ѿ����ӽ����Ŀͻ�
	static SocketThread[] socketThreadArray = new SocketThread[20];
	public static void main(String[] args) {
		System.out.println("����������");
		try {
			ServerSocket serverSocket = new ServerSocket(6688);
			Integer idcard = 0;
			ServerInputThread serverInputThread = new ServerInputThread(System.in);
			serverInputThread.start();
			while(true) {
				//��һ���ͻ����ӽ��������Ǿ��½�һ���̣߳�����߳�������ݱ�ʶ�ģ�idcard��������ʶ����1����������0
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
