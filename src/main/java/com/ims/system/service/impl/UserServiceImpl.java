package com.ims.system.service.impl;

import com.ims.system.model.RoleMenu;
import com.ims.system.model.RoleUser;
import com.ims.system.model.User;
import com.ims.system.constant.SystemCons;
import com.ims.system.mapper.RoleMenuMapper;
import com.ims.system.mapper.RoleUserMapper;
import com.ims.system.mapper.UserMapper;
import com.ims.system.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.IMSCodec;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.Query;
import com.ims.common.util.R;
import com.ims.common.util.SqlHelpUtil;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-09-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 
     */
	@Autowired
    private RoleUserMapper roleUserMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<User>
	 */
    @Override
	public List<User> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<User>
	 */
	@Override
	public Page<User> listPage(Dto pDto){
	    Query<User> query=new Query<User>(pDto);
	    query.setRecords(baseMapper.listPage(query,pDto));
	    return query;
	};
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<User>
	 */
	@Override
	public List<User> like(Dto pDto){
	
	    return baseMapper.like(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<User>
	 */
	@Override
	public Page<User> likePage(Dto pDto){
	    Query<User> query=new Query<User>(pDto);
	    query.setRecords(baseMapper.likePage(query,pDto));
	    return query;
	};
	/**
	 * 根据数学表达式进行数学运算
	 * 
	 * @param pDto
	 * @return String
	 */
	 @Override
	 public String calc(Dto pDto){
	 
	     return baseMapper.calc(pDto);
	 }
	 /**
	  * 
	  * 简要说明：分页查询用户信息
	  * 编写者：陈骑元
	  * 创建时间：2018年9月28日 上午11:48:14
	  * @param 说明
	  * @return 说明
	  */
	@Override
	public Page<User> listUserPage( Dto pDto) {
		  Query<User> query=new Query<User>(pDto);
		  query.setRecords(baseMapper.listUserPage(query,pDto));
		  return query;
	}
	/**
	 * 验证登陆
	 */
	@Override
	public R checkLogin(String account, String password) {
		EntityWrapper<User> userWrapper = new EntityWrapper<User>();
		SqlHelpUtil.eq(userWrapper, "account", account);
		SqlHelpUtil.eq(userWrapper, "is_del", IMSCons.IS.NO);
		User user=this.selectOne(userWrapper);
		if(IMSUtil.isEmpty(user)){
			
			return R.warn("用户账号输入错误，请重新输入。");
		}
		String status = user.getStatus();
		if (SystemCons.USER_STATUS_LOCK.equals(status)) {
			
			return R.warn("该用户帐号已被锁定，系统拒绝登录，请联系管理员。");
		}
		if (SystemCons.USER_STATUS_STOP.equals(status)) {
			
			return R.warn("该用户帐号已被锁定，系统拒绝登录，请联系管理员。");
		}
		String decryptPassword = IMSCodec.decrypt(user.getPassword(), IMSCons.PASSWORD_KEY);
		String userId=user.getUserId();
		if (password.equals(decryptPassword)) { // 判断密码是否一致
			User updateUser = new User();
			updateUser.setErrorNum(0);
			updateUser.setUserId(userId);
			updateUser.setUpdateBy(userId);
			updateUser.setUpdateTime(IMSUtil.getDateTime());
			this.updateById(updateUser);
			if (SystemCons.SUPER_ADMIN.equals(account)) {  //超级用户没有角色
				R r=R.ok();
				r.put("user", user);
				return r;
			}else{
				RoleUser entity=new RoleUser();
				entity.setUserId(userId);
				RoleUser roleUser = roleUserMapper.selectOne(entity);
				if (IMSUtil.isEmpty(roleUser)) { // 判断该角色是否授予角色权限
					
					return R.warn("该用户帐号未授予角色权限，系统拒绝登录，请联系管理员。");
				} else {
					EntityWrapper<RoleMenu> countWrapper = new EntityWrapper<RoleMenu>();
					SqlHelpUtil.eq(countWrapper, "role_id", roleUser.getRoleId());
					int count =roleMenuMapper.selectCount(countWrapper);
					if(count>0){
						R r=R.ok();
						r.put("user", user);
						return r;
					}else{
						return R.warn("该用户所在的角色未授予菜单权限，系统拒绝登录，请联系管理员。");
					}
					
				}
			}
		}else{
			// 当前错误次数=错误次数+1;
			Integer currentErrorNum = user.getErrorNum() + 1;
			// 锁定次数
			Integer lockNum = user.getLockNum();
			// 更新错误次数
			User errorUser = new User();
			errorUser.setErrorNum(currentErrorNum);
			errorUser.setUserId(userId);
			errorUser.setUpdateBy(userId);
			errorUser.setUpdateTime(IMSUtil.getDateTime());
			String warnMsg="";
			if (currentErrorNum >= lockNum) {
				errorUser.setStatus(SystemCons.USER_STATUS_LOCK);
				 warnMsg="你已经连续输错密码" + currentErrorNum + "次，超过系统错误次数最大限制，系统自动锁定改账号，请联系管理员";
				
			} else {
				// 错误次数到达三次以上开始提醒
				if (currentErrorNum >= 3) {
					int endNum = lockNum - currentErrorNum;
					warnMsg="你已经连续输错密码" + currentErrorNum + "次，如果再输错" + endNum + "次，系统自动锁定该账号，请慎重";
				} else {
					warnMsg="用户密码输入错误，请输入正确密码";
					
				}
			}

			this.updateById(errorUser);
			return R.warn(warnMsg);
						
		}
	}
	 
}
