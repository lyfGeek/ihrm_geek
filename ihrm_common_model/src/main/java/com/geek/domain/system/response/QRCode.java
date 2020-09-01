package com.geek.domain.system.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class QRCode implements Serializable {
    /**
     * 随机生成码。
     */
    private String code;
    /**
     * Base64 二维码文件。
     */
    private String file;
}
