package xiazdong.me.blurdemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlurActivity extends AppCompatActivity {

    @BindView(R.id.image_blur) ImageView mImage;
    @BindView(R.id.image_result) ImageView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        ButterKnife.bind(this);
        final Bitmap image = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
        final ProgressDialog bar = new ProgressDialog(this);
        bar.setMessage("正在高斯模糊...");
        bar.show();
        new Thread() {
            public void run() {
                long start = SystemClock.currentThreadTimeMillis();
                final Bitmap result = GussianBlur.getInstance().blur(image);
                long end = SystemClock.currentThreadTimeMillis();
                Log.v("BlurActivity", "" + (end - start) / 1000);
                final Bitmap compare = Bitmap.createBitmap(result.getWidth(), result.getHeight() * 2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(compare);
                Paint paint = new Paint();
                canvas.drawBitmap(image, 0, 0, paint);
                canvas.drawBitmap(result, 0, image.getHeight(), paint);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult.setImageBitmap(result);
                        bar.hide();
                        try {
                            compare.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(Environment.getExternalStorageDirectory() + "/result.jpg"));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
}
