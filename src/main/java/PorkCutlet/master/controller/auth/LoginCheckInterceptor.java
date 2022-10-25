package PorkCutlet.master.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import PorkCutlet.master.controller.SessionConst;

public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession();

		if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
			response.sendRedirect("/auth/login?redirectURL=" + requestURI);
			return false;
		}

		return true;
	}
}
