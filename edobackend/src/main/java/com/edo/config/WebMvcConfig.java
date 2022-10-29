package com.edo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/***
 * 이미지 뷰를 재시작하지 않는 이상 파일 이름을 못 읽어오는 현상 제거를 위해 제작
 * 이미지는 프로젝트 내 resources 폴더에 동적으로 저장시킬시 리로드하지 않는 이상 안 보이는 에러가 나타남.
 * 따라서 외부 경로로 지정 후 디음과 같이 권한을 부여 해 볼 수 있도록 해야한다.
 *
 */
@Configuration
@Slf4j
@PropertySource("classpath:/application.properties")
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${part4.upload.path}")
    private String imgUploadPath;

    @Value("${part5.upload.path}")
    private String outImgPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("resources/**");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///"+System.getProperty("user.dir") + imgUploadPath);
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:///" + outImgPath);
   }

}
