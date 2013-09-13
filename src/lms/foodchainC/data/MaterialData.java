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
	public int materialId = 0;
	// 名字
	public String name = "猪肉";
	// 单位
	public String unit = "g";
	// 每单位单价
	public float price = 0;
	// 库存
	public float count = 0;

	public static MaterialData current = new MaterialData();

	public MaterialData() {
		// TODO Auto-generated constructor stub
	}

	public MaterialData(int materialId, String name, String unit, float price,
			float count) {
		this.materialId = materialId;
		this.name = name;
		this.unit = unit;
		this.price = price;
		this.count = count;
	}
}
