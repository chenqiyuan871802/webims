package com.ims.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ims.common.web.BaseController;

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
	 
	 
	 /**
		 * 
		 * 简要说明：初始化主页面
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@GetMapping("initMain")
		public String initMain() {

			return prefix + "main";
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
