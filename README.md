# 五子棋
根据所学的自定义View的知识，运用五子棋来练习与回顾。

### 实现步骤
1. 棋盘的绘制
   1. 自定义view实现五子棋的绘制；
   2. 重写onDraw方法，实现绘制工作；
   3. 在onMeasure中去测量布局五子棋面板，使其为正方形；
2. 重写onTouchEvent方法，实现手势触摸操作
   1. 对棋子进行缩放处理；
   2. 在	ACTION_UP中处理手势操作；
   3. 运用两个list数组记录黑白棋点击的位置；
3. 绘制棋子
   1. canvas绘制bitmap是从左上开始绘制；
   2. 确定棋子的xy坐标；
4. 逻辑判断
   1. 针对每个棋子都进行水平、竖直、斜上、斜下进行满足五子棋要求的检查；
   2. 判断方法：运用count累计，从当前棋子坐标开始，查看list中相应坐标上是否含有值，有则count++，否则，退出循环。然后，从另一边开始循环。
5. view的保存与绘制
   1. 在view中运用onSaveInstanceState和onRestoreInstanceState方法去保存和恢复view；
   2. 如果想在自定义view中保存与恢复view，则理论上是需要为该view添加一个唯一的id，使activity在调用view进行恢复时，可以唯一的找到给view。即，运用该id去标识view。

### 难点
1. 棋盘的绘制
   1. 获取view的宽高，取其小值，绘制成正方形；
   2. 确定棋盘的单元格的宽和高；
   3. 为了让棋子在边缘上可以绘制棋子，需要将棋盘缩放在view内，而缩放的大小为单元格高度的一半，这样就可以在边缘绘制棋子；
   4. 画静态草图，计算每条线的起始坐标和结束坐标规律；
2. 棋子的绘制
   1. 缩小棋子图片的尺寸，利用Bitmap.createScaledBitmap()方法去将原有的bitmap按照一定的尺寸进行缩放；
   2. 根据设置的单元格将棋盘转换为从x-->0-n,y-->n的形式去替换其所在的坐标，利用positionX=x/cellHeight;positionY=y/cellHeight;
   2. canvas绘制bitmap时，是从左上开始绘制。则，画静态草图，计算每个单元格上左上的坐标变化规律；
   
   
   
![](https://github.com/under-side/WuZiQiPlay/blob/master/photo/wuzipi.png "五子棋绘制示意图")
