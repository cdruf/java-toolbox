package gui;

import java.awt.Color;

import javax.swing.JFrame;

public class MyColorCollection {

    private static int nextColIndex = 0;
    private static final Color[] colors;

    static {
	colors = new Color[8];
	colors[0] = new Color(101, 158, 238);
	colors[1] = new Color(255, 192, 0);
	colors[2] = new Color(0, 101, 189);
	colors[3] = new Color(44, 165, 255);
	colors[4] = new Color(0, 176, 80);
	colors[5] = new Color(123, 116, 88);
	colors[6] = new Color(150, 210, 255);
	colors[7] = new Color(255, 102, 255);
    }

    public static Color getNextColor() {
	Color ret = colors[nextColIndex];
	nextColIndex = (++nextColIndex) % 8;
	return ret;
    }

    public static void test() {
	JFrame f = new JFrame();
	f.getContentPane().setBackground(MyColorCollection.getNextColor());
	f.setBounds(0, 0, 100, 100);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setVisible(true);
	for (int i = 0; i < 10; i++) {
	    f.getContentPane().setBackground(MyColorCollection.getNextColor());
	    try {
		Thread.sleep(2000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
}
