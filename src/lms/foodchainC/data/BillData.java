package lms.foodchainC.data;

import java.util.List;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 账单数据类
 * 
 */
public class BillData {

	public int id = 0;
	public String customerId = "";
	public String seatId = "";
	public String tableId = "";
	// 创建时间
	public String createTime = "2012-11-17 20:45";
	// 地址
	public String address = "北京";
	// 顾客名称
	public String customerName = "匿名顾客";
	/** 类型:HERE即食AWAY外卖 */
	public int type = CaseData.HERE;
	/** 状态:Unpaid=0,Paid=1 */
	public int state = 0;
	// 小费
	public int tip = 10;
	// 折扣
	public float discount = 0.88f;
	// 消费
	public float cost = 99.1f;

	public static BillData current = new BillData();
	private List<CaseData> caseList;
	/** 电话 */
	public String tel = "";

	public void setCaseList(List<CaseData> caseList) {
		this.caseList = caseList;
	}

	public List<CaseData> getCaseList() {
		return caseList;
	}

	public BillData() {

	}

	public BillData(int id, String customerId, String seatId, String tableId,
			String createTime, String address, int type, int state, int tip,
			float discount, float cost) {
		this.id = id;
		this.customerId = customerId;
		this.seatId = seatId;
		this.tableId = tableId;
		this.createTime = createTime;
		this.address = address;
		this.type = type;
		this.state = state;
		this.tip = tip;
		this.discount = discount;
		this.cost = cost;
	}
}
