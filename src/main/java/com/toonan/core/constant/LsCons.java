package com.toonan.core.constant;

/**
 * 
 * 类名:com.toonan.thirdApi.constant.ThirdCons
 * 描述:第三方接口配置文件
 * 编写者:陈骑元
 * 创建时间:2019年5月22日 下午5:48:49
 * 修改说明:
 */
public interface LsCons {

	public static final String URL_TEMPLE = "%s/%s";

	public static final String LS_CDKEY = "cdkey";
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

	public static final String LS_LOGIN_URL = "mobileApp/mobileAppQuery/login";

	/**
	 * 来穗房屋栋详情查询
	 */
	public static final String LS_QUERY_DIC_LIST_URL = "mobile/appComQuery/queryDictionaryList";

	/**
	 * 通过证件号码或姓名查询流动人员信息
	 */
	public static final String LS_QUERY_LDRY_DETAIL_URL = "mobileApp/mobileAppQuery/queryLdryDetail";

	/**
	 * 通过证件号码或姓名查询流动人员信息
	 */
	public static final String QUERY_LDRY_DETAILBYZJHM = "mobileApp/mobileAppQuery/queryLdryDetailByZjhm";
	/**
	 * 查询房屋流动人员数量
	 */
	public static final String QUERY_FWXXFH_LDRY_COUNT = "mobile/appComQuery/queryFwxxfhLdryCount";
	/**
	 * 流动人员列表
	 */
	public static final String LS_QUERY_LDRY_LIST_URL = "mobile/appComQuery/queryFwxxfhLdryCount";
	/**
	 * 流动人员分页列表
	 */
	public static final String LS_QUERY_LDRY_PAGE_URL = "mobile/appComQuery/queryLdryPage";
	/**
	 * 流动人员历史查询
	 */
	public static final String LS_QUERY_LDRYLS_PAGE_URL = "mobileApp/mobileAppQuery/queryLdryLsPage";
	/**
	 * 来穗房屋栋详情查询
	 */
	public static final String LS_QUERY_MP_PAGE_URL = "mobile/appComQuery/queryMpPage";
	/**
	 * 来穗查询小区信息
	 */
	public static final String LS_QUERY_XQXX_PAGE_URL = "mobile/appComQuery/queryXqxxPage";
	/**
	 * 来穗查询检查条目信息
	 */
	public static final String LS_QUERY_JCTM_PAGE_URL = "mobile/appComQuery/queryJctmPage";

	/**
	 * 来穗网格化信息查询
	 */
	public static final String LS_QUERY_WGXX_PAGE_URL = "mobileApp/mobileAppQuery/queryWgxxPage";

	/**
	 * 来穗房屋栋详情查询
	 */
	public static final String LS_QUERY_FWXXZ_DETAIL_URL = "mobileApp/mobileAppQuery/queryFwxxzDetail";
	/**
	 * 来穗房屋栋列表查询
	 */
	public static final String LS_QUERY_FWXXZ_PAGE_URL = "mobile/appComQuery/queryFwxxzPage";
	/**
	 * 来穗房屋栋列表查询
	 */
	public static final String LS_QUERY_FWXXZ_LIST_URL = "mobile/appComQuery/queryFwxxzList";

	/**
	 * 更新房屋栋坐标
	 */
	public static final String LS_UPDATE_FWXXZZB_URL = "mobile/appComUpdate/updateFwxxzZb";
	/**
	 * 来穗房屋套详情查询
	 */
	public static final String LS_QUERY_FWXXFH_DETAIL_URL = "mobileApp/mobileAppQuery/queryFwxxfhDetail";
	/**
	 * 来穗房屋套详情查询通过标准门牌编号和单元号
	 */
	public static final String LS_QUERY_FWXXFH_DETAIL_MPID_URL = "mobile/appComQuery/queryFwxxfhByMpidAndDy";
	/**
	 * 来穗房屋套列表查询
	 */
	public static final String LS_QUERY_FWXXFH_PAGE_URL = "mobile/appComQuery/queryFwxxfhPage";
	/**
	 * 来穗街镇查询接口
	 */
	public static final String LS_QUERY_JZ_URL = "mobile/appComQuery/queryJzdmList";
	/**
	 * 来穗居委会查询接口
	 */
	public static final String LS_QUERY_JWH_URL = "mobile/appComQuery/queryJwhdmList";
	/**
	 * 来穗派出所查询接口
	 */
	public static final String LS_QUERY_PCS_URL = "mobile/appComQuery/queryPcsdmList";
	/**
	 * 来穗街道代码查询接口
	 */
	public static final String LS_QUERY_JDDM_URL = "mobile/appComQuery/queryJddmList";

	/**
	 * 来穗接口查询证明材料
	 */
	public static final String LS_QUERY_ZMCL_LIST_URL = "mobile/appComQuery/queryZmclList";

	/**
	 * 来穗接口查询随行人员
	 */
	public static final String LS_QUERY_SXRY_LIST_URL = "mobileApp/mobileAppQuery/querySxryList";
	/**
	 * 分页查询随行人员信息
	 */
	public static final String LS_QUERY_SXRY_PAGE_URL = "mobile/appComQuery/querySxryPage";
	/**
	 * 分页查询移动巡查房屋信息
	 */
	public static final String LS_QUERY_YDCJFWXXFH_PAGE_URL = "mobile/appComQuery/queryYdcjFwxxfhPage";
	/**
	 * 分页查询居住证信息
	 */
	public static final String LS_QUERY_JZZXX_PAGE_URL = "mobile/appComQuery/queryJzzxxPage";
	/**
	 * 来穗房屋产权人信息
	 */
	public static final String LS_QUERY_CQR_LIST_URL = "mobileApp/mobileAppQuery/queryCqrList";
	/**
	 * 分页查询产权人信息
	 */
	public static final String LS_QUERY_CQR_PAGE_URL = "mobileApp/mobileAppQuery/queryCqrPage";
	/**
	 * 来穗房屋产权单位信息
	 */
	public static final String LS_QUERY_CQDW_LIST_URL = "mobileApp/mobileAppQuery/queryCqdwList";
	/**
	 * 来穗房屋产权单位信息分页
	 */
	public static final String LS_QUERY_CQDW_PAGE_URL = "mobileApp/mobileAppQuery/queryCqdwPage";
	/**
	 * 查询关注人员列表
	 */
	public static final String LS_QUERY_GZRY_LIST_URL = "mobile/appComQuery/queryGzryList";
	/**
	 * 来穗房屋照片信息查询
	 */
	public static final String LS_QUERY_FWXXPHOTO_LIST_URL = "mobileApp/mobileAppQuery/queryFwxxPhotoList";
	/**
	 * 来穗房屋照片信息查询
	 */
	public static final String LS_QUERY_GAXZ_PAGE_URL = "mobileApp/mobileAppQuery/queryGaXzPage";
	public static final String LS_QUERY_QUERY_JZZYW_PAGE = "mobileApp/mobileAppQuery/queryJzzywPage";
	/**
	 * 来穗房屋信息证明材料
	 */
	public static final String LS_QUERY_FWXXZMCL_LIST_URL = "mobile/appComQuery/queryFwxxZmclList";

	/**
	 * 来穗接口查询随行人员
	 */
	public static final String LS_QUERY_ICZP_INFO_URL = "ls_query_iczp_info";

	/**
	 * 来穗街镇查询接口
	 */
	public static final String LS_QUERY_JZ_PAGE_URL = "mobile/appComQuery/queryJzdmPage";
	/**
	 * 来穗居委会查询接口
	 */
	public static final String LS_QUERY_JWH_PAGE_URL = "mobile/appComQuery/queryJwhdmPage";
	/**
	 * 来穗派出所查询接口
	 */
	public static final String LS_QUERY_PCS_PAGE_URL = "mobile/appComQuery/queryPcsdmPage";
	/**
	 * 来穗街道代码查询接口
	 */
	public static final String LS_QUERY_JDDM_PAGE_URL = "mobile/appComQuery/queryJddmPage";

	/**
	 * 来穗接口请求参数健
	 */
	public static final String LS_QUERY_DWXX_URL = "mobile/appComQuery/queryDwxxPage";

	/**
	 * 居住证校验接口请求参数健
	 */
	public static final String LS_CHECK_JZZ_URL = "mobile/appComQuery/checkJzz";

	/**
	 * 居住证卡面信息接口请求参数健
	 */
	public static final String LS_JZZ_CARD_INFO = "mobile/appComQuery/jzzCardInfo";

	/**
	 * 居住证业务信息接口请求参数健
	 */
	public static final String LS_JZZ_YW_CARD_INFO = "mobile/appComQuery/jzzywCardInfo";
	/**
	 * 来穗登记天数请求参数健
	 */
	public static final String LS_LIVE_CARD_CHECK_URL = "mobile/appComQuery/liveCardCheck";
	/**
	 * 查询日常巡查隐患照片列表
	 */
	public static final String LS_QUERY_RCXC_YHZP_LIST_URL = "mobile/appComQuery/queryRcxcYhzpList";

	/**
	 * 查询日常巡查分页
	 */
	public static final String LS_QUERY_RCXC_PAGE_URL = "mobileApp/mobileAppQuery/queryRcjcLsPage";
	/**
	 * 查询日常巡查详情
	 */
	public static final String LS_QUERY_RCXC_DETAIL_URL = "mobileApp/mobileAppQuery/queryRcjcLsDetail";

	/**
	 * 来穗登记天数请求参数健
	 */
	public static final String LS_DOWN_IMAGE_URL = "mobileApp/mobileAppQuery/downloadImage";

	/**
	 * 查询房屋租赁合同信息
	 */
	public static final String LS_QUERY_FWZLHTXX_PAGE_URL = "mobile/appComQuery/queryFwzlhtxxPage";

	/**
	 * 查询房屋租赁合同信息列表
	 */
	public static final String LS_QUERY_FWZLHTXX_LIST_URL = "mobile/appComQuery/queryFwzlhtxxList";
	/**
	 * 查询房屋租赁合同详细信息detail
	 */
	public static final String LS_QUERY_FWZLHTXX_DETAIL_URL = "mobile/appComQuery/queryFwzlhtxxDetail";
	/**
	 * 查询房屋租赁合同详细信息个数
	 */
	public static final String LS_QUERY_FWZLHTXX_COUNT_URL = "mobile/appComQuery/queryFwzlhtxxCount";
	/**
	 * 查询居住证信息数量
	 */
	public static final String LS_QUERY_JZZXX_COUNT_URL = "mobile/appComQuery/queryJzzxxCount";

	/**
	 * 居住证业务分页查询
	 */
	public static final String LS_QUERY_JZZYW_PAGE_URL = "mobile/appComQuery/queryJzzywPage";
	/**
	 * 查询房屋租赁合同人员信息
	 */
	public static final String LS_QUERY_FWZLRYXX_PAGE_URL = "mobile/appComQuery/queryFwzlryxxPage";

	/**
	 * 查询房屋租赁合同信息列表
	 */
	public static final String LS_QUERY_FWZLRYXX_LIST_URL = "mobile/appComQuery/queryFwzlryxxList";
	/**
	 * 查询房屋租赁人员详细信息
	 */
	public static final String LS_QUERY_FWZLRYXX_DETAIL_URL = "mobile/appComQuery/queryFwzlryxxDetail";

	/**
	 * 查询房屋租赁合同契税信息
	 */
	public static final String LS_QUERY_FWZLQSXX_PAGE_URL = "mobile/appComQuery/queryFwzlqsxxPage";

	/**
	 * 查询房屋租赁合同契税信息列表
	 */
	public static final String LS_QUERY_FWZLQSXX_LIST_URL = "mobile/appComQuery/queryFwzlqsxxList";
	/**
	 * 查询房屋租赁合同详细信息detail
	 */
	/**
	 * 查询房屋租赁合同契税信息
	 */
	public static final String LS_QUERY_FWZLQSXX_COUNT_URL = "mobile/appComQuery/queryFwzlqsxxCount";

	/**
	* 来穗通#预约
	*/
	public static final String LS_WEIX_SAVERESERVATION = "mobileApp/wechatApi/saveReservationGeneric";
	/**
	* 来穗通#上门居住登记
	*/
	public static final String LS_WEIX_SAVEREGISTRATION = "mobileApp/wechatApi/saveRegistrationGeneric";
	/**
	* 来穗通#预约登记取消
	*/
	public static final String LS_WEIX_CCANCELYYDJ = "mobileApp/wechatApi/cancelYydj";
	/**
	* 来穗通#取消上门核实预约
	*/
	public static final String LS_WEIX_CANCELSMHSYY = "mobileApp/wechatApi/cancelSmhsyy";
	/**
	* 来穗通#查询来穗人员办理居住证条件
	*/
	public static final String LS_WEIX_QUERYJZZCHECK = "mobileApp/wechatApi/queryJzzCheck";
	/**
	* 来穗通#查询预约日期时间段
	*/
	public static final String LS_WEIX_QUERYYYSZLIST = "mobileApp/wechatApi/queryYyszList";
	/**
	* 来穗通#查询预约日期上门核实时间段
	*/
	public static final String LS_WEIX_QUERYSMHSSZLIST = "mobileApp/wechatApi/querySmhsszList";
	/**
	* 来穗通#查询我的预约表
	*/
	public static final String LS_WEIX_MYRESERVATIONLIST = "mobileApp/wechatApi/myReservationList";
	/**
	* 来穗通#查询单个我的预约详情
	*/
	public static final String LS_WEIX_QUERYMYYYDETAIL = "mobileApp/wechatApi/queryMyYyDetail";
	/**
	 * 来穗通#查询用户信息
	 */
	public static final String LS_WEIX_QUERYWEIXUSER = "mobileApp/wechatApi/queryWeixUser";

	/**
	 * 用户注册&登录
	 */
	public static final String LS_WEIX_APPLOGIN = "mobileApp/wechatApi/AppLogin";
	public static final String LS_WEIX_JZDM_PAGE = "/mobileApp/wechatApi/queryJzdmPage";

	public static final String LS_WEIX_JWHDM_PAGE = "/mobileApp/wechatApi/queryJwhdmPage";

	public static final String LS_WEIX_PCSDM_PAGE = "/mobileApp/wechatApi/queryPcsdmPage";

	public static final String LS_WEIX_JDDM_PAGE = "/mobileApp/wechatApi/queryJddmPage";

	public static final String LS_WEIX_FWXXZ_PAGE = "/mobileApp/wechatApi/queryFwxxzPage";

	public static final String LS_WEIX_FWXXFH_PAGE = "/mobileApp/wechatApi/queryFwxxfhPage";
	public static final String LS_WEIX_DICT = "/mobileApp/wechatApi/queryDictionaryList";
	public static final String LS_WEIX_DWXX = "/mobileApp/wechatApi/queryDwxxPage";
	public static final String LS_WEIX_MYYYLIST = "mobileApp/wechatApi/queryMyYyList";
	public static final String LS_WEIX_QUERYMYREGIS = "mobileApp/wechatApi/queryMyRegist";
	public static final String LS_WEIX_QUERYLIVECARDDAYS = "mobileApp/wechatApi/queryLiveCardDays";
	public static final String LS_WEIX_QUERYLDRYDETAIL = "mobileApp/wechatApi/queryLdryDetail";

	/**
	 * 出租屋管理员查询
	 */
	public static final String LS_QUERY_CZWGLY_PAGE_URL = "mobileApp/mobileAppQuery/queryCzwglyPage";
	/**
	 * 出租屋管理小组查询
	 */
	public static final String LS_QUERY_CZWGLXZ_PAGE_URL = "mobileApp/mobileAppQuery/queryCzwglxzPage";
	/**
	 * 栋套合并日常巡查信息
	 */
	public static final String LS_QUERY_FWXXTORCXC_PARENT_URL = "mobileApp/mobileAppQuery/queryFwxxToRcxcParent";
	
	
	/**
	 *人员信息上报统计接口
	 */
	public static final String LS_RYXXSBTJ_LIST_URL = "mobileApp/mobileAppQuery/listRyxxsbtj";
	/**
	 * 日常检查新增接口
	 */
	public static final String LS_ADD_RCJC_URL = "mobile/appUpdate/addRcjc";
	/**
	 * 获取 令牌accessToken令牌信息
	 */
	public static final String LS_GET_ACCESS_TOKEN_USER_INFO="mobileApp/mobileAppQuery/getAccessTokenUserInfo";
	
	
	
	/**
	 * 日常检查审核信息
	 */
	public static final String LS_RCXC_SHXX_URL = "mobileApp/mobileAppUpdate/saveRcxcShxx";
	/**
	 * 更新同步流动人员信息
	 */
	public static final String LS_UPDATE_SYNC_LDRY = "mobileApp/mobileAppUpdate/updateSyncLdry";
	/**
	 * 注销流动人员业务表
	 */
	public static final String LS_CANCEL_LDRY_QUERY = "mobileApp/mobileAppQuery/querySyncCacnelLdry";
	
	/**
	 * 查询居住证状态
	 */
	public static final String LS_LIST_JZZZT_URL = "mobileApp/mobileAppQuery/listJzzzt";
	/**
	 *查询居住证业务
	 */
	public static final String LS_LIST_JZZYW_URL = "mobileApp/mobileAppQuery/listJzzyw";
	/**
	 * 插入短信记录
	 */
	public static final String LS_INSERT_SMS_RECORD = "mobileApp/mobileAppUpdate/insertSmsRecord";
	/**
	 * 注销流动人员业务表
	 */
	public static final String LS_LIST_SYNC_LDRY_RECORD = "mobileApp/mobileAppQuery/listLdryOperationRecord";
	/**
	 * 查询用户列表
	 */
	public static final String LS_QUERY_USER_LIST = "mobile/appComQuery/queryUserList";
	/*****/
	public static final String COLUMN_FHID = "fhid";
	public static final String COLUMN_ZHID = "zhid";
	public static final String COLUMN_JDDM = "jddm";
	public static final String COLUMN_CJSJ = "cjsj";
	public static final String COLUMN_SFZX = "sfzx";
	public static final String COLUMN_XZQ = "xzq";
	public static final String COLUMN_XZQ_DICT = "xzqDict";
	public static final String COLUMN_JZ = "jz";
	public static final String COLUMN_TIMEID = "timeid";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_LDRYID = "ldryid";
	public static final String COLUMN_JZZID = "jzzid";
	public static final String COLUMN_SLYY = "slyy";
	public static final String COLUMN_SFZZSM = "sfzzsm";
	public static final String COLUMN_SJRXM = "sjrxm";
	public static final String COLUMN_SJRLXDH = "sjrlxdh";
	public static final String COLUMN_SJRDZA = "sjrdza";
	public static final String COLUMN_SJRDZ = "sjrdz";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_OPENID = "openid";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IDNUMBER = "idnumber";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_CJR = "cjr";
	public static final String COLUMN_ZHXGR = "zhxgr";
	public static final String COLUMN_ZHXGSJ = "zhxgsj";
	public static final String COLUMN_STATE = "state";
	public static final String COLUMN_MEMO1 = "memo1";
	public static final String COLUMN_MEMO2 = "memo2";
	public static final String COLUMN_DATE1 = "date1";
	public static final String COLUMN_DATE2 = "date2";
	public static final String COLUMN_SJLY = "sjly";
	public static final String COLUMN_OPENID1 = "openid1";
	public static final String COLUMN_OPENID2 = "openid2";
	public static final String COLUMN_XM = "xm";
	public static final String COLUMN_ZJHM = "zjhm";
	public static final String COLUMN_LXDH = "lxdh";
	public static final String COLUMN_DATALIST = "dataList";
	/**
	 * 云盾数据来源
	 */
	public static final Object COLUMN_SJLY_BYYD = "27";

	public static final String LS_WEIX_UPDATERESERVATION = "mobileApp/wechatApi/updateReservation";

	public static final String LS_WEIX_UPDATEREGISTRATION = "mobileApp/wechatApi/updateRegistration";

	public static final String LS_WEIX_QUERYFWZPAGE = "mobileApp/wechatApi/queryFwzPage";
}
