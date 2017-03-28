package com.sree.share.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class MoveData {

	public void loadData() throws Exception {
		Connection con = getConnection();
		new MoveData().move(con);
	}

	public void move(Connection localCon) {
		Connection awsConnection = null;
		try {
			Statement cs = localCon.createStatement();
			 awsConnection = getAWSConnection();
			ResultSet resultSet = cs.executeQuery("Select * from share_master order by sname");
			PreparedStatement ps = awsConnection.prepareStatement("INSERT INTO share_master (sname, stype,url,createddate) values (?, ?,?,?)");
			PreparedStatement ps2 = awsConnection.prepareStatement("Select shareid from share_master where sname=?");
			PreparedStatement ps_localTrades = localCon.prepareStatement("Select * from trade_details where sname=? order by tradeDate asc");
			String qry ="insert into trade_details (shareid,tradeDate,current_price,prev_pric,change_percent,createddate) values ( ?, ?,?,?,?,?)";
			PreparedStatement awsTrade = awsConnection.prepareStatement(qry);
			while (resultSet.next()) {
				String sname = resultSet.getString(2);
				String stype = resultSet.getString(3);
				Date createddate = resultSet.getDate(4);
				String URL = resultSet.getString(5);
				ps.setString(1, sname);
				ps.setString(2, stype);
				ps.setString(3,  URL);
				ps.setDate(4, new java.sql.Date(createddate.getTime()));
				ps2.setString(1, sname);
				ResultSet sidrs = ps2.executeQuery();
				Integer newshid = null;
				if(sidrs.next()) {
					
					 newshid = sidrs.getInt(1);
				}else{
					ps.execute();
				}
				ps2.setString(1, sname);
				if(newshid == null){
				ResultSet sidrs2 = ps2.executeQuery();
				if(sidrs2.next()) {
					newshid = sidrs2.getInt(1);
				}
				}	ps_localTrades.setString(1, sname);
					ResultSet trades = ps_localTrades.executeQuery();
					while(trades.next()){
						try{
						awsTrade.setInt(1, newshid);
						awsTrade.setDate(2,trades.getDate("tradeDate"));
						awsTrade.setDouble(3, trades.getDouble("current_price"));
						awsTrade.setDouble(4, trades.getDouble("prev_pric"));
						awsTrade.setFloat(5, trades.getFloat("change_percent"));
						awsTrade.setTimestamp(6,new Timestamp(new Date().getTime()));
						awsTrade.execute();
						}catch(Exception e){
							
						}
					}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				localCon.close();
				awsConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;databaseName=share;", "sa",
					"Cps@2016");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private Connection getAWSConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://ec2-52-33-255-80.us-west-2.compute.amazonaws.com:3306/share", "sa", "Cps@2016");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] arguments) throws Exception {

		MoveData t = new MoveData();
		try {
			t.loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
