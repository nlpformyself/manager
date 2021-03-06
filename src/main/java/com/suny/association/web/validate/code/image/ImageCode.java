package com.suny.association.web.validate.code.image;

import com.suny.association.web.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**************************************
 *  Description   图形验证码
 *  @author 孙建荣
 *  @date 18-1-28.下午3:17
 *  @version 1.0
 **************************************/
public class ImageCode extends ValidateCode {
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public ImageCode(BufferedImage image,String code, int expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
