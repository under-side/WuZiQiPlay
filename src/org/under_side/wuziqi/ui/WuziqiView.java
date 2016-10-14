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
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class WuziqiView extends View {

	//���廭�ʱ���
	private Paint myPaint ;
	
	//�Զ���������Ϊ12��
	private static int 	MAX_LINE=12;
	
	//�Զ���view�Ŀ��
	private int mWidth;
	
	//�Զ���view�ĵ�Ԫ��ĸ߶�
	private float mCellHeight;
	
	//���������������ڴ洢���Ƶ���ڰ����ӵ�λ������
	private ArrayList<Point> mWhitePiecesArray;
	private ArrayList<Point> mBlackPiecesArray;
	
	//�øñ������ڱ�־��˭����
	private boolean isWhitePieces=true;
	
	private boolean isGameOver=false;
	
	//���ӵ�bitmapʵ������
	private Bitmap mWhitePiecesBitmap;
	private Bitmap mBlacePiecesBitmap;
	
	//�������ŵı�������
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

	//��ʼ������
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
	
	private onWuziqiChangedListener listener;
	
	public void setOnWuziqiChangedListener(onWuziqiChangedListener listener)
	{
		this.listener=listener;
	}
	
	public interface onWuziqiChangedListener{
		public void isWhoWinner();
		public void onPiecesChanged(boolean isWhitePieces);
	}
	
	
	/*
	 * ��дonMeasure����ȥ�����Զ����view�Ŀ�ߡ�
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
		int measureWidthModel=MeasureSpec.getMode(widthMeasureSpec);
		
		int measureHeight=MeasureSpec.getSize(widthMeasureSpec);
		int measureHeightModel=MeasureSpec.getMode(widthMeasureSpec);
		
		int min=Math.min(measureHeight, measureWidth);
		
		//��һ���жϣ���ֹ���Զ���viewǶ��������view���޷���ȡ�߶ȺͿ��
		if(measureHeightModel==MeasureSpec.UNSPECIFIED)
		{
			measureHeight=measureWidth;
		}else if(measureWidthModel==MeasureSpec.UNSPECIFIED)
		{
			measureWidth=measureHeight;
		}
		//�÷���������onMeasure�����е��ã��������ִ���
		setMeasuredDimension(min, min);
	}
	/*
	 * ��дonDraw���������л������̺ͻ������Ӳ���
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		//��������
		DrawViewUtils.onDrawPanle(canvas, myPaint, mCellHeight, mWidth);
		
		//���ư���
		DrawViewUtils.onDrawBlackPieces(canvas, mWhitePiecesArray, myPaint, mWhitePiecesBitmap, mCellHeight);
		
		//���ƺ���
		DrawViewUtils.onDrawBlackPieces(canvas, mBlackPiecesArray, myPaint, mBlacePiecesBitmap, mCellHeight);
	}
	/*
	 * ��дonTouchEvent�����������ﴦ�����Ʋ�����ȥ�������Ƶ�������ɵ����ӡ�
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		//�����ư���ʱ���ж��Ƿ����������������򲻶Ը����Ƹ���Ȥ�ˡ�����������������
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(isGameOver)
				return false;
		}
		//������̧��ʱ��ȥ�������������ɻ���
		else if(event.getAction()==MotionEvent.ACTION_UP)
		{
			int x=(int) event.getX();
			int y=(int) event.getY();
			
			Point point=getAvailablePoint(x,y);
			
			/*
			 * �жϣ��������ĵط��Ѿ��������ˣ��Ͳ������ڸ�λ���Ͻ���������ӵĲ���
			 * ����false����ʾ�Դ˴ε����Ʋ���������Ȥ�������κζ���������Ĵ���Ͳ��ᴦ���ˡ�
			 * �˴ε���¼�Ҳ��������ӵ�ArrayList�������档
			 */
			if(mWhitePiecesArray.contains(point))
			{
				return false;
			}else if(mBlackPiecesArray.contains(point))
			{
				return false;
			}
			
			//����isWhitePieces����������
			if(isWhitePieces)
			{
			    mWhitePiecesArray.add(point);
			}else{
				mBlackPiecesArray.add(point);
			}
			
			//����invalidate����ȥ���»��ƽ��棬������Ʋ������ɵ�����
			invalidate();
			/*
			 * ��������ӻ��Ƴɹ���ȥ����check������������м�������
			 * ���ݴ˴����ӵ����ӣ��ж�����˴��ǰ������ӣ���ֻ�԰��ӽ��м�飻
			 * ����ֻ�Ժ��ӽ��м�顣
			 */
			if(isWhitePieces)
			{
				isGameOver=CheckGameUtils.checkGameIsOver(mWhitePiecesArray);
				if(isGameOver)
				{
					Toast.makeText(getContext(), "����ʤ��", Toast.LENGTH_SHORT).show();
				}
			}else{
				isGameOver=CheckGameUtils.checkGameIsOver(mBlackPiecesArray);
				if(isGameOver)
				{
					Toast.makeText(getContext(), "����ʤ��", Toast.LENGTH_SHORT).show();
				}
			}
			//�Ը�ֵȡ�����Ǻڰ�����������
			isWhitePieces=!isWhitePieces;
			if(listener!=null)
		    {
		    	listener.onPiecesChanged(isWhitePieces);
		    }
		}
		return true;
	}

	//��ȡ��ȷ����������
	private Point getAvailablePoint(int x, int y) {
		return new Point((int)(x/mCellHeight), (int)(y/mCellHeight));
		
	}

	//��onSizeChanged������ȥ��ȡ����size������
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mCellHeight = mWidth*1.0f/MAX_LINE;
		
		//��bitmap����Ĵ�С���ŵ�ָ���Ŀ��
		int bitmapHeight=(int) (mRatioBitmapOfCellHeight*mCellHeight);
		
		mWhitePiecesBitmap=Bitmap.createScaledBitmap(mWhitePiecesBitmap, bitmapHeight, bitmapHeight, false);
		mBlacePiecesBitmap=Bitmap.createScaledBitmap(mBlacePiecesBitmap, bitmapHeight, bitmapHeight, false);
	}
	
	/*
	 * �Ը�view�е����ݽ��б��棬��ֹview�ؽ�ʱ���ؼ����ݶ�ʧ��
	 * ��д��Ϊ��׼��view������ָ���д������ͬ���Ǹ�����Ŀ�����󱣴治ͬ�����ݡ�
	 * ������Ҫ��һ���ǣ�����Զ���view����Ҫ������ָ�viewʱ����Ҫ����viewһ��id��
	 * �ô�idΨһ�ı�ʶ��view��ʹactivity�����ø�viewȥ�ؽ���
	 */
	
	//���屣�����ݵļ�ֵ��
	private static final String INSTANCE="instance";
	private static final String INSTANCE_IS_GAME_OVER="isGameOver";
	private static final String INSTANCE_WHITE_PIECES_ARRAY="WhitePiecesArray";
	private static final String INSTANCE_BLACK_PIECES_ARRAY="BlackPiecesArray";
	
	@Override
	protected Parcelable onSaveInstanceState() {
		//����view����Ҫ��������ݱ��浽bundle��
		Bundle bundle=new Bundle();
		
		bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
		bundle.putBoolean(INSTANCE_IS_GAME_OVER, isGameOver);
		bundle.putParcelableArrayList(INSTANCE_WHITE_PIECES_ARRAY, mWhitePiecesArray);
		bundle.putParcelableArrayList(INSTANCE_BLACK_PIECES_ARRAY, mBlackPiecesArray);
		return bundle;
	}
	
	/*
	 * ���ݱ�������ݣ�activity�ڻظ�viewʱ�����ø÷��������ݷ����е����ݶ�view�����ؽ�
	 * @see android.view.View#onRestoreInstanceState(android.os.Parcelable)
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		//���жϣ���ȡ��state�Ƿ�Ϊ�Զ��屣��Ķ����ǵĻ����ͻ�ȡ��������ݣ�����view���ؽ���ֵ
		if(state instanceof Bundle)
		{
			Bundle bundle=(Bundle) state;
			//��bundle�л�ȡ��������ݣ���ֵ��ʼ������view��ȫ�ֱ���
			isGameOver=bundle.getBoolean(INSTANCE_IS_GAME_OVER);
			mWhitePiecesArray=bundle.getParcelableArrayList(INSTANCE_WHITE_PIECES_ARRAY);
			mBlackPiecesArray=bundle.getParcelableArrayList(INSTANCE_BLACK_PIECES_ARRAY);
		
			//��ȡϵͳĬ�ϱ�������ݣ������ø����onRestoreInstanceState����ȥʵ��ϵͳĬ�ϵĲ���
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
		}
		super.onRestoreInstanceState(state);
	}
}
