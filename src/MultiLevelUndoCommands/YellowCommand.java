package MultiLevelUndoCommands;

import java.awt.Color;

import objectdraw.FilledRect;

/**
 * A concrete command to set the rectangle to yellow.
 * 
 * @author yuhu
 *
 */
public class YellowCommand extends StyleCommand {

	public String toString() {
		return "Yellow";
	}

	/**
	 * Set the rectangle to Yellow if it's not yellow.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		Color currColor = rect.getColor();
		if (currColor.equals(Color.YELLOW)) {
			return false;
		}
		rect.setColor(Color.YELLOW);
		return true;
	}
}
