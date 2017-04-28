package xiazdong.me.blurdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.wonderkiln.blurkit.BlurKit;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class BlurryActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    Integer[] images = {
            R.mipmap.img_01,
            R.mipmap.img_02,
            R.mipmap.img_03,
            R.mipmap.img_04,
            R.mipmap.img_01,
            R.mipmap.img_02,
            R.mipmap.img_03,
            R.mipmap.img_04,
            R.mipmap.img_01,
            R.mipmap.img_02,
            R.mipmap.img_03,
            R.mipmap.img_04,
            R.mipmap.img_01,
            R.mipmap.img_02,
            R.mipmap.img_03,
            R.mipmap.img_04,
            R.mipmap.img_01,
            R.mipmap.img_02,
            R.mipmap.img_03,
            R.mipmap.img_04
    };
    List<Integer> mImageList = Arrays.asList(images);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blurry);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new QuickAdapter<Integer>(mImageList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_blur;
            }

            @Override
            public void convert(VH holder, Integer data, int position) {
                ImageView imageView = holder.getView(R.id.item_image);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 4;
                Bitmap image = BitmapFactory.decodeResource(BlurryActivity.this.getResources(), data, opts);
                if (position % 2 == 0) {
                    Blurry.with(BlurryActivity.this).radius(10)
                            .sampling(2).async().from(image).into(imageView);
                } else {
                    imageView.setImageBitmap(image);
                }
            }
        });
    }
}
