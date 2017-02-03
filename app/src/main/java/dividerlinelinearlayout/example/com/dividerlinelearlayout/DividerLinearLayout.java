package dividerlinelinearlayout.example.com.dividerlinelearlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by ZhaoLiBing on 2016/2/3.
 */

public class DividerLinearLayout extends LinearLayout {
    private int dividerLineWidth = 0;
    private boolean dividerHeaderLineEnable = false;
    private boolean dividerFooterLineEnable = false;
    private int dividerHeaderStart;
    private int dividerFooterStart;
    private int dividerHeaderEnd;
    private int dividerFooterEnd;
    private GradientDrawable mDrawable;
    private int mDividerColor;
    private int paddingTop;
    private int paddingBottom;
    private int paddingRight;
    private int paddingleft;

    public DividerLinearLayout(Context context) {
        super(context);
        initDrawableDivider();
    }
    public DividerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawableDivider();
        adjustPadding();
        resolveAttributeSet(attrs);
    }
    public DividerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDrawableDivider();
        adjustPadding();
        resolveAttributeSet(attrs);
    }
    private void initDrawableDivider() {
        setShowDividers(SHOW_DIVIDER_MIDDLE);
        setDividerDrawableAttributeSet();
    }

    private void setDividerDrawableAttributeSet() {
        mDrawable = new GradientDrawable();
        mDrawable.setColor(mDividerColor);
        mDrawable.setShape(GradientDrawable.RECTANGLE);
        if (getOrientation() == VERTICAL){
            mDrawable.setSize(getMeasuredWidth(),dividerLineWidth);
        }else
            mDrawable.setSize(dividerLineWidth,getMeasuredWidth());
        setDividerDrawable(mDrawable);
    }
    private void adjustPadding() {
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        paddingleft = getPaddingLeft();
        paddingRight = getPaddingRight();
        setPadding(paddingleft,paddingTop,paddingRight,paddingBottom);
    }
    private void resolveAttributeSet(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.dividerline);
        for (int i =0;i<a.getIndexCount();i++){
            int index = a.getIndex(i);
            switch (index){
                case R.styleable.dividerline_dividerLineColor:
                    mDividerColor = a.getColor(index,getResources().getColor(R.color.colorAccent));
                    break;
                case R.styleable.dividerline_dividerLineWidth:
                    dividerLineWidth = ((int) a.getDimension(index, 0));
                    break;
                case R.styleable.dividerline_headerEnable:
                    dividerHeaderLineEnable = a.getBoolean(index,false);
                    break;
                case R.styleable.dividerline_footerEnable:
                    dividerFooterLineEnable = a.getBoolean(index,false);
                    break;
                case R.styleable.dividerline_paddingHeaderStart:
                    dividerHeaderStart = ((int) a.getDimension(index, 0));
                    break;
                case R.styleable.dividerline_paddingHeaderEnd:
                    dividerHeaderEnd = ((int) a.getDimension(index, 0));
                    break;
                case R.styleable.dividerline_paddingFooterStart:
                    dividerFooterStart = ((int) a.getDimension(index, 0));
                    break;
                case R.styleable.dividerline_paddingFooterEnd:
                    dividerFooterEnd = ((int)a.getDimension(index, 0));
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dividerHeaderLineEnable){
            drawableHeaderLine(canvas);
        }
        if (dividerFooterLineEnable){
            drawableFooterLine(canvas);
        }

    }

    private void drawableHeaderLine(Canvas canvas) {
        View child = findFirstView();
        if (child != null)
            if (getOrientation() == VERTICAL)
                drawHeaderDividerVertical(child, canvas);
            else
                drawHeaderDividerHorizontal(child, canvas);

    }

    private void drawHeaderDividerVertical(View child, Canvas canvas) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int top = child.getTop() - lp.topMargin - dividerLineWidth;
        mDrawable.setBounds(dividerHeaderStart, top,
                getMeasuredWidth() - dividerFooterEnd, top + dividerLineWidth);
        mDrawable.draw(canvas);
    }

    private void drawHeaderDividerHorizontal(View child, Canvas canvas) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int left = child.getLeft() - lp.leftMargin - dividerLineWidth;
        mDrawable.setBounds(left, dividerHeaderStart, left + dividerLineWidth, getMeasuredHeight() - dividerHeaderEnd);
        mDrawable.draw(canvas);
    }

    private View findFirstView() {
        if (getChildCount() > 0){
            for (int i=0;i<getChildCount();i++){
                if (getChildAt(i) != null && getChildAt(i).getVisibility() != GONE)
                    return getChildAt(i);
            }
        }
        return null;
    }

    private void drawableFooterLine(Canvas canvas){
        View child = findLastView();
        if (child != null)
            if (getOrientation() == VERTICAL)
                drawFooterDividerVertical(child, canvas);
            else
                drawFooterDividerHorizontal(child, canvas);
    }

    private void drawFooterDividerVertical(View child, Canvas canvas) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int bottom = child.getBottom() + lp.bottomMargin + dividerLineWidth;
        mDrawable.setBounds(dividerFooterStart, bottom - dividerLineWidth,
                getMeasuredWidth() - dividerFooterEnd, bottom);
        mDrawable.draw(canvas);
    }

    private void drawFooterDividerHorizontal(View child, Canvas canvas) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int right = child.getRight() + lp.rightMargin + dividerLineWidth;
        mDrawable.setBounds(right - dividerLineWidth, dividerFooterStart, right, getMeasuredHeight() - dividerFooterEnd);
        mDrawable.draw(canvas);
    }

    private View findLastView() {
        if (getChildCount() > 0) {
            for (int i = getChildCount() - 1; i >= 0; i--) {
                if (getChildAt(i) != null && getChildAt(i).getVisibility() != GONE) {
                    return getChildAt(i);
                }
            }
        }
        return null;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (getOrientation() == VERTICAL){
            top = dividerLineWidth + top;
        }else
            left = dividerLineWidth + left;
        super.setPadding(left, top, right, bottom);
    }
    public void showHeaderDivider(boolean visiable){
        if (dividerHeaderLineEnable != visiable)
            resetPadding();
        this.dividerHeaderLineEnable = visiable;
        adjustPadding();
        postInvalidate();
    }
    public void showFooterDivider(boolean visiable){
        if (dividerFooterLineEnable != visiable)
            resetPadding();
        this.dividerFooterLineEnable = visiable;
        adjustPadding();
        postInvalidate();
    }

    private void resetPadding() {
        if (dividerHeaderLineEnable && dividerLineWidth > 0){
            if (getOrientation() == VERTICAL){
                super.setPadding(paddingleft,paddingTop-dividerLineWidth,paddingRight,paddingBottom);
            }else
                super.setPadding(paddingleft - dividerLineWidth,paddingTop,paddingRight,paddingBottom);
        }
        if (dividerFooterLineEnable && dividerLineWidth > 0){
            if (getOrientation() == VERTICAL){
                super.setPadding(paddingleft,paddingTop,paddingRight,paddingBottom - dividerLineWidth);
            }else
                super.setPadding(paddingleft,paddingTop,paddingRight - dividerLineWidth,paddingBottom);
        }
    }
    public void setDividerWidth(int width){
        if (dividerLineWidth != width)
            resetPadding();
        this.dividerLineWidth = width;
        adjustPadding();
        setDividerDrawableAttributeSet();
    }
    public void setDividerColor(int color){
        if (mDividerColor != color){
            resetPadding();
            this.mDividerColor = color;
            setDividerDrawableAttributeSet();
            postInvalidate();
        }
    }
    public void setHeaderPadding(int start,int end){
        if (dividerHeaderStart != start || dividerHeaderEnd != end){
            this.dividerHeaderStart = start;
            this.dividerHeaderEnd = end;
            postInvalidate();
        }
    }
    public void setFooterPadding(int start,int end){
        if (dividerFooterStart != start || dividerFooterEnd != end){
            this.dividerFooterStart = start;
            this.dividerFooterEnd = end;
            postInvalidate();
        }
    }
}
