package com.ruoyi.system.log.aop;


import com.alibaba.fastjson2.JSON;
import com.ruoyi.system.log.AccessAddressUtils;
import com.ruoyi.system.log.LoggerUtils;
import com.ruoyi.system.log.dto.LoggerEntity;
import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : qzy
 * @since : 2024/1/24 16:36
 * <p style='color:yellow'>
 *     通用请求日志打印处理类，方便联调/测试，生产环境视情况可选择是否保留
 *
 * </p>
 **/
@Aspect
@Component
public class WebRequestLogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebRequestLogAspect.class);

	private final ThreadLocal<LoggerEntity> threadLocal = new ThreadLocal<>();

	private final ThreadLocal<Long> startTimeLocal = new ThreadLocal<>();


	@Pointcut("within(com.ruoyi.*.controller.*)")
	public void webRequestLog() {
	}

	@Before("webRequestLog()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			// 接收到请求，记录请求内容
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if (requestAttributes == null) {
				// 防止拦截的不是接口层，获取不到请求数据的情况
				threadLocal.remove();
				return;
			}
			ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
			HttpServletRequest request = attributes.getRequest();
			//处理特殊请求，直接返回
//			if (request.getRequestURI().contains("admin/sysLog")) {
//				threadLocal.remove();
//				return;
//			}

			String method = request.getMethod();
			String params;
			String post_code = "POST";
			if (post_code.equals(method)) {
				Object[] paramsArray = joinPoint.getArgs();
				params = argsArrayToString(paramsArray);
			} else {
				//获取请求参数信息
				params = JSON.toJSONString(request.getParameterMap());
			}

			LoggerEntity optLog = new LoggerEntity();
			//设置请求方法
			optLog.setId(RandomUtils.nextLong());
			optLog.setMethodCode(request.getMethod());
			//设置请求类型（json|普通请求）
			optLog.setTypeCode(LoggerUtils.getRequestType(request));
			//设置请求参数内容json字符串
			optLog.setParamData(params);
			//设置请求地址
			optLog.setUri(request.getRequestURI());
			//设置sessionId
			optLog.setSessionId(request.getSession().getId());
			//设置调用的java类
			optLog.setJavaBean(joinPoint.getSignature().getDeclaringTypeName());
			//设置调用的java方法
			optLog.setJavaMethod(joinPoint.getSignature().getName());
//            optLog.setParamData(params);
			//设置调用方ip
			optLog.setClientIp(AccessAddressUtils.getIpAddress(request));
			threadLocal.set(optLog);
			startTimeLocal.set(System.currentTimeMillis());
			LOGGER.info(optLog.toLogStr());
		} catch (Exception e) {
			LOGGER.error("***操作请求日志记录失败doBefore()***", e);
		}
	}

	@AfterReturning(returning = "result", pointcut = "webRequestLog()")
	public void doAfterReturning(Object result) {
		// 处理完请求，返回内容
		if (threadLocal.get() == null) {
			return;
		}
		LoggerEntity optLog = threadLocal.get();
		Long startTime = startTimeLocal.get();
		optLog.setReturnData(JSON.toJSONString(result));
		optLog.setTimeConsuming((int) (System.currentTimeMillis() - startTime));
		LOGGER.info(optLog.toLogStr());
		threadLocal.remove();
		startTimeLocal.remove();
	}

	/**
	 * 请求参数拼装
	 * @return String
	 */
	private String argsArrayToString(Object[] paramsArray) {
		StringBuilder params = new StringBuilder();
		if (paramsArray != null) {
			for (Object o : paramsArray) {
				if (o instanceof MultipartFile || o instanceof MultipartFile[]) {
					// 当导入文件时，不打印输出二进制
					params.append("上传文件 ");
				} else {
					try {
						Object jsonObj = JSON.toJSON(o);
						params.append(jsonObj.toString()).append(" ");
					} catch (Exception e) {
						LOGGER.debug("参数json解析异常,{}", e.getMessage());
					}
				}
			}
		}
		return params.toString().trim();
	}

}
