package com.toonan.system.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.velocity.WebplusVelocity;
import com.toonan.core.vo.Item;
import com.toonan.system.model.Dict;
import com.toonan.system.tag.resource.TagResource;



/**
 * 
 * 类描述： 字典数据存储标签
 * 创建人：陈骑元
 * 邮箱：240823329@qq.com
 * 创建时间：Oct 7, 2016 9:34:49 AM
 * 修改人：
 * 修改时间：
 * 修改备注： 
 * @version 1.0
 */
public class CodeStoreTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(CodeStoreTag.class);
	private String fields;

	/**
	 * 标签开始
	 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append(WebplusCons.SCRIPT_START);
		Dto vmDto=Dtos.newDto();
		String[] arrayFields = fields.split(WebplusCons.MARK_CSV);
		
		for (int i = 0; i < arrayFields.length; i++) {
			String dictKey=arrayFields[i];
			List<Item> codeList=WebplusCache.getItemList(dictKey);
			vmDto.put("field", arrayFields[i]);
			vmDto.put("codeList", codeList);
			StringWriter writer = WebplusVelocity.mergeFileTemplate(TagResource.CODE_STORE_VM, vmDto);
			sb.append(writer.toString()).append("\n");
		}
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
		fields = null;
		super.release();
	}

	public void setFields(String fields) {
		this.fields = fields;
	}


}
