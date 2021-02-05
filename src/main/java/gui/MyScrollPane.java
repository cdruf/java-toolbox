package gui;

import java.awt.Component;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

@SuppressWarnings({ "serial" })
public class MyScrollPane extends JScrollPane {

    public MyScrollPane() {
        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setBorder(null);
        JScrollBar scrollBar = this.getVerticalScrollBar();
        scrollBar.setUnitIncrement(25);
        scrollBar.setBlockIncrement(100);

    }

    public MyScrollPane(Component comp) {
        super(comp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setBorder(null);
        JScrollBar scrollBar = this.getVerticalScrollBar();
        scrollBar.setUnitIncrement(25);
        scrollBar.setBlockIncrement(100);

    }

}
