package shop.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shop.user.controller.UserController;
import shop.model.vo.Cart;
import shop.model.vo.Product;

public class UserMenu {

	UserController userController = new UserController();
	static Scanner sc = new Scanner(System.in);
	String loginId = null;

	/*
	 * =============================================================================
	 * ======== 메뉴 진입
	 * =============================================================================
	 * ========
	 */

	public void Menu(String loginId) {
		this.loginId = loginId;
		while (true) {

			StringBuffer sb = new StringBuffer();
			sb.append("==============================================================\n");
			sb.append(loginId + " 회원님 환영합니다.\n");
			sb.append("1. 상품조회\n");
			sb.append("2. 장바구니\n");
			sb.append("3. 주문(결제)\n");
			sb.append("4. 주문조회\n");
			sb.append("5. 별점 및 후기\n");
			sb.append("9. 프로그램 종료\n");
			sb.append("==============================================================");
			System.out.println(sb);

			System.out.print("메뉴 선택 : ");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				// 상품조회
				productInquiry();
				continue;
			case 2:
				// 장바구니 조회
				selectCart();
				continue;
			case 3:
				// 주문(결제)

			case 4:
				// 주문조회

			case 5:
				// 별점 및 후기

			case 9:
				// 프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				return;
			default:
				System.out.println("잘못입력하셨습니다. 다시 입력 해주세요");
				continue;
			}
		}
	}

	/*
	 * =============================================================================
	 * ======== 상품 조회
	 * =============================================================================
	 * ========
	 */
	public void productInquiry() { // 상품명, 상품 가격, 재고, 판매여부

		int page = 0; // 페이지
		int pageView = 5; // 한 페이지에 보일 갯수

		while (true) {
			List<Product> list = new ArrayList<Product>(userController.productInquiry());

			System.out.println("==============================================================");
			System.out.println("	 		    상품 조회");
			System.out.println("==============================================================");
			for (int j = 0; j < 5; j++) {
				if (j + page >= list.size()) {
					break;
				}
				StringBuffer sb = new StringBuffer();
				sb.append((j + page + 1) + "번 상품 [");
				sb.append("상품명 : " + list.get(j + page).getProName() + " ");
				sb.append("가격 : " + list.get(j + page).getProPrice() + "원 ");
				sb.append("재고 : " + list.get(j + page).getProStock() + "EA ");

				if (list.get(j + page).getProState().equals("Y")) {
					sb.append("판매여부 : 판매중 ]");
				} else if (list.get(j + page).getProStock() == 0) {
					sb.append("판매여부 : 품절 ]");
				} else {
					sb.append("판매여부 : 판매중지 ]");
				}

				System.out.println(sb);

			}
			System.out.println("==============================================================");

			StringBuffer sb = new StringBuffer();

			sb.append("현재 페이지 : " + (page / pageView + 1) + "\n");
			if (page + pageView < list.size()) {
				sb.append("1. 다음페이지\n");
			}
			if (page != 0) {
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
						page += pageView;
						System.out.println("페이지 더해짐");
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

	/*
	 * =============================================================================
	 * ======== 카테고리 조회
	 * =============================================================================
	 * ========
	 */

	public void selectCategory() {

		int page = 0; // 페이지
		int pageView = 5; // 한 페이지에 보일 갯수

		while (true) {
			List<String> list = new ArrayList<String>(userController.selectCategory());

			System.out.println("==============================================================");
			System.out.println("	 		    카테고리 조회");
			System.out.println("==============================================================");
			for (int j = 0; j < 5; j++) {
				if (j + page >= list.size()) {
					break;
				}
				StringBuffer sb = new StringBuffer();
				sb.append((j + page + 1) + "번 카테고리 [");
				sb.append(list.get(j + page) + "]");

				System.out.println(sb);

			}
			System.out.println("==============================================================");

			StringBuffer sb = new StringBuffer();

			sb.append("현재 페이지 : " + (page / 5 + 1) + "\n");
			if (page + pageView < list.size()) {
				sb.append("1. 다음페이지\n");
			}
			if (page != 0) {
				sb.append("2. 이전페이지\n");
			}
			sb.append("3. 조회 할 카테고리 선택\n");
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

	/*
	 * =============================================================================
	 * ======== 카테고리 상품 조회
	 * =============================================================================
	 * ========
	 */

	public void selectCategoryPro(String selectCategoryName) {

		int page = 0; // 페이지
		int pageView = 5; // 한 페이지에 보일 갯수

		while (true) {
			List<Product> list = new ArrayList<Product>(userController.selectCategoryPro(selectCategoryName));

			System.out.println("==============================================================");
			System.out.println("	 		    " + selectCategoryName);
			System.out.println("==============================================================");

			for (int j = 0; j < 5; j++) {
				if (j + page >= list.size()) {
					break;
				}
				StringBuffer sb = new StringBuffer();
				sb.append((j + page + 1) + "번 상품 [");
				sb.append("상품명 : " + list.get(j + page).getProName() + " ");
				sb.append("가격 : " + list.get(j + page).getProPrice() + "원 ");
				sb.append("재고 : " + list.get(j + page).getProStock() + "EA ");

				if (list.get(j + page).getProState().equals("1")) {
					sb.append("판매여부 : 판매중 ]");
				} else if (list.get(j + page).getProStock() == 0) {
					sb.append("판매여부 : 품절 ]");
				} else {
					sb.append("판매여부 : 판매중지 ]");
				}

				System.out.println(sb);

			}
			System.out.println("==============================================================");

			StringBuffer sb = new StringBuffer();

			sb.append("현재 페이지 : " + (page / 5 + 1) + "\n");
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

	/*
	 * =============================================================================
	 * ======== 상품 상세 조회
	 * =============================================================================
	 * ========
	 */

	public void selectProduct(int selectProNo) {
		while (true) {

			Product selectProduct = userController.selectProduct(selectProNo);

			StringBuffer sb = new StringBuffer();
			sb.append("==============================================================\n");
			sb.append("	 		    [" + selectProduct.getProName() + "] 조회\n");
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

			menu.append("1. 장바구니 담기\n");
			menu.append("2. 상품 구매\n");

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
					int addCartNum = 0;
					while (true) {

						try {
							System.out.print("갯수 입력 : ");
							addCartNum = Integer.parseInt(sc.nextLine());
							if (addCartNum > 0) {
								break;
							}
							continue;

						} catch (Exception e) {
							continue;
						}
					}
					addCart(selectProNo, addCartNum);
					break;
				case 2:
					int buyNum = 0;
					while (true) {

						try {
							System.out.print("갯수 입력 : ");
							buyNum = Integer.parseInt(sc.nextLine());
							if (buyNum > 0) {
								break;
							}
							continue;

						} catch (Exception e) {
							continue;
						}
					}
					addCart(selectProNo, buyNum);
					while (true) {
						System.out.println("장바구니를 확인해보시겠습니까?(Y/N)");
						String selectCart = sc.nextLine();

						if (selectCart.toUpperCase().equals("Y")) {
							System.out.println("장바구니로 이동합니다.");
							selectCart();
						} else if (selectCart.toUpperCase().equals("N")) {
							break;
						} else {
							System.out.println("다시 입력해주세요");
							continue;
						}
					}
					break;

				case 9:
					return;

				default:
					continue;
				}

			}

		}
	}

	/*
	 * =============================================================================
	 * ======== 장바구니
	 * =============================================================================
	 * ========
	 */

	public void selectCart() {

		while (true) {
			List<Cart> list = userController.selectCart(loginId);
			System.out.println("==============================================================");
			System.out.println("	 		    장바구니 조회");
			System.out.println("==============================================================");
			if (list == null || list.isEmpty()) {
				System.out.println("	 		    장바구니가 비어있습니다.");
				break;
			}
			for (int i = 0; i < list.size(); i++) {

				StringBuffer sb = new StringBuffer();

				Product product = userController.selectProduct(list.get(i).getProNo());
				sb.append((i + 1) + "번 ");
				sb.append("상품명 : " + product.getProName());
				sb.append(" 총 계 : " + list.get(i).getCartCnt() + "EA ");
				sb.append("총 액 : " + (list.get(i).getCartCnt() * product.getProPrice()) + "원");

				System.out.println(sb);
			}
			System.out.println("==============================================================");
			StringBuffer sbMenu = new StringBuffer();
			sbMenu.append("1. 전체 구매\n");
			sbMenu.append("2. 장바구니 상품 삭제\n");
			sbMenu.append("3. 장바구니 상품 전체 삭제\n");
			sbMenu.append("0. 돌아가기");
			System.out.println(sbMenu);
			int selectNo = 0;
			while (true) {
				try {
					System.out.print("번호 선택 : ");
					selectNo = Integer.parseInt(sc.nextLine());
					if (selectNo < 0) {
						System.out.println("다시 입력해주세요.");
						continue;
					}
					break;
				} catch (Exception e) {
					System.out.println("다시 입력해주세요.");
					continue;
				}

			}

			switch (selectNo) {

			case 1:
				// 전체 주문 메소드
				break;

			case 2:
				int selectCartNo = 0;
				while (true) {
					try {
						System.out.print("삭제할 장바구니 번호 선택 : ");
						selectCartNo = Integer.parseInt(sc.nextLine());
						if (selectCartNo < 0 || selectCartNo > list.size()) {
							System.out.println("다시 입력해주세요.");
							continue;
						}
						break;
					} catch (Exception e) {
						System.out.println("다시 입력해주세요.");
						continue;
					}
				}
				deleteCartOne(list.get((selectCartNo - 1)).getProNo());
				break;

			case 3:
				deleteCartAll();
				break;
			default:

			case 0:
				return;
			}

		}
	}

	public void addCart(int proNo, int buyCnt) {

		int result = 0;

		if (userController.selectOne(loginId, proNo) == null) {
			System.out.println("add");
			result = userController.addCart(proNo, loginId, buyCnt);

		} else if (userController.selectOne(loginId, proNo) != null) {
			System.out.println("upp");
			result = userController.updateCart(proNo, loginId, buyCnt);

		} else {

			result = -2;

		}

		String str = result > 0 ? "장바구니 추가 완료" : "장바구니 추가 실패";

		System.out.println(str);

	}

	public void deleteCartAll() {
		userController.deleteCartAll(loginId);
	}

	public void deleteCartOne(int proNo) {
		userController.deleteCartOne(loginId, proNo);
	}

}
