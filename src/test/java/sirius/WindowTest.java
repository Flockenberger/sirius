package sirius;

import java.net.URISyntaxException;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.graphic.Cursor;
import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.resource.ImageResource;
import at.flockenberger.sirius.engine.resource.ResourceManager;

public class WindowTest
{

	public static void main(String[] args) throws URISyntaxException
	{

		ImageResource imgicon = ResourceManager.get().loadImageResource("windowIcon", "/texture.png");

		Icon ico = new Icon(imgicon.getImage());
		Window window = new Window();

		window.show();
		window.setIcon(ico);
		Mouse.get().setCursor(new Cursor(imgicon.getImage().resize(32, 32)));

		while (!window.askClose())
		{
			window.update();
			
			if (Keyboard.get().isKeyReleased(KeyCode.A))
			{
				System.out.println("A");
			} else if (Keyboard.get().isKeyPressed(KeyCode.B))
				System.out.println("pressed");
			if (Mouse.get().isLeftButtonClicked())
			{
				System.out.println("clicked");
			}
		}
		window.free();
	}

}
