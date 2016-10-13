package org.under_side.wuziqi.utils;

import java.util.ArrayList;

import android.graphics.Point;

/**
 * 
 * �����װ�˼���������Ƿ�����Ҫ��ĸ��ַ���
 * @author under_side
 *
 */
public class CheckGameUtils {

	private static int MAX_PIECES_IN_LINE = 5;
	private static boolean isGameOver = false;

	public static boolean checkGameIsOver(ArrayList<Point> points) {

		/*
		 * ���ÿһ�����ӣ���������к�������б�ϡ�б�½����жϡ� ������Ƿ������������Ҫ���ˡ�
		 */
		for (Point point : points) {
			// �����������Ƿ�������������Ҫ��
			isGameOver = checkHorizontalLine(points, point);
			if (isGameOver)
				return true;
			// ������������Ƿ������������Ҫ��
			isGameOver = checkVerticalLine(points, point);
			if (isGameOver)
				return true;
			// �����б�������Ƿ������������Ҫ��
			isGameOver = checkLeftSlantLine(points, point);
			if (isGameOver)
				return true;
			// �����б�������Ƿ������������Ҫ��
			isGameOver = checkRightSlantLine(points, point);
			if (isGameOver)
				return true;
		}
		return false;
	}

	// �����б�������Ƿ������������Ҫ��
	private static boolean checkRightSlantLine(ArrayList<Point> points,
			Point point) {
		// ���ڱ������һ�����ӣ���count��ʼ��Ϊ 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// �ȼ����б���ϲ��Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x + i, y - i);
			if (points.contains(nextPoint)) {
				count++;
			} else {
				break;
			}
		}
		if (count == 5) {
			return true;
		}
		// ����б���Ļ�������ȥ�����б���²��Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x - i, y + i);
			if (points.contains(nextPoint)) {
				count++;
				if (count == 5) {
					return true;
				}

			} else {
				break;
			}
		}
		return false;
	}

	// �����б�������Ƿ������������Ҫ��
	private static boolean checkLeftSlantLine(ArrayList<Point> points,
			Point point) {
		// ���ڱ������һ�����ӣ���count��ʼ��Ϊ 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// �ȼ����б���ϲ��Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x - i, y - i);
			if (points.contains(nextPoint)) {
				count++;
			} else {
				break;
			}
		}
		if (count == 5) {
			return true;
		}
		// �������Ļ�������ȥ��������Ҳ��Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x + i, y + i);
			if (points.contains(nextPoint)) {
				count++;
				if (count == 5) {
					return true;
				}

			} else {
				break;
			}
		}
		return false;
	}

	// ������������Ƿ������������Ҫ��
	private static boolean checkVerticalLine(ArrayList<Point> points,
			Point point) {
		// ���ڱ������һ�����ӣ���count��ʼ��Ϊ 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// �ȼ��������ϲ��Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x, y - i);
			if (points.contains(nextPoint)) {
				count++;
			} else {
				break;
			}
		}
		if (count == 5) {
			return true;
		}
		// �������Ļ�������ȥ��������Ҳ��Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x, y + i);
			if (points.contains(nextPoint)) {
				count++;
				if (count == 5) {
					return true;
				}

			} else {
				break;
			}
		}
		return false;
	}

	// �����������Ƿ�������������Ҫ��
	private static boolean checkHorizontalLine(ArrayList<Point> points,
			Point point) {
		// ���ڱ������һ�����ӣ���count��ʼ��Ϊ 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// �ȼ����������Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x - i, y);
			if (points.contains(nextPoint)) {
				count++;
			} else {
				break;
			}
		}
		if (count == 5) {
			return true;
		}
		// �������Ļ�������ȥ��������Ҳ��Ƿ��������������Ҫ��
		for (int i = 1; i < MAX_PIECES_IN_LINE; i++) {
			Point nextPoint = new Point(x + i, y);
			if (points.contains(nextPoint)) {
				count++;
				if (count == 5) {
					return true;
				}

			} else {
				break;
			}
		}
		return false;
	}
}
