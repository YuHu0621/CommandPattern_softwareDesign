package MultiLevelUndoCommands;

import objectdraw.FilledRect;

public abstract class Command implements Cloneable {
	// All the command is called upon a filledRect object in the drawing
	// Program.
	protected FilledRect rect;

	public abstract boolean execute(FilledRect rect);

	public abstract void undo();

	public abstract void redo();

	/**
	 * The command is cloneable.
	 */
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// Should not happen
			throw new AssertionError();
		}
	}
}
