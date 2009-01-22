package beans;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class CommonBean extends HashMap<String, Object>
{

        private static String header = "ABC - BANK";
	private String message = "message";
	private String exception = "exception";
	private String footer = "footer";

	public CommonBean ()
	{
		System.out.println("CommonBean()");
	}

         @SuppressWarnings("unchecked")
        public void populate(HttpServletRequest r)
        {
                //System.out.println("CommonBean.populate()");
                Enumeration<String> e = r.getParameterNames();
                while(e.hasMoreElements())
                {
                        String n = e.nextElement();
                        System.out.println("this.put(" + n + ", " + r.getParameter(n) + ")");
                        this.put( n, r.getParameter(n) );
                }
        }

	public String getHeader ()
	{
		System.out.println("CommonBean.getHeader()");
		return header;
	}

	public String getMessage ()
	{
		System.out.println("CommonBean.getMessage()");
		return message;
	}

	public String getException ()
	{
		System.out.println("CommonBean.getException()");
		return exception;
	}

	public String getFooter ()
	{
		System.out.println("CommonBean.getFooter()");
		return footer;
	}

	public void setHeader (String newHeader)
	{
		System.out.println("CommonBean.setHeader(" + newHeader + ")");
		header = newHeader;
	}

	public void setMessage (String newMessage)
	{
		System.out.println("CommonBean.setHeader(" + newMessage + ")");
		message = newMessage;
	}

	public void setException (String newException)
	{
		System.out.println("CommonBean.setException(" + newException + ")");
		exception = newException;
	}

	public void setFooter (String newFooter)
	{
		System.out.println("CommonBean.setFooter(" + newFooter + ")");
		footer = newFooter;
	}

	@Override
	protected void finalize () throws Throwable
	{
		System.err.println("CommonBean.finalize()");
		super.finalize();
	}
}
