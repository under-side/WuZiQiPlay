package org.under_side.wuziqi;

import org.under_side.wuziqi.ui.WuziqiView;
import org.under_side.wuziqi.ui.WuziqiView.onWuziqiChangedListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView mWhoTurnText;

	private WuziqiView mWuziqiView;

	private TextView mWinnerText;

	private LinearLayout mTurnLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
	}

	// 进行activity中的初始化操作
	private void init() {

		mWuziqiView = (WuziqiView) findViewById(R.id.id_wuziqi);

		mWuziqiView.setSaveEnabled(true);

		// mWuziqiView.setBackgroundColor(0x44ff0000);

		mWhoTurnText = (TextView) findViewById(R.id.text_turn);

		mWinnerText = (TextView) findViewById(R.id.winner_text);

		mTurnLayout = (LinearLayout) findViewById(R.id.turn_layout);

		mWuziqiView.setOnWuziqiChangedListener(new onWuziqiChangedListener() {

			@Override
			public void isWhoWinner(boolean isWhitePieces) {

				String winner = (isWhitePieces ? "白棋胜利" : "黑棋胜利");

				mTurnLayout.setVisibility(View.INVISIBLE);

				mWinnerText.setVisibility(View.VISIBLE);
				mWinnerText.setText(winner);

				Toast.makeText(MainActivity.this, winner, Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void isWhoTurns(boolean isWhitePieces) {
				// Toast.makeText(MainActivity.this, "call", 0).show();
				String text = (isWhitePieces ? "白棋" : "黑棋");
				mWhoTurnText.setText(text);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.back:
			mWuziqiView.getBackPieces();
			break;
		case R.id.fail:
			mWuziqiView.admitDefeat();
			break;
		case R.id.again:
			mTurnLayout.setVisibility(View.VISIBLE);
			mWinnerText.setVisibility(View.INVISIBLE);
			mWuziqiView.playAgain();
			break;
		}
		return true;
	}
}
