package com.spirit.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.spirit.constant.Constant;
import com.spirit.domain.User;
import com.spirit.service.UserServise;
import com.spirit.service.impl.UserServiseImpl;
import com.spirit.utils.UUIDUtils;
import com.spirit.web.servlet.base.BaseServelt;
	/**
	 * 用户模块
	 * @author Spirit
	 *
	 */
public class UserServlet extends BaseServelt {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户退出
	 */
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
		return null;
	}

	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		try {
			//1.获取用户名和密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//2.调用service完成登录 返回值user
			UserServise us = new UserServiseImpl();
			User user = us.login(username,password);
			
			//3.判断user	根据结果生成提示信息
			if (user == null) {
				//用户名和密码不匹配
				request.setAttribute("msg", "用户名和密码不匹配");
				return "/jsp/login.jsp";
			}
			//若用户不为空，继续判断是否激活
			if (Constant.USER_IS_ACTIVE != user.getState()) {
				//未激活
				request.setAttribute("msg", "请先去邮箱激活，在登录！");
				return "/jsp/msg.jsp";
			}
			
			//登录成功	保存用户的登录状态
			request.getSession().setAttribute("user", user);
			
			//记住用户名
			//判断是否勾选了记住用户名
			if (Constant.SAVE_NAME.equals(request.getParameter("savename"))) {
				Cookie c = new Cookie("saveName", URLEncoder.encode(username,"utf-8"));
				c.setMaxAge(Integer.MAX_VALUE);
				c.setPath(request.getContextPath()+"/");
				response.addCookie(c);
			}
			
			//跳转到index.jsp
			response.sendRedirect(request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "用户登录失败");
			return "/jsp/msg.jsp";
		}
		
		return null;
	}
	
	/**
	 * 跳转到登录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		return "/jsp/login.jsp";
	}
	
	/**
		 * 用户激活
		 * @param request
		 * @param response
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
	public String active(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		try {
			//1.接受code
			String code = request.getParameter("code");
			
			//2.调用service，完成激活，返回user
			UserServise us = new UserServiseImpl();
			User user = us.active(code);
			//3.判断user 生成不同的提示信息
			if (user == null) {
				//没有找到用户，激活失败
				request.setAttribute("msg","激活失败，请重新注册~");
				return "/jsp/msg.jsp";
			}
			//激活成功
			request.setAttribute("msg", "恭喜你，激活成功，请登录！");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			request.setAttribute("msg","激活失败，请重新注册~");
			return "/jsp/msg.jsp";
		}
	}
	
	/**
	 * 用户注册 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {

			try {
				//1.封装对象
				User user = new User();
				BeanUtils.populate(user, request.getParameterMap());
				
				//1.1手动封装uid
				user.setUid(UUIDUtils.getId());
				
				//1.2手动封装激活状态state
				user.setState(Constant.USER_IS_NOT_ACTIVE);
				
				//1.3手动封装code
				user.setCode(UUIDUtils.getCode());
				//System.out.println("获取的User对象"+user);
				//2.调用service 完成注册
				UserServise us = new UserServiseImpl();
				us.regist(user);
				//System.out.println("注册方法已进入!!!");
				//3.页面转发，提示信息
				request.setAttribute("msg", "恭喜你注册成功，请登录邮箱完成激活！");
			} catch (Exception e) {
				e.printStackTrace();
				//转发到msg.jsp
				request.setAttribute("msg","用户注册失败");
			}
		
		return "/jsp/msg.jsp";
	}
	
	
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		return "/jsp/register.jsp";
	}

}
