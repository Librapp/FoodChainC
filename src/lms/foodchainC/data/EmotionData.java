package lms.foodchainC.data;

/**
 * @author ����˼
 * @createTime 2012-5-31
 * @description ������ʵ����
 * @changeLog
 */
public class EmotionData {

	private static EmotionData current;

	private String phrase;// ����ʹ�õ��������
	private int orderNumber;// �ñ�����ϵͳ�е��������
	private String category;// �������
	private String imageName;// �������
	private int id;

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static EmotionData current() {
		if (current == null) {
			current = new EmotionData();
		}
		return current;
	}
}
