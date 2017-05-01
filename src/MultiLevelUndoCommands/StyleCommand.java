package MultiLevelUndoCommands;

import java.awt.Color;

import objectdraw.FilledRect;

/**
 * A command that is able to undo all commands that change rectangle styles.
 * This class is extended with concrete command classes that make specific style
 * changes.
 * 
 * @author Barbara Lerner and yuhu
 *
 */
public abstract class StyleCommand extends Command {
	private double prevWidth;
	private double prevHeight;
	private Color prevFilledColor;

	/**
	 * Save the state of the component before its font is changed so that it can
	 * be restored on undo. Then execute the command. If the command has an
	 * effect, save it in the command history so that it can be undone later.
	 * 
	 * This method is a template method. Subclasses need to define doCommand to
	 * carry out the commands action.
	 * 
	 * @param rect
	 *            The component whose font is changing.
	 * @return true if the font is changed
	 */
	public boolean execute(FilledRect rect) {
		saveCommand(rect);
		if (doCommand(rect)) {
			CommandHistory.record(this);
			return true;
		}
		return false;
	}

	/**
	 * Saves the state needed to undo this command. Specifically, it saves the
	 * component whose font is changed and the font it has before the command is
	 * applied.
	 * 
	 * If a subclass wants to save additional information, it should override
	 * saveState, being sure to call super.saveState().
	 * 
	 * @param rect
	 *            the GUI component that is being modified.
	 */
	private void saveCommand(FilledRect rect) {
		this.rect = rect;
		prevWidth = rect.getHeight();
		prevHeight = rect.getHeight();
		prevFilledColor = rect.getColor();
	}

	/**
	 * Details of doCommand is specified in each sub classes.
	 * 
	 * @param rect
	 * @return
	 */
	protected abstract boolean doCommand(FilledRect rect);

	@Override
	/**
	 * Restore the previous style.
	 */
	public void undo() {
		rect.setSize(prevWidth, prevHeight);
		rect.setColor(prevFilledColor);
	}

	@Override
	/**
	 * Re-execute the command.
	 */
	public void redo() {
		doCommand(rect);
	}
}
