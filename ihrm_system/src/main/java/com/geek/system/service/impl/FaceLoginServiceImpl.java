package com.geek.system.service.impl;

import com.baidu.aip.util.Base64Util;
import com.geek.common.utils.IdWorker;
import com.geek.domain.system.User;
import com.geek.domain.system.response.FaceLoginResult;
import com.geek.domain.system.response.QRCode;
import com.geek.system.dao.IUserDao;
import com.geek.system.service.IFaceLoginService;
import com.geek.system.utils.BaiduAiUtil;
import com.geek.system.utils.QRCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class FaceLoginServiceImpl implements IFaceLoginService {

    @Value("${qr.url}")
    private String url;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private QRCodeUtil qrCodeUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BaiduAiUtil baiduAiUtil;

    @Autowired
    private IUserDao userDao;

    /**
     * 创建二维码。
     *
     * @return
     * @throws IOException
     */
    @Override
    public QRCode getQRCode() throws IOException {
        // 创建唯一标识。
        String code = idWorker.nextId() + "";
        // 生成二维码。
        String content = url + "?code=" + code;
        System.out.println("content = " + content);
        String file = qrCodeUtil.crateQRCode(content);
        System.out.println("file = " + file);
        // 存入当前二维码的状态（存入 Redis）。
        FaceLoginResult result = new FaceLoginResult("-1");
        /**
         * result ~ 状态对象。
         * l ~ 失效时间。
         * 单位。
         */
        redisTemplate.boundValueOps(getCacheKey(code)).set(result, 10, TimeUnit.MINUTES);
        return new QRCode(code, file);
    }

    /**
     * 根据唯一标识，查询用户是否登录成功。
     *
     * @param code
     * @return
     */
    @Override
    public FaceLoginResult checkQRCode(String code) {
        String key = getCacheKey(code);
        FaceLoginResult faceLoginResult = (FaceLoginResult) redisTemplate.opsForValue().get(key);
        return faceLoginResult;
    }

    /**
     * 扫描二维码之后，使用拍摄照片进行登录。
     *
     * @param code
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @Override
    public String loginByFace(String code, MultipartFile multipartFile) throws IOException {
        // 调用百度云 AI 查询当前的用户。
        String userId = baiduAiUtil.faceSearch(Base64Util.encode(multipartFile.getBytes()));
        // 自动登录。
        FaceLoginResult faceLoginResult = new FaceLoginResult("0");// 初始值 0 登录失败。
        if (userId != null) {
            // 模拟登陆。（模拟 shiro 登录逻辑）。需要 手机号 密码。
            User user = userDao.findById(userId).get();
            if (user != null) {
                // 获取 subject。
                Subject subject = SecurityUtils.getSubject();
                // 调用 login 方法登陆。
                subject.login(new UsernamePasswordToken(user.getMobile(), user.getPassword()));
                // 获取 token。
                String token = (String) subject.getSession().getId();
                faceLoginResult = new FaceLoginResult("1", token, userId);
            }
        }
        // 修改二维码的状态。
        redisTemplate.boundValueOps(getCacheKey(code)).set(faceLoginResult, 10, TimeUnit.MINUTES);
        return userId;
    }

    /**
     * 构造缓存 key。
     *
     * @param code
     * @return
     */
    private String getCacheKey(String code) {
        return "qrcode_" + code;
    }
}
