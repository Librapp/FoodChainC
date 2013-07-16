package lms.foodchainC.data;

import java.util.ArrayList;

public class MenuData {
	public int restaurantId;
	private ArrayList<CaseStyleData> list;

	public void setList(ArrayList<CaseStyleData> list) {
		this.list = list;
	}

	public ArrayList<CaseStyleData> getList() {
		return list;
	}
}
