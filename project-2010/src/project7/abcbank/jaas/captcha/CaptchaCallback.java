package project7.abcbank.jaas.captcha;

import javax.security.auth.callback.Callback;

public class CaptchaCallback implements Callback
{
	private String captcha = null;

	public String getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha(String captcha)
	{
		this.captcha = captcha;
	}
}
