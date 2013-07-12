package lms.foodchainC.data;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 材料数据类
 * 
 */
public class MaterialData {
	// ID
	public int id;
	// 名字
	public String name;
	// 单位
	public String unit;
	// 每单位单价
	public float price;
	// 库存
	public int count;

	public static MaterialData current = new MaterialData();

	public MaterialData() {
		// TODO Auto-generated constructor stub
	}

	public MaterialData(int id, String name, String unit, float price, int count) {
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.price = price;
		this.count = count;
	}
}
