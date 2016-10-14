package org.under_side.wuziqi;

import org.under_side.wuziqi.ui.WuziqiView;
import org.under_side.wuziqi.ui.WuziqiView.onWuziqiChangedListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mWhoTurnText;

	private WuziqiView mWuziqiView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		mWuziqiView = (WuziqiView) findViewById(R.id.id_wuziqi);
		// mWuziqiView.setBackgroundColor(0x44ff0000);
		mWhoTurnText = (TextView) findViewById(R.id.text_turn);
		mWuziqiView.setOnWuziqiChangedListener(new onWuziqiChangedListener() {

			@Override
			public void isWhoWinner() {

			}

			@Override
			public void onPiecesChanged(boolean isWhitePieces) {
				// Toast.makeText(MainActivity.this, "call", 0).show();
				String text = (isWhitePieces ? "°×Æå" : "ºÚÆå");
				mWhoTurnText.setText(text);
			}

		});
	}
}
