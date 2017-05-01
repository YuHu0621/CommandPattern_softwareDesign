package MultiLevelUndoCommands;

import java.awt.Color;

import objectdraw.FilledRect;

/**
 * A concrete command to set the rectangle to red
 * 
 * @author yuhu
 *
 */
public class RedCommand extends StyleCommand {

	public String toString() {
		return "Red";
	}

	/**
	 * Set the rectangle to red if it's not red.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		Color currColor = rect.getColor();
		if (currColor.equals(Color.RED)) {
			return false;
		}
		rect.setColor(Color.RED);
		return true;
	}
}
