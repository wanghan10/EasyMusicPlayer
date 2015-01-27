package org.music.local;

import org.music.tools.MusicListAdapter;
//import org.music.tools.ScanSDCardReceiver;

import com.music.player.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 之前我们自定义了音乐列表适配器，我们就可以在Activity用ContentResolver的query方法查找/读出媒体信息了。
 * 以下是ContentResolver的查找方法: 
 * URI：指明要查询的数据库名称加上表的名称 
 * Projection：指定查询数据库表中的哪几列，返回的游标中将包括相应的信息。Null则返回所有信息。 
 * selection: 指定查询条件
 * selectionArgs：参数selection里有？这个符号是，这里可以以实际值代替这个问号。如果selection这个没有？的话，那么这个String数组可以为null
 * SortOrder：指定查询结果的排列顺序。 并显示在一张列表上 所以控件上ListView其实蛮重要的。
 *
 */
public class LocalMusicActivity extends Activity {
	private int[] _ids;// 保存音乐ID临时数组
	private String[] _artists;// 保存艺术家
	private String[] _titles;// 标题临时数组
	private ListView listview;// 列表对象
//	private ScanSDCardReceiver receiver = null;// 扫描SD卡的实例
	private static final int EXIT = Menu.FIRST;//重写菜单的常量
	private static final int ABOUT = Menu.FIRST + 1;
	/**
	 * 定义查找音乐信息数组，1.标题，2音乐时间,3.艺术家,4.音乐id，5.显示名字,6.数据。
	 */
	String[] media_info = new String[] { MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.localmusic);
		listview = (ListView) findViewById(R.id.music_list);// 找ListView的ID
		listview.setOnItemClickListener(new MusicListOnClickListener());// 创建一个ListView监听器对象
		ShowMp3List();// 显示音乐

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, EXIT, 0, "退出");
		menu.add(1, ABOUT, 1, "关于");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == EXIT) {
			//完全退出应用
			System.exit(0);
		} if(item.getItemId() == ABOUT){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("关于作者");
			builder.setIcon(R.drawable.ic_launcher);
			builder.setMessage("路家豪\n" +
					"2014年7月8日10:22:29\n" +
					"项目地址：http://www.github.com/xinruodingshui")
					.setPositiveButton("OK",null)
					.setNegativeButton("Cancle", null).show();

		}
		return true;
	}

	/**
	 * 显示音乐列表
	 */
	private void ShowMp3List() {
		Cursor cursor = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, media_info, null,
				null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		cursor.moveToFirst();// 将游标移动到初始位置
		_ids = new int[cursor.getCount()];// 返回INT的一个列
		_artists = new String[cursor.getCount()];// 返回String的一个列
		_titles = new String[cursor.getCount()];// 返回String的一个列
		for (int i = 0; i < cursor.getCount(); i++) {
			_ids[i] = cursor.getInt(3);
			_titles[i] = cursor.getString(0);
			_artists[i] = cursor.getString(2);
			cursor.moveToNext();// 将游标移到下一行
		}
		listview.setAdapter(new MusicListAdapter(this, cursor));// 用setAdapter装载数据
	}

	// 点击列表事件
	public class MusicListOnClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			playMusic(position);// 根据点击位置来播放音乐

		}

	}

	// 播放音乐方法
	public void playMusic(int position) {
		Intent intent = new Intent(LocalMusicActivity.this,
				PlayMusicActivity.class);
		intent.putExtra("_ids", _ids);
		intent.putExtra("_titles", _titles);
		intent.putExtra("_artists", _artists);
		intent.putExtra("position", position);
		startActivity(intent);
		finish();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if(receiver!=null)
//				unregisterReceiver(receiver);//注销掉接收器
           AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Say Good Bye!");
			builder.setIcon(R.drawable.ic_launcher);
			builder.setMessage("Thanks for your attention!")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									//完全退出应用
									System.exit(0);
								}
							}).setNegativeButton("Cancle", null).show();

		}
		return false;
	}

}