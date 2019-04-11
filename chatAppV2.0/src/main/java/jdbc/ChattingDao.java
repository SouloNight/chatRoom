package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entity.ChattingRecord;
import entity.User;

public class ChattingDao {
	private Utils util = new Utils();
	private Connection conn;
	private PreparedStatement state;
	private ResultSet rs;
	
	public void saveChattingMsg(List<ChattingRecord> record) {
		
		try {
			
			conn = util.getConn();
			conn.setAutoCommit(false);
			String sql = "insert into chattingrecord(time,username,message) values(?,?,?)";
			state = conn.prepareStatement(sql);
			for(int i=0;i<record.size();i++) {
				ChattingRecord msg = record.get(i);
				String time = msg.getTime();
				String username = msg.getUsername();
				String content = msg.getContent();
				state.setString(1, time);
				state.setString(2, username);
				state.setString(3, content);
				state.addBatch();
			}
			state.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(conn, state, null);
		}
	}
	
	public void reg(User user) {
		try {
			String userAccount = user.getUserAccount();
			String password = user.getPassword();
			String nickname = user.getNickname();
			int age = user.getAge();
			String sql = "insert into user(account,password,nickname,age) values(?,?,?,?)";
			conn = util.getConn();
			state = conn.prepareStatement(sql);
			state.setString(1, userAccount);
			state.setString(2, password);
			state.setString(3, nickname);
			state.setInt(4, age);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			util.close(conn, state, null);
		}
	}
	
	public User login(User user) {
		User rigUser = null;
		try{
			String userAccount = user.getUserAccount();
			String password = user.getPassword();
			String sql = "select account,password,nickname,age from user where account=? and password=?";
			conn = util.getConn();
			state = conn.prepareStatement(sql);
			state.setString(1, userAccount);
			state.setString(2, password);
			rs = state.executeQuery();
			
			while(rs.next()) {
				rigUser = new User();
				String rigAccount = rs.getString("account");
				String rigPassword = rs.getString(2);
				String rigNickname = rs.getString(3);
				int rigAge = rs.getInt(4);
				rigUser.setUserAccount(rigAccount);
				rigUser.setPassword(rigPassword);
				rigUser.setNickname(rigNickname);
				rigUser.setAge(rigAge);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			util.close(conn, state, rs);
		}
		return rigUser;
	}
	
	/*public static void main(String[] args) {
		User user = new User();
		user.setUserAccount("qazplmgame");
		user.setPassword("qazplm");
		ChattingDao dao = new ChattingDao();
		User newUser = dao.login(user);
		if(newUser == null) {
			System.out.println("null");
		}else {
			System.out.println("not null");
		}
	}*/
}
