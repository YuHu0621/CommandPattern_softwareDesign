package MultiLevelUndoCommands;

import java.awt.Color;

import objectdraw.FilledRect;

/**
 * a concrete command to turn the rectangle blue
 * 
 * @author yuhu
 *
 */
public class BlueCommand extends StyleCommand {

	public String toString() {
		return "Blue";
	}

	/**
	 * Set the rectangle to blue if it's not blue.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		Color currColor = rect.getColor();
		if (currColor.equals(Color.BLUE)) {
			return false;
		}
		rect.setColor(Color.BLUE);
		return true;
	}

}
