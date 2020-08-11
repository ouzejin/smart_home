package com.lingnan.summer.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import com.lingnan.summer.util.JDBCUtil;

public class TimerTest {
	
	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.schedule(new MyTask(), 1000, 2000);

	}

}

class MyTask extends TimerTask {

	@Override
	public void run() {
		// 查找日志
		Statement stat = null;
		Connection conn =JDBCUtil.getConn();
		String SQL = "select * from record where risk='高'";
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(SQL);
			String newrisk = rs.getString("risk");
			/*while(rs.next()){
				Record record = new Record();
				System.out.println(rs);
			}*/
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Record record = new Record();
/*		// 分析 if
		if (newrisk.equals("中")) {
			int did;
			String dname;
			String risk;
			String measure;

			did = record.getDid();
			dname = record.getDname();
			risk = record.getRisk();
			measure = "保修";
			PreparedStatement prep = null;
			
			try {
				prep = conn.prepareStatement("insert into alarm values(?,?,?,?)");
				prep.setInt(1, did);
				prep.setString(2, dname);
				prep.setString(3, risk);
				prep.setString(4, measure);
				prep.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (newrisk.equals("高")) {
			int did;
			String dname;
			String risk;
			String measure;

			did = record.getDid();
			dname = record.getDname();
			risk = record.getRisk();
			measure = "更换";
			PreparedStatement prep1 = null;
			try {
				prep1 = conn.prepareStatement("insert into alarm values(?,?,?,?)");
				prep1.setInt(1, did);
				prep1.setString(2, dname);
				prep1.setString(3, risk);
				prep1.setString(4, measure);
				prep1.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		// --->alarm 。。。。。。。。。。。。。
		// System.out.println("--------");*/
	}

}
