package me.yeojoy.lifecyclecheckapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import my.lib.MyLog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends Activity {
	
	private static final String BUNDLE_KEY_COUNT = "count_";

	private TextView mTvCounts;
	private TextView mTvActivityStatus;
	
	private boolean isBackground = false;
	
	private int CALL_COUNT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTvCounts = (TextView) findViewById(R.id.tv_counts);
		mTvActivityStatus = (TextView) findViewById(R.id.tv_status);
		
		traceLifeCycle("S onCreate");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		traceLifeCycle("S onStart");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		traceLifeCycle("S onRestart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		traceLifeCycle("S onResume");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			MyLog.d("yeojoy", "onRestoreInstanceState is called");
			CALL_COUNT = savedInstanceState.getInt(BUNDLE_KEY_COUNT);
		}
		traceLifeCycle("S onRestoreInstanceState");
	}
	
	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		if (isBackground) {
			traceLifeCycle("S onUserInteraction");
			isBackground = false;
		}
	}
	
	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		traceLifeCycle("S onUserLeaveHint");
		isBackground = true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		traceLifeCycle("S onPause");
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		traceLifeCycle("S onNewIntent");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_KEY_COUNT, CALL_COUNT);
		
		traceLifeCycle("S onSaveInstanceState");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		traceLifeCycle("S onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		traceLifeCycle("S onDestroy");
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		traceLifeCycle("S onAttachedToWindow");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		
		switch (item.getItemId()) {
			case R.id.action_call_myself:
				intent = new Intent(SecondActivity.this, SecondActivity.class);
				startActivity(intent);
				return true;
				
			case R.id.action_call_next:
				intent = new Intent(this, ThirdActivity.class);
				startActivity(intent);
				return true;
				
			case R.id.action_call_next_with_flag_clear:
				intent = new Intent(this, ThirdActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
				
			case R.id.action_call_next_with_flag_single:
				intent = new Intent(this, ThirdActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				return true;
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Life Cycle 추적
	 * message를 남기면 내용을 보여주고
	 * 로그로 남긴다.
	 * @param message
	 */
	private void traceLifeCycle(String message) {
		long milliseconds = System.currentTimeMillis();
		Date date = new Date(milliseconds);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(date)).append("  ");
		
		if (CALL_COUNT < 10) {
			sb.append("00").append(CALL_COUNT);
		} else if (CALL_COUNT < 100) {
			sb.append("0").append(CALL_COUNT);
		} else {
			sb.append(CALL_COUNT);
		}
		sb.append(". ").append(message).append("\n");
		
		MyLog.i(sb.toString());
		mTvCounts.setText("" + (CALL_COUNT++));
		mTvActivityStatus.append(sb);
	}
}
