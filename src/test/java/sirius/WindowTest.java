package sirius;

import java.net.URISyntaxException;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.input.MouseButton;
import at.flockenberger.sirius.engine.resource.ImageResource;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.graphic.Cursor;
import at.flockenberger.sirius.graphic.WindowIcon;

public class WindowTest {

	public static void main(String[] args) throws URISyntaxException {

		ImageResource imgicon = ResourceManager.get().loadImageResource("windowIcon", "/texture.png");
		
		WindowIcon ico = new WindowIcon(imgicon.getImage());
		Window window = new Window();
		window.setIcon(ico);
		window.show();

		while (!window.askClose()) {
			window.update();

		}
		window.free();
	}

}
