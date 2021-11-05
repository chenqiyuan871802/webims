package com.toonan.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusSqlHelp;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.R;
import com.toonan.core.vo.UserToken;
import com.toonan.core.web.BaseController;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.model.Param;
import com.toonan.system.service.ParamService;
import com.toonan.system.util.SystemCache;

/**
 * <p>
 * 键值参数 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-04-12
 */
@Controller
@RequestMapping("/system/param")
public class ParamController extends BaseController {
	
	private String prefix = "system/param/"; 
	
    @Autowired
    private ParamService paramService;
	
    /**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {
		return prefix + "paramList";
	}
	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("list")
	@ResponseBody
	public R list() {
		Dto pDto = Dtos.newDto(request);
		pDto.setOrder("create_time DESC");
		Page<Param> page =paramService.likePage(pDto);
		WebplusCache.convertItem(page);
		return R.toPage(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("add")
	public String add() {

		return prefix + "addParam";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:param:add")
	@PostMapping("save")
	@ResponseBody
	public R save(Param param) {
    	String userId=this.getUserId();
		EntityWrapper<Param> countWrapper = new EntityWrapper<Param>();
		WebplusSqlHelp.eq(countWrapper, "param_key", param.getParamKey());
		int count=paramService.selectCount(countWrapper);
		if(count>0){
			return R.warn("参数键已被占用，请修改其它参数键再保存。");
		}
		param.setCreateBy(userId);
		param.setUpdateBy(userId);
		param.setCreateTime(WebplusUtil.getDateTime());
		param.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = paramService.insert(param);
		if (result) {
			SystemCache.cacheParam(param.getParamKey());
			return R.ok();
		} else {
			return R.error("保存失败");
		}

	}

	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:param:edit")
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Param param=paramService.selectById(id);
		model.addAttribute("paramModel", param);
		return prefix + "editParam";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
   // @RequiresPermissions("system:param:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(Param param,String oldParamKey) {
    	String userId=this.getUserId();
		if(WebplusUtil.isNotEmpty(oldParamKey)){
			if(!oldParamKey.equals(param.getParamKey())){
				EntityWrapper<Param> countWrapper = new EntityWrapper<Param>();
				WebplusSqlHelp.eq(countWrapper, "param_key", param.getParamKey());
				int count=paramService.selectCount(countWrapper);
				if(count>0){
					return R.warn("参数键已被占用，请修改其它参数键再保存。");
				}
				SystemCache.removeCacheParam(oldParamKey);
			}
			
		}
		param.setUpdateBy(userId);
		param.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = paramService.updateById(param);
		if (result) {
			String paramKey=param.getParamKey();
			if(WebplusUtil.isEmpty(paramKey)){
				Param paramEntity=paramService.selectById(param.getParamId());
				paramKey=paramEntity.getParamKey();
			}
			SystemCache.cacheParam(paramKey);
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	

	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:param:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		Param param=paramService.selectById(id);
		UserToken userToken=this.getUserToken();
		if(WebplusCons.EDITMODE_READ.equals(param.getEditMode())
				&&!SystemCons.SUPER_ADMIN.equals(userToken.getAccount())){
			
			return R.warn("当前删除的键值参数数据为只读，只读的数据不能修改和删除");
		}
		boolean result = paramService.deleteById(id);
		if (result) {
			SystemCache.removeCacheParam(param.getParamKey());
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	/**
	 * 
	 * 简要说明：刷新键值参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:param:refreshCache")
	@PostMapping("refreshParam")
	@ResponseBody
	public R refreshParam() {
		SystemCache.refreshParam();
	    
		return R.ok("刷新键值参数缓存操作成功");
	}
	
	
}

