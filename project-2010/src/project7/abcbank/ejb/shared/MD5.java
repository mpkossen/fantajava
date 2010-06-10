package project7.abcbank.ejb.shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
	private static final String code = "0123456789abcdef";

	private static MessageDigest md5 = null;

	static
	{
		try
		{
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException nsae)
		{
			System.err.println(nsae);
		}
	}

	public static byte[] hash(String w)
	{
		// System.out.println("MD5.hash("+w+")");
		md5.reset();
		md5.update(w.getBytes());
		return md5.digest();
	}

	public static String encode(byte[] md)
	{
		// System.out.println("MD5.encode("+md+")");
		String ret = "";
		for (int i = 0; i < md.length; i++)
		{
			char c1 = code.charAt((md[i] & 0xF0) >>> 4);
			char c2 = code.charAt(md[i] & 0x0F);
			ret += c1 + "" + c2;
		}
		// System.out.println(ret);
		return ret;
	}

	public static byte[] decode(String s)
	{
		// System.out.println("MD5.decode("+s+")");
		byte[] ret = new byte[s.length() / 2];
		for (int i = 0; i < s.length(); i += 2)
		{
			int c1 = code.indexOf(s.charAt(i)) << 4;
			int c2 = code.indexOf(s.charAt(i + 1));
			ret[i / 2] = (byte) (c1 | c2);
		}
		return ret;
	}
}
