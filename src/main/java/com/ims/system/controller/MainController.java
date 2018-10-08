package com.ims.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ims.common.util.JsonUtil;
import com.ims.common.web.BaseController;
import com.ims.system.constant.SystemCons;
import com.ims.system.model.Menu;
import com.ims.system.model.RoleUser;
import com.ims.system.model.User;
import com.ims.system.service.MenuService;
import com.ims.system.service.RoleService;

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
	 private MenuService menuService;
	 @Autowired
	 private RoleService roleService;
	 
	 
	 /**
		 * 
		 * 简要说明：初始化主页面
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@GetMapping("initMain")
		public ModelAndView initMain(HttpSession session) {
			ModelAndView modelAndView=new ModelAndView();
			User user=this.getUserInfo(session);
			EntityWrapper<Menu> menuWrapper= new EntityWrapper<Menu>();
			List<String> menuTypeList=Lists.newArrayList();
			menuTypeList.add(SystemCons.MENU_TYPE_SUB);
			menuTypeList.add(SystemCons.MENU_TYPE_PARENT);
			menuWrapper.in("menu_type", menuTypeList);
			menuWrapper.orderBy("LENGTH(cascade_id) ASC,sort_no ASC ");
			if(!SystemCons.SUPER_ADMIN.equals(user.getAccount())){ //不是超级用户
				 String userId=user.getUserId();
				 RoleUser roleUser=roleService.selectRoleUser(userId);
				 menuWrapper.andNew(" EXISTS (SELECT 1 FROM SYS_ROLE_MENU m WHERE m.menu_id=sys_menu.menu_id AND m.role_id={0})",roleUser.getRoleId());
			}
			List<Menu> menuList=menuService.selectList(menuWrapper);
			String menuJson=JsonUtil.toJson(menuList);
			 modelAndView.addObject("user", user);
			modelAndView.addObject("menuJson", menuJson);
			modelAndView.setViewName(prefix + "main");
			return modelAndView;
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
		public String mainIndex() {
			
			return prefix + "mainIndex";
		}
		
		


}
