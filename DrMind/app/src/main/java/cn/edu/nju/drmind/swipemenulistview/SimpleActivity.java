 
package cn.edu.nju.drmind.swipemenulistview;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.cn.R;
import cn.edu.nju.drmind.activity.MindActivity;
import cn.edu.nju.drmind.bl.paintblImpl;
import cn.edu.nju.drmind.data.paintDao;
import cn.edu.nju.drmind.impl.paintDataServiceImpl;
import cn.edu.nju.drmind.service.paintService;
import cn.edu.nju.drmind.service.paintDataService;

public class SimpleActivity extends Activity {
 
    private List<String> nameOfFile;
    private AppAdapter mAdapter;
    private SwipeMenuListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_list);
 
        nameOfFile=new ArrayList<String>();
        initList();

        mListView = (SwipeMenuListView) findViewById(R.id.listView);

        mAdapter = new AppAdapter();
        mListView.setAdapter(mAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String nameItem=nameOfFile.get(position);
                switch (index) {
                    case 0:
                    	Bundle bundle=new Bundle();
                    	bundle.putString("state", "open");
                    	bundle.putString("name", nameItem);
                    	Intent intent=new Intent(SimpleActivity.this,MindActivity.class);
                    	intent.putExtras(bundle);
                    	startActivity(intent);
                    	 
                        break;
                    case 1:
                    	//数据库删除
                    	paintDataService service=new paintDataServiceImpl();
                    	paintDao dao= paintDao.getDao(SimpleActivity.this);
                    	service.deleteData(nameItem, dao);
                    	//界面删除
                        nameOfFile.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
 

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
//                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
    
    //图表列表内容
    private void initList(){
    	
    	paintService service=new paintblImpl();
//    	paintDao dao=new paintDao(this);
		paintDao dao=paintDao.getDao(this);
    	ArrayList<String> list=service.getAllPaintName(dao);
//    	list.add(" 文件目录");
    	for(int i=0;i<list.size();i++){
    		nameOfFile.add(list.get(i));
    	}
    }

 

    class AppAdapter extends BaseSwipListAdapter {

        public int getCount() {
        	return nameOfFile.size();
        }
 
        public String getItem(int position) {
        	return nameOfFile.get(position);
        }
 
        public long getItemId(int position) {
            return position;
        }
 
        @SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.iv_icon.setImageDrawable(SimpleActivity.this.getResources().getDrawable(R.drawable.think_white));
            holder.tv_name.setTextColor(Color.WHITE);
            String nameItem =getItem(position);
            holder.tv_name.setText(nameItem);

            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipEnableByPosition(int position) {
//            if(position % 2 == 0){
//                return false;
//            }
            return true;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_left) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }
        if (id == R.id.action_right) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
