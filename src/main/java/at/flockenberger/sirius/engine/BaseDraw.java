package at.flockenberger.sirius.engine;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>BaseDraw</h1> <br>
 * The BaseDraw class is able to draw basic objects into the view.
 * 
 * @author Florian Wagner
 *
 */

public class BaseDraw
{

	private Color penColor;
	private int centerX;
	private int centerY;

	private int width;
	private int height;

	private double xmin = 0;
	private double ymin = 0;
	private double xmax = 1;
	private double ymax = 1;

	public BaseDraw()
	{
		this.penColor = new Color(1, 0, 0);
		this.usePenColor();
		this.centerX = 0;
		this.centerY = 0;

	}

	public BaseDraw(int w, int h)
	{
		this();
		this.width = w;
		this.height = h;
	}

	/**
	 * Draws a line from x1y1 to x2y2
	 * 
	 * @param x1 the x coordinate of the first point
	 * @param y1 the y coordinate of the first point
	 * @param x2 the x coordinate of the second point
	 * @param y2 the y coordinate of the second point
	 */
	public void drawLine(float x1, float y1, float x2, float y2)
	{
		usePenColor();
		GL20.glBegin(GL15.GL_LINES);
		setVertexLine(x1, y1, x2, y2);
		GL20.glEnd();
	}

	/**
	 * Draws a rectangle between the two given coordinates.
	 * 
	 * @param x1 the x coordinate of the upper left corner
	 * @param y1 the y coordinate of the upper right corner
	 * @param x2 the x coordinate of the bottom right corner
	 * @param y2 the y coordinate of the bottom right corner
	 */
	public void drawRectangle(float x1, float y1, float x2, float y2)
	{
		usePenColor();

		GL20.glBegin(GL15.GL_LINES);
		setVertexLine(x1, y1, x2, y1);
		setVertexLine(x2, y1, x2, y2);
		setVertexLine(x1, y1, x1, y2);
		setVertexLine(x1, y2, x2, y2);
		GL20.glEnd();

	}

	/**
	 * Draws a filled rectangle with the current {@link #penColor}.
	 * 
	 * @param x1 the x coordinate of the upper left corner
	 * @param y1 the y coordinate of the upper right corner
	 * @param x2 the x coordinate of the bottom right corner
	 * @param y2 the y coordinate of the bottom right corner
	 */
	public void drawFilledRectangle(float x1, float y1, float x2, float y2)
	{
		usePenColor();
		GL20.glBegin(GL15.GL_TRIANGLE_STRIP);

		setVertexLine(x1, y1, x2, y1);
		setVertexLine(x2, y1, x2, y2);
		setVertexLine(x1, y1, x1, y2);
		GL20.glEnd();

	}

	/**
	 * Draws a 2D circle into the view.
	 * 
	 * @param cx           the center x position
	 * @param cy           the center y position
	 * @param r            the radius
	 * @param num_segments the number of segments of the circle
	 */
	public void drawCircle(float cx, float cy, float r, int num_segments)
	{
		usePenColor();
		GL20.glBegin(GL15.GL_LINE_LOOP);
		for (int ii = 0; ii < num_segments; ii++)
		{
			float theta = (float) (2.0f * Math.PI * (float) ii / (float) num_segments);// get the current angle

			float x = (float) (r * Math.cos(theta));// calculate the x component
			float y = (float) (r * Math.sin(theta));// calculate the y component

			setVertex(x + cx, y + cy, 0);// output vertex

		}
		GL20.glEnd();
	}

	/**
	 * Draws a filled 2D circle into the view.
	 * 
	 * @param cx           the center x position
	 * @param cy           the center y position
	 * @param r            the radius
	 * @param num_segments the number of segments of the circle
	 */
	public void drawFilledCircle(float cx, float cy, float r, int num_segments)
	{
		usePenColor();
		GL20.glBegin(GL15.GL_POLYGON);
		for (int ii = 0; ii < num_segments; ii++)
		{
			float theta = (float) (2.0f * Math.PI * (float) ii / (float) num_segments);// get the current angle

			float x = (float) (r * Math.cos(theta));// calculate the x component
			float y = (float) (r * Math.sin(theta));// calculate the y component

			setVertex(x + cx, y + cy, 0);// output vertex

		}
		GL20.glEnd();
	}

	/**
	 * Draws an 2D arc with the current pen color into the view.F
	 * 
	 * @param cx           the center x coordinate of the arc
	 * @param cy           the center y coordinate of the arc
	 * @param r            the radius of the arc
	 * @param start_angle  the starting angle
	 * @param arc_angle    the ending angle
	 * @param num_segments the amount of segments
	 */
	public void drawArc(float cx, float cy, float r, float start_angle, float arc_angle, int num_segments)
	{
		usePenColor();
		arc_angle = SUtils.degToRad(arc_angle);
		start_angle = SUtils.degToRad(start_angle);

		float theta = arc_angle / (float) (num_segments - 1);
		float tangetial_factor = (float) Math.tan(theta);
		float radial_factor = (float) Math.cos(theta);
		float x = (float) (r * Math.cos(start_angle));
		float y = (float) (r * Math.sin(start_angle));

		GL20.glBegin(GL15.GL_LINE_STRIP);
		for (int ii = 0; ii < num_segments; ii++)
		{
			setVertex(x + cx, y + cy, 0);

			float tx = -y;
			float ty = x;

			x += tx * tangetial_factor;
			y += ty * tangetial_factor;

			x *= radial_factor;
			y *= radial_factor;
		}
		GL20.glEnd();
	}

	/**
	 * Draws an 2D arc with the current pen color into the view.F
	 * 
	 * @param cx           the center x coordinate of the arc
	 * @param cy           the center y coordinate of the arc
	 * @param r            the radius of the arc
	 * @param start_angle  the starting angle
	 * @param arc_angle    the ending angle
	 * @param num_segments the amount of segments
	 */
	public void drawFilledArc(float cx, float cy, float r, float start_angle, float arc_angle, int num_segments)
	{
		usePenColor();
		arc_angle = SUtils.degToRad(arc_angle);
		start_angle = SUtils.degToRad(start_angle);
		float theta = arc_angle / (float) (num_segments - 1);
		float tangetial_factor = (float) Math.tan(theta);
		float radial_factor = (float) Math.cos(theta);
		float x = (float) (r * Math.cos(start_angle));
		float y = (float) (r * Math.sin(start_angle));

		GL20.glBegin(GL15.GL_POLYGON);
		for (int ii = 0; ii < num_segments; ii++)
		{
			setVertex(x + cx, y + cy, 0);

			float tx = -y;
			float ty = x;

			x += tx * tangetial_factor;
			y += ty * tangetial_factor;

			x *= radial_factor;
			y *= radial_factor;
		}
		GL20.glEnd();
	}

	/**
	 * @return the current pen color
	 */
	public Color getPenColor()
	{
		return penColor;
	}

	/**
	 * The pen color is the color used to draw
	 * 
	 * @param penColor the pen color to set
	 */
	public void setPenColor(Color penColor)
	{
		this.penColor = penColor;

	}

	public void setCenterX(int x)
	{
		this.centerX = x;
	}

	public void setCenterY(int y)
	{
		this.centerY = y;
	}

	public void translateCenter()
	{
		setCenterX(width / 2);
		setCenterY(height / 2);
	}

	public void clear(Color black)
	{
		GL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		GL20.glClearColor(black.getRed(), black.getGreen(), black.getBlue(), black.getAlpha());

	}

	public void setWidth(int w)
	{
		this.width = w;
	}

	public void setHeight(int h)
	{
		this.height = h;
	}

	public void setScaleX(double min, double max)
	{
		double size = max - min;
		if (size == 0.0)
			throw new IllegalArgumentException("the min and max are the same");

		xmin = min * size;
		xmax = max * size;

	}

	public void setScaleY(double min, double max)
	{
		double size = max - min;
		if (size == 0.0)
			throw new IllegalArgumentException("the min and max are the same");
		ymin = min * size;
		ymax = max * size;

	}

	private void setVertex(float x1, float y1, float z1)
	{
		// System.out.println(x1);
		// double x = CardinalUtils.map(x1, 0, width, -1, 1);
		// double y = CardinalUtils.map(y1, 0, height, -1, 1);

		GL20.glVertex3d(x1, y1, z1);
	}

	private void setVertexLine(float x1, float y1, float x2, float y2)
	{
		setVertex(x1, y1, 0);
		setVertex(x2, y2, 0);
	}

	private void usePenColor()
	{
		GL20.glColor4fv(penColor.toFloatArray());
	}

}
