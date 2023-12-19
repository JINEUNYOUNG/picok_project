package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.vo.BoardCommentVO;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.MemberVO;
import com.tjoeun.vo.ReplyVO;


public class BoardDAO {
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

	private DataSource dataSource;
	

	// dao클래스의 bean이 생성 될 떄 오라클과 연결한다.
	public BoardDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			logger.info("연결진행했고, 성공");
		} catch (NamingException e) {
			logger.info("연결오류남");
		}

	}

	// 파일을 업로드 할때마다 업로드하는 파일 이름과 실제로 저장되는 파일 이름을 테이블에 가져와 저장하는 메소드 insert
	public void upload(String file_name, String id, String category, String board_title, String board_content) {
		logger.info("dao로 와서 upload()");

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "insert into picok.board(board_idx,id,category,board_title,board_content,file_name) values (picok.board_seq.nextval,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, category);
			pstmt.setString(3, board_title);
			pstmt.setString(4, board_content);
			pstmt.setString(5, file_name);
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


	public ArrayList<BoardVO> adminBoard() {
		logger.info("adminboard()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> list = null; 

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * from picok.board order by board_idx desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<BoardVO>(); 
			
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardVO vo = ctx.getBean("boardVO",BoardVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setId(rs.getString("id"));
				vo.setCategory(rs.getString("category"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setFile_name(rs.getString("file_name"));
				vo.setBoard_reg_date(rs.getDate("board_reg_date"));
				vo.setView_num(rs.getInt("view_num"));
				vo.setLikes_num(rs.getInt("likes_num"));
				vo.setReport_num(rs.getInt("report_num"));
				vo.setSecretOK(rs.getString("secretOK").charAt(0));

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



	public int selectCount() {
		logger.info("selectcount()");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) from picok.board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
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
		return result;
	}

	public ArrayList<BoardVO> BoardlistById(HashMap<String, Object> hmap) {
		logger.info("boardlistbyid()");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> list = null; 
	    String id = (String) hmap.get("id");
	    int startNo = (Integer) hmap.get("startNo");
	    int endNo = (Integer) hmap.get("endNo");


		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM ("
				    + "    SELECT board_idx, category, board_title, board_content, file_name, "
				    + "           TO_CHAR(board_reg_date, 'YYYY-MM-DD') AS board_reg_date, "
				    + "           ROW_NUMBER() OVER (ORDER BY board_reg_date DESC) AS row_num FROM picok.board "
				    + "    WHERE id = ?" + ") WHERE row_num BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			rs = pstmt.executeQuery();
						
			list = new ArrayList<BoardVO>();
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardVO vo = ctx.getBean("boardVO",BoardVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setCategory(rs.getString("category"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setFile_name(rs.getString("file_name"));
				vo.setBoard_reg_date(rs.getDate("board_reg_date"));
				list.add(vo);
				logger.info(list.toString());
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

	public int selectCountByCategory(String category) {
		logger.info("selectcount()");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) from picok.board where category=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
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
		return result;
	}

	public ArrayList<BoardVO> BoardlistByCategory(HashMap<String, Object> hmap) {
		logger.info("boardlistbyid()");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> list = null; 
	    int startNo = (Integer) hmap.get("startNo");
	    int endNo = (Integer) hmap.get("endNo");
	    String category = (String) hmap.get("category");


		try {
			conn = dataSource.getConnection();
			String sql = " select * from (" + 
					"         select rownum rnum, TT.* from (" + 
					"        	 select * from picok.board where category =? order by board_reg_date desc" + 
					"         )TT where rownum <= ?" + 
					"      )where rnum >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, endNo);
			pstmt.setInt(3, startNo);
			rs = pstmt.executeQuery();
						
			list = new ArrayList<BoardVO>();
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardVO vo = ctx.getBean("boardVO",BoardVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setId(rs.getString("id"));
				vo.setCategory(rs.getString("category"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setFile_name(rs.getString("file_name"));
				vo.setBoard_reg_date(rs.getDate("board_reg_date"));
				list.add(vo);
				logger.info(list.toString());
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

	public ArrayList<BoardVO> Boardlist2ByCategory(HashMap<String, Object> hmap) {
		logger.info("boardlist2bycategory(인기순)()");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> list = null; 
	    int startNo = (Integer) hmap.get("startNo");
	    int endNo = (Integer) hmap.get("endNo");
	    String category = (String) hmap.get("category");


		try {
			conn = dataSource.getConnection();
			String sql = " 		SELECT *" + 
					"		FROM (" + 
					"		    SELECT ROWNUM AS rnum, subq.*" + 
					"		    FROM (" + 
					"		        SELECT b.*, COUNT(l.board_idx) AS like_count" + 
					"		        FROM picok.board b" + 
					"		        LEFT JOIN likes l ON b.board_idx = l.board_idx" + 
					"		        WHERE b.category = ?" + 
					"		        GROUP BY b.board_idx,b.id,b.category,b.board_title,b.board_content,b.file_name,b.board_reg_date,b.view_num,b.likes_num,b.report_num,b.deleteOK,b.secretOK" + 
					"		        ORDER BY like_count DESC, b.board_reg_date DESC" + 
					"		    ) subq" + 
					"		    WHERE ROWNUM <= ?" + 
					"		)" + 
					"		WHERE rnum >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, endNo);
			pstmt.setInt(3, startNo);
			rs = pstmt.executeQuery();
						
			list = new ArrayList<BoardVO>();
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardVO vo = ctx.getBean("boardVO",BoardVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setCategory(rs.getString("category"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setFile_name(rs.getString("file_name"));
				vo.setBoard_reg_date(rs.getDate("board_reg_date"));
				list.add(vo);
				logger.info(list.toString());
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

	public int selectLikeCount(String id) {
		logger.info("selectLikeCount()");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) AS likeCount FROM picok.likes WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
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
		return result;
	}
	public ArrayList<BoardVO> LikeBoardlistById(HashMap<String, Object> hmap) {
		logger.info("likeboardlistbyid()");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> list = null; 
		
	    String id = (String) hmap.get("id");
	    int startNo = (Integer) hmap.get("startNo");
	    int endNo = (Integer) hmap.get("endNo");

	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT * FROM (SELECT b.board_idx, b.category, b.file_name, " +
	                "ROW_NUMBER() OVER (ORDER BY l.like_reg_date DESC) AS row_num " +
	                "FROM picok.likes l " +
	                "JOIN picok.board b ON l.board_idx = b.board_idx " +
	                "WHERE l.id = ?) " +
	                "WHERE row_num BETWEEN ? AND ?";

	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, id);
	    pstmt.setInt(2, startNo);
	    pstmt.setInt(3, endNo);

	        rs = pstmt.executeQuery();
						
			list = new ArrayList<BoardVO>();
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardVO vo = ctx.getBean("boardVO",BoardVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setCategory(rs.getString("category"));
				vo.setFile_name(rs.getString("file_name"));
				list.add(vo);
				logger.info(list.toString());
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

	public boolean deleteByIdx(int idxToDelete) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM picok.board WHERE board_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idxToDelete);
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

	public ArrayList<BoardVO> adminBoardFilter(String category) {		
		logger.info("adminboard()");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> list = null; 
	
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * from picok.board where category = ? order by board_idx desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<BoardVO>(); 
			
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardVO vo = ctx.getBean("boardVO",BoardVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setId(rs.getString("id"));
				vo.setCategory(rs.getString("category"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setFile_name(rs.getString("file_name"));
				vo.setBoard_reg_date(rs.getDate("board_reg_date"));
				vo.setView_num(rs.getInt("view_num"));
				vo.setLikes_num(rs.getInt("likes_num"));
				vo.setReport_num(rs.getInt("report_num"));
				vo.setSecretOK(rs.getString("secretOK").charAt(0));
	
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

	//	좋아요 취소
	public void cancelLike(int board_idx, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			String query = "delete from picok.likes where board_idx = ? and id = ?";
			 pstmt = conn.prepareStatement(query);
			 
			 pstmt.setInt(1, board_idx);
			 pstmt.setString(2, id);
			 pstmt.executeUpdate();
			 
			 conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//	게시글 댓글 작성하는 메소드
	public void addComment(BoardCommentVO boardCommentVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO picok.board_comment (board_idx, comment_idx, id, comment_content, comment_reg_date) "
					+ "VALUES (?, picok.board_comment_seq.nextval, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			System.out.println("boardCommentVO.getBoard_idx(): " + boardCommentVO.getBoard_idx());
			pstmt.setInt(1, boardCommentVO.getBoard_idx());
			pstmt.setString(2, boardCommentVO.getId());
			pstmt.setString(3, boardCommentVO.getComment_content());
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


	//	신고 메소드
	public void report(int board_idx, String ip) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "insert into picok.report(board_idx,ip) values (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setString(2, ip);
		
			pstmt.executeUpdate();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}



	//	댓글 삭제 메소드
	public boolean deleteComment(int board_idx, int comment_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();

			// 게시글에 연결된 댓글 삭제
			String deleteCommentQuery = "DELETE FROM picok.board_comment WHERE comment_idx = ?";
			pstmt = conn.prepareStatement(deleteCommentQuery);
			pstmt.setInt(1, comment_idx);
			pstmt.executeUpdate();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	//	게시글 삭제하는 메소드
	public boolean writeDelete(int board_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		 try {
		        conn = dataSource.getConnection();
		        /*
		        // 게시글에 연결된 댓글 삭제
		        String deleteCommentQuery = "DELETE FROM picok.board_comment WHERE board_idx = ?";
		        pstmt = conn.prepareStatement(deleteCommentQuery);
		        pstmt.setInt(1, board_idx);
		        pstmt.executeUpdate();
		        
		        // 게시글에 연결된 댓글 삭제
		        String deleteViewLogQuery = "DELETE FROM picok.view_log WHERE board_idx = ?";
		        pstmt = conn.prepareStatement(deleteViewLogQuery);
		        pstmt.setInt(1, board_idx);
		        pstmt.executeUpdate();
		        
		        // 게시글에 연결된 댓글 삭제
		        String deleteLikeQuery = "DELETE FROM picok.likes WHERE board_idx = ?";
		        pstmt = conn.prepareStatement(deleteLikeQuery);
		        pstmt.setInt(1, board_idx);
		        pstmt.executeUpdate();
		        
		        // 게시글에 연결된 댓글 삭제
		        String deleteReportQuery = "DELETE FROM picok.report WHERE board_idx = ?";
		        pstmt = conn.prepareStatement(deleteReportQuery);
		        pstmt.setInt(1, board_idx);
		        pstmt.executeUpdate();
*/
		        // 게시글 삭제
		        String deleteBoardQuery = "DELETE FROM picok.board WHERE board_idx = ?";
		        pstmt = conn.prepareStatement(deleteBoardQuery);
		        pstmt.setInt(1, board_idx);
		        pstmt.executeUpdate();
		        
		        conn.close();
		        //pstmt.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
			return true;
	}


	//	좋아요 판별 selectLike()
	public boolean selectLike(int board_idx, String id) {
		logger.info("BoardDAO의 selectLike() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) cnt from picok.likes where board_idx = ? and id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt("cnt") > 0) {
					result = true;
				}
			}
			conn.close();
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}


	//	좋아요 
	public void addLikes(int board_idx, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "insert into picok.likes(board_idx, id, like_reg_date) values (?, ?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	boardSingleList
	public BoardVO boardSingleList(int board_idx) {
		logger.info("BoardDAO의 boardSingleList() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardVO vo = ctx.getBean("boardVO",BoardVO.class);	
		
		try {
			conn = dataSource.getConnection();
			String query = "select * FROM picok.board WHERE board_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board_idx);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setId(rs.getString("id"));
				vo.setCategory(rs.getString("category"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setFile_name(rs.getString("file_name"));
				vo.setBoard_reg_date(rs.getDate("board_reg_date"));
				vo.setView_num(rs.getInt("view_num"));
				vo.setLikes_num(rs.getInt("likes_num"));
				vo.setReport_num(rs.getInt("report_num"));
				vo.setSecretOK(rs.getString("secretOK").charAt(0));
			}
			conn.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}


	//	조회수 판별
	public int selectBoardView(int board_idx, String ip) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			System.out.println("selectBoardView DB 드갔어요");
			conn = dataSource.getConnection();
			String sql = "select count(*) cnt from picok.view_log where board_idx = ? and ip = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setString(2, ip);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("rs : " + rs.getInt("cnt"));
				result = rs.getInt("cnt");
			}
			
			rs.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("result : " + result);
		System.out.println("selectBoardView 끝났어요");
		return result;
	}


	//	조회수 증가
	public void addViewNum(int board_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String query = "update board set view_num = view_num + 1 where board_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board_idx);
			rs = pstmt.executeQuery();
			
			conn.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//	조회수
	public void uploadView(int board_idx, String ip) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "insert into picok.view_log(board_idx,ip) values (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setString(2, ip);
			pstmt.executeUpdate();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//	viewComment
	public ArrayList<BoardCommentVO> viewComment(int board_idx) {
		logger.info("BoardDAO의 viewComment() 메소드 실행");	
		
//		ArrayList<BoardCommentVO> lists = new ArrayList<BoardCommentVO>();
		ArrayList<BoardCommentVO> lists = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
		    // 현재 페이지 목록 가져오기. (관리자 권한으로 전체) 
		    String sql = "SELECT bc.*, m.nickname FROM picok.board_comment bc, picok.member m where bc.id = m.id and bc.board_idx = ? order by bc.comment_reg_date";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, board_idx);
		    rs = pstmt.executeQuery();
		    lists = new ArrayList<BoardCommentVO>();
			
			// 데이터베이스에서 글 목록 가져와서 리스트에 추가
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardCommentVO vo = ctx.getBean("boardCommentVO", BoardCommentVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setComment_idx(rs.getInt("comment_idx"));
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
				vo.setComment_content(rs.getString("comment_content"));
				vo.setComment_ceg_Date(rs.getDate("comment_reg_Date"));
				
				lists.add(vo);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lists;
	}


	//	좋아요 수 countLikes()
	public int countLikes(int board_idx) {
		logger.info("BoardDAO의 countLikes() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) cnt from picok.likes where board_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt("cnt");
			}
			conn.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}


	//	신고 판별
	public boolean ReportCheck(int board_idx, String ip) {
		logger.info("BoardDAO의 ReportCheck() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) cnt from picok.report where board_idx = ? and ip = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setString(2, ip);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt("cnt") > 0) {
					result = true;
				}
			}
			
			conn.close();
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}



	//	작성자가 게시글 수정하는 메소드 writeUpdate()
	public boolean writeUpdate(int board_idx, String id, String category, String board_title, String board_content,
			String file_name) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
	        conn = dataSource.getConnection();

	        // 게시글 업데이트
	        String updateBoardQuery = "UPDATE picok.board SET category = ?, board_title = ?, board_content = ?, file_name = ?, board_reg_date=sysdate WHERE board_idx = ? AND id = ?";
	        pstmt = conn.prepareStatement(updateBoardQuery);
	        pstmt.setString(1, category);
	        pstmt.setString(2, board_title);
	        pstmt.setString(3, board_content);
	        pstmt.setString(4, file_name);
	        pstmt.setInt(5, board_idx);
	        pstmt.setString(6, id);

	        pstmt.executeUpdate();

	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
		return true;
	}

	public ArrayList<BoardVO> otherBoardById(String member_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> list = null; 

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM (\n" + 
					"    SELECT * FROM picok.board WHERE id = ? ORDER BY board_idx DESC\n" + 
					") WHERE ROWNUM <= 9";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<BoardVO>(); 
			
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
				BoardVO vo = ctx.getBean("boardVO",BoardVO.class);
				vo.setBoard_idx(rs.getInt("board_idx"));
				vo.setId(rs.getString("id"));
				vo.setCategory(rs.getString("category"));
				vo.setBoard_title(rs.getString("board_title"));
				vo.setBoard_content(rs.getString("board_content"));
				vo.setFile_name(rs.getString("file_name"));
				vo.setBoard_reg_date(rs.getDate("board_reg_date"));
				vo.setReport_num(rs.getInt("report_num"));

				list.add(vo);
				System.out.println(list);
				System.out.println(vo);
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
//  =============================================================================
  //   진욱 코드 추가
  //   report_num Update() - 신고수 (신고여부 x)
  public void reportNumUpdate(int board_idx, int report_num) {
     logger.info("BoardDAO의 reportNumUpdate() 메소드 실행");
     Connection conn = null;
     PreparedStatement pstmt = null;
     
     try {
          conn = dataSource.getConnection();

          // 게시글 업데이트
          String updateBoardQuery = "UPDATE picok.board SET report_num = ? WHERE board_idx = ?";
          pstmt = conn.prepareStatement(updateBoardQuery);
          pstmt.setInt(1, report_num);
          pstmt.setInt(2, board_idx);
          pstmt.executeUpdate();

          conn.close();
          pstmt.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

  //   특정 신고수 이상일 때 게시글이 삭제
  public void reportDelete(int board_idx) {
     logger.info("BoardDAO의 reportDelete() 메소드 실행");
     Connection conn = null;
     PreparedStatement pstmt = null;
     
     try {
        conn = dataSource.getConnection();
        conn.setAutoCommit(false); // 수동 커밋 모드 설정
        
         // 게시글 삭제
          String deleteBoardQuery = "DELETE FROM picok.board WHERE board_idx = ?";
          pstmt = conn.prepareStatement(deleteBoardQuery);
          pstmt.setInt(1, board_idx);
          pstmt.executeUpdate();
          
          conn.commit(); 
          conn.close();
          pstmt.close();
     } catch (Exception e) {
        e.printStackTrace();
     }
  } // reportDelete()
  //   코드 추가 끝
  public void addReply(ReplyVO replyVO) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      try {
         conn = dataSource.getConnection();
         String sql = "INSERT INTO picok.reply (board_idx, comment_idx, reply_idx, id, reply_content, reply_reg_date) "
               + "VALUES (?, ?, picok.reply_seq.nextval, ?, ?, SYSDATE)";
         pstmt = conn.prepareStatement(sql);
         System.out.println("replyVO.getBoard_idx(): " + replyVO.getBoard_idx());
         pstmt.setInt(1, replyVO.getBoard_idx());
         pstmt.setInt(2, replyVO.getComment_idx());
         pstmt.setString(3, replyVO.getId());
         pstmt.setString(4, replyVO.getReply_content());
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
public ArrayList<ReplyVO> selectReply(int board_idx) {
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      ArrayList<ReplyVO> replyList = new ArrayList<ReplyVO>();
      
      try {
         conn = dataSource.getConnection();
         //String query = "select * FROM picok.reply WHERE board_idx = ?";
         String query = "SELECT reply.*, m.nickname FROM picok.reply reply, picok.member m where reply.id = m.id and reply.board_idx = ?";

         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, board_idx);
         rs = pstmt.executeQuery();
         while (rs.next()) {
            ReplyVO vo = new ReplyVO();
            vo.setBoard_idx(rs.getInt("board_idx"));
            vo.setComment_idx(rs.getInt("comment_idx"));
            vo.setReply_idx(rs.getInt("reply_idx"));
            vo.setId(rs.getString("id"));
            vo.setReply_content(rs.getString("reply_content"));
            vo.setNickname(rs.getString("nickname"));
            replyList.add(vo);
            System.out.println("----"+vo.toString());
         }
         conn.close();
         rs.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return replyList;
   }
	// 대댓글 삭제
	 public boolean deleteReply(int board_idx, int reply_idx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	       conn = dataSource.getConnection();
	
	       // 게시글에 연결된 댓글 삭제
	       String deleteCommentQuery = "DELETE FROM picok.reply WHERE reply_idx = ?";
	       pstmt = conn.prepareStatement(deleteCommentQuery);
	       pstmt.setInt(1, reply_idx);
	       pstmt.executeUpdate();
	
	       conn.close();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	    return true;
	 }
}
    

