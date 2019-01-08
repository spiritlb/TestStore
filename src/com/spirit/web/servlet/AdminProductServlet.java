package com.spirit.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spirit.domain.Product;
import com.spirit.service.CategoryService;
import com.spirit.service.ProductService;
import com.spirit.utils.BeanFactory;
import com.spirit.web.servlet.base.BaseServelt;

/**
 * 后台商品
 * @author Spirit
 *
 */
public class AdminProductServlet extends BaseServelt {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 跳转到添加商品页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//调用categoryservice 查询所有分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			
			request.setAttribute("list", cs.findList());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}
	
	/**
	 * 展示已上架商品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.调用service查询已上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findAll();
			
			//2.将返回值放入request域中，请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/product/list.jsp";
	}
}
