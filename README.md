﻿# MineSweeper(扫雷)
### Game MineSweeper on Java with version 1.0.0.

### 游戏界面(采用30*17网格布局)包括：
1. 显示剩余雷数区域(2*1)；
2. 显示最快记录区域(2*1)；
3. 显示本局游戏计时区域(单独线程)(2*1)：
4. 30*16的游戏方格(30*16)；

### 游戏内容:
1. 在30*16方格内通过点击鼠标扫雷，左键为打开区域，右键为标记区域，左右同按为探索周围区域，若正确探明所有区域则获胜；
2. 当游戏结束时，会显示所有雷区，并关闭网格内鼠标监听器,点击“重来”方格可以重新游戏；
3. 剩余雷数会实时的显示在面板上；
4. 最快记录会在获胜时实时比较更新；
5. 游戏结束或关闭时，会保存最快记录数据至工程目录下的TimeRecord.txt文本文档中，如果不存在，则创建该文档；
6. 游戏开始时如果存在TimeRecord.txt，则会读入其中数据并显示于最快记录上；
7. 计时器通过单独线程，每秒+1，在重新游戏时重置；

### 游戏包含四个类：
| - | - |
| Main类 | 类中主要实现了游戏主窗口的设置与主面板等的定义； |
| Pane类 | 类中包含了游戏原理的实现，以及添加方格、最快记录,记录文档读写、点击监听器功能等； |
| Block类 | 此类为方格类，包含了方格组件的相关设置，颜色变化，数字显示等功能； |
| Timer类 | 包含了计时器的相关定义与实现及对应线程的建立与开启； |

<br/>

![MineSweeper1][1]

[1]: /MineSweeper1.png
