package MultiLevelUndoCommands;

import objectdraw.FilledRect;

/**
 * a concrete command to set the rectangle to MAX_SIZE.
 * 
 * @author yuhu
 *
 */
public class BiggerCommand extends StyleCommand {

	private static final int MAX_SIZE = 100;

	public String toString() {
		return "Big";
	}

	/**
	 * Set the rectangle to bigger size if it's not bigger size.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		if (rect.getHeight() == MAX_SIZE && rect.getWidth() == MAX_SIZE) {
			return false;
		}
		rect.setSize(MAX_SIZE, MAX_SIZE);
		return true;

	}
}
