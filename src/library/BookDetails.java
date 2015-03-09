package library;

public class BookDetails implements java.io.Serializable{

	private static final long serialVersionUID=-8792355134417983448L; //对象序列化
	private String isbn; //ISBN,国际标准书号
	private String barcode;
	private String name; //书名
	private String series;//丛书名
	private String authors; //作者
	private String publisher; //出版信息
	private String size; //图书开本
	private int page; //页数
	private double price; //定价
	private String introduction; //图书简介
	private String picture;//封面图片名
	private String clnum;//图书分类号
	private String location;
	public BookDetails(String isbn, String name, String series, String authors,
			String publisher, String size, int page, double price,
			String introduction, String picture, String clnum) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.series = series;
		this.authors = authors;
		this.publisher = publisher;
		this.size = size;
		this.page = page;
		this.price = price;
		this.introduction = introduction;
		this.picture = picture;
		this.clnum = clnum;
	}
	
	public BookDetails(String isbn, String barcode, String name, String series,
			String authors, String publisher, String size, int page,
			double price, String introduction, String picture, String clnum) {
		super();
		this.isbn = isbn;
		this.barcode = barcode;
		this.name = name;
		this.series = series;
		this.authors = authors;
		this.publisher = publisher;
		this.size = size;
		this.page = page;
		this.price = price;
		this.introduction = introduction;
		this.picture = picture;
		this.clnum = clnum;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BookDetails() {
		super();
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getClnum() {
		return clnum;
	}
	public void setClnum(String clnum) {
		this.clnum = clnum;
	}
	@Override
	public String toString() {
		return name+"-"+authors+"-"+publisher;
	}
	
	public int compareTo(Object object){
		BookDetails book=(BookDetails)object;
		String targetBook=book.getName();
		return name.compareTo(targetBook);
	}
}
