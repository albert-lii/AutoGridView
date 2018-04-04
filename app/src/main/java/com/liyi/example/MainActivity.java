package com.liyi.example;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liyi.grid.AutoGridView;
import com.liyi.grid.GridConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_nine, btn_normal, btn_data1, btn_data2, btn_data3, btn_data4, btn_clearPic, btn_addPic;
    private TextView tv_mode;
    private AutoGridView gridLayout;

    private MainAdapter mAdapter;
    private ArrayList<Integer> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        addListeners();
    }

    private void initUI() {
        btn_nine = (Button) findViewById(R.id.btn_nine);
        btn_normal = (Button) findViewById(R.id.btn_normal);
        btn_data1 = (Button) findViewById(R.id.btn_data1);
        btn_data2 = (Button) findViewById(R.id.btn_data2);
        btn_data3 = (Button) findViewById(R.id.btn_data3);
        btn_data4 = (Button) findViewById(R.id.btn_data4);
        btn_clearPic = (Button) findViewById(R.id.btn_clear_pic);
        btn_addPic = (Button) findViewById(R.id.btn_add_pic);
        tv_mode = (TextView) findViewById(R.id.tv_mode);
        gridLayout = (AutoGridView) findViewById(R.id.gridLayout);

        mList = new ArrayList<>();
        mAdapter = new MainAdapter();
        gridLayout.setAdapter(mAdapter);
        update2();
    }

    private void addListeners() {
        btn_nine.setOnClickListener(this);
        btn_normal.setOnClickListener(this);
        btn_data1.setOnClickListener(this);
        btn_data2.setOnClickListener(this);
        btn_data3.setOnClickListener(this);
        btn_data4.setOnClickListener(this);
        btn_clearPic.setOnClickListener(this);
        btn_addPic.setOnClickListener(this);
        gridLayout.setOnItemClickListener(new AutoGridView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(MainActivity.this, "我是" + position + "号", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nine:
                gridLayout.setGridMode(GridConfig.GRID_NINE);
                tv_mode.setText("当前是九宫格模式");
                break;
            case R.id.btn_normal:
                gridLayout.setGridMode(GridConfig.GRID_NORMAL);
                tv_mode.setText("当前是普通模式");
                break;
            case R.id.btn_data1:
                update1();
                break;
            case R.id.btn_data2:
                update2();
                break;
            case R.id.btn_data3:
                update3();
                break;
            case R.id.btn_data4:
                update4();
                break;
            case R.id.btn_clear_pic:
                clearPic();
                break;
            case R.id.btn_add_pic:
                addPic();
                break;
        }
    }

    private void update1() {
        mList.clear();
        mList.add(R.drawable.landiao);
        mAdapter.setData(mList, gridLayout.getGridMode() == GridConfig.GRID_NINE ? true : false);
        gridLayout.notifyChanged();
    }

    private void update2() {
        mList.clear();
        mList.add(R.drawable.landiao);
        mList.add(R.drawable.landiao);
        mList.add(R.drawable.landiao);
        mList.add(R.drawable.landiao);
        mAdapter.setData(mList, gridLayout.getGridMode() == GridConfig.GRID_NINE ? true : false);
        gridLayout.notifyChanged();
    }

    private void update3() {
        mList.clear();
        mList.add(R.drawable.landiao);
        mList.add(R.drawable.landiao);
        mList.add(R.drawable.landiao);
        mList.add(R.drawable.landiao);
        mList.add(R.drawable.landiao);
        mAdapter.setData(mList, gridLayout.getGridMode() == GridConfig.GRID_NINE ? true : false);
        gridLayout.notifyChanged();
    }

    private void update4() {
        mList.clear();
        for (int i = 0; i < 9; i++) {
            mList.add(R.drawable.landiao);
        }
        mAdapter.setData(mList, gridLayout.getGridMode() == GridConfig.GRID_NINE ? true : false);
        gridLayout.notifyChanged();
    }

    private void clearPic() {
        mList.clear();
        mAdapter.setData(mList, false);
        gridLayout.notifyChanged();
    }

    private void addPic() {
        mList.add(R.drawable.landiao);
        mAdapter.setData(mList, false);
        if (gridLayout.getGridMode() == GridConfig.GRID_NINE) {
            gridLayout.setGridMode(GridConfig.GRID_NORMAL);
        }
        gridLayout.notifyChanged();
        tv_mode.setText("当前是普通模式");
    }
}
