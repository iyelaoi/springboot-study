package cn.wqz.study.springboot.shiro.configuration;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的Realm类继承AuthorizingRealm类，并且重载doGetAuthorizationInfo和doGetAuthenticationInfo两个方法。
 * doGetAuthorizationInfo： 权限认证，即登录过后，每个身份不一定，对应的所能看的页面也不一样。
 * doGetAuthenticationInfo：身份认证。即登录通过账号和密码验证登陆人的身份信息。
 *
 */
public class CustomRealm extends AuthorizingRealm {

    /**
     * 权限相关
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 后台实现模拟添加权限
        // UserController中的user/show对应的方法上是 @RequiresPermissions("user:list")
        // 则权限需要user:list
        Set<String> stringSet = new HashSet<>();
        // 可以将list权限注释测试
        //stringSet.add("user:list");
        stringSet.add("user:show");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);
        return info;
    }

    /**
     * 认证相关
     * 这里可以注入userService,为了方便演示，写死了帐号了密码
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //模拟根据用户名从数据库中获取的密码
        String password = "123";
        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else if (!userPwd.equals(password )) {
            throw new AccountException("密码不正确");
        }
        // 不适用加密密码的方式
        //return new SimpleAuthenticationInfo(userName, password,getName());

        // 使用加密密码的方式
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(userName, password,
                ByteSource.Util.bytes(userName + "salt"), getName());
    }
}