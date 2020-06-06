package cn.wqz.study.springboot.oos.constant;

/**
 * 仅仅封装一些常量属性
 */
public class AliyunOSSConfigConstant {
    //私有构造方法 禁止该类初始化
    private AliyunOSSConfigConstant() {
    }

    //仓库名称
    public static final String BUCKE_NAME = "yelao-test";
    //地域节点
    public static final String END_POINT = "oss-cn-beijing.aliyuncs.com";
    //AccessKey ID
    public static final String AccessKey_ID = "LTAI4G1JcREw2Anqjen4ddCk";
    //Access Key Secret
    public static final String AccessKey_Secret = "nIhxIWmkgWVV0en3xDAO9ZQwKUtQX4";
    //仓库中的某个文件夹
    public static final String FILE_HOST = "test";
}
