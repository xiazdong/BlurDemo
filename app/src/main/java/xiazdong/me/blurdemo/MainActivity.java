package xiazdong.me.blurdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wonderkiln.blurkit.BlurKit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //BlurKit.init(this);
        list = (ListView) findViewById(R.id.image);
        final Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.test);
        Bitmap b = BlurKit.getInstance().blur(bitmap, 25);
        list.setAdapter(new ArrayAdapter<>(this, R.layout.item_listview, R.id.text_item, new String[]{"a,b,c,d","hahah","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","hahah","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","hahah","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d","a,b,c,d"}));
/*        new Thread() {
            public void run() {
                long start = SystemClock.currentThreadTimeMillis();
                final Bitmap blurImage = GussianBlur.getInstance().blur(bitmap);
                long end = SystemClock.currentThreadTimeMillis();
                Log.v("MainActivity", "" + (end - start) / 1000.0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(blurImage);
                        try {
                            blurImage.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream("/storage/emulated/0/DCIM/3.jpg"));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();*/
    }
}
