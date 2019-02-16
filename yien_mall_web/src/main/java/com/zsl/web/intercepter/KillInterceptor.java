package com.zsl.web.intercepter;

import com.google.gson.Gson;
import com.zsl.common.utils.Msg;
import com.zsl.web.intercepter.service.KillTimeServiceDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 进入秒杀界面的拦截器
 */
public class KillInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(KillInterceptor.class);

	@Autowired
	private KillTimeServiceDo killTimeServiceDo;

	@Autowired
	private Gson gson;

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
		Boolean bool = killTimeServiceDo.validationKillOpen();
		if (!bool) {
			//重置response，并设置respose的格式
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			Msg msg = new Msg();
			//设置状态码，当为300 的时候，则表示还未开启秒杀
			msg.setCode(300);
			msg.addInfo("秒杀暂时未开启,敬请期待！");
			String s = gson.toJson(msg);
			pw.print(s);//消息
			pw.flush();
			pw.close();
		}
		return  true;
	}

}