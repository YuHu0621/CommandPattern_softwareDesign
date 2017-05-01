package MultiLevelUndoCommands;

import objectdraw.FilledRect;

/**
 * A concrete command to set the rectangle to MIDIUM_SIZE
 * 
 * @author yuhu
 *
 */
public class MediumCommand extends StyleCommand {

	private static final int MEDIUM_SIZE = 50;

	public String toString() {
		return "Medium";
	}

	/**
	 * Set the rectangle to medium size if it's not medium size.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		if (rect.getHeight() == MEDIUM_SIZE && rect.getWidth() == MEDIUM_SIZE) {
			return false;
		}
		rect.setSize(MEDIUM_SIZE, MEDIUM_SIZE);
		return true;
	}

}
