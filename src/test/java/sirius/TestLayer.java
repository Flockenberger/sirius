package sirius;

import java.util.ArrayList;
import java.util.List;

import at.flockenberger.sirius.engine.PlayerFixedCamera;
import at.flockenberger.sirius.engine.RenderSettings;
import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.animation.AnimationMode;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.particle.SimpleParticleEmitter;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.postprocess.TestFilter;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.game.Player;
import at.flockenberger.sirius.game.application.LayerBase;
import at.flockenberger.sirius.map.GameLevel;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class TestLayer extends LayerBase
{

	Texture tex;
	Texture tiles;
	PlayerFixedCamera cam;
	Player p;
	SimpleParticleEmitter emitter;
	TestFilter filter;
	Companion comp;
	Text text;
	Color c;

	Animation<String> posAni;

	public TestLayer(String layerName)
	{
		super(layerName);
	}

	@Override
	public void free()
	{
		tex.free();
		tiles.free();
		p.free();

	}

	@Override
	public void attach()
	{
		SLogger.getSystemLogger().debug("TestLayer attached!");
		ResourceManager.get().loadImageResource("texture", "/texture.jpg");
		ResourceManager.get().loadImageResource("sheet", "/sprite-animation1.png");
		tex = Texture.createTexture(ResourceManager.get().getImage("texture").resize(32, 32));
		tiles = Texture.createTexture(ResourceManager.get().loadImageResource("tiles", "/tiles.png").getImage());
		p = new Player();
		comp = new Companion(p);

		cam = new PlayerFixedCamera(p);

		filter = new TestFilter();

		ResourceManager.get().loadMapResource("mapTest", "/gameart2d-desert.tmx");
		List<String> keyframes = new ArrayList<>();
		keyframes.add("Flo");
		keyframes.add("Ana");
		keyframes.add("Po");

		posAni = new Animation<String>(0.002f, keyframes);
		posAni.setAnimationMode(AnimationMode.LOOP);
		text = new Text("msg");
		c = Color.ORANGE;

	}

	@Override
	public void detach()
	{
		SLogger.getSystemLogger().debug("TestLayer detached!");
	}

	@Override
	public void onUpdate(float ft)
	{
		p.update(ft);
		comp.update(ft);
	}

	@Override
	public void onUpdate()
	{
		cam.update();
		p.update();
		comp.update();
	}

	@Override
	public void onInput()
	{

		cam.input();
		p.input();
		comp.input();
	}

	@Override
	public void onRender(Renderer render)
	{
		onRender(render, 1f);
	}

	@Override
	public void onRender(Renderer render, float alpha)
	{
		render.clear(Sirius.renderSettings.getColor(RenderSettings.BACKGROUND));

		render.updateMatrix(cam);

		render.begin();

		/*
		 * int size = 32; int hSize = size / 2; for (int i = 0; i <
		 * Window.getActiveWidth(); i += size) for (int j = 0; j <
		 * Window.getActiveHeight(); j += size) { render.draw(tex, i, j, hSize, hSize,
		 * size, size, 1, 1, (float) i + j); // kk++; }
		 */
		//
		// render.end();
		// text.setPosition(0, -text.getTextHeight());
		// text.setText("Drawing: " + kk + "Quads");
		// text.draw();

		// render.begin();
		// render.draw(tiles, 0, 0, 182, 128, 256, 256, 1, 1,
		// (float)Math.sin(Sirius.timer.getTime())*90);

		// render.end();

		// render.begin();
		// render.drawText("Hello World", 10, 10, Color.RED);
		// render.end();
		p.render(render);
		comp.render(render);

		text.setText(posAni.getNextFrame());
		text.setColor(c);
		text.draw();
	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{
		// pp.applyFilter(filter);
	}

}
