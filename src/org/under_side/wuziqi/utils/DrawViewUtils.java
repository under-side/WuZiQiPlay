package org.under_side.wuziqi.utils;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * �����װ�˻������������ĸ��ַ���
 * @author under_side
 *
 */
public class DrawViewUtils {

	private static float mRatioBitmapOfCellHeight = 3 * 1.0f / 4;

	// �Զ���������Ϊ12��
	private static int MAX_LINE = 12;

	// ��������
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

	// ��������
	public static void onDrawPanle(Canvas canvas, Paint myPaint,
			float cellHeight, int width) {

		/*
		 * �����̵ĺ��� �����̵ĺ���ʱ����ʼ��ͽ������x��������ǲ���ģ����������Y������ꡣ
		 * �����趨���ǵ�Ԫ����ΪmCellHeight�������֮����� һ��mCellHeight�ĸ߶ȡ�
		 */
		for (int i = 0; i < MAX_LINE; i++) {

			int startX = (int) (cellHeight / 2);
			int endX = (int) (width - cellHeight / 2);

			int y = (int) ((0.5 + i) * cellHeight);

			canvas.drawLine(startX, y, endX, y, myPaint);
		}

		/*
		 * �����̵����� �����̵�����ʱ����ʼ��ͽ������Y��������ǲ���ģ����������X������ꡣ
		 * �����趨���ǵ�Ԫ����ΪmCellHeight��������֮����� һ��mCellHeight�ĸ߶ȡ�
		 */
		for (int i = 0; i < MAX_LINE; i++) {
			int startY = (int) (cellHeight / 2);
			int endY = (int) (width - cellHeight / 2);

			int x = (int) ((0.5 + i) * cellHeight);

			canvas.drawLine(x, startY, x, endY, myPaint);
		}
	}
}
