package MultiLevelUndoCommands;

import objectdraw.FilledRect;

/**
 * A concrete command to set the rectangle to MIN_SIZE.
 * 
 * @author yuhu
 *
 */
public class SmallerCommand extends StyleCommand {
	private static final int MIN_SIZE = 10;

	public String toString() {
		return "Small";
	}

	/**
	 * Set the rectangle to small if it's not small size.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		if (rect.getWidth() == MIN_SIZE && rect.getHeight() == MIN_SIZE) {
			return false;
		}
		rect.setSize(MIN_SIZE, MIN_SIZE);
		return true;
	}

}
