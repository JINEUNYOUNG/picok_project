package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.ContactVO;

public class ContactDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

	private DataSource dataSource;
	
	// dao클래스의 bean이 생성 될 떄 오라클과 연결한다.
	public ContactDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			logger.info("연결진행했고, 성공");
		} catch (NamingException e) {
			logger.info("연결오류남");
		}

	}

	public void contactInsert(ContactVO contactVO) {
		logger.info("dao로 와서 contactinsert()");

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "insert into picok.contactBoard(idx,category,id,email,title,content,answerDate) values (picok.contactBoard_seq.nextval,'미응답',?,?,?,?,null)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, contactVO.getId());
			pstmt.setString(2, contactVO.getEmail());
			pstmt.setString(3, contactVO.getTitle());
			pstmt.setString(4, contactVO.getContent());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public ArrayList<ContactVO> contactBoard() {
	    logger.info("adminboard()");
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ArrayList<ContactVO> list = new ArrayList<ContactVO>(); // ArrayList 초기화 추가

	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT * from picok.contactBoard order by idx desc";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml"); // 한 번만 생성
	            ContactVO vo = ctx.getBean("contactVO", ContactVO.class);
	            vo.setIdx(rs.getInt("idx"));
	            vo.setId(rs.getString("id"));
	            vo.setCategory(rs.getString("category"));
	            vo.setTitle(rs.getString("title"));
	            vo.setContent(rs.getString("content"));
	            vo.setWriteDate(rs.getDate("writeDate"));
	            vo.setAnswer(rs.getString("answer"));
	            vo.setAnswerDate(rs.getDate("answerDate"));

	            list.add(vo);
	            logger.info(list + "");
	        }
	    } catch (SQLException e) {
	        logger.info("커넥션풀불러오기실패");
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return list;
	}


	public ArrayList<ContactVO> contactBoardFilter(String category) {
		logger.info("adminboard()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ContactVO> list = null; 
	
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * from picok.contactBoard where category = ? order by idx desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<ContactVO>(); 
			
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				ContactVO vo = ctx.getBean("contactVO",ContactVO.class);
				vo.setIdx(rs.getInt("idx"));
				vo.setId(rs.getString("id"));
				vo.setCategory(rs.getString("category"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setWriteDate(rs.getDate("writeDate"));
				vo.setAnswer(rs.getString("answer"));
				vo.setAnswerDate(rs.getDate("answerDate"));

				list.add(vo);
				logger.info(list+"");
	
			}			
		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
}

	public ContactVO contactSingle(ContactVO contactVO) {
	    logger.info("contactsingle()");
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml"); // 한 번만 생성
	    ContactVO vo = ctx.getBean("contactVO", ContactVO.class);

	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT * from picok.contactBoard where idx = ? order by idx desc";
	        pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, contactVO.getIdx());
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            vo.setIdx(rs.getInt("idx"));
	            vo.setId(rs.getString("id"));
	            vo.setEmail(rs.getString("email"));
	            vo.setCategory(rs.getString("category"));
	            vo.setTitle(rs.getString("title"));
	            vo.setContent(rs.getString("content"));
	            vo.setWriteDate(rs.getDate("writeDate"));
	            vo.setAnswer(rs.getString("answer"));
	            vo.setAnswerDate(rs.getDate("answerDate"));

	            logger.info(vo + "");
	        }
	    } catch (SQLException e) {
	        logger.info("커넥션풀불러오기실패");
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return vo;
	}

	public void contactOK(int idx, String content) {
		logger.info("dao로 와서 contactOK()");

	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
		conn = dataSource.getConnection();
		String sql = "UPDATE picok.contactBoard SET category = '응답완료', answer = ?, answerDate = sysdate WHERE idx = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setInt(2, idx);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

}
