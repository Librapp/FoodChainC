package lms.foodchainC.data;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 菜品数据类
 * 
 */
import java.util.List;

public class CaseData {
	// Id
	public int id;
	// 网上Id
	public int caseId = 0;
	// 图片
	public String picPath = OtherData.CASE_PIC;
	// 简介
	public String intro = "经典";
	// 菜名
	public String name = "蛋炒饭";
	// 订单时间
	public String orderTime;
	// 等待时间
	public String waitTime;
	// 评分
	public float mark = 5;
	// 消息
	public String message;
	// 订单编号
	public int billId;
	// 价格
	public float price = 10f;
	// 特价
	public float special = 0f;
	// 菜系
	public int family = 0;
	// 厨师
	public int cookerId;
	// 服务生
	public int waiterId;
	public static final int HERE = 0;
	public static final int AWAY = 1;
	public static final int PACK = 2;
	/** 类型:HERE即食AWAY外卖PACK带走 */
	public int type = HERE;
	// 状态类型
	public static final int AVILIABLE = 0;
	public static final int UNAVILIABLE = 1;
	/** 状态 AVILIABLE、UNAVILIABLE */
	public int state = AVILIABLE;
	/** 分类 冷菜热菜主副食 */
	public int style = 0;
	// 份数
	public int count = 0;
	// 烹饪时间
	public int cookTime = 10;
	// 烹饪列表Id
	public int orderId;
	// 是否是新创建
	public boolean isNew = false;
	/** 星期几 */
	public int weekday = 0;
	public static final int MORNING = 1;
	public static final int NOON = 2;
	public static final int NIGHT = 3;
	/** 时段 MORNING NOON NIGHT */
	public int time = 0;
	// 评论
	private List<CommentData> comment;
	// 原料
	private List<MaterialData> materials;

	public static CaseData current;

	public CaseData() {
		this.picPath += this.name + OtherData.PICTYPE;
	}

	/** 所有菜单用 */
	public CaseData(int id, int caseId, String name, float price, String pic,
			int cookTime, int style) {
		this.id = id;
		this.caseId = caseId;
		this.name = name;
		this.price = price;
		this.picPath = pic;
		this.cookTime = cookTime;
		this.style = style;
	}

	/** 账单用 */
	public CaseData(int orderId, int id, int caseId, String name, float price,
			int billId, String orderTime, int state, int type, String message) {
		this.orderId = orderId;
		this.id = id;
		this.caseId = caseId;
		this.name = name;
		this.price = price;
		this.billId = billId;
		this.orderTime = orderTime;
		this.state = state;
		this.type = type;
		this.message = message;
	}

	/** 详情信息用 */
	public CaseData(int id, int caseId, String name, float price, String pic,
			String intro, int cookTime, int style, int family) {
		this.id = id;
		this.caseId = caseId;
		this.name = name;
		this.price = price;
		this.picPath = pic;
		this.intro = intro;
		this.cookTime = cookTime;
		this.style = style;
		this.family = family;
	}

	/** 星期菜单用 */
	public CaseData(int id, int caseId, String name, float price, String pic,
			int cookTime, String intro, int style, float special, int state,
			int weekday, int time) {
		this.id = id;
		this.caseId = caseId;
		this.name = name;
		this.price = price;
		this.picPath = pic;
		this.cookTime = cookTime;
		this.intro = intro;
		this.style = style;
		this.special = special;
		this.state = state;
		this.weekday = weekday;
		this.time = time;
	}

	public static CaseData current() {
		if (current == null) {
			current = new CaseData();
		}
		return current;
	}

	public void setComment(List<CommentData> comment) {
		this.comment = comment;
	}

	public List<CommentData> getComment() {
		return comment;
	}

	public void setMaterials(List<MaterialData> materials) {
		this.materials = materials;
	}

	public List<MaterialData> getMaterials() {
		return materials;
	}
}
