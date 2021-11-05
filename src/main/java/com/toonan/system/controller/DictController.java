package com.toonan.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusFormater;
import com.toonan.core.util.WebplusSqlHelp;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.Query;
import com.toonan.core.vo.R;
import com.toonan.core.web.BaseController;
import com.toonan.system.model.Dict;
import com.toonan.system.model.DictIndex;
import com.toonan.system.service.DictIndexService;
import com.toonan.system.service.DictService;
import com.toonan.system.util.SystemCache;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-01
 */
@Controller
@RequestMapping("/system/dict")
public class DictController extends BaseController {
	
	private String prefix = "system/dict/"; 
    @Autowired
    private DictService dictService;
    @Autowired
    private DictIndexService dictIndexService;
	
    /**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {

		return prefix + "dictList";
	}
	/**
	 * 
	 * 简要说明：分页查询 字典类型
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("listDictIndex")
	@ResponseBody
	public R listDictIndex() {
		Dto pDto = Dtos.newDto(request);
		String queryParam=pDto.getString("queryParam");
		Query<DictIndex> query=new Query<DictIndex>(pDto);
		EntityWrapper<DictIndex> wrapper = new EntityWrapper<DictIndex>();
	    WebplusSqlHelp.eq(wrapper, "dict_type", pDto,"dictType");
		if(WebplusUtil.isNotEmpty(queryParam)){
			wrapper.and("dict_key like {0}", "%"+queryParam+"%").or("dict_name like {0}", "%"+queryParam+"%");
		}
		wrapper.orderBy("create_time", false);
		Page<DictIndex> page=dictIndexService.selectPage( query,wrapper);
		WebplusCache.convertItem(page);
		return R.toPage(page);
	}
	
	/**
	 * 
	 * 简要说明：分页查询 字典类型
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("listDict")
	@ResponseBody
	public R listDict() {
		Dto pDto = Dtos.newDto(request);
		Query<Dict> query=new Query<Dict>(pDto);
		EntityWrapper<Dict> wrapper = new EntityWrapper<Dict>();
		WebplusSqlHelp.eq(wrapper, "dict_index_id", pDto,"dictIndexId");
		WebplusSqlHelp.like(wrapper, "dict_code", pDto,"dictCode");
		WebplusSqlHelp.like(wrapper, "dict_value", pDto,"dictValue");
		wrapper.orderBy("sort_no", true);
		Page<Dict> page =dictService.selectPage(query,wrapper);
		//转换字典标签
		WebplusCache.convertItem(page);
		return R.toPage(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("addDictIndex")
	public String addDictIndex() {

		return prefix + "addDictIndex";
	}
	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("saveDictIndex")
	@ResponseBody
	public R saveDictIndex(DictIndex dictIndex) {
    	String userId=this.getUserId();
		EntityWrapper<DictIndex> countWrapper = new EntityWrapper<DictIndex>();
		WebplusSqlHelp.eq(countWrapper, "dict_key", dictIndex.getDictKey());
		int count=dictIndexService.selectCount(countWrapper);
		if(count>0){
			return R.warn("字典标识已被占用，请修改其它字典标识再保存。");
		}
		dictIndex.setCreateBy(userId);
		dictIndex.setUpdateBy(userId);
		dictIndex.setCreateTime(WebplusUtil.getDateTime());
		dictIndex.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = dictIndexService.insert(dictIndex);
		if (result) {
			return R.ok();
		} else {
			return R.error("保存失败");
		}

	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("addDict")
	public ModelAndView addDict(String id ) {
		ModelAndView modelAndView=new ModelAndView();
		DictIndex dictIndex=dictIndexService.selectById(id);
		modelAndView.addObject("dictIndex", dictIndex);
		modelAndView.setViewName(prefix + "addDict");
		return modelAndView;
	}
	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
   // @RequiresPermissions("system:dict:add")
	@PostMapping("saveDict")
	@ResponseBody
	public R saveDict(Dict dict) {
    	String userId=this.getUserId();
		EntityWrapper<Dict> countWrapper = new EntityWrapper<Dict>();
		WebplusSqlHelp.eq(countWrapper, "dict_index_id", dict.getDictIndexId());
		WebplusSqlHelp.eq(countWrapper, "dict_code", dict.getDictCode());
		int count=dictService.selectCount(countWrapper);
		if(count>0){
			return R.warn("字典对照码已被占用，请修改其它字典对照码再保存。");
		}
		dict.setCreateBy(userId);
		dict.setUpdateBy(userId);
		dict.setCreateTime(WebplusUtil.getDateTime());
		dict.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = dictService.insert(dict);
		if (result) {
			SystemCache.cacheDict(dict.getDictKey()); //刷新字典
			return R.ok();
		} else {
			return R.error("保存失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
   // @RequiresPermissions("system:dict:edit")
	@GetMapping("editDictIndex")
	public String editDictIndex(String id,Model model) {
		DictIndex dictIndex=dictIndexService.selectById(id);
		model.addAttribute("dictIndex", dictIndex);
		return prefix + "editDictIndex";
	}
	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
   // @RequiresPermissions("system:dict:edit")
	@GetMapping("editDict")
	public String editDict(String id,Model model) {
		Dict dict=dictService.selectById(id);
		DictIndex dictIndex=dictIndexService.selectById(dict.getDictIndexId());
		model.addAttribute("dictIndex", dictIndex);
		model.addAttribute("dict", dict);
		return prefix + "editDict";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:dict:edit")
	@PostMapping("updateDictIndex")
	@ResponseBody
	public R updateDictIndex(DictIndex dictIndex) {
    	String userId=this.getUserId();
		DictIndex dictIndexOld=dictIndexService.selectById(dictIndex.getDictIndexId());
		String oldDictKey=dictIndexOld.getDictKey();
		if(!oldDictKey.equals(dictIndex.getDictKey())){ //如果新的字典对照码改变，则进行校验
			EntityWrapper<DictIndex> countWrapper = new EntityWrapper<DictIndex>();
			WebplusSqlHelp.eq(countWrapper, "dict_key", dictIndex.getDictKey());
			int count=dictIndexService.selectCount(countWrapper);
			if(count>0){
				return R.warn("字典对照码已被占用，请修改其它字典对照码再保存。");
			}
			SystemCache.removeCacheDict(oldDictKey);  //移除旧的字典缓存
		}
        dictIndex.setUpdateBy(userId);
		dictIndex.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = dictIndexService.updateById(dictIndex);
		if (result) {
			SystemCache.cacheDict(dictIndex.getDictKey());
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:dict:edit")
	@PostMapping("updateDict")
	@ResponseBody
	public R updateDict(Dict dict,String oldDictCode) {
    	String userId=this.getUserId();
		String dictCode=dict.getDictCode();
		if(WebplusUtil.isNotEmpty(dictCode)){
			if(!oldDictCode.equals(dictCode)){  //新的字典对照码不等于旧的对照码，则校验重复
				EntityWrapper<Dict> countWrapper = new EntityWrapper<Dict>();
				WebplusSqlHelp.eq(countWrapper, "dict_index_id", dict.getDictIndexId());
				WebplusSqlHelp.eq(countWrapper, "dict_code", dict.getDictCode());
				int count=dictService.selectCount(countWrapper);
				if(count>0){
					return R.warn("字典对照码已被占用，请修改其它字典对照码再保存。");
				}
			}	
		}
		dict.setUpdateBy(userId);
		dict.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = dictService.updateById(dict);
		if (result) {
			Dict dbDict=dictService.selectById(dict.getDictId());
			DictIndex dictIndex=dictIndexService.selectById(dbDict.getDictIndexId());
			SystemCache.cacheDict(dictIndex.getDictKey());
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:dict:remove")
	@PostMapping("removeDictIndex")
	@ResponseBody
	public R removeDictIndex(String id) {
		EntityWrapper<Dict> wrapper =new EntityWrapper<Dict>();
		wrapper.eq("edit_mode", WebplusCons.EDITMODE_READ);
		wrapper.eq("dict_index_id",id);
		int count=dictService.selectCount(wrapper);
		if(count>0){
			
			return R.warn("当前字典标识下存在只读的字典对照数据，只读的数据不允许修改和删除。");
		}
		Dto delDto=Dtos.newDto("dict_index_id", id);
		dictService.deleteByMap(delDto);
		DictIndex dictIndex=dictIndexService.selectById(id);
		boolean result = dictIndexService.deleteById(id);
		if (result) {
			SystemCache.removeCacheDict(dictIndex.getDictKey());//移除字典对照
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	/**
	 * 
	 * 简要说明：删除字典标识
	 * 编写者：陈骑元
	 * 创建时间：2019年4月28日 下午10:25:17
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:dict:remove")
	@PostMapping("removeDict")
	@ResponseBody
	public R removeDict(String id) {
	
	    Dict dict=dictService.selectById(id);
		if(WebplusCons.EDITMODE_READ.equals(dict.getEditMode())){
			
			return R.warn("删除字典对照码中存在只读的字典对照数据，只读的数据不允许修改和删除。");
		}
		boolean result = dictService.deleteById(id);
		if (result) {
			DictIndex dictIndex=dictIndexService.selectById(dict.getDictIndexId());
		    SystemCache.cacheDict(dictIndex.getDictKey());
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：批量删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:dict:remove")
	@PostMapping("batchRemoveDict")
	@ResponseBody
	public R batchRemoveDict(String ids) {
	
		List<String> idList=WebplusFormater.separatStringToList(ids);
		List<Dict> dictList=dictService.selectBatchIds(idList);
		String dictIndexId="";
		for(Dict dict:dictList){
			dictIndexId=dict.getDictIndexId();
			if(WebplusCons.EDITMODE_READ.equals(dict.getEditMode())){
				
				return R.warn("删除字典对照码中存在只读的字典对照数据，只读的数据不允许修改和删除。");
			}
		}
		boolean result = dictService.deleteBatchIds(idList);
		if (result) {
			DictIndex dictIndex=dictIndexService.selectById(dictIndexId);
			SystemCache.cacheDict(dictIndex.getDictKey());
			return R.ok();
		} else {
			return R.error("删除失败");
		}
	}
	/**
	 * 
	 * 简要说明：刷新字典缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("refreshDict")
	@ResponseBody
	public R refreshDict() {
		SystemCache.cacheAllDict();
	    
		return R.ok("刷新字典缓存操作成功");
	}
	
	@GetMapping("getDictByKey")
	@ResponseBody
	public List<Dict> getDictByKey(String key) {
		DictIndex dictIndex = dictIndexService.getByDictKey(key);
		List<Dict> list = dictService.getByIndex(dictIndex.getDictIndexId());
		return list;
	}
	
}

