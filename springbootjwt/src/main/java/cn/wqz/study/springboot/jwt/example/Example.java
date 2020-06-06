package cn.wqz.study.springboot.jwt.example;

import cn.wqz.study.springboot.jwt.util.KeyTool;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jwt token由三部分组成，用.连接
 * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.ihOZFzg3ZGIbBMneRy-4RMqors1P3nuO-wRJnQtTzWQ
 * 第一部分：base64加密的头信息，头部信息中有使用的加密算法
 * 第二部分：base64加密的载荷信息，可以存储一些非敏感数据（因为可以被解密）
 * 第三部分：通过头信息中的加密算法对信息进行加密后生成的字符串
 *
 * 笔者自己认为：
 * 认证时，服务端根据密钥（此密钥只存在于服务端）及算法，判断第三个字符串是否正确
 */
@Slf4j
public class Example {


    @Test
    public void test1(){
        // 根据alg算法与私有秘钥进行加密得到的签名字串；
        // 这一段是最重要的敏感信息，只能在服务端解密；
        /*  HMACSHA256(
                base64UrlEncode(header) + "." +
                        base64UrlEncode(payload),
                SECREATE_KEY
        )*/
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create().withIssuer("auth0").sign(algorithm);
        System.out.println(token);
    }

    @Test
    public void test2(){
        Map keyMap = KeyTool.initKey();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyMap.get(KeyTool.PUBLIC_KEY);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyMap.get(KeyTool.PRIVATE_KEY);
        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
        String token = JWT.create().withIssuer("auth0").sign(algorithm);
        System.out.println(token);
    }

    /**
     * 创建不携带自定义信息的token
     */
    @Test
    public void createToken() {

        String secret = "secret";// token 密钥
        Algorithm algorithm = Algorithm.HMAC256(secret);

        // 头部信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 使用 HS256 加密算法
        map.put("alg", "HS256");
        map.put("typ", "JWT");


        Date nowDate = new Date();
        Date expireDate = getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);// 2小过期

        String token = JWT.create()
                .withHeader(map)// 设置头部信息 Header
                .withIssuer("SERVICE")//设置 载荷 签名是有谁生成 例如 服务器
                .withSubject("this is test token")//设置 载荷 签名的主题
                // .withNotBefore(new Date())//设置 载荷 定义在什么时间之前，该jwt都是不可用的.
                .withAudience("APP")//设置 载荷 签名的观众 也可以理解谁接受签名的
                .withIssuedAt(nowDate) //设置 载荷 生成签名的时间
                .withExpiresAt(expireDate)//设置 载荷 签名过期的时间
                .sign(algorithm);//签名 Signature
        System.out.println(token);
        Assert.assertTrue(token.length() > 0);
    }

    /**
     * 生成携带自定义信息 JWT token
     * @return
     */
    @Test
    public void createTokenWithChineseClaim() {

        Date nowDate = new Date();
        Date expireDate = getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);// 2小过期

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create().withHeader(map)
                /* 设置 载荷 Payload */
                .withClaim("loginName", "zhuoqianmingyue").withClaim("userName", "张三").withClaim("deptName", "技术部")
                .withIssuer("SERVICE")// 签名是有谁生成 例如 服务器
                .withSubject("this is test token")// 签名的主题
                // .withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的
                .withAudience("APP")// 签名的观众 也可以理解谁接受签名的
                .withIssuedAt(nowDate) // 生成签名的时间
                .withExpiresAt(expireDate)// 签名过期的时间
                /* 签名 Signature */
                .sign(algorithm);
        System.out.println(token);
        Assert.assertTrue(token.length() > 0);
    }


    /**
     * token 认证
     * @throws UnsupportedEncodingException
     */
    @Test
    public void verifyToken() throws UnsupportedEncodingException {
        // 创建token，相当于从请求获取到token
        String token = createTokenWithChineseClaim2();

        // 创建对应的认证
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("SERVICE").build(); // Reusable verifier instance

        // 获取token中的载荷信息，并打印
        DecodedJWT jwt = verifier.verify(token);
        String subject = jwt.getSubject();
        List<String> audience = jwt.getAudience();
        Map<String, Claim> claims = jwt.getClaims();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            String key = entry.getKey();
            Claim claim = entry.getValue();
            log.info("key:" + key + " value:" + claim.asString());
        }
        Claim claim = claims.get("loginName");

        log.info(claim.asString());
        log.info(subject);
        log.info(audience.get(0));

    }


    public String createTokenWithChineseClaim2() throws UnsupportedEncodingException {

        Date nowDate = new Date();
        Date expireDate = getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);// 2小过期

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        User user = new User();
        user.setUsername("张三");
        user.setDeptname("技术部");
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        String userJsonBase64 = BaseEncoding.base64().encode(userJson.getBytes());

        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create().withHeader(map)

                .withClaim("loginName", "zhuoqianmingyue").withClaim("user", userJsonBase64).withIssuer("SERVICE")// 签名是有谁生成
                .withSubject("this is test token")// 签名的主题
                // .withNotBefore(new Date())//该jwt都是不可用的时间
                .withAudience("APP")// 签名的观众 也可以理解谁接受签名的
                .withIssuedAt(nowDate) // 生成签名的时间
                .withExpiresAt(expireDate)// 签名过期的时间
                .sign(algorithm);//签名 Signature

        return token;
    }

    private Date getAfterDate(Date nowDate, int i, int i1, int i2, int i3, int i4, int i5) {
        long l = nowDate.getTime();
        l = l + 2*60*60*1000;
        return new Date(l);
    }
    private static class User {

        private String username;
        private String deptname;

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
