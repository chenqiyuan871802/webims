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
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.velocity.WebplusVelocity;
import com.toonan.core.vo.Item;
import com.toonan.system.tag.resource.TagResource;




/**
 * 
 * 类描述： 多个字典值标签格式化
 * 创建人：陈骑元
 * 邮箱：240823329@qq.com
 * 创建时间：Oct 7, 2016 9:34:49 AM
 * 修改人：
 * 修改时间：
 * 修改备注： 
 * @version 1.0
 */
public class MultiCodeFormatterTag extends TagSupport {

	
		/**  描述   (@author: wangjianglong) */      
	    
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(MultiCodeFormatterTag.class);
	/**
	 * 字典键字典 多个使用 WebplusCons.MARK_CSV
	 */
	private String fields;
	/**
	 * 字典代码分割标签
	 */
	private String inMark;
	/**
	 * 字典代码值输出分割标签
	 */
	private String outMark;

	/**
	 * 标签开始
	 */
	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append(WebplusCons.SCRIPT_START);
		Dto vmDto=Dtos.newDto();
		String[] arrayFields = fields.split(WebplusCons.MARK_CSV);
		if(WebplusUtil.isEmpty(inMark)){
			inMark=WebplusCons.MARK_CSV;
		}
		if(WebplusUtil.isEmpty(outMark)){
			outMark=WebplusCons.MARK_PAUSE;
		}
		for (int i = 0; i < arrayFields.length; i++) {
			String dictKey=arrayFields[i];
			List<Item> codeList=WebplusCache.getItemList(dictKey);
			vmDto.put("field", arrayFields[i]);
			vmDto.put("codeList", codeList);
			vmDto.put("inMark", inMark);
			vmDto.put("outMark", outMark);
			StringWriter writer = WebplusVelocity.mergeFileTemplate(TagResource.MULTICODE_FORMATTER_VM, vmDto);
			sb.append(writer.toString()).append("\n");
		}
		sb.append(WebplusCons.SCRIPT_END);
		try {
			pageContext.getOut().write(sb.toString());
		} catch (IOException e) {
			log.error("字典格式化标签发生错误："+ e.getMessage());
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	public String getInMark() {
		return inMark;
	}

	public void setInMark(String inMark) {
		this.inMark = inMark;
	}

	public String getOutMark() {
		return outMark;
	}

	public void setOutMark(String outMark) {
		this.outMark = outMark;
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
		inMark=null;
		outMark=null;
		super.release();
	}

	public void setFields(String fields) {
		this.fields = fields;
	}


}
