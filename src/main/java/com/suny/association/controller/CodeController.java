package com.suny.association.controller;

import com.suny.association.enums.BaseEnum;
import com.suny.association.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Comments:  对页面产生验证码
 * Author:   孙建荣
 * Create Date: 2017/03/14 17:02
 */
@RequestMapping("/code")
@Controller
public class CodeController {
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    
    
    @RequestMapping("/generateCode.do")
    public void generateCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        // 定义图像buffer
        int width = 90;   //验证码的宽度
        int height = 20;   //验证码的高度
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
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);
        
        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);
        
        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 40; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red, green, blue;
        
        // 随机产生codeCount数字的验证码。
        int codeCount = 4;
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
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        System.out.print(randomCode);
        session.setAttribute("code", randomCode.toString());
        
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        
        resp.setContentType("image/jpeg");
        
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
    
    
    @RequestMapping("/checkCode.do")
    @ResponseBody
    public JsonResult checkCode(HttpServletRequest request,
                                @RequestParam String formCode) {
//        String sessionCode = (String) request.getSession().getAttribute("code");
       /* if (matchCode(formCode, sessionCode)) {
            return JsonResult.successResult(BaseEnum.VALIDATE_CODE_SUCCESS);
        }
        return JsonResult.failResult(BaseEnum.VALIDATE_CODE_ERROR);*/

        return JsonResult.successResult(BaseEnum.VALIDATE_CODE_SUCCESS);
    }
    
    
    private boolean matchCode(String formCode, String sessionCode) {
        return !formCode.equals("") && sessionCode.equals(formCode);
    }
    
    
}
