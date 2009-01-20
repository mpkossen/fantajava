/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

/**
 * 
 * @author Bami (with code taken from Eric Gerlofsma example) [you stealing
 *         bastard]
 */

public class CommonBean {
	/***************************************************************************
	 * Static
	 **************************************************************************/
	private static final long serialVersionUID = 1L;
	private static String header = "EFG - BANK";
	private String message = "message";
	private String exception = "exception";
	private String footer = "footer";

	/***************************************************************************
	 * Construction
	 **************************************************************************/
	public CommonBean() {
		System.out.println("CommonBean()");
	}

	/***************************************************************************
	 * getters
	 **************************************************************************/
	public String getHeader() {
		System.out.println("CommonBean.getHeader()");
		return header;
	}

	public String getMessage() {
		System.out.println("CommonBean.getMessage()");
		return message;
	}

	public String getException() {
		System.out.println("CommonBean.getException()");
		return exception;
	}

	public String getFooter() {
		System.out.println("CommonBean.getFooter()");
		return footer;
	}

	/***************************************************************************
	 * setters
	 **************************************************************************/
	public void setHeader(String newHeader) {
		System.out.println("CommonBean.setHeader(" + newHeader + ")");
		header = newHeader;
	}

	public void setMessage(String newMessage) {
		System.out.println("CommonBean.setHeader(" + newMessage + ")");
		message = newMessage;
	}

	public void setException(String newException) {
		System.out.println("CommonBean.setException(" + newException + ")");
		exception = newException;
	}

	public void setFooter(String newFooter) {
		System.out.println("CommonBean.setFooter(" + newFooter + ")");
		footer = newFooter;
	}

	/***************************************************************************
	 * finalize
	 **************************************************************************/
	@Override
	protected void finalize() throws Throwable {
		System.err.println("CommonBean.finalize()");
		super.finalize();
	}
}
