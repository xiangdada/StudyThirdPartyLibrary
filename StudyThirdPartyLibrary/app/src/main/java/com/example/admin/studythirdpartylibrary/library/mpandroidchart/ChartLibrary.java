package com.example.admin.studythirdpartylibrary.library.mpandroidchart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.uitl.LogUtil;
import com.example.admin.studythirdpartylibrary.uitl.SizeUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xpf on 2017/8/16.
 */

public class ChartLibrary {


    public static Chart lineChart(Context context) {
        LineChart lineChart = new LineChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lineChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        lineChart.setLogEnabled(false);

        // 设置文字描述
        lineChart.getDescription().setEnabled(false);

        // 设置当没有数据时显示的文字和样式
        lineChart.setNoDataText("没有数据");
        lineChart.setNoDataTextColor(Color.RED);
        lineChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(true);

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        lineChart.setPinchZoom(true);

        MyMarkerView mv = new MyMarkerView(context, R.layout.markerview);
        mv.setChartView(lineChart); // 边界控制
        lineChart.setMarker(mv);

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f,10f,0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setTypeface(Typeface.DEFAULT);

        LimitLine ll2 = new LimitLine(-30f,"Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f,10f,0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setTypeface(Typeface.DEFAULT);

        YAxis yAxisL = lineChart.getAxisLeft();
        yAxisL.removeAllLimitLines();
        yAxisL.addLimitLine(ll1);
        yAxisL.addLimitLine(ll2);
        yAxisL.setAxisMaximum(200f);
        yAxisL.setAxisMinimum(-50f);
        yAxisL.enableGridDashedLine(10f,10f,0f);
        yAxisL.setDrawZeroLine(false);
        yAxisL.setDrawLimitLinesBehindData(true);

        lineChart.getAxisRight().setEnabled(false);

        List<Entry> entries = new ArrayList<>();
        for(int i=0;i<50;i++) {
            entries.add(new Entry(i,(float)(Math.random() * 100)));
        }
        LineDataSet lineDataSet;
        if(lineChart.getData() != null && lineChart.getData().getDataSetCount()>0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(entries);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(entries,"DataSet 1");
            lineDataSet.setDrawIcons(false);
            lineDataSet.enableDashedLine(10f,5f,0f);
            lineDataSet.setColor(Color.BLACK);
            lineDataSet.setCircleColor(Color.BLACK);
            lineDataSet.setLineWidth(1f);
            lineDataSet.setCircleRadius(3f);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setValueTextSize(9f);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFormLineWidth(1f);
            // 设置图例为虚线效果
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f,5f},0f));
            lineDataSet.setFormSize(16f);

            List<ILineDataSet> iLineDataSets = new ArrayList<>();
            iLineDataSets.add(lineDataSet);

            LineData lineData = new LineData(iLineDataSets);
            lineChart.setData(lineData);

        }

        lineChart.animateX(2500);

        Legend legend = lineChart.getLegend();

        // 设置图例样式是直线
        legend.setForm(Legend.LegendForm.LINE);

        lineChart.invalidate();


        return lineChart;
    }


    public static Chart barChart0(Context context) {
        BarChart barChart = new BarChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        barChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        barChart.setLogEnabled(false);

        // 设置文字描述
        Description description = new Description();
        description.setText("图书销量");
        description.setPosition(SizeUtil.getScreenWidth(context) / 2, 100);
        description.setTextColor(Color.RED);
        description.setTextAlign(Paint.Align.RIGHT);    // 文字对齐的方式默认RIGHT，即文字的右侧对齐position位置
        barChart.setDescription(description);

        // 设置当没有数据时显示的文字和样式
        barChart.setNoDataText("没有数据");
        barChart.setNoDataTextColor(Color.RED);
        barChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(true);

        // 此处用的是BarEntry而不是Entry
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(0f, 100f));
        entries.add(new BarEntry(1f, 111f));
        entries.add(new BarEntry(2f, 123f));
        entries.add(new BarEntry(3f, 151f));
        entries.add(new BarEntry(4f, 182f));
        entries.add(new BarEntry(5f, 98f));
        entries.add(new BarEntry(6f, 200f));
        entries.add(new BarEntry(7f, 121f));
        entries.add(new BarEntry(8f, 99f));
        entries.add(new BarEntry(9f, 161f));
        entries.add(new BarEntry(10f, 112f));
        entries.add(new BarEntry(11f, 191f));

        BarDataSet barDataSet = new BarDataSet(entries, "月份");
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.85f); // 设置条形宽度 default 0.85
        barChart.setData(barData);
        barChart.setFitBars(true);  // 使X轴充分的得到利用
        barChart.invalidate();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);    // 与条目数据一致，不设置可能会在setValueFormatter()方法中有些值取不到
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

        return barChart;
    }


    public static Chart barChart1(Context context) {
        BarChart barChart = new BarChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        barChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        barChart.setLogEnabled(false);

        // 设置文字描述
        Description description = new Description();
        description.setText("图书销量");
        description.setPosition(SizeUtil.getScreenWidth(context) / 2, 100);
        description.setTextColor(Color.RED);
        description.setTextAlign(Paint.Align.RIGHT);    // 文字对齐的方式默认RIGHT，即文字的右侧对齐position位置
        barChart.setDescription(description);

        // 设置当没有数据时显示的文字和样式
        barChart.setNoDataText("没有数据");
        barChart.setNoDataTextColor(Color.RED);
        barChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(true);

        // 此处用的是BarEntry而不是Entry
        List<BarEntry> entriesJava = new ArrayList<>();
        entriesJava.add(new BarEntry(0f, 100f));
        BarDataSet barDataSetJava = new BarDataSet(entriesJava, "JAVA");
        barDataSetJava.setColor(Color.YELLOW);

        List<BarEntry> entriesAndroid = new ArrayList<>();
        entriesAndroid.add(new BarEntry(1f, 112f));
        BarDataSet barDataSetAndroid = new BarDataSet(entriesAndroid, "ANDROID");
        barDataSetAndroid.setColor(Color.GREEN);

        List<BarEntry> entriesPhp = new ArrayList<>();
        entriesPhp.add(new BarEntry(2f, 200f));
        BarDataSet barDataSetPhp = new BarDataSet(entriesPhp, "PHP");
        barDataSetPhp.setColor(Color.RED);

        List<BarEntry> entriesIos = new ArrayList<>();
        entriesIos.add(new BarEntry(3f, 999f));
        BarDataSet barDataSetIos = new BarDataSet(entriesIos, "IOS");
        barDataSetIos.setColor(Color.BLUE);

        BarData barData = new BarData(barDataSetJava, barDataSetAndroid, barDataSetPhp, barDataSetIos);

        barData.setBarWidth(0.85f); // 设置条形宽度 default 0.85
        barChart.setData(barData);
        barChart.setFitBars(true);  // 使X轴充分的得到利用
        barChart.invalidate();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(4);    // 与条目数据一致，不设置可能会在setValueFormatter()方法中有些值取不到
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String string = "";
                if (value == 0f) {
                    string = "java";
                } else if (value == 1f) {
                    string = "android";
                } else if (value == 2f) {
                    string = "php";
                } else if (value == 3f) {
                    string = "ios";
                }
                return string;
            }
        });

        return barChart;
    }


    public static Chart scatterChart(Context context) {
        ScatterChart scatterChart = new ScatterChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        scatterChart.setLayoutParams(lp);

        scatterChart.setLogEnabled(false);
        // 设置文字描述
        Description description = new Description();
        description.setText("零花费用");
        description.setPosition(SizeUtil.getScreenWidth(context) / 2, 100);
        description.setTextColor(Color.RED);
        description.setTextAlign(Paint.Align.RIGHT);    // 文字对齐的方式默认RIGHT，即文字的右侧对齐position位置
        scatterChart.setDescription(description);

        // 设置当没有数据时显示的文字和样式
        scatterChart.setNoDataText("没有数据");
        scatterChart.setNoDataTextColor(Color.RED);
        scatterChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        scatterChart.setDrawGridBackground(false);
        scatterChart.setDrawBorders(true);

        List<Entry> entriesXM = new ArrayList<>();
        List<Entry> entriesXH = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 12; i++) {
            entriesXM.add(new Entry(i, random.nextFloat() * 1000));
            entriesXH.add(new Entry(i, random.nextFloat() * 1000));
        }
        ScatterDataSet scatterDataSetXM = new ScatterDataSet(entriesXM, "小明");
        ScatterDataSet scatterDataSetXH = new ScatterDataSet(entriesXH, "小红");

        scatterDataSetXM.setColor(Color.BLUE);
        scatterDataSetXH.setColor(Color.RED);

        ScatterData scatterData = new ScatterData(scatterDataSetXM, scatterDataSetXH);

        scatterChart.setData(scatterData);

        scatterChart.invalidate();

        XAxis xAxis = scatterChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(10);    // 与条目数据一致，不设置可能会在setValueFormatter()方法中有些值取不到
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(13f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                LogUtil.i("测试", "value: " + value);
                String string = "";
                if (value == 1f) {
                    string = "Jan";
                } else if (value == 2f) {
                    string = "Feb";
                } else if (value == 3f) {
                    string = "Mar";
                } else if (value == 4f) {
                    string = "Apr";
                } else if (value == 5f) {
                    string = "May";
                } else if (value == 6f) {
                    string = "Jun";
                } else if (value == 7f) {
                    string = "Jul";
                } else if (value == 8f) {
                    string = "Aug";
                } else if (value == 9f) {
                    string = "Sep";
                } else if (value == 10f) {
                    string = "Oct";
                } else if (value == 11f) {
                    string = "Nov";
                } else if (value == 12f) {
                    string = "Dec";
                }
                return string;
            }
        });

        YAxis yAxisR = scatterChart.getAxisRight();
        yAxisR.setEnabled(false);

        return scatterChart;
    }


    public static Chart candleStickChart(Context context) {
        CandleStickChart candleStickChart = new CandleStickChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        candleStickChart.setLayoutParams(lp);

        candleStickChart.setLogEnabled(false);
        // 设置文字描述
        candleStickChart.getDescription().setEnabled(false);

        // 设置当没有数据时显示的文字和样式
        candleStickChart.setNoDataText("没有数据");
        candleStickChart.setNoDataTextColor(Color.RED);
        candleStickChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        candleStickChart.setDrawGridBackground(false);
        candleStickChart.setDrawBorders(true);
        /**
         * 当CandleDataSet和CandleData的setDrawValues()都为true时，
         * 若数据点的个数小于该值则绘制数据值，反之则所有的数据点都不绘制数据值
         * 若当CandleDataSet或CandleData的setDrawValues()为false时此处的设置是不会生效的
         */
        candleStickChart.setMaxVisibleValueCount(60);
        candleStickChart.setPinchZoom(false);

        XAxis xAxis = candleStickChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis yAxisL = candleStickChart.getAxisLeft();
        yAxisL.setLabelCount(7, false);
        yAxisL.setDrawGridLines(false);
        yAxisL.setDrawAxisLine(false);

        YAxis yAxisR = candleStickChart.getAxisRight();
        yAxisR.setEnabled(false);

        candleStickChart.getLegend().setEnabled(false);

        List<CandleEntry> entries = new ArrayList<>();
        for (int i = 0; i < 200; i += 2) {
            float mult = 99f;
            float val = (float) (Math.random() * 40) + mult;

            float height = (float) (Math.random() * 9) + 8f;
            float low = (float) (Math.random() * 9) + 8f;

            float open = (float) (Math.random() * 6) + 1f;
            float close = (float) (Math.random() * 6) + 1f;
            if (i == 1) {
                LogUtil.i("数据", "val: " + val);
                LogUtil.i("数据", "height: " + height);
                LogUtil.i("数据", "low: " + low);
                LogUtil.i("数据", "open: " + open);
                LogUtil.i("数据", "close: " + close);
            }
            boolean even = i % 4 == 0;
            // 参数：X轴坐标，细线顶部，细线底部，开盘数值，收盘数值，一个图标
            entries.add(new CandleEntry(
                    i, val + height,
                    val - low,
                    even ? val + open : val - open,
                    even ? val - close : val + close,
                    context.getResources().getDrawable(R.mipmap.star)
            ));

        }

        CandleDataSet candleDataSet = new CandleDataSet(entries, "K线");
        candleDataSet.setDrawIcons(false);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleDataSet.setShadowColor(Color.DKGRAY);
        candleDataSet.setShadowWidth(0.7f);
        candleDataSet.setDecreasingColor(Color.RED);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(Color.rgb(122, 242, 84));
        candleDataSet.setIncreasingPaintStyle(Paint.Style.STROKE);
        candleDataSet.setNeutralColor(Color.BLUE);
        CandleData candleData = new CandleData(candleDataSet);
        candleStickChart.setData(candleData);
        candleStickChart.invalidate();

        int count = candleStickChart.getData().getEntryCount();
        float xScale = 1f;
        if (count > 60) {   // 设置水平方向只显示60个数据
            xScale = (float) count / (float) 60;
        }
        // 当xScale大于1时设置可以左右滑动，如此也就使得数据点的间距增大
        Matrix m = new Matrix();
        m.postScale(xScale, 1f);
        candleStickChart.getViewPortHandler().refresh(m, candleStickChart, false);

        return candleStickChart;
    }


    public static Chart pieChart(Context context) {
        PieChart pieChart = new PieChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pieChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        pieChart.setLogEnabled(false);

        // 设置文字描述
        pieChart.getDescription().setEnabled(false);

        // 设置当没有数据时显示的文字和样式
        pieChart.setNoDataText("没有数据");
        pieChart.setNoDataTextColor(Color.RED);
        pieChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);


        pieChart.setUsePercentValues(true); // 饼图上的数据是否按百分比显示
        pieChart.setExtraOffsets(5, 10, 5, 5); // 设置饼图位置的额外偏移量，当饼图与图外标签干涉时可通过此设置来调整
        pieChart.setDragDecelerationFrictionCoef(0.95f);    // 设置持续滚动的速度快慢 [0,1] 当值为1时会自动转换成0.999f
        pieChart.setCenterTextTypeface(Typeface.DEFAULT);  // 设置中心圆孔显示文字的字体
        pieChart.setDrawCenterText(true);   // 是否绘制中心圆孔中显示的文字
        pieChart.setCenterText("饼图");   // 设置中心圆孔中显示的文字
        pieChart.setHoleRadius(50f);    // 设置中心圆孔半径的大小，默认是整个饼图的50%
        pieChart.setTransparentCircleRadius(55f);   // 设置中心圆孔透明层圆形的半径大小，默认是整个饼图的55%
        pieChart.setRotationEnabled(true);  // 设置是否旋转角度
        pieChart.setRotationAngle(0);   // 设置饼图旋转的角度，通过此方法可是设置饼图展示时进行动画起始的位置
        pieChart.setHighlightPerTapEnabled(true);   // 设置是否点击扇形区域面积放大
        // 必须setHighlightPerTapEnabled(true)此方法才会执行接口中设置的回调
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e != null) {
                    LogUtil.i("测试", "value--e.getY(): " + e.getY());
                    LogUtil.i("测试", "index--h.getX(): " + e.getY());
                    LogUtil.i("测试", "DataSet index--h.getDataSetIndex(): " + h.getDataSetIndex());
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        List<PieEntry> entries = new ArrayList<>();
        String[] lables = new String[]{"android", "java", "php", "ios"};
        for (int i = 0; i < lables.length; i++) {
            entries.add(new PieEntry((float) (Math.random() * 1000 + 1000), lables[i]));
        }
        PieDataSet pieDataSet = new PieDataSet(entries, "图书销量");
        pieDataSet.setDrawIcons(false);
        pieDataSet.setSliceSpace(3f);   // 设置扇形之间的间距
        pieDataSet.setIconsOffset(new MPPointF(0, 40));
        pieDataSet.setSelectionShift(5f);   // 设置扇形点击放大的大小值

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);

        // 设置饼图上数据的属性
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
        pieData.setValueTypeface(Typeface.DEFAULT);

        pieChart.setData(pieData);
        pieChart.invalidate();

        // 动画
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        // 图例
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        // 设置饼图上标签的属性
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTypeface(Typeface.SERIF);
        pieChart.setEntryLabelTextSize(12f);

        return pieChart;
    }


    public static Chart bubbleChart(Context context) {
        BubbleChart bubbleChart = new BubbleChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bubbleChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        bubbleChart.setLogEnabled(false);

        // 设置文字描述
        bubbleChart.getDescription().setEnabled(false);

        // 设置当没有数据时显示的文字和样式
        bubbleChart.setNoDataText("没有数据");
        bubbleChart.setNoDataTextColor(Color.RED);
        bubbleChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        bubbleChart.setDrawGridBackground(false);
        bubbleChart.setDrawBorders(true);

        bubbleChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e != null) {
                    LogUtil.i("测试", "value--e.getY(): " + e.getY());
                    LogUtil.i("测试", "index--h.getX(): " + e.getY());
                    LogUtil.i("测试", "DataSet index--h.getDataSetIndex(): " + h.getDataSetIndex());
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        bubbleChart.setTouchEnabled(true);
        bubbleChart.setDragEnabled(true);
        bubbleChart.setScaleEnabled(true);

        /**
         * 当CandleDataSet和CandleData的setDrawValues()都为true时，
         * 若数据点的个数小于该值则绘制数据值，反之则所有的数据点都不绘制数据值
         * 若当CandleDataSet或CandleData的setDrawValues()为false时此处的设置是不会生效的
         */
        bubbleChart.setMaxVisibleValueCount(200);
        bubbleChart.setPinchZoom(true); // 设置X和Y轴是否可以同时进行缩放

        Legend legend = bubbleChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setDrawInside(false);
        legend.setTypeface(Typeface.DEFAULT);

        YAxis yAxisL = bubbleChart.getAxisLeft();
        yAxisL.setTypeface(Typeface.DEFAULT);
        yAxisL.setSpaceTop(30f);    // 绘制气泡的区域与整个网格顶部的间距
        yAxisL.setSpaceBottom(30f); // 绘制气泡的区域与整个网格底部的间距
        yAxisL.setDrawZeroLine(false);

        bubbleChart.getAxisRight().setEnabled(false);

        XAxis xAxis = bubbleChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(Typeface.DEFAULT);

        int[] colors = new int[]{Color.GREEN, Color.BLUE, Color.RED};
        List<IBubbleDataSet> iBubbleDataSets = new ArrayList<>();
        for (int i = 0; i < colors.length; i++) {
            List<BubbleEntry> entries = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                entries.add(new BubbleEntry(j,
                        (float) (Math.random() * 100),
                        (float) (Math.random() * 100)));
            }
            BubbleDataSet bubbleDataSet = new BubbleDataSet(entries, "BubbleDataSet" + (i + 1));
            bubbleDataSet.setDrawIcons(false);
            bubbleDataSet.setColor(colors[i]);
            bubbleDataSet.setDrawValues(true);

            if (1 == i) {
                bubbleDataSet.setIconsOffset(new MPPointF(0, 15));
            }
            iBubbleDataSets.add(bubbleDataSet);
        }

        BubbleData bubbleData = new BubbleData(iBubbleDataSets);
        bubbleData.setDrawValues(false);
        bubbleData.setValueTypeface(Typeface.DEFAULT);
        bubbleData.setValueTextSize(8f);
        bubbleData.setValueTextColor(Color.WHITE);
        bubbleData.setHighlightCircleWidth(1.5f);

        bubbleChart.setData(bubbleData);
        bubbleChart.invalidate();

        return bubbleChart;
    }


    public static Chart radarChart(Context context) {
        RadarChart radarChart = new RadarChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        radarChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        radarChart.setLogEnabled(false);

        // 设置文字描述
        radarChart.getDescription().setEnabled(false);

        // 设置当没有数据时显示的文字和样式
        radarChart.setNoDataText("没有数据");
        radarChart.setNoDataTextColor(Color.RED);
        radarChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        radarChart.setWebLineWidth(1f); // 中心点到顶点的线的宽度
        radarChart.setWebColor(Color.LTGRAY);  // 中心点到顶点的线的颜色
        radarChart.setWebLineWidthInner(1f);    // 多边形边线的宽度
        radarChart.setWebColorInner(Color.LTGRAY);  // 多边形边线的颜色
        radarChart.setWebAlpha(100);    // 线条的透明度

        // 动画
        radarChart.animateXY(1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);


        final String[] lables = new String[]{"德", "智", "体", "美", "劳"};
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTypeface(Typeface.DEFAULT);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return lables[(int) value];
            }
        });

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setTypeface(Typeface.DEFAULT);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);  // 设置是否绘制每一个多边形所代表的数据值

        Legend legend = radarChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTypeface(Typeface.DEFAULT);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(5f);
        legend.setTextColor(Color.BLUE);

        List<IRadarDataSet> iRadarDataSets = new ArrayList<>();
        int edges = 5;  // 边数
        int[] colors = new int[]{Color.RED, Color.GREEN};
        String[] legends = new String[]{"小红", "小明"};
        for (int i = 0; i < colors.length; i++) {
            List<RadarEntry> entries = new ArrayList<>();
            for (int j = 0; j < edges; j++) {
                entries.add(new RadarEntry((float) (Math.random() * 80 + 20)));
            }
            RadarDataSet radarDataSet = new RadarDataSet(entries, legends[i]);
            radarDataSet.setColor(colors[i]);   // 数据点连接线的颜色
            radarDataSet.setFillColor(colors[i]);   // 填充区域的颜色
            radarDataSet.setDrawFilled(true);   // 是否填充数据区域
            radarDataSet.setFillAlpha(180); // 数据填充区域的透明度
            radarDataSet.setLineWidth(2f);  // 数据点连接线的宽度
            radarDataSet.setDrawHighlightCircleEnabled(true);   // 是否点击数据点时绘制一个圆形的标记符
            radarDataSet.setDrawHighlightIndicators(true);  // 是否点击数据点时绘制垂直交叉的两条直线来标记点击的数据点
            iRadarDataSets.add(radarDataSet);
        }
        RadarData radarData = new RadarData(iRadarDataSets);
        radarData.setValueTypeface(Typeface.DEFAULT);
        radarData.setValueTextSize(8f);
        radarData.setDrawValues(false);
        radarData.setValueTextColor(Color.WHITE);

        radarChart.setData(radarData);
        radarChart.invalidate();

        return radarChart;

    }


    public static Chart combinedChart(Context context) {
        CombinedChart combinedChart = new CombinedChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        combinedChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        combinedChart.setLogEnabled(false);

        // 设置文字描述
        combinedChart.getDescription().setEnabled(false);

        // 设置当没有数据时显示的文字和样式
        combinedChart.setNoDataText("没有数据");
        combinedChart.setNoDataTextColor(Color.RED);
        combinedChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBorders(false);
        combinedChart.setHighlightFullBarEnabled(false); // 在做测试的时候设置了为true点击图表的时候报了数组越界的错误，估计是测试代码有些地方没写好

        // 设置有哪些图表的数据会被提供进行组合图表绘制
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.BUBBLE,
                CombinedChart.DrawOrder.CANDLE,
                CombinedChart.DrawOrder.LINE,
                CombinedChart.DrawOrder.SCATTER
        });

        Legend legend = combinedChart.getLegend();
        legend.setWordWrapEnabled(true);    // 设置图例是否包裹文字，这样可以设置文字和颜色一起进行自动换行
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        YAxis yAxisR = combinedChart.getAxisRight();
        yAxisR.setDrawGridLines(false);
        yAxisR.setAxisMinimum(0f);

        YAxis yAxisL = combinedChart.getAxisLeft();
        yAxisL.setDrawGridLines(false);
        yAxisL.setAxisMinimum(0f);

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String string = "";
                if ((int) value < months.length) {
                    string = months[(int) value];
                }
                return string;
            }
        });

        CombinedData combinedData = new CombinedData();
        combinedData.setData(generateLineData());
        combinedData.setData(generateBarData());
        combinedData.setData(generateBubbleData());
        combinedData.setData(generateScatterData());
        combinedData.setData(generateCandleData());
        combinedData.setValueTypeface(Typeface.DEFAULT);

        xAxis.setAxisMaximum(combinedData.getXMax() + 0.25f);

        combinedChart.setData(combinedData);
        combinedChart.invalidate();

        return combinedChart;
    }


    public static Chart horizontalBarChart(Context context) {
        HorizontalBarChart horizontalBarChart = new HorizontalBarChart(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        horizontalBarChart.setLayoutParams(lp);

        // 关闭chart的LogCat输出
        horizontalBarChart.setLogEnabled(false);

        // 设置文字描述
        horizontalBarChart.getDescription().setEnabled(false);

        // 设置当没有数据时显示的文字和样式
        horizontalBarChart.setNoDataText("没有数据");
        horizontalBarChart.setNoDataTextColor(Color.RED);
        horizontalBarChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);

        horizontalBarChart.setDrawGridBackground(false);
        horizontalBarChart.setDrawBorders(true);

        horizontalBarChart.setDrawBarShadow(false);  // 是否给每个条目绘制阴影
        horizontalBarChart.setDrawValueAboveBar(true);  // 是否在每个条目上面绘制数据值
        /**
         * 当CandleDataSet和CandleData的setDrawValues()都为true时，
         * 若数据点的个数小于该值则绘制数据值，反之则所有的数据点都不绘制数据值
         * 若当CandleDataSet或CandleData的setDrawValues()为false时此处的设置是不会生效的
         */
        horizontalBarChart.setMaxVisibleValueCount(60);
        horizontalBarChart.setPinchZoom(false); // 是否支持在X轴和Y轴上面同时进行缩放的操作

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(Typeface.DEFAULT);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(value);
            }
        });

        YAxis yAxisL = horizontalBarChart.getAxisLeft();
        yAxisL.setTypeface(Typeface.DEFAULT);
        yAxisL.setDrawAxisLine(true);
        yAxisL.setDrawGridLines(false);
        yAxisL.setAxisMinimum(0f);

        YAxis yAxisR = horizontalBarChart.getAxisRight();
        yAxisR.setTypeface(Typeface.DEFAULT);
        yAxisR.setDrawAxisLine(true);
        yAxisL.setDrawGridLines(false);
        yAxisR.setAxisMinimum(0f);


        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (float) (Math.random() * 100)));
        }
        BarDataSet barDataSet;
        if (horizontalBarChart.getData() != null && horizontalBarChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) horizontalBarChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(entries);
            horizontalBarChart.getData().notifyDataChanged();
            horizontalBarChart.notifyDataSetChanged();
        } else {
            barDataSet = new BarDataSet(entries, "数据");
            barDataSet.setDrawIcons(false);

            List<IBarDataSet> iBarDatasets = new ArrayList<>();
            iBarDatasets.add(barDataSet);

            BarData barData = new BarData(iBarDatasets);
            barData.setValueTextSize(10f);
            barData.setValueTypeface(Typeface.DEFAULT);
            barData.setBarWidth(0.9f);
            horizontalBarChart.setData(barData);
        }

        horizontalBarChart.setFitBars(true);
        horizontalBarChart.animateY(2500);

        Legend legend = horizontalBarChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(8f);
        legend.setXEntrySpace(4f);

        return horizontalBarChart;
    }


    private static LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++)
            entries.add(new Entry(index + 0.5f, getRandom(15, 5)));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }


    private static BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        for (int index = 0; index < 12; index++) {
            entries1.add(new BarEntry(0, getRandom(25, 25)));

            // stacked
            entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }


    protected static ScatterData generateScatterData() {

        ScatterData d = new ScatterData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (float index = 0; index < 12; index += 0.5f)
            entries.add(new Entry(index + 0.25f, getRandom(10, 55)));

        ScatterDataSet set = new ScatterDataSet(entries, "Scatter DataSet");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setScatterShapeSize(7.5f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        return d;
    }


    protected static CandleData generateCandleData() {

        CandleData d = new CandleData();

        ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();

        for (int index = 0; index < 12; index += 2)
            entries.add(new CandleEntry(index + 1f, 90, 70, 85, 75f));

        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setDecreasingColor(Color.rgb(142, 150, 175));
        set.setShadowColor(Color.DKGRAY);
        set.setBarSpace(0.3f);
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        d.addDataSet(set);

        return d;
    }


    protected static BubbleData generateBubbleData() {

        BubbleData bd = new BubbleData();

        ArrayList<BubbleEntry> entries = new ArrayList<BubbleEntry>();

        for (int index = 0; index < 12; index++) {
            float y = getRandom(10, 105);
            float size = getRandom(100, 105);
            entries.add(new BubbleEntry(index + 0.5f, y, size));
        }

        BubbleDataSet set = new BubbleDataSet(entries, "Bubble DataSet");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.WHITE);
        set.setHighlightCircleWidth(1.5f);
        set.setDrawValues(true);
        bd.addDataSet(set);

        return bd;
    }


    private static float getRandom(int i, int j) {
        return (float) (Math.random() * i + j);
    }


    public static class MyMarkerView extends MarkerView {

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
}
