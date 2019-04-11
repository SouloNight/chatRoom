package chat;

import java.util.ArrayList;
import java.util.List;

public class ServerSaveChatting implements Runnable{

	private List<String> chattingList;
	private int listLength;
	
	public ServerSaveChatting(List<String> chattingList) {
		this.chattingList = chattingList;
		listLength = chattingList.size();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(10000);
				if(listLength != chattingList.size()) {
					//将列表chattingList中的聊天记录写入数据库
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
