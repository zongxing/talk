package com.mashensoft.talk;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketThread extends Thread {
	PrintWriter pw;
	
	/**
	 * 客户端的标识
	 */
	private Integer idcard;
	
	private Socket socket;

	public SocketThread(Socket socket,Integer idcard) {
		System.out.println(socket.getInetAddress()+"连接进来了");
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
				System.out.println("输入：客户："+this.getIdcard()+",说了："+msg);
				
				//客户端说的这句话，我们把它发给另一个客户端
				//要把这句话放到哪里去。数据结构：   编号|聊天内容
				String[] array = new String[2];
				if(msg.contains(",")) {
					array = msg.split(",");
					
				}
				if(msg.contains("，")) {
					array = msg.split("，");
					
				}
				if(!msg.contains(",")&&!msg.contains("，")) {
					System.out.println("请按如下格式输入：idcard,内容； 举例：1,测试测试");
					continue;
				}
				Integer idcard = Integer.parseInt(array[0]);
				String content = array[1];
				//去找所有的连接，看看哪个连接idcard是我们要找的。
				for(int i=0;i<ServerSocketDemo.socketThreadArray.length;i++) {
					SocketThread socketThread = ServerSocketDemo.socketThreadArray[i];
					if(socketThread!=null) {
						if(idcard==socketThread.getIdcard()) {
							//找到是要发消息给谁。发给它
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
