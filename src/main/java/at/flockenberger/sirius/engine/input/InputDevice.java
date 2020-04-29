package at.flockenberger.sirius.engine.input;

public abstract class InputDevice {

	private InputState oldState = InputState.RELEASED;

	protected boolean onClicked(InputState newState) {
		boolean retVal = false;
		if (newState.equals(InputState.PRESSED) && oldState.equals(InputState.RELEASED))
			retVal = true;
		oldState = newState;
		return retVal;
	}

	protected boolean onPressed(InputState newState) {
		return newState.equals(InputState.PRESSED);
	}

	protected boolean onReleased(InputState newState) {
		boolean retVal = false;
		if (newState.equals(InputState.RELEASED) && oldState.equals(InputState.PRESSED))
			retVal = true;
		oldState = newState;
		return retVal;
	}
}
