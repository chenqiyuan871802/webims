package com.toonan.system.constant;

import com.toonan.core.constant.WebplusCons;

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
	 *1待审核2审核通过3审核退回
	 */
	public static final String SHZT_WAIT="1";
	public static final String SHZT_YES="2";
	public static final String SHZT_NO="3";
	
	/**
	 * JOSQL ListUtils 中使用的KEY
	 */
	public static final String LIST_KEY = ":List";

	/**
	 * 系统开关键
	 */
	public static final String SYSTEM_SWITCH_KEY = "system_switch";

	/**居住证状态*/
	public static final String JZZZT_1 = "1"; //有效	
	public static final String JZZZT_2 = "2"; //中止	
	public static final String JZZZT_3 = "3"; //休眠	
	public static final String JZZZT_4 = "4"; //注销	
	public static final String JZZZT_10 = "10"; //即将到期	
	public static final String JZZZT_11 = "11"; //已到期	
	/**
	 * 通用密码
	 */
	public static String REMIND_DAY_KEY = "remind_day";
	/**
	 * 通用密码
	 */
	public static String ROLE_MENU_SWITCH_KEY = "role_menu_switch";
	/**
	 * 通用密码
	 */
	public static String COMMON_PASSWORD_KEY = "common_password";

	/**
	 * 开关 开
	 */
	public static String LOG_SWITCH_KEY = "log_switch";
	/**
	 * 开关 开
	 */
	public static String SWITCH_ON = "on";
	/**
	 * 开关 关
	 */
	public static String SWITCH_OFF = "off";
	/**
	 * 日志字典类型，默认
	 */
	public static String LOG_TYPE_DEFAULT = "0";
	/**
	 * 日志字典类型，登陆
	 */
	public static String LOG_TYPE_LOING = "1";
	/**
	 * 日志字典类型，退出
	 */
	public static String LOG_TYPE_LONIGOUT = "2";
	/**
	 * 日志字典类型，查询
	 */
	public static String LOG_TYPE_QUERY = "3";
	/**
	 * 日志字典类型，更新
	 */
	public static String LOG_TYPE_UPDATE = "4";
	/**
	 * 日志字典类型，删除
	 */
	public static String LOG_TYPE_REMOVE = "5";

	/**
	 * 用户字典编码参数
	 */
	public static String TYPECODE_SYSUSER = "SYSUSER";

	/**
	 * 租客用户
	 */
	public static String USER_TYPE_ZK = "0";
	/**
	 * 房东用户
	 */
	public static String USER_TYPE_FD = "1";
	/**
	 *门禁管理员
	 */
	public static String USER_TYPE_MJGLY = "2";
	/**
	 *运维人员
	 */
	public static String USER_TYPE_YW = "3";

	/**
	 * 系统用户
	 */
	public static String USER_TYPE_SYSTEM = "4";
	/**
	 * 来穗用户
	 */
	public static String USER_TYPE_LS = "5";

	/**
	 * token参数
	 */
	public static String LOCK_MINUTE_KEY = "lock_minute";
	/**
	 * token参数
	 */
	public static String ERROR_COUNT_KEY = "error_count";

	/**
	 * token参数
	 */
	public static String TOKEN_PARAM = "token";
	/**
	 * token超时时间
	 */
	public static String TOKEN_TIMEOUT_KEY = "token_timeout";
	/**
	 * token默认超时时间2小时
	 */
	public static int DEFAULT_TIMECOUT = 2 * 3600;

	/**
	 * 应答代码 失败
	 */
	public static String ACK_FAILURE = "0";
	/**
	 * 应答代码 成功
	 */
	public static String ACK_SUCCESS = "1";

	public static final String CDKEY = "cdkey";
	public static final String CDKEY_PARAM = "cdkey";

	/**
	 * 来穗房屋栋详情查询
	 */
	public static final String LS_QUERY_DIC_LIST_KEY = "ls_query_dic_list";

	/**
	 * 通过证件号码或姓名查询流动人员信息
	 */
	public static final String LS_QUERY_LDRY_DETAIL_KEY = "ls_query_ldry_detail";

	/**
	 * 通过证件号码或姓名查询流动人员信息
	 */
	public static final String QUERY_LDRY_DETAILBYZJHM = "queryLdryDetailByZjhm";
	/**
	 * 查询房屋流动人员数量
	 */
	public static final String QUERY_FWXXFH_LDRY_COUNT = "queryFwxxfhLdryCount";
	/**
	 * 流动人员列表
	 */
	public static final String LS_QUERY_LDRY_LIST_KEY = "ls_query_ldry_list";
	/**
	 * 流动人员分页列表
	 */
	public static final String LS_QUERY_LDRY_PAGE_KEY = "ls_query_ldry_page";
	/**
	 * 流动人员历史查询
	 */
	public static final String LS_QUERY_LDRYLS_PAGE_KEY = "ls_query_ldryls_page";
	/**
	 * 来穗房屋栋详情查询
	 */
	public static final String LS_QUERY_MP_PAGE_KEY = "ls_query_mp_page";

	/**
	 * 来穗房屋栋详情查询
	 */
	public static final String LS_QUERY_FWXXZ_DETAIL_KEY = "ls_query_fwxxz_detail";
	/**
	 * 来穗房屋栋列表查询
	 */
	public static final String LS_QUERY_FWXXZ_PAGE_KEY = "ls_query_fwxxz_page";
	/**
	 * 来穗房屋栋列表查询
	 */
	public static final String LS_QUERY_FWXXZ_LIST_KEY = "ls_query_fwxxz_list";

	/**
	 * 更新房屋栋坐标
	 */
	public static final String LS_UPDATE_FWXXZZB_KEY = "ls_update_fwxxzzb";
	/**
	 * 来穗房屋套详情查询
	 */
	public static final String LS_QUERY_FWXXFH_DETAIL_KEY = "ls_query_fwxxfh_detail";
	/**
	 * 来穗房屋套列表查询
	 */
	public static final String LS_QUERY_FWXXFH_PAGE_KEY = "ls_query_fwxxfh_page";
	/**
	 * 来穗街镇查询接口
	 */
	public static final String LS_QUERY_JZ_KEY = "ls_query_jz";
	/**
	 * 来穗居委会查询接口
	 */
	public static final String LS_QUERY_JWH_KEY = "ls_query_jwh";
	/**
	 * 来穗派出所查询接口
	 */
	public static final String LS_QUERY_PCS_KEY = "ls_query_pcs";
	/**
	 * 来穗街道代码查询接口
	 */
	public static final String LS_QUERY_JDDM_KEY = "ls_query_jddm";

	/**
	 * 来穗接口查询证明材料
	 */
	public static final String LS_QUERY_ZMCL_LIST_KEY = "ls_query_zmcl_list";

	/**
	 * 来穗接口查询随行人员
	 */
	public static final String LS_QUERY_SXRY_LIST_KEY = "ls_query_sxry_list";
	/**
	 * 分页查询随行人员信息
	 */
	public static final String LS_QUERY_SXRY_PAGE_KEY = "ls_query_sxry_page";
	/**
	 * 分页查询移动巡查房屋信息
	 */
	public static final String LS_QUERY_YDCJFWXXFH_PAGE_KEY = "ls_query_ydcjfwxxfh_page";
	/**
	 * 分页查询居住证信息
	 */
	public static final String LS_QUERY_JZZXX_PAGE_KEY = "ls_query_jzzxx_page";
	/**
	 * 来穗房屋产权人信息
	 */
	public static final String LS_QUERY_CQR_LIST_KEY = "ls_query_cqr_list";
	/**
	 * 来穗房屋产权单位信息
	 */
	public static final String LS_QUERY_CQDW_LIST_KEY = "ls_query_cqdw_list";
	/**
	 * 来穗房屋照片信息查询
	 */
	public static final String LS_QUERY_FWXXPHOTO_LIST_KEY = "ls_query_fwxxphoto_list";
	/**
	 * 来穗房屋信息证明材料
	 */
	public static final String LS_QUERY_FWXXZMCL_LIST_KEY = "ls_query_fwxxzmcl_list";

	/**
	 * 来穗接口查询随行人员
	 */
	public static final String LS_QUERY_ICZP_INFO_KEY = "ls_query_iczp_info";

	/**
	 * 来穗街镇查询接口
	 */
	public static final String LS_QUERY_JZ_PAGE_KEY = "ls_query_jz_page";
	/**
	 * 来穗居委会查询接口
	 */
	public static final String LS_QUERY_JWH_PAGE_KEY = "ls_query_jwh_page";
	/**
	 * 来穗派出所查询接口
	 */
	public static final String LS_QUERY_PCS_PAGE_KEY = "ls_query_pcs_page";
	/**
	 * 来穗街道代码查询接口
	 */
	public static final String LS_QUERY_JDDM_PAGE_KEY = "ls_query_jddm_page";

	/**
	 * 来穗接口请求参数健
	 */
	public static final String LS_QUERY_DWXX_KEY = "ls_query_dwxx";

	/**
	 * 居住证校验接口请求参数健
	 */
	public static final String LS_CHECK_JZZ_KEY = "ls_check_jzz";

	/**
	 * 居住证卡面信息接口请求参数健
	 */
	public static final String LS_JZZ_CARD_INFO = "ls_jzz_card_info";

	/**
	 * 居住证业务信息接口请求参数健
	 */
	public static final String LS_JZZ_YW_CARD_INFO = "ls_jzz_yw_card_info";

	/**
	 * 来穗登记天数请求参数健
	 */
	public static final String LS_LIVE_CARD_CHECK_KEY = "ls_live_card_check";

	/**
	 * 来穗接口请求参数健
	 */
	public static final String LS_REQUEST_URL_KEY = "ls_request_url";
	/**
	 * 来穗登记天数请求参数健
	 */
	public static final String LS_DOWN_IMAGE_KEY = "ls_download_image";

	/**
	 * 查询房屋租赁合同信息
	 */
	public static final String LS_QUERY_FWZLHTXX_PAGE_KEY = "ls_query_fwzlhtxx_page";

	/**
	 * 查询房屋租赁合同信息列表
	 */
	public static final String LS_QUERY_FWZLHTXX_LIST_KEY = "ls_query_fwzlhtxx_list";
	/**
	 * 查询房屋租赁合同详细信息detail
	 */
	public static final String LS_QUERY_FWZLHTXX_DETAIL_KEY = "ls_query_fwzlhtxx_detail";
	/**
	 * 查询房屋租赁合同详细信息个数
	 */
	public static final String LS_QUERY_FWZLHTXX_COUNT_KEY = "ls_query_fwzlhtxx_count";
	/**
	 * 查询居住证信息数量
	 */
	public static final String LS_QUERY_JZZXX_COUNT_KEY = "ls_query_jzzxx_count";
	/**
	 * 查询房屋租赁合同人员信息
	 */
	public static final String LS_QUERY_FWZLRYXX_PAGE_KEY = "ls_query_fwzlryxx_page";

	/**
	 * 查询房屋租赁合同信息列表
	 */
	public static final String LS_QUERY_FWZLRYXX_LIST_KEY = "ls_query_fwzlryxx_list";
	/**
	 * 查询房屋租赁人员详细信息
	 */
	public static final String LS_QUERY_FWZLRYXX_DETAIL_KEY = "ls_query_fwzlryxx_detail";

	/**
	 * 查询房屋租赁合同契税信息
	 */
	public static final String LS_QUERY_FWZLQSXX_PAGE_KEY = "ls_query_fwzlqsxx_page";

	/**
	 * 查询房屋租赁合同契税信息列表
	 */
	public static final String LS_QUERY_FWZLQSXX_LIST_KEY = "ls_query_fwzlqsxx_list";
	/**
	 * 查询房屋租赁合同详细信息detail
	 */
	/**
	 * 查询房屋租赁合同契税信息
	 */
	public static final String LS_QUERY_FWZLQSXX_COUNT_KEY = "ls_query_fwzlqsxx_count";

	/**
	 * 粤省市接口请求参数健
	 */
	public static final String YSS_REQUEST_URL_KEY = "yss_request_url";
	/**
	 *粤省市登录接口 app/webApp/login
	 */
	public static final String YSS_LOGIN = "yss_login";
	/**
	 * 粤省市使用电话号码查询用户信息
	 */
	public static final String YSS_QUERY_INFO_FOR_MOBILE = "app/webApp/queryUserInfoByMobile";
	/**
	 * 粤省市查询预约日期时间段接口 app/webApp/queryYyszList
	 */
	public static final String YSS_QUERY_YYSZLIST = "yss_query_YyszList";

	/**
	 * 粤省市查询预约日期上门核实时间段接口 app/webApp/querySmhsszList
	 */
	public static final String YSS__QUERY_SMHSSZLIST = "yss_query_SmhsszList";

	/**
	 *粤省市查询我的预约表接口 app/webApp/queryMyYyList
	 */
	public static final String YSS_QUERY_MY_YYLIST = "yss_query_queryMyYyList";

	/**
	 * 粤省市预约登记取消接口 app/webApp/cancelYydj
	 */
	public static final String YSS_CANCELYYDJ = "yss_cancelYydj";

	/**
	 * 粤省市取消上门核实预约接口 app/webApp/cancelSmhsyy
	 */
	public static final String YSS_CANCELSMHSYY = "yss_cancelSmhsyy";

	/**
	 * 粤省市查询单个我的预约详情接口 app/webApp/queryMyYyDetail
	 */
	public static final String YSS_QUERY_MYYYDETAIL = "yss_queryMyYyDetail";

	/**
	 * 粤省市保存新增预约信息接口 app/webApp/saveReservation
	 */
	public static final String YSS_SAVERESERVATION = "yss_saveReservation";

	/**
	 * 粤省市查询用户的预约信息接口 app/webApp/queryMyRegist
	 */
	public static final String YSS_QUERYMYREGIST = "yss_queryMyRegist";

	/**
	 * 粤省市查询行政区 app/webApp/queryXzqList
	 */
	public static final String YSS_QUERYXZQLIST = "yss_queryXzqList";

	/**
	 * 粤省市查询街镇 mobile/appComQuery/queryJzdmPage
	 */
	public static final String YSS_QUERYJZDMPAGE = "yss_queryJzdmPage";
	/**
	 * 粤省市查询居委会  app/webApp/queryJwhdmPage
	 */
	public static final String YSS_QUERYJWHDMPAGE = "yss_queryJwhdmPage";
	/**
	 * 粤省市查询派出所 app/webApp/queryPcsdmList
	 */
	public static final String YSS_QUERYPCSDMPAGE = "yss_queryPcsdmPage";
	/**
	 * 粤省市查询街路巷 app/webApp/queryJddmPage
	 */
	public static final String YSS_QUERYJDDMPAGE = "yss_queryJddmPage";

	/**
	 * 查询企业单位信息 app/webApp/queryDwxxPage
	 */
	public static final String YSS_QUERYDWXXPAGE = "yss_queryDwxxPage";

	/**
	 * 查询服务站信息  app/webApp/queryFwzPage
	 */
	public static final String YSS_QUERYFWZPAGE = "yss_queryFwzPage";

	/**
	 * 查询出租屋（栋）信息 mobile/appComQuery/queryFwxxzPage
	 */
	public static final String YSS_QUERYFWXXZPAGE = "yss_queryFwxxzPage";

	/**
	 *查询单个出租屋（栋）信息 app/webApp/queryFwxxzDetail
	 */
	public static final String YSS_QUERYFWXXZDETAIL = "yss_queryFwxxzDetail";
	/**
	 *查询出租屋（套）信息 app/webApp/queryFwxxfhPage
	 */
	public static final String YSS_QUERYFWXXFHPAGE = "yss_queryFwxxfhPage";
	/**
	 *查询单个出租屋（套）信息 app/webApp/queryFwxxfhDetail
	 */
	public static final String YSS_QUERYFWXXFHDETAIL = "yss_queryFwxxfhDetail";

	/**
	 *查询单个来穗人员信息 app/webApp/queryLdryDetail
	 */
	public static final String YSS_QUERYLDRYDETAIL = "yss_queryLdryDetail";

	/**
	 *查询来穗人员登记情况 app/webApp/queryLiveCardDays
	 */
	public static final String YSS_QUERYLIVECARDDAYS = "yss_queryLiveCardDays";

	/**
	 *查询来穗人员办理居住证条件  app/webApp/queryJzzCheck
	 */
	public static final String YSS_QUERYJZZCHECK = "yss_queryJzzCheck";
	/**
	 *查询证明材料类型 app/webApp/queryCllxList
	 */
	public static final String YSS_QUERYCLLXLIST = "yss_queryCllxList";

	/**
	 *变更居住登记 app/webApp/updateRegistration
	 */
	public static final String YSS_UPDATEREGISTRATION = "yss_updateRegistration";
	/**
	 *新增保存居住登记 app/webApp/saveRegistration
	 */
	public static final String YSS_SAVEREGISTRATION = "yss_saveRegistration";

	/**
	 *查询流动人员居住登记信息  app/webApp/queryLdryYw
	 */
	public static final String YSS_QUERYLDRYYW = "yss_queryLdryYw";
	/**
	 *查询具体的流动人员居住登记信息 app/webApp/queryLdryYwDetail
	 */
	public static final String YSS_QUERYLDRYYWDETAIL = "yss_queryLdryYwDetail";
	/**
	 *查询居住证信息 app/webApp/jzzCardInfo
	 */
	public static final String YSS_JZZCARDINFO = "yss_jzzCardInfo";
	/**
	 *查询居住证业务信息 app/webApp/jzzywCardInfo
	 */
	public static final String YSS_JZZYWCARDINFO = "yss_jzzywCardInfo";

	/**
	 *居住证新办回写来穗人员证明材料 app/webApp/queryLdryZmcl
	 */
	public static final String YSS_QUERYLDRYZMCL = "yss_queryLdryZmcl";
	/**
	 *居住证新办(申领) app/webApp/saveNewJzz
	 */
	public static final String YSS_SAVENEWJZZ = "yss_saveNewJzz";
	/**
	 *注销居住登记 app/webApp/logoutRegistration
	 */
	public static final String YSS_LOGOUTREGISTRATION = "yss_logoutRegistration";
	/**
	 *查询下载证明材料图片 app/webApp/downloadFile
	 */
	public static final String YSS_DOWNLOADFILE = "yss_downloadFile";
	/**
	 *查询来穗人员照片信息 app/webApp/queryIcZpInfo
	 */
	public static final String YSS_QUERYICZPINFO = "yss_queryIcZpInfo";
	/**
	 * APP登录
	 */
	public static final String YSS_APP_LOGIN = "yss_appLogin";
	/**
	 * 用户注册
	 */
	public static final String YSS_SGIN_UP = "yss_saveuser";
	/**
	 * 检验验证码
	 */
	public static final String YSS_CHECK_MESSAGE = "checkMessage";
	/**
	 * 发送短信
	 */
	public static final String YSS_SEND_MESSAGE = "sendMessage";
	/**
	 *居住证申领审核状态查询 app/webApp/queryReservationStatus
	 */
	public static final String YSS_QUERYRESERVATIONSTATUS = "yss_queryReservationStatus";

	/**
	 * 行政区代码参数键
	 */
	public static final String XZQ_CODE_KEY = "xzq_code";
	/**
	 * 查询模式 行政区
	 */
	public static final String QUERY_MODE_XZQ = "1";
	/**
	 * 查询模式 街镇
	 */
	public static final String QUERY_MODE_JZ = "2";
	/**
	 * 查询模式 居委会
	 */
	public static final String QUERY_MODE_JWH = "3";
	/**
	 * 查询模式 派出所
	 */
	public static final String QUERY_MODE_PCS = "4";
	/**
	 * 查询模式 派出所
	 */
	public static final String QUERY_MODE_JDDM = "5";
	/**
	 * 超级账号
	 */
	public static final String SUPER_ADMIN = "super";
	/**
	 * 用户状态 有效
	 */
	public static final String USER_STATE_VALID = "1";

	/**
	 * 用户状态 停用
	 */
	public static final String USER_STATUS_STOP = "2";
	/**
	 * 用户状态 锁定
	 */
	public static final String USER_STATUS_LOCK = "3";
	/**
	 * 菜单类型：缺省
	 */
	public static final String MENU_TYPE_DEFAULT = "0";
	/**
	 * 菜单类型：系统菜单共享
	 */
	public static final String MENU_TYPE_SYSTEM = "1";
	/**
	 * 菜单类型：菜单类型门禁
	 */
	public static final String MENU_TYPE_MJ = "2";
	/**
	 * 菜单类型：菜单类型
	 */
	public static final String MENU_TYPE_YDCH = "3";

	/**
	 * 菜单类型：父菜单
	 */
	public static final String MODULE_TYPE_PARENT = "1";
	/**
	 * 菜单类型：子菜单
	 */
	public static final String MODULE_TYPE_SUB = "2";
	/**
	 * 菜单类型：按钮
	 */
	public static final String MODULE_TYPE_BUTTON = "3";

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
	public static final String TREE_ROOT_ID = "440112";
	/**
	 * 
	 */
	public static final String MENU_ROOT_ID = "0";
	/**
	 * 科目根节点名称
	 */
	public static final String TREE_ROOT_NAME = "全部分类";

	/**
	 * 科目根节点语义ID
	 */
	public static final String TREE_ROOT_CASCADE_ID = "0";
	/**
	 * 树的节点打开
	 */
	public static final String TREE_STATE_OPEN = "open";
	/**
	 * 树的节点关闭
	 */
	public static final String TREE_STATE_CLOSED = "closed";
	/**
	 * 菜单的根节点的名称
	 */
	public static final String MENU_ROOT_NAME = "功能菜单";
	/**
	 * 组织机构根节点名称
	 */
	public static final String DEPT_ROOT_NAME = "黄埔区";
	/**
	 * 菜单根节点图标
	 */
	public static final String MENU_ROOT_ICONCLS = "book";
	/**
	 * 组织机构根节点图标
	 */
	public static final String DEPT_ROOT_ICONCLS = "dept_config";
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
	public static final String EDITMODE_READ = "0";
	/**
	 * 编辑模式：可编辑
	 */
	public static final String EDITMODE_EDIT = "1";

	/**
	 * minio ip
	 */
	public static final String MINIO_IP = "minio_ip";
	public static final String MINIO_ACCOUNT = "minio_account";
	public static final String MINIO_PASSWORD = "minio_password";

	/**
	 * 配置的图片显示与保存路径
	 */
	public static final String WINDOWS_ZP_PIC = "windows_zj_pic";
	public static final String WINDOWS_APK_PIC = "windows_apk_pic";//手机app路径
	public static final String WINDOWS_FACE_PIC = "windows_face_pic";
	public static final String LINUX_FACE_PIC = "linux_face_pic";
	public static final String LINUX_ZP_PIC = "linux_zj_pic";
	public static final String LINUX_APK_PIC = "linux_zpk_pic";

	public static final String SHOW_ZP_PIC = "show_zj_pic_url";
	public static final String SHOW_FACE_PIC = "show_face_pic_url";
	public static final String SHOW_APK = "show_apk_url";//手机app路径
	public static final String SHOW_MANUAL_VIDEO = "show_manual_video";//视频流的链接配置，与访问服务器一致

	public static final String WINDOWS_ZLGL_PIC = "windows_zlgl_pic";
	public static final String LINUX_ZLGL_PIC = "linux_zlgl_pic";
	public static final String SHOW_ZLGL_PIC_URL = "show_zlgl_pic_url";

	public static final String WINDOWS_MJ_FACE = "windows_mj_face";
	public static final String LINUX_MJ_FACE = "linux_mj_face";
	public static final String REQUPOAD_ISRY = "reupload_isry";

	public static final String SERVER_URL = "server_url";

	/**
	 * 
	 */

	/**
	 * Cache对象前缀
	 *
	 */
	public static final class CACHE_PREFIX {
		//全局参数
		public static final String PARAM = WebplusCons.CACHE_PREFIX_HEAD + "param:";
		//字典
		public static final String DICT = WebplusCons.CACHE_PREFIX_HEAD + "dict:";
		//token 
		public static final String TOKEN = WebplusCons.CACHE_PREFIX_HEAD + "token:";
		//序列
		public static final String SEQ = WebplusCons.CACHE_PREFIX_HEAD + "seq:";
		//用户
		public static final String USER = WebplusCons.CACHE_PREFIX_HEAD + "user:";

		//锁实体信息
		public static final String LOCK_SN_VO = WebplusCons.CACHE_PREFIX_HEAD + "lockSnVo:";

		//缓存菜单
		public static final String MENU = WebplusCons.CACHE_PREFIX_HEAD + "menu";

		//门禁设备授权
		public static final String MJSQ = WebplusCons.CACHE_PREFIX_HEAD + "mjsq:";

		//门禁设备上的短视频列表
		public static final String VIDEOLIST = WebplusCons.CACHE_PREFIX_HEAD + "videolist:";

		//门禁异常刷锁列表
		public static final String UNUSUALLOCK = WebplusCons.CACHE_PREFIX_HEAD + "unusualLock:";

		//门禁异常刷锁列表
		public static final String LONGTIMENOENTRYPEOPLE = WebplusCons.CACHE_PREFIX_HEAD + "longTimeNoEntryPeople:";
		//用户授权菜单
		public static final String ROLEMENU = WebplusCons.CACHE_PREFIX_HEAD + "roleMenu:";

		//缓存没有坐标的栋地址
		public static final String FWXXZ_NOT_POINT_ADDRESS = WebplusCons.CACHE_PREFIX_HEAD + "fwxxzNotPointAddress";

		//授权未登记人员列表
		public static final String AUTH_UN_REGISTERED = WebplusCons.CACHE_PREFIX_HEAD + "AuthUnRegistered";

		//门禁自动巡查前置检查通过列表
		public static final String MJ_PATROL_PRE_CHECKED = WebplusCons.CACHE_PREFIX_HEAD + "MjPatrolPreChecked";

		//门禁自动巡查前置检查未通过列表
		public static final String MJ_PATROL_UN_PRE_CHECKED = WebplusCons.CACHE_PREFIX_HEAD + "MjPatrolUnPreChecked";

		//门禁自动巡查日志信息临时存储
		public static final String MJ_PATROL_LOG_TEMP = WebplusCons.CACHE_PREFIX_HEAD + "mjPatrolLogTemp";

		//门禁自动巡查日志信息去重临时存储
		public static final String MJ_PATROL_LOG_TEMP_UNDUPLICATING = WebplusCons.CACHE_PREFIX_HEAD
				+ "mjPatrolLogTempUnduplicating";

		//门禁自动巡查巡查通过请求待发送列表
		public static final String MJ_PATROL_REQUEST_CHECKED = WebplusCons.CACHE_PREFIX_HEAD + "mjPatrolRequestChecked";

		//门禁自动巡查巡查未通过请求待发送列表
		public static final String MJ_PATROL_REQUEST_UNCHECKED = WebplusCons.CACHE_PREFIX_HEAD + "mjPatrolRequestUnchecked";

		//门禁自动巡查未通过详情临时存储
		public static final String MJ_PATROL_UNCHECKED_DETAIL = WebplusCons.CACHE_PREFIX_HEAD + "mjPatrolUncheckedDetail";

		//门禁自动巡查检查状态（套）
		public static final String MJ_PATROL_STATE = WebplusCons.CACHE_PREFIX_HEAD + "mjPatrolState";

		//门禁自动巡查zhid和sn映射关系临时存储
		public static final String MJ_PATROL_ZHID_TO_LOCK_MAPPING = WebplusCons.CACHE_PREFIX_HEAD
				+ "mjPatrolZhidToLockMapping";

		public static final String MJ_LONG_TIME_NO_REPORT_LOCKINFO = WebplusCons.CACHE_PREFIX_HEAD
				+ "mjLongTimeNoReportLockinfo";

	}

	/**
	 * 门禁自动巡查详情状态登记
	 * TODO：更改至数据字典存储
	 */
	public static final class MJ_PATROL_LEVEL {
		//次数检查未通过
		public static final String TIMES_CHECK = "3";

		//登记检查未通过
		public static final String REGISTERED_CHECK = "2";

		//网络异常
		public static final String INTERNET_ERROR = "1";
	}

	//	private static String serviceNamespace = "https://openapi.lookdoor.cn:443";
	//	private static String serviceUrl = "http://openapi.lookdoor.cn:9090/";
	//	private static String newserviceUrl = "http://openapi.lookdoor.cn:9090/";
	//	private static String productUrl="https://openapi.lookdoor.cn";
	//	private static String testUrl="https://tc.lookdoor.cn:6443";
	//	private static String productSyncId="gz-ns-tunan";
	//	private static String testSyncId="devTest";
	//	private static String productToken = "20b590694432184ecf7ae9ece26bc55f913c05e4e9651a76e6a3a16d5eda3e93a9bfd51b599b88e8772ed754ede5ffb6";
	//	private static String testToken= "452a89738da3b755cac93eb48c81acaeaa5e2ee3d2aecd53f0be8c9cda2acd50bd402ed7c52da32cd9c9846b9f4e697c";

	public static final String LD_SERVICENAMESPACE = "ld_serviceNamespace";
	public static final String LD_SERVICEURL = "ld_serviceUrl";
	public static final String LD_NEWSERVICEURL = "ld_newserviceUrl";
	public static final String LD_PRODUCTURL = "ld_productUrl";
	public static final String LD_TESTURL = "ld_testUrl";
	public static final String LD_PRODUCTSYNCID = "ld_productSyncId";
	public static final String LD_TESTSYNCID = "ld_testSyncId";
	public static final String LD_PRODUCTTOKEN = "ld_productToken";
	public static final String LD_TESTTOKEN = "ld_testToken";
	
	//暂时目录
	public static final String FILE_SERVER_TMP = "tmp";

}
