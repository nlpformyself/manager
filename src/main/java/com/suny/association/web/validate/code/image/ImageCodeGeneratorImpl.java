package com.suny.association.web.validate.code.image;

import com.suny.association.web.validate.code.ValidateCode;
import com.suny.association.web.validate.code.ValidateCodeGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**************************************
 *  Description  图形验证码产生
 *  @author 孙建荣
 *  @date 18-1-28.下午3:23
 *  @version 1.0
 **************************************/
@org.springframework.stereotype.Component
public class ImageCodeGeneratorImpl implements ValidateCodeGenerator {
    private static ImageCodeProperties codeProperties;
    private static final int INTERFERING_LINE_COUNT = 40;
    private static final String FONT_NAME = "Fixedsys";
    private StringBuilder randomCode;
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    static {
        codeProperties = new ImageCodeProperties();
    }

    private BufferedImage generateCode()
            throws IOException {
        // 定义图像buffer
        //验证码的宽度
        int width = codeProperties.getWidth();
        //验证码的高度
        int height = codeProperties.getHeight();
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        int fontHeight = 18;
        Font font = new Font(FONT_NAME, Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        interferenceLine(width, height, gd, random);

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        randomCode = new StringBuilder();
        int red;
        int green;
        int blue;

        // 随机产生codeCount数字的验证码。
        int codeCount = codeProperties.getLength();
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            int xx = 15;
            int codeY = 16;
            gd.drawString(code, (i + 1) * xx, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }

        return buffImg;
    }

    private void interferenceLine(int width, int height, Graphics gd, Random random) {
        for (int i = 0; i < INTERFERING_LINE_COUNT; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
    }


    @Override
    public ValidateCode generatorCode() throws IOException {
        return new ImageCode(generateCode(), randomCode.toString(), 600);
    }
}
