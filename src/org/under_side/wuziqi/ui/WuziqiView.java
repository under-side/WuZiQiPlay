package org.under_side.wuziqi.ui;

import java.util.ArrayList;

import org.under_side.wuziqi.R;
import org.under_side.wuziqi.utils.CheckGameUtils;
import org.under_side.wuziqi.utils.DrawViewUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class WuziqiView extends View {

	// 定义画笔变量
	private Paint myPaint;

	// 自定义五子棋为12行
	private static int MAX_LINE = 12;

	// 自定义view的宽度
	private int mWidth;

	// 自定义view的单元格的高度
	private float mCellHeight;

	// 声明两个变量用于存储手势点击黑白棋子的位置坐标
	private ArrayList<Point> mWhitePiecesArray;
	private ArrayList<Point> mBlackPiecesArray;

	// 用该变量用于标志该谁下棋
	private boolean isWhitePiecesTurns = true;

	private boolean isGameOver = false;

	// 棋子的bitmap实例对象
	private Bitmap mWhitePiecesBitmap;
	private Bitmap mBlacePiecesBitmap;

	// 棋子缩放的比例对象
	private float mRatioBitmapOfCellHeight = 3 * 1.0f / 4;

	public WuziqiView(Context context) {
		this(context, null);
	}

	public WuziqiView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WuziqiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	// 初始化操作
	private void init() {
		myPaint = new Paint();
		myPaint.setColor(Color.GRAY);
		myPaint.setAntiAlias(true);
		myPaint.setStyle(Style.STROKE);

		mWhitePiecesArray = new ArrayList<Point>();
		mBlackPiecesArray = new ArrayList<Point>();

		mWhitePiecesBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.stone_w);
		mBlacePiecesBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.stone_b);

	}

	private onWuziqiChangedListener listener;

	private boolean isRegretPieces;

	public void setOnWuziqiChangedListener(onWuziqiChangedListener listener) {
		this.listener = listener;
	}

	public interface onWuziqiChangedListener {

		/**
		 * 该方法是当isGameOver为true时调用，用于判断当前棋局，谁是胜利者。
		 * 
		 * @param isWhitePieces
		 *            为true时，白棋胜利；为false时，黑棋胜利。
		 */
		public void isWhoWinner(boolean isWhitePieces);

		/**
		 * 该方法是用于判断该谁下棋了，起到一个提醒了作用
		 * 
		 * @param isWhitePieces
		 *            为true时该白棋下，false则为黑棋
		 */
		public void isWhoTurns(boolean isWhitePieces);
	}

	/*
	 * 重写onMeasure方法去测量自定义的view的宽高。
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
		int measureWidthModel = MeasureSpec.getMode(widthMeasureSpec);

		int measureHeight = MeasureSpec.getSize(widthMeasureSpec);
		int measureHeightModel = MeasureSpec.getMode(widthMeasureSpec);

		int min = Math.min(measureHeight, measureWidth);

		// 加一个判断，防止在自定义view嵌套在其他view中无法获取高度和宽度
		if (measureHeightModel == MeasureSpec.UNSPECIFIED) {
			measureHeight = measureWidth;
		} else if (measureWidthModel == MeasureSpec.UNSPECIFIED) {
			measureWidth = measureHeight;
		}
		// 该方法必须在onMeasure方法中调用，否则会出现错误
		setMeasuredDimension(min, min);
	}

	/*
	 * 重写onDraw方法，进行绘制棋盘和绘制棋子操作
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		// 绘制棋盘
		DrawViewUtils.onDrawPanle(canvas, myPaint, mCellHeight, mWidth);

		// 绘制白棋
		DrawViewUtils.onDrawBlackPieces(canvas, mWhitePiecesArray, myPaint,
				mWhitePiecesBitmap, mCellHeight);

		// 绘制黑棋
		DrawViewUtils.onDrawBlackPieces(canvas, mBlackPiecesArray, myPaint,
				mBlacePiecesBitmap, mCellHeight);
		
		if(listener!=null)
		{
			listener.isWhoTurns(isWhitePiecesTurns);
		}
	}

	/*
	 * 重写onTouchEvent方法，在这里处理手势操作，去绘制手势点击所生成的棋子。
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// 在手势按下时，判断是否结束。如果结束，则不对该手势感兴趣了。即，不能再下棋了
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (isGameOver)
				return false;
		}
		// 在手势抬起时，去处理点击棋子生成绘制
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			Point point = getAvailablePoint(x, y);

			/*
			 * 判断，如果点击的地方已经有棋子了，就不会再在该位置上进行添加棋子的操作
			 * 返回false，表示对此次的手势操作不感兴趣，不做任何动作，后面的代码就不会处理了。
			 * 此次点击事件也不会再添加到ArrayList数组里面。
			 */
			if (mWhitePiecesArray.contains(point)) {
				return false;
			} else if (mBlackPiecesArray.contains(point)) {
				return false;
			}

			// 根据isWhitePieces来轮流下棋
			if (isWhitePiecesTurns) {
				mWhitePiecesArray.add(point);
				// 对该变量重新赋值
				isRegretPieces = false;
			} else {
				isRegretPieces = false;
				mBlackPiecesArray.add(point);
			}

			// 调用invalidate方法去重新绘制界面，添加手势操作生成的棋子
			invalidate();
			/*
			 * 在完成落子绘制成功后，去调用check方法，对其进行检查操作。 根据此次落子的棋子，判断如果此次是白子落子，则只对白子进行检查；
			 * 否则，只对黑子进行检查。
			 */
			if (isWhitePiecesTurns) {
				isGameOver = CheckGameUtils.checkGameIsOver(mWhitePiecesArray);
				if (isGameOver) {
					if (listener != null) {
						listener.isWhoWinner(isWhitePiecesTurns);
					}
				}
			} else {
				isGameOver = CheckGameUtils.checkGameIsOver(mBlackPiecesArray);
				if (isGameOver) {
					if (listener != null) {
						listener.isWhoWinner(isWhitePiecesTurns);
					}
				}
			}
			// 对该值取反，是黑白棋轮流下棋
			isWhitePiecesTurns = !isWhitePiecesTurns;
			if (listener != null) {
				listener.isWhoTurns(isWhitePiecesTurns);
			}
		}
		return true;
	}

	// 获取正确的手势坐标
	private Point getAvailablePoint(int x, int y) {
		return new Point((int) (x / mCellHeight), (int) (y / mCellHeight));

	}

	// 在onSizeChanged方法中去获取关于size的问题
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mCellHeight = mWidth * 1.0f / MAX_LINE;

		// 将bitmap对象的大小缩放到指定的宽高
		int bitmapHeight = (int) (mRatioBitmapOfCellHeight * mCellHeight);

		mWhitePiecesBitmap = Bitmap.createScaledBitmap(mWhitePiecesBitmap,
				bitmapHeight, bitmapHeight, false);
		mBlacePiecesBitmap = Bitmap.createScaledBitmap(mBlacePiecesBitmap,
				bitmapHeight, bitmapHeight, false);
	}

	/*
	 * 对该view中的数据进行保存，防止view重建时，关键数据丢失。 该写法为标准的view保存与恢复的写法，不同的是根据项目的需求保存不同的数据。
	 * 另外重要的一点是：如果自定义view中想要保存与恢复view时，需要给该view一个id，
	 * 用此id唯一的标识该view，使activity来调用该view去重建。
	 */

	// 定义保存数据的键值对
	private static final String INSTANCE = "instance";
	private static final String INSTANCE_IS_GAME_OVER = "isGameOver";
	private static final String INSTANCE_WHITE_PIECES_ARRAY = "WhitePiecesArray";
	private static final String INSTANCE_BLACK_PIECES_ARRAY = "BlackPiecesArray";

	@Override
	protected Parcelable onSaveInstanceState() {
		// 将该view中需要保存的数据保存到bundle中
		Bundle bundle = new Bundle();

		bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
		bundle.putBoolean(INSTANCE_IS_GAME_OVER, isGameOver);
		bundle.putParcelableArrayList(INSTANCE_WHITE_PIECES_ARRAY,
				mWhitePiecesArray);
		bundle.putParcelableArrayList(INSTANCE_BLACK_PIECES_ARRAY,
				mBlackPiecesArray);
		return bundle;
	}

	/*
	 * 根据保存的数据，activity在回复view时，调用该方法，根据方法中的数据对view进行重建
	 * 
	 * @see android.view.View#onRestoreInstanceState(android.os.Parcelable)
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// 先判断，获取的state是否为自定义保存的对象，是的话，就获取保存的数据，进行view的重建赋值
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			// 从bundle中获取保存的数据，赋值初始化给该view的全局变量
			isGameOver = bundle.getBoolean(INSTANCE_IS_GAME_OVER);
			mWhitePiecesArray = bundle
					.getParcelableArrayList(INSTANCE_WHITE_PIECES_ARRAY);
			mBlackPiecesArray = bundle
					.getParcelableArrayList(INSTANCE_BLACK_PIECES_ARRAY);

			// 获取系统默认保存的内容，并调用父类的onRestoreInstanceState方法去实现系统默认的操作
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
		} else {
			super.onRestoreInstanceState(state);
		}
	}

	/*
	 * 该方法用于悔棋的操作处理，使棋盘状态恢复到上一步的状态。 但是，规定一次只能悔棋一步。
	 */
	public void getBackPieces() {
		// 如果当前游戏结束，则不允许悔棋
		if (!isGameOver) {
			// 利用全局变量控制，只能悔棋一次，不能多次连续悔棋
			if (!isRegretPieces) {
				// 当列表中没有棋子，则无法悔棋
				if (isWhitePiecesTurns && mBlackPiecesArray.size() > 0) {
					isRegretPieces = true;
					mBlackPiecesArray.remove(mBlackPiecesArray.size() - 1);
				} else if (!isWhitePiecesTurns && mWhitePiecesArray.size() > 0) {
					isRegretPieces = true;
					mWhitePiecesArray.remove(mWhitePiecesArray.size() - 1);
				}
				// 只有当悔棋操作执行了，该操作才能执行
				if (isRegretPieces) {
					// 将下一次下棋的变量取反，回到上一个下棋者
					isWhitePiecesTurns = !isWhitePiecesTurns;
					// 在调用回调接口方法，重置
					if (listener != null) {
						listener.isWhoTurns(isWhitePiecesTurns);
					}
					invalidate();
				}
			} else {
				Toast.makeText(getContext(), "just once regret chess chance.",
						Toast.LENGTH_SHORT).show();
			}

		}

	}

	/*
	 * 该方法是用于主动认输的操作处理。 如果isWhitePieces为false，则该黑棋下，则此次白棋胜利，否则，反之。
	 */
	public void admitDefeat() {
		if (!isGameOver) {
			isGameOver = true;
			if (listener != null) {
				listener.isWhoWinner(!isWhitePiecesTurns);
			}
		}
	}

	/*
	 * 该方法是用于重置游戏变量，重新开始一场游戏。 并将此次由黑子先手。
	 */
	public void playAgain() {
		mWhitePiecesArray.clear();
		mBlackPiecesArray.clear();
		isGameOver = false;
		isWhitePiecesTurns = true;

		isRegretPieces = false;
		invalidate();
	}
}
