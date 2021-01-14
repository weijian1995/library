package com.qiwei.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiwei.library.R;
import com.qiwei.library.utils.DensityUtils;

import java.util.Objects;


/**
 * Created by shuan on 2018/5/9.
 * 选择框
 */

public class FormSelectLayout extends LinearLayout {

    private Context context;
    private ImageView leftImage, rightImage;
    private TextView need;
    private TextView textView, unitTv;
    private EditText editText;
    private int backgroundResourceId;
    private int divideRes;
    private boolean isCheck;

    public FormSelectLayout(Context context) {
        super(context);
    }

    public FormSelectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormSelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormSelectLayout);
        String text = a.getString(R.styleable.FormSelectLayout_select_text);
        String select_text = a.getString(R.styleable.FormSelectLayout_select_right_text);
        String hint = a.getString(R.styleable.FormSelectLayout_select_hint);
        boolean isNeed = a.getBoolean(R.styleable.FormSelectLayout_select_isNeed,false);
        boolean showRight = a.getBoolean(R.styleable.FormSelectLayout_select_show_right,true);
        boolean showUnit = a.getBoolean(R.styleable.FormSelectLayout_select_showUnit, false);
        boolean rightBold = a.getBoolean(R.styleable.FormSelectLayout_select_right_bold, false);
        boolean showDivide = a.getBoolean(R.styleable.FormSelectLayout_select_show_divide, false);
        int leftResourceId = a.getResourceId(R.styleable.FormSelectLayout_select_left_img, 0);
        int leftImgMargin = (int) a.getDimension(R.styleable.FormSelectLayout_select_left_img_margin, DensityUtils.dp2px(context, 15));
        int paddingLeft = (int) a.getDimension(R.styleable.FormSelectLayout_select_padding_left, 0);
        int paddingRight = (int) a.getDimension(R.styleable.FormSelectLayout_select_padding_right, 0);
        String unitText = a.getString(R.styleable.FormSelectLayout_select_unitText);
        String smallText = a.getString(R.styleable.FormSelectLayout_select_smallText);
        int rightSize = a.getInteger(R.styleable.FormSelectLayout_select_right_text_size, 14);
        int leftSize = a.getInteger(R.styleable.FormSelectLayout_select_text_size, 14);
        int rightColor = a.getColor(R.styleable.FormSelectLayout_select_right_text_color, Color.parseColor("#222222"));
        backgroundResourceId = a.getResourceId(R.styleable.FormSelectLayout_select_background, 0);
        int rightImg = a.getResourceId(R.styleable.FormSelectLayout_select_right_img, R.mipmap.ic_more_indicator_gray);
        divideRes = a.getResourceId(R.styleable.FormSelectLayout_select_divide_res, R.drawable.shape_divide_f2);
        a.recycle();

        int padding15 = DensityUtils.dp2px(context,15);
        setPadding(paddingLeft, padding15, paddingRight, padding15);

        showDivide(showDivide);

        leftImage = new ImageView(context);
        LayoutParams imgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imgParams.setMargins(0, 0, leftImgMargin, 0);
        leftImage.setLayoutParams(imgParams);
        leftImage.setVisibility(GONE);
        if (leftResourceId != 0) {
            leftImage.setVisibility(VISIBLE);
            leftImage.setImageResource(leftResourceId);
        }

        need = new TextView(context);
        need.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        need.setGravity(Gravity.CENTER_VERTICAL);
        need.setTextColor(context.getResources().getColor(R.color.color_need));
        need.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        need.setText(R.string.text_necessary);
        if (isNeed) {
            need.setVisibility(VISIBLE);
        } else {
            need.setVisibility(GONE);
        }

        textView = new TextView(context);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(context.getResources().getColor(R.color.color_black));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,leftSize);
        textView.setText(text);


        editText = new NoClickEditText(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
        params.setMargins(DensityUtils.dp2px(context,20),0,0,0);
        editText.setLayoutParams(params);
        editText.setBackgroundResource(R.color.transparent);
        int padding3 = DensityUtils.dp2px(context,3);
        editText.setPadding(0,padding3,0,padding3);
        editText.setTextColor(rightColor);
        editText.setHintTextColor(Color.parseColor("#999999"));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,rightSize);
        editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        editText.setHint(hint);
        editText.setText(select_text);
//        if (showRight) {
//            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.mipmap.ic_more_indicator_gray, null);
//            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            editText.setCompoundDrawables(null,null,drawable,null);
//            editText.setCompoundDrawablePadding(DensityUtils.dp2px(context,5));
//        }
        if (rightBold) {
            editText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setEnabled(false);
        editText.setClickable(false);

        rightImage = new ImageView(context);
        LayoutParams rightImgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightImgParams.setMargins(DensityUtils.dp2px(context,5), DensityUtils.dp2px(context,1.5f), 0, DensityUtils.dp2px(context,1f));
        rightImage.setLayoutParams(rightImgParams);
        rightImage.setVisibility(showRight ? VISIBLE : GONE);
        rightImage.setImageResource(rightImg);

        unitTv = new TextView(context);
        LayoutParams unitParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        unitParams.setMargins(DensityUtils.dp2px(context, 2), 0, 0, 0);
        unitTv.setLayoutParams(unitParams);
        unitTv.setTextColor(context.getResources().getColor(R.color.color_black));
        unitTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        unitTv.setText(unitText);
        unitTv.setVisibility(showUnit ? VISIBLE : GONE);


        this.addView(leftImage);
        this.addView(need);
        this.addView(textView);
        this.addView(editText);
        this.addView(rightImage);
        this.addView(unitTv);
    }

    public void setNoClick() {
        setEnabled(false);
        showRight(false);
        editText.setText("-");
        editText.setHint("");
        editText.setTextColor(context.getResources().getColor(R.color.color_black));
    }

    public void showRight(boolean show) {
        rightImage.setVisibility(show ? VISIBLE : GONE);
    }

    public void showDivide(boolean show) {
        if (show) {
            setBackgroundResource(divideRes);
        } else if (backgroundResourceId != 0) {
            setBackgroundResource(backgroundResourceId);
        } else {
            setBackgroundColor(Color.WHITE);
        }
    }

    public void setRightImage(int resId) {
        rightImage.setImageResource(resId);
    }

    public void setLeftText(String text) {
        textView.setText(text);
    }

    public void setLeftBold(boolean bold) {
        textView.setTypeface(Typeface.defaultFromStyle(bold ? Typeface.BOLD : Typeface.NORMAL));
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            editText.setText(text);
        }
    }

    public void clearText() {
        editText.setText("");
    }

    public EditText getEditText() {
        return editText;
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setNeed (boolean need) {
        if (need) {
            this.need.setVisibility(VISIBLE);
        } else {
            this.need.setVisibility(INVISIBLE);
        }
    }

    public void setTag(int key, Objects objects) {
        editText.setTag(key, objects);
    }

    public Object getTag() {
        return editText.getTag(R.id.tg_field_value);
    }


    public void setBold(boolean bold) {
        editText.getPaint().setFakeBoldText(bold);
    }

    public void hideRight() {
        editText.setCompoundDrawables(null,null,null,null);
        editText.setHint("");
    }

    public void addTextChangedListener(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        setSelected(check);
    }
}
