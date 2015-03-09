package library;

import java.sql.Date;

public class LenInfo implements java.io.Serializable{

	private static final long serialVersionUID=-8792355134417983433L;
	protected String readid;
	protected String bookcode;
	protected Date borrowdate;
	protected Date duedate;
	protected Date returndate;
	protected int renew;
	protected int overdate;
	protected int fine;
	
	
	public LenInfo(String readid, String bookcode, Date duedate,
			Date returndate, int overdate, int fine) {
		super();
		this.readid = readid;
		this.bookcode = bookcode;
		this.duedate = duedate;
		this.returndate = returndate;
		this.overdate = overdate;
		this.fine = fine;
	}
	public LenInfo(String readid, String bookcode, Date borrowdate, Date duedate) {
		super();
		this.readid = readid;
		this.bookcode = bookcode;
		this.borrowdate = borrowdate;
		this.duedate = duedate;
	}
	public LenInfo(String readid, String bookcode) {
		super();
		this.readid = readid;
		this.bookcode = bookcode;
	}
	public LenInfo(String readid, String bookcode, Date borrowdate) {
		super();
		this.readid = readid;
		this.bookcode = bookcode;
		this.borrowdate = borrowdate;
	}
	public LenInfo(String readid, String bookcode, Date borrowdate,
			Date duedate, Date returndate, int renew, int overdate, int fine) {
		super();
		this.readid = readid;
		this.bookcode = bookcode;
		this.borrowdate = borrowdate;
		this.duedate = duedate;
		this.returndate = returndate;
		this.renew = renew;
		this.overdate = overdate;
		this.fine = fine;
	}
	public String getReadid() {
		return readid;
	}
	public void setReadid(String readid) {
		this.readid = readid;
	}
	public String getBookcode() {
		return bookcode;
	}
	public void setBookcode(String bookcode) {
		this.bookcode = bookcode;
	}
	public Date getBorrowdate() {
		return borrowdate;
	}
	public void setBorrowdate(Date borrowdate) {
		this.borrowdate = borrowdate;
	}
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public Date getReturndate() {
		return returndate;
	}
	public void setReturndate(Date returndate) {
		this.returndate = returndate;
	}
	public int getRenew() {
		return renew;
	}
	public void setRenew(int renew) {
		this.renew = renew;
	}
	public int getOverdate() {
		return overdate;
	}
	public void setOverdate(int overdate) {
		this.overdate = overdate;
	}
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "LenInfo [readid=" + readid + ", bookcode=" + bookcode
				+ ", borrowdate=" + borrowdate + ", duedate=" + duedate
				+ ", returndate=" + returndate + ", renew=" + renew
				+ ", overdate=" + overdate + ", fine=" + fine + "]";
	}
	
}
