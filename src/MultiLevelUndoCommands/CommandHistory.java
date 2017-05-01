package MultiLevelUndoCommands;
import java.util.Stack;

/**
 * Keeps track of the commands that can be undone and redone.
 * 
 * @author Barbara Lerner
 *
 */
public class CommandHistory {

	private static Stack<Command> undoCommands = new Stack<Command>();
	private static Stack<Command> redoCommands = new Stack<Command>();

	/**
	 * Adds a command to the history.
	 * 
	 * @param cmd
	 *            the command to add
	 */
	public static void record(Command cmd) {
		// Since there is just one instance of each command instantiated
		// when we create the GUI, we would have only one undo state
		// available without the clone. We need to clone the command
		// to support multi-level undo.
		undoCommands.push((Command) cmd.clone());
	}

	/**
	 * Undo the last command and move it to the redo stack.
	 */
	public static void undo() {
		Command cmd = undoCommands.pop();
		cmd.undo();
		redoCommands.push(cmd);
	}

	/**
	 * Redo the last command that was undone and move it to the undo stack.
	 */
	public static void redo() {
		Command cmd = redoCommands.pop();
		cmd.redo();
		undoCommands.push(cmd);
	}

	/**
	 * 
	 * @return true if there are no commands left to undo
	 */
	public static boolean canUndo() {
		return undoCommands.isEmpty();
	}

	/**
	 * 
	 * @return true if there are no commands left to redo
	 */
	public static boolean canRedo() {
		return redoCommands.isEmpty();
	}

	/**
	 * Remove all of the commands from the redo stack.
	 */
	public static void clearRedoCommands() {
		redoCommands.clear();
	}

}
