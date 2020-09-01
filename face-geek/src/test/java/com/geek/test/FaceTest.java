package com.geek.test;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FaceTest {

    // 设置 APPID/AK/SK。
    private static final String APP_ID = "22249409";
    private static final String API_KEY = "n9KC5d6UsMOPLIAt35OZDfcX";
    private static final String SECRET_KEY = "SWMHsylM8soQv1Fsgke0qHUKLyqtx9ca";

    private AipFace client;

    @Before
    public void init() {

        // - 创建 Java 代码和百度云交互的 client 对象。
        // 初始化一个 AipFace。
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    }

    /**
     * 人脸注册。
     *
     * @throws IOException
     */
    @Test
    public void testFaceRegister() throws IOException {
//
//        // - 创建 Java 代码和百度云交互的 client 对象。
//        // 初始化一个 AipFace。
//        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // - 参数设置。
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        //        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");// 图片质量。NONE LOW NORMAL HIGH。
        options.put("liveness_control", "LOW");// 活体检测。
//        options.put("action_type", "REPLACE");

//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
        String image = "C:\\Users\\geek\\Pictures\\Camera Roll\\WIN_20200826_01_13_28_Pro.jpg";
//        String image = "C:\\Users\\geek\\Desktop\\rennian2.jpg";
        // 图片信息（总数据大小应小于10M），图片上传方式根据 image_type 来判断。注：组内每个 uid 下的人脸图片数目上限为 20 张。
        String imageType = "BASE64";
        //  	图片类型
        //BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
        //URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
        //FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
        byte[] bytes = Files.readAllBytes(Paths.get(image));
        String encode = Base64Util.encode(bytes);

        String groupId = "group1";
        String userId = "user1";

        // 调用 api 方法完成人脸注册。
        /*
        参数 1：图片的 url 或图片的 Base64字符串。
        参数 2：图片形式（url, Base64）。
        参数 3：组 ID。（固定字符串）。
        参数 4：用户 ID。
        参数 5：HashMap 中的配置。
         */
        // 人脸注册。
        JSONObject res = client.addUser(encode, imageType, groupId, userId, options);
        System.out.println(res.toString());
    }

    /**
     * 人脸检测。
     *
     * @throws IOException
     */
    @Test
    public void testFaceCheck() throws IOException {
        // 初始化一个AipFace
//            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数。
//            client.setConnectionTimeoutInMillis(2000);
//            client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址，http 和 socket 二选一，或者均不设置。
//            client.setHttpProxy("proxy_host", proxy_port);// 设置 http 代理。
//            client.setSocketProxy("proxy_host", proxy_port);// 设置 socket 代理。

        // 调用接口。
        // 构造图片。
        String image = "C:\\Users\\geek\\Desktop\\rennian.jpg";
        String imageType = "BASE64";
        //  	图片类型
        //BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
        //URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
        //FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
        byte[] bytes = Files.readAllBytes(Paths.get(image));
        String encode = Base64Util.encode(bytes);

        // 调用 api 方法完成人脸检测。
        // 人脸检测。
        /*
        参数 1：图片的 url 或图片的 Base64字符串。
        参数 2：图片形式（url, Base64）。
        参数 3：HashMap 中的配置。null 使用默认。
         */
        JSONObject res = client.detect(encode, imageType, null);
        System.out.println(res.toString(2));
    }

    /**
     * 人脸搜索。
     * 根据用户上传的图片和指定人脸库的所有人脸进行比对，获取相似度较高的某几个的评分。
     * <p>
     * 返回值：数据，只需要第一条，相似度最高的数据。
     * score：相似度评分（80 分以上可以认为是同一人）。
     */
    @Test
    public void testFaceSearch() throws IOException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "3");
        options.put("match_threshold", "70");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("user_id", "233451");
        options.put("max_user_num", "3");

        // 构造图片。
//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//        String groupIdList = "3,2";
        String image = "C:\\Users\\geek\\Desktop\\rennian3.jpg";
//        String image = "C:\\Users\\geek\\Pictures\\Camera Roll\\WIN_20200826_01_13_28_Pro.jpg";

        String imageType = "BASE64";
        //  	图片类型
        //BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
        //URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
        //FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
        byte[] bytes = Files.readAllBytes(Paths.get(image));
        String encode = Base64Util.encode(bytes);

        // 人脸搜索。
        JSONObject res = client.search(encode, imageType, "group1", null);
        System.out.println(res.toString(2));
    }

    /**
     * 更新人脸库中的照片。
     */
    @Test
    public void sample() throws IOException {
        // 传入可选参数调用接口。
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
//        options.put("action_type", "REPLACE");

//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//        String groupId = "group1";
//        String userId = "user1";
        String image = "C:\\Users\\geek\\Desktop\\rennian2.jpg";
        String imageType = "BASE64";
        String groupId = "group1";
        String userId = "user1";
        byte[] bytes = Files.readAllBytes(Paths.get(image));
        String encode = Base64Util.encode(bytes);

        // 人脸更新。
        JSONObject res = client.updateUser(encode, imageType, groupId, userId, options);
        System.out.println(res.toString(2));
//
//        for (int i = 0; i < 10; i++) {
//            // 人脸更新。
//            JSONObject res = client.updateUser(encode, imageType, groupId, userId, options);
//            System.out.println(res.toString(2));
//            //  "error_msg": "Open api qps request limit reached",
//            //  "error_code": 18
//        }

    }
}
