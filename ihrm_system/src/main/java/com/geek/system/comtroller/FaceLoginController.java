package com.geek.system.comtroller;

import com.baidu.aip.util.Base64Util;
import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.domain.system.response.FaceLoginResult;
import com.geek.domain.system.response.QRCode;
import com.geek.system.service.IFaceLoginService;
import com.geek.system.utils.BaiduAiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/sys/faceLogin")
public class FaceLoginController {

    @Autowired
    private IFaceLoginService faceLoginService;

    @Autowired
    private BaiduAiUtil baiduAiUtil;

    /**
     * 获取刷脸登录二维码。
     *
     * @return QRCode 对象。（code, image）。
     */
    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public Result qrcode() throws IOException {
        QRCode qrCode = faceLoginService.getQRCode();
        return new Result(ResultCode.SUCCESS, qrCode);
    }

    /**
     * 检查二维码：登录页面轮询调用此方法，根据唯一标识 code 判断用户登录情况。
     * - 查询二维码扫描状态。
     * 返回值：FaceLoginResult。
     * state：-1, 0, 1（userId 和 token）。
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/qrcode/{code}", method = RequestMethod.GET)
    public Result qrcodeCeck(@PathVariable(name = "code") String code) {
        FaceLoginResult checkQRCode = faceLoginService.checkQRCode(code);
        return new Result(ResultCode.SUCCESS, checkQRCode);
    }

    /**
     * 人脸登录：根据落地页随机拍摄的面部头像进行登录。
     * 根据拍摄的图片调用百度云 AI 进行检索查找。
     *
     * @param code
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{code}", method = RequestMethod.POST)
    public Result loginByFace(@PathVariable(name = "code") String code, @RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        // 人脸登录，获取用户 id。
        String userId = faceLoginService.loginByFace(code, multipartFile);
        if (userId != null) {
            return new Result(ResultCode.SUCCESS);
        } else {
            return new Result(ResultCode.FAIL);
        }
    }

    /**
     * 图像检测，判断图片中是否存在面部头像。
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkFace", method = RequestMethod.POST)
    public Result checkFace(@RequestParam(name = "file") MultipartFile multipartFile) throws Exception {
        String image = Base64Util.encode(multipartFile.getBytes());
        Boolean isExist = baiduAiUtil.faceCheck(image);
        if (isExist) {
            return new Result(ResultCode.SUCCESS);
        } else {
            return new Result(ResultCode.FAIL);
        }
    }
}
