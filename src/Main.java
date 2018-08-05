import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

class Main extends JFrame										//主类
{
    private static int SIZEX = 16;
    private static int SIZEY = 30;

    public Main()
    {
        setTitle("MineSweeper");										//设置标题
        setSize(SIZEY * 60, (SIZEX + 1) * 60);					//设定窗口大小
        setLocation(20, 10);						//设定窗口起始位置
        setResizable(false);									//禁止调整窗体大小
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	//窗口关闭按钮方式
        getContentPane().setLayout(new GridLayout(SIZEX + 1, SIZEY, 5, 5));	//设定布局方式为GridLayout型

        Pane GamePanel = new Pane(this);						//创建2048棋盘
        addWindowListener										//关闭按钮事件
                (
                        new WindowAdapter()
                        {
                            public void windowClosing(WindowEvent e)
                            {
                                GamePanel.SaveTimeRecord();					//保存记录
                                System.exit(0);
                            }
                        }
                );
        this.setVisible(true);									//设为可视
    }

    public static void main(String args[])
    {
        JFrame.setDefaultLookAndFeelDecorated(true);			//设定Frame的缺省外观
        new Main();
    }
}