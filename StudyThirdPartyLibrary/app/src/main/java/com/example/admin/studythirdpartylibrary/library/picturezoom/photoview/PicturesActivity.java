package com.example.admin.studythirdpartylibrary.library.picturezoom.photoview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.AbstractRecyclerViewAdapter;
import com.example.admin.studythirdpartylibrary.adapter.WaterfallPictruesAdapter;
import com.example.admin.studythirdpartylibrary.entity.ImageUrlData;
import com.example.admin.studythirdpartylibrary.uitl.GlideUtil;
import com.example.admin.studythirdpartylibrary.uitl.LogUtil;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/21.
 */

public class PicturesActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.rl_actionbar)
    RelativeLayout mRlActionBar;
    @BindView(R.id.tv_position)
    TextView mTvPosition;

    private WaterfallPictruesAdapter mAdapter;

    private String[] mUrls = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503312996401&di=9f4d602a3e88bd8ce68f597f25bb69a9&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F8c1001e93901213ff763533b52e736d12e2e9580.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503313068704&di=364dc1dceb4a736fda9f7e407c4241fe&imgtype=0&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Fmxy%2F12%2F11%2F4%2F2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503907800&di=ce64b1e2d9599e68dde8a0405b6148c9&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuku.cn%2Ffile_big%2F201503%2Fd8905515d1c046aeba51025f0ea842f0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503313575187&di=305aedbb228face4ff4d4eafafcab8bd&imgtype=0&src=http%3A%2F%2Fimage16-c.poco.cn%2Fmypoco%2Fmyphoto%2F20140916%2F21%2F3570160320140916210422044.jpg%3F1000x667_120",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503313757808&di=87139983554365406563f5efa4a76dee&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D6c444b100055b31988f48a362bc0e853%2Feac4b74543a982260c7b2c368082b9014a90eb6d.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503313174549&di=d37424e4fedff02dd26a064a66ff88ae&imgtype=0&src=http%3A%2F%2Fwww.xnnews.com.cn%2Fsh%2Flxsj%2F201406%2FW020140604833922008630.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503907922&di=89f0be53abef20bd6ca97df7b816d7f7&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1107%2F04%2Fc12%2F8226433_8226433_1309790257964_mthumb.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503313259070&di=14e7363645230fcef95f6779cd711d86&imgtype=0&src=http%3A%2F%2Fimg.kutoo8.com%2Fupload%2Fimage%2F79745338%2Fsj201308091001_320x480.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503313312329&di=354403aa877d280ca36d5e6eb16a3d9f&imgtype=0&src=http%3A%2F%2Fimg2.3lian.com%2F2014%2Ff4%2F102%2Fd%2F91.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503314793559&di=4931a54044207f65eeff41b1e6bf7d4a&imgtype=0&src=http%3A%2F%2Fimgb.mumayi.com%2Fandroid%2Fscreenshot%2F000%2F84%2F29%2F97%2F360_600_2.jpg"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 当activity继承自AppCompatActivity时此方法是无效的
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pictures);
        ButterKnife.bind(this);

        mAdapter = new WaterfallPictruesAdapter(this, null);
        recyclerview();
    }

    private void recyclerview() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(new AbstractRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AbstractRecyclerViewAdapter adapter, View view, int position) {
                LogUtil.i("PicturesActivity", "position: " + position);
                List<String> urls = new ArrayList<String>();
                List<ImageUrlData> imageUrlDatas = adapter.getDatas();
                for (ImageUrlData data : imageUrlDatas) {
                    urls.add(data.getUrl());
                }
                showViewPager(urls, position);

            }

            @Override
            public void onItemLongClick(AbstractRecyclerViewAdapter adapter, View view, int position) {

            }
        });
        mAdapter.notifyDataSetChanged(getDatas(10));
    }

    private void showViewPager(final List<String> urls, int position) {
        mViewPager.setVisibility(View.VISIBLE);
        mRlActionBar.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(new SamplePagerAdapter(urls));
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvPosition.setText((position+1)+"/" + urls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTvPosition.setText((mViewPager.getCurrentItem()+1) + "/" + urls.size());
    }

    private List<ImageUrlData> getDatas(int count) {
        List<ImageUrlData> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ImageUrlData data = new ImageUrlData();
            data.setUrl(mUrls[i % mUrls.length]);
            double d = Math.random();
            if (d < 0.5) {
                data.setSizeType(ImageUrlData.SMALL);
            } else {
                data.setSizeType(ImageUrlData.BIG);
            }
            datas.add(data);
        }
        return datas;
    }

    class SamplePagerAdapter extends PagerAdapter {

        private List<String> mPictures;


        public SamplePagerAdapter(List<String> pictures) {
            if (pictures != null) {
                mPictures = pictures;
            } else {
                mPictures = new ArrayList<>();
            }
        }

        @Override
        public int getCount() {
            return mPictures.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            GlideUtil.setImage(container.getContext(), mPictures.get(position), photoView);
            container.addView(photoView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getVisibility() == View.VISIBLE) {
            mViewPager.setVisibility(View.GONE);
            mRlActionBar.setVisibility(View.GONE);
            mViewPager.clearOnPageChangeListeners();
        } else {
            super.onBackPressed();
        }
    }
}
