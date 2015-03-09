package library;

public interface LibProtocals {

	public static final int OP_GET_BOOK_DETAILS=100;//读取图书详细信息
	public static final int OP_GET_BOOK_LIBINFO=101;//读取图书在在馆情况
	public static final int OP_GET_BORROINFO=102;//读取读者借阅信息
	public static final int OP_CHECK_USER=103;//读取读者借阅信息
	public static final int OP_SUCCESS=104;//成功
	public static final int OP_FAIL=105;//失败
	public static final int OP_ADDBOOK=106;
	public static final int OP_ADDREADER=107;
	public static final int OP_CHECK_MANAGER=108;
	public static final int OP_LENBOOK=109;
	public static final int OP_RETURNBOOK=110;
	public static final int OP_NOTEXIT=111;
	public static final int OP_DELBOOK=112;
	public static final int OP_CANTN=113;
	public static final int OP_DELREADER=114;
}
