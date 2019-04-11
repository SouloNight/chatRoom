package server;

import java.util.List;

import entity.ChattingRecord;
import jdbc.ChattingDao;

public class ServerSaveChatting implements Runnable{

	private List<ChattingRecord> chattingList;
	private int listLength;
	
	public ServerSaveChatting(List<ChattingRecord> chattingList) {
		this.chattingList = chattingList;
		listLength = chattingList.size();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(5000);
				if(listLength != chattingList.size()) {
					ChattingDao dao = new ChattingDao();
					dao.saveChattingMsg(chattingList);
					chattingList.clear();
					System.out.println("运行了一次存档聊天记录，并清空聊天记录列表");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
