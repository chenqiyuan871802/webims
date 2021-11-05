package com.toonan.core.exception;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toonan.core.vo.R;

@Controller
public class WebplusErrorController implements ErrorController {
	private static final String ERROR_PATH = "/error";

	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public R handleError() {
		
		return R.error("服务器找不到相关请求地址信息，请确认请求地址是否正确");
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERROR_PATH;
	}

}