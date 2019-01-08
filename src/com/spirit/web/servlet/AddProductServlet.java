package com.spirit.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.spirit.constant.Constant;
import com.spirit.domain.Category;
import com.spirit.domain.Product;
import com.spirit.service.ProductService;
import com.spirit.utils.BeanFactory;
import com.spirit.utils.UUIDUtils;
import com.spirit.utils.UploadUtils;
import com.spirit.web.servlet.base.BaseServelt;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 保存商品
 * @author Spirit
 *
 */
public class AddProductServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//0.使用fileuload保存图片和将商品的信息放入map中
			//0.1创建map集合  存放商品信息
			Map<String, Object> map = new HashMap<>();
			
			//0.2创建磁盘文件项工厂（设置临时文件的大小和位置）
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			//0.3创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//0.4解析request
			List<FileItem> list = upload.parseRequest(request);
			
			//0.5遍历list 获取每一个文件项
			for (FileItem fi : list){
				//0.6获取name属性的值
				String key = fi.getFieldName();
				
				//0.7判断是否是普通上传组件
				if (fi.isFormField()) {
					//普通
					map.put(key, fi.getString());
				} else {
					//文件
					//a.获取文件的名称
					String name = fi.getName();
					
					//b.获取文件的真实名称
					String realName = UploadUtils.getRealName(name);
					
					//c.获取文件的随机名称
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//d.获取随机目录
					String dir = UploadUtils.getDir();
					
					//e.获取文件内容（输入流）
					InputStream is = fi.getInputStream();
					
					//f.创建输出流
					//获取produs的目录的真实路径
					String productPath = getServletContext().getRealPath("/products");
					//创建随机目录
					File dirFile = new File(productPath,dir);
					if (!dirFile.exists()) {
						dirFile.mkdirs();
					}
					//目录
					FileOutputStream os = new FileOutputStream(new File(dirFile,uuidName));
					
					//g.对拷流
					IOUtils.copy(is, os);
					
					//h.释放资源
					os.close();
					is.close();
					
					//i.删除临时文件
					fi.delete();
					
					//j.将商品的路径放入map
					map.put(key, "products"+dir+"/"+uuidName);
				}
				
			}
			
			//1.封装product对象
			Product p = new Product();
			//1.1.手动设置pid
			map.put("pid", UUIDUtils.getId());
			
			//1.2.手动设置pdate
			map.put("pdate", new Date());
			
			//1.3.手动设置pflag 上架
			map.put("pflag", Constant.PRODUCT_IS_UP);
			
			//1.4.使用beanutils封装
			BeanUtils.populate(p, map);
			//1.5.手动设置category
			Category c = new Category();
			c.setCid((String) map.get("cid"));
			p.setCategory(c);
			
			//2.调用service完成保存
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.save(p);
			
			//3.重定向
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存商品失败");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
