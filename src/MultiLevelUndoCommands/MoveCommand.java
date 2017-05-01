package MultiLevelUndoCommands;

import objectdraw.FilledRect;
import objectdraw.Location;

/**
 * A concrete command that move the rectangle with mouse.
 * 
 * @author yuhu
 *
 */
public class MoveCommand extends Command {

	// init pos
	private Location startLoc;
	// final pos
	private Location finalLoc;

	@Override
	/**
	 * Execute move command when user release the mouse saveCommand store the
	 * finalLoc as the current location of the rectangle; initLoc stores the
	 * previous finalLoc value. if the rectangle's current location is different
	 * from its initLoc, store the command in the CommandHistory and return
	 * true. Otherwise, return false.
	 * 
	 */
	public boolean execute(FilledRect rect) {
		saveCommand(rect);
		if (finalLoc != startLoc) {
			CommandHistory.record(this);
			return true;
		}
		return false;
	}

	/**
	 * Save previous command that remember the previous location and final
	 * destination of the moving rectangle.
	 * 
	 * @param rect
	 */
	private void saveCommand(FilledRect rect) {
		this.rect = rect;
		finalLoc = rect.getLocation();
		startLoc = DrawRect.getStartLoc();
	}

	/**
	 * Restore the previous location.
	 */
	@Override
	public void undo() {
		rect.moveTo(startLoc);
	}

	/**
	 * Re-locate to the destination position.
	 */
	@Override
	public void redo() {
		rect.moveTo(finalLoc);
	}

}
