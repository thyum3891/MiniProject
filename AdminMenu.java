package shop.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shop.admin.controller.AdminController;
import shop.model.vo.Product;

public class AdminMenu {

//			1. 상품조회 => 조회할 카테고리 선택 => 카테고리 별 상품 조회 (상품 수정기능 추가할지 말지)

	static Scanner sc = new Scanner(System.in);
	AdminController adminController = new AdminController();
	String loginId = null;

	
	/* =====================================================================================
	 *										메뉴 진입
	 * ===================================================================================== 
	 */	
	public void menu(String loginId) {
		this.loginId = loginId;
		while (true) {

			StringBuffer sb = new StringBuffer();
			sb.append("==============================================================\n");
			sb.append(this.loginId + "관리자님 환영합니다.\n");
			sb.append("1. 상품조회\n");
			sb.append("2. 상품등록\n");
			sb.append("3. 주문현황\n");
			sb.append("4. 반품현황\n");
			sb.append("9. 프로그램 종료\n");
			sb.append("==============================================================");
			System.out.println(sb);
			
			int selectNum;
			
			while (true) {
				try {
					
					System.out.print("선택 메뉴 : ");
					selectNum = Integer.parseInt(sc.nextLine());
					break;
					
				} catch (Exception e) {
					continue;
				}
			}

			switch (selectNum) {
			case 1:
				productInquiry();
				continue;

			case 2:
				addPro();
				continue;

			case 3:

				continue;

			case 9:
				System.out.println("프로그램을 종료합니다.");
				return;
			default:
				System.out.println("잘못입력하셨습니다. 다시 입력 해주세요");
				continue;
			}
		}

	}

/* =====================================================================================
 *										상품 조회
 * ===================================================================================== 
 */
	public void productInquiry() { // 상품명, 상품 가격, 재고, 판매여부

		int page = 1; // 페이지
		int pageView = 5; // 한 페이지에 보일 갯수
		int pageStart = (pageView*(page-1));
		
		while (true) {
			List<Product> list = new ArrayList<Product>(adminController.productInquiry(page, pageView));
			
			
			

			System.out.println("==============================================================");
			System.out.println("	 		    상품 조회");
			System.out.println("==============================================================");
			for (int j = 0; j < list.size(); j++) {
				if (j + pageStart >= list.size()) {
					break;
				}
				StringBuffer sb = new StringBuffer();
				sb.append((j + pageStart+1) + "번 상품 [");
				sb.append("상품명 : " + list.get(j + pageStart).getProName() + " ");
				sb.append("가격 : " + list.get(j + pageStart).getProPrice() + "원 ");
				sb.append("재고 : " + list.get(j + pageStart).getProStock() + "EA ");

				if (list.get(j + pageStart).getProState().equals("1")) {
					sb.append("판매여부 : 판매중 ]");
				} else if (list.get(j + pageStart).getProStock() == 0) {
					sb.append("판매여부 : 품절 ]");
				} else {
					sb.append("판매여부 : 판매중지 ]");
				}

				System.out.println(sb);

			}
			System.out.println("==============================================================");

			StringBuffer sb = new StringBuffer();

			sb.append("현재 페이지 : " + (page) + "\n");
			if (page + pageView < list.size()) {
				sb.append("1. 다음페이지\n");
			}
			if (page != 1) {
				sb.append("2. 이전페이지\n");
			}
			sb.append("3. 상품 선택\n");
			sb.append("4. 카테고리로 조회\n");
			sb.append("9. 메뉴로 돌아가기");
			System.out.println(sb);

			int selectNum = 0;
			while (true) {
				try {
					System.out.print("선택 메뉴 : ");
					selectNum = Integer.parseInt(sc.nextLine());

				} catch (Exception e) {
					continue;
				}

				switch (selectNum) {

				case 1:
					if (page + pageView < list.size()) {
						page++;
					}
					break;

				case 2:
					if (page != 0) {
						page--;

					}
					break;

				case 3:
					int selectNo = 0;
					while (true) {
						try {
							System.out.print("선택 할 상품번호 : ");
							selectNo = Integer.parseInt(sc.nextLine());
							if (selectNo < 0 || selectNo > list.size()) {
								continue;
							}
							break;
						} catch (Exception e) {
							continue;
						}

					}

					int selectProNo = list.get(selectNo - 1).getProNo();
					selectProduct(selectProNo);
					break;

				case 4:
					selectCategory();
					break;

				case 9:
					return;
				default:
					System.out.println("다시 입력해주세요.");
					continue;

				}
				break;
			}

		}

	}

	/* =====================================================================================
	 *										카테고리 조회
	 * ===================================================================================== 
	 */	
	
	public void selectCategory() {
		
		int page = 1; // 페이지
		int pageView = 5; // 한 페이지에 보일 갯수
		int pageStart = (pageView*(page-1));
		
		while (true) {
			List<String> list = adminController.selectCategory(page, pageView);

			if(list==null) {
				System.out.println("조회된 카테고리가 없습니다.");
				return;
			}

			System.out.println("==============================================================");
			System.out.println("	 		    카테고리 조회");
			System.out.println("==============================================================");
			for (int j = 0; j < list.size(); j++) {
				if (j + pageStart >= list.size()) {
					break;
				}
				StringBuffer sb = new StringBuffer();
				sb.append((j + pageStart + 1) + "번 카테고리 [");
				sb.append(list.get(j + pageStart) + "]");

				System.out.println(sb);

			}
			System.out.println("==============================================================");

			StringBuffer sb = new StringBuffer();

			sb.append("현재 페이지 : " + (page) + "\n");
			if (pageStart + pageView < list.size()) {
				sb.append("1. 다음페이지\n");
			}
			if (page != 1) {
				sb.append("2. 이전페이지\n");
			}
			sb.append("3. 조회 할 카테고리 선택\n");
			sb.append("4. 카테고리 삭제(품목 포함)\n");
			sb.append("9. 메뉴로 돌아가기");
			System.out.println(sb);

			int selectNum = 0;
			while (true) {
				try {
					System.out.print("선택 메뉴 : ");
					selectNum = Integer.parseInt(sc.nextLine());

				} catch (Exception e) {
					continue;
				}

				switch (selectNum) {

				case 1:
					if (page + pageView < list.size()) {
						page += pageView;
					}
					break;

				case 2:
					if (page != 0) {
						page -= pageView;

					}
					break;

				case 3:
					int selectNo = 0;
					while (true) {
						try {
							System.out.print("선택 할 카테고리번호 : ");
							selectNo = Integer.parseInt(sc.nextLine());
							if (selectNo < 0 || selectNo > list.size()) {
								continue;
							}
							break;
						} catch (Exception e) {
							continue;
						}

					}

					String selectCategoryName = list.get(selectNo - 1);
					selectCategoryPro(selectCategoryName);

					break;

				case 4:
					deleteCategory();
					break;

				case 9:
					return;
				default:
					System.out.println("다시 입력해주세요.");
					continue;

				}
				break;
			}

		}

	}
	
	/* =====================================================================================
	 *									카테고리 상품 조회
	 * ===================================================================================== 
	 */

	public void selectCategoryPro(String selectCategoryName) {
		int page = 1; // 페이지
		int pageView = 5; // 한 페이지에 보일 갯수
		int pageStart = (pageView*(page-1));
		
		while (true) {
			List<Product> list = new ArrayList<Product>(adminController.selectCategoryPro(selectCategoryName, page, pageView));

	
			System.out.println("==============================================================");
			System.out.println("	 		    "+selectCategoryName);
			System.out.println("==============================================================");

			for (int j = 0; j < 5; j++) {
				if (j + pageStart >= list.size()) {
					break;
				}
				StringBuffer sb = new StringBuffer();
				sb.append((j + pageStart + 1) + "번 상품 [");
				sb.append("상품명 : " + list.get(j + pageStart).getProName() + " ");
				sb.append("가격 : " + list.get(j + pageStart).getProPrice() + "원 ");
				sb.append("재고 : " + list.get(j + pageStart).getProStock() + "EA ");

				if (list.get(j + pageStart).getProState().equals("1")) {
					sb.append("판매여부 : 판매중 ]");
				} else if (list.get(j + pageStart).getProStock() == 0) {
					sb.append("판매여부 : 품절 ]");
				} else {
					sb.append("판매여부 : 판매중지 ]");
				}

				System.out.println(sb);

			}
			System.out.println("==============================================================");

			StringBuffer sb = new StringBuffer();

			sb.append("현재 페이지 : " + (page) + "\n");
			if (page + pageView < list.size()) {
				sb.append("1. 다음페이지\n");
			}
			if (page != 0) {
				sb.append("2. 이전페이지\n");
			}
			sb.append("3. 상품 선택\n");

			sb.append("9. 메뉴로 돌아가기");
			System.out.println(sb);

			int selectNum = 0;
			while (true) {
				try {
					System.out.print("선택 메뉴 : ");
					selectNum = Integer.parseInt(sc.nextLine());

				} catch (Exception e) {
					continue;
				}

				switch (selectNum) {

				case 1:
					if (page + pageView < list.size()) {
						page += pageView;
					}
					break;

				case 2:
					if (page != 0) {
						page -= pageView;

					}
					break;

				case 3:
					int selectNo = 0;
					while (true) {
						try {
							System.out.print("선택 할 상품번호 : ");
							selectNo = Integer.parseInt(sc.nextLine());
							if (0 > selectNo || selectNo > list.size()) {
								continue;
							}
							break;
						} catch (Exception e) {
							continue;
						}
					}

					int selectProNo = list.get(selectNo - 1).getProNo();
					selectProduct(selectProNo);
					break;

				case 9:
					return;
				default:
					System.out.println("다시 입력해주세요.");
					continue;

				}
				break;
			}
		}
	}
	
	/* =====================================================================================
	 *										상품 상세 조회
	 * ===================================================================================== 
	 */

	public void selectProduct(int selectProNo) {
		while (true) {

			Product selectProduct = adminController.selectProduct(selectProNo);

			StringBuffer sb = new StringBuffer();
			sb.append("==============================================================\n");
			sb.append("	 		    ["+selectProduct.getProName()+"] 조회\n");
			sb.append("==============================================================\n");
			sb.append("상품번호 : " + selectProduct.getProNo() + "\n");
			sb.append("상품명 : " + selectProduct.getProName() + "\n");
			sb.append("상세설명 : " + selectProduct.getProDesc() + "\n");
			sb.append("가격 : " + selectProduct.getProPrice() + "원\n");
			sb.append("재고 : " + selectProduct.getProStock() + "EA\n");
			sb.append("조회수 : " + selectProduct.getHit() + "회\n");

			if (selectProduct.getProState().equals("1")) {
				sb.append("판매여부 : 판매중");
			} else if (selectProduct.getProStock() == 0) {
				sb.append("판매여부 : 품절");
			} else {
				sb.append("판매여부 : 판매중지");
			}

			sb.append("카테고리 : " + selectProduct.getProCategory() + "\n");
			sb.append("==============================================================");
			System.out.println(sb);

			StringBuffer menu = new StringBuffer();

			menu.append("1. 재고수정\n");
			menu.append("2. 상품수정\n");
			menu.append("3. 상품삭제\n");
			menu.append("4. 판매여부 변경\n");
			menu.append("9. 메인메뉴로 돌아가기\n");

			System.out.println(menu);

			int selectNum = 0;

			while (true) {

				try {
					System.out.print("선택 메뉴 : ");
					selectNum = Integer.parseInt(sc.nextLine());

				} catch (Exception e) {
					continue;
				}

				switch (selectNum) {
				case 1:
					updateStock(selectProduct.getProNo());
					break;

				case 2:
					updateProOption(selectProduct.getProNo());
					break;

				case 3:
					deletePro(selectProduct.getProNo());

					return;

				case 9:
					return;

				default:
					continue;
				}

				break;
			}

		}
	}

/* =====================================================================================
 *										재고 수정
 * ===================================================================================== 
 */
	
	public void updateStock(int selectProNo) {

		Product product = adminController.selectProduct(selectProNo);

		int stock = 0;
		
		while (true) {
			try {

				System.out.print("수정할 재고 입력 : ");
				stock = Integer.parseInt(sc.nextLine());

				if (stock >= 0) {
					break;
				} else {
					continue;
				}
			} catch (Exception e) {
				continue;
			}

		}

		product.setProStock(stock);

		int result = adminController.updateProduct(product, product.getProNo());

		if (result > 0) {
			System.out.println("재고 수정 성공");
		} else {
			System.out.println("재고 수정 실패");
		}
	}
	/* =====================================================================================
	 *										상품 옵션 수정
	 * ===================================================================================== 
	 */
	public void updateProOption(int selectProNo) {

		Product product = adminController.selectProduct(selectProNo);

		StringBuffer sb = new StringBuffer();
		sb.append("==============================================================\n");
		sb.append("	 		    변경할 상품 옵션\n");
		sb.append("==============================================================\n");
		sb.append("1. 상품명\n");
		sb.append("2. 상세설명\n");
		sb.append("3. 가격\n");
		sb.append("4. 재고\n");
		sb.append("5. 판매상태\n");
		sb.append("6. 카테고리" + "\n");
		sb.append("9. 취소" + "\n");
		sb.append("==============================================================");
		System.out.println(sb);

		while (true) {
			int selectNum = 0;
			try {
				System.out.print("선택 메뉴 : ");
				selectNum = Integer.parseInt(sc.nextLine());

			} catch (Exception e) {
				continue;
			}

			switch (selectNum) {
			case 1:
				System.out.print("변경 할 상품명 : ");
				String proName = sc.nextLine();

				product.setProName(proName);

				break;

			case 2:
				System.out.print("변경 할 상세설명 : ");
				String proDesc = sc.nextLine();

				product.setProDesc(proDesc);
				break;

			case 3:
				System.out.print("변경 할 가격 : ");
				int proPrice = Integer.parseInt(sc.nextLine());
				product.setProPrice(proPrice);
				break;

			case 4:
				System.out.print("변경 할 재고 : ");
				int proStock = Integer.parseInt(sc.nextLine());
				product.setProStock(proStock);
				break;

			case 5:
				System.out.print("변경 할 판매상태(판매중 : 1/ 판매중지 : 0) : ");

				String proState = sc.nextLine();
				if (proState.equals("1") || proState.equals("0")) {
					product.setProState(proState);
					break;
				} else {
					System.out.println("잘못 입력하셨습니다.");
					continue;
				}

			case 6:
				System.out.print("변경 할 카테고리 : ");
				String proCategory = sc.nextLine();
				product.setProCategory(proCategory);

				break;

			case 9:
				return;

			default:
				System.out.println("없는 메뉴입니다. \n다시 입력해주세요.");
				continue;

			}

			break;
		}

		int result = adminController.updateProduct(product, selectProNo);
		if (result > 0) {
			System.out.println("재고 수정 성공");
		} else {
			System.out.println("재고 수정 실패");
		}

	}
	/* =====================================================================================
	 *										상품 삭제
	 * ===================================================================================== 
	 */
	public void deletePro(int selectProNo) {
		adminController.deleteProduct(selectProNo);
	}

	/* =====================================================================================
	 *										상품 등록
	 * ===================================================================================== 
	 */

	public void addPro() {

		System.out.print("상품명 : ");
		String proName = sc.nextLine();

		System.out.print("상품상세 : ");
		String proDesc = sc.nextLine();

		System.out.print("상품 가격 : ");
		int proPrice = Integer.parseInt(sc.nextLine());

		System.out.print("상품 재고 : ");
		int proStock = Integer.parseInt(sc.nextLine());

		System.out.print("판매상태 : ");
		String proState = sc.nextLine();

		System.out.print("카테고리 : ");
		String proCategory = sc.nextLine();

		adminController.addPro(proName, proDesc, proPrice, proStock, proState, proCategory);

	}

	/* =====================================================================================
	 *										카테고리 삭제(품목 포함)
	 * ===================================================================================== 
	 */

	public void deleteCategory() {

		
		int selectNum = 0;
		List<String> list = new ArrayList<String>(adminController.selectCategory());
		System.out.println("==============================================================");
		System.out.println("	 		    카테고리 목록");
		System.out.println("==============================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ". " + list.get(i));
		}

		System.out.println("==============================================================");

		while (true) {
			try {
				System.out.print("삭제할 카테고리 번호(취소 : 0 입력) : ");
				selectNum = Integer.parseInt(sc.nextLine());
				if (0>selectNum||selectNum > list.size()) {
					System.out.println("없는 카테고리 번호입니다. 다시 입력해주세요.");
					continue;
				}else if(selectNum==0){
					System.out.println("취소되었습니다.");
					return;
				}
				break;
			} catch (Exception e) {
				continue;
			}
		}

		adminController.deleteCategory(list.get(selectNum - 1));
	}

//			5. 주문현황 => 주문완료된 회원의 데이터 받아서 조회 => 배송현황(배송준비,배송중,배송완료,주문취소(재고없음/기타))

//			6. 반품현황 => 반품처리 or 반려

}
