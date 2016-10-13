package org.under_side.wuziqi.utils;

import java.util.ArrayList;

import android.graphics.Point;

/**
 * 
 * 该类封装了检查五子棋是否满足要求的各种方法
 * @author under_side
 *
 */
public class CheckGameUtils {

	private static int MAX_PIECES_IN_LINE = 5;
	private static boolean isGameOver = false;

	public static boolean checkGameIsOver(ArrayList<Point> points) {

		/*
		 * 针对每一个棋子，都对其进行横向、纵向、斜上、斜下进行判断。 检查其是否满足五子棋的要求了。
		 */
		for (Point point : points) {
			// 检测横向棋子是否满足了五子琪要求
			isGameOver = checkHorizontalLine(points, point);
			if (isGameOver)
				return true;
			// 检测纵向棋子是否满足五子棋的要求
			isGameOver = checkVerticalLine(points, point);
			if (isGameOver)
				return true;
			// 检测左斜向棋子是否满足五子棋的要求
			isGameOver = checkLeftSlantLine(points, point);
			if (isGameOver)
				return true;
			// 检测右斜向棋子是否满足五子棋的要求
			isGameOver = checkRightSlantLine(points, point);
			if (isGameOver)
				return true;
		}
		return false;
	}

	// 检测右斜向棋子是否满足五子棋的要求
	private static boolean checkRightSlantLine(ArrayList<Point> points,
			Point point) {
		// 由于本身就是一个棋子，则count初始化为 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// 先检测右斜的上侧是否满足了五子棋的要求
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
		// 在右斜检查的基础上再去检测右斜的下侧是否满足了五子棋的要求
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

	// 检测左斜向棋子是否满足五子棋的要求
	private static boolean checkLeftSlantLine(ArrayList<Point> points,
			Point point) {
		// 由于本身就是一个棋子，则count初始化为 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// 先检测左斜的上侧是否满足了五子棋的要求
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
		// 在左侧检查的基础上再去检测横向的右侧是否满足了五子棋的要求
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

	// 检测纵向棋子是否满足五子棋的要求
	private static boolean checkVerticalLine(ArrayList<Point> points,
			Point point) {
		// 由于本身就是一个棋子，则count初始化为 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// 先检测纵向的上侧是否满足了五子棋的要求
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
		// 在左侧检查的基础上再去检测横向的右侧是否满足了五子棋的要求
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

	// 检测横向棋子是否满足了五子琪要求
	private static boolean checkHorizontalLine(ArrayList<Point> points,
			Point point) {
		// 由于本身就是一个棋子，则count初始化为 1
		int count = 1;
		int x = point.x;
		int y = point.y;
		// 先检测横向的左侧是否满足了五子棋的要求
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
		// 在左侧检查的基础上再去检测横向的右侧是否满足了五子棋的要求
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
