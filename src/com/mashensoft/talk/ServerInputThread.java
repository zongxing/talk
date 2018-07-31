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
	}
	
}
