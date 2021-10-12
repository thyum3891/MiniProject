package shop.model.vo;

public class Cart {

	private int proNo;
	private String memberId;
	private int cartCnt;
	
	
	
	
	public Cart() {
	}
	
	public Cart(int proNo, String memberId, int cartCnt) {
		this.proNo = proNo;
		this.memberId = memberId;
		this.cartCnt = cartCnt;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public String getProMemberId() {
		return memberId;
	}

	public void setProId(String memberId) {
		this.memberId = memberId;
	}

	public int getCartCnt() {
		return cartCnt;
	}

	public void setCartCnt(int cartCnt) {
		this.cartCnt = cartCnt;
	}

	@Override
	public String toString() {
		return "Cart [proNo=" + proNo + ", memberId=" + memberId + ", cartCnt=" + cartCnt + "]";
	}
	
	
	
}
