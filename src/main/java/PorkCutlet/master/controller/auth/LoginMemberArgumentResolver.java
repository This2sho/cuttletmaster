package PorkCutlet.master.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import PorkCutlet.master.controller.SessionConst;
import PorkCutlet.master.controller.dto.UserInfoDto;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
		boolean hasUserInfoDtoType = UserInfoDto.class.isAssignableFrom(parameter.getParameterType());
		return hasLoginAnnotation && hasUserInfoDtoType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		if (session == null) {
			return null;
		}
		UserInfoDto user = (UserInfoDto)session.getAttribute(SessionConst.LOGIN_USER);
		session.setMaxInactiveInterval(1200);
		mavContainer.addAttribute("user", user);
		return session.getAttribute(SessionConst.LOGIN_USER);
	}
}
