package com.geek.system.service;

import com.geek.domain.system.response.FaceLoginResult;
import com.geek.domain.system.response.QRCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFaceLoginService {

    /**
     * 创建二维码。
     *
     * @return
     */
    QRCode getQRCode() throws IOException;

    /**
     * 根据唯一标识，查询用户是否登录成功。
     *
     * @param code
     * @return
     */
    FaceLoginResult checkQRCode(String code);

    /**
     * 扫描二维码之后，使用拍摄照片进行登录。
     *
     * @param code
     * @param attachment
     * @return
     */
    String loginByFace(String code, MultipartFile attachment) throws IOException;
}
