package gui;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class MyJTextPane extends JTextPane {

    public void append(String str) {
	Document doc = getDocument();
	if (doc != null) {
	    try {
		doc.insertString(doc.getLength(), str, null);
	    } catch (BadLocationException e) {
	    }
	}
    }

    public void append(String str, AttributeSet attributeSet) {
	Document doc = getDocument();
	if (doc != null) {
	    try {
		doc.insertString(doc.getLength(), str, attributeSet);
	    } catch (BadLocationException e) {
	    }
	}
    }

}
