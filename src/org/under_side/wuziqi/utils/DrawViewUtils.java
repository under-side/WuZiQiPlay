package org.under_side.wuziqi.utils;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * 该类封装了绘制五子棋界面的各种方法
 * @author under_side
 *
 */
public class DrawViewUtils {

	private static float mRatioBitmapOfCellHeight = 3 * 1.0f / 4;

	// 自定义五子棋为12行
	private static int MAX_LINE = 12;

	// 绘制棋子
	public static void onDrawBlackPieces(Canvas canvas,
			ArrayList<Point> points, Paint myPaint, Bitmap bitmap,
			float cellHeight) {
		for (Point point : points) {
			int x = point.x;
			int y = point.y;

			float left = (x + (1 - mRatioBitmapOfCellHeight) / 2) * cellHeight;

			float top = (y + (1 - mRatioBitmapOfCellHeight) / 2) * cellHeight;

			canvas.drawBitmap(bitmap, left, top, myPaint);
		}
	}

	// 绘制棋盘
	public static void onDrawPanle(Canvas canvas, Paint myPaint,
			float cellHeight, int width) {

		/*
		 * 画棋盘的横线 画棋盘的横线时，起始点和结束点的x轴的坐标是不变的，变的是纵向Y轴的坐标。
		 * 由于设定的是单元格宽高为mCellHeight，则横线之间相差 一个mCellHeight的高度。
		 */
		for (int i = 0; i < MAX_LINE; i++) {

			int startX = (int) (cellHeight / 2);
			int endX = (int) (width - cellHeight / 2);

			int y = (int) ((0.5 + i) * cellHeight);

			canvas.drawLine(startX, y, endX, y, myPaint);
		}

		/*
		 * 画棋盘的纵线 画棋盘的纵线时，起始点和结束点的Y轴的坐标是不变的，变的是纵向X轴的坐标。
		 * 由于设定的是单元格宽高为mCellHeight，则纵线之间相差 一个mCellHeight的高度。
		 */
		for (int i = 0; i < MAX_LINE; i++) {
			int startY = (int) (cellHeight / 2);
			int endY = (int) (width - cellHeight / 2);

			int x = (int) ((0.5 + i) * cellHeight);

			canvas.drawLine(x, startY, x, endY, myPaint);
		}
	}
}
