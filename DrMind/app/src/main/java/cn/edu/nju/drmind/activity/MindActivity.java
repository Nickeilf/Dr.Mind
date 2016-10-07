package cn.edu.nju.drmind.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import cn.edu.cn.R;
import cn.edu.nju.drmind.FAB.FloatingActionButton;
import cn.edu.nju.drmind.FAB.FloatingActionMenu;
import cn.edu.nju.drmind.FAB.SubActionButton;
import cn.edu.nju.drmind.swipemenulistview.SimpleActivity;
import cn.edu.nju.drmind.util.Constant;
import cn.edu.nju.drmind.view.DEditTextView;
import cn.edu.nju.drmind.view.DViewGroup;
import cn.edu.nju.drmind.voice.VoiceToWord;


public class MindActivity extends Activity {
    public static MindActivity a;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 设置总布局
        setContentView(R.layout.main);

        // 大小的初始化
        init();

        // 右侧FAB的初始化
        initRightButton();

        // 左侧DrawerLayout的初始化
        initDrawerListener();

        // 跳转时的过滤，需修改
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            String state = bundle.getString("state");
            if (state != null && (state.equals("open") || state.equals("save"))) {
                String name = bundle.getString("name");
                DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
                group.load(name);
            }
        }
    }

    //左边侧滑栏的监听
    private void initDrawerListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.left_navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mulu_item:
                        init_mulu_listener();
                        break;
                    case R.id.xinjian_item:
                        init_new_listener();
                        break;
                    case R.id.save_item:
                        init_save_lisetener();
                        break;
                    case R.id.daochu_item:
                        init_export_listener();
                        break;
                }
                return false;
            }
        });
    }

    //左侧Navigation的Item监听
    //目录项
    private void init_mulu_listener() {
        startActivity(new Intent(MindActivity.this, SimpleActivity.class));
    }

    //新建项
    private void init_new_listener() {
        startActivity(new Intent(MindActivity.this, MindActivity.class));
        Toast.makeText(getApplicationContext(), "新建图表成功", Toast.LENGTH_LONG).show();
    }

    //保存项
    private void init_save_lisetener() {
        final EditText editText = new EditText(MindActivity.this);
        DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
        if (group.isOpenSaved()) {
            editText.setText(group.getCurretFileName());
        }

        new AlertDialog.Builder(MindActivity.this).setTitle("请输入保存的图表名")
                .setIcon(android.R.drawable.ic_dialog_info).setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
                        if (name.equals("")) {
                            Toast.makeText(getApplicationContext(), "图表名不能为空哟！" + name, Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }
                        if (group.existPaint(name)) {
                            Toast.makeText(getApplicationContext(), "图表 " + name + "已存在！", Toast.LENGTH_LONG)
                                    .show();
                            return;
                        } else {
                            System.out.println("保存的图名： " + name);

                            group.save(name);

                            Intent intent = new Intent(MindActivity.this, MindActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("state", "save");
                            bundle.putString("name", name);
                            intent.putExtras(bundle);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), "图表" + name + "保存成功~", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    //导出项
    private void init_export_listener() {
        final EditText editText = new EditText(MindActivity.this);

        new AlertDialog.Builder(MindActivity.this).setTitle("请输入导出的图片名")
                .setIcon(android.R.drawable.ic_dialog_info).setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
                        if (name.equals("")) {
                            Toast.makeText(getApplicationContext(), "图表名不能为空哟！" + name, Toast.LENGTH_LONG)
                                    .show();
                            return;
                        } else {
                            System.out.println("导出的图片名： " + name);


                            try {
                                if (group.exportPicture(name)) {
                                    System.out.println("daochu success !");
                                } else {
                                    Toast.makeText(getApplicationContext(), "图片 " + name + "已存在！", Toast.LENGTH_LONG)
                                            .show();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), "图片" + name + "导出成功~", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }


    // 右侧的FAB按钮
    private void initRightButton() {
        // 中心图标
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(this.getResources().getDrawable(R.drawable.fab));
        FloatingActionButton actionButton = new FloatingActionButton.Builder(
                this).setContentView(icon).build();

        // 分散式图标
        // 语音功能
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageDrawable(this.getResources().getDrawable(
                R.drawable.voice));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // 别人的讯飞账户，我的待审核
                VoiceToWord voice = new VoiceToWord(MindActivity.this,
                        "534e3fe2", (DViewGroup) findViewById(R.id.viewgroup));
                voice.GetWordFromVoice();
            }
        });

        // 删除节点
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable(this.getResources().getDrawable(
                R.drawable.delete));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                new AlertDialog.Builder(MindActivity.this)
                        .setTitle("您选择删除：")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("全部删除",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
                                        group.deleteNode();
                                    }
                                })
                        .setNeutralButton("仅删除此节点",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
                                        group.deleteMerge();
                                    }
                                }).setNegativeButton("取消", null).show();
            }
        });

        // 增加节点
        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageDrawable(this.getResources().getDrawable(
                R.drawable.plus));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
                group.insertNode();
            }
        });

        // 收缩节点
        ImageView itemIcon4 = new ImageView(this);
        itemIcon4.setImageDrawable(this.getResources().getDrawable(
                R.drawable.minus));
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();
        button4.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
                group.hideOrShow();
            }
        });


        // 整合在一起
        FloatingActionMenu actionMenu =
                new FloatingActionMenu.Builder(this).addSubActionView(button1)
                        .addSubActionView(button2)
                        .addSubActionView(button3)
                        .addSubActionView(button4)
                        .attachTo(actionButton).build();

    }

    /**
     * 初始化：设定viewGroup大小为3*3倍屏幕大小
     */
    private void init() {
        WindowManager wm = this.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // 设定常量
        Constant.setScreenHeight(height);
        Constant.setScreenWidth(width);
        DViewGroup dView = (DViewGroup) findViewById(R.id.viewgroup);
        LayoutParams lay = (LayoutParams) findViewById(
                R.id.viewgroup).getLayoutParams();

        lay.height = 3 * height;
        lay.width = 3 * width;

        dView.setScreenWidth(width);
        dView.setScreenHeight(height);
        dView.bringToFront();
        findViewById(R.id.viewgroup).setX(0);
        findViewById(R.id.viewgroup).setY(-height);
        findViewById(R.id.viewgroup).setLayoutParams(lay);

    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    // 获取点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof DEditTextView)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}