package xiazdong.me.blurdemo;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
* 高斯模糊的最简单实现，速度极其慢，纯粹只是为了了解高斯模糊的内部实现才写的。
* Created by damonxia on 17/4/26.
*/

public class GussianBlur {
    private static final GussianBlur INSTANCE = new GussianBlur();
    private int sigma = 15;   //通过调节该参数设置高斯模糊的模糊程度，越大越模糊
    private int radius = 3 * sigma;
    private double[] kernel = new double[radius + 1];  //kernel[i]表示离中心点距离为i的权重

    private GussianBlur() {}

    /**
     * 初始化卷积核
     * 0.39894 = 1 / (Math.sqrt(2 * PI))
     */
    private void initKernel() {
        double sum = 0.0;
        for (int i = 0; i < kernel.length; i++) {
            kernel[i] = 0.39894 * Math.exp(- (i * i * 1.0) / (2.0 * sigma * sigma)) / sigma;
            if (i > 0) {
                sum = sum + kernel[i] * 2.0;
            } else {
                sum = sum + kernel[i];
            }
        }
        for (int i = 0; i < kernel.length; i++) {
            kernel[i] = kernel[i] / sum;
        }
    }

    /**
     * 高斯模糊
     */
    public Bitmap blur(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        Bitmap tmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //横向
        for (int i = 0; i < w * h; i++) {
            int x = i % w;
            int y = i / w;
            double[] sum = new double[4];
            for (int j = -radius; j <= radius; j++) {
                int currentX = Math.min(Math.max(x + j, 0), w - 1);
                int index = y * w + currentX;
                int a = Color.alpha(pix[index]);
                int r = Color.red(pix[index]);
                int g = Color.green(pix[index]);
                int b = Color.blue(pix[index]);
                sum[0] = sum[0] + a * kernel[Math.abs(j)];
                sum[1] = sum[1] + r * kernel[Math.abs(j)];
                sum[2] = sum[2] + g * kernel[Math.abs(j)];
                sum[3] = sum[3] + b * kernel[Math.abs(j)];
            }
            int rc = Color.argb((int) sum[0], (int) sum[1], (int) sum[2], (int) sum[3]);
            tmp.setPixel(x, y, rc);
        }
        tmp.getPixels(pix, 0, w, 0, 0, w, h);
        //纵向
        for (int i = 0; i < w * h; i++) {
            int x = i % w;
            int y = i / w;
            double[] sum = new double[4];
            for (int j = -radius; j <= radius; j++) {
                int currentY = Math.min(Math.max(y + j, 0), h - 1);
                int index = currentY * w + x;
                int r = Color.red(pix[index]);
                int g = Color.green(pix[index]);
                int b = Color.blue(pix[index]);
                int a = Color.alpha(pix[index]);
                sum[0] = sum[0] + a * kernel[Math.abs(j)];
                sum[1] = sum[1] + r * kernel[Math.abs(j)];
                sum[2] = sum[2] + g * kernel[Math.abs(j)];
                sum[3] = sum[3] + b * kernel[Math.abs(j)];
            }
            int rc = Color.argb((int) sum[0], (int) sum[1], (int) sum[2], (int) sum[3]);
            result.setPixel(x, y, rc);
        }
        return result;
    }

    private void setRadius(int radius) {
        this.sigma = radius;
        this.radius = 3 * sigma;
        this.kernel = new double[radius + 1];
        initKernel();
    }

    public static GussianBlur getInstance(int radius) {
        INSTANCE.setRadius(radius);
        return INSTANCE;
    }

    public static GussianBlur getInstance() {
        INSTANCE.setRadius(15);
        return INSTANCE;
    }
}