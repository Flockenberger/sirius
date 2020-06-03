package sirius;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.animation.AnimateableValue;
import at.flockenberger.sirius.engine.animation.AnimateableVector2f;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.animation.AnimationMode;
import at.flockenberger.sirius.engine.camera.EntityFixedCamera;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.particle.SimpleParticleEmitter;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.postprocess.TestFilter;
import at.flockenberger.sirius.engine.render.RenderSettings;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.engine.render.Renderer.ShapeType;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.game.application.LayerBase;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class TestLayer extends LayerBase
{

	Texture tex;
	Texture tiles;
	EntityFixedCamera cam;
	Player p;
	SimpleParticleEmitter emitter;
	TestFilter filter;
	Companion comp;
	Text text;
	Color c;

	AnimateableValue<Vector2f> vec;

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

		cam = new EntityFixedCamera(p);

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
		vec = new AnimateableVector2f(new Vector2f(0, 0), new Vector2f(10, 10), 500f);

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
		vec.update(ft);
	}

	@Override
	public void onUpdate()
	{
		cam.update();
		p.update();
		comp.update();
		if (Sirius.timer.getFPS() >= 55)
			vec.update(Sirius.timer.getDelta());
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
		p.render(render);
		comp.render(render);
		Vector2f pos = p.getPosition();

		// text.position(vec.get());
		text.setText(p.getPosition());

		text.color(c);
		text.draw();

		// render.drawColor(0, 0, 100, 100, Color.BRIGHT_ORANGE);
		render.end();

		// render.updateMatrix(Sirius.game.getGUICamera());
		render.beginShape(ShapeType.TRIANGLE);
		render.color(Color.BRIGHT_ORANGE);
		render.rect(pos.x, pos.y, p.getWidth(), p.getHeight());

		render.color(Color.BRIGHT_GREEN);
		render.circle(0, 0, 100);
		render.endShape();

	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{
		// pp.applyFilter(filter);
	}

}
