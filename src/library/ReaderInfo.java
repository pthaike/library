package library;

import java.sql.Date;

public class ReaderInfo implements java.io.Serializable{

	private static final long serialVersionUID=-8792525525525255255L;
	protected String readerid=null;
	protected String readerpswd=null;
	protected String readerName=null;
	protected String readergender=null;
	protected String readeraddress=null;
	protected String readertel=null;
	protected Date startdate=null;
	protected Date enddate=null;
	protected int type;
	public ReaderInfo(String readerid, String readerpswd, String readerName,
			String readergender, String readeraddress, String readertel,
			Date startdate, Date enddate, int type) {
		super();
		this.readerid = readerid;
		this.readerpswd = readerpswd;
		this.readerName = readerName;
		this.readergender = readergender;
		this.readeraddress = readeraddress;
		this.readertel = readertel;
		this.startdate = startdate;
		this.enddate = enddate;
		this.type = type;
	}
	public String getReaderid() {
		return readerid;
	}
	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}
	public String getReaderpswd() {
		return readerpswd;
	}
	public void setReaderpswd(String readerpswd) {
		this.readerpswd = readerpswd;
	}
	public String getReaderName() {
		return readerName;
	}
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	public String getReadergender() {
		return readergender;
	}
	public void setReadergender(String readergender) {
		this.readergender = readergender;
	}
	public String getReaderaddress() {
		return readeraddress;
	}
	public void setReaderaddress(String readeraddress) {
		this.readeraddress = readeraddress;
	}
	public String getReadertel() {
		return readertel;
	}
	public void setReadertel(String readertel) {
		this.readertel = readertel;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
