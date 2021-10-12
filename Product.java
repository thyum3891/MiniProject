package shop.model.vo;

public class Product {

	private int proNo;
	private String proName;
	private String proDesc;
	private int proPrice;
	private int proStock;
	private int hit;
	private String proState;
	private String proCategory;

	public Product() {
	}

	public Product(int proNo, String proName, String proDesc, int proPrice, int proStock, int hit, String proState,
			String proCategory) {
		this.proNo = proNo;
		this.proName = proName;
		this.proDesc = proDesc;
		this.proPrice = proPrice;
		this.proStock = proStock;
		this.hit = hit;
		this.proState = proState;
		this.proCategory = proCategory;
	}

	public int getProNo() {
		return proNo;
	}

	public void setProNo(int proNo) {
		this.proNo = proNo;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public int getProPrice() {
		return proPrice;
	}

	public void setProPrice(int proPrice) {
		this.proPrice = proPrice;
	}

	public int getProStock() {
		return proStock;
	}

	public void setProStock(int proStock) {
		this.proStock = proStock;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getProState() {
		return proState;
	}

	public void setProState(String proState) {
		this.proState = proState;
	}

	public String getProCategory() {
		return proCategory;
	}

	public void setProCategory(String proCategory) {
		this.proCategory = proCategory;
	}

	@Override
	public String toString() {
		return "Product [proNo=" + proNo + ", proName=" + proName + ", proDesc=" + proDesc + ", proPrice=" + proPrice
				+ ", proStock=" + proStock + ", hit=" + hit + ", proState=" + proState + ", proCategory=" + proCategory
				+ "]";
	}

}
