package com.geek.common.utils;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.util.Date;

public class QiniuUploadUtil {

    private static final String accessKey = "0dmVG04OKdDY_4LfALMZ56MFEtBM0bg5dUf0xs46";
    private static final String secretKey = "Y6DU1QZSgmDquSrF4xMzkNtlBpE5fsdjt4wSNbsc";
    private static final String bucket = "geek-bucket";
    private static final String prefix = "http://qfoimb6i6.hn-bkt.clouddn.com/";
    private UploadManager manager;

    public QiniuUploadUtil() {
        // 初始化基本配置。
        // 构造一个带指定 Region 对象的配置类。
        Configuration cfg = new Configuration(Region.region2());
        // 创建上传管理器。
        manager = new UploadManager(cfg);
    }

    /**
     * 文件上传。
     *
     * @param imgName 文件名 = key
     * @param bytes   文件的 byte 数组。
     * @return 图片请求地址。
     */
    public String upload(String imgName, byte[] bytes) {
        Auth auth = Auth.create(accessKey, secretKey);
        // 构造覆盖上传 token。
        String upToken = auth.uploadToken(bucket, imgName);
        try {
            Response response = manager.put(bytes, imgName, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            // 返回请求地址。
            return prefix + putRet.key + "?t=" + new Date().getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
