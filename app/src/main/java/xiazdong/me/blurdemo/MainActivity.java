package xiazdong.me.blurdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_blur) Button mBlurBtn;
    @BindView(R.id.btn_blurkit) Button mBlurKitBtn;
    @BindView(R.id.btn_blurry) Button mBlurryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBlurBtn.setOnClickListener(this);
        mBlurKitBtn.setOnClickListener(this);
        mBlurryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_blur:
                goToActivity(BlurActivity.class);
                break;
            case R.id.btn_blurkit:
                goToActivity(BlurKitActivity.class);
                break;
            case R.id.btn_blurry:
                goToActivity(BlurryActivity.class);
                break;
        }
    }

    void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
