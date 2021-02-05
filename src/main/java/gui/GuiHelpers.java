package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class GuiHelpers {

    /**
     * Helper method for adding components to a GridBagLayout.
     * 
     * @param cont
     * @param gbl
     * @param c
     * @param x
     * @param y
     * @param width
     * @param height
     * @param weightx
     * @param weighty
     */
    public static void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
            int height, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbl.setConstraints(c, gbc);
        cont.add(c);
    }

    /**
     * Verlaengert einen String um Leerzeichen, bis er eine gewünschte Länge erreicht hat.
     * 
     * @param s
     * @param l
     * @return
     */
    public static String formatString(String s, int l) {
        int dif = l - s.length();
        if (dif > 0) {
            for (int i = 0; i < dif; i++)
                s += " ";
        }
        return s;
    }
}
