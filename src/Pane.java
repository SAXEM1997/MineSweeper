import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;

import javax.swing.*;

public class Pane implements KeyListener
{
    private JPanel panel;

    private Block [][] pane;

    private static int SIZEX = 16;
    private static int SIZEY = 30;
    private static int MINENUMBER = 99;

    private int mineLeft;
    private int blockLeft;
    private int timeRecord;

    private boolean enableMouseListener;
    private boolean ifFirstClick;

    private JTextField TimeRecord;
    private JTextField MineLeft;
    private Timer MyTimer;
    private File Records;

    public Pane(JFrame frame)
    {
        pane = new Block[SIZEX][SIZEY];
        this.panel = (JPanel)frame.getContentPane();
        mineLeft = MINENUMBER;
        blockLeft = SIZEX * SIZEY;
        timeRecord = 0;
        enableMouseListener = true;
        ifFirstClick = true;

        Records = new File(System.getProperty("user.dir" + "\\TimeRecord.txt"), "TimeRecord.txt");
        if (Records.exists())
        {
            try
            {
                FileReader myFile = new FileReader(Records);
                int ch;
                while(true)							//读入记录
                {
                    ch = myFile.read();
                    if (ch == -1)
                        break;
                    timeRecord = timeRecord * 10 + ch - 48;
                }
                myFile.close();
            }
            catch (IOException e)
            {
            }
        }

        MyTimer = new Timer(frame);				//创建计时器

        JLabel labelRecord = new JLabel("记录:");
        labelRecord.setHorizontalAlignment(JLabel.CENTER);
        labelRecord.setFocusable(false);
        panel.add(labelRecord);

        TimeRecord = new JTextField("0");
        TimeRecord.setEditable(false);
        TimeRecord.setHorizontalAlignment(JTextField.CENTER);
        TimeRecord.setText(String.valueOf(timeRecord));
        panel.add(TimeRecord);

        JLabel labelMine = new JLabel("雷数:");
        labelMine.setHorizontalAlignment(JLabel.CENTER);
        labelMine.setFocusable(false);
        panel.add(labelMine);

        MineLeft = new JTextField();
        MineLeft.setHorizontalAlignment(JTextField.CENTER);
        MineLeft.setEditable(false);
        MineLeft.setFocusable(false);
        panel.add(MineLeft);									//添加至主面板中

        JLabel labelRestart = new JLabel("重来");
        labelRestart.setHorizontalAlignment(JLabel.CENTER);
        labelRestart.setFocusable(false);
        panel.add(labelRestart);
        labelRestart.addMouseListener
                (
                        new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                NewGame();
                            }
                        }
                );

        for (int i = 0; i < SIZEY - 7; i++)
        {
            JLabel blankLabel = new JLabel();
            panel.add(blankLabel);
        }

        for (int i = 0; i < SIZEX; i++)
        for (int j = 0; j < SIZEY; j++)
        {
            pane[i][j] = new Block();							//加入方格
            pane[i][j].setHorizontalAlignment(JLabel.CENTER);
            pane[i][j].setOpaque(true);							//不透明的标签
            panel.add(pane[i][j]);
            int nowi = i;
            int nowj = j;
            pane[i][j].addMouseListener
                    (
                            new MouseAdapter()
                            {
                                @Override
                                public void mouseClicked(MouseEvent e)
                                {

                                }

                                @Override
                                public void mousePressed(MouseEvent e)
                                {
                                    if (enableMouseListener)
                                    {
                                        if (e.getModifiersEx() == (e.BUTTON3_DOWN_MASK + e.BUTTON1_DOWN_MASK))
                                        {
                                            BothClick(nowi, nowj);
                                            for (int k = Max(nowi - 1, 0); k <= Min(nowi + 1, SIZEX - 1); k++)
                                                for (int l = Max(nowj - 1, 0); l <= Min(nowj + 1, SIZEY - 1); l++)
                                                    pane[k][l].setState(3);
                                        } else if (e.getButton() == MouseEvent.BUTTON1)
                                        {
                                            LeftClick(nowi, nowj);
                                        }
                                        else if (e.getButton() == MouseEvent.BUTTON3)
                                        {
                                            RightClick(nowi, nowj);
                                        }
                                    }
                                }

                                @Override
                                public void mouseReleased(MouseEvent e)
                                {
                                    if (enableMouseListener)
                                    {
                                        if (e.getButton() == MouseEvent.BUTTON1)
                                        {
                                            for (int k = Max(nowi - 1, 0); k <= Min(nowi + 1, SIZEX - 1); k++)
                                                for (int l = Max(nowj - 1, 0); l <= Min(nowj + 1, SIZEY - 1); l++)
                                                    pane[k][l].setState(4);
                                        }
                                    }
                                }
                            }
                    );
        }
        NewGame();
    }

    private static int Max(int a, int b)
    {
        return (a>b?a:b);
    }

    private static int Min(int a, int b)
    {
        return (a<b?a:b);
    }

    private void NewGame()
    {
        mineLeft = MINENUMBER;
        blockLeft = SIZEX * SIZEY;
        MyTimer.ResetTimer();
        MineLeft.setText(String.valueOf(mineLeft));
        ifFirstClick = true;
        enableMouseListener = true;
        for (int i = 0; i < SIZEX; i++)
        for (int j = 0; j < SIZEY; j++)
        {
            pane[i][j].setValue(0);
            pane[i][j].setState(0);
        }
    }

    private void FirstClick(int i, int j)
    {
        ifFirstClick = false;
        java.util.Random r = new java.util.Random();
        int imin = Max(i - 1, 0);
        int imax = Min(i + 1, SIZEX - 1);
        int jmin = Max(j - 1, 0);
        int jmax = Min(j + 1, SIZEY - 1);
        int mleft = mineLeft;
        int k, l;
        while (mleft > 0)
        {
            k = r.nextInt(SIZEX);
            l = r.nextInt(SIZEY);
            if (!(k >= imin && k <= imax && l >= jmin && l <= jmax) && (pane[k][l].getValue() != -1))
            //if (!(k == i && l == j) && (pane[k][l].getValue() != -1))
            {
                pane[k][l].setValue(-1);
                mleft--;
                for (int m = Max(k - 1, 0); m <= Min(k + 1, SIZEX - 1); m++)
                for (int n = Max(l - 1, 0); n <= Min(l + 1, SIZEY - 1); n++)
                {
                    if (pane[m][n].getValue() != -1)
                        pane[m][n].setValue(pane[m][n].getValue() + 1);
                }
            }
        }
        LeftClick(i, j);
    }

    private boolean IfSweeped(int i, int j)
    {
        int value = pane[i][j].getValue();
        int count = 0;
        for (int k = Max(i - 1, 0); k <= Min(i + 1, SIZEX - 1); k++)
        for (int l = Max(j - 1, 0); l <= Min(j + 1, SIZEY - 1); l++)
        {
            if (count >= value)
                return true;
            if (pane[k][l].getState() == 2)
                count++;
        }
        return count >= value;
    }

    private void LeftClick(int i, int j)
    {
        if (ifFirstClick)
            FirstClick(i, j);
        else
        {
            if (pane[i][j].getValue() == -1)
            {
                Over();
                return;
            }
            if (pane[i][j].getState() != 1)
            {
                if (pane[i][j].getState() == 2)
                {
                    mineLeft++;
                    MineLeft.setText(String.valueOf(mineLeft));
                }
                pane[i][j].setState(1);
                blockLeft--;
                if (blockLeft == MINENUMBER)
                    Win();
                if (IfSweeped(i, j))
                {
                    for (int k = Max(i - 1, 0); k <= Min(i + 1, SIZEX - 1); k++)
                    for (int l = Max(j - 1, 0); l <= Min(j + 1, SIZEY - 1); l++)
                    {
                        if (pane[k][l].getState() == 0)
                            LeftClick(k, l);
                    }
                }
            }
        }
    }

    private void RightClick(int i, int j)
    {
        int state = pane[i][j].getState();
        pane[i][j].setState(2 - state);
        mineLeft -= 1 - state;
        MineLeft.setText(String.valueOf(mineLeft));
    }

    private void BothClick(int i, int j)
    {
        if (pane[i][j].getState() == 1)
        {
            if (IfSweeped(i, j))
            {
                for (int k = Max(i - 1, 0); k <= Min(i + 1, SIZEX - 1); k++)
                    for (int l = Max(j - 1, 0); l <= Min(j + 1, SIZEY - 1); l++)
                    {
                        if (pane[k][l].getState()==0)
                            LeftClick(k, l);
                    }
            }
        }
    }

    private void Over()
    {
        enableMouseListener = false;
        for (int i = 0; i < SIZEX; i++)
        for (int j = 0; j < SIZEY; j++)
        {
            if (pane[i][j].getValue() == -1)
                pane[i][j].setState(1);
        }
    }

    private void Win()
    {
        if (MyTimer.getTime() < timeRecord || timeRecord == 0)
        {
            timeRecord = MyTimer.getTime();
            TimeRecord.setText(String.valueOf(timeRecord));
            SaveTimeRecord();
        }
        for (int i = 0; i < SIZEX; i++)
        for (int j = 0; j < SIZEY; j++)
        {
            if (pane[i][j].getState() == 0)
            {
                RightClick(i, j);
                pane[i][j].setState(4);
            }
        }
        enableMouseListener = false;
    }

    public void SaveTimeRecord()			//保存记录至txt中
    {
        try
        {
            FileWriter myFile1 = new FileWriter(Records);
            myFile1.write(String.valueOf(timeRecord));
            myFile1.close();
        }
        catch (IOException e1)
        {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
    }


    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
