package com.toonan.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.lsapi.LsApiUtil;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusFormater;
import com.toonan.core.util.WebplusHashCodec;
import com.toonan.core.util.WebplusJson;
import com.toonan.core.util.WebplusSqlHelp;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.Item;
import com.toonan.core.vo.R;
import com.toonan.core.web.BaseController;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.model.Dept;
import com.toonan.system.model.LsUser;
import com.toonan.system.model.User;
import com.toonan.system.service.DeptService;
import com.toonan.system.service.UserService;

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
    @Autowired
    private DeptService deptService;
	
   
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
	public R list() {
		Dto pDto = Dtos.newDto(request);
		//设置数据权限范围
		WebplusCache.setDataRange(pDto);
		pDto.put("isDel", WebplusCons.WHETHER_NO);
		pDto.setOrder(" a.create_time DESC ");
		Page<User> page =userService.listUserPage(pDto);
		WebplusCache.convertItem(page);
		return R.toPage(page);
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
    //@RequiresPermissions("system:user:add")
	@PostMapping("save")
	@ResponseBody
	public R save(User user) {
    	String userId=this.getUserId();
		EntityWrapper<User> countWrapper = new EntityWrapper<User>();
		WebplusSqlHelp.eq(countWrapper, "account", user.getAccount());
		WebplusSqlHelp.eq(countWrapper, "is_del",WebplusCons.WHETHER_NO);
		int count=userService.selectCount(countWrapper);
		if(count>0){
			return R.warn("该账号已被注册，请注册其它账号。");
		}
		
		this.setDataRange(user);
		user.setCreateBy(userId);
		user.setUpdateBy(userId);
		user.setCreateTime(WebplusUtil.getDateTime());
		user.setUpdateTime(WebplusUtil.getDateTime());
		user.setUserType(SystemCons.USER_TYPE_COMMON);
		String password=user.getPassword();
		String encryptPassword=WebplusHashCodec.md5(password);
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
	 * 简要说明：设置地域范围
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年9月17日 上午10:16:11 
	 * @param 说明
	 * @return 说明
	 */
	public void setDataRange(User user) {
		String deptId=user.getDeptId();
		Dept dept=deptService.selectById(deptId);
		String deptType=dept.getDeptType();
		String dataRange=user.getDataRange();
		user.setDyfw(deptId);
		String dymc=WebplusCons.DYMC_XZQ;
		String xzq=WebplusCache.getParamValue(WebplusCons.XZQ_CODE_KEY);
		user.setXzq(xzq);
		if(WebplusCons.DEPT_TYPE_JZ.equals(deptType)) {
		
			user.setJz(deptId);
			dymc=WebplusCons.DYMC_JZ;
		}else if(WebplusCons.DEPT_TYPE_JWH.equals(deptType)) {
			dymc=WebplusCons.DYMC_JWH;
			user.setJz(dept.getParentId());
			user.setJwh(deptId);
		}
		user.setDymc(dymc);
		if(deptId.equals(dataRange)) {
			
			user.setDataRange(WebplusCons.ALL);
		}else {
			List<String> rangeList=WebplusFormater.separatStringToList(dataRange);
			rangeList=WebplusUtil.removeStrByList(deptId, rangeList);
			dataRange=WebplusFormater.toString(rangeList);
			user.setDataRange(dataRange);
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
   // @RequiresPermissions("system:user:edit")
	@GetMapping("edit")
	public String edit(String id,Model model) {
		User user=userService.selectById(id);
		String dataRange=user.getDataRange();
		if(WebplusUtil.isEmpty(dataRange)||WebplusCons.ALL.equals(dataRange)) {
			user.setDataRange(user.getDeptId());
		}
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
    //@RequiresPermissions("system:user:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(User user) {
    	String userId=this.getUserId();
		user.setUpdateTime(WebplusUtil.getDateTime());
		if(WebplusCons.ENABLED_YES.equals(user.getStatus())){
			user.setErrorNum(0);
		}
		this.setDataRange(user);
		user.setUpdateBy(userId);
		user.setUpdateTime(WebplusUtil.getDateTime());
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
		
		String updateBy=this.getUserId();
		if(WebplusUtil.isEmpty(userId)){  //如果用户编号为空，则是修改自己的用户密码
			userId=updateBy;
		}
		String encryptPassword=WebplusHashCodec.md5(password);
		User user=new User();
		user.setUserId(userId);
		user.setPassword(encryptPassword);
		user.setUpdateBy(updateBy);
		user.setUpdateTime(WebplusUtil.getDateTime());
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
	//@RequiresPermissions("system:user:move")
	@PostMapping("saveMoveUser")
	@ResponseBody
	public R saveMoveUser(String ids,String deptId) {
		String updateBy=this.getUserId();
		List<String> idList=WebplusFormater.separatStringToList(ids);
		List<User> userList=Lists.newArrayList();
		for(String userId:idList){
			User user=new User();
			user.setUserId(userId);
			user.setDeptId(deptId);
			user.setUpdateBy(updateBy);
			user.setUpdateTime(WebplusUtil.getDateTime());
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
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:user:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		String updateBy=this.getUserId();
		User user=new User();
		user.setUserId(id);
		user.setIsDel(WebplusCons.WHETHER_YES);
		user.setUpdateBy(updateBy);
		user.setUpdateTime(WebplusUtil.getDateTime());
		boolean result=userService.updateById(user);
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
	//@RequiresPermissions("system:user:remove")
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		String updateBy=this.getUserId();
		List<String> idList=WebplusFormater.separatStringToList(ids);
		List<User> userList=Lists.newArrayList();
		for(String userId:idList){
			User user=new User();
			user.setUserId(userId);
			user.setIsDel(WebplusCons.WHETHER_YES);
			user.setUpdateBy(updateBy);
			user.setUpdateTime(WebplusUtil.getDateTime());
			userList.add(user);
		}
		boolean result=userService.updateBatchById(userList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	/**
	 * 
	 * 简要说明：同步来穗用户
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月24日 下午11:56:14 
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("syncLsUser")
	@ResponseBody
	public R syncLsUser() {
		String loginUserId=this.getUserId();
		String xzq=WebplusCache.getParamValue(WebplusCons.XZQ_CODE_KEY);
		List<Dto> lsUserList=LsApiUtil.listUser(xzq, "", "");
		this.saveLsUser(lsUserList, loginUserId);
		return R.ok("同步来穗用户成功");
	}
	
	/**
	 * 
	 * 简要说明：保存用户信息
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月25日 上午12:05:46 
	 * @param 说明
	 * @return 说明
	 */
	public void saveLsUser(List<Dto> userList,String loginUserId) {
		List<User> insertUserList=Lists.newArrayList();
		List<User> updateUserList=Lists.newArrayList();
		Date now=WebplusUtil.getDateTime();
		for(Dto  userDto:userList) {
			LsUser lsUser=new LsUser();
			WebplusUtil.copyProperties(userDto, lsUser);
			if(WebplusUtil.isNotEmpty(lsUser)) {
				String czyid=lsUser.getCzyid();
				String xzq=lsUser.getXzq();
				String jz=lsUser.getJz();
				String jwh=lsUser.getJwh();
				User user=new User();
				user.setUserId(czyid);
				user.setAccount(lsUser.getDlm());
				user.setPassword(lsUser.getDlkl());
				user.setUsername(lsUser.getXm());
				user.setIdno(lsUser.getZjhm());
				user.setMobile(lsUser.getLxdh());
				user.setSex(lsUser.getXb());
				user.setAddress(lsUser.getLxdz());
				user.setXzq(xzq);
				user.setJz(jz);
				user.setJwh(jwh);
				user.setDyfw(lsUser.getDyfw());
				user.setDymc(lsUser.getDymc());
				user.setDataRange(lsUser.getDataScale());
				if(WebplusUtil.isNotEmpty(jwh)) {
					user.setDeptId(jwh);
				}else {
					if(WebplusUtil.isNotEmpty(jz)) {
						user.setDeptId(jz);
					}else {
						user.setDeptId(xzq);
						
					}
				}
				user.setUserType(SystemCons.USER_TYPE_LS);
				user.setUpdateBy(loginUserId);
				user.setUpdateTime(now);
				User entity=userService.selectById(czyid);
				if(WebplusUtil.isNotEmpty(entity)) {
					
				    User updateUser=new User();
				    updateUser.setUserId(czyid);
				    updateUser.setPassword(lsUser.getDlkl());
				    updateUser.setUpdateBy(loginUserId);
				    updateUser.setUpdateTime(now);
					updateUserList.add( updateUser);
				}else {
				
					user.setCreateBy(loginUserId);
					user.setCreateTime(now);
					insertUserList.add(user);
				}
			}
		
		}
		if(WebplusUtil.isNotEmpty(insertUserList)) {
			
			userService.insertBatch(insertUserList);
		}
        if(WebplusUtil.isNotEmpty(updateUserList)) {
			
			userService.updateBatchById(updateUserList);
		}
	}
	
	/**
	 * 
	 * 简要说明：刷新系统缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:user:refreshCache")
	@PostMapping("refreshCache")
	@ResponseBody
	public R refreshCache() {
		EntityWrapper<User> wrapper=new EntityWrapper<User>();
		List<User> userList=userService.selectList(wrapper);
		Map<String,String> hashMap=new HashMap<String,String>();
		 for(User user:userList) {
			 Item item=new Item();
			 String itemCode=user.getUserId();
			 item.setItemCode(itemCode);
			 item.setItemName(user.getUsername());
			 item.setTypeCode(WebplusCons.SYSUSER);
			 hashMap.put(itemCode, WebplusJson.toJson(item));
		 }
		 String key=WebplusCons.CACHE_PREFIX.SYSUSER;
		 WebplusCache.delString(key);
		 WebplusCache.hmset(key,hashMap);
		return R.ok("刷新用户缓存操作成功");
	}
}

