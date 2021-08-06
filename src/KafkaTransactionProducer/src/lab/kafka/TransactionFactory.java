package lab.kafka;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TransactionFactory {

	private static final Long SoTienToiDa = 100000000L;
	public static boolean useRandomNgayGiaoDich = true;
	
	public static String PRODUCER_NAME = "PRODUCER_NAME";
	public static Transaction random() {
		String MaGiaoDich = PRODUCER_NAME + " " + UUID.randomUUID().toString();
		String LoaiGiaoDich = randomLoaiGiaoDich();
		String TenKhachHang = randomKhachHang();
		String NgayGiaoDich = useRandomNgayGiaoDich ? randomNgayGiaoDich() : convertDate2String(Calendar.getInstance().getTime());
		Long SoTienGiaoDich = new Double(Math.random() * SoTienToiDa).longValue();

		return new Transaction(MaGiaoDich, LoaiGiaoDich, TenKhachHang, NgayGiaoDich, SoTienGiaoDich);
	}

	public static ArrayList<Transaction> random(Integer soluong) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		while (soluong > 0) {
			transactions.add(random());
			soluong--;
		}

		return transactions;
	}

	private static String randomKhachHang() {
		String[] ho = { "Tran", "Ngo", "Nguyen", "Le", "Pham", "Hoang", "Vu", "Phan", "Dang", "Huynh", "Vo" };
		String[] tenDem = { "Phuc", "Hung", "Xuan", "Ngoc", "Minh", "Phi", "Hoang", "Quang", "Dang", "Hanh", "Dung",
				"Hong", "An", "Anh", "Ha", "Thien", "Hong", "Nhat", "Phi", "Long", "Ba", "Linh", "Lan", "Than", "Dau", "Tuat", "Hoi", "Ti", "Suu" };

		int hIndex = (int) (Math.random() * 100) % ho.length;
		int tdIndex = (int) (Math.random() * 100) % tenDem.length;
		int tIndex = (int) (Math.random() * 100) % tenDem.length;

		return String.format("%s %s %s", ho[hIndex], tenDem[tdIndex], tenDem[tIndex]);
	}

	private static String randomLoaiGiaoDich() {
		double k = Math.random();
		if (k < 0.2) {
			return "Chuyen tien";
		} else if (k > 0.8)
			return "Mo tiet kiem";
		return "Rut tien";
	}

	private static String randomNgayGiaoDich() {
		Double day = Math.random() * 365;
		Date date = new Date(new Date().getTime() - day.longValue() * 60 * 60 * 24 * 1000);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return dateFormat.format(date);
	}

	private static String convertDate2String(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return dateFormat.format(date);
	}
}
