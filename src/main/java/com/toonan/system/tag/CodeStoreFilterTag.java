package com.toonan.system.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.velocity.WebplusVelocity;
import com.toonan.core.vo.Item;
import com.toonan.system.model.Dict;
import com.toonan.system.tag.resource.TagResource;


/**
 * 
 * 类描述： 字典数据存储存储过滤标签
 * 创建人：陈骑元
 * 邮箱：240823329@qq.com
 * 创建时间：Oct 7, 2016 9:34:49 AM
 * 修改人：
 * 修改时间：
 * 修改备注： 
 * @version 1.0
 */
public class CodeStoreFilterTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(CodeStoreFilterTag.class);
	/**
	 * 字典名称
	 */
	private String field;
	/**
	 * 重命名标签函数
	 */
	private String rename;
	/**
	 * 过滤数据
	 */
	private String filter;
	
	

	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public String getRename() {
		return rename;
	}


	public void setRename(String rename) {
		this.rename = rename;
	}


	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}

    /**
     * 
     * 简要说明：判断字符串是否存在数组中
     * 编写者：陈骑元
     * 创建时间：2017年3月10日 下午5:09:51
     * @param 说明
     * @return 说明
     */
	public boolean exists(String[] array,String str){
		 if(WebplusUtil.isNotEmpty(array)&&WebplusUtil.isNotEmpty(str)){
			 for(int i=0;i<array.length;i++){
				 String strTemp=array[i];
				 if(str.equals(strTemp)){
					 return true;
				 }
			 }
		 }
		 return false;
		 
	}
	/**
	 * 标签开始
	 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append(WebplusCons.SCRIPT_START);
		Dto vmDto=Dtos.newDto();
		List<Item> codeList=WebplusCache.getItemList(field, filter);
		
		 if(WebplusUtil.isNotEmpty(rename)){
			 field=rename;
		 }
		 vmDto.put("field", field);
		 vmDto.put("codeList", codeList);
		 StringWriter writer = WebplusVelocity.mergeFileTemplate(TagResource.CODE_STORE_VM, vmDto);
		 sb.append(writer.toString()).append("\n");
	
		sb.append(WebplusCons.SCRIPT_END);
		try {
			pageContext.getOut().write(sb.toString());
		} catch (IOException e) {
			log.error("字典标签发生错误："+ e.getMessage());
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}
	 

	/**
	 * 标签结束
	 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		return super.EVAL_PAGE;
	}

	/**
	 * 释放资源
	 */
	public void release() {
		field = null;
		rename = null;
		filter = null;
		super.release();
	}

	

}
