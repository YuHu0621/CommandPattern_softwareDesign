package MultiLevelUndoCommands;

import objectdraw.DrawingCanvas;
import objectdraw.FilledRect;
import objectdraw.Location;

/**
 * A concrete command that create a new rectangle
 * 
 * @author yuhu
 *
 */
public class NewRectCommand extends Command {

	private DrawingCanvas canvas;
	private Location loc;
	private double height;
	private double width;

	/**
	 * execute create new rectangle command.
	 */
	@Override
	public boolean execute(FilledRect rect) {
		if (rect == null) {
			return false;
		}
		saveCommand(rect);
		CommandHistory.record(this);
		return true;
	}

	/**
	 * Remember where the rectangle is created.
	 * 
	 * @param rect
	 *            the new rectangle
	 */
	private void saveCommand(FilledRect rect) {
		this.rect = rect;
		canvas = rect.getCanvas();
		loc = rect.getLocation();
		height = rect.getHeight();
		width = rect.getWidth();
	}

	@Override
	/**
	 * remove the rectangle from the canvas
	 */
	public void undo() {
		rect.removeFromCanvas();
		rect = null;
	}

	@Override
	/**
	 * Put the rectangle back onto the canvas
	 */
	public void redo() {
		rect = new FilledRect(loc, height, width, canvas);
	}

}
