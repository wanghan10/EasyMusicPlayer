package org.music.local;

import com.music.player.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
/**
 * Ϊ����ʾ�����ı�ǩ�ײ������ǽ���ʽ���ó�RadioGroupģʽ������ͨ��һϵ������,ͨ��setCurrentTabByTag���л���ͬ��ѡ�
 */
@SuppressWarnings("deprecation")
public class WelcomeActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2000; // �ӳ���

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
