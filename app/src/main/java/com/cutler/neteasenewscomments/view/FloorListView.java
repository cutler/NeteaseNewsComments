package com.cutler.neteasenewscomments.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutler.neteasenewscomments.R;
import com.cutler.neteasenewscomments.model.Floor;
import com.cutler.neteasenewscomments.util.DpPxUtil;

import java.util.List;

public class FloorListView extends LinearLayout implements View.OnClickListener {

    private Drawable mRectDrawable;
    private Drawable mLineDrawable;
    private List<Floor> mFloorList;
    private View.OnClickListener onShowHideButtonClickListener;

    public FloorListView(Context context) {
        super(context);
        init(context);
    }

    public FloorListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloorListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        // 设置为垂直方向
        this.setOrientation(LinearLayout.VERTICAL);
        mRectDrawable = getResources().getDrawable(R.drawable.bg_comment_rect);
        mLineDrawable = getResources().getDrawable(R.drawable.bg_comment_line);
    }

    public void setFloorList(List<Floor> mFloorList) {
        this.mFloorList = mFloorList;
        resetLayout();
    }

    public void appendFloorList(List<Floor> floorList) {
        if (floorList != null) {
            List<Floor> oldFloorList = this.mFloorList;
            int index = -1;
            for (int i = 0; i < oldFloorList.size(); i++) {
                Floor floor = oldFloorList.get(i);
                if (floor.isHide()) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                oldFloorList.remove(index);
                oldFloorList.addAll(index, floorList);
                resetLayout();
            }
        }
    }

    private void resetLayout() {
        removeAllViews();
        for (int i = 0; i < mFloorList.size(); i++) {
            Floor floor = mFloorList.get(i);
            if (floor.isHide()) {
                View view = createFloorViewForHide(floor, getContext());
                view.setOnClickListener(this);
                addView(view);
            } else {
                addView(createFloorViewForNormal(floor, getContext()));
            }
        }
        reLayoutChildren();
    }

    /**
     * 重新布局子View的位置，呈现出楼层的效果
     */
    public void reLayoutChildren() {
        int count = getChildCount();
        int density = (int) (3.0F * getResources().getDisplayMetrics().density);
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            int margin = Math.min((count - i - 1), 4) * density;
            layout.leftMargin = margin;
            layout.rightMargin = margin;
            if (i == 0) {
                layout.topMargin = 4 * density;
            }
            view.setLayoutParams(layout);
        }
    }

    /**
     * 分发给子组件进行绘制，给每个子View画背景
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count = getChildCount();
        if (null != mRectDrawable && count > 0) {
            for (int j = count - 1; j >= 0; j--) {
                View view = getChildAt(j);
                // drawable将在被绘制在canvas的哪个矩形区域内。
                if (count > 5 && j < count - 5) {
                    mLineDrawable.setBounds(view.getLeft(), view.getBottom() - DpPxUtil.dp2px(getContext(), 2),
                            view.getRight(), view.getBottom());
                    mLineDrawable.draw(canvas);
                } else {
                    mRectDrawable.setBounds(view.getLeft(), view.getLeft(),
                            view.getRight(), view.getBottom());
                    mRectDrawable.draw(canvas);
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.getChildCount() <= 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public View createFloorViewForNormal(Floor floor, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_post_sub_comments_normal, null);
        TextView floorNum = (TextView) view.findViewById(R.id.sub_floor_num);
        TextView username = (TextView) view.findViewById(R.id.sub_floor_username);
        TextView content = (TextView) view.findViewById(R.id.sub_floor_content);
        floorNum.setText(String.valueOf(floor.getFloorId()));
        username.setText(floor.getUserName());
        content.setText(floor.getContent());
        return view;
    }

    public View createFloorViewForHide(Floor floor, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_post_sub_comments_hide, null);
        TextView hide_text = (TextView) view.findViewById(R.id.hide_text);
        hide_text.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_comment_down_arrow, 0, 0, 0);
        view.findViewById(R.id.hide_pb).setVisibility(View.GONE);
        return view;
    }

    public void setOnShowHideButtonClickListener(OnClickListener onShowHideButtonClickListener) {
        this.onShowHideButtonClickListener = onShowHideButtonClickListener;
    }

    @Override
    public void onClick(View view) {
        TextView hide_text = (TextView) view.findViewById(R.id.hide_text);
        hide_text.setVisibility(View.GONE);
        view.findViewById(R.id.hide_pb).setVisibility(View.VISIBLE);
        if (onShowHideButtonClickListener != null) {
            onShowHideButtonClickListener.onClick(view);
        }
    }
}
