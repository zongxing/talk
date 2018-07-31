package com.mashensoft.talk;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketThread extends Thread {
	PrintWriter pw;
	
	/**
	 * �ͻ��˵ı�ʶ
	 */
	private Integer idcard;
	
	private Socket socket;

	public SocketThread(Socket socket,Integer idcard) {
		System.out.println(socket.getInetAddress()+"���ӽ�����");
		this.socket = socket;
		this.idcard = idcard;
		try {
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getIdcard() {
		return idcard;
	}

	public void setId(Integer idcard) {
		this.idcard = idcard;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			Scanner s = new Scanner(socket.getInputStream());
			while(s.hasNextLine()) {
				String msg = s.nextLine();
				System.out.println("���룺�ͻ���"+this.getIdcard()+",˵�ˣ�"+msg);
				
				//�ͻ���˵����仰�����ǰ���������һ���ͻ���
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
