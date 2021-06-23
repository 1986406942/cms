package com.cms.portal.controller.admin;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    public RedisTemplate<String, String> redisTemplate;
    @Autowired
    public Producer captchaProducer;

    @GetMapping("login.do")
    public String toLogin() {
        //redisTemplate.opsForValue().set("name","superhuan");
        return "admin/login";//servlet-commom.xml配置文件中规定了模板引擎的前后缀分别为/WEB-INF/ 和.html
    }

    @GetMapping("kaptcha.do")
    public void doKaptcha(HttpServletResponse httpServletResponse) {
        //使用try-with-resource更加优雅的关闭流:
        //java 1.7之后，增加了 try-wit-resource的语法糖，大概的用法就是在try中声明一个或者多个的流，会在try块代码执行完成后自动关闭流，不用再写finally进行手都关闭.
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream();){
            String text = captchaProducer.createText();
            BufferedImage image = captchaProducer.createImage(text);
            //ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
