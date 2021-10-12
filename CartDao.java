package shop.model.dao;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.model.vo.Cart;

public class CartDao {

	Connection conn = null;

	public CartDao() {

		try {
			if (conn != null) {
				close(conn);
			}

			this.conn = getConnection();

		} catch (Exception e) {
		}

	}
	
	@Override
	protected void finalize() throws Throwable { // 객체 종료시 호출되는 메소드
		close(conn);
	}
	
	public List<Cart> selectAll(String loginId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cart> list = new ArrayList<Cart>();

		try {
			String sql = "SELECT * FROM CART WHERE user_id = ? ";
			pstmt = conn.prepareStatement(sql); // 객체 생성
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {
				int proNo = rs.getInt(1);
				String memberId = rs.getString(2);
				int cartCnt = rs.getInt(3);
			
				Cart cart = new Cart(proNo, memberId, cartCnt);

				list.add(cart);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				if(rs!=null) {
				rs.close();
				}
				pstmt.close();
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			}
		}

		return null;

	}

	public Cart selectOne(String loginId, int whereProNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM cart WHERE USER_Id = ? AND pro_no = ? ";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			pstmt.setString(1, loginId);
			pstmt.setInt(2, whereProNo);

			rs = pstmt.executeQuery();// 쿼리 실행문

			if (rs.next()) {
				
				int proNo = rs.getInt(1);
				String memberId = rs.getString(2);
				int cartCnt = rs.getInt(3);
			
				Cart cart = new Cart(proNo, memberId, cartCnt);
				
				return cart;
			}

		} catch (SQLException e) {	
		}finally {
			try {
				if(rs!=null) {
				rs.close();
				}
				pstmt.close();
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			}
		}

		return null;
	}

	public int updateAddCnt(int proNo,String loginId, int addCnt) {
		PreparedStatement pstmt = null;

		try {

			String sql = "UPDATE Cart SET CART_CNT = ? WHERE pro_no = ? AND USER_ID = ? ";			pstmt = conn.prepareStatement(sql); // 객체 생성

			
			pstmt.setInt(1, selectOne(loginId, proNo).getCartCnt() + addCnt);
			pstmt.setInt(2, proNo);
			pstmt.setString(3, loginId);

			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
			
			return result;
			
			

		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			}
		}

		return -1;

	}

	
	
	public int insertCart(int proNo, String loginId, int cartCnt) {

		PreparedStatement pstmt = null;

		try {

			String sql = "INSERT INTO CART VALUES(?,?,?)";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			pstmt.setInt(1, proNo);
			pstmt.setString(2, loginId);
			pstmt.setInt(3, cartCnt);
			
			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
			
			return result;

		} catch (Exception e) {
			
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			}
		}

		return -1;

	}

	public int deleteProduct(String loginId, int proNo) {
		PreparedStatement pstmt = null;

		try {

			String sql = "DELETE FROM CART WHERE user_id = ? AND pro_no = ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			pstmt.setString(1, loginId);
			pstmt.setInt(2, proNo);

			int result = pstmt.executeUpdate();// 쿼리 실행문
			
System.out.println(""+result+" "+proNo+" "+loginId);
			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
						
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return -1;

	}

	public int deleteAll(String loginId) {
		PreparedStatement pstmt = null;

		try {
			String sql = "DELETE FROM CART WHERE user_id = ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성
			pstmt.setString(1, loginId);

			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
			
			return result;

		} catch (Exception e) {
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			}
		}

		return -1;

	}
	
}
