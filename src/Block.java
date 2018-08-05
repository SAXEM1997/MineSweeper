import javax.swing.*;
import java.awt.*;

public class Block extends JLabel
{
    private int value;
    private int state;

    public Block()
    {
        value = 0;
        state = 0;
        setFont(new Font("font", Font.BOLD, 30));   //设定字体
        setBackground(new Color(192, 192, 192));      //设定初始颜色为灰色
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int v)
    {
        value = v;
        showText();
    }

    public int getState()
    {
        return state;
    }

    public void setState(int s)
    {
        if (s == 3)
            setBackground(new Color(128, 138, 135));
        else if (s == 4 && state != 1)
            setBackground(new Color(192, 192, 192));
        else if (s == 4 && state == 1);
        else if (state != s)
        {
            state = s;
            showText();
        }
    }

    private void showText()
    {
        String text = String.valueOf(value);
        switch (state)
        {
            case 0: setText(""); setBackground(new Color(192, 192, 192)); break;
            case 1:
                setBackground(new Color(128, 138, 135));
                switch (value)
                {
                    case -1: setText("*"); setForeground(Color.black); break;
                    case  0: setText(" "); setForeground(Color.gray); break;
                    case  1: setText("1"); setForeground(new Color(0, 0, 255)); break;
                    case  2: setText("2"); setForeground(new Color(0, 150, 0)); break;
                    case  3: setText("3"); setForeground(new Color(255, 0, 0)); break;
                    case  4: setText("4"); setForeground(new Color(0, 0, 128)); break;
                    case  5: setText("5"); setForeground(new Color(139, 76, 57 )); break;
                    case  6: setText("6"); setForeground(new Color(0,  206, 209 )); break;
                    case  7: setText("7"); setForeground(new Color(138, 43, 226)); break;
                    case  8: setText("8"); setForeground(new Color(255, 69, 0 )); break;
                } break;
            case 2: setText("!"); setForeground(Color.red); setBackground(new Color(192, 192, 192)); break;
        }
    }
}
