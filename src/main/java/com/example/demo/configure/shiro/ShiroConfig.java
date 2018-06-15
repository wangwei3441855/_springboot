package com.example.demo.configure.shiro;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysRsources;
import com.example.demo.domain.SysUser;
import com.example.demo.service.system.SysRsourcesService;
import com.example.demo.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configuration
public class ShiroConfig {

    public static final int HASH_ITERATIONS = 1024;
    public static final String HASH_ALGORITHM_NAME = "MD5";
    private Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SysRsourcesService sysRsourcesService;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置拦截的地址
        shiroFilterFactoryBean.setFilterChainDefinitionMap(getFilterChain());
        //登陆
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登陆成功跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //过滤器
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("perms", new SysAuthorizationFilter());
        return shiroFilterFactoryBean;
    }

    /**
     * - anon:所有url都都可以匿名访问
     * - authc: 需要认证才能进行访问
     * - user:配置记住我或认证通过可以访问
     *
     * @return
     */
    private Map<String, String> getFilterChain() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注销
        filterChainDefinitionMap.put("/logout", "logout");
        //不拦截的请求
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        //需要拦截请求
        List<SysRsources> allRsources = sysRsourcesService.getAllRsources();
        if (allRsources != null) {
            for (SysRsources res : allRsources) {
                filterChainDefinitionMap.put(res.getResourceContent(), "perms[" + res.getResourceContent() + "]");
            }
        }
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    @Bean
    public SysShiroRealm shiroRealm() {
        SysShiroRealm shiroRealm = new SysShiroRealm();
        //密码md5
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(HASH_ALGORITHM_NAME);
        hashedCredentialsMatcher.setHashIterations(HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }

    public class SysShiroRealm extends AuthorizingRealm {

        /**
         * 权限认证
         *
         * @param principalCollection
         * @return AuthorizationInfo
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
            SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if ("admin".equals(sysUser.getUserName())) {
                info.addRole("ROLE_ADMIN");
                List<SysRsources> allRsources = sysRsourcesService.getAllRsources();
                Set<String> permissions = new HashSet<String>();
                for (SysRsources res : allRsources) {
                    permissions.add(res.getResourceContent());
                }
                info.setStringPermissions(permissions);
            } else {
                Map<String, Object> pram = new HashMap<String, Object>();
                pram.put("userId", sysUser.getUserId());
                SysUser userRoleRes = userService.getUserRoleRes(pram);
                Set<String> roles = new HashSet<String>();
                for (SysRole role : userRoleRes.getRoles()) {
                    roles.add(role.getRoleName());
                }
                Set<String> permissions = new HashSet<String>();
                for (SysRsources res : userRoleRes.getRsources()) {
                    permissions.add(res.getResourceContent());
                }
                info.setRoles(roles);
                info.setStringPermissions(permissions);
            }
            return info;
        }

        /**
         * 登录认证
         *
         * @param authenticationToken
         * @return AuthenticationInfo
         * @throws AuthenticationException
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            AuthenticationInfo authent = null;
            String userName = authenticationToken.getPrincipal().toString();
            SysUser user = userService.getUserByName(userName);
            if (user == null) {
                throw new UnknownAccountException("UnknownAccountException");
            }
            authent = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return authent;
        }
    }


    public class SysAuthorizationFilter extends PermissionsAuthorizationFilter {
        @Override
        protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
            Subject subject = this.getSubject(request, response);
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String requestURI = httpServletRequest.getRequestURI();
            String requestedWith = httpServletRequest.getHeader("X-Requested-With");
            if (subject.getPrincipal() == null) {
                this.saveRequestAndRedirectToLogin(request, response);
            } else {
                //ajax请求判断
                if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("success", false);
                    map.put("errorMsg", "No authority");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.getWriter().write(JSON.toJSONString(map));
                } else {
                    String unauthorizedUrl = this.getUnauthorizedUrl();
                    if (StringUtils.hasText(unauthorizedUrl)) {
                        WebUtils.issueRedirect(request, response, unauthorizedUrl);
                    } else {
                        WebUtils.toHttp(response).sendError(401);
                    }
                }
            }
            return false;
        }
    }
}
