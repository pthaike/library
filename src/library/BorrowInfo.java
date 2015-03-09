package library;

import java.sql.Date;

public class BorrowInfo implements java.io.Serializable{

	private static final long serialVerrsionUID=8729453305993405592L;
	protected String bookName;//����
	protected String bookAuthors;//����
	protected String pubilisher; //������Ϣ
	protected Date borrowDate; //����ʱ��
	protected Date dueDate; //Ӧ��ʱ��
	protected Date returnDate;//����ʱ��
	protected int overduedays;; //��������
	protected double fineMoney;//������
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthors() {
		return bookAuthors;
	}
	public void setBookAuthors(String bookAuthors) {
		this.bookAuthors = bookAuthors;
	}
	public String getPubilisher() {
		return pubilisher;
	}
	public void setPubilisher(String pubilisher) {
		this.pubilisher = pubilisher;
	}
	public Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public int getOverduedays() {
		return overduedays;
	}
	public void setOverduedays(int overduedays) {
		this.overduedays = overduedays;
	}
	public double getFineMoney() {
		return fineMoney;
	}
	public void setFineMoney(double fineMoney) {
		this.fineMoney = fineMoney;
	}
	public static long getSerialverrsionuid() {
		return serialVerrsionUID;
	}
	public BorrowInfo() {
		super();
	}
	public BorrowInfo(String bookName, String bookAuthors, String pubilisher,
			Date borrowDate, Date dueDate, Date returnDate, int overduedays,
			double fineMoney) {
		super();
		this.bookName = bookName;
		this.bookAuthors = bookAuthors;
		this.pubilisher = pubilisher;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.overduedays = overduedays;
		this.fineMoney = fineMoney;
		System.out.println(this.bookName+"----"+this.bookAuthors+"-----"+this.pubilisher+"-------"+this.borrowDate+"------"+this.dueDate+"------"+this.returnDate+"------"+this.overduedays);
	}
}
