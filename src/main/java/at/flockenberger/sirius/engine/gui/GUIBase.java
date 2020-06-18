package at.flockenberger.sirius.engine.gui;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.event.gui.GUIClickListener;
import at.flockenberger.sirius.engine.event.gui.GUIEnterListener;
import at.flockenberger.sirius.engine.event.gui.GUIExitedListener;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.render.Renderer;

/**
 * <h1>GUIBase</h1><br>
 * The base class for all GUI elements.<br>
 * It features some simple functionality to check whether the mouse has entered
 * or exited this GUI by calling {@link #onMouseEntered()} and
 * {@link #onMouseExited()} respectively. <br>
 * The {@link #onMouseClicked()}, {@link #onMouseEntered()} and
 * {@link #onMouseExited()} methods are mostly for internal use and should be
 * overridden by the GUI itself to handle these input events additionally any
 * GUI supports listeners namely {@link GUIClickListener},
 * {@link GUIEnterListener} and {@link GUIExitedListener} which can be set by
 * their respective methods.<br>
 * These allow external calls to be made uppon one of these events.
 * 
 * @author Florian Wagner
 *
 */
public abstract class GUIBase
{
	/**
	 * position of the gui
	 */
	protected Vector2f position;

	/**
	 * width of the gui
	 */
	protected int width;

	/**
	 * height of the gui
	 */
	protected int height;

	/**
	 * the current mouse state of the gui
	 */
	protected GUIMouseState mouseState;

	/**
	 * the old / previous mouse state of the gui
	 */
	protected GUIMouseState oldMouseState;

	/**
	 * the current gui state
	 */
	protected GUIState guiState;

	/**
	 * called when this gui was clicked on
	 */
	protected GUIClickListener clickListener;

	/**
	 * called when the mouse entered this gui
	 */
	protected GUIEnterListener enteredListener;

	/**
	 * called when the mouse exited this gui
	 */
	protected GUIExitedListener exitedListener;

	/**
	 * Constructor.<br>
	 * Creates a new GUIBase Object.
	 */
	public GUIBase()
	{
		this.position = new Vector2f(0, 0);
		this.width = 0;
		this.height = 0;
		this.mouseState = GUIMouseState.OUTSIDE;
		this.guiState = GUIState.ENABLED;
	}

	/**
	 * Updates this gui and test if the {@link Mouse} was moved inside/outside this
	 * GUI element.<br>
	 * This method sets the current {@link GUIMouseState} parameter and checks if a
	 * mouse click has occured. If the mouse has entered the GUI
	 * {@link #onMouseEntered()} will be called vice versa if the mouse has exited
	 * the GUI {@link #onMouseExited()} will be called.<br>
	 * Internally this method calls {@link #isMouseInside()} to check whether the
	 * mouse was moved inside the GUI or if it was exited.<br>
	 * 
	 */
	public void update()
	{
		this.oldMouseState = this.mouseState;
		if (this.guiState.equals(GUIState.ENABLED))
		{
			if (isMouseInside())
			{

				this.mouseState = GUIMouseState.HOVERED;
				if (!oldMouseState.equals(GUIMouseState.HOVERED))
				{
					onMouseEntered();
					if (this.enteredListener != null)
						this.enteredListener.onGUIEntered(this);
				}
			} else
			{
				this.mouseState = GUIMouseState.OUTSIDE;
				if (oldMouseState.equals(GUIMouseState.HOVERED))
				{
					onMouseExited();
					if (this.exitedListener != null)
						this.exitedListener.onGUIExited(this);
				}
			}

			if (this.mouseState.equals(GUIMouseState.HOVERED) && this.guiState.equals(GUIState.ENABLED)
					&& Mouse.get().isLeftButtonClicked())
			{
				onMouseClicked();
				if (clickListener != null)
					clickListener.onGUIClicked(this);
			}
		}
	}

	/**
	 * Tests whether the mouse is inside or outside the GUI.<br>
	 * Note: This method does not set the {@link #guiState} of this GUI it only test
	 * the mouse positions. <br>
	 * This method will always return false if the {@link GUIState}
	 * {@link #guiState} is set to {@link GUIState#DISABLED} and will not perform
	 * any testing.
	 * 
	 * @return true if inside otherwise false
	 */
	public boolean isMouseInside()
	{
		if (guiState.equals(GUIState.DISABLED))
			return false;

		float x = (float) Mouse.get().getX();
		float y = (float) Mouse.get().getY();

		return x >= this.position.x - width / 2f && x <= this.position.x + width / 2f
				&& y >= this.position.y - width / 2f && y <= this.position.y + height / 2f;
	}

	/**
	 * Sets the width of this GUI.<br>
	 * 
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{ this.width = width; }

	/**
	 * Sets the height of this GUI.<br>
	 * 
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{ this.height = height; }

	/**
	 * @return the current width of this GUI
	 */
	public int getWidth()
	{ return this.width; }

	/**
	 * @return the current height of this GUI
	 */
	public int getHeight()
	{ return this.height; }

	/**
	 * Calculates and returns the minimum width required by this GUI element.<br>
	 * This method is used for internal layout and rendering.
	 * 
	 * @return the minimum width of this GUI
	 */
	public abstract int getMinWidth();

	/**
	 * Calculates and returns the minimum height required by this GUI element.<br>
	 * This method is used for internal layout and rendering.
	 * 
	 * @return the minimum height of this GUI
	 */
	public abstract int getMinHeight();

	/**
	 * Returns the position of this GUI.<br>
	 * Note: Generally the position should be the center point of the GUI.<br>
	 * While the width and height have no influence on the position, meaning the
	 * position, by default , corresponds to the top left corner of the GUI it is
	 * advised to correct the position during rendering and other uses.
	 * 
	 * @return the position of this GUI
	 */
	public Vector2f getPosition()
	{ return this.position; }

	/**
	 * Sets the the {@link GUIClickListener} for this GUI.<br>
	 * This allows external handling for this specific event.
	 * 
	 * @param guiClickListener the {@link GUIClickListener} to set
	 */
	public void setOnMouseClicked(GUIClickListener guiClickListener)
	{ this.clickListener = guiClickListener; }

	/**
	 * Sets the the {@link GUIEnterListener} for this GUI.<br>
	 * This allows external handling for this specific event.
	 * 
	 * @param guiEnteredListener the {@link GUIEnterListener} to set
	 */
	public void setOnMouseEntered(GUIEnterListener guiEnteredListener)
	{ this.enteredListener = guiEnteredListener; }

	/**
	 * Sets the the {@link GUIExitedListener} for this GUI.<br>
	 * This allows external handling for this specific event.
	 * 
	 * @param guiExitedListener the {@link GUIExitedListener} to set
	 */
	public void setOnMouseExited(GUIExitedListener guiExitedListener)
	{ this.exitedListener = guiExitedListener; }

	/**
	 * Override this method to handle mouse clicks for this GUI.<br>
	 * <br>
	 * This method is called to signal this GUI that the {@link Mouse} has issued a
	 * left click.<br>
	 * For this method to be called three things must occur:<br>
	 * <ul>
	 * <li>The GUI must be enabled, meaning its {@link #guiState} must be set to
	 * {@link GUIState#ENABLED}<br>
	 * </li>
	 * <li>The Mouse cursor must be inside this GUI, meaning the {@link #mouseState}
	 * must be set to {@link GUIMouseState#HOVERED}<br>
	 * </li>
	 * <li>And lastly the Mouse must issue a left click.<br>
	 * </li>
	 * </ul>
	 * All these things are handled automatically by the {@link #update()} method!
	 */
	public abstract void onMouseClicked();

	/**
	 * Override this method to handle if a mouse has entered the GUI.<br>
	 * This method is called automatically by the {@link #update()} method.<br>
	 * For this method to be called the GUI state {@link #guiState} must be set to
	 * {@link GUIState#ENABLED}.
	 */
	public abstract void onMouseEntered();

	/**
	 * Override this method to handle if a mouse has exited the GUI.<br>
	 * This method is called automatically by the {@link #update()} method.<br>
	 * For this method to be called the GUI state {@link #guiState} must be set to
	 * {@link GUIState#ENABLED}.
	 */
	public abstract void onMouseExited();

	/**
	 * Renders the GUI.<br>
	 * How the GUI is rendered is up to the GUI itself.<br>
	 * 
	 * @param render the {@link Renderer} to use for the GUI rendering
	 */
	public abstract void render(Renderer render);

}
