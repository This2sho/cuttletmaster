package PorkCutlet.master.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import PorkCutlet.master.controller.auth.LoginCheckInterceptor;
import PorkCutlet.master.controller.auth.LoginMemberArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginMemberArgumentResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())
			.order(1)
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/auth/join", "/auth/login", "/reviews", "/reviews/{spring:^[0-9]*$}",
				"/css/**", "/js/**", "/img/**", "/fonts/**", "/*.ico", "/images/**", "/recommend",
				"/reviews/{spring:^[0-9]*$}/comments");
	}
}
