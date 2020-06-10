package sirius;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.animation.AnimateableValue;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.camera.Camera;
import at.flockenberger.sirius.engine.camera.EntityFixedCamera;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.gui.TestComponent;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
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
	Camera cam;
	Player p;
	SimpleParticleEmitter emitter;
	TestFilter filter;
	Companion comp;
	Text text;
	Color c;
	TestComponent cmp;
	AnimateableValue<Vector2f> vec;
	OtherEntity ent;
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
		ResourceManager manager = Sirius.resMan;
		SLogger.getSystemLogger().debug("TestLayer attached!");

		tex = Texture.createTexture(manager.getImage("texture").resize(32, 32).trimImage());
		tiles = Texture.createTexture(manager.getImage("tiles"));

		p = new Player();
		comp = new Companion(p);
		cam = new EntityFixedCamera(p);
		filter = new TestFilter();
		manager.loadMapResource("mapTest", "/gameart2d-desert.tmx");

		text = new Text("msg");
		c = Color.ORANGE;
		cmp = new TestComponent();
		ent = new OtherEntity();
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
		p.update();
		comp.update();
		cmp.update();
		ent.update();
		cam.update();

	}

	@Override
	public void onInput()
	{

		cam.input();
		p.input();
		comp.input();
		cmp.input();
		ent.input();
		if (Keyboard.isKeyTyped(KeyCode.R))
		{
			ent.getAudioSource().play(Sirius.resMan.getAudio("bdo"));
		}
		
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
		text.setText(pos);
		text.color(c);
		text.draw();

		render.end();
		drawCenter(render);
		ent.render(render);
		cmp.render(render);

		// render.updateMatrix(Sirius.game.getGUICamera());
		// render.beginShape(ShapeType.TRIANGLE);
		// render.color(Color.BRIGHT_ORANGE);
		// render.rect(pos.x, pos.y, p.getWidth(), p.getHeight());
		//
		// render.color(Color.BRIGHT_GREEN);
		// render.circle(0, 0, 100);
		// render.endShape();

	}

	private void drawCenter(Renderer r)
	{
		r.beginShape(ShapeType.LINE);
		r.line(0, Window.getActiveHeight() / 2f, 0, -Window.getActiveHeight() / 2f);
		r.line(Window.getActiveWidth() / 2f, 0, -Window.getActiveWidth() / 2f, 0);
		r.endShape();

	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{
		// pp.applyFilter(filter);
	}

}
