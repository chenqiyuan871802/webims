package com.ims.system.constant;
/**
 * 
 * 类名:com.ims.system.constant.SystemCons
 * 描述:系统常量表
 * 编写者:陈骑元
 * 创建时间:2018年5月1日 下午5:02:40
 * 修改说明:
 */
public interface SystemCons {
	
	
	/**
	 * 菜单类型：父菜单
	 */
	public static final String MENU_TYPE_PARENT = "1";
	/**
	 * 菜单类型：子菜单
	 */
	public static final String MENU_TYPE_SUB = "2";
	/**
	 * 菜单类型：按钮
	 */
	public static final String MENU_TYPE_BUTTON = "3";
	
	/**
	 * 用户类型：普通用户
	 */
	public static final String USER_TYPE_COMMON = "1";

	/**
	 * 用户类型：超级用户
	 */
	public static final String USER_TYPE_SUPER = "2";

	/**
	 * 用户类型：其他注册用户
	 */
	public static final String USER_TYPE_REGISTER = "3";

	
	/**
	 * 
	 */
	public static final String TREE_ROOT_ID="0";
	/**
	 * 科目根节点名称
	 */
	public static final String TREE_ROOT_NAME="全部分类";

	/**
	 * 科目根节点语义ID
	 */
	public static final String TREE_ROOT_CASCADE_ID="0";
	/**
	 * 树的节点打开
	 */
	public static final String TREE_STATE_OPEN="open";
	/**
	 * 树的节点关闭
	 */
	public static final String TREE_STATE_CLOSED="closed";
	/**
	 * 菜单的根节点的名称
	 */
	public static final String MENU_ROOT_NAME="功能菜单";
	/**
	 * 组织机构根节点名称
	 */
	public static final String DEPT_ROOT_NAME="组织机构";
	/**
	 * 菜单根节点图标
	 */
	public static final String MENU_ROOT_ICONCLS="book";
	/**
	 * 组织机构根节点图标
	 */
	public static final String DEPT_ROOT_ICONCLS="dept_config";
	/**
	 * 当前状态：启用
	 */
	public static final String ENABLED_YES = "1";
	
	/**
	 * 当前状态：停用
	 */
	public static final String ENABLED_NO = "0";
	/**
	 * 编辑模式：只读
	 */
	public static final String EDITMODE_READ="0";
	/**
	 * 编辑模式：可编辑
	 */
	public static final String EDITMODE_EDIT="1";
	
	/**
	 * Cache对象前缀
	 *
	 */
	public static  final class CACHE_PREFIX{
		//全局参数
		public static final String PARAM = "webims:param:";
		//字典
		public static final String DICT = "webims:dict:";
		
	}

}
