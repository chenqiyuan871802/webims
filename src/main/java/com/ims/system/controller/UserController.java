package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSCodec;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.PageDto;
import com.ims.common.util.R;
import com.ims.common.util.SqlHelpUtil;

import java.util.List;

import com.ims.system.constant.SystemCons;
import com.ims.system.model.DictIndex;
import com.ims.system.model.User;
import com.ims.system.service.UserService;
import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

/**
 * <p>
 * 用户基本信息表 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-09-28
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    private String prefix = "system/user/"; 
    @Autowired
    private UserService userService;
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {

		return prefix + "userList";
	}

	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("list")
	@ResponseBody
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		pDto.put("isDel", IMSCons.IS.NO);
		pDto.setOrder(" a.create_time DESC ");
		Page<User> page =userService.listUserPage(pDto);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("add")
	public String add(String deptId,Model model) {
        model.addAttribute("deptId", deptId);
		return prefix + "addUser";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("save")
	@ResponseBody
	public R save(User user) {
		EntityWrapper<User> countWrapper = new EntityWrapper<User>();
		SqlHelpUtil.eq(countWrapper, "account", user.getAccount());
		SqlHelpUtil.eq(countWrapper, "is_del", IMSCons.IS.NO);
		int count=userService.selectCount(countWrapper);
		if(count>0){
			return R.warn("该账号已被注册，请注册其它账号。");
		}
		user.setCreateTime(IMSUtil.getDateTime());
		user.setUpdateTime(IMSUtil.getDateTime());
		user.setUserType(SystemCons.USER_TYPE_COMMON);
		String password=user.getPassword();
		String encryptPassword=IMSCodec.encrypt(password,IMSCons.PASSWORD_KEY);
		user.setPassword(encryptPassword);
		boolean result = userService.insert(user);
		if (result) {
			return R.ok();
		} else {
			return R.error("保存失败");
		}

	}
	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("edit")
	public String edit(String id,Model model) {
		User user=userService.selectById(id);
		model.addAttribute("user", user);
		return prefix + "editUser";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("update")
	@ResponseBody
	public R update(User user) {
		user.setUpdateTime(IMSUtil.getDateTime());
		boolean result = userService.updateById(user);
		if (result) {
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 跳转重置密码界面
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("resetPassword")
	public String resetPassword(String id,Model model) {
	
		model.addAttribute("userId", id);
		return prefix + "updatePassword";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("updatePassword")
	@ResponseBody
	public R updatePassword(String userId,String password) {
		String encryptPassword=IMSCodec.encrypt(password,IMSCons.PASSWORD_KEY);
		User user=new User();
		user.setUserId(userId);
		user.setPassword(encryptPassword);
		user.setUpdateTime(IMSUtil.getDateTime());
		boolean result = userService.updateById(user);
		if (result) {
			return R.ok("密码重置成功");
		} else {
			return R.error("密码重置失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 移动用户
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("moveUser")
	public String moveUser(String ids,Model model) {
		model.addAttribute("ids",ids);
		return prefix + "moveUser";
	}
	
	/**
	 * 
	 * 简要说明：移动用户
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("saveMoveUser")
	@ResponseBody
	public R saveMoveUser(String ids,String deptId) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		List<User> userList=Lists.newArrayList();
		for(String userId:idList){
			User user=new User();
			user.setUserId(userId);
			user.setDeptId(deptId);
			user.setUpdateTime(IMSUtil.getDateTime());
			userList.add(user);
		}
		boolean result=userService.updateBatchById(userList);
		if (result) {
			return R.ok("移动用户成功");
		} else {
			return R.error("移动用户失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 展示详情
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		User user=userService.selectById(id);
		model.addAttribute("user",user);
		return prefix + "showUser";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		boolean result = userService.deleteById(id);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：批量删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		List<User> userList=Lists.newArrayList();
		for(String userId:idList){
			User user=new User();
			user.setUserId(userId);
			user.setIsDel(IMSCons.IS.YES);
			user.setUpdateTime(IMSUtil.getDateTime());
			userList.add(user);
		}
		boolean result=userService.updateBatchById(userList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
}

