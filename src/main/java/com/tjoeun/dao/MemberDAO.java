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

import com.tjoeun.vo.MemberVO;

public class MemberDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

	private DataSource dataSource;
	
	// dao클래스의 bean이 생성 될 떄 오라클과 연결한다.
	public MemberDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			logger.info("연결진행했고, 성공");
		} catch (NamingException e) {
			logger.info("연결오류남");
		}

	}
	


	public MemberVO login(MemberVO memberVO) {
		logger.info("login()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO mvo = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT mem_idx, id, password, name, nickname, email, addr, phone_num, joindate, mem_lv FROM picok.member "
					+ "WHERE id = ? and password = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPassword());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				mvo = ctx.getBean("memberVO",MemberVO.class);
				mvo.setMem_idx(rs.getInt("mem_idx"));
				mvo.setId(rs.getString("id"));
				mvo.setPassword(rs.getString("password"));
				mvo.setName(rs.getString("name"));
				mvo.setNickname(rs.getString("nickname"));
				mvo.setEmail(rs.getString("email"));
				mvo.setAddr(rs.getString("addr"));
				mvo.setPhone_num(rs.getString("phone_num"));
				mvo.setJoindate(rs.getDate("joindate"));
				mvo.setMem_lv(rs.getInt("mem_lv"));
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
		return mvo;	
	}

	public boolean deleteById(String idxToDelete) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM picok.member WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idxToDelete);
			pstmt.executeUpdate();

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<MemberVO> adminMemberSearch(String query) {
		logger.info("adminmembersearch()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberVO> list = null; 

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT mem_idx, id, password, name, nickname, email, addr, phone_num, joindate, mem_lv FROM picok.member "
					+ "WHERE name LIKE ? OR nickname LIKE ? OR email LIKE ? OR id LIKE ?";
			pstmt = conn.prepareStatement(sql);
			String searchKeyword = "%" + query + "%";
			for (int i = 1; i <= 4; i++) {
				pstmt.setString(i, searchKeyword);
			}
			rs = pstmt.executeQuery();
			
			list = new ArrayList<MemberVO>(); 
			
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				MemberVO vo = ctx.getBean("memberVO",MemberVO.class);
				vo.setMem_idx(rs.getInt("mem_idx"));
				vo.setId(rs.getString("id"));
				vo.setPassword(rs.getString("password"));
				vo.setName(rs.getString("name"));
				vo.setNickname(rs.getString("nickname"));
				vo.setEmail(rs.getString("email"));
				vo.setAddr(rs.getString("addr"));
				vo.setPhone_num(rs.getString("phone_num"));
				vo.setJoindate(rs.getDate("joindate"));
				vo.setMem_lv(rs.getInt("mem_lv"));

				list.add(vo);

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
	public ArrayList<MemberVO> adminMember() {
		logger.info("adminmember()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberVO> list = null; 

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * from picok.member";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<MemberVO>(); 
			
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				MemberVO vo = ctx.getBean("memberVO",MemberVO.class);
				vo.setMem_idx(rs.getInt("mem_idx"));
				vo.setId(rs.getString("id"));
				vo.setPassword(rs.getString("password"));
				vo.setName(rs.getString("name"));
				vo.setNickname(rs.getString("nickname"));
				vo.setEmail(rs.getString("email"));
				vo.setAddr(rs.getString("addr"));
				vo.setPhone_num(rs.getString("phone_num"));
				vo.setJoindate(rs.getDate("joindate"));
				vo.setMem_lv(rs.getInt("mem_lv"));

				list.add(vo);

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


	public MemberVO updateMember(MemberVO memberVO) {
		logger.info("updateMember()");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE PICOK.MEMBER SET PASSWORD = ?, NICKNAME = ?, EMAIL = ?, ADDR = ?, PHONE_NUM = ? WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberVO.getPassword());
			pstmt.setString(2, memberVO.getNickname());
			pstmt.setString(3, memberVO.getEmail());
			pstmt.setString(4, memberVO.getAddr());
			pstmt.setString(5, memberVO.getPhone_num());
			pstmt.setString(6, memberVO.getId());
			pstmt.executeQuery();
			rs = pstmt.executeQuery();
			while (rs.next()) {
				memberVO.setId(rs.getString("id"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setNickname(rs.getString("nickname"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setAddr(rs.getString("addr"));
				memberVO.setPhone_num("phone_num");
			logger.info(memberVO.toString());

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
		return memberVO;
	}
	
	public int deleteAccount(String id) {
		logger.info("deleteAccount()");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "delete from picok.member where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
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

	public MemberVO autoLogin(String id) {
		logger.info("autologin()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO mvo = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT mem_idx, id, password, name, nickname, email, addr, phone_num, joindate, mem_lv FROM picok.member "
					+ "WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				mvo = ctx.getBean("memberVO",MemberVO.class);
				mvo.setMem_idx(rs.getInt("mem_idx"));
				mvo.setId(rs.getString("id"));
				mvo.setPassword(rs.getString("password"));
				mvo.setName(rs.getString("name"));
				mvo.setNickname(rs.getString("nickname"));
				mvo.setEmail(rs.getString("email"));
				mvo.setAddr(rs.getString("addr"));
				mvo.setPhone_num(rs.getString("phone_num"));
				mvo.setJoindate(rs.getDate("joindate"));
				mvo.setMem_lv(rs.getInt("mem_lv"));
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
		return mvo;	
	}
}
