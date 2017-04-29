package xiazdong.me.blurdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RenderScriptActivity extends AppCompatActivity {

    @BindView(R.id.image_origin)
    ImageView mOriginImageView;
    @BindView(R.id.image_result)
    ImageView mResultImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_script);
        ButterKnife.bind(this);
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.test);
        Bitmap result = blur(bmp);
        mResultImageView.setImageBitmap(result);
    }

    public Bitmap blur(Bitmap bmp) {
        Bitmap result = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(this); //创建RenderScript对象
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); //创建高斯模糊脚本对象
        Allocation in = Allocation.createFromBitmap(rs, bmp);  //输入
        Allocation out = Allocation.createFromBitmap(rs, result); //输出
        blur.setRadius(25f); //设置模糊半径
        blur.setInput(in); //把输入图像传进去
        blur.forEach(out); //执行，并写入到out
        out.copyTo(result); //拷贝到Bitmap中
        rs.destroy();
        return result;
    }
}
