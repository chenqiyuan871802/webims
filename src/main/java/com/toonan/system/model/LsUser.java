package com.toonan.system.model;

import java.util.Date;

/**
 * <p>
 * 系统用户操作员表
 * </p>
 *
 * @author 肖意
 * @since 2020-10-24
 */
public class LsUser  {


    /**
     * 操作员ID
     */
    private String czyid;
    /**
     * 人员ID
     */
    private String ryid;
    /**
     * 登录名
     */
    private String dlm;
    /**
     * 登录口令
     */
    private String dlkl;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 是否注销
     */
    private String sfzx;
    /**
     * 行政区
     */
    private String xzq;
    /**
     * 街镇
     */
    private String jz;
    /**
     * 派出所
     */
    private String pcs;
    /**
     * 居委会
     */
    private String jwh;
    /**
     * 查看信息级别
     */
    private String jb;
    /**
     * 用户级别
     */
    private String yhjb;
    /**
     * 地域名称
     */
    private String dymc;
    /**
     * 地域范围
     */
    private String dyfw;
    /**
     * 信息等级
     */
    private String xxdj;
    /**
     * 社区警务室
     */
    private String sqjws;
    /**
     * 注销时间
     */
    private Date zxsj;
    /**
     * 注销原因
     */
    private String zxyy;
    /**
     * 证件号码
     */
    private String zjhm;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 证件类别
     */
    private String zjlb;
    /**
     * 性别
     */
    private String xb;
    /**
     * 出生日期
     */
    private Date csrq;
    /**
     * 民族
     */
    private String mz;
    /**
     * 政治面貌
     */
    private String zzmm;
    /**
     * 文化程度
     */
    private String whcd;
    /**
     * 是否本市人员
     */
    private String sfbsry;
    /**
     * 受理号
     */
    private String slh;
    /**
     * 联系地址
     */
    private String lxdz;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 邮政编码
     */
    private String yzbm;
    /**
     * 数据范围
     */
    private String dataScale;
    /**
     * 创建人
     */
    private String cjr;
    /**
     * 创建时间
     */
    private Date cjsj;
    /**
     * 最后修改人
     */
    private String zhxgr;
    /**
     * 最后修改时间
     */
    private Date zhxgsj;
    /**
     * 是否管理员
     */
    private String sfgly;
    /**
     * 登录错误次数
     */
    private Double errorcount;
    /**
     * 锁定时间
     */
    private Date locktime;


    public String getCzyid() {
        return czyid;
    }

    public void setCzyid(String czyid) {
        this.czyid = czyid;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getDlm() {
        return dlm;
    }

    public void setDlm(String dlm) {
        this.dlm = dlm;
    }

    public String getDlkl() {
        return dlkl;
    }

    public void setDlkl(String dlkl) {
        this.dlkl = dlkl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSfzx() {
        return sfzx;
    }

    public void setSfzx(String sfzx) {
        this.sfzx = sfzx;
    }

    public String getXzq() {
        return xzq;
    }

    public void setXzq(String xzq) {
        this.xzq = xzq;
    }

    public String getJz() {
        return jz;
    }

    public void setJz(String jz) {
        this.jz = jz;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getJwh() {
        return jwh;
    }

    public void setJwh(String jwh) {
        this.jwh = jwh;
    }

    public String getJb() {
        return jb;
    }

    public void setJb(String jb) {
        this.jb = jb;
    }

    public String getYhjb() {
        return yhjb;
    }

    public void setYhjb(String yhjb) {
        this.yhjb = yhjb;
    }

    public String getDymc() {
        return dymc;
    }

    public void setDymc(String dymc) {
        this.dymc = dymc;
    }

    public String getDyfw() {
        return dyfw;
    }

    public void setDyfw(String dyfw) {
        this.dyfw = dyfw;
    }

    public String getXxdj() {
        return xxdj;
    }

    public void setXxdj(String xxdj) {
        this.xxdj = xxdj;
    }

    public String getSqjws() {
        return sqjws;
    }

    public void setSqjws(String sqjws) {
        this.sqjws = sqjws;
    }

    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjlb() {
        return zjlb;
    }

    public void setZjlb(String zjlb) {
        this.zjlb = zjlb;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    public String getSfbsry() {
        return sfbsry;
    }

    public void setSfbsry(String sfbsry) {
        this.sfbsry = sfbsry;
    }

    public String getSlh() {
        return slh;
    }

    public void setSlh(String slh) {
        this.slh = slh;
    }

    public String getLxdz() {
        return lxdz;
    }

    public void setLxdz(String lxdz) {
        this.lxdz = lxdz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

   

    public String getCjr() {
        return cjr;
    }

    public String getDataScale() {
		return dataScale;
	}

	public void setDataScale(String dataScale) {
		this.dataScale = dataScale;
	}

	public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getZhxgr() {
        return zhxgr;
    }

    public void setZhxgr(String zhxgr) {
        this.zhxgr = zhxgr;
    }

    public Date getZhxgsj() {
        return zhxgsj;
    }

    public void setZhxgsj(Date zhxgsj) {
        this.zhxgsj = zhxgsj;
    }

    public String getSfgly() {
        return sfgly;
    }

    public void setSfgly(String sfgly) {
        this.sfgly = sfgly;
    }

    public Double getErrorcount() {
        return errorcount;
    }

    public void setErrorcount(Double errorcount) {
        this.errorcount = errorcount;
    }

    public Date getLocktime() {
        return locktime;
    }

    public void setLocktime(Date locktime) {
        this.locktime = locktime;
    }

  

    @Override
    public String toString() {
        return "SysUser{" +
        "czyid=" + czyid +
        ", ryid=" + ryid +
        ", dlm=" + dlm +
        ", dlkl=" + dlkl +
        ", email=" + email +
        ", sfzx=" + sfzx +
        ", xzq=" + xzq +
        ", jz=" + jz +
        ", pcs=" + pcs +
        ", jwh=" + jwh +
        ", jb=" + jb +
        ", yhjb=" + yhjb +
        ", dymc=" + dymc +
        ", dyfw=" + dyfw +
        ", xxdj=" + xxdj +
        ", sqjws=" + sqjws +
        ", zxsj=" + zxsj +
        ", zxyy=" + zxyy +
        ", zjhm=" + zjhm +
        ", xm=" + xm +
        ", zjlb=" + zjlb +
        ", xb=" + xb +
        ", csrq=" + csrq +
        ", mz=" + mz +
        ", zzmm=" + zzmm +
        ", whcd=" + whcd +
        ", sfbsry=" + sfbsry +
        ", slh=" + slh +
        ", lxdz=" + lxdz +
        ", lxdh=" + lxdh +
        ", yzbm=" + yzbm +
        ", datascale=" + dataScale +
        ", cjr=" + cjr +
        ", cjsj=" + cjsj +
        ", zhxgr=" + zhxgr +
        ", zhxgsj=" + zhxgsj +
        ", sfgly=" + sfgly +
        ", errorcount=" + errorcount +
        ", locktime=" + locktime +
        "}";
    }
}
