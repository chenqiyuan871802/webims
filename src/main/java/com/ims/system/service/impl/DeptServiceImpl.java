package com.ims.system.service.impl;

import com.ims.system.model.Dept;
import com.ims.system.model.TreeModel;
import com.ims.system.constant.SystemCons;
import com.ims.system.mapper.DeptMapper;
import com.ims.system.service.DeptService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.Query;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-14
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

	/**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Dept>
	 */
	@Override
	public List<Dept> list(Dto pDto) {

		return baseMapper.list(pDto);
	};

	/**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Dept>
	 */
	@Override
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
	@Override
	public List<Dept> like(Dto pDto) {

		return baseMapper.like(pDto);

	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Dept>
	 */
	@Override
	public Page<Dept> likePage(Dto pDto) {
		Query<Dept> query = new Query<Dept>(pDto);
		query.setRecords(baseMapper.likePage(query, pDto));
		return query;
	}

	@Override
	public TreeModel loadDeptTree(Dto pDto) {
		// 查询 根节点 dept=0;
		Dept rootDept = baseMapper.selectById(SystemCons.TREE_ROOT_ID);
		// 如果数据库没有根节点 则创建一个根节点,并保存到数据库中
		if (IMSUtil.isEmpty(rootDept)) {
			rootDept = new Dept();
			rootDept.setDeptId(SystemCons.TREE_ROOT_ID);
			rootDept.setParentId("-1");
			rootDept.setDeptName(SystemCons.DEPT_ROOT_NAME);
			rootDept.setIconName(SystemCons.DEPT_ROOT_ICONCLS);
			rootDept.setCascadeId(SystemCons.TREE_ROOT_CASCADE_ID);
			rootDept.setIsDel(IMSCons.IS.NO);
			rootDept.setIsAutoExpand(IMSCons.IS.YES);
			rootDept.setSortNo(1);
			rootDept.setRemark("顶级机构不能进行移动和删除操作，只能进行修改");
			rootDept.setCreateTime(IMSUtil.getDateTime());
			baseMapper.insert(rootDept);

		}
		pDto.put("isDel", IMSCons.IS.NO); // 查询有效的组织机构信息
		pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
		List<Dept> deptList = baseMapper.list(pDto);
		TreeModel rootModel = new TreeModel();
		rootModel.setText(rootDept.getDeptName());
		rootModel.setId(rootDept.getDeptId());
		if (IMSUtil.isNotEmpty(rootDept.getIconName())) {
			rootModel.setIconCls(rootDept.getIconName());
		} else {
			rootModel.setIconCls(SystemCons.DEPT_ROOT_ICONCLS);
		}
		rootModel.setCascadeId(rootDept.getCascadeId());
		if (IMSCons.IS.NO.equals(rootDept.getIsAutoExpand())) {
			rootModel.setState(SystemCons.TREE_STATE_CLOSED);

		}
		for (int i = 0; i < deptList.size(); i++) {
			Dept dept = deptList.get(i);

			String parentId = dept.getParentId();
			String icon_name = dept.getIconName();
			TreeModel treeModel = new TreeModel();
			treeModel.setId(dept.getDeptId());
			treeModel.setParentId(parentId);
			treeModel.setCascadeId(dept.getCascadeId());
			treeModel.setText(dept.getDeptName());
			if (IMSUtil.isNotEmpty(icon_name)) {
				treeModel.setIconCls(icon_name);
			}

			rootModel.add(treeModel);
		}
		return rootModel;

	}

	@Override
	public String calc(Dto pDto) {
		// TODO Auto-generated method stub
		return baseMapper.calc(pDto);
	}

	/**
	 * 更新处理机构信息
	 */
	@Transactional
	@Override
	public boolean updateDept(Dept dept) {
		String deptId = dept.getDeptId();
		Dept oldDept=this.selectById(deptId);
		// 根节点的父节点是不存在的，因此默认为-1
		if (SystemCons.TREE_ROOT_ID.equals(deptId)) {
			dept.setParentId("-1");
		}
		String parentId = dept.getParentId();
		String parentIdOld = oldDept.getParentId();
		dept.setUpdateTime(IMSUtil.getDateTime());
		if (!parentId.equals(parentIdOld)) { // 如果机构发生改变了，则更新子节点的语义ID
			// 查询当前父节点下面是否存在最大的语义ID
			Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
			calcDto.put("parentId", parentId);
			String maxCascadeId = this.calc(calcDto);
			// 如果当前父节点不存在最大的语义ID，则初始化生成
			if (IMSUtil.isEmpty(maxCascadeId)) {
				Dept parentDept = this.selectById(parentId);
				if (IMSUtil.isEmpty(parentDept)) {
					maxCascadeId = "0.0000";
				} else {
					maxCascadeId = parentDept.getCascadeId() + ".0000";
				}
			}
			// 生成新的语义ID
			String cascadeId = IMSUtil.createCascadeId(maxCascadeId, 9999);
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
				childDept.setUpdateTime(IMSUtil.getDateTime());
				baseMapper.updateById(childDept);

			}
		}
		int row = baseMapper.updateById(dept);
		return row > 0;
	};
}
