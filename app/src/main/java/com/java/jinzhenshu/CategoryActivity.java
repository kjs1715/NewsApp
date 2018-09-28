package com.java.jinzhenshu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.java.jinzhenshu.untils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.checkbox_one)
    CheckBox checkbox_one;
    @BindView(R.id.checkbox_two)
    CheckBox checkbox_two;
    @BindView(R.id.checkbox_three)
    CheckBox checkbox_three;
    @BindView(R.id.checkbox_four)
    CheckBox checkbox_four;
    @BindView(R.id.checkbox_five)
    CheckBox checkbox_five;
    @BindView(R.id.checkbox_six)
    CheckBox checkbox_feven;
    @BindView(R.id.checkbox_feven)
    CheckBox checkbox_six;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);


        checkbox_one.setChecked(getShow("checkbox_one"));

        checkbox_two.setChecked(getShow("checkbox_two"));
        checkbox_three.setChecked(getShow("checkbox_three"));
        checkbox_four.setChecked(getShow("checkbox_four"));
        checkbox_five.setChecked(getShow("checkbox_five"));

        checkbox_feven.setChecked(getShow("checkbox_feven"));
        checkbox_six.setChecked(getShow("checkbox_six"));

        checkbox_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.getInstance().set("checkbox_one",b);
            }
        });
        checkbox_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.getInstance().set("checkbox_two",b);
            }
        });
        checkbox_three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.getInstance().set("checkbox_three",b);
            }
        });
        checkbox_four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.getInstance().set("checkbox_four",b);
            }
        });
        checkbox_five.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.getInstance().set("checkbox_five",b);
            }
        });
        checkbox_feven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.getInstance().set("checkbox_feven",b);
            }
        });
        checkbox_six.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.getInstance().set("checkbox_six",b);
            }
        });

    }



    private Boolean getShow(String check){
        return (Boolean) SPUtils.getInstance().get(check,true);
    }
}
