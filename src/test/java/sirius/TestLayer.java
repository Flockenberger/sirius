package sirius;

import java.util.ArrayList;
import java.util.List;

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
import at.flockenberger.sirius.engine.gui.GUIButton;
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
	GUIButton cmp;
	AnimateableValue<Vector2f> vec;
	OtherEntity ent;
	Animation<String> posAni;
	Texture background;
	List<OtherEntity> others = new ArrayList<>();

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
		for (OtherEntity oe : others)
			oe.free();

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
		cam = new EntityFixedCamera(p, 0.05f);
		filter = new TestFilter();

		text = new Text("msg");
		c = Color.ORANGE;
		cmp = new GUIButton();
		ent = new OtherEntity();

		cmp.setOnMouseClicked(gui ->
			{

				p.jump();
			});
		// Gamepad gp = Gamepad.get(Gamepads.GAMEPAD_1);
		// background = Texture.createTexture(Sirius.resMan.getImage("hsd"));
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

		p.collision(ent);
		for (OtherEntity oe : others)
		{
			oe.update();
			p.collision(oe);

		}

	}

	@Override
	public void onInput()
	{

		cam.input();
		p.input();
		comp.input();
		ent.input();
		// for (OtherEntity oe : others)
		// oe.input();

		if (Keyboard.get().isKeyReleased(KeyCode.R))
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

		// draw background
		if (background != null)
		{
			render.updateMatrix(Sirius.game.getGUICamera());
			render.begin();
			render.draw(background, 0, 0, 0, 0, Window.getActiveWidth(), Window.getActiveHeight(), 1, 1, 0);
			render.end();
		}

		render.begin();
		render.updateMatrix(cam);

		Vector2f pos = p.getPosition();
		text.setText(pos);
		text.color(c);
		text.draw();

		p.render(render);
		comp.render(render);

		for (OtherEntity oe : others)
			oe.render(render);

		ent.render(render);
		cmp.render(render);

		render.end();

		drawCenter(render);
	}

	private void drawCenter(Renderer r)
	{
		if (r.isDrawing())
			r.end();
		r.beginShape(ShapeType.LINE);
		r.color(Color.ORANGE);
		r.line(0, Window.getActiveHeight() / 2f, 0, -Window.getActiveHeight() / 2f);
		r.line(Window.getActiveWidth() / 2f, 0, -Window.getActiveWidth() / 2f, 0);
		r.endShape();
		r.begin();

	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{
		// pp.applyFilter(filter);
	}

}
