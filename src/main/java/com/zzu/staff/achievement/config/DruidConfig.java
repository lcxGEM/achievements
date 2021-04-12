package com.zzu.staff.achievement.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid监控配置
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    //后台监控: web.xmL,servletRegistrationBean
    //因为SpringBoot 内置了servlet容器，所以没有web.xml , 替代方法: ServletRegistrationBean
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        HashMap<String, String> initParam = new HashMap<>();
        //增加配置
        initParam.put("loginUsername","admin");//登陆key 是固定的 loginUsername loginPassword
        initParam.put("loginPassword","123456");
        //允许谁可以访问
        initParam.put( "allow","");

        bean.setInitParameters(initParam);
        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean webStaterFilter(){
        //过滤请求
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParameters = new HashMap();
        //exclusions: 表示这些请求不进行过滤
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
