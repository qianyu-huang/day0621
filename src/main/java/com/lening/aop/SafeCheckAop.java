package com.lening.aop;

import com.lening.pojo.RoleMenuButton;
import com.lening.pojo.User;
import com.lening.service.RoleMenuButtonService;
import com.lening.utils.AjaxResponse;
import com.lening.utils.AjaxUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Aspect
@Component
public class SafeCheckAop {

	@Autowired
	private RoleMenuButtonService roleMenuButtonService;

	@Pointcut(value = "execution(public * com.lening.controller.*.save(..))" + "|| execution(public * com.lening.controller.*.delete*(..))"
			+ "||execution(public * com.lening.controller.*.update(..))" + "||execution(public * com.lening.controller.*.add*(..))")
	public void log() {

	}

	@Around(value = "log()")
	public AjaxResponse around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = servletRequestAttributes.getRequest();

		// 开始接口校验
		HttpSession session = request.getSession();

		// 校验用户是否登录
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return AjaxUtils.fail("用户未登录");
		}

		Integer userId = user.getId();

		List<RoleMenuButton> rmbList = roleMenuButtonService.findByUser(userId);

		// 获取当前url路径
		String url = request.getServletPath();

		// 设置标记位
		boolean valid = false;

		// 开始循环比对
		for (RoleMenuButton roleMenuButton : rmbList) {
			if (url.equals(roleMenuButton.getUrl())) {
				valid = true;
				break;
			}
		}

		if (valid == false) {

			return AjaxUtils.fail("非法请求，拒绝访问");

		} else {

			Object proceed = proceedingJoinPoint.proceed();

			return (AjaxResponse) proceed;
		}

	}
}
