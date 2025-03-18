package SpaceSurvivorGame.HighScore;

import javax.swing.text.*;

/**
 * A custom document used to restrict the number of characters that can be entered into a text component.
 */
public class FormatDocument extends PlainDocument {
    private int limit;
    public FormatDocument(int limit) {
        super();
        this.limit = limit;
    }

    /**
     * Inserts a string into the document, if it does not exceed the character limit.
     *
     * @param offset the offset at which the string should be inserted.
     * @param str the string to insert.
     * @param attr the set of attributes for the inserted string.
     * @throws BadLocationException if the insertion position is invalid.
     */
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
