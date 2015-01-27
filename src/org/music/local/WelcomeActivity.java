package org.music.local;

import com.music.player.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
/**
 * 为了显示华丽的标签底部，我们将样式设置成RadioGroup模式。并且通过一系列设置,通过setCurrentTabByTag来切换不同的选项卡
 */
@SuppressWarnings("deprecation")
public class WelcomeActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟秒

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(WelcomeActivity.this,
						LocalMusicActivity.class);
				WelcomeActivity.this.startActivity(mainIntent);
				WelcomeActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGHT);

}
}
