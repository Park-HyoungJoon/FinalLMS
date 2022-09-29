package com.edo.util.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/***
 * Image의 용량 크기 변경을 위한 Utils
 * ImageUtils의 static 메서드인 resize에서는 입력받은 이미지 파일에 대한 InputStream, 변경하고자 하는 width,height를 인자 값으로 받는다.
 *
 */
public class ImageUtils {
    public BufferedImage resize(InputStream inputStream ,int width, int height)
        throws IOException {
        BufferedImage inputImage = ImageIO.read(inputStream);

        BufferedImage outputImage = new BufferedImage(width,height,inputImage.getType());

        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage,0,0,width,height,null);
        graphics2D.dispose();

        return outputImage;
    }
}
