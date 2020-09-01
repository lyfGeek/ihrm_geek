package com.geek.test;

import com.baidu.aip.util.Base64Util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRCodeTest {
    // 生成 dataUrl 形式二维码。
    public static void main(String[] args) throws WriterException, IOException {

        // 二维码信息。
        String content = "http://www.baidu.com";

        // 通过 zxing 生成二维码。（可在到本地，支持以 data url 的形式体现）。
        // 创建 QRCodeWriter 对象。
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 基本配置。
        // 二维码信息。
        // 图片类型。
        // 宽度。
        // 长度。
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);

        // 创建 ByteArrayOutputStream。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 将二维码数据以 byte 数组的形式保存到 ByteArrayOutputStream。
        // 1. image 对象。
        // 2. 图片格式。
        // 3. OutputStream。
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        boolean png = ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        // 对 byte 数组进行 BASE64 处理。
        String encode = Base64Util.encode(byteArrayOutputStream.toByteArray());
        System.out.println(encode);
        System.out.println("data:image/png;base64, " + encode);// 将结果复制到浏览器就是图片。
    }

//    public static void main(String[] args) throws WriterException, IOException {
//
//        // 二维码信息。
//        String content = "http://www.baidu.com";
//
//        // 通过 zxing 生成二维码。（可在到本地，支持以 data url 的形式体现）。
//        // 创建 QRCodeWriter 对象。
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        // 基本配置。
//        // 二维码信息。
//        // 图片类型。
//        // 宽度。
//        // 长度。
//        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);
//
//        // 保存二维码到本地。
//        Path path = new File("./test.png").toPath();
//        MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
//    }
}
