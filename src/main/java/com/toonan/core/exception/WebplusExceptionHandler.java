package com.toonan.core.exception;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toonan.core.constant.WebplusCons;
import com.toonan.core.vo.R;



/**
 * 异常处理器
 * 
 */
@ControllerAdvice
@ResponseBody
public class WebplusExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(WebplusException.class)
	public R handleBDException(WebplusException e) {
		R r = new R();
		r.put(WebplusCons.APPCODE_KEY, e.getAppcode());
		r.put(WebplusCons.APPCODE_KEY, e.getMessage());

		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
	public R noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
		logger.error(e.getMessage(), e);
		return R.error("没找找到页面");
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e) {
		logger.error(e.getMessage(), e);
		return R.error("未授权");
	}
	/**
	 * 
	 * 简要说明：token超时
	 * 编写者：陈骑元
	 * 创建时间：2019年5月12日 下午6:27:24
	 * @param 说明
	 * @return 说明
	 */
	@ExceptionHandler(AuthenticationException.class)
	public R handleAuthenticationException(AuthenticationException e) {
		logger.error(e.getMessage(), e);
		return R.error("未授权");
	}
	/**
	 * 
	 * 简要说明：未授权异常
	 * 编写者：陈骑元
	 * 创建时间：2019年5月12日 下午3:30:43
	 * @param 说明
	 * @return 说明
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public R handleUnauthorizedException(UnauthorizedException e) {
		logger.error(e.getMessage(), e);
		return R.error("该用户未授权这个功能模块，请联系管理授权");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error("服务器错误，请联系管理员");
	}
	
	
	
	@ResponseBody
    @ExceptionHandler(value = BindException.class)
    public R handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        //return CommonResult.validateFailed(message);
        return R.error(message);
    }
	
	/**
     * 用来处理bean validation异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public  R resolveConstraintViolationException(ConstraintViolationException ex){
    	//CommonResult<?> errorWebResult = CommonResult.validateFailed();
    	
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if(!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder msgBuilder = new StringBuilder();
            for(ConstraintViolation<?> constraintViolation :constraintViolations){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if(errorMessage.length()>1){
                errorMessage = errorMessage.substring(0,errorMessage.length()-1);
            }
            //errorWebResult.setMessage(errorMessage);
            //return errorWebResult;
            return R.error(errorMessage);
        }
        //errorWebResult.setMessage(ex.getMessage());
        //return errorWebResult;
        return R.error(ex.getMessage()); 
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    	//CommonResult<?> errorWebResult = CommonResult.validateFailed();
        List<ObjectError>  objectErrors = ex.getBindingResult().getAllErrors();
        if(!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            //errorWebResult.setMessage(errorMessage);
            //return errorWebResult;
            return R.error(errorMessage);
        }
        //errorWebResult.setMessage(ex.getMessage());
        //return errorWebResult;
        return R.error(ex.getMessage());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R illegalArgumentException(IllegalArgumentException ex){
        return R.error(ex.getMessage());
    }
    
}
