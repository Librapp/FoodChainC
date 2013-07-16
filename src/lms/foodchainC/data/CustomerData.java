package lms.foodchainC.data;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 顾客数据类
 * 
 */
import java.util.List;

import org.cybergarage.upnp.Device;

public class CustomerData extends UserData {

	private static CustomerData current;
	// 座位Id
	public String seatId;
	// 桌Id
	public String tableId;
	// 状态0未点餐1已点餐未上菜2所有已点已上3买单4已买单
	// 是否在店
	public boolean isIn = false;
	// 是否好友
	public boolean isFriend = false;
	// 账单
	public BillData bill;
	// 喜欢的菜
	private List<CaseData> favoriteCase;
	// 喜欢的餐馆
	private List<RestaurantData> favoriteRes;
	// 黑名单
	private List<RestaurantData> blackList;

	public CustomerData(String name, int id, int gender, int credit,
			String address, String tel, int status, String seatId,
			String tableId) {
		this.name = name;
		this.id = id;
		this.gender = gender;
		this.credit = credit;
		this.address = address;
		this.tel = tel;
		this.state = status;
		this.seatId = seatId;
		this.tableId = tableId;
	}

	public CustomerData(Device d) {
		this.name = d.getFriendlyName();
	}

	public CustomerData() {

	}

	public static CustomerData current() {
		if (current == null) {
			current = new CustomerData();
		}
		return current;
	}

	public void setFavoriteCase(List<CaseData> favoriteCase) {
		this.favoriteCase = favoriteCase;
	}

	public List<CaseData> getFavoriteCase() {
		return favoriteCase;
	}

	public void setFavoriteRes(List<RestaurantData> favoriteRes) {
		this.favoriteRes = favoriteRes;
	}

	public List<RestaurantData> getFavoriteRes() {
		return favoriteRes;
	}

	public void setBlackList(List<RestaurantData> blackList) {
		this.blackList = blackList;
	}

	public List<RestaurantData> getBlackList() {
		return blackList;
	}

}
