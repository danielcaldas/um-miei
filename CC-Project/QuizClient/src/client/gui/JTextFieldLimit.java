package client.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Limitar input de carateres num campo de texto.
 * @author jdc
 * @version 2015.05.21
 */

public class JTextFieldLimit extends PlainDocument {
  private int limit;

  public JTextFieldLimit(int limit) {
    super();
    this.limit = limit;
  }

  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
    if (str == null) return;

    if ((getLength() + str.length()) <= limit) {
      super.insertString(offset, str, attr);
    }
  }
}
