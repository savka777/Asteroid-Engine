package AsteriodGame.Player;

import javax.swing.text.*;

public class LimitedDocument extends PlainDocument {
    private int limit;

    public LimitedDocument(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
