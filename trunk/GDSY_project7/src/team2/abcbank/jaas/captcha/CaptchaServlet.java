package team2.abcbank.jaas.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.service.CaptchaServiceException;

public class CaptchaServlet extends HttpServlet
{
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			// gebruik de sessionId om de captcha de identificeren, als we h'm willen controleren
			String captchaId = request.getSession().getId();

			// haal het plaatje op
			BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, request.getLocale());
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/png");
			ServletOutputStream os = response.getOutputStream();
			ImageIO.write(challenge, "png", os);
			os.flush();
			os.close();
		}
		catch (IllegalArgumentException e)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		catch (CaptchaServiceException e)
		{
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// we vangen zowel GET als POST op
		doGet(request, response);
	}
}
