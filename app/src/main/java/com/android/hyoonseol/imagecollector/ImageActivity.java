package com.android.hyoonseol.imagecollector;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.text.ICUCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.hyoonseol.imagecollector.fragment.IFragment;
import com.android.hyoonseol.imagecollector.util.ICUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016-07-29.
 */

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_IMG_URL = "img_url";
    public static final String EXTRA_IS_LOCAL = "is_local";

    private Toolbar mToolbar;
    private PhotoView mImage;
    private View mLayMenu;
    private Button mBtn;

    private boolean mIsLocal;
    private String mTitle;
    private String mImgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);

        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra(EXTRA_TITLE);
            mImgUrl = getIntent().getStringExtra(EXTRA_IMG_URL);
            mIsLocal = getIntent().getBooleanExtra(EXTRA_IS_LOCAL, false);
        }
        setViews();
        setImage();
    }

    private void setViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        mImage = (PhotoView) findViewById(R.id.pv_image);
        mLayMenu = findViewById(R.id.rl_menu);
        mBtn = (Button)findViewById(R.id.btn_menu);

        setMenu();

        mBtn.setOnClickListener(this);
    }

    private void setImage() {
        if (mTitle != null && !TextUtils.isEmpty(mTitle)) {
            mToolbar.setTitle(mTitle);
        }
        if (mImgUrl != null && !TextUtils.isEmpty(mImgUrl)) {
            Glide.with(this).load(mImgUrl).into(mImage);
        }
    }

    private void setMenu() {
        if (mIsLocal) {
            mBtn.setText("삭제하기");
        } else {
            mBtn.setText("다운받기");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_menu) {
            if (mIsLocal) {
                if (deleteBitmap(mImgUrl)) {
                    setResult(IFragment.RESULT_LOCAL_DELETE);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "이미지 삭제를 실패하였습니다." , Toast.LENGTH_SHORT).show();
                }
            } else {
                if (saveBitmap(((GlideBitmapDrawable)mImage.getDrawable()).getBitmap(), mTitle)) {
                    Toast.makeText(getApplicationContext(), "이미지가 저장되었습니다." , Toast.LENGTH_SHORT).show();
                    setResult(IFragment.RESULT_LOCAL_SAVE);
                } else {
                    Toast.makeText(getApplicationContext(), "이미지 저장을 실패하였습니다." , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean deleteBitmap(String path) {
        boolean result = false;
        File file = new File(path);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    private boolean saveBitmap(Bitmap bitmap, String title) {
        boolean result = false;
        FileOutputStream out = null;
        try {
            File file = new File(ICUtils.getImageFolderPath() + "/" + ICUtils.removeHtmlTag(title) + ".jpg");
            out = new FileOutputStream(file.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            result = true;
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }
}
