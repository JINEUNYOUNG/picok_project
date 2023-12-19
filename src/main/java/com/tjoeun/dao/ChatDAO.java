package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.Date;
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

import com.tjoeun.vo.ChatVO;
import com.tjoeun.vo.MessageVO;

public class ChatDAO {
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

	private DataSource dataSource;
	
	public ChatDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			logger.info("연결진행했고, 성공");
		} catch (NamingException e) {
			logger.info("연결오류남");
		}
	}

	// 모든 채팅방 리스트 (message 테이블과 join해서 최근 글 1건과 날짜, 읽었는지를 함께 가져온다.)
	public ArrayList<ChatVO> chatListById(String from_id) {
			ArrayList<ChatVO> list = new ArrayList<>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = dataSource.getConnection();
				String sql = "SELECT \n" + 
						"    c.*,\n" + 
						"    m.max_writedate,\n" + 
						"    m.write_content,\n" + 
						"    m.readdate,\n" + 
						"    m.write_id \n" + 
						"FROM picok.chat c\n" + 
						"LEFT JOIN (\n" + 
						"    SELECT \n" + 
						"        m1.chat_idx,\n" + 
						"        m1.max_writedate,\n" + 
						"        m2.write_content,\n" + 
						"        m2.readdate,\n" + 
						"        m2.write_id\n" + 
						"    FROM (\n" + 
						"        SELECT \n" + 
						"            chat_idx,\n" + 
						"            MAX(writedate) AS max_writedate\n" + 
						"        FROM picok.message\n" + 
						"        GROUP BY chat_idx\n" + 
						"    ) m1\n" + 
						"    INNER JOIN picok.message m2 ON m1.chat_idx = m2.chat_idx AND m1.max_writedate = m2.writedate\n" + 
						") m ON c.chat_idx = m.chat_idx\n" + 
						"WHERE (c.exit_id IS NULL OR c.exit_id != ?) AND (c.from_id = ? OR c.to_id = ?)\n" + 
						"ORDER BY m.max_writedate DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, from_id);
				pstmt.setString(2, from_id);
				pstmt.setString(3, from_id);
				rs = pstmt.executeQuery();
				System.out.println(rs.toString());
							
				while (rs.next()) {
					AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
					ChatVO vo = ctx.getBean("chatVO",ChatVO.class);
					MessageVO vo2 = ctx.getBean("messageVO",MessageVO.class);
					vo.setChat_idx(rs.getInt("chat_idx"));
					vo.setFrom_id(rs.getString("from_id"));
					vo.setTo_id(rs.getString("to_id"));
					vo.setChatdate(rs.getDate("chatdate"));
					vo.setExit_id(rs.getString("exit_id"));
					
					vo2.setWrite_id(rs.getString("write_id"));
					vo2.setReaddate(rs.getDate("readdate"));
					vo2.setWrite_content(rs.getString("write_content"));
					vo2.setWritedate(rs.getDate("max_writedate")); 
					vo.setLatest(vo2); 
					list.add(vo);
					System.out.println(list.toString());
				}			
			} catch (SQLException e) {
				logger.info("커넥션풀불러오기실패");
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
			return list;
	}

	// 채팅창 하나에 메시지 가져오기 
	public ArrayList<MessageVO> chatContentByIdx(int chat_idx) {
			ArrayList<MessageVO> list = new ArrayList<>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = dataSource.getConnection();
				String sql = "SELECT * FROM picok.message where chat_idx = ? order by writedate";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, chat_idx);
				rs = pstmt.executeQuery();
				System.out.println(rs.toString());
							
				while (rs.next()) {
					AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
					MessageVO vo = ctx.getBean("messageVO",MessageVO.class);
					vo.setMessage_idx(rs.getInt("message_idx"));
					vo.setChat_idx(rs.getInt("chat_idx"));
					vo.setWrite_id(rs.getString("write_id"));
					vo.setWrite_content(rs.getString("write_content"));
					vo.setWritedate(rs.getDate("writedate"));
					Date readdate = rs.getDate("readdate");
					if (readdate != null) {
						vo.setReaddate(rs.getDate("readdate"));
					}
					list.add(vo);
					System.out.println(list.toString());
				}			
			} catch (SQLException e) {
				logger.info("커넥션풀불러오기실패");
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
			return list;
		}


	public void chatInsert(String from_id, int chat_idx, String message) {
		logger.info("chatInsert");
		logger.info(from_id+chat_idx+message);
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "Insert into picok.message(message_idx, chat_idx, write_id, write_content,readdate) "
					+ "values (picok.message_SEQ.nextval, ?, ?, ?, null)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_idx);
			pstmt.setString(2, from_id);
			pstmt.setString(3, message);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
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

	public Boolean chatCheck(String from_id, String to_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
            String sql = "SELECT COUNT(DISTINCT chat_idx) AS chat_count\n" + 
            		"FROM picok.chat\n" + 
            		"WHERE (from_id = ? AND to_id = ?)\n" + 
            		"   OR (from_id = ? AND to_id = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, from_id);
			pstmt.setString(2, to_id);
			pstmt.setString(3, to_id);
			pstmt.setString(4, from_id);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            int chatCount = rs.getInt("chat_count");
	            return chatCount >= 1;
	        }

	    } catch (SQLException e) {
	        logger.info("커넥션풀불러오기실패");
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
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
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return false;
	}


	public void chatCreate(String from_id, String to_id) {
		logger.info("chatCreate");
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "Insert into picok.chat(chat_idx, from_id, to_id) "
					+ "values (picok.message_SEQ.nextval, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, from_id);
			pstmt.setString(2, to_id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
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

	public void updateMessageRead(int chat_idx, String user) {
		logger.info("읽음으로 바꿔주기");
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE picok.message " + 
					"SET readdate = CURRENT_TIMESTAMP " + 
					"WHERE chat_idx = ? " + 
					"AND write_id != ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_idx);
			pstmt.setString(2, user);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
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
	
	//상대방이 이미 나간 채팅방인지 체크
	public boolean chatExitCheck(int chat_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			String sql = "select * from picok.chat where chat_idx = ? and exit_id is not null";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_idx);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
	            return true;
			}
		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
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
		return false;
	}

	public void chatDelete(int chat_idx) {
		logger.info("채팅방 삭제");
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "delete from picok.chat where chat_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_idx);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
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

	public void chatExit(int chat_idx, String user) {
		logger.info("채팅방 나가기");
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "update picok.chat set exit_id = ? where chat_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			pstmt.setInt(2, chat_idx);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
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

	public void chatReEnter(String from_id, String to_id) {
		logger.info("채팅방 나간 기록 지워주기");
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE picok.chat SET exit_id = null WHERE exit_id = ? " +
		             "AND ((from_id = ? AND to_id = ?) OR (from_id = ? AND to_id = ?))";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, from_id);
			pstmt.setString(2, from_id);
			pstmt.setString(3, to_id);
			pstmt.setString(4, to_id);
			pstmt.setString(5, from_id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.info("커넥션풀불러오기실패");
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

	public String chatFindId(String from_id, int chat_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String userId = null; // 결과로 받을 사용자 ID 변수

	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT DISTINCT CASE\n" + 
	                "  WHEN from_id <> ? THEN from_id\n" + 
	                "  ELSE to_id\n" + 
	                "END AS user_id\n" + 
	                "FROM picok.chat\n" + 
	                "WHERE chat_idx = ?\n" + 
	                "  AND (from_id <> ? OR to_id <> ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, from_id);
	        pstmt.setInt(2, chat_idx);
	        pstmt.setString(3, from_id);
	        pstmt.setString(4, from_id);

	        rs = pstmt.executeQuery(); // 쿼리 실행

	        // 결과 처리
	        if (rs.next()) {
	            userId = rs.getString("user_id"); // 결과에서 user_id 열을 가져옴
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return userId; // 사용자 ID 반환
	}

		
}
