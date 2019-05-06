package com.angcyo.uikitdemo.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.angcyo.uikitdemo.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;

/**
 * 多重水波纹
 * Created by SCWANG on 2017/12/11.
 * https://github.com/scwang90/MultiWaveHeader
 */
@SuppressWarnings("unused")
public class WaveView extends View {

    private Paint mPaint = new Paint();
    private Matrix mMatrix = new Matrix();
    private List<Wave> mltWave = new ArrayList<>();
    private int mWaveHeight;
    private int mStartColor;
    private int mCloseColor;
    private int mGradientAngle;
    private boolean mIsRunning = true;
    private float mVelocity;
    private float mColorAlpha;
    private float mProgress;
    private long mLastTime = 0;

    public WaveView(Context context) {
        this(context, null, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint.setAntiAlias(true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaveView);

        mWaveHeight = ta.getDimensionPixelOffset(R.styleable.WaveView_mwhWaveHeight, dp2px(50));
        mStartColor = ta.getColor(R.styleable.WaveView_mwhStartColor, 0xFF056CD0);
        mCloseColor = ta.getColor(R.styleable.WaveView_mwhCloseColor, 0xFF31AFFE);
        mColorAlpha = ta.getFloat(R.styleable.WaveView_mwhColorAlpha, 0.45f);
        mProgress = ta.getFloat(R.styleable.WaveView_mwhProgress, 1f);
        mVelocity = ta.getFloat(R.styleable.WaveView_mwhVelocity, 1f);
        mGradientAngle = ta.getInt(R.styleable.WaveView_mwhGradientAngle, 45);
        mIsRunning = ta.getBoolean(R.styleable.WaveView_mwhIsRunning, mIsRunning);

        if (ta.hasValue(R.styleable.WaveView_mwhWaves)) {
            setTag(ta.getString(R.styleable.WaveView_mwhWaves));
        } else if (getTag() == null) {
            //int offsetX, int offsetY, float scaleX, float scaleY, int velocity,
            setTag("0,0,1,1,40\n100,0,1,0.8,-30\n250,0,1,1.2,25");
        }
        ta.recycle();
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param colorId 颜色ID
     * @return 颜色
     */
    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorId);
        }
        //noinspection deprecation
        return context.getResources().getColor(colorId);
    }

    /**
     * dp转px
     *
     * @param dpVal dp 值
     * @return px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateWavePath(w, h);
        updateLinearGradient(w, h);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mltWave.size() > 0 && mPaint != null) {
            View thisView = this;
            int height = thisView.getHeight();
            long thisTime = System.currentTimeMillis();
            for (Wave wave : mltWave) {
                mMatrix.reset();
                canvas.save();
                if (mLastTime > 0 && wave.velocity != 0) {
                    float offsetX = (wave.offsetX - (wave.velocity * mVelocity * (thisTime - mLastTime) / 1000f));
                    if (-wave.velocity > 0) {
                        offsetX %= wave.width / 2;
                    } else {
                        while (offsetX < 0) {
                            offsetX += (wave.width / 2);
                        }
                    }
                    wave.offsetX = offsetX;
                    mMatrix.setTranslate(offsetX, (1 - mProgress) * height);//wave.offsetX =
                    canvas.translate(-offsetX, -wave.offsetY - (1 - mProgress) * height);
                } else {
                    mMatrix.setTranslate(wave.offsetX, (1 - mProgress) * height);
                    canvas.translate(-wave.offsetX, -wave.offsetY - (1 - mProgress) * height);
                }
                mPaint.getShader().setLocalMatrix(mMatrix);
                canvas.drawPath(wave.path, mPaint);
                canvas.restore();
            }
            mLastTime = thisTime;
        }
        if (mIsRunning) {
            invalidate();
        }
    }

    private void updateLinearGradient(int width, int height) {
        int startColor = ColorUtils.setAlphaComponent(mStartColor, (int) (mColorAlpha * 255));
        int closeColor = ColorUtils.setAlphaComponent(mCloseColor, (int) (mColorAlpha * 255));
        //noinspection UnnecessaryLocalVariable
        double w = width;
        double h = height * mProgress;
        double r = Math.sqrt(w * w + h * h) / 2;
        double y = r * Math.sin(2 * Math.PI * mGradientAngle / 360);
        double x = r * Math.cos(2 * Math.PI * mGradientAngle / 360);
        mPaint.setShader(new LinearGradient((int) (w / 2 - x), (int) (h / 2 - y),
                (int) (w / 2 + x), (int) (h / 2 + y),
                startColor, closeColor,
                Shader.TileMode.CLAMP));
    }

    private void updateWavePath(int w, int h) {

        mltWave.clear();

        if (getTag() instanceof String) {
            String[] waves = getTag().toString().split("\\s+");
            if ("-1".equals(getTag())) {
                waves = "70,25,1.4,1.4,-26\n100,5,1.4,1.2,15\n420,0,1.15,1,-10\n520,10,1.7,1.5,20\n220,0,1,1,-15".split("\\s+");
            } else if ("-2".equals(getTag())) {
                waves = "0,0,1,0.5,90\n90,0,1,0.5,90".split("\\s+");
            }
            for (String wave : waves) {
                String[] args = wave.split("\\s*,\\s*");
                if (args.length == 5) {
                    mltWave.add(new Wave(dp2px(parseFloat(args[0])), dp2px(parseFloat(args[1])),
                            dp2px(parseFloat(args[4])),
                            parseFloat(args[2]), parseFloat(args[3]),
                            w, h, mWaveHeight / 2));
                }
            }
        } else {
            mltWave.add(new Wave(dp2px(50), dp2px(0),
                    dp2px(5),
                    1.7f, 2f,
                    w, h, mWaveHeight / 2));
        }

    }

    public void setWaves(String waves) {
        setTag(waves);
        if (mLastTime > 0) {
            View thisView = this;
            updateWavePath(thisView.getWidth(), thisView.getHeight());
        }
    }

    public int getWaveHeight() {
        return mWaveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        this.mWaveHeight = dp2px(waveHeight);
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateWavePath(thisView.getWidth(), thisView.getHeight());
        }
    }

    public float getVelocity() {
        return mVelocity;
    }

    public void setVelocity(float velocity) {
        this.mVelocity = velocity;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        if (mPaint != null) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public int getGradientAngle() {
        return mGradientAngle;
    }

    public void setGradientAngle(int angle) {
        this.mGradientAngle = angle;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public int getStartColor() {
        return mStartColor;
    }

    public void setStartColor(int color) {
        this.mStartColor = color;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public void setStartColorId(@ColorRes int colorId) {
        final View thisView = this;
        setStartColor(getColor(thisView.getContext(), colorId));
    }

    public int getCloseColor() {
        return mCloseColor;
    }

    public void setCloseColor(int color) {
        this.mCloseColor = color;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public void setCloseColorId(@ColorRes int colorId) {
        final View thisView = this;
        setCloseColor(getColor(thisView.getContext(), colorId));
    }

    public float getColorAlpha() {
        return mColorAlpha;
    }

    public void setColorAlpha(float alpha) {
        this.mColorAlpha = alpha;
        if (!mltWave.isEmpty()) {
            View thisView = this;
            updateLinearGradient(thisView.getWidth(), thisView.getHeight());
        }
    }

    public void start() {
        if (!mIsRunning) {
            mIsRunning = true;
            mLastTime = System.currentTimeMillis();
            invalidate();
        }
    }

    public void stop() {
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    /**
     * 水波对象
     * Created by SCWANG on 2017/12/11.
     */
    static class Wave /*extends View*/ {

        Path path;          //水波路径
        int width;          //画布宽度（2倍波长）
        int wave;           //波幅（振幅）
        float offsetX;        //水波的水平偏移量
        float offsetY;        //水波的竖直偏移量
        float velocity;       //水波移动速度（像素/秒）
        private float scaleX;       //水平拉伸比例
        private float scaleY;       //竖直拉伸比例
//    int startColor;     //开始颜色
//    int closeColor;     //结束颜色
//    float alpha;        //颜色透明度

        /**
         * 通过参数构造一个水波对象
         *
         * @param offsetX  水平偏移量
         * @param offsetY  竖直偏移量
         * @param velocity 移动速度（像素/秒）
         * @param scaleX   水平拉伸量
         * @param scaleY   竖直拉伸量
         * @param w        波长
         * @param h        画布高度
         * @param wave     波幅（波宽度）
         */
        Wave(int offsetX, int offsetY, int velocity, float scaleX, float scaleY, int w, int h, int wave) {
            this.width = (int) (2 * scaleX * w); //画布宽度（2倍波长）
            this.wave = wave;           //波幅（波宽）
            this.scaleX = scaleX;       //水平拉伸量
            this.scaleY = scaleY;       //竖直拉伸量
            this.offsetX = offsetX;     //水平偏移量
            this.offsetY = offsetY;     //竖直偏移量
            this.velocity = velocity;   //移动速度（像素/秒）
            this.path = buildWavePath(width, h);
        }


        public void updateWavePath(int w, int h, int waveHeight) {
            this.wave = (wave > 0) ? wave : waveHeight / 2;
            this.width = (int) (2 * scaleX * w);  //画布宽度（2倍波长）
            this.path = buildWavePath(width, h);
        }


        private Path buildWavePath(int width, int height) {
            int DP = dp2px(1);//一个dp在当前设备表示的像素量（水波的绘制精度设为一个dp单位）
            if (DP < 1) {
                DP = 1;
            }

            int wave = (int) (scaleY * this.wave);//计算拉伸之后的波幅

            Path path = new Path();
            path.moveTo(0, 0);
            path.lineTo(0, height - wave);

            for (int x = DP; x < width; x += DP) {
                path.lineTo(x, height - wave - wave * (float) Math.sin(4.0 * Math.PI * x / width));
            }

            path.lineTo(width, height - wave);
            path.lineTo(width, 0);
            path.close();
            return path;
        }
    }
}
