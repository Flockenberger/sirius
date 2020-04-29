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
		ImageResource cursor = ResourceManager.get().loadImageResource("cursor", "/cursor.png");

		WindowIcon ico = new WindowIcon(imgicon.getImage());

		Window window = new Window(800, 600, "Test Window");
		window.setIcon(ico);
		window.show();

		Keyboard k = Keyboard.get();
		Mouse m = Mouse.get();
		Cursor c = new Cursor(cursor.getImage().resize(32, 32));

		boolean toggle = false;

		while (!window.askClose()) {
			window.update();

			if (k.isKeyTyped(KeyCode.A)) {
				System.out.println("key A typed!");
			}
			
			if (m.isLeftButtonReleased()) {
				if (toggle)
					m.setCursor(null);
				else
					m.setCursor(c);
				toggle = !toggle;
			}
		}
		window.free();
	}

}
