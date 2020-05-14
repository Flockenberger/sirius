package sirius;

import java.awt.Font;

import org.joml.Vector3f;

import at.flockenberger.sirius.engine.Camera;
import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.SiriusFont;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.particle.ParticleSystem;
import at.flockenberger.sirius.engine.particle.SimpleParticleEmitter;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.engine.texture.Texture;
import at.flockenberger.sirius.game.application.LayerBase;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class TestLayer extends LayerBase
{

	Texture tex;
	Texture fontTex;
	Texture tiles;
	Camera cam;

	SiriusFont font;
	ParticleSystem system;
	SimpleParticleEmitter emitter;

	public TestLayer(String layerName)
	{
		super(layerName);
	}

	@Override
	public void free()
	{

	}

	@Override
	public void attach()
	{
		SLogger.getSystemLogger().debug("TestLayer attached!");
		ResourceManager.get().loadImageResource("texture", "/texture.jpg");
		tex = Texture.createTexture(ResourceManager.get().getImage("texture").resize(32, 32));
		font = new SiriusFont(Font.getFont(Font.SANS_SERIF));
		tiles = Texture.createTexture(ResourceManager.get().loadImageResource("tiles", "/tiles.png").getImage());
		system = new ParticleSystem();
		system.setMaxParticles(10_000);
		emitter = new SimpleParticleEmitter(10, new Vector3f(400, 300, 0));
		system.addEmitter(emitter);
		cam = new Camera();

	}

	@Override
	public void detach()
	{
		SLogger.getSystemLogger().debug("TestLayer detached!");
	}

	@Override
	public void onUpdate(float ft)
	{

	}

	@Override
	public void onUpdate()
	{
		system.update();
		cam.update();
	}

	@Override
	public void onInput()
	{
		if (Mouse.isLeftButtonDown())
		{
			float x = (float) Mouse.getX();
			float y = (float) Mouse.getY();
			emitter.setPosition(new Vector3f(x, y, 0));
		}

		cam.input();
	}

	@Override
	public void onRender(Renderer render)
	{
		onRender(render, 1f);
	}

	@Override
	public void onRender(Renderer render, float alpha)
	{
		render.clear(Color.PINK);

		render.begin(cam);
		system.render(render);
		render.end();

		render.begin(cam);
		tex.bind();
		for (int i = 0; i < Window.getActiveWidth(); i += 32)
			for (int j = 0; j < Window.getActiveHeight(); j += 32)
				render.drawTexture(tex, i, j);
		render.end();

		render.begin(cam);
		tiles.bind();
		render.drawTexture(tiles, 100, 100);
		render.end();

		render.begin(cam);
		render.drawText("Hello World", 10, 10, Color.BRIGHT_BLUE);
		render.end();
	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{
		
	}

}
