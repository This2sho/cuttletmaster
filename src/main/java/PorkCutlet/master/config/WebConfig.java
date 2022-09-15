package PorkCutlet.master.config;

import PorkCutlet.master.controller.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/stop"); // 비로그인시 못들어감 <- 리뷰 생성페이지, 마이페이지 관련 으로 바꿔야함
    }
}
