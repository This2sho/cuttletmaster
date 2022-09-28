package PorkCutlet.master.controller.login;

import PorkCutlet.master.controller.SessionConst;
import PorkCutlet.master.controller.dto.UserDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasUserDtoType = UserDto.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasUserDtoType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        if (session == null) {
            return null;
        }
        UserDto user = (UserDto) session.getAttribute(SessionConst.LOGIN_USER);
        mavContainer.addAttribute("user", user);
        return session.getAttribute(SessionConst.LOGIN_USER);
    }
}
