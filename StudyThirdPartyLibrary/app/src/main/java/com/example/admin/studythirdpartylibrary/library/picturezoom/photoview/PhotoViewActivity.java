package com.example.admin.studythirdpartylibrary.library.picturezoom.photoview;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.uitl.LogUtil;
import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.OnViewDragListener;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/21.
 */

public class PhotoViewActivity extends AppCompatActivity {
    public static final String TAG = PhotoViewActivity.class.getSimpleName();

    @BindView(R.id.buttonRotation)
    Button mButtonRotation;
    @BindView(R.id.buttonSpecific)
    Button mButtonSpecific;
    @BindView(R.id.imageview)
    PhotoView mImageView;

    private final Handler handler = new Handler();
    private boolean rotating = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        ButterKnife.bind(this);

        onClickListener();
        photoViewListener();
    }

    private void onClickListener() {
        mButtonRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRotation();
            }
        });
        mButtonSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoViewActivity.this, PicturesActivity.class));
            }
        });

    }

    private void toggleRotation() {
        if (rotating) {
            handler.removeCallbacksAndMessages(null);
        } else {
            rotateLoop();
        }
        rotating = !rotating;
    }

    private void rotateLoop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * setRotationBy(float rotationDegree)相对于当前位置旋转rotationDegree
                 * setRotationTo(float rotationDegree)相对于原始位置旋转rotationDegree
                 */
                mImageView.setRotationBy(1);
                rotateLoop();
            }
        }, 15);
    }

    /**
     * 连接一些监听，虽然不是需要
     */
    public void photoViewListener() {
        // 测试的时候不执行，猜测被setOnDoubleTapListener--onSingleTapConfirmed的回调所拦截
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG, "setOnClickListener");
            }
        });
        // 测试的时候不执行，猜测被setOnDoubleTapListener--onSingleTapConfirmed的回调所拦截
        mImageView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                LogUtil.i(TAG, "setOnPhotoTapListener");
            }
        });
        // 双击图片监听，如需双击图片放大缩小则不调用次监听即可
        mImageView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                LogUtil.i(TAG, "setOnDoubleTapListener--onSingleTapConfirmed");
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                LogUtil.i(TAG, "setOnDoubleTapListener--onDoubleTap");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                LogUtil.i(TAG, "setOnDoubleTapListener--onDoubleTapEvent");
                return false;
            }
        });
        // 图形矩阵大小发生改变监听
        mImageView.setOnMatrixChangeListener(new OnMatrixChangedListener() {
            @Override
            public void onMatrixChanged(RectF rect) {
                LogUtil.i(TAG, "setOnMatrixChangeListener");
            }
        });
        // 图片长按监听
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogUtil.i(TAG, "setOnLongClickListener");
                return false;
            }
        });
        // 图片外围发生点击事件监听
        mImageView.setOnOutsidePhotoTapListener(new OnOutsidePhotoTapListener() {
            @Override
            public void onOutsidePhotoTap(ImageView imageView) {
                LogUtil.i(TAG, "setOnOutsidePhotoTapListener");
            }
        });
        // 图片发生缩放的监听
        mImageView.setOnScaleChangeListener(new OnScaleChangedListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                LogUtil.i(TAG, "setOnScaleChangeListener");
            }
        });
        // 图片发生拖动的监听
        mImageView.setOnViewDragListener(new OnViewDragListener() {
            @Override
            public void onDrag(float dx, float dy) {
                LogUtil.i(TAG, "setOnViewDragListener");
            }
        });
        // 当图片没有占满整个PhotoView时点击非图形区域调用次监听
        mImageView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                LogUtil.i(TAG, "setOnViewTapListener");
            }
        });

        // 当图片尺寸小于或者等于PhotoView尺寸时手指滑动操作会执行这个监听回调
        mImageView.setOnSingleFlingListener(new OnSingleFlingListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtil.i(TAG, "setOnSingleFlingListener");
                return false;
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
