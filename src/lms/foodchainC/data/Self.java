package lms.foodchainC.data;

import java.util.List;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 当前用户数据类
 * 
 */
public class Self extends CustomerData {

	private static Self current;
	// 密码
	public String passWord;
	// 纬度
	public float latitude = 0;
	// 经度
	public float longitude = 0;
	// 精确度
	public float accuracy = 0;
	// 登录
	public boolean login = false;
	// 营业额
	public int billCount = 0;
	// 订单列表
	private List<BillData> bill;
	// 烹饪顺序
	private List<CaseData> order;
	/** 正在选择餐厅 */
	public boolean isSelecting = true;

	public static Self current() {
		if (current == null) {
			current = new Self();
		}
		return current;
	}

	public void setBill(List<BillData> bill) {
		this.bill = bill;
	}

	public List<BillData> getBill() {
		return bill;
	}

	public void setOrder(List<CaseData> order) {
		this.order = order;
	}

	public List<CaseData> getOrder() {
		return order;
	}

}
