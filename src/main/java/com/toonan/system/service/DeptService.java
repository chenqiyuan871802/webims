package com.toonan.system.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusFormater;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.EasyuiTreeModel;
import com.toonan.core.vo.Query;
import com.toonan.core.vo.UserToken;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.mapper.DeptMapper;
import com.toonan.system.model.Dept;


/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-14
 */
@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept>{

	/**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Dept>
	 */
	public List<Dept> list(Dto pDto) {

		return baseMapper.list(pDto);
	};

	/**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Dept>
	 */
	public Page<Dept> listPage(Dto pDto) {
		Query<Dept> query = new Query<Dept>(pDto);
		query.setRecords(baseMapper.listPage(query, pDto));
		return query;
	};

	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Dept>
	 */
	public List<Dept> like(Dto pDto) {

		return baseMapper.like(pDto);

	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Dept>
	 */
	public Page<Dept> likePage(Dto pDto) {
		Query<Dept> query = new Query<Dept>(pDto);
		query.setRecords(baseMapper.likePage(query, pDto));
		return query;
	}
	/**
	 * 
	 * 简要说明：加载easyui树
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年8月18日 下午2:23:25 
	 * @param 说明
	 * @return 说明
	 */
	public EasyuiTreeModel loadDeptTree(Dto pDto,String whetherGrant) {
		EasyuiTreeModel rootModel = new EasyuiTreeModel();
		List<Dept> allDeptList=Lists.newArrayList();
		// 查询 根节点 dept=0;
		if(WebplusCons.WHETHER_YES.equals(whetherGrant)) {
			 String grantDeptId=pDto.getString("grantDeptId");
				List<String> deptTypeList=Lists.newArrayList();
		    Dept rootDept=null;
			 if(WebplusUtil.isNotEmpty(grantDeptId)) {
				 Dept parentDept=this.selectById(grantDeptId);
				 if(WebplusUtil.isNotEmpty(parentDept)) {
					 String deptType=parentDept.getDeptType();
					 if(WebplusCons.DEPT_TYPE_XZQ.equals(deptType)) {
						 rootDept=parentDept;
						 pDto.put("cascadeId", parentDept.getCascadeId());
						 deptTypeList.add(WebplusCons.DEPT_TYPE_JZ);
					 }else if(WebplusCons.DEPT_TYPE_JZ.equals(deptType)) {
						 rootDept=parentDept;
						 pDto.put("cascadeId", parentDept.getCascadeId());
						 deptTypeList.add(WebplusCons.DEPT_TYPE_JWH);
					 }else if(WebplusCons.DEPT_TYPE_JWH.equals(deptType)) {
						 pDto.put("cascadeId", parentDept.getCascadeId());
						 rootDept=parentDept;
					 }
				 }
			 }
			 if(WebplusUtil.isEmpty(rootDept)) {
				 String rootDeptId=WebplusCache.getParamValue(WebplusCons.XZQ_CODE_KEY,SystemCons.TREE_ROOT_ID);
				 rootDept=this.selectById(rootDeptId);
				 deptTypeList.add(WebplusCons.DEPT_TYPE_JZ);
				 deptTypeList.add(WebplusCons.DEPT_TYPE_JWH);
			 }
			 pDto.put("deptTypeList", deptTypeList);
			 rootModel.setText(rootDept.getDeptName());
		     rootModel.setId(rootDept.getDeptId());
		     rootModel.setState(SystemCons.TREE_STATE_OPEN);
		     rootModel.setTreeBussType(rootDept.getDeptType());
		     pDto.put("isDel", WebplusCons.WHETHER_NO); // 查询有效的组织机构信息
		     pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
			 List<Dept> deptList = baseMapper.list(pDto);
			 allDeptList.addAll(deptList);
		}else {
			UserToken userToken=WebplusCache.getUserToken(pDto);
			String dyfw=userToken.getDyfw();
    		String dymc=userToken.getDymc();
    		String dataRange=userToken.getDataRange();
    		List<String> dataRangeList=Lists.newArrayList();
    		String whetherRange=WebplusCons.WHETHER_NO;  //是否有数据范围
    		if(WebplusUtil.isNotEmpty(dataRange) && !WebplusCons.ALL.equals(dataRange)) {
    			whetherRange=WebplusCons.WHETHER_YES;  //是否有数据范围
    			List<String> rangeList=WebplusFormater.separatStringToList(dataRange);
    			dataRangeList.addAll(rangeList);
    		}else {
    			dataRangeList.add(dyfw);
    		}
			Dept rootDept = baseMapper.selectById(dyfw);
			rootModel.setText(rootDept.getDeptName());
			rootModel.setId(rootDept.getDeptId());
			if (WebplusUtil.isNotEmpty(rootDept.getIconName())) {
				rootModel.setIconCls(rootDept.getIconName());
			} else {
				rootModel.setIconCls(SystemCons.DEPT_ROOT_ICONCLS);
			}
			rootModel.setCascadeId(rootDept.getCascadeId());
			/*
			 * if (WebplusCons.WHETHER_NO.equals(rootDept.getIsAutoExpand())) {
			 * rootModel.setState(SystemCons.TREE_STATE_CLOSED); }
			 */
			if(WebplusCons.WHETHER_YES.equals(whetherRange)) {
				if(WebplusCons.DYMC_XZQ.equals(dymc)) {
					for(String deptId:dataRangeList) {
						Dept jzDept=this.selectById(deptId);
						Dto newDto=Dtos.newDto();
						newDto.put("cascadeId",jzDept.getCascadeId());
						newDto.put("isDel", WebplusCons.WHETHER_NO); // 查询有效的组织机构信息
						newDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
						 List<Dept> deptList = this.list(newDto);
						 allDeptList.addAll(deptList);
					}
				}else if(WebplusCons.DYMC_JZ.equals(dymc)) {
					pDto.put("deptIdList", dataRangeList);
					pDto.put("isDel", WebplusCons.WHETHER_NO); // 查询有效的组织机构信息
				     pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
					 List<Dept> deptList = baseMapper.list(pDto);
					 allDeptList.addAll(deptList);
				}
			}else {  
				//没有数据范围直接查询
				List<String> deptTypeList=Lists.newArrayList();
				if(!WebplusCons.DYMC_JWH.equals(dymc)) {
					if(WebplusCons.DYMC_XZQ.equals(dymc)) {
						deptTypeList.add(WebplusCons.DEPT_TYPE_JZ);
					}
					
					deptTypeList.add(WebplusCons.DEPT_TYPE_JWH);
				    pDto.put("deptTypeList", deptTypeList);
					pDto.put("cascadeId", rootDept.getCascadeId());
					pDto.put("isDel", WebplusCons.WHETHER_NO); // 查询有效的组织机构信息
				     pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
					 List<Dept> deptList = baseMapper.list(pDto);
					 allDeptList.addAll(deptList);
				}
				
			}
			
			
		}
		
		for (int i = 0; i < allDeptList.size(); i++) {
			Dept dept = allDeptList.get(i);
			String parentId = dept.getParentId();
			String icon_name = dept.getIconName();
			EasyuiTreeModel treeModel = new EasyuiTreeModel();
			treeModel.setId(dept.getDeptId());
			treeModel.setParentId(parentId);
			treeModel.setCascadeId(dept.getCascadeId());
			treeModel.setText(dept.getDeptName());
			treeModel.setTreeBussType(dept.getDeptType());
			if (WebplusUtil.isNotEmpty(icon_name)) {
				treeModel.setIconCls(icon_name);
			}
			rootModel.add(treeModel);
		}
		return rootModel;

	}

  
	public String calc(Dto pDto) {
		// TODO Auto-generated method stub
		return baseMapper.calc(pDto);
	}

	/**
	 * 更新处理机构信息
	 */
	@Transactional
	public boolean updateDept(Dept dept) {
		String deptId = dept.getDeptId();
		Dept oldDept=this.selectById(deptId);
		// 根节点的父节点是不存在的，因此默认为-1
		if (SystemCons.TREE_ROOT_ID.equals(deptId)) {
			dept.setParentId("-1");
		}
		String parentId = dept.getParentId();
		String parentIdOld = oldDept.getParentId();
		dept.setUpdateTime(WebplusUtil.getDateTime());
		if (!parentId.equals(parentIdOld)) { // 如果机构发生改变了，则更新子节点的语义ID
			// 查询当前父节点下面是否存在最大的语义ID
			Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
			calcDto.put("parentId", parentId);
			String maxCascadeId = this.calc(calcDto);
			// 如果当前父节点不存在最大的语义ID，则初始化生成
			if (WebplusUtil.isEmpty(maxCascadeId)) {
				Dept parentDept = this.selectById(parentId);
				if (WebplusUtil.isEmpty(parentDept)) {
					maxCascadeId = "0.0000";
				} else {
					maxCascadeId = parentDept.getCascadeId() + ".0000";
				}
			}
			// 生成新的语义ID
			String cascadeId = WebplusUtil.createCascadeId(maxCascadeId, 9999);
			dept.setCascadeId(cascadeId);
			// 原始的语义ID
			String cascadeIdOld =oldDept.getCascadeId();
			Dto pDto = Dtos.newDto("cascadeId", cascadeIdOld);
			// 查询所有子节点进行更新
			List<Dept> childDeptList = this.like(pDto);
			for (Dept childDept : childDeptList) {
				String cascade_id_temp = childDept.getCascadeId();
				cascade_id_temp = cascade_id_temp.replace(cascadeIdOld, cascadeId);
				childDept.setCascadeId(cascade_id_temp);
				childDept.setUpdateTime(WebplusUtil.getDateTime());
				baseMapper.updateById(childDept);

			}
		}
		int row = baseMapper.updateById(dept);
		return row > 0;
	};
	
	/**
	 * 
	 * 简要说明：获取父节点下面最大的节点
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月22日 下午11:25:46 
	 * @param 说明
	 * @return 说明
	 */
	public String getCurCascadeId(String parentId) {
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", parentId);
		String maxCascadeId = this.calc(calcDto);
		if (WebplusUtil.isEmpty(maxCascadeId)) {
			Dept parentDept = this.selectById(parentId);
			if (WebplusUtil.isEmpty(parentDept)) {
				maxCascadeId = "0.0000";
			} else {
				maxCascadeId = parentDept.getCascadeId() + ".0000";
			}

		}
		String curCascadeId = WebplusUtil.createCascadeId(maxCascadeId, 9999);
		return curCascadeId;
	}
}
