package MultiLevelUndoCommands;

import java.awt.Color;

import objectdraw.FilledRect;

/**
 * a concrete command to turn the rectangle black
 * 
 * @author yuhu
 *
 */
public class BlackCommand extends StyleCommand {

	public String toString() {
		return "Black";
	}

	/**
	 * Set the rectangle to black if it's not black.
	 */
	@Override
	protected boolean doCommand(FilledRect rect) {
		Color currColor = rect.getColor();
		if (currColor.equals(Color.BLACK)) {
			return false;
		}
		rect.setColor(Color.BLACK);
		return true;
	}
}
