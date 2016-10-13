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
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class WuziqiView extends View {

	//定义画笔变量
	private Paint myPaint ;
	
	//自定义五子棋为12行
	private static int 	MAX_LINE=12;
	
	//自定义view的宽度
	private int mWidth;
	
	//自定义view的单元格的高度
	private float mCellHeight;
	
	//声明两个变量用于存储手势点击黑白棋子的位置坐标
	private ArrayList<Point> mWhitePiecesArray;
	private ArrayList<Point> mBlackPiecesArray;
	
	//用该变量用于标志该谁下棋
	private boolean isWhitePieces=true;
	
	private boolean isGameOver=false;
	
	//棋子的bitmap实例对象
	private Bitmap mWhitePiecesBitmap;
	private Bitmap mBlacePiecesBitmap;
	
	//棋子缩放的比例对象
	private float mRatioBitmapOfCellHeight=3*1.0f/4;
	
	public WuziqiView(Context context) {
		this(context,null);
	}

	public WuziqiView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public WuziqiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	//初始化操作
	private void init() {
		myPaint=new Paint();
		myPaint.setColor(Color.GRAY);
		myPaint.setAntiAlias(true);
		myPaint.setStyle(Style.STROKE);
		
		mWhitePiecesArray=new ArrayList<Point>();
		mBlackPiecesArray=new ArrayList<Point>();
		
		mWhitePiecesBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.stone_w);
		mBlacePiecesBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.stone_b);
	}
	
	/*
	 * 重写onMeasure方法去测量自定义的view的宽高。
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
		int measureWidthModel=MeasureSpec.getMode(widthMeasureSpec);
		
		int measureHeight=MeasureSpec.getSize(widthMeasureSpec);
		int measureHeightModel=MeasureSpec.getMode(widthMeasureSpec);
		
		int min=Math.min(measureHeight, measureWidth);
		
		//加一个判断，防止在自定义view嵌套在其他view中无法获取高度和宽度
		if(measureHeightModel==MeasureSpec.UNSPECIFIED)
		{
			measureHeight=measureWidth;
		}else if(measureWidthModel==MeasureSpec.UNSPECIFIED)
		{
			measureWidth=measureHeight;
		}
		//该方法必须在onMeasure方法中调用，否则会出现错误
		setMeasuredDimension(min, min);
	}
	/*
	 * 重写onDraw方法，进行绘制棋盘和绘制棋子操作
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		//绘制棋盘
		DrawViewUtils.onDrawPanle(canvas, myPaint, mCellHeight, mWidth);
		
		//绘制白棋
		DrawViewUtils.onDrawBlackPieces(canvas, mWhitePiecesArray, myPaint, mWhitePiecesBitmap, mCellHeight);
		
		//绘制黑棋
		DrawViewUtils.onDrawBlackPieces(canvas, mBlackPiecesArray, myPaint, mBlacePiecesBitmap, mCellHeight);
	}
	/*
	 * 重写onTouchEvent方法，在这里处理手势操作，去绘制手势点击所生成的棋子。
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		//在手势按下时，判断是否结束。如果结束，则不对该手势感兴趣了。即，不能再下棋了
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(isGameOver)
				return false;
		}
		//在手势抬起时，去处理点击棋子生成绘制
		else if(event.getAction()==MotionEvent.ACTION_UP)
		{
			int x=(int) event.getX();
			int y=(int) event.getY();
			
			Point point=getAvailablePoint(x,y);
			
			/*
			 * 判断，如果点击的地方已经有棋子了，就不会再在该位置上进行添加棋子的操作
			 * 返回false，表示对此次的手势操作不感兴趣，不做任何动作，后面的代码就不会处理了。
			 * 此次点击事件也不会再添加到ArrayList数组里面。
			 */
			if(mWhitePiecesArray.contains(point))
			{
				return false;
			}else if(mBlackPiecesArray.contains(point))
			{
				return false;
			}
			
			//根据isWhitePieces来轮流下棋
			if(isWhitePieces)
			{
			    mWhitePiecesArray.add(point);
			}else{
				mBlackPiecesArray.add(point);
			}
			
			//调用invalidate方法去重新绘制界面，添加手势操作生成的棋子
			invalidate();
			/*
			 * 在完成落子绘制成功后，去调用check方法，对其进行检查操作。
			 * 根据此次落子的棋子，判断如果此次是白子落子，则只对白子进行检查；
			 * 否则，只对黑子进行检查。
			 */
			if(isWhitePieces)
			{
				isGameOver=CheckGameUtils.checkGameIsOver(mWhitePiecesArray);
				if(isGameOver)
				{
					Toast.makeText(getContext(), "白棋胜利", Toast.LENGTH_SHORT).show();
				}
			}else{
				isGameOver=CheckGameUtils.checkGameIsOver(mBlackPiecesArray);
				if(isGameOver)
				{
					Toast.makeText(getContext(), "黑棋胜利", Toast.LENGTH_SHORT).show();
				}
			}
			//对该值取反，是黑白棋轮流下棋
			isWhitePieces=!isWhitePieces;
		}
		return true;
	}

	//获取正确的手势坐标
	private Point getAvailablePoint(int x, int y) {
		return new Point((int)(x/mCellHeight), (int)(y/mCellHeight));
		
	}

	//在onSizeChanged方法中去获取关于size的问题
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mCellHeight = mWidth*1.0f/MAX_LINE;
		
		//将bitmap对象的大小缩放到指定的宽高
		int bitmapHeight=(int) (mRatioBitmapOfCellHeight*mCellHeight);
		
		mWhitePiecesBitmap=Bitmap.createScaledBitmap(mWhitePiecesBitmap, bitmapHeight, bitmapHeight, false);
		mBlacePiecesBitmap=Bitmap.createScaledBitmap(mBlacePiecesBitmap, bitmapHeight, bitmapHeight, false);
	}
}
