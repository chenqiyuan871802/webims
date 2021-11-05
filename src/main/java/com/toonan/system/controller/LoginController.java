package com.toonan.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.lsapi.LsApiUtil;
import com.toonan.core.matatype.Dto;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.R;
import com.toonan.core.vo.UserToken;
import com.toonan.core.web.BaseController;
import com.toonan.system.model.User;
import com.toonan.system.service.UserService;



/**
 * 
 * 类名:com.toonan.system.controller.LoginController
 * 描述:登录控制类
 * 编写者:陈骑元
 * 创建时间:2018年12月15日 下午1:57:09
 * 修改说明:
 */
@Controller
@RequestMapping(value= {"/","/login"})
public class LoginController extends BaseController{
	
	
	 @Autowired
	 private UserService userService;
	/*
	 *//**
	  * 
	  * 简要说明：登录首页
	  * 编写者：陈骑元
	  * 创建时间：2019年1月9日 上午9:45:40
	  * @param 说明
	  * @return 说明
	  */
	 
	 @GetMapping({ "/", "index" })
     public String initLogin(String accessToken,Model model) {
		    model.addAttribute("accessToken", accessToken);
			return "login";
     }
	  
	
	
	/**
	 *
	 * 简要说明：开始登陆
	 * 编写者：陈骑元 
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("doLogin")
	@ResponseBody
	public R doLogin(String account,String password) {
		
		R r=userService.doLogin(account, password,WebplusCons.WHETHER_YES,"");
		
		return r;
	}
	
	/**
	 * 
	 * 简要说明：第三方单点登录
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年1月21日 下午3:18:05 
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("thirdLogin")
	@ResponseBody
	public R thirdLogin(String accessToken) {
		if(WebplusUtil.isEmpty(accessToken)) {
			
			return R.warn("第三方登录令牌不能为空");
		}
	    UserToken userToken=WebplusCache.getUserToken(accessToken);
	    if(WebplusUtil.isNotEmpty(userToken)) {
	    	
	    	return R.ok("登陆成功").put("userToken", userToken);
	    }
	    Dto userDto=LsApiUtil.getAccessTokenUser(accessToken);
	    if(WebplusUtil.isEmpty(userDto)) {
	    	
	    	return R.warn("第三方令牌校验不合法");
	    }
	    String userId=userDto.getString("czyid");
	    if(WebplusUtil.isEmpty(userId)) {
	    	return R.warn("第三方令牌校验不合法");
	    }
	    User user=userService.selectById(userId);
	    if(WebplusUtil.isEmpty(user)) {
	    	
	    	return R.warn("第三方令牌校验不合法,无法找到映射用户");
	    }
	    return userService.doLogin(user.getAccount(), user.getPassword(),WebplusCons.WHETHER_YES,accessToken);
		
	}
	
	/**注销并安全退出系统
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@GetMapping(value = "logout")
	public  String logout(String token) {
		WebplusCache.removeToken(token);
		return "login";
	}
	

	/**
	 * 
	 * 简要说明：403未授权
	 * 编写者：陈骑元
	 * 创建时间：2019年1月9日 上午11:27:43
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("/401")
	String error401() {
		return "forward:error/401.html";
	}
	
}
