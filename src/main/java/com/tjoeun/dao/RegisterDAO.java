package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tjoeun.vo.MemberVO;

public class RegisterDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterDAO.class);

	private DataSource dataSource;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public RegisterDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			logger.info("RegisterDAO 연결진행했고, 성공");
			logger.info("RegisterDAO DataSource 객체 상태: " + dataSource); // DataSource 객체 상태 로그 출력
		} catch (NamingException e) {
			logger.error("RegisterDAO 연결 오류남", e); // 예외 상세 메시지 로그 출력
		}
	}

   public void insertMember(MemberVO memberVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO picok.member (mem_idx, id, password, name, nickname, email, addr, phone_num, mem_lv) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, memberVO.getMem_idx());
			pstmt.setString(2, memberVO.getId());
			pstmt.setString(3, memberVO.getPassword());
			pstmt.setString(4, memberVO.getName());
			pstmt.setString(5, memberVO.getNickname());
			pstmt.setString(6, memberVO.getEmail());
			pstmt.setString(7, memberVO.getAddr());
			pstmt.setString(8, memberVO.getPhone_num());
			pstmt.setInt(9, memberVO.getMem_lv());

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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

   public int joinMember(MemberVO vo) {
      System.out.println("MemberDAO 클래스의 register() 메소드 실행");

      try {
         this.conn = this.dataSource.getConnection();
         String sql = "insert into picok.member(mem_idx, id, password, name, nickname, email, addr, phone_num, joindate) values (picok.member_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
         this.pstmt = this.conn.prepareStatement(sql);
         this.pstmt.setString(1, vo.getId());
         this.pstmt.setString(2, vo.getPassword());
         this.pstmt.setString(3, vo.getName());
         this.pstmt.setString(4, vo.getNickname());
         this.pstmt.setString(5, vo.getEmail());
         this.pstmt.setString(6, vo.getAddr());
         this.pstmt.setString(7, vo.getPhone_num());
         this.pstmt.setDate(8, new Date(vo.getJoindate().getTime()));
         this.pstmt.executeUpdate();
         this.conn.close();
         return 1;
      } catch (SQLException var3) {
         var3.printStackTrace();
         return 0;
      }
   }

   public int idCheck(String id) {
      System.out.println("RegisterDAO 클래스의 registerCheck() 메소드 실행");

      try {
         conn = dataSource.getConnection();
         String sql = "SELECT * FROM picok.member WHERE id = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         if (id.trim().equals("")) {
            return 1;
         } else {
            return rs.next() ? 2 : 3;
         }
      } catch (SQLException var3) {
         var3.printStackTrace();
         return 0;
      }
   }

   public int nicknameCheck(String nickname) {
      System.out.println("RegisterDAO 클래스의 registerCheck2() 메소드 실행");

      try {
         conn = dataSource.getConnection();
         String sql = "SELECT * FROM picok.member WHERE nickname = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, nickname);
         rs = pstmt.executeQuery();
         if (nickname.trim().equals("")) {
            return 1;
         } else {
            return rs.next() ? 2 : 3;
         }
      } catch (SQLException var3) {
         var3.printStackTrace();
         return 0;
      }
   }

   public int deleteMember(String id) {
      System.out.println("deleteMember 메소드가 호출되었습니다. 삭제할 ID: " + id);

      try {
         this.conn = this.dataSource.getConnection();
         String sql = "DELETE FROM picok.member WHERE id = ?";
         this.pstmt = this.conn.prepareStatement(sql);
         this.pstmt.setString(1, id);
         this.pstmt.executeUpdate();
         this.conn.close();
         return 1;
      } catch (SQLException var3) {
         var3.printStackTrace();
         return 0;
      }
   }
   
   public MemberVO updateMember(MemberVO vo) {
		MemberVO mvo = null;

	   System.out.println("RegisterDAO 클래스의 updateMember() 메소드 실행");
	    try {
	        conn = dataSource.getConnection();
	        String sql = "UPDATE picok.member SET password=?, nickname=?, email=?, addr=?, phone_num=? WHERE id=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, vo.getPassword());
	        pstmt.setString(2, vo.getNickname());
	        pstmt.setString(3, vo.getEmail());
	        pstmt.setString(4, vo.getAddr());
	        pstmt.setString(5, vo.getPhone_num());
	        pstmt.setString(6, vo.getId());
	        pstmt.executeUpdate();
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mvo = new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setPassword(rs.getString("password"));
				mvo.setNickname(rs.getString("nickname"));
				mvo.setEmail(rs.getString("email"));
				mvo.setAddr(rs.getString("addr"));
				mvo.setPhone_num(rs.getString("phone_num"));
			}
	        
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mvo;
	}
}
