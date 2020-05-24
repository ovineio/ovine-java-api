package com.igroupes.rtadmin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RunWith(SpringRunner.class)
public class VerifyCodeUtilTest {

    @Test
    public void createImage() throws IOException {
        Object[] objs = VerifyCodeUtil.createImage();
        BufferedImage image = (BufferedImage) objs[1];
        OutputStream os = new FileOutputStream("d:/1.png");
        ImageIO.write(image, "png", os);
        os.close();
    }
}
