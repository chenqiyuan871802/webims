package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSCxt;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.JsonUtil;
import com.ims.common.util.PageDto;
import com.ims.common.util.R;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ims.system.model.Dept;
import com.ims.system.model.TreeModel;
import com.ims.system.service.DeptService;
import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-14
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    private String prefix = "system/dept/"; 
    @Autowired
    private DeptService deptService;
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {

		return prefix + "deptList";
	}
	/**
	 * 
	 * 加载组织机构树
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "loadTree")
	public void loadDeptTree(HttpServletRequest request, HttpServletResponse response) {
		Dto pDto=Dtos.newDto(request);
		TreeModel treeModel=deptService.loadDeptTree(pDto);
		String outString ="["+JsonUtil.toJson(treeModel)+"]";
		IMSCxt.write(response, outString);
	}
	
	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("list")
	@ResponseBody
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		pDto.put("is_del", IMSCons.IS.NO);
		pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
		Page<Dept> page =deptService.likePage(pDto);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("add")
	public String add(String parentId,Model model) {
		model.addAttribute("parentId", parentId);
		return prefix + "addDept";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("save")
	@ResponseBody
	public R save(Dept dept) {
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", dept.getParentId());
		String maxCascadeId =deptService.calc(calcDto);
		if(IMSUtil.isEmpty(maxCascadeId)){
			Dept parentDept=deptService.selectById(dept.getParentId());
			if(IMSUtil.isEmpty(parentDept)){
					maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentDept.getCascadeId()+".0000";
			}
				
		}
		String curCascadeId=IMSUtil.createCascadeId(maxCascadeId, 9999);
		dept.setCascadeId(curCascadeId);
		dept.setCreateTime(IMSUtil.getDateTime());
		dept.setUpdateTime(IMSUtil.getDateTime());
		boolean result = deptService.insert(dept);
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
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Dept dept=deptService.selectById(id);
		model.addAttribute("dept", dept);
		return prefix + "editDept";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("update")
	@ResponseBody
	public R update(Dept dept) {
		boolean result = deptService.updateDept(dept);
		if (result) {
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明： 展示详情
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Dept dept=deptService.selectById(id);
		model.addAttribute("dept",dept);
		return prefix + "showDept";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		boolean result = deptService.deleteById(id);
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
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		boolean result = deptService.deleteBatchIds(idList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
}

