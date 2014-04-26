package cn.liuyb.app.common.utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 工具类 Copyright (c) 2008 by MTA.
 * 
 */
public class ImageUtils {
	public static void createMiniPic(File file, int width, int height,String newImgName)
			throws IOException {
		File f = file;
		String result = null;
		if(StringUtils.isNotBlank(newImgName)){
			int len = file.getPath().lastIndexOf(File.separator);
			result = file.getPath().substring(0, len + 1) + newImgName;
		}
		scale2(f.getAbsolutePath(),result,height,width,false);
	}
	/**
     * 缩放图像（按高度和宽度缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException 
     */
    @SuppressWarnings("static-access")
	public final static void scale2(String srcImageFile, String result, int height, int width, boolean bb) throws IOException {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            if(result!=null && result.toUpperCase().endsWith("PNG")){
            	ImageIO.write((BufferedImage) itemp, "PNG", new File(result));
            }else {
            	ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
            }
    }
	

	public static void main(String[] s) throws IOException {
		//File file = new File("D:/test/img.jpg");
		//createMiniPic(file, 480, 400, "ss23.jpg");
		scale2("D:/mdesk/apprepo/15/com.xuanfoulockscreen.chijiunan/8/screenshot1.png", "D:/mdesk/apprepo/15/com.xuanfoulockscreen.chijiunan/8/screenshotmini.png", 130, 216, false);

	}
}