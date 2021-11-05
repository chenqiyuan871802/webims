package com.toonan.system.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.minio.MinioClientUtil;
import com.toonan.core.util.WebplusFile;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.R;
import com.toonan.core.vo.ResultFileUpload;
import com.toonan.core.web.BaseController;
import com.toonan.system.constant.SystemCons;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;



/**
 * 
 * 类名:com.ims.business.controller.FileController
 * 描述:文件操作控制类
 * 编写者:陈骑元
 * 创建时间:2019年7月9日 上午8:20:26
 * 修改说明:
 */
@Api(tags = "文件操作控制类")
@Controller
@RequestMapping("/system/file")
public class FileController extends BaseController{
	
	
	/**
	 * 显示图片
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showImage")
	public void showImage(String fileName,HttpServletRequest request,HttpServletResponse response) {
		if(WebplusUtil.isNotEmpty(fileName)){
			String folderPath=WebplusCache.getParamValue(WebplusCons.SAVE_ROOT_PATH_KEY	);
			String filePath=folderPath+File.separator;
			if(fileName.indexOf("Q")>-1){//
				 filePath=folderPath+File.separator+WebplusCons.QRCODE_PATH;
			}else {
				 filePath=folderPath+File.separator+WebplusCons.IMAGE_PATH;
			}
			
			filePath+=File.separator+fileName;
			
			WebplusFile.downloadFile(request, response,  filePath, fileName);
		}
		
		
		
	}
	/**
	 * 
	 * 简要说明：下载文件
	 * 编写者：陈骑元
	 * 创建时间：2019年8月26日 下午5:55:50
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping(value = "downloadFile")
	public  void downloadFile(String fileName,HttpServletRequest request,HttpServletResponse response){
		if(WebplusUtil.isNotEmpty(fileName)){
			String folderPath=WebplusCache.getParamValue(WebplusCons.SAVE_ROOT_PATH_KEY	);
			String filePath=folderPath+File.separator+WebplusCons.FILE_PATH+File.separator+fileName;;
			
			WebplusFile.downloadFile(request, response,  filePath, fileName);
		}
	}
	/**
	 * 
	 * 简要说明：下载APK方法
	 * 编写者：陈骑元
	 * 创建时间：2019年10月25日 下午11:03:57
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping(value = "downloadApk")
	public void downloadApk(String fid,HttpServletRequest request,HttpServletResponse response) {
		
		if(WebplusUtil.isNotEmpty(fid)){
			String folderPath=WebplusCache.getParamValue(WebplusCons.SAVE_ROOT_PATH_KEY	);
			String filePath=folderPath+File.separator+WebplusCons.APK_PATH+File.separator+fid	;
			WebplusFile.downloadFile(request, response,  filePath, fid);
		}
		
	}
	/**
	 * 
	 * 简要说明：下载导入模板文件
	 * 编写者：陈骑元
	 * 创建时间：2019年8月26日 下午5:55:50
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping(value = "downloadTemplateFile")
	public  void downloadTemplateFile(String fileName,HttpServletRequest request,HttpServletResponse response){
		if(WebplusUtil.isNotEmpty(fileName)){
			String filePath="";
			try {
				filePath = ResourceUtils.getURL("classpath:static").getPath()+File.separator+"template"+File.separator+fileName;
				WebplusFile.downloadFile(request, response,  filePath, fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
	}
	/**
	 * 
	 * 简要说明： 新增信息保存 22
	 * 编写者：陈骑元
	 * 创建时间：2019-02-23
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("uploadImage")
	@ResponseBody
	public R uploadImage(@RequestParam(value = "file", required = false) MultipartFile file,String fileType,
			HttpServletRequest request,HttpServletResponse response) {
		if(WebplusUtil.isNotEmpty(file)&&file.getSize()>0){
			
			try {
				String rootPath=WebplusCache.getParamValue(WebplusCons.SAVE_ROOT_PATH_KEY);
				String folderPath=rootPath+File.separator+WebplusCons.IMAGE_PATH;
				String imageName=file.getOriginalFilename();
				String imageFileName=WebplusUtil.createFileId()+"."+WebplusFile.getFileType(imageName);
				WebplusFile.createFolder(folderPath); 
				File targetFile = new File(folderPath,imageFileName); 
				file.transferTo(targetFile);
				return R.ok().put("fileName", imageName).put("fid", imageFileName);
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
			return R.error("上传文件失败");
			 
		}
       
		return R.error("无文件流上传");
	}
	/**
	 * 
	 * 简要说明： 多文件上传
	 * 编写者：陈骑元
	 * 创建时间：2019-02-23
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("uploadMoreImage")
	@ResponseBody
	public R uploadMoreImage(HttpServletRequest request,HttpServletResponse response) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		
		try {
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Iterator<String> fileForms = multipartRequest.getFileNames();
				String rootPath=WebplusCache.getParamValue(WebplusCons.SAVE_ROOT_PATH_KEY);
				String folderPath=rootPath+File.separator+WebplusCons.IMAGE_PATH;
				
				List<Dto> dataList=Lists.newArrayList();
				while (fileForms.hasNext()) {
					String fileForm = fileForms.next();// 文件提交表单域名称
					List<MultipartFile> files = multipartRequest.getFiles(fileForm);
					for (int i = 0; i < files.size(); i++) {
						MultipartFile file = files.get(i);
						if (file != null && file.getSize() > 0) { // 如果存在上传文件

							String imageName = file.getOriginalFilename();
							String imageFileName = WebplusUtil.createFileId()+ "." + WebplusFile.getFileType(imageName);
							WebplusFile.createFolder(folderPath);
							File targetFile = new File(folderPath, imageFileName);
							file.transferTo(targetFile);
							Dto dataDto=Dtos.newDto();
							dataDto.put("fileName", imageFileName);
							dataList.add(dataDto);
						}
					}
				}
				return R.toList(dataList);

			} 
		} catch (Exception e) {
			return R.error();
		}
		return R.error();
	}
	
	/**
	 * 
	 * 简要说明：下载excel文文件
	 * 编写者：陈骑元
	 * 创建时间：2019年8月26日 下午5:55:50
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping(value = "downloadExcelFile")
	public  void downloadExcelFile(String fid,String fileName,HttpServletRequest request,HttpServletResponse response){
		if(WebplusUtil.isNotEmpty(fid)){
			String folderPath=WebplusCache.getParamValue(WebplusCons.SAVE_ROOT_PATH_KEY	);
			String filePath=folderPath+File.separator+WebplusCons.EXCEL_PATH+File.separator+fid;;
			if(WebplusUtil.isEmpty(fileName)){
				fileName=fid;
			}
			WebplusFile.downloadFile(request, response,  filePath, fileName);
		}
	}
	
	/**
	 * <p>上传文件到临时空间</p> 
	 * @author 黄健达 (lsr@sina.com)
	 * @date: Sep 16, 2021 10:23:06 AM 
	 * @param files
	 * @return R
	*/
	@ApiOperation(value = "上传文件到临时空间")
	@ApiImplicitParams({
	    @ApiImplicitParam(value = "文件",required = true,name = "file",paramType = "form",dataType = "__file")
	})
	@RequestMapping(value = "updateFile",method = RequestMethod.POST)
    public R updateFile(MultipartFile file) {
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String fileName = WebplusUtil.uuid2();
        try {
            MinioClientUtil.uploadInputStream(SystemCons.FILE_SERVER_TMP, fileName, file.getInputStream(), contentType);
        } catch (IOException e) {
            String errMsg = String.format("上传异常，错误码：「%s」", WebplusUtil.uuid2());
            logger.error(errMsg, e);
            return R.error(errMsg);
        }
        ResultFileUpload resultFileUpload = new ResultFileUpload(fileName, originalFilename, contentType,size);
        return R.toData(resultFileUpload);
    }
}
