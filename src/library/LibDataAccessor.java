package library;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LibDataAccessor {

	private ArrayList<BookDetails>bookDataList;//图书详细信息列表
	private ArrayList<BookInLibrary> bookLibList;//图书馆藏信息列表
	private ArrayList<BorrowInfo>borrowDataList;//我的借阅信息列表
	private DbInfoGetter dbInfo;//数据库参数连接对象
	private Connection con=null; //数据库连接
	private Statement stms=null;//数据库操作对象
	private ResultSet rs=null;//数据库操作结果集
	private String dbDriver; //数据库驱动程序
	private static String dbURL;//数据库
	private static String dbUser;//数据库用户名
	private static String dbPassword;//数据库密码
	public LibDataAccessor(){
		dbInfo=new DbInfoGetter();
		dbDriver=dbInfo.getDbDriver();
		dbURL=dbInfo.getDbURL();
		dbUser=dbInfo.getDbUser();
		dbPassword=dbInfo.getDbPassword();
		try{
			Class.forName(dbDriver);//数据库加载
			log("数据库连接成功");
		}catch(ClassNotFoundException e){
			log("找不到数据库驱动程序");
		}
	}
	
	/**
	 * 从数据库中取得图书详细信息
	 */
	public ArrayList<BookDetails>getBookDetails(String theField,String theKeyword){
		try{
			//con=DriverManager.getConnection(dbURL,dbUser,dbPassword);
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
		}catch(SQLException e){
			log("建立数据库连接失败");
		}
		try{
			stms=con.createStatement();
			String sql="SELECT * FROM tb_bookdata WHERE "+theField+" LIKE '%"+theKeyword+"%'";
			rs=stms.executeQuery(sql);
			bookDataList=new ArrayList<BookDetails>();
			BookDetails bookDetails;
			String isbn;
			String name;
			String series;
			String authors;
			String publisher;
			String size = null;
			int pages;
			double price;
			String introduction;
			String picture;
			String clnum;
			while(rs.next()){
				isbn=rs.getString("isbn");
				name=rs.getString("name");
				series=rs.getString("series");
				authors=rs.getString("authors");
				publisher=rs.getString("publisher");
				size=rs.getString("size");
				pages=rs.getInt("pages");
				price=rs.getDouble("price");
				introduction=rs.getString("introduction");
				picture=rs.getString("picture");
				clnum=rs.getString("clnum");
				bookDetails=new BookDetails(isbn,name,series,authors,publisher,size,pages,price,introduction,picture,clnum);
				bookDataList.add(bookDetails);
			}
			rs.close();
			stms.close();
			con.close();
			return bookDataList;
		}catch(SQLException e){
			log("数据库读取异常"+e);
			return bookDataList;
		}
	}
	
	/**
	 * 从数据库中获取图书馆藏信息
	 * @param msg
	 */
	public ArrayList<BookInLibrary>getBookLibInfo(String isbn){
		try{
			//con=DriverManager.getConnection(dbURL, dbUser, dbPassword);
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
		}catch(SQLException e){
			log("建立数据库连接失败");
		}
		try{
			stms=con.createStatement();
			String sql="select * from tb_bookinfo where tb_bookinfo.isbn like '%"+isbn+"%'";
			rs=stms.executeQuery(sql);
			bookLibList=new ArrayList<BookInLibrary>();
			BookInLibrary bookInLibrary=null;
			String barCode; //条形码
			int status;//是否在馆中，1.在，2.不在
			String location;//馆藏位置
			Date dueReturnDate;
			while(rs.next()){
				barCode=rs.getString("barCode");
				status=rs.getInt("status");
				location=rs.getString("location");
				dueReturnDate=rs.getDate("duedate");
				bookInLibrary=new BookInLibrary(barCode,status,location,dueReturnDate);
				bookLibList.add(bookInLibrary);
			}
			rs.close();
			stms.close();
			con.close();
			return bookLibList;
		}catch(SQLException e){
			log("数据库读取异常"+e);
			return bookLibList;
		}
	}
	
	/**
	 * 从数据库读取我的借阅信息
	 * @param 
	 */
	public ArrayList<BorrowInfo>getBorrowInfo(String readerID){
		try{
			//con=DriverManager.getConnection(dbURL,dbUser,dbPassword);
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
		}catch(SQLException e){
			log("建立数据库连接失败");
		}
		try{
			stms=con.createStatement();
			String sqlsel="select tb_bookdata.name,tb_bookdata.authors,tb_bookdata.publisher,tb_lendinfo.borrowdate,tb_lendinfo.duedate,tb_lendinfo.returndate,tb_lendinfo.overduedate,tb_lendinfo.fine ";
			String sqlfrom="from tb_lendinfo,tb_bookdata,tb_bookinfo ";
			String sqlwhere="where tb_lendinfo.readerid='"+readerID+"' and tb_lendinfo.bookcode=tb_bookinfo.barcode and tb_bookinfo.isbn=tb_bookdata.isbn";
			String sql=sqlsel+sqlfrom+sqlwhere;
			rs=stms.executeQuery(sql);
			borrowDataList=new ArrayList<BorrowInfo>();
			BorrowInfo borrowInfo=null;
			String bookName;
			String bookAuthors;
			String publisher;
			Date borrowDate;
			Date dueDate;
			Date returnDate;
			int overDueDays;
			double fineMoney;
			while(rs.next()){
				System.out.println("okokok");
				bookName=rs.getString("name");
				bookAuthors=rs.getString("authors");
				publisher=rs.getString("publisher");
				borrowDate=rs.getDate("borrowdate");
				dueDate=rs.getDate("duedate");
				returnDate=rs.getDate("returndate");
				overDueDays=rs.getInt("overduedate");
				fineMoney=rs.getDouble("fine");
				System.out.println("result===>"+bookName+bookAuthors+borrowDate);
				borrowInfo=new BorrowInfo(bookName,bookAuthors,publisher,borrowDate,dueDate,returnDate,overDueDays,fineMoney);
				borrowDataList.add(borrowInfo);
			}
			rs.close();
			stms.close();
			con.close();
			return borrowDataList;
		}catch(SQLException e){
			log("数据库读取异常"+e);
			return borrowDataList;
		}
	}
	
	protected void log(Object msg){
		System.out.println(CurrDateTime.currDateTime()+"LibDataAccessor类"+msg);
	}
	
	protected boolean checkUser(String readerId,String pwd){
		boolean isreader=false;
		try{
			con=DriverManager.getConnection(dbURL,dbUser,dbPassword);
			stms=con.createStatement();
			String sql="select * from tb_reader where readerid='"+readerId+"' and passwd='"+pwd+"'";
			rs=stms.executeQuery(sql);
			while(rs.next()){
				isreader=true;
			}
			stms.close();
			rs.close();
			con.close();
		}catch(SQLException e){
			log("check-->数据库连接出错");
		}
		return isreader;
	}
	
	protected boolean checkManager(String id, String pswd){
		try{
			con=DriverManager.getConnection(dbURL,dbUser,dbPassword);
			stms=con.createStatement();
			String sql="select * from tb_librarian where userid='"+id+"' and pswd='"+pswd+"'";
			rs=stms.executeQuery(sql);
			while(rs.next()){
				stms.close();
				rs.close();
				con.close();
				return true;
			}
			stms.close();
			rs.close();
			con.close();
		}catch(SQLException e){
			log("check-->数据库连接出错");
		}
		return false;
	}
	
	protected boolean addBook(BookDetails bookDetails){
		try{
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
			//con=DriverManager.getConnection(dbURL,dbUser,dbPassword);
			stms=con.createStatement();
			String sql="insert into tb_bookdata(isbn,name,series,authors,publisher,size,pages,price,introduction,clnum) values('"+
			bookDetails.getIsbn()+"','"+bookDetails.getName()+"','"+bookDetails.getSeries()+"','"+bookDetails.getAuthors()+"','"+bookDetails.getPublisher()+"','"+bookDetails.getSize()+"',"+
			bookDetails.getPage()+","+bookDetails.getPrice()+",'"+bookDetails.getIntroduction()+"','"+bookDetails.getClnum()+"')";
			String sql1="insert into tb_bookinfo(barcode,isbn,status,location) values('"+bookDetails.getBarcode()+"','"+bookDetails.getIsbn()+"',"+1+",'"+bookDetails.getLocation()+"')";
			stms.execute(sql);
			stms.execute(sql1);
			stms.close();
			//rs.close();
			con.close();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	protected boolean addReader(ReaderInfo readerInfo){
		try{
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
			//con=DriverManager.getConnection(dbURL,dbUser,dbPassword);
			stms=con.createStatement();
			String sql="insert into tb_reader(readerid,passwd,name,gender,address,tel,startdate,enddate,type)values('"+
					readerInfo.getReaderid()+"','"+readerInfo.getReaderpswd()+"','"+readerInfo.getReaderName()+"','"+readerInfo.getReadergender()+"','"+readerInfo.getReaderaddress()+"','"+
					readerInfo.getReadertel()+"','"+readerInfo.getStartdate()+"','"+readerInfo.getEnddate()+"',"+readerInfo.getType()+")";
			stms.execute(sql);
			stms.close();
			con.close();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public int borrowBook(LenInfo lenInfo){
		log("insert leninfo");
		try{
			int type;
			int period;
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
			stms=con.createStatement();
			String sql1="select type from tb_reader where readerid='"+lenInfo.getReadid()+"'";
			rs=stms.executeQuery(sql1);
			if(!rs.next()){
				stms.close();
				rs.close();
				con.close();
				return 0;
			}
			type=rs.getInt("type");
			String sql2="select period  from tb_parameter where type="+type;
			rs=stms.executeQuery(sql2);
			while(rs.next()){
				period=rs.getInt("period");
			}
			int statu=0;
			String sql4="select status from tb_bookinfo where barcode='"+lenInfo.getBookcode()+"'";
			rs=stms.executeQuery(sql4);
		    while(rs.next()){
				statu=rs.getInt("status");
			}
			if(statu==0){
				return 3;
			}
			java.util.Date now = new java.util.Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();        
		    calendar.setTime(lenInfo.getBorrowdate());
		    calendar.add(Calendar.DATE, +20);
		    String afterdate=dateFormat.format(calendar.getTime());
		    Date duedate=Date.valueOf(afterdate);
		    String sql3="insert into tb_lendinfo(readerid,bookcode,borrowdate,duedate) values('"+
		    lenInfo.getReadid()+"','"+lenInfo.getBookcode()+"','"+lenInfo.getBorrowdate()+"','"+duedate+"')";
		    stms.execute(sql3);
		    String sql5="update tb_bookinfo set status="+0+" where barcode='"+lenInfo.getBookcode()+"'";
			stms.execute(sql5);
		    rs.close();
			stms.close();
			con.close();
			return 1;
		}catch(SQLException e){
			e.printStackTrace();
		}
		log("fail");
		return 2;
	}
	
	public int returnBook(LenInfo lenInfo){
		try{
			int over=0;
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
			stms=con.createStatement();
			String sql1="select duedate from tb_lendinfo where readerid='"+lenInfo.getReadid()+"' and renew=0";
			rs=stms.executeQuery(sql1);
			if(rs.next()){
				Date duedate=null;
				duedate=rs.getDate("duedate");
				System.out.println(duedate+"--->"+lenInfo.getReturndate());
				if(lenInfo.getReturndate().after(duedate)){
					Calendar duecalendar = Calendar.getInstance();        
				    duecalendar.setTime(duedate);
				    Calendar returncalendar = Calendar.getInstance();        
				    returncalendar.setTime(lenInfo.getReturndate());
				    over=(int)(returncalendar.getTimeInMillis()-duecalendar.getTimeInMillis())/(3600*24*1000);
				}
			}else{
				return 0;
			}
			String sql="update tb_lendinfo set returndate='"+lenInfo.getReturndate()+"',overduedate="+over+",fine="+over*0.1+",renew=1 where readerid='"+lenInfo.getReadid()+"' and bookcode='"+lenInfo.getBookcode()+"' and renew=0";
			stms.execute(sql);
			String sqlstatu="update tb_bookinfo set status="+1+" where barcode='"+lenInfo.getBookcode()+"'";
			stms.execute(sqlstatu);
			stms.close();
			con.close();
			return 1;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 2;
	}
	
	protected int delBook(String isbn){
		try{
			int status=0;
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
			stms=con.createStatement();
			String sql="select status from tb_bookinfo where isbn='"+isbn+"'";
			rs=stms.executeQuery(sql);
			while(rs.next()){
				status=rs.getInt("status");
			}
			if(0==status){
				return 0;
			}
			String sqldle="delete from tb_bookdata where isbn='"+isbn+"'";
			if(stms.execute(sqldle)){
				System.out.println("good");
				return 2;
			}else{
				System.out.println("nod");
				return 1;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 2;
	}
	
	protected int delReader(String readerid){
		try{
			int bookcount=0;
			con=DriverManager.getConnection(dbURL+"?user="+dbUser+"&password="+dbPassword+"&useUnicode=true&characterEncoding=utf-8");
			stms=con.createStatement();
			String sql="select count(*) from tb_lendinfo where readerid='"+readerid+"' and renew=0";
			rs=stms.executeQuery(sql);
			while(rs.next()){
				bookcount=rs.getInt("count(*)");
			}
			System.out.println(bookcount+"===");
			if(bookcount>0){
				return 1;
			}
			String sqldle="delete from tb_reader where readerid='"+readerid+"'";
			if(stms.execute(sqldle)){
				return 0;
			}else{
				return 2;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 3;
	}
	
	public static void main(String []args) throws SQLException, ParseException{
//		String start="2013"+"-"+"12"+"-"+"13";
//		String end="2013"+"-"+"12"+"-"+"24";
//		java.sql.Date startdate = java.sql.Date.valueOf(start);
//		java.sql.Date enddate = java.sql.Date.valueOf(end);
		//LibDataAccessor lib=new LibDataAccessor();
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_lib","sea","just");
		ResultSet rs=null;
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_lib"+"?user="+"sea"+"&password="+"just"+"&useUnicode=true&characterEncoding=utf-8");
		Statement stms=con.createStatement();
		//String sql="insert into tb_reader(readerid,passwd,name,gender,address,tel,startdate,enddate,type)values('1','1','1','男','','','"+startdate+"','"+enddate+"',"+1+")";
		String sql="select duedate from tb_lendinfo where readerid='1143041137' and renew=0";
		//String sql="INSERT INTO tb_reader (`id`, `readerid`, `passwd`, `name`, `gender`, `address`, `tel`, `startdate`, `enddate`, `type`) VALUES (NULL, '1', '1', '1', '1', '11', '1', '2013-12-05', '2013-12-03', '1')";
		rs=stms.executeQuery(sql);
		if(rs.next()){
			System.out.println(rs.getDate("duedate"));
		}else{
			System.out.println("onno");
		}
		
		stms.close();
		//rs.close();
		con.close();
		
//		String start="2013"+"-"+"12"+"-"+"13";
//		String end="2013"+"-"+"12"+"-"+"24";
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		Date startdate = (Date) sdf.parse(start);
//		Date enddate = (Date) sdf.parse(end);
//		System.out.println(startdate+"---"+enddate);
//		
//		java.util.Date now = new java.util.Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String hehe = dateFormat.format(now);
//		Date date=Date.valueOf(hehe);
//		System.out.println(now.getDay());
//		Calendar calendar = Calendar.getInstance();        
//	    calendar.setTime(date);
//	    calendar.add(Calendar.DATE, +20);
//	    String afterdate=dateFormat.format(calendar.getTime());
//	    Date after=Date.valueOf(afterdate);
////		int day=date.getDay()+20;
////		date.setDate(day);
//		System.out.println(after.getTimezoneOffset());
	}
}
