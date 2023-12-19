package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.vo.AlarmVO;
import com.tjoeun.vo.MessageVO;
//
//public class MessageDAO {
//	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
//
//	private DataSource dataSource;
//	
//	public MessageDAO() {
//		try {
//			Context context = new InitialContext();
//			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
//			logger.info("연결진행했고, 성공");
//		} catch (NamingException e) {
//			logger.info("연결오류남");
//		}
//	}
//
//	// 메세지 리스트
//	public ArrayList<MessageVO> messageList(MessageVO vo) {
//
//		  String from_id = vo.getFrom_id();
//		  
//	      ArrayList<MessageVO> list = null;
//	      Connection conn = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//				conn = dataSource.getConnection();
//				String sql = "SELECT * from picok.message where from_id = ? order by messagedate desc";
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setString(1, from_id);
//				rs = pstmt.executeQuery();
//							
//				while (rs.next()) {
//					AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//					vo.setMessage_idx(rs.getInt("alarm_idx"));
//					vo.setContent(rs.getString("content"));
//					vo.setTo_id(rs.getString("to_id"));
//					vo.setFrom_id(rs.getString("from_id"));
//					vo.setRead_chk(rs.getString("read_chk"));
//					vo.setMessagedate(rs.getDate("messagedate"));
//					list.add(vo);
//
//				}			
//			} catch (SQLException e) {
//				logger.info("커넥션풀불러오기실패");
//			} finally {
//				if (conn != null) {
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			return list;
//	}
//
//}
