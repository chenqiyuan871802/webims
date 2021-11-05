package com.toonan.core.constant;

public enum LsUri {
	/**
	 * 
	 */
	LS_LOGIN_URL("mobileApp/mobileAppQuery/login"),
	/**
	* 来穗房屋栋详情查询
	*/
	LS_QUERY_DIC_LIST_URL("mobile/appComQuery/queryDictionaryList"),

	/**
	* 通过证件号码或姓名查询流动人员信息
	*/
	LS_QUERY_LDRY_DETAIL_URL("mobileApp/mobileAppQuery/queryLdryDetail"),

	/**
	* 通过证件号码或姓名查询流动人员信息
	*/
	QUERY_LDRY_DETAILBYZJHM("mobileApp/mobileAppQuery/queryLdryDetailByZjhm"),
	/**
	* 查询房屋流动人员数量
	*/
	QUERY_FWXXFH_LDRY_COUNT("mobile/appComQuery/queryFwxxfhLdryCount"),
	/**
	* 流动人员列表
	*/
	LS_QUERY_LDRY_LIST_URL("mobile/appComQuery/queryFwxxfhLdryCount"),
	
	/**
	 * 移动采集查询人员列表
	 */
	APP_QUERY_LDRY_LIST_URL("mobileApp/mobileAppQuery/queryLdryList"),
	/**
	* 流动人员分页列表
	*/
	LS_QUERY_LDRY_PAGE_URL("mobile/appComQuery/queryLdryPage"),
	/**
	* 流动人员历史查询
	*/
	LS_QUERY_LDRYLS_PAGE_URL("mobileApp/mobileAppQuery/queryLdryLsPage"),
	/**
	* 来穗房屋栋详情查询
	*/
	LS_QUERY_MP_PAGE_URL("mobile/appComQuery/queryMpPage"),
	/**
	* 来穗查询小区信息
	*/
	LS_QUERY_XQXX_PAGE_URL("mobile/appComQuery/queryXqxxPage"),
	/**
	* 来穗查询检查条目信息
	*/
	LS_QUERY_JCTM_PAGE_URL("mobile/appComQuery/queryJctmPage"),

	/**
	* 来穗网格化信息查询
	*/
	LS_QUERY_WGXX_PAGE_URL("mobileApp/mobileAppQuery/queryWgxxPage"),

	/**
	* 来穗房屋栋详情查询
	*/
	LS_QUERY_FWXXZ_DETAIL_URL("mobileApp/mobileAppQuery/queryFwxxzDetail"),
	/**
	* 来穗房屋栋列表查询
	*/
	LS_QUERY_FWXXZ_PAGE_URL("mobile/appComQuery/queryFwxxzPage"),
	/**
	* 来穗房屋栋列表查询
	*/
	LS_QUERY_FWXXZ_LIST_URL("mobile/appComQuery/queryFwxxzList"),

	/**
	* 更新房屋栋坐标
	*/
	LS_UPDATE_FWXXZZB_URL("mobile/appComUpdate/updateFwxxzZb"),
	/**
	* 来穗房屋套详情查询
	*/
	LS_QUERY_FWXXFH_DETAIL_URL("mobileApp/mobileAppQuery/queryFwxxfhDetail"),
	/**
	* 来穗房屋套详情查询通过标准门牌编号和单元号
	*/
	LS_QUERY_FWXXFH_DETAIL_MPID_URL("mobile/appComQuery/queryFwxxfhByMpidAndDy"),
	/**
	* 来穗房屋套列表查询
	*/
	LS_QUERY_FWXXFH_PAGE_URL("mobile/appComQuery/queryFwxxfhPage"),
	/**
	* 来穗街镇查询接口
	*/
	LS_QUERY_JZ_URL("mobile/appComQuery/queryJzdmList"),
	/**
	* 来穗居委会查询接口
	*/
	LS_QUERY_JWH_URL("mobile/appComQuery/queryJwhdmList"),
	/**
	* 来穗派出所查询接口
	*/
	LS_QUERY_PCS_URL("mobile/appComQuery/queryPcsdmList"),
	/**
	* 来穗街道代码查询接口
	*/
	LS_QUERY_JDDM_URL("mobile/appComQuery/queryJddmList"),

	/**
	* 来穗接口查询证明材料
	*/
	LS_QUERY_ZMCL_LIST_URL("mobile/appComQuery/queryZmclList"),

	/**
	* 来穗接口查询随行人员
	*/
	LS_QUERY_SXRY_LIST_URL("mobileApp/mobileAppQuery/querySxryList"),
	/**
	* 分页查询随行人员信息
	*/
	LS_QUERY_SXRY_PAGE_URL("mobile/appComQuery/querySxryPage"),
	/**
	* 分页查询移动巡查房屋信息
	*/
	LS_QUERY_YDCJFWXXFH_PAGE_URL("mobile/appComQuery/queryYdcjFwxxfhPage"),
	/**
	* 分页查询居住证信息
	*/
	LS_QUERY_JZZXX_PAGE_URL("mobile/appComQuery/queryJzzxxPage"),
	/**
	* 来穗房屋产权人信息
	*/
	LS_QUERY_CQR_LIST_URL("mobileApp/mobileAppQuery/queryCqrList"),
	/**
	* 分页查询产权人信息
	*/
	LS_QUERY_CQR_PAGE_URL("mobileApp/mobileAppQuery/queryCqrPage"),
	/**
	* 来穗房屋产权单位信息
	*/
	LS_QUERY_CQDW_LIST_URL("mobileApp/mobileAppQuery/queryCqdwList"),
	/**
	* 来穗房屋产权单位信息分页
	*/
	LS_QUERY_CQDW_PAGE_URL("mobileApp/mobileAppQuery/queryCqdwPage"),
	/**
	* 查询关注人员列表
	*/
	LS_QUERY_GZRY_LIST_URL("mobile/appComQuery/queryGzryList"),
	/**
	* 来穗房屋照片信息查询
	*/
	LS_QUERY_FWXXPHOTO_LIST_URL("mobileApp/mobileAppQuery/queryFwxxPhotoList"),
	/**
	* 来穗房屋照片信息查询
	*/
	LS_QUERY_GAXZ_PAGE_URL("mobileApp/mobileAppQuery/queryGaXzPage"),
	/**
	 * 
	 */
	LS_QUERY_QUERY_JZZYW_PAGE("mobileApp/mobileAppQuery/queryJzzywPage"),
	/**
	* 来穗房屋信息证明材料
	*/
	LS_QUERY_FWXXZMCL_LIST_URL("mobile/appComQuery/queryFwxxZmclList"),

	/**
	* 来穗接口查询随行人员
	*/
	LS_QUERY_ICZP_INFO_URL("ls_query_iczp_info"),

	/**
	* 来穗街镇查询接口
	*/
	LS_QUERY_JZ_PAGE_URL("mobile/appComQuery/queryJzdmPage"),
	/**
	* 来穗居委会查询接口
	*/
	LS_QUERY_JWH_PAGE_URL("mobile/appComQuery/queryJwhdmPage"),
	/**
	* 来穗派出所查询接口
	*/
	LS_QUERY_PCS_PAGE_URL("mobile/appComQuery/queryPcsdmPage"),
	/**
	* 来穗街道代码查询接口
	*/
	LS_QUERY_JDDM_PAGE_URL("mobileApp/mobileAppQuery/queryJddmPage"),

	/**
	* 来穗接口请求参数健
	*/
	LS_QUERY_DWXX_URL("mobile/appComQuery/queryDwxxPage"),

	/**
	* 居住证校验接口请求参数健
	*/
	LS_CHECK_JZZ_URL("mobile/appComQuery/checkJzz"),

	/**
	* 居住证卡面信息接口请求参数健
	*/
	LS_JZZ_CARD_INFO("mobile/appComQuery/jzzCardInfo"),

	/**
	* 居住证业务信息接口请求参数健
	*/
	LS_JZZ_YW_CARD_INFO("mobile/appComQuery/jzzywCardInfo"),
	/**
	* 来穗登记天数请求参数健
	*/
	LS_LIVE_CARD_CHECK_URL("mobile/appComQuery/liveCardCheck"),
	/**
	* 查询日常巡查隐患照片列表
	*/
	LS_QUERY_RCXC_YHZP_LIST_URL("mobile/appComQuery/queryRcxcYhzpList"),

	/**
	* 查询日常巡查分页
	*/
	LS_QUERY_RCXC_PAGE_URL("mobileApp/mobileAppQuery/queryRcjcLsPage"),
	/**
	* 查询日常巡查详情
	*/
	LS_QUERY_RCXC_DETAIL_URL("mobileApp/mobileAppQuery/queryRcjcLsDetail"),

	/**
	* 来穗登记天数请求参数健
	*/
	LS_DOWN_IMAGE_URL("mobileApp/mobileAppQuery/downloadImage"),

	/**
	* 查询房屋租赁合同信息
	*/
	LS_QUERY_FWZLHTXX_PAGE_URL("mobile/appComQuery/queryFwzlhtxxPage"),

	/**
	* 查询房屋租赁合同信息列表
	*/
	LS_QUERY_FWZLHTXX_LIST_URL("mobile/appComQuery/queryFwzlhtxxList"),
	/**
	* 查询房屋租赁合同详细信息detail
	*/
	LS_QUERY_FWZLHTXX_DETAIL_URL("mobile/appComQuery/queryFwzlhtxxDetail"),
	/**
	* 查询房屋租赁合同详细信息个数
	*/
	LS_QUERY_FWZLHTXX_COUNT_URL("mobile/appComQuery/queryFwzlhtxxCount"),
	/**
	* 查询居住证信息数量
	*/
	LS_QUERY_JZZXX_COUNT_URL("mobile/appComQuery/queryJzzxxCount"),

	/**
	* 居住证业务分页查询
	*/
	LS_QUERY_JZZYW_PAGE_URL("mobile/appComQuery/queryJzzywPage"),
	/**
	* 查询房屋租赁合同人员信息
	*/
	LS_QUERY_FWZLRYXX_PAGE_URL("mobile/appComQuery/queryFwzlryxxPage"),

	/**
	* 查询房屋租赁合同信息列表
	*/
	LS_QUERY_FWZLRYXX_LIST_URL("mobile/appComQuery/queryFwzlryxxList"),
	/**
	* 查询房屋租赁人员详细信息
	*/
	LS_QUERY_FWZLRYXX_DETAIL_URL("mobile/appComQuery/queryFwzlryxxDetail"),

	/**
	* 查询房屋租赁合同契税信息
	*/
	LS_QUERY_FWZLQSXX_PAGE_URL("mobile/appComQuery/queryFwzlqsxxPage"),

	/**
	* 查询房屋租赁合同契税信息列表
	*/
	LS_QUERY_FWZLQSXX_LIST_URL("mobile/appComQuery/queryFwzlqsxxList"),

	/**
	* 查询房屋租赁合同契税信息
	*/
	LS_QUERY_FWZLQSXX_COUNT_URL("mobile/appComQuery/queryFwzlqsxxCount"),

	/**
	* 来穗通#预约
	*/
	LS_WEIX_SAVERESERVATION("mobileApp/wechatApi/saveReservationGeneric"),
	/**
	* 来穗通#上门居住登记
	*/
	LS_WEIX_SAVEREGISTRATION("mobileApp/wechatApi/saveRegistrationGeneric"),
	/**
	* 来穗通#预约登记取消
	*/
	LS_WEIX_CCANCELYYDJ("mobileApp/wechatApi/cancelYydj"),
	/**
	* 来穗通#取消上门核实预约
	*/
	LS_WEIX_CANCELSMHSYY("mobileApp/wechatApi/cancelSmhsyy"),
	/**
	* 来穗通#查询来穗人员办理居住证条件
	*/
	LS_WEIX_QUERYJZZCHECK("mobileApp/wechatApi/queryJzzCheck"),
	/**
	* 来穗通#查询预约日期时间段
	*/
	LS_WEIX_QUERYYYSZLIST("mobileApp/wechatApi/queryYyszList"),
	/**
	* 来穗通#查询预约日期上门核实时间段
	*/
	LS_WEIX_QUERYSMHSSZLIST("mobileApp/wechatApi/querySmhsszList"),
	/**
	* 来穗通#查询我的预约表
	*/
	LS_WEIX_MYRESERVATIONLIST("mobileApp/wechatApi/myReservationList"),
	/**
	* 来穗通#查询单个我的预约详情
	*/
	LS_WEIX_QUERYMYYYDETAIL("mobileApp/wechatApi/queryMyYyDetail"),
	/**
	* 来穗通#查询用户信息
	*/
	LS_WEIX_QUERYWEIXUSER("mobileApp/wechatApi/queryWeixUser"),

	/**
	* 用户注册&登录
	*/
	LS_WEIX_APPLOGIN("mobileApp/wechatApi/AppLogin"),
	/**
	 * 街镇查询
	 */
	LS_WEIX_JZDM_PAGE("/mobileApp/wechatApi/queryJzdmPage"),
	/**
	 * 村居委查询
	 */
	LS_WEIX_JWHDM_PAGE("/mobileApp/wechatApi/queryJwhdmPage"),

	/**
	 * 派出所查询
	 */
	LS_WEIX_PCSDM_PAGE("/mobileApp/wechatApi/queryPcsdmPage"),
	/**
	 * 街路巷查询
	 */
	LS_WEIX_JDDM_PAGE("/mobileApp/wechatApi/queryJddmPage"),
	/**
	 * 房屋门牌
	 */
	LS_WEIX_FWXXZ_PAGE("/mobileApp/wechatApi/queryFwxxzPage"),
	/**
	 * 单元详址查询
	 */
	LS_WEIX_FWXXFH_PAGE("/mobileApp/wechatApi/queryFwxxfhPage"),
	/**
	 * 字典项
	 */
	LS_WEIX_DICT("/mobileApp/wechatApi/queryDictionaryList"),
	/**
	 * 企业信息
	 */
	LS_WEIX_DWXX("/mobileApp/wechatApi/queryDwxxPage"),
	/**
	 * 查询我的窗口预约表
	 */
	LS_WEIX_MYYYLIST("mobileApp/wechatApi/queryMyYyList"),
	/**
	 * 查询单个我的窗口预约详情
	 */
	LS_WEIX_QUERYMYREGIS("mobileApp/wechatApi/queryMyRegist"),
	/**
	 * 查询来穗人员年内累计登记天数
	 */
	LS_WEIX_QUERYLIVECARDDAYS("mobileApp/wechatApi/queryLiveCardDays"),
	/**
	 * 来穗人员登记信息详细查询
	 */
	LS_WEIX_QUERYLDRYDETAIL("mobileApp/wechatApi/queryLdryDetail"),
	/**
	 * 更新预约窗口登记（未测试，未使用） 
	 */
	LS_WEIX_UPDATERESERVATION("mobileApp/wechatApi/updateReservation"),
	/**
	 * 更新预约上门登记 
	 */
	LS_WEIX_UPDATEREGISTRATION("mobileApp/wechatApi/updateRegistration"),
	/**
	 * 查询服务站信息
	 */
	LS_WEIX_QUERYFWZPAGE("mobileApp/wechatApi/queryFwzPage");

	private String value;

	private LsUri(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
