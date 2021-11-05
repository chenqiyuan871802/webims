package com.toonan.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.toonan.core.util.WebplusJson;
import com.toonan.core.util.WebplusSqlHelp;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.EasyuiTreeModel;
import com.toonan.core.vo.Item;
import com.toonan.core.vo.R;
import com.toonan.core.vo.TreeModel;
import com.toonan.core.vo.UserToken;
import com.toonan.core.web.BaseController;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.model.Dept;
import com.toonan.system.service.DeptService;

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
    
	@RequestMapping(value = "loadDeptTree")
	@ResponseBody
	public List<EasyuiTreeModel> loadDeptTree(String whetherGrant,HttpServletRequest request, HttpServletResponse response) {
		Dto pDto=Dtos.newDto(request);
	    UserToken userToken=WebplusCache.getUserToken(pDto);
		EasyuiTreeModel treeModel=deptService.loadDeptTree(pDto,WebplusCons.WHETHER_NO);
		if(WebplusCons.WHETHER_YES.equals(whetherGrant)&&!WebplusCons.SUPER_ADMIN.equals(userToken.getAccount())) {
			List<EasyuiTreeModel> dataList=treeModel.getChildren();
			if(WebplusUtil.isNotEmpty(dataList)) {
				
				return dataList;
			}
			 dataList=Lists.newArrayList();
			 dataList.add(treeModel);
			return dataList;
		}else {
			List<EasyuiTreeModel> dataList=Lists.newArrayList();
			dataList.add(treeModel);
			return dataList;
		}
		
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
	public R list() {
		Dto pDto = Dtos.newDto(request);
		pDto.put("isDel", WebplusCons.WHETHER_NO);
		pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
		Page<Dept> page =deptService.likePage(pDto);
		WebplusCache.convertItem(page);
		return R.toPage(page);
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
	//@RequiresPermissions("system:dept:add")
	@PostMapping("save")
	@ResponseBody
	public R save(Dept dept) {
		String userId=this.getUserId();
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", dept.getParentId());
		String maxCascadeId =deptService.calc(calcDto);
		if(WebplusUtil.isEmpty(maxCascadeId)){
			Dept parentDept=deptService.selectById(dept.getParentId());
			if(WebplusUtil.isEmpty(parentDept)){
					maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentDept.getCascadeId()+".0000";
			}
				
		}
		String curCascadeId=WebplusUtil.createCascadeId(maxCascadeId, 9999);
		dept.setCascadeId(curCascadeId);
		dept.setCreateBy(userId);
		dept.setUpdateBy(userId);
		dept.setCreateTime(WebplusUtil.getDateTime());
		dept.setUpdateTime(WebplusUtil.getDateTime());
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
	//@RequiresPermissions("system:dept:edit")
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
	//@RequiresPermissions("system:dept:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(Dept dept) {
		String userId=this.getUserId();
		dept.setUpdateTime(WebplusUtil.getDateTime());
		dept.setUpdateBy(userId);
		boolean result = deptService.updateById(dept);
		if (result) {
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 移动组织机构
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("move")
	public String move(String deptId,Model model) {
		Dept dept=deptService.selectById(deptId);
		model.addAttribute("dept", dept);
		return prefix + "moveDept";
	}
	/**
	 * 
	 * 简要说明：保存移动机构信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:dept:move")
	@PostMapping("saveMoveDept")
	@ResponseBody
	public R saveMoveDept(Dept dept) {
		boolean result = deptService.updateDept(dept);
		if (result) {
			return R.ok("移动机构成功");
		} else {
			return R.error("移动机构失败");
		}
		
	}
	
	
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:dept:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		String userId=this.getUserId();
		EntityWrapper<Dept> wrapper = new EntityWrapper<Dept>();
		WebplusSqlHelp.eq(wrapper, "parent_id", id);
		WebplusSqlHelp.eq(wrapper, "is_del", WebplusCons.WHETHER_NO);
		int row=deptService.selectCount(wrapper);
		if(row>0){
			return R.warn("操作失败，当前组织机构下存在子机构，不允许删除，请先删除子机构然后再删除。");
		}
		Dept dept=new Dept();
		dept.setDeptId(id);
		dept.setUpdateBy(userId);
		dept.setIsDel(WebplusCons.WHETHER_YES);
		dept.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = deptService.updateById(dept);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	 /**
     * 
     * 简要说明：同步来穗行政区地址
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年5月11日 上午9:59:37 
     * @param 说明
     * @return 说明
     */
    @PostMapping("syncXzdz")
	@ResponseBody
    public R syncXzdz() {
    	String userId=this.getUserId();
    	List<Dept> deptList=Lists.newArrayList();
    	Date now=WebplusUtil.getDateTime();
    	String xzq=WebplusCache.getParamValue(WebplusCons.XZQ_CODE_KEY);
    	String xzqName=WebplusCache.getParamValue(WebplusCons.XZQ_NAME_KEY);
    	String xzqCascadeId=deptService.getCurCascadeId(SystemCons.TREE_ROOT_ID);
    	Dept xzqDept = new Dept();
		xzqDept.setDeptId(xzq);
		xzqDept.setDeptCode(xzq);
		xzqDept.setParentId(SystemCons.TREE_ROOT_ID);
		xzqDept.setDeptName(xzqName);
		xzqDept.setCascadeId(xzqCascadeId);
		xzqDept.setDeptType(WebplusCons.DEPT_TYPE_XZQ);
		xzqDept.setIsDel(WebplusCons.WHETHER_NO);
		xzqDept.setIsAutoExpand(WebplusCons.WHETHER_YES);
		xzqDept.setSortNo(1);
		xzqDept.setCreateBy(userId);
		xzqDept.setCreateTime(now);
		xzqDept.setUpdateBy(userId);
		xzqDept.setUpdateTime(now);
		deptList.add(xzqDept);
    	List<Item> itemList=LsApiUtil.queryJzListByLs(xzq, "");
    	for(int i=0;i<itemList.size();i++) {
    	   Item jzItem=itemList.get(i);
    	    
			Dept jzDept=this.getDept(jzItem, i+1, xzqCascadeId, WebplusCons.DEPT_TYPE_JZ, userId);
			deptList.add(jzDept);
			List<Dept> jwhDeptList=this.syscLsJwh(jzDept);
			deptList.addAll(jwhDeptList);
    	}
    	if(WebplusUtil.isNotEmpty(deptList)) {
    		List<String> deptTypeList=Lists.newArrayList();
        	deptTypeList.add(WebplusCons.DEPT_TYPE_XZQ);
        	deptTypeList.add(WebplusCons.DEPT_TYPE_JZ);
        	deptTypeList.add(WebplusCons.DEPT_TYPE_JWH);
        	EntityWrapper<Dept> wrapper=new EntityWrapper<Dept>();
        	wrapper.in("dept_type", deptTypeList);
        	deptService.delete(wrapper);
        	deptService.insertBatch(deptList);
        	this.refreshXzdzCache(deptList);
        	return R.ok("同步成功");
    	}
    	
        
    	return R.warn("同步行政区域失败");
    }
    /**
     * 
     * 简要说明：组装组织机构
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年10月22日 下午11:46:15 
     * @param 说明
     * @return 说明
     */
    public Dept getDept(Item item,int sortNo,String parentCascadeId,String deptType,String userId) {
    	Dept dept = new Dept();
	    Date now=WebplusUtil.getDateTime();
		String seq= StringUtils.leftPad((sortNo+1)+"", 4, "0");
		String jzCascadeId=parentCascadeId+"."+seq;
		dept.setDeptId(item.getItemCode());
		dept.setDeptCode(item.getItemCode());
		dept.setParentId(item.getTypeCode());
		dept.setDeptName(item.getItemName());
		dept.setCascadeId(jzCascadeId);
		dept.setIsDel(WebplusCons.WHETHER_NO);
		dept.setIsAutoExpand(WebplusCons.WHETHER_NO);
		dept.setDeptType(deptType);
		dept.setSortNo(sortNo);
		dept.setCreateBy(userId);
		dept.setCreateTime(now);
		dept.setUpdateBy(userId);
		dept.setUpdateTime(now);
		
		return dept;
    }
	/**
	 * 
	 * 简要说明：同步居委会
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月22日 下午11:20:02 
	 * @param 说明
	 * @return 说明
	 */
    public List<Dept> syscLsJwh(Dept jzDept){
    	List<Dept> deptList=Lists.newArrayList();
    	String jz=jzDept.getDeptId();
    	List<Item> itemList=LsApiUtil.queryJwhListByLs("", jz, "");
    	for(int i=0;i<itemList.size();i++) {
    		Item jwhItem=itemList.get(i);
    		Dept jwhDept=this.getDept(jwhItem, i+1, jzDept.getCascadeId(), WebplusCons.DEPT_TYPE_JWH, jzDept.getUpdateBy());
    	    deptList.add(jwhDept);
    	}
    	return deptList;
    }
    
    
    /**
	  *
	  * 简要说明：刷新行政地址缓存
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年4月22日 上午10:06:45
	  * @param 说明
	  * @return 说明
	  */
	 public void  refreshXzdzCache(List<Dept> deptList) {
		 
		Map<String,String> hashMap=new HashMap<String,String>();
		 for(Dept dept:deptList) {
			 Item item=new Item();
			 String itemCode=dept.getDeptId();
			 item.setItemCode(itemCode);
			 item.setItemName(dept.getDeptName());
			 item.setTypeCode(dept.getParentId());
			 hashMap.put(itemCode, WebplusJson.toJson(item));
		 }
		 String key=WebplusCons.CACHE_PREFIX.XZDZALL;
		 WebplusCache.delString(key);
		 WebplusCache.hmset(key,hashMap);
	 }
	 
	 
	 /**
		 * 
		 * 简要说明：加载所在区域
		 * 编写者：陈骑元
		 * 创建时间：2018-05-14
		 * @param 说明
		 * @return 说明
		 */
		@PostMapping("listDeptArea")
		@ResponseBody
		public R listDeptArea(Dept dept) {
			Dto pDto=Dtos.newDto();
			List<String>	 deptTypeList=Lists.newArrayList();
			deptTypeList.add(WebplusCons.DEPT_TYPE_XZQ);
			deptTypeList.add(WebplusCons.DEPT_TYPE_JZ);
			pDto.put("deptTypeList", deptTypeList);
			pDto.put("isDel", WebplusCons.WHETHER_NO); // 查询有效的组织机构信息
			pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
			List<Dept> deptList=deptService.list(pDto); 
			
			return R.toList(deptList);
		}
		
		/**
		 * 
		 * 简要说明：刷新系统缓存
		 * 编写者：陈骑元
		 * 创建时间：2018年5月13日 下午11:09:04
		 * @param 说明
		 * @return 说明
		 */
		@PostMapping("refreshCache")
		@ResponseBody
		public R refreshCache() {
			Dto pDto=Dtos.newDto();
			pDto.put("isDel", WebplusCons.WHETHER_NO); // 查询有效的组织机构信息
			pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
			List<Dept> deptList=deptService.list(pDto); 
			this.refreshXzdzCache(deptList);
			return R.ok("刷新机构缓存成功");
		}
		
		
		/**
		 * 
		 * 简要说明：加载用户权限树
		 * 编写者：陈骑元（chenqiyuan@toonan.com）
		 * 创建时间： 2020年10月25日 上午11:44:00 
		 * @param 说明 grantDeptId授权机构编号
		 * @return 说明
		 */
		@RequestMapping("listGrantTree")
		@ResponseBody
		public List<EasyuiTreeModel> listGrantTree(String grantDeptId) {
			
			Dto pDto=Dtos.newDto();
			pDto.put("grantDeptId", grantDeptId);
			EasyuiTreeModel treeModel=deptService.loadDeptTree(pDto,WebplusCons.WHETHER_YES);
			List<EasyuiTreeModel> dataList=Lists.newArrayList();
			dataList.add(treeModel);
			return dataList;
			
			
		}
	
}

