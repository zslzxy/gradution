package com.zsl.web.shiro.configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这个过滤器主要是用于拦截ajax的请求，然后返回的是json数据，不会进行页面的跳转操作
 */
@Component("ApiCustomUser")
public class ApiCustomUserFilter extends UserFilter {

	/**
	 * 拦截时返回  JSON，而不是跳转到一个loginUrl
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
									 ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (isAjaxRequest(req)) {
			writeJson("您尚未登录", res);
		} else {
			redirectToLogin(request, response);
		}

		return false;
	}

	/**
	 * 是否是Ajax请求
	 *
	 * @param
	 * @return
	 * @throws
	 * @Description:
	 * @author pengbin <pengbin>
	 * 2018/11/26 10:58
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("x-requested-with");
		if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 输出JSON
	 *
	 * @param
	 * @return
	 * @throws
	 * @Description:
	 * @author pengbin <pengbin>
	 * 2018/11/26 10:59
	 */
	private void writeJson(String json, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			out = response.getWriter();
			out.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}