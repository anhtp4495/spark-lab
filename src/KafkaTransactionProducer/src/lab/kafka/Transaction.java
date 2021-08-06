package lab.kafka;

public class Transaction {
	private String _MaGiaoDich;
	private String _LoaiGiaoDich;  
	private String _TenKhachHang;
	private String _NgayGiaoDich;
	private Long _SoTienGiaoDich;
	
	public Transaction(String MaGiaoDich, 
			String LoaiGiaoDich,  
			String TenKhachHang,
			String NgayGiaoDich,
			Long SoTienGiaoDich) {
		this._MaGiaoDich = MaGiaoDich;
		this._LoaiGiaoDich = LoaiGiaoDich;
		this._TenKhachHang = TenKhachHang;
		this._NgayGiaoDich = NgayGiaoDich;
		this._SoTienGiaoDich = SoTienGiaoDich;
	}
	
	public String getKey() {
		return _MaGiaoDich;
	}
	
	@Override
	public String toString() {
		return String.format("'%s' '%s' '%s' '%s' '%s'", _MaGiaoDich, _NgayGiaoDich, _LoaiGiaoDich, _TenKhachHang, _SoTienGiaoDich);
	}
}
