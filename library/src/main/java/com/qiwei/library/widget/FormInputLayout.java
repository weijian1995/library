package com.qiwei.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
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


/**
 * Created by shuan on 2018/5/9.
 * 输入框
 */

public class FormInputLayout extends LinearLayout {

    private Context context;
    private TextView need;
    private TextView textView,unitTv;
    private EditText editText;
    private boolean showDivide;
    private boolean show;
    private int divideRes;
    private ImageView rightImage;

    public FormInputLayout(Context context) {
        super(context);
    }

    public FormInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setOrientation(LinearLayout.HORIZONTAL);
        int padding12 = DensityUtils.dp2px(context,12);
        int padding15 = DensityUtils.dp2px(context,15);
        setPadding(0,padding15,0,padding15);
        setGravity(Gravity.CENTER_VERTICAL);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormInputLayout);
        String text = a.getString(R.styleable.FormInputLayout_text);
        String hint = a.getString(R.styleable.FormInputLayout_hint);
        int input_type = a.getInteger(R.styleable.FormInputLayout_input_type, 0x00000001);
        int maxLength = a.getInteger(R.styleable.FormInputLayout_maxLength,0);
        boolean isNeed = a.getBoolean(R.styleable.FormInputLayout_isNeed,false);
        boolean canInput = a.getBoolean(R.styleable.FormInputLayout_canInput,true);
        boolean showUnit = a.getBoolean(R.styleable.FormInputLayout_showUnit, false);
        boolean rightBold = a.getBoolean(R.styleable.FormInputLayout_right_bold, false);
        int rightImg = a.getResourceId(R.styleable.FormInputLayout_right_img, 0);
        showDivide = a.getBoolean(R.styleable.FormInputLayout_show_divide, false);
        divideRes = a.getResourceId(R.styleable.FormInputLayout_divide_res, R.drawable.shape_divide_f2);

        this.show = showDivide;
        String unitText = a.getString(R.styleable.FormInputLayout_unitText);
        a.recycle();

        if (showDivide) {
            setBackgroundResource(divideRes);
        } else {
            setBackgroundColor(Color.TRANSPARENT);
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
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        textView.setText(text);

        editText = new EditText(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
        params.setMargins(DensityUtils.dp2px(context,20),0,0,0);
        editText.setLayoutParams(params);
        editText.setBackgroundResource(R.color.transparent);
        int padding3 = DensityUtils.dp2px(context,3);
        editText.setPadding(0,padding3,0,padding3);
        editText.setTextColor(context.getResources().getColor(R.color.color_black));
        editText.setHintTextColor(Color.parseColor("#999999"));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        if (rightBold) {
            editText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        editText.setSingleLine();
        editText.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        editText.setHint(hint);
        if (maxLength != 0) {
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
        }
        editText.setInputType(input_type);
        if (!canInput) {
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        }

        rightImage = new ImageView(context);
        LayoutParams rightImgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//        rightImgParams.setMargins(0, 0, 0, 0);
        rightImage.setLayoutParams(rightImgParams);
        rightImage.setVisibility(rightImg != 0 ? VISIBLE : GONE);
        rightImage.setImageResource(rightImg);

        unitTv = new TextView(context);
        LayoutParams unitParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        unitParams.setMargins(DensityUtils.dp2px(context, 2), 0, 0, 0);
        unitTv.setLayoutParams(unitParams);
        unitTv.setTextColor(context.getResources().getColor(R.color.color_black));
        unitTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        unitTv.setText(unitText);
        unitTv.setVisibility(showUnit ? VISIBLE : GONE);


        this.addView(textView);
        this.addView(need);
        this.addView(editText);
        this.addView(rightImage);
        this.addView(unitTv);
    }

    public void setNoClick() {
        setEnabled(false);
        editText.setText("-");
        editText.setTextColor(context.getResources().getColor(R.color.color_black));
        setCanInput(false);
        rightImage.setVisibility(GONE);
    }

    public void setCanInput(boolean canInput) {
        editText.setFocusable(canInput);
        editText.setFocusableInTouchMode(canInput);
        editText.setHint("");
    }

    public void showDivide(boolean show) {
        this.show = show;
        if (show) {
            setBackgroundResource(R.drawable.shape_divide_f2);
        } else {
            setBackgroundColor(Color.WHITE);
        }
    }

    public boolean isShow() {
        return show;
    }

    public void setHint(String str) {
        editText.setHint(str);
    }

    public void setHint(int resid) {
        editText.setHint(resid);
    }

    public void setEditText(String str) {
        editText.setText(str);
    }

    public void setText(int resid) {
        editText.setText(resid);
    }

    public void setText(String str) {
        if (!TextUtils.isEmpty(str)) {
            editText.setText(str);
        }
    }

    public void clearText() {
        editText.setText("");
    }

    public String getText() {
        return editText.getText().toString();
    }

    public EditText getEditView() {
        return editText;
    }

    public TextView getLeftTextView() {
        return textView;
    }

    public void setNeed (boolean need) {
        if (need) {
            this.need.setVisibility(VISIBLE);
        } else {
            this.need.setVisibility(INVISIBLE);
        }
    }

    public void setBold(boolean bold) {
        editText.getPaint().setFakeBoldText(bold);
    }

    public void addTextChangedListener(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }
}
