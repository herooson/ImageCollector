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

import com.android.hyoonseol.imagecollector.util.ICUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016-07-29.
 */

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_IMG_URL = "img_url";
    public static final String EXTRA_IS_LOCAL = "is_local";

    private Toolbar mToolbar;
    private ImageView mImage;
    private View mLayMenu;
    private Button mBtn;

    private boolean mIsLocal;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);

        String imgUrl = null;
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra(EXTRA_TITLE);
            imgUrl = getIntent().getStringExtra(EXTRA_IMG_URL);
            mIsLocal = getIntent().getBooleanExtra(EXTRA_IS_LOCAL, false);
        }
        setViews();
        setImage(mTitle, imgUrl);
    }

    private void setViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        mImage = (ImageView)findViewById(R.id.iv_image);
        mLayMenu = findViewById(R.id.rl_menu);
        mBtn = (Button)findViewById(R.id.btn_menu);

        setMenu();

        mImage.setOnClickListener(this);
        mBtn.setOnClickListener(this);
    }

    private void setImage(String title, String mImgUrl) {
        if (title != null && !TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
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

        if (id == R.id.iv_image) {
            if (mLayMenu.getVisibility() == View.GONE) {
                mLayMenu.setVisibility(View.VISIBLE);
            } else {
                mLayMenu.setVisibility(View.GONE);
            }
        } else if (id == R.id.btn_menu) {
            if (mIsLocal) {
                //TODO delete
            } else {
                saveBitmap(((GlideBitmapDrawable)mImage.getDrawable()).getBitmap(), mTitle);
            }
        }
    }

    private void saveBitmap(Bitmap bitmap, String title) {
        FileOutputStream out = null;
        try {
            File file = new File(ICUtils.getImageFolderPath() + "/" + ICUtils.removeHtmlTag(title) + ".jpg");
            out = new FileOutputStream(file.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
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
    }
}
