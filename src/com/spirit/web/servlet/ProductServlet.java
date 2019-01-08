package com.spirit.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spirit.domain.PageBean;
import com.spirit.domain.Product;
import com.spirit.service.ProductService;
import com.spirit.service.impl.ProductServiceImpl;
import com.spirit.web.servlet.base.BaseServelt;

/**
 * 商品模块
 *
 */
public class ProductServlet extends BaseServelt {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 分类商品分页展示
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.获取pagenumber cid 设置pagesize
			//SString parameter = request.getParameter("pageNumber");
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (NumberFormatException e) {
			}
			
			int pageSize = 12;
			String cid = request.getParameter("cid");

			//2.调用service	分页查询商品 参数：3个，返回值：pagebean
			ProductService ps = new ProductServiceImpl();
			PageBean<Product> bean = ps.findByPage(pageNumber,pageSize,cid);
			
			//3.将pagebean放入request域中，请求转发product_list.jsp
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			request.setAttribute("msg", "分页查询失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_list.jsp";
	}
	
	/**
	 * 商品详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.获取pid
			String pid = request.getParameter("pid");
			
			//2.调用service获取单个商品参数：pid 返回值：product
			ProductService ps = new ProductServiceImpl();
			Product pro = ps.getById(pid);
			
			//3.将product放入request域中，请求转发/jsp/product_info.jsp
			request.setAttribute("bean", pro);
		} catch (Exception e) {
			request.setAttribute("msg","查询单个商品失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_info.jsp";
	}
}
