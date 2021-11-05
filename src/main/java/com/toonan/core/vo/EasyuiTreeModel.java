package com.toonan.core.vo;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 类名:com.tunan.system.persistence.model.TreeModel
 * 描述:定义树模型
 * 编写者:陈骑元
 * 创建时间:2016年7月14日 下午3:18:29
 * 修改说明:
 */
public class EasyuiTreeModel {
	
	public static final String TREE_ROOT_ID="0";
	/**
	 * 树模型ID
	 */
	private String id; 
	/**
	 * 父节点
	 */
	private String parentId;
	/**
	 * 语义ID
	 */
	private String cascadeId;
	
	/**
	 * 树节点名称
	 */
	private String text;
	/**
	 * 树节点图标
	 */
	private String  iconCls;
	/**
	 * 树节点打开或关闭状态
	 */
	private String state;
	/**
	 * 选中
	 */
	private String checked;
	/**
	 * 树的业务类型
	 */
	private String treeBussType;
	
	
	public String getTreeBussType() {
		return treeBussType;
	}



	public void setTreeBussType(String treeBussType) {
		this.treeBussType = treeBussType;
	}



	/**
	 * 子节点
	 */
    private List<EasyuiTreeModel> children=new ArrayList<EasyuiTreeModel>();
    
    public void add(EasyuiTreeModel treeModel){
    	if(TREE_ROOT_ID.equals(treeModel.getParentId())){
    		children.add( treeModel);
    	}else if(id.equals(treeModel.getParentId())){
    		children.add(treeModel);
    	}else{
    		for(EasyuiTreeModel tmp_treeModel:children){
    			tmp_treeModel.add( treeModel);
    		}
    	}
    }
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getParentId() {
		return parentId;
	}



	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<EasyuiTreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<EasyuiTreeModel> children) {
		this.children = children;
	}


	public String getCascadeId() {
		return cascadeId;
	}



	public void setCascadeId(String cascadeId) {
		this.cascadeId = cascadeId;
	}



	public String getChecked() {
		return checked;
	}



	public void setChecked(String checked) {
		this.checked = checked;
	}
	
}
