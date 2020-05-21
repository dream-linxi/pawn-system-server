package com.dreams.sys.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreams.sys.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreams.sys.po.CodePo;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginController {


	@Resource
	private UserService userService;
	
	@GetMapping("/getImageCode")
	public void createImageCode(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		req.setCharacterEncoding("utf-8");
		BufferedImage bi = new BufferedImage(70, 25, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 70, 25);
		String sbs = "";
		g.setColor(Color.RED);
		for (int i = 0; i < 4; i++) {
			String s = (int)(Math.random()*10) + "";
			sbs += s;
		}
		CodePo code = new CodePo(sbs, 30);
		g.drawString(sbs, 20, 20);
		req.getSession().setAttribute("imageCode", code);
		ImageIO.write(bi, "jpeg", resp.getOutputStream());
	}

	@GetMapping("/sendCode")
	public Map<String,Object> sendCode(HttpServletRequest req) throws IOException
	{
		Map<String,Object> map = new HashMap<>();

		req.setCharacterEncoding("utf-8");
		String phone = req.getParameter("phone");
		if (phone == null || phone.trim().length() == 0)
		{
			map.put("phoneCode",0);
			map.put("msg","手机号码不能为空!!!");
			return map;
		}


		CodePo codePo = (CodePo) req.getSession().getAttribute("phoneCode");
		if (codePo != null) {
			if (codePo.getTime() > System.currentTimeMillis())
			{
				map.put("phoneCode",0);
				map.put("msg","验证码已经发送!!!");
				return map;
			}
		}

		System.out.println(phone);
		String sbs = "";
		for (int i = 0; i < 4; i++) {
			String s = (int)(Math.random()*10) + "";
			sbs += s;
		}
		System.out.println("发生短信验证成功: " + sbs);
		CodePo code = new CodePo(sbs, 30);
		req.getSession().setAttribute("phoneCode", code);

		map.put("phoneCode",1);
		map.put("msg","发生成功");
		return map;
	}

	@RequestMapping("/qqlogin")
	public void redirectQqUrl(String code,HttpServletRequest req) {

		System.out.println("=====================");
		//System.out.println(code); //577E996DAC841CF7AC791BBB8984F363
		//req.setCharacterEncoding("utf-8");
		String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=101780702&client_secret=be3cf79b89eafe3f0b8f90462aed8065&code="+ code +"&redirect_uri=http://www.pawntest.com/qqlogin";

		RestTemplate restTemplate = new RestTemplate();
		String template = restTemplate.getForObject(url, String.class);
		System.out.println(template); //access_token=CDF476B8D665C26ECE2F98D85A46772F&expires_in=7776000&refresh_token=AB5BAD5427B5597F627B3026A1F4BC62
		String token = template.substring(template.indexOf("=")+1,template.indexOf("&"));

		url = "https://graph.qq.com/oauth2.0/me?access_token="+ token;

		String result = restTemplate.getForObject(url, String.class);
		String openId = result.substring(result.lastIndexOf(":")+2,result.lastIndexOf("\""));
		System.out.println(openId);

		req.getSession().setAttribute("openId", openId);

		url = "https://graph.qq.com/user/get_user_info?access_token="+token+"&oauth_consumer_key=101780702&openid="+openId;
		result = restTemplate.getForObject(url, String.class);

	}

	/**
	 * 绑定 QQ 号码
	 */
	@RequestMapping("/bangdingQQ")
	public Map<String,Object> bangdingQQ(@RequestParam("username") String username,
										 @RequestParam("password") String password)
	{
		return this.userService.bangdingQQ(username,password);
	}
}
