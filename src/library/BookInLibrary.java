package library;

import java.sql.Date;

public class BookInLibrary implements java.io.Serializable{

	private static final long serrialVersionUID=738444304600938175L;
	protected String barCode; //������
	protected int status; //�Ƿ��ڹݣ�1.�ڣ�0����
	protected String location;//�ݲ�λ��
	protected Date dueReturnDate; //Ӧ������
	public BookInLibrary(String barCode, int status, String location,
			Date dueReturnDate) {
		super();
		this.barCode = barCode;
		this.status = status;
		this.location = location;
		this.dueReturnDate = dueReturnDate;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getDueReturnDate() {
		return dueReturnDate;
	}
	public void setDueReturnDate(Date dueReturnDate) {
		this.dueReturnDate = dueReturnDate;
	}
	public static long getSerrialversionuid() {
		return serrialVersionUID;
	}
	public String toString(){
		return barCode+"-"+status+"-"+location+"-"+dueReturnDate;
	}
}
