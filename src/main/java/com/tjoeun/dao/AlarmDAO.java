package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.vo.AlarmVO;
import com.tjoeun.vo.BoardCommentVO;

public class AlarmDAO {

	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

	private DataSource dataSource;
	

	// dao클래스의 bean이 생성 될 떄 오라클과 연결한다.
	public AlarmDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			logger.info("연결진행했고, 성공");
		} catch (NamingException e) {
			logger.info("연결오류남");
		}

	}


	public List<AlarmVO> alarmList(String memberId) {
		logger.info("alarmList()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        List<AlarmVO> list = new ArrayList<>();

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * from picok.alarm where to_id = ? order by alarmdate desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
						
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				AlarmVO vo = ctx.getBean("alarmVO",AlarmVO.class);
				vo.setAlarm_idx(rs.getInt("alarm_idx"));
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setTo_id(rs.getString("to_id"));
				vo.setFrom_id(rs.getString("from_id"));
				vo.setCategory(rs.getString("category"));
				vo.setAlarmdate(rs.getDate("alarmdate"));
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

	//댓글에 대한 알림 추가
	public void addComment(BoardCommentVO boardCommentVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			//타이틀이랑 글작성자 id는 메서드 실행해서 따로 가져오기 
	        Map<String, String> idAndTitle = getToIdAndTitleByBoardIdx(boardCommentVO.getBoard_idx());

	        String to_id = idAndTitle.get("to_id");
	        String board_title = idAndTitle.get("boardTitle");

			String sql = "INSERT INTO picok.alarm (alarm_idx, board_idx, board_title, to_id, from_id, category) "
					+ "VALUES (picok.alarm_SEQ.nextval,?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardCommentVO.getBoard_idx());
			pstmt.setString(2, board_title);
			pstmt.setString(3, to_id);
			pstmt.setString(4, boardCommentVO.getId());
			pstmt.setString(5, "reply");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//알람이 뜰 id(to_id)를 가져오기 & 글 제목도 같이 가져오기 
	public Map<String, String> getToIdAndTitleByBoardIdx(int boardIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Map<String, String> result = new HashMap<>();

	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT id, board_title FROM picok.board WHERE board_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardIdx);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String id = rs.getString("id");
	            String boardTitle = rs.getString("board_title");
	            result.put("to_id", id);
	            result.put("boardTitle", boardTitle);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return result;
	}

	//좋아요에 대한 알림 추가 
	public void addLike(int board_idx, String member_id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			//타이틀이랑 글작성자 id는 메서드 실행해서 따로 가져오기 
	        Map<String, String> idAndTitle = getToIdAndTitleByBoardIdx(board_idx);

	        String to_id = idAndTitle.get("to_id");
	        String board_title = idAndTitle.get("boardTitle");

			String sql = "INSERT INTO picok.alarm (alarm_idx, board_idx, board_title, to_id, from_id, category) "
					+ "VALUES (picok.alarm_SEQ.nextval,?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setString(2, board_title);
			pstmt.setString(3, to_id);
			pstmt.setString(4, member_id);
			pstmt.setString(5, "like");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//좋아요 취소하면 알람에서도 취소 
	public void cancelLike(int board_idx, String member_id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = dataSource.getConnection();
	        String sql = "DELETE FROM picok.alarm WHERE board_idx = ? AND from_id = ? AND category = 'like'";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, board_idx);
	        pstmt.setString(2, member_id);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	//리플 삭제될 시에 알람에서도 삭제 (alarmdate <-> comment reg_date 대조해서 삭제)
	public void deleteComment(int board_idx, int comment_idx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = dataSource.getConnection();

	        //comment_idx로 comment reg_date 가져오기(timestamp여야 정확한 비교가 가능하다)
	        Timestamp comment_reg_date = getCommentRegDateByCommentIdx(comment_idx);

	        String sql = "DELETE FROM picok.alarm WHERE alarmdate = ? AND board_idx = ? AND category = 'reply'";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setTimestamp(1, comment_reg_date);
	        pstmt.setInt(2, board_idx);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


	private Timestamp getCommentRegDateByCommentIdx(int comment_idx) {	
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Timestamp comment_reg_date = null;
	    System.out.println("comment_idx"+comment_idx);
	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT comment_reg_date FROM picok.board_comment WHERE comment_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, comment_idx);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            comment_reg_date = rs.getTimestamp("comment_reg_date");
	            System.out.println("comment_reg_date"+comment_reg_date);
	        } else {System.out.println("검색되지않음");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return comment_reg_date;
	}

	//클릭으로 딱 하나의 알람 지우기
	public void deleteSingleAlarm(int alarm_idx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = dataSource.getConnection();

	        String sql = "DELETE FROM picok.alarm WHERE alarm_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, alarm_idx);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	//알람 전체삭제 
	public void deleteAllAlarm(String from_id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = dataSource.getConnection();

	        String sql = "DELETE FROM picok.alarm WHERE to_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, from_id);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	//alarm count해줌
	public int countAlarm(String to_id) {		
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int count = 0;
	    
	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT COUNT(*) AS cnt FROM picok.alarm WHERE to_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, to_id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt("cnt");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return count;
	}

}

