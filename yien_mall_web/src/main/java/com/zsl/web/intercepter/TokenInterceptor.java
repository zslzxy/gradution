package com.zsl.web.intercepter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zsl.common.utils.Msg;
import com.zsl.web.anno.Token;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.UUID;

public class TokenInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

	/**
	 * 前置处理方法
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			//如果存在这个注解，则进行注解的验证操作
			if (annotation != null) {
				//判断注解的save方法为 true的话，生成对应的数据，并将其存放至请求域中
				boolean needSaveSession = annotation.save();
				if (needSaveSession) {
					String uuid = UUID.randomUUID().toString();
					log.debug("提交生成令牌" + uuid);
					request.getSession(false).setAttribute("token", uuid);
				}
				//判断是否需要移除token数据，如果为 true，执行移除token操作
				boolean needRemoveSession = annotation.remove();
				if (needRemoveSession) {
					//则验证是否为重复提交，如果token是重复提交，则直接进行拦截操作
					if (isRepeatSubmit(request)) {

						//重置response，并设置respose的格式
						response.reset();
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json;charset=UTF-8");
						PrintWriter pw = response.getWriter();
						Msg msg = new Msg();
						//设置状态码，当为300 的时候，则表示还未开启秒杀
						msg.setCode(300);
						msg.addInfo("请无重复提交表单数据");
						String s = gson.toJson(msg);
						pw.print(s);//消息
						pw.flush();
						pw.close();

						return false;
					}
					//移除token
					log.debug("提交移除令牌" + request.getSession().getAttribute("token"));
					request.getSession(false).removeAttribute("token");
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	/**
	 * 验证是否为重复提交
	 *
	 * @param request
	 * @return
	 */
	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute("token");
		//如果 session 为空的话，就表明是重复提交了
		if (serverToken == null) {
			return true;
		}
		//获取请求域中的参数token，如果参数是空的话，则表明已经是重复提交了
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {
			return true;
		}
		//如果两个对应的值不相等，则也表示重复提交了
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}
}