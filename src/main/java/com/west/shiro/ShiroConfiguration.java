package com.west.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {

  @Bean
  public ServletContextListener environmentLoaderListener(){
	  return new EnvironmentLoaderListener(); 
  }

  @Bean
  public Realm shiroRealm(){
	  ShiroRealm realm = new ShiroRealm();
	  return realm;
  }

  @Bean(name = "shiroFilter")
  public AbstractShiroFilter shiroFilter() throws Exception {
      ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
      shiroFilter.setSecurityManager(securityManager());
      shiroFilter.setLoginUrl("/login.html");
      Map<String, Filter> filters = new HashMap<>();
      filters.put("anon", new AnonymousFilter());
      filters.put("authc", new FormAuthenticationFilter());
      //LogoutFilter logoutFilter = new LogoutFilter();
      //logoutFilter.setRedirectUrl("/login?logout");
      //filters.put("logout", logoutFilter);
      //filters.put("roles", new RolesAuthorizationFilter());
      //filters.put("user", new UserFilter());
     
      Map<String, String> f = new HashMap<>();
      f.put("/login.html","authc");
      f.put("/app/index.html ","authc");
      shiroFilter.setFilterChainDefinitionMap(f);
      shiroFilter.setFilters(filters);
      return (AbstractShiroFilter) shiroFilter.getObject();
  }

  @Bean(name = "securityManager")
  public DefaultWebSecurityManager securityManager() {
      DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
      securityManager.setRealm(shiroRealm());
      return securityManager;
  }

  @Bean
  public FilterRegistrationBean shiroFilterRegistration() throws Exception {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      registration.setFilter(shiroFilter());
      registration.addUrlPatterns("/*");
      registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);
      registration.setName("shiroFilter");
      return registration;
  }
  
  @Bean
  public HttpServlet loginServlet(){
	  return new LoginServlet();
  }
  
  @Bean
  public HttpServlet logoutServlet(){
	  return new LogoutServlet();
  }
  
  @Bean
  public ServletRegistrationBean loginServletRegistration(){
      return new ServletRegistrationBean(loginServlet(),"/Login");
  }

  @Bean
  public ServletRegistrationBean logoutServletRegistration(){
      return new ServletRegistrationBean(logoutServlet(), "/Logout");
  }
  
  @Bean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreatorBean(){
	 return new DefaultAdvisorAutoProxyCreator();
  }
  
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisorBean(){
	  return new AuthorizationAttributeSourceAdvisor();
  }
  
}
