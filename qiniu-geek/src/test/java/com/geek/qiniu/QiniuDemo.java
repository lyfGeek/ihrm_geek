package com.geek.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class QiniuDemo {

    /**
     * 将图片上传到七牛云。
     */
    @Test
    public void testUpload() {
        // 构造一个带指定 Region 对象的配置类。
        Configuration cfg = new Configuration(Region.region2());
        // 机房 	Region
        //华东 	Region.region0(), Region.huadong()
        //华北 	Region.region1(), Region.huabei()
        //华南 	Region.region2(), Region.huanan()
        //北美 	Region.regionNa0(), Region.beimei()
        //东南亚 	Region.regionAs0(), Region.xinjiapo()
//...其他参数参考类注释

        // 上传管理器。
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传。
        String accessKey = "0dmVG04OKdDY_4LfALMZ56MFEtBM0bg5dUf0xs46";
        String secretKey = "Y6DU1QZSgmDquSrF4xMzkNtlBpE5fsdjt4wSNbsc";
        String bucket = "geek-bucket";
        // 如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "/home/qiniu/test.png";
        String localFilePath = "C:\\Users\\geek\\Desktop\\geek2.jpg";
        // 默认不指定 key 的情况下，以文件内容的 hash 值作为文件名。
//        String key = null;
        String key = "test";

        Auth auth = Auth.create(accessKey, secretKey);

//        String upToken = auth.uploadToken(bucket);// 普通上传。
        // {ResponseInfo:com.qiniu.http.Response@e50a6f6,status:614, reqId:HbsAAADKiMGd3y4W, xlog:X-Log, xvia:, adress:upload-z2.qiniup.com/221.4.146.197:443, duration:0.601000 s, error:file exists}
        //{"error":"file exists"}

        // ~ ~ ~ ~ ~
        // 覆盖上传。
        String upToken = auth.uploadToken(bucket, key);

        // ~ ~ ~ ~ ~

        try {
            // 上传。
            Response response = uploadManager.put(localFilePath, key, upToken);
            // 解析上传成功的结果。
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);// test
            System.out.println(putRet.hash);// FgJdFuXl6Eb2csqz-6AiZjK0LLgp
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        // http://qfoimb6i6.hn-bkt.clouddn.com
    }

    /**
     * 断点续传。
     */
    @Test
    public void testUpload02() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
//...其他参数参考类注释

//...生成上传凭证，然后准备上传
        String accessKey = "0dmVG04OKdDY_4LfALMZ56MFEtBM0bg5dUf0xs46";
        String secretKey = "Y6DU1QZSgmDquSrF4xMzkNtlBpE5fsdjt4wSNbsc";
        String bucket = "geek-bucket";
        // 如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "/home/qiniu/test.png";
        String localFilePath = "D:\\lyfGeek。download。\\Postman-win64-7.30.1-Setup.exe";
        // 默认不指定 key 的情况下，以文件内容的 hash 值作为文件名。
//        String key = null;
        String key = "test";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        // 断点续传。
//        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        String localTempDir = Paths.get(System.getProperty("java.io.tmpdir"), bucket).toString();
        System.out.println("localTempDir = " + localTempDir);
        try {
            // 设置断点续传文件进度保存目录。
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                Response response = uploadManager.put(localFilePath, key, upToken);
                // 解析上传成功的结果。
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
