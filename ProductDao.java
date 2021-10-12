package shop.model.dao;

import static common.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.model.vo.Product;

public class ProductDao {

	Connection conn = null;

	public ProductDao() {

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

	public List<String> category() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();

		try {
			String sql = "SELECT pro_category FROM PRODUCT GROUP BY pro_category" + "";
			pstmt = conn.prepareStatement(sql); // 객체 생성
			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {
				String category = rs.getString(1);
				list.add(category);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	public List<Product> selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();

		try {
			String sql = "SELECT * FROM PRODUCT";
			pstmt = conn.prepareStatement(sql); // 객체 생성
			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {
				int proNo = rs.getInt(1);
				String proName = rs.getString(2);
				String proDesc = rs.getString(3);
				int proPrice = rs.getInt(4);
				int proStock = rs.getInt(5);
				int proHit = rs.getInt(6);
				String proState = rs.getString(7);
				String proCategory = rs.getString(8);

				Product product = new Product(proNo, proName, proDesc, proPrice, proStock, proHit, proState,
						proCategory);
				list.add(product);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	public List<Product> selectAllPage(int inputPage, int inputPageSize) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<Product>();

		try {
			String sql = "SELECT * FROM " + "(SELECT ROWNUM RN, A.* FROM "
					+ "(SELECT * FROM PRODUCT  ORDER BY PRO_PRICE DESC ) A ) " + "where RN BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			int start = (inputPage - 1) * inputPageSize + 1; // 1
			int end = inputPage * inputPageSize; // 5

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {
				int proNo = rs.getInt(2);
				String proName = rs.getString(3);
				String proDesc = rs.getString(4);
				int proPrice = rs.getInt(5);
				int proStock = rs.getInt(6);
				int proHit = rs.getInt(7);
				String proState = rs.getString(8);
				String proCategory = rs.getString(9);

				Product product = new Product(proNo, proName, proDesc, proPrice, proStock, proHit, proState,
						proCategory);
				list.add(product);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	public List<Product> selectCategoryPro(String selectCategory) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();

		try {
			String sql = "SELECT * FROM PRODUCT WHERE pro_category = ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성
			pstmt.setString(1, selectCategory);
			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {
				int proNo = rs.getInt(1);
				String proName = rs.getString(2);
				String proDesc = rs.getString(3);
				int proPrice = rs.getInt(4);
				int proStock = rs.getInt(5);
				int proHit = rs.getInt(6);
				String proState = rs.getString(7);
				String proCategory = rs.getString(8);

				Product product = new Product(proNo, proName, proDesc, proPrice, proStock, proHit, proState,
						proCategory);

				list.add(product);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return null;

	}

	public List<Product> selectCategoryProPage(String selectCategory, int inputPage, int inputPageSize) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();

		try {
			String sql = "SELECT * FROM" + "(SELECT ROWNUM RN, A.* FROM "
					+ "(SELECT * FROM PRODUCT WHERE PRO_CATEGORY = ? ORDER BY PRO_PRICE DESC ) A )"
					+ "RN BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			int start = (inputPage - 1) * inputPageSize + 1;
			int end = inputPage * inputPageSize;

			pstmt.setString(1, selectCategory);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {
				int proNo = rs.getInt(2);
				String proName = rs.getString(3);
				String proDesc = rs.getString(4);
				int proPrice = rs.getInt(5);
				int proStock = rs.getInt(6);
				int proHit = rs.getInt(7);
				String proState = rs.getString(8);
				String proCategory = rs.getString(9);

				Product product = new Product(proNo, proName, proDesc, proPrice, proStock, proHit, proState,
						proCategory);

				list.add(product);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return null;

	}

	public List<String> selectCategory() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();

		try {
			String sql = "SELECT PRO_CATEGORY FROM PRODUCT GROUP BY PRO_CATEGORY";
			pstmt = conn.prepareStatement(sql); // 객체 생성
			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {

				String categoryName = rs.getString(1);

				list.add(categoryName);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<String> selectCategoryPage(int inputPage, int inputPageSize) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();

		try {
			String sql = "SELECT * FROM"
					+ "(SELECT ROWNUM RN, A.* FROM (SELECT PRO_CATEGORY FROM PRODUCT GROUP BY PRO_CATEGORY ) A )"
					+ "WHERE RN BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			int start = (inputPage - 1) * inputPageSize + 1;
			int end = inputPage * inputPageSize;

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();// 쿼리 실행문

			while (rs.next()) {

				String categoryName = rs.getString(2);

				list.add(categoryName);

			}
			return list;

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}
		return null;
	}

	public Product selectOne(int selectProNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM PRODUCT WHERE pro_no = ? ";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			pstmt.setInt(1, selectProNo);

			rs = pstmt.executeQuery();// 쿼리 실행문

			if (rs.next()) {
				int proNo = rs.getInt(1);
				String proName = rs.getString(2);
				String proDesc = rs.getString(3);
				int proPrice = rs.getInt(4);
				int proStock = rs.getInt(5);
				int proHit = rs.getInt(6);
				String proState = rs.getString(7);
				String proCategory = rs.getString(8);

				Product product = new Product(proNo, proName, proDesc, proPrice, proStock, proHit, proState,
						proCategory);
				return product;
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}

		return null;
	}

	public int updateProduct(Product product, int pro_no) {
		PreparedStatement pstmt = null;

		try {

			String sql = "UPDATE PRODUCT SET PRO_NAME = ?, PRO_DESC = ?,PRO_PRICE = ?,PRO_STOCK = ?, PRO_STATE = ?, PRO_CATEGORY = ? WHERE pro_no = ? ";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			pstmt.setString(1, product.getProName());
			pstmt.setString(2, product.getProDesc());
			pstmt.setInt(3, product.getProPrice());
			pstmt.setInt(4, product.getProStock());
			pstmt.setString(5, product.getProState());
			pstmt.setString(6, product.getProCategory());
			pstmt.setInt(7, pro_no);

			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}

		return -1;

	}

	public int insertProduct(String proName, String proDesc, int proPrice, int proStock, String proState,
			String proCategory) {

		PreparedStatement pstmt = null;

		try {

			String sql = "INSERT INTO PRODUCT VALUES(SEQ_PRODUCT_NO.NEXTVAL,?,?, ?,?,0,?,?) ";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			pstmt.setString(1, proName);
			pstmt.setString(2, proDesc);
			pstmt.setInt(3, proPrice);
			pstmt.setInt(4, proStock);
			pstmt.setString(5, proState);
			pstmt.setString(6, proCategory);

			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}

		return -1;

	}

	public int deleteProduct(int proNo) {
		PreparedStatement pstmt = null;

		try {

			String sql = "DELETE FROM product WHERE pro_no = ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			pstmt.setInt(1, proNo);

			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}

		return -1;

	}

	public int deleteAll() {
		PreparedStatement pstmt = null;

		try {

			String sql = "DELETE FROM PRODUCT";
			pstmt = conn.prepareStatement(sql); // 객체 생성

			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}

		return -1;

	}

	public int deleteCategory(String category) {
		PreparedStatement pstmt = null;

		try {

			String sql = "DELETE FROM PRODUCT WHERE pro_category = ?";
			pstmt = conn.prepareStatement(sql); // 객체 생성
			pstmt.setString(1, category);
			int result = pstmt.executeUpdate();// 쿼리 실행문

			if (result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				//
				e.printStackTrace();
			}
		}

		return -1;

	}

}
