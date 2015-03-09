package library;

public class BookDetails implements java.io.Serializable{

	private static final long serialVersionUID=-8792355134417983448L; //�������л�
	private String isbn; //ISBN,���ʱ�׼���
	private String barcode;
	private String name; //����
	private String series;//������
	private String authors; //����
	private String publisher; //������Ϣ
	private String size; //ͼ�鿪��
	private int page; //ҳ��
	private double price; //����
	private String introduction; //ͼ����
	private String picture;//����ͼƬ��
	private String clnum;//ͼ������
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
