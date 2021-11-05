package com.toonan.core.aop;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.toonan.core.annotation.RepeatSubmit;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.vo.R;

@Aspect
@Component
public class RepeatSubmitAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String redisKeyTemplate;
    private final String identificationTemplate = "%s.%s(%s)";
    private final String datePattern = "yyyy年MM月dd日HH时mm分ss秒";

    @PostConstruct
    public void setup() {
        redisKeyTemplate = WebplusCons.CACHE_PREFIX_HEAD + "RepeatSubmit:";
    }

    @Around(value = "@annotation(around)")
    public Object around(ProceedingJoinPoint point, RepeatSubmit around) throws Throwable {
        Object proceed = null;
        long ttl = around.timeout();
        String identification = around.identification();
        String[] params = around.params();
        String redisKey = getParamValueHex(identification, params);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) ttl);
        String value = DateFormatUtils.format(calendar, datePattern);
        String setnx = WebplusCache.setnx(redisKey, (int) ttl, value);
        if ("0".equals(setnx)) {
            String text = WebplusCache.getString(redisKey);
            String content = String.format("该笔任务已经锁定，将于%s解锁。", StringUtils.isNotBlank(text) ? text : value);
            long savedTtl = WebplusCache.ttl(redisKey);
            if (savedTtl == -1) {
                Date lockTime = getLockTime(text);
                if (lockTime.before(Calendar.getInstance().getTime())) {
                    WebplusCache.expire(redisKey, 3);
                    content = "该笔任务超时锁定，３秒后自动解锁。请重新操作";
                }
            }
            return R.error(content);
        }
        try {
            proceed = point.proceed();
        } finally {
            WebplusCache.delString(redisKey);
        }
        return proceed;
    }

    private String getParamValueHex(String identification, String[] params) {
        StringBuilder sb = new StringBuilder(redisKeyTemplate + identification + WebplusCons.COLON);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String value = Stream.of(params).map(StringUtils::trim).distinct().sorted().map(e -> {
            String[] paramData = parameterMap.get(e);
            if (ArrayUtils.isEmpty(paramData)) {
                return WebplusCons.EMPTY;
            }
            String content = Stream.of(paramData).sorted().filter(StringUtils::isNotBlank).collect(Collectors.joining(WebplusCons.MARK_CSV));
            return String.format("%s=%s", e, content);
        }).collect(Collectors.joining(WebplusCons.HASH, WebplusCons.EMPTY, WebplusCons.EMPTY));
        sb.append(DigestUtils.md5Hex(value));
        return sb.toString();
    }

    private Date getLockTime(String text) {
        try {
            return DateUtils.parseDate(text, datePattern);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Calendar.getInstance().getTime();
    }

}
