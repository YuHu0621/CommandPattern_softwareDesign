package MultiLevelUndoCommands;

import java.awt.Color;

import objectdraw.FilledRect;

/**
 * A concrete command to set the rectangle green.
 * 
 * @author yuhu
 *
 */
public class GreenCommand extends StyleCommand {

	public String toString() {
		return "Green";
	}

	/**
	 * Set the rectangle to green if it's not green.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		Color currColor = rect.getColor();
		if (currColor.equals(Color.GREEN)) {
			return false;
		}
		rect.setColor(Color.GREEN);
		return true;
	}
}
