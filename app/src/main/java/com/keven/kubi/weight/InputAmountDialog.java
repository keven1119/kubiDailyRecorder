package com.keven.kubi.weight;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.keven.kubi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by keven-liang on 2018/4/28.
 */

public class InputAmountDialog extends AlertDialog {


    @BindView(R.id.edittext_input_amount)
    EditText edittextInputAmount;

    public InputAmountDialog(@NonNull Context context) {
        super(context);
        initView();
    }

    public InputAmountDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();

    }

    public InputAmountDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();

    }

    private void initView() {
        setContentView(R.layout.input_amount_layout);
        ButterKnife.bind(this);
        edittextInputAmount.setInputType(EditorInfo.TYPE_NUMBER_FLAG_SIGNED);
    }


    @OnClick(R.id.edittext_input_amount)
    public void onViewClicked() {
    }

    public int getInputAmount(){
        return Integer.parseInt( edittextInputAmount.getText().toString());
    }


    public static class Builder extends AlertDialog.Builder{

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int themeResId) {
            super(context, themeResId);
        }


        @Override
        public AlertDialog create() {

            return super.create();
        }
    }

}
