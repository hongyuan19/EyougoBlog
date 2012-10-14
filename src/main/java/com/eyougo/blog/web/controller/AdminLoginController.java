package com.eyougo.blog.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eyougo.blog.biz.BlogConfigBiz;
import com.eyougo.blog.util.EyougoStringUtil;

@Controller
@RequestMapping(value="/admin/login")
public class AdminLoginController {
	private BlogConfigBiz blogConfigBiz;

	private ResourceBundleMessageSource messageSource;
	
	@Autowired
	@Required
	public void setBlogConfigBiz(BlogConfigBiz blogConfigBiz) {
		this.blogConfigBiz = blogConfigBiz;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String get(){
		return "/admin/admin_login.ftl";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam String adminPassword, HttpSession session, Model model){
		String vAdminPassword = blogConfigBiz.findAdminPassword();
		if (adminPassword!=null&&EyougoStringUtil.hash(adminPassword).equals(vAdminPassword)){
			session.setAttribute("admin", "admin");
			return "redirect:/admin/index";
		}else{
			model.addAttribute("error.adminPassword",messageSource.getMessage("error.adminPassword", null, Locale.getDefault()));
			return "/admin/admin_login.ftl";
		}
	}
}