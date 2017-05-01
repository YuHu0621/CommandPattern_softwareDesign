package MultiLevelUndoCommands;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import objectdraw.FilledRect;
import objectdraw.Location;
import objectdraw.WindowController;

/**
 * Draw Rectangle is a drawing program that can resize and re-color a rectangle.
 * 
 * @author Barbara Lerner and yuhu
 *
 */
@SuppressWarnings("serial")
public class DrawRect extends WindowController implements ActionListener {
	// Approximate height of the menu bar added by Java
	private static final int MENU_BAR_HEIGHT = 50;

	// Size parameters
	private static final int INIT_SIZE = 100;

	private static final int CANVAS_WIDTH = 600;
	private static final int CANVAS_HEIGHT = 500;

	private ArrayList<FilledRect> shapes = new ArrayList<FilledRect>();
	// Where was the mouse the last time onMousePress/Drag was called?
	// Used to calculate how far to move the rectangle when dragging
	private Location lastMouse;

	// The shape that the user is dragging. This is null if
	// the user is not dragging the shape.
	private FilledRect shapeToMove;

	private FilledRect selectedShape;
	// Menu to change color of the shape
	private JComboBox<Command> colorMenu = new JComboBox<Command>();

	// Menu to change size of shape
	private JComboBox<Command> sizeMenu = new JComboBox<Command>();

	// Buttons for undo and redo
	private JButton undoButton;
	private JButton redoButton;

	private JButton newRectButton;
	private Command moveCMD;
	private Command createRectCmd;

	// There will be one single rectangle moving at a time.
	private static Location startLoc;

	/*
	 * Draws the program with the menus and a blank drawing area
	 */
	@Override
	public void begin() {
		resize(600, 500);

		// Put values in the size menu
		sizeMenu.addItem(new BiggerCommand());
		sizeMenu.addItem(new MediumCommand());
		sizeMenu.addItem(new SmallerCommand());
		sizeMenu.addActionListener(this);

		// Create color menu
		colorMenu.addItem(new BlackCommand());
		colorMenu.addItem(new RedCommand());
		colorMenu.addItem(new BlueCommand());
		colorMenu.addItem(new GreenCommand());
		colorMenu.addItem(new YellowCommand());
		colorMenu.addActionListener(this);

		// Create Buttons panel
		JPanel southPanel = new JPanel();
		southPanel.add(sizeMenu);
		southPanel.add(colorMenu);
		undoButton = createUndoButton();
		southPanel.add(undoButton);
		redoButton = createRedoButton();
		southPanel.add(redoButton);
		newRectButton = createNewRectButton();
		southPanel.add(newRectButton);
		add(southPanel, BorderLayout.SOUTH);

		// Create and display the rectangle
		Location defaultLoc = new Location(canvas.getWidth() / 2 - INIT_SIZE / 2,
				(canvas.getHeight() - MENU_BAR_HEIGHT) / 2 - INIT_SIZE / 2);
		FilledRect shape = new FilledRect(defaultLoc, INIT_SIZE, INIT_SIZE, canvas);
		selectedShape = shape;
		shapes.add(shape);

		// Create a move command
		moveCMD = new MoveCommand();

	}

	/**
	 * Handle menu actions
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		@SuppressWarnings("unchecked")
		JComboBox<Command> menu = (JComboBox<Command>) event.getSource();
		Command selectedCommand = (Command) menu.getSelectedItem();
		if (selectedCommand.execute(selectedShape)) {
			undoButton.setEnabled(true);
			redoButton.setEnabled(false);
			CommandHistory.clearRedoCommands();
		}

	}

	/**
	 * Create Redo Button.
	 * 
	 * @return return a redo button.
	 */
	private JButton createRedoButton() {
		JButton button = new JButton("Redo");
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {

			/** Redo the last command undone. */
			@Override
			public void actionPerformed(ActionEvent e) {
				CommandHistory.redo();
				undoButton.setEnabled(true);
				if (CommandHistory.canRedo()) {
					redoButton.setEnabled(false);
				}
			}

		});

		return button;
	}

	/**
	 * Create an undo button.
	 * 
	 * @return return an undo button.
	 */
	private JButton createUndoButton() {
		JButton button = new JButton("Undo");
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {

			/** Undo the last command executed. */
			@Override
			public void actionPerformed(ActionEvent e) {
				CommandHistory.undo();
				if (CommandHistory.canUndo()) {
					undoButton.setEnabled(false);
				}
				redoButton.setEnabled(true);
			}

		});

		return button;
	}

	/**
	 * Create a button that calls createNewRect command.
	 * 
	 * @return return a Jbutton that has an ActionListener to call createNewRect
	 *         Command.
	 */
	private JButton createNewRectButton() {
		JButton button = new JButton("New Rectangle");
		button.setEnabled(true);
		createRectCmd = new NewRectCommand();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Location initLoc = new Location(CANVAS_WIDTH / 2 - INIT_SIZE / 2,
						(CANVAS_HEIGHT - MENU_BAR_HEIGHT) / 2 - INIT_SIZE / 2);

				FilledRect newRect = new FilledRect(initLoc, INIT_SIZE, INIT_SIZE, canvas);
				shapes.add(newRect);
				selectedShape = newRect;
				if (createRectCmd.execute(newRect)) {
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
					CommandHistory.clearRedoCommands();
				}
			}

		});
		return button;
	}

	/**
	 * Select the object if it is pressed on and start a drag of that object
	 * 
	 * @param loc
	 *            where the user pressed the mouse button down
	 */
	@Override
	public void onMousePress(Location loc) {
		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i) == null) {
				continue;
			}
			if (shapes.get(i).contains(loc)) {
				selectedShape = shapes.get(i);
				shapeToMove = selectedShape;
				lastMouse = loc;
				startLoc = selectedShape.getLocation();
				break;
			}
		}

	}

	/**
	 * Drag the shape with the mouse.
	 * 
	 * @param loc
	 *            where the mouse is
	 */
	@Override
	public void onMouseDrag(Location loc) {
		if (shapeToMove != null) {
			// Move the shape and the selection border around the shape
			shapeToMove.move(loc.getX() - lastMouse.getX(), loc.getY() - lastMouse.getY());
			lastMouse = loc;
		}
	}

	/**
	 * Stop a drag.
	 * 
	 * @param loc
	 *            where the mouse is.
	 */
	@Override
	public void onMouseRelease(Location loc) {
		boolean mouseOnRect = shapeToMove != null && shapeToMove.contains(loc);
		if (mouseOnRect && moveCMD.execute(selectedShape)) {
			undoButton.setEnabled(true);
			redoButton.setEnabled(false);
			CommandHistory.clearRedoCommands();
		}
		shapeToMove = null;
	}

	/**
	 * static method for MoveCommand to get the starting location of the moving
	 * rectangle.
	 * 
	 * @return return startLoc.
	 */
	public static Location getStartLoc() {
		return startLoc;
	}
}
