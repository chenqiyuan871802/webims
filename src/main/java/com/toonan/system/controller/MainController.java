package com.toonan.system.controller;

import java.util.List;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.util.WebplusJson;
import com.toonan.core.vo.R;
import com.toonan.core.vo.TreeModel;
import com.toonan.core.vo.UserToken;
import com.toonan.core.web.BaseController;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.model.Menu;
import com.toonan.system.model.RoleUser;
import com.toonan.system.model.User;
import com.toonan.system.service.MenuService;
import com.toonan.system.service.RoleService;
import com.toonan.system.service.UserService;
import com.toonan.system.util.SystemCache;



/**
 * 
 * 类名:com.ims.system.controller.SystemController
 * 描述:系统控制类
 * 编写者:陈骑元
 * 创建时间:2018年5月4日 下午9:45:59
 * 修改说明:
 */
@Controller
@RequestMapping("/system/main")
public class MainController extends BaseController {
	
	 private String prefix = "system/main/"; 
	
	 @Autowired
     private UserService userService;
	 
	 
	 /**
		 * 
		 * 简要说明：初始化主页面
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@PostMapping("initMain")
		public String initMain(String menuType,String token,Model model) {
			UserToken userToken=this.getUserToken();
		    List<Menu> menuList=SystemCache.getMenuList(userToken);
			String menuJson=WebplusJson.toJson(menuList);
			model.addAttribute("user", userToken);
			model.addAttribute("menuJson", menuJson);
		    model.addAttribute("token", token);
			return prefix+"main";
		}
		/**
		 * 
		 * 简要说明：加菜卡片菜单
		 * 编写者：陈骑元
		 * 创建时间：2019年5月8日 下午1:50:28
		 * @param 说明
		 * @return 说明
		 */
		@PostMapping("loadCardMenu")
		@ResponseBody
		public R loadCardMenu(String menuType){
			UserToken user=this.getUserToken();
			String whetherSuper=WebplusCons.WHETHER_NO;
			if(SystemCons.SUPER_ADMIN.equals(user.getAccount())){
				whetherSuper=WebplusCons.WHETHER_YES;
			}
			List<TreeModel> cardMenuList=SystemCache.getCardMenu(user.getUserId(),menuType,whetherSuper);
	    	Set<String> grantSet=SystemCache.getAuthPermissions(user.getUserId(),whetherSuper);
			return R.toDataAndList(grantSet, cardMenuList);
		}
		/**
		 * 
		 * 简要说明：初始化主页面
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@GetMapping("mainIndex")
		public String mainIndex(Model model,String token) {
			
			return prefix + "mainIndex";
		}
		
		/**
		 * 
		 * 简要说明：解锁屏幕
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@PostMapping("unlockScreen")
		@ResponseBody
		public R unlockScreen(String password) {
			UserToken userToken=this.getUserToken();
			User user=userService.selectById(userToken.getUserId());
			if(user.getPassword().equals(password)){
				
				return R.ok();
			}
			return R.warn("登陆密码不正确，无法进行解锁");
		}


}
