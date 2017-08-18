package com.example.admin.studythirdpartylibrary.library.mpandroidchart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.uitl.SizeUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/14.
 * <p>
 * LineChart的内容主要学习自 @link http://www.cnblogs.com/r-decade/p/6241693.html
 *
 * 引用MPAndroidChart库的Github地址 @link https://github.com/PhilJay/MPAndroidChart
 */

public class MPAndroidChartActivity extends AppCompatActivity {
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.buttonChange)
    Button mButtonChange;
    @BindView(R.id.lineChart)
    LineChart mLineChart;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroidchart);
        ButterKnife.bind(this);

        mContext = this;
        onClickListener();
        spinner();
        showLineChart();

    }

    private void onClickListener() {
        mButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当有数据时点击按钮清空数据并绘制显示无数据界面，当无数据时点击按钮重新传入数据并绘制
                if (mLineChart.getData() != null) {
                    // 设置数据为空
                    mLineChart.setData(null);
                    // 重新绘制表格
                    mLineChart.invalidate();
                } else {
                    // 绘制表格数据
                    showLineChart();
                }

            }
        });
    }

    /**
     * 初始化下拉选择控件
     */
    private void spinner() {
        final String[] mCharts = getResources().getStringArray(R.array.charts);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mCharts);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chart = (String) parent.getAdapter().getItem(position);
                if (mContent.getChildAt(0) != null) {
                    mContent.removeAllViews();
                }
                displaySelectChart(mContent,chart);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showLineChart() {
        // 关闭chart的LogCat输出
        mLineChart.setLogEnabled(false);

        // 设置文字描述
        Description description = new Description();
        description.setText("图书销量");
        description.setPosition(SizeUtil.getScreenWidth(this) / 2, 100);
        description.setTextColor(Color.RED);
        description.setTextAlign(Paint.Align.RIGHT);    // 文字对齐的方式默认RIGHT，即文字的右侧对齐position位置
        mLineChart.setDescription(description);

        // 设置当没有数据时显示的文字和样式
        mLineChart.setNoDataText("没有数据");
        mLineChart.setNoDataTextColor(Color.RED);
        mLineChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        mLineChart.setDrawGridBackground(false);

        mLineChart.setDrawBorders(true);

        defaultSettings();
        setLegend();
        setAnimation();
        setMarkerView();

        ArrayList<Entry> v1 = new ArrayList<>();
        ArrayList<Entry> v2 = new ArrayList<>();

        v1.add(new Entry(0, 5));
        v1.add(new Entry(1, 2));
        v1.add(new Entry(2, 3));
        v1.add(new Entry(3, 12));
        v1.add(new Entry(4, 11));
        v1.add(new Entry(5, 13));
        v1.add(new Entry(6, 22));
        v1.add(new Entry(7, 43));
        v1.add(new Entry(8, 35));
        v1.add(new Entry(9, 67));
        v1.add(new Entry(10, 90));
        v1.add(new Entry(11, 100));


        v2.add(new Entry(0, 1));
        v2.add(new Entry(1, 3));
        v2.add(new Entry(2, 9));
        v2.add(new Entry(3, 16));
        v2.add(new Entry(4, 13));
        v2.add(new Entry(5, 30));
        v2.add(new Entry(6, 50));
        v2.add(new Entry(7, 44));
        v2.add(new Entry(8, 22));
        v2.add(new Entry(9, 80));
        v2.add(new Entry(10, 92));
        v2.add(new Entry(11, 101));


        List<ArrayList<Entry>> values = new ArrayList<>();
        values.add(v1);
        values.add(v2);
        List<String> lables = new ArrayList<>();
        lables.add("JAVA基础");
        lables.add("ANDROID基础");
        int[] colors = new int[]{Color.BLUE, Color.GREEN};
        setValues(values, lables, colors);

    }


    /**
     * 设置或者更新数据
     * 更加合理的做法是将设置和更新都单独写成一个方法
     *
     * @param values
     * @param lables
     */
    private void setValues(List<ArrayList<Entry>> values, List<String> lables, int[] colors) {
        if (values != null && lables != null) {
            if (mLineChart.getData() != null) { // 如果已经有数据了则直接更新
                if (values.size() != mLineChart.getData().getDataSetCount()) {
                    // 如果更新的数据与之前的数据大小不一致则抛出一个异常
                    throw new IllegalArgumentException("更新数据大小错误");
                }
                for (int i = 0; i < values.size(); i++) {
                    LineDataSet lineDataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(i);
                    lineDataSet.setValues(values.get(i));
                }
                mLineChart.getData().notifyDataChanged();
                mLineChart.notifyDataSetChanged();
            } else {    // 如果还没有数据则进行绘制
                if (values.size() != lables.size()) {
                    // 如果数据数组大小和标签数组大小不相等则抛出一个异常
                    throw new IllegalArgumentException("传入的数据大小与标签的大小不相等");
                }
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                for (int i = 0; i < values.size(); i++) {
                    LineDataSet lineDataSet = new LineDataSet(values.get(i), lables.get(i));
                    if (i < colors.length) {
                        lineDataSet.setColor(colors[i]);
                        lineDataSet.setCircleColor(colors[i]);
                    }
                    lineDataSet.setLineWidth(1f);
                    lineDataSet.setCircleRadius(3f);
                    lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
                    lineDataSet.setHighlightLineWidth(2f);
                    lineDataSet.setHighlightEnabled(true);
                    lineDataSet.setHighLightColor(Color.RED);
                    lineDataSet.setValueTextSize(12f);
                    lineDataSet.setDrawFilled(false);   // 关闭折线与坐标轴区域颜色的填充
                    lineDataSet.setFillColor(Color.RED);    // 设置折线与坐标轴区域的填充颜色，必须setDrawFilled(true)
                    lineDataSet.setDrawCircles(true);  // 是否将数据点绘制成圆形
                    lineDataSet.setDrawCircleHole(true);   // 是否在圆形数据点上绘制一个小孔
                    lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // 设置线条样式 LINEAR折线；STEPPED阶梯线；CUBIC_BEZIER曲线；HORIZONTAL_BEZIER水平的贝塞尔曲线；

                    // 格式化数据的显示形式，列如100,000,000
                    final DecimalFormat format = new DecimalFormat("###,###,###");
                    lineDataSet.setValueFormatter(new IValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                            return format.format(value);
                        }
                    });

                    dataSets.add(lineDataSet);
                }
                LineData data = new LineData(dataSets);
                mLineChart.setData(data);
                mLineChart.invalidate();
            }

            setXAxis();
            setYAxis();
        }
    }

    /**
     * 设置X轴
     */
    private void setXAxis() {
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setEnabled(true); // 设置轴是启用还是禁用，启用则后面的设置才有效
        xAxis.setDrawAxisLine(true);    // 是否绘制轴线
        xAxis.setDrawGridLines(true);  // 是否根据每个值绘制一条竖线
        xAxis.setDrawLabels(true);  // 是否绘制标签
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // 绘制轴的位置
        xAxis.enableGridDashedLine(10f, 10f, 0f); // 将每一条竖线绘制成虚线
        xAxis.setAxisMinimum(0f);   // 设置轴的最小值,尝试设置成1f然后在setValueFormatter()中会出现精度问题
        xAxis.setAxisMaximum(12f);  // 设置轴的最大值
        xAxis.setAvoidFirstLastClipping(false);  // 避免第一个和最后一个标签条目被裁剪，第一个和最后一个标签会往内部偏移
        xAxis.setLabelRotationAngle(12f);   // 标签旋转角度
        xAxis.setLabelCount(12);    // 标签个数
        xAxis.setTextColor(Color.BLUE); // 标签字体颜色
        xAxis.setTextSize(13f);  // 标签字体大小
        xAxis.setGridLineWidth(1f); // 竖线的宽度
        xAxis.setAxisLineColor(Color.BLACK);    // 轴线的颜色
        xAxis.setAxisLineWidth(1f); // 轴线的宽度
        // 设置标签的显示文字为自定义的内容
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String string = "";
                if (value == 0f) {
                    string = "Jan";
                } else if (value == 1f) {
                    string = "Feb";
                } else if (value == 2f) {
                    string = "Mar";
                } else if (value == 3f) {
                    string = "Apr";
                } else if (value == 4f) {
                    string = "May";
                } else if (value == 5f) {
                    string = "Jun";
                } else if (value == 6f) {
                    string = "Jul";
                } else if (value == 7f) {
                    string = "Aug";
                } else if (value == 8f) {
                    string = "Sep";
                } else if (value == 9f) {
                    string = "Oct";
                } else if (value == 10f) {
                    string = "Nov";
                } else if (value == 11f) {
                    string = "Dec";
                }
                return string;
            }
        });
    }

    /**
     * 设置Y轴
     * <p>
     * 其他属性与设置X轴的属性时一样的
     */
    private void setYAxis() {
        // 获取右侧轴线
        YAxis yAxisR = mLineChart.getAxisRight();
        // 禁用右侧轴线
        yAxisR.setEnabled(false);
        // 获取左侧轴线
        YAxis yAxisL = mLineChart.getAxisLeft();
        // 0所在的网格线是否绘制，测试的时候未发现什么明显的区别
        yAxisL.setDrawZeroLine(false);

    }

    /**
     * 其他的一些与图表交互的设置
     */
    private void defaultSettings() {
        mLineChart.setTouchEnabled(true);   // 设置是否可以触摸
        mLineChart.setDragEnabled(true);    // 设置是否可以拖拽
        mLineChart.setScaleEnabled(true);   // 设置是否可以缩放
        mLineChart.setScaleXEnabled(true);  // 设置是否可以缩放 X轴
        mLineChart.setScaleYEnabled(true);  // 设置是否可以缩放 Y轴
        mLineChart.setPinchZoom(true);  // 设置X轴和Y轴能否同时缩放 默认false
        mLineChart.setDoubleTapToZoomEnabled(true); // 设置是否可以双击放大
        mLineChart.setHighlightPerDragEnabled(true);    // 设置是否可以拖拽搞亮线
        mLineChart.setDragDecelerationEnabled(true);    // 设置拖拽滚动时手放开后是否还会持续滚动
        mLineChart.setDragDecelerationFrictionCoef(0.99f);  // 设置持续滚动的速度快慢 [0,1] 当值为1时会自动转换成0.999f
    }

    /**
     * 设置图例
     */
    private void setLegend() {
        Legend legend = mLineChart.getLegend();

        // 以下属性取代了原来的setPosition()方法
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        legend.setTextSize(13f);    // 图例字体大小
        legend.setForm(Legend.LegendForm.CIRCLE);   // 图例图形形状
        legend.setFormSize(13f);    // 图例图形大小
        legend.setWordWrapEnabled(true);    // 是否自动换行
        legend.setFormLineWidth(12f);   // 图例图形宽度
    }

    /**
     * 设置显示动画
     */
    private void setAnimation() {
        // 若只需单独的X轴或者Y轴有动画则调用animateX()或者animateY()方法
        mLineChart.animateXY(1000, 1000);
    }

    /**
     * 设置提示窗口
     */
    private void setMarkerView() {
        MyMarkerView mv = new MyMarkerView(this, R.layout.markerview);
        //        mv.setChartView(mLineChart);
        mLineChart.setMarker(mv);
    }


    public class MyMarkerView extends MarkerView {

        private TextView mTextView;

        /**
         * Constructor. Sets up the MarkerView with a custom layout resource.
         *
         * @param context
         * @param layoutResource the layout resource to use for the MarkerView
         */
        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            mTextView = (TextView) findViewById(R.id.textview);
        }

        /**
         * 每次重新绘制MarkerView该方法都会被调用
         *
         * @param e
         * @param highlight
         */
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            super.refreshContent(e, highlight);
            if (e instanceof CandleEntry) {
                CandleEntry candleEntry = (CandleEntry) e;
                mTextView.setText("" + Utils.formatNumber(candleEntry.getHigh(), 0, true));
            } else {
                mTextView.setText("" + Utils.formatNumber(e.getY(), 0, true));
            }


        }

        /**
         * 默认情况下MarkerView的左上角与数据点对其
         *
         * @return
         */
        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }
    }


    private void displaySelectChart(LinearLayout linearLayout,String string) {
        if ("LineChart折线图".equals(string)) {
            linearLayout.addView(ChartLibrary.lineChart(mContext));
        } else if ("BarChart条形图style0".equals(string)) {
            linearLayout.addView(ChartLibrary.barChart0(mContext));
        } else if ("BarChart条形图style1".equals(string)) {
            linearLayout.addView(ChartLibrary.barChart1(mContext));
        } else if ("ScatterChart分散图".equals(string)) {
            linearLayout.addView(ChartLibrary.scatterChart(mContext));
        } else if ("CandleStickChart烛台图".equals(string)) {
            linearLayout.addView(ChartLibrary.candleStickChart(mContext));
        } else if ("PieChart饼形图".equals(string)) {
            linearLayout.addView(ChartLibrary.pieChart(mContext));
        } else if ("BubbleChart气泡图".equals(string)) {
            linearLayout.addView(ChartLibrary.bubbleChart(mContext));
        } else if ("RadarChart雷达图".equals(string)) {
            linearLayout.addView(ChartLibrary.radarChart(mContext));
        } else if ("CombinedChart组合图".equals(string)) {
            linearLayout.addView(ChartLibrary.combinedChart(mContext));
        } else if ("HorizontalBarChart水平条形图".equals(string)) {
            linearLayout.addView(ChartLibrary.horizontalBarChart(mContext));
        }

    }


}
