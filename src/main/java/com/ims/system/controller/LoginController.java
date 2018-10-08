package com.ims.system.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ims.common.constant.IMSCons;
import com.ims.common.util.R;
import com.ims.common.web.BaseController;
import com.ims.system.service.UserService;


/**
 * 
 * 类名:com.ims.system.controller.LoginController 描述:登陆控制 编写者:陈骑元 创建时间:2018年10月7日
 * 下午7:29:35 修改说明:
 */
@Controller
@RequestMapping("/system/login")
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * 简要说明：初始化登陆页面 编写者：陈骑元 创建时间：2018-05-01
	 * 
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("goLogin")
	public String goLogin() {

		return "login";
	}

	/**
	 * 
	 * 简要说明：用户登陆验证
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("doLogin")
	@ResponseBody
	public R doLogin(String account,String password,HttpSession session) {
		R r=userService.checkLogin(account, password);
		if(IMSCons.SUCCESS==r.getAppCode()){
			session.setAttribute(IMSCons.USERINFOKEY, r.get("user")); //存入Session
		}
		return r;
	}
	/**注销并安全退出系统
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "loginout")
	public  String loginout(HttpSession session) {
		session.invalidate(); //注销退出
		return "login";
	}
}
