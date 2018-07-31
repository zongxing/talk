package com.mashensoft.talk;

import java.io.InputStream;
import java.util.Scanner;

public class ServerInputThread extends Thread{
	InputStream is;

	public ServerInputThread(InputStream is) {
		this.is = is;
	}
	@Override
	public void run() {
		Scanner s = new Scanner(is);
		while(s.hasNextLine()) {
			String msg = s.nextLine();
			//Ҫ����仰�ŵ�����ȥ�����ݽṹ��   ���|��������
			String[] array = new String[2];
			if(msg.contains(",")) {
				array = msg.split(",");
				
			}
			if(msg.contains("��")) {
				array = msg.split("��");
				
			}
			if(!msg.contains(",")&&!msg.contains("��")) {
				System.out.println("�밴���¸�ʽ���룺idcard,���ݣ� ������1,���Բ���");
				continue;
			}
			Integer idcard = Integer.parseInt(array[0]);
			String content = array[1];
			//ȥ�����е����ӣ������ĸ�����idcard������Ҫ�ҵġ�
			for(int i=0;i<ServerSocketDemo.socketThreadArray.length;i++) {
				SocketThread socketThread = ServerSocketDemo.socketThreadArray[i];
				if(socketThread!=null) {
					if(idcard==socketThread.getIdcard()) {
						//�ҵ���Ҫ����Ϣ��˭��������
						socketThread.pw.println(content);
						socketThread.pw.flush();
					}
				}
			}
		}
	}
	
}
