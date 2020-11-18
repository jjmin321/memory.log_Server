package dgsw.memorylog.memorylog_Server.config;

import dgsw.memorylog.memorylog_Server.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/member/getInfo")
                .addPathPatterns("/member/editInfo")
                .addPathPatterns("/paper/createPaper")
                .addPathPatterns("/paper/getMyPaper")
                .addPathPatterns("/paper/showPaper")
                .addPathPatterns("/paperLike/updateLike")
                .addPathPatterns("/paperComment/createPaperComment")
                .addPathPatterns("/paperComment/showComment/**")
                .addPathPatterns("/paperComment/modify/**")
                .addPathPatterns("/paperComment/delete/**");
    }
}
