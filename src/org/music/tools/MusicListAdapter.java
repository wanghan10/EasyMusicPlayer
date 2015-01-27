package org.music.tools;

import com.music.player.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 若要想自定义布局，那么定义一个适配器。 目的在于把控件存放到指定的位置。 我们这里用游标把它遍历出来，以及上下文的位置
 */
public class MusicListAdapter extends BaseAdapter {
	private Context mcontext;// 上下文
	private Cursor mcursor;// 游标

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param cursor
	 */
	public MusicListAdapter(Context context, Cursor cursor) {
		mcontext = context;
		mcursor = cursor;
	}

	public int getCount() {
		return mcursor.getCount();// 返回游标行数
	}

	
	public Object getItem(int position) {
		return position;
	}

	
	public long getItemId(int position) {
		return position;
	}

	// 获取视图
	
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mcontext).inflate(
				R.layout.musiclist_item, null);// 用LayoutInflater装载布局。
		mcursor.moveToPosition(position);// 将游标移动到某位置
		ImageView images = (ImageView) convertView.findViewById(R.id.listitem);// 通过convertView找ID。
		images.setImageResource(R.drawable.music);
		TextView music_title = (TextView) convertView
				.findViewById(R.id.musicname);
		music_title.setText(mcursor.getString(0));
		TextView music_singer = (TextView) convertView
				.findViewById(R.id.singer);
		music_singer.setText(mcursor.getString(2));
		TextView music_time = (TextView) convertView.findViewById(R.id.time);
		music_time.setText(toTime(mcursor.getInt(1)));
		return convertView;
	}

	/**
	 * 时间的转换
	 * 
	 * @param time
	 * @return
	 */
	public String toTime(int time) {

		time /= 1000;
		int minute = time / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}
}
