package lms.foodchainC.util;

import java.util.ArrayList;
import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.EmotionData;

/**
 * 
 * @author ����
 * 
 */
public class MakeEmotionsList {

	private static MakeEmotionsList instance;
	private List<EmotionData> le;
	private final int EMOTIONCOUNT = 46;

	public List<EmotionData> getLe() {
		le = new ArrayList<EmotionData>();

		for (int i = 0; i < EMOTIONCOUNT; i++) {
			EmotionData e = new EmotionData();

			switch (i) {
			case 0:
				e.setPhrase("[����]");
				e.setOrderNumber(0);
				e.setImageName("hh");
				e.setId(R.drawable.hh);

				break;
			case 1:
				e.setPhrase("[�Ǻ�]");
				e.setOrderNumber(1);
				e.setImageName("hehe");
				e.setId(R.drawable.hehe);

				break;
			case 2:
				e.setPhrase("[��]");
				e.setOrderNumber(2);
				e.setImageName("cool");
				e.setId(R.drawable.cool);

				break;
			case 3:
				e.setPhrase("[����]");
				e.setOrderNumber(3);
				e.setImageName("huaxin");
				e.setId(R.drawable.huaxin);

				break;
			case 4:
				e.setPhrase("[�ɰ�]");
				e.setOrderNumber(4);
				e.setImageName("keai");
				e.setId(R.drawable.keai);

				break;
			case 5:
				e.setPhrase("[͵Ц]");
				e.setOrderNumber(5);
				e.setImageName("tx");
				e.setId(R.drawable.tx);

				break;
			case 6:
				e.setPhrase("[��Ƥ]");
				e.setOrderNumber(6);
				e.setImageName("tp");
				e.setId(R.drawable.tp);

				break;
			case 7:
				e.setPhrase("[����]");
				e.setOrderNumber(7);
				e.setImageName("hx");
				e.setId(R.drawable.hx);

				break;
			case 8:
				e.setPhrase("[��]");
				e.setOrderNumber(8);
				e.setImageName("lei");
				e.setId(R.drawable.lei);

				break;
			case 9:
				e.setPhrase("[���]");
				e.setOrderNumber(9);
				e.setImageName("dk");
				e.setId(R.drawable.dk);

				break;
			case 10:
				e.setPhrase("[����]");
				e.setOrderNumber(10);
				e.setImageName("bs");
				e.setId(R.drawable.bs);

				break;
			case 11:
				e.setPhrase("[����]");
				e.setOrderNumber(11);
				e.setImageName("by");
				e.setId(R.drawable.by);

				break;
			case 12:
				e.setPhrase("[����]");
				e.setOrderNumber(12);
				e.setImageName("bz");
				e.setId(R.drawable.bz);

				break;
			case 13:
				e.setPhrase("[�Ծ�]");
				e.setOrderNumber(13);
				e.setImageName("cj");
				e.setId(R.drawable.cj);

				break;
			case 14:
				e.setPhrase("[���]");
				e.setOrderNumber(14);
				e.setImageName("dk");
				e.setId(R.drawable.dk);

				break;
			case 15:
				e.setPhrase("[��ŭ]");
				e.setOrderNumber(15);
				e.setImageName("fn");
				e.setId(R.drawable.fn);

				break;
			case 16:
				e.setPhrase("[����]");
				e.setOrderNumber(16);
				e.setImageName("gg");
				e.setId(R.drawable.gg);

				break;
			case 17:
				e.setPhrase("[good]");
				e.setOrderNumber(17);
				e.setImageName("good");
				e.setId(R.drawable.good);

				break;
			case 18:
				e.setPhrase("[��]");
				e.setOrderNumber(18);
				e.setImageName("han");
				e.setId(R.drawable.han);

				break;
			case 19:
				e.setPhrase("[����]");
				e.setOrderNumber(19);
				e.setImageName("hand");
				e.setId(R.drawable.hand);

				break;
			case 20:
				e.setPhrase("[��]");
				e.setOrderNumber(20);
				e.setImageName("hua");
				e.setId(R.drawable.hua);

				break;
			case 21:
				e.setPhrase("[����]");
				e.setOrderNumber(21);
				e.setImageName("jk");
				e.setId(R.drawable.jk);

				break;
			case 22:
				e.setPhrase("[����]");
				e.setOrderNumber(22);
				e.setImageName("kiss");
				e.setId(R.drawable.kiss);

				break;
			case 23:
				e.setPhrase("[��]");
				e.setOrderNumber(23);
				e.setImageName("kun");
				e.setId(R.drawable.kun);

				break;
			case 24:
				e.setPhrase("[Ǯ]");
				e.setOrderNumber(24);
				e.setImageName("money");
				e.setId(R.drawable.money);

				break;
			case 25:
				e.setPhrase("[����]");
				e.setOrderNumber(25);
				e.setImageName("moon");
				e.setId(R.drawable.moon);

				break;
			case 26:
				e.setPhrase("[ok]");
				e.setOrderNumber(1);
				e.setImageName("ok");
				e.setId(R.drawable.ok);

				break;
			case 27:
				e.setPhrase("[Ʋ��]");
				e.setOrderNumber(27);
				e.setImageName("pz");
				e.setId(R.drawable.pz);

				break;
			case 28:
				e.setPhrase("[��]");
				e.setOrderNumber(28);
				e.setImageName("ruo");
				e.setId(R.drawable.ruo);

				break;
			case 29:
				e.setPhrase("[����]");
				e.setOrderNumber(29);
				e.setImageName("sad");
				e.setId(R.drawable.sad);

				break;
			case 30:
				e.setPhrase("[���]");
				e.setOrderNumber(30);
				e.setImageName("shit");
				e.setId(R.drawable.shit);

				break;
			case 31:
				e.setPhrase("[˥]");
				e.setOrderNumber(31);
				e.setImageName("shuai");
				e.setId(R.drawable.shuai);

				break;
			case 32:
				e.setPhrase("[˯��]");
				e.setOrderNumber(32);
				e.setImageName("sj");
				e.setId(R.drawable.sj);

				break;
			case 33:
				e.setPhrase("[˼��]");
				e.setOrderNumber(33);
				e.setImageName("sk");
				e.setId(R.drawable.sk);

				break;
			case 34:
				e.setPhrase("[ʧ��]");
				e.setOrderNumber(34);
				e.setImageName("sw");
				e.setId(R.drawable.sw);

				break;
			case 35:
				e.setPhrase("[��]");
				e.setOrderNumber(35);
				e.setImageName("tu");
				e.setId(R.drawable.tu);

				break;
			case 36:
				e.setPhrase("[ί��]");
				e.setOrderNumber(36);
				e.setImageName("wq");
				e.setId(R.drawable.wq);

				break;
			case 37:
				e.setPhrase("[��]");
				e.setOrderNumber(37);
				e.setImageName("xin");
				e.setId(R.drawable.xin);

				break;
			case 38:
				e.setPhrase("[��]");
				e.setOrderNumber(38);
				e.setImageName("xu");
				e.setId(R.drawable.xu);

				break;
			case 39:
				e.setPhrase("[Ү]");
				e.setOrderNumber(39);
				e.setImageName("yeah");
				e.setId(R.drawable.yeah);

				break;
			case 40:
				e.setPhrase("[��]");
				e.setOrderNumber(40);
				e.setImageName("yun");
				e.setId(R.drawable.yun);

				break;
			case 41:
				e.setPhrase("[����]");
				e.setOrderNumber(41);
				e.setImageName("yw");
				e.setId(R.drawable.yw);

				break;
			case 42:
				e.setPhrase("[������]");
				e.setOrderNumber(42);
				e.setImageName("zgl");
				e.setId(R.drawable.zgl);

				break;
			case 43:
				e.setPhrase("[�ݰ�]");
				e.setOrderNumber(43);
				e.setImageName("zj");
				e.setId(R.drawable.zj);

				break;
			case 44:
				e.setPhrase("[ץ��]");
				e.setOrderNumber(44);
				e.setImageName("zk");
				e.setId(R.drawable.zk);

				break;
			case 45:
				e.setPhrase("[��ͷ]");
				e.setOrderNumber(45);
				e.setImageName("zt");
				e.setId(R.drawable.zt);

				break;
			default:
				break;
			}

			le.add(e);
		}

		return le;
	}

	public void setLe(List<EmotionData> le) {
		this.le = le;
	}

	public static MakeEmotionsList current() {
		if (instance == null) {
			instance = new MakeEmotionsList();
		}
		return instance;
	}
}
