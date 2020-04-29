package sirius;

import at.flockenberger.sirius.engine.resource.ImageResource;
import at.flockenberger.sirius.engine.resource.ResourceManager;

public class ResourceTest {
	
	public static void main(String[] args) {
		
		ImageResource res1 = ResourceManager.get().loadImageResource("windowIcon", "/texture.png");
		assert ResourceManager.get().getImageResource("windowIcon") != null;
		
		ImageResource res2 =ResourceManager.get().loadImageResource("windowIcon", "/texture.png");
		assert res1 == res2;
		
		ResourceManager.get().getImageResource("test");
		ResourceManager.get().getImageResource(null);
		
	}

}
