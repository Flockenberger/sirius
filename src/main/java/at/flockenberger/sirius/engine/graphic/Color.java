package at.flockenberger.sirius.engine.graphic;

import java.io.Serializable;

import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>Color</h1><br>
 * The color class stores rgba values as floats.<br>
 * 
 * 
 * @author Florian Wagner
 *
 */
public class Color implements Serializable {

	private static final long serialVersionUID = -9079657496497537564L;

	// i am not sure if I might want to change these to double later on
	private float red;
	private float green;
	private float blue;
	private float alpha;

	// darken, brighten as well as saturate, de-saturate factor
	private final float FACTOR = 0.6F;

	/*
	 * some colors I thought look good pretty :) going to add more in the future
	 */

	public static Color random() {
		return new Color(Math.random(), Math.random(), Math.random());
	}
	
	public static final Color WHITE = new Color(1, 1, 1);

	public static final Color BLACK = new Color(0, 0, 0);

	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	public static final Color RED = Color.rgb(247, 55, 60);

	public static final Color BRIGHT_RED = Color.rgb(254, 134, 137);

	public static final Color DARK_RED = Color.rgb(224, 12, 17);

	public static final Color GREEN = Color.rgb(128, 222, 50);

	public static final Color BRIGHT_GREEN = Color.rgb(178, 239, 127);

	public static final Color DARK_GREEN = Color.rgb(97, 201, 11);

	public static final Color LIME = Color.rgb(181, 233, 55);

	public static final Color BRIGHT_LIME = Color.rgb(214, 247, 133);

	public static final Color DARK_LIME = Color.rgb(154, 211, 14);

	public static final Color YELLOW = Color.rgb(247, 243, 58);

	public static final Color BRIGHT_YELLOW = Color.rgb(255, 253, 137);

	public static final Color DARK_YELLOW = Color.rgb(224, 220, 15);

	public static final Color PINK = Color.rgb(215, 51, 111);

	public static final Color BRIGHT_PINK = Color.rgb(236, 127, 168);

	public static final Color DARK_PINK = Color.rgb(195, 13, 80);

	public static final Color PURPLE = Color.rgb(157, 38, 161);

	public static final Color BRIGHT_PURPLE = Color.rgb(202, 111, 205);

	public static final Color DARK_PURPLE = Color.rgb(141, 10, 146);

	public static final Color VIOLET = Color.rgb(105, 49, 167);

	public static final Color BRIGHT_VIOLET = Color.rgb(161, 120, 209);

	public static final Color DARK_VIOLET = Color.rgb(82, 21, 152);

	public static final Color TURQUOISE = Color.rgb(35, 148, 148);

	public static final Color BRIGHT_TURQUOISE = Color.rgb(106, 198, 198);

	public static final Color DARK_TURQUOISE = Color.rgb(9, 134, 134);

	public static final Color BROWN = Color.rgb(247, 144, 58);

	public static final Color BRIGHT_BROWN = Color.rgb(255, 191, 137);

	public static final Color DARK_BROWN = Color.rgb(224, 110, 15);

	public static final Color BLUE = Color.rgb(52, 86, 165);

	public static final Color BRIGHT_BLUE = Color.rgb(122, 148, 208);

	public static final Color DARK_BLUE = Color.rgb(25, 63, 150);

	public static final Color ORANGE = Color.rgb(249, 153, 56);

	public static final Color BRIGHT_ORANGE = Color.rgb(255, 195, 135);

	public static final Color DARK_ORANGE = Color.rgb(226, 120, 12);

	/**
	 * Creates a new Color with a gray value
	 * 
	 * @param gray a value between 0-255.
	 * @return a new gray color.
	 */
	public static Color grayRgb(int gray) {
		return rgb(gray, gray, gray);
	}

	/**
	 * Creates a new rgb color.<br>
	 * r, g, b have to be between 0-255
	 * 
	 * @param red   red channel
	 * @param green green channel
	 * @param blue  blue channel
	 * @return a new color
	 */
	public static Color rgb(int red, int green, int blue) {
		checkRGB(red, green, blue);
		return new Color(red / (float) 255.0, green / (float) 255.0, blue / (float) 255.0);
	}

	/**
	 * Creates a new rgb color.<br>
	 * r, g, b have to be between 0-255
	 * 
	 * @param red   red channel
	 * @param green green channel
	 * @param blue  blue channel
	 * @return a new color
	 */
	public static Color rgb(byte red, byte green, byte blue) {
		checkRGB(red, green, blue);
		return new Color(red / (float) 255.0, green / (float) 255.0, blue / (float) 255.0);
	}

	/**
	 * Creates a new rgb color.<br>
	 * r, g, b a have to be between 0-255
	 * 
	 * @param r red channel
	 * @param g green channel
	 * @param b blue channel
	 * @param a the opacity component
	 */
	public Color(byte r, byte g, byte b, byte a) {
		this(r / (float) 255.0, g / (float) 255.0, b / (float) 255.0, a / (float) 255.0);
	}

	/**
	 * Sets the color values.
	 * 
	 * @param r red channel
	 * @param g green channel
	 * @param b blue channel
	 * @param a the opacity component
	 */
	public void set(float r, float g, float b, float a) {
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(a);
	}

	/**
	 * Sets the color values.
	 * 
	 * @param r red channel
	 * @param g green channel
	 * @param b blue channel
	 * @param a the opacity component
	 */
	public void set(int r, int g, int b, int a) {
		checkRGB(r, g, b);
		set(r / (float) 255.0, g / (float) 255.0, b / (float) 255.0, a / (float) 255.0);
	}

	/**
	 * Sets the color values.
	 * 
	 * @param r red channel
	 * @param g green channel
	 * @param b blue channel
	 */
	public void set(int r, int g, int b) {
		checkRGB(r, g, b);
		setRed(r / (float) 255.0);
		setGreen(g / (float) 255.0);
		setBlue(b / (float) 255.0);
	}

	/**
	 * Creates a new gray color.
	 * 
	 * @param gray between 0-1
	 */
	public Color(int gray) {
		this(gray, gray, gray);
	}

	/**
	 * Creates a new RGB Color. The values for <code> r, g, b </code> need to be in
	 * space of 0-1
	 * 
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 */
	private Color(float r, float g, float b) {
		this(r, g, b, 1.0f);

	}

	/**
	 * Creates a new RGB Color. The values for <code> r, g, b, opacity </code> need
	 * to be in space of 0-1
	 * 
	 * @param r       the red component
	 * @param g       the green component
	 * @param b       the blue component
	 * @param opacity the opacity component
	 */
	public Color(float r, float g, float b, float opacity) {
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = 1.0f;

	}

	/**
	 * Creates a new RGB Color. The values for <code> r, g, b, a </code> need to be
	 * in space of 0-255
	 * 
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 * @param a the opacity component
	 */
	public Color(int r, int g, int b, int a) {
		checkRGB(r, g, b);
		setRed(r / (float) 255.0);
		setGreen(g / (float) 255.0);
		setBlue(b / (float) 255.0);
		setAlpha(a / (float) 255.0);
	}

	/**
	 * Creates a new RGB Color. The values for <code> r, g, b </code> need to be in
	 * space of 0-1
	 * 
	 * @param r the red component
	 * @param g the green component
	 * @param b the blue component
	 */
	public Color(double r, double g, double b) {
		this.red = (float) r;
		this.green = (float) g;
		this.blue = (float) b;
		this.alpha = 1.0f;

	}

	/**
	 * Creates a new White Color.
	 */
	public Color() {
		this(1, 1, 1);
	}

	/**
	 * Creates a new RGB Color. The values for <code> r, g, b, opacity </code> need
	 * to be in space of 0-1
	 * 
	 * @param r     the red component
	 * @param g     the green component
	 * @param b     the blue component
	 * @param alpha the opacity component
	 */
	public Color(double d, double e, double f, double g) {
		this.red = (float) d;
		this.green = (float) e;
		this.blue = (float) f;
		this.alpha = (float) g;
	}

	/**
	 * @return Hue component in the range in the range {@code 0.0-360.0}.
	 */
	public double getHue() {
		return RGBtoHSB_(red, green, blue)[0];
	}

	/**
	 * @return Saturation component in the range in the range {@code 0.0-1.0}.
	 */
	public double getSaturation() {
		return RGBtoHSB_(red, green, blue)[1];
	}

	/**
	 * @return Brightness component in the range in the range {@code 0.0-1.0}.
	 */
	public double getBrightness() {
		return RGBtoHSB_(red, green, blue)[2];
	}

	/**
	 * @return the red channel of this color
	 */
	public float getRed() {
		return red;
	}

	/**
	 * @return the green channel of this color
	 */
	public float getGreen() {
		return green;
	}

	/**
	 * @return the blue channel of this color
	 */
	public float getBlue() {
		return blue;
	}

	/**
	 * @return the alpha channel of this color
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * Creates a new HSB Color.
	 * 
	 * @param hue        the hue of the color
	 * @param saturation the saturation between 0-1
	 * @param brightness the brightness between 0-1
	 * @return a new Color with these values
	 */
	public static Color hsb(double hue, double saturation, double brightness) {
		checkSB(saturation, brightness);
		return HSBtoRGB(hue, saturation, brightness);
	}

	/**
	 * Creates a new HSB Color.
	 * 
	 * @param hue        the hue of the color
	 * @param saturation the saturation between 0-1
	 * @param brightness the brightness between 0-1
	 * @return a new Color with these values
	 */
	public static Color hsb(double hue, double saturation, double brightness, double opacity) {
		checkSB(saturation, brightness);
		Color c = HSBtoRGB(hue, saturation, brightness);
		c.setAlpha((float) opacity);
		return c;
	}

	/**
	 * Sets the red component of this color
	 * 
	 * @param red the red component
	 */
	public void setRed(float red) {
		red = SUtils.putInBounds(red, 0, 1);
		this.red = red;
	}

	/**
	 * Sets the green component of this color
	 * 
	 * @param green the green component
	 */
	public void setGreen(float green) {
		green = SUtils.putInBounds(green, 0, 1);
		this.green = green;
	}

	/**
	 * Sets the blue component of this color
	 * 
	 * @param blue the blue component
	 */
	public void setBlue(float blue) {
		blue = SUtils.putInBounds(blue, 0, 1);
		this.blue = blue;
	}

	/**
	 * Sets the opacity component of this color
	 * 
	 * @param opacity the opacity component
	 */
	public void setAlpha(float opacity) {
		opacity = SUtils.putInBounds(opacity, 0, 1);
		this.alpha = opacity;
	}

	/**
	 * @return a Color that is brighter than this Color
	 */
	public Color brighter() {
		return brighter(FACTOR);
	}

	/**
	 * @return a Color that is darker than this Color
	 */
	public Color darker() {
		return darker(FACTOR);
	}

	/**
	 * @return a Color that is more saturated than this Color
	 */
	public Color saturate() {
		return saturate(FACTOR);
	}

	/**
	 * @return a Color that is less saturated than this Color
	 */
	public Color desaturate() {
		return desaturate(FACTOR);
	}

	/**
	 * Returns a Color that is brighter than this Color
	 * 
	 * @param factor the color factor, must be between 0-1
	 * @return a Color that is brighter than this Color
	 */
	public Color brighter(float factor) {
		factor = SUtils.putInBounds(factor, 0, 1);
		return deriveColor(0, 1.0, 1.0 / factor, 1.0);
	}

	/**
	 * Returns a Color that is darker than this Color
	 * 
	 * @param factor the color factor, must be between 0-1
	 * @return a Color that is darker than this Color
	 */
	public Color darker(float factor) {
		factor = SUtils.putInBounds(factor, 0, 1);
		return deriveColor(0, 1.0, factor, 1.0);
	}

	/**
	 * Returns a Color that is more saturated than this Color
	 * 
	 * @param factor the color factor, must be between 0-1
	 * @return a Color that is more saturated than this Color
	 */
	public Color saturate(float factor) {
		factor = SUtils.putInBounds(factor, 0, 1);
		return deriveColor(0, 1.0 / factor, 1.0, 1.0);
	}

	/**
	 * Returns a Color that is less saturated than this Color
	 * 
	 * @param factor the color factor, must be between 0-1
	 * @return a Color that is less saturated than this Color
	 */
	public Color desaturate(float factor) {
		factor = SUtils.putInBounds(factor, 0, 1);
		return deriveColor(0, factor, 1.0, 1.0);
	}

	/**
	 * Interpolates between two colors
	 * 
	 * @param endValue the end color to interpolate
	 * @param t        the strength
	 * @return the newly interpolated color
	 */
	public Color interpolate(Color endValue, double t) {
		if (t <= 0.0)
			return this;
		if (t >= 1.0)
			return endValue;
		float ft = (float) t;
		return new Color(red + (endValue.red - red) * ft, green + (endValue.green - green) * ft,
				blue + (endValue.blue - blue) * ft);
	}

	/**
	 * Converts a HSB Color to RGB.
	 * 
	 * @param hue        the hue of the color to convert
	 * @param saturation the saturation of the color to convert
	 * @param brightness the brightness of the color to convert
	 * @return an array of double with size 3 representing RGB
	 */
	public static double[] HSBtoRGB_(double hue, double saturation, double brightness) {
		// normalize the hue
		double normalizedHue = ((hue % 360) + 360) % 360;
		hue = normalizedHue / 360;

		double r = 0, g = 0, b = 0;
		if (saturation == 0) {
			r = g = b = brightness;
		} else {
			double h = (hue - Math.floor(hue)) * 6.0;
			double f = h - java.lang.Math.floor(h);
			double p = brightness * (1.0 - saturation);
			double q = brightness * (1.0 - saturation * f);
			double t = brightness * (1.0 - (saturation * (1.0 - f)));
			switch ((int) h) {
			case 0:
				r = brightness;
				g = t;
				b = p;
				break;
			case 1:
				r = q;
				g = brightness;
				b = p;
				break;
			case 2:
				r = p;
				g = brightness;
				b = t;
				break;
			case 3:
				r = p;
				g = q;
				b = brightness;
				break;
			case 4:
				r = t;
				g = p;
				b = brightness;
				break;
			case 5:
				r = brightness;
				g = p;
				b = q;
				break;
			}
		}
		double[] f = new double[3];
		f[0] = r;
		f[1] = g;
		f[2] = b;
		return f;
	}

	/**
	 * Converts HSB values to a {@link Color} with RGB.
	 * 
	 * @param hue        the hue of the color to convert
	 * @param saturation the saturation of the color to convert
	 * @param brightness the brightness of the color to convert
	 * @return a color representing the RGB values
	 */
	public static Color HSBtoRGB(double hue, double saturation, double brightness) {
		double[] rgb = HSBtoRGB_(hue, saturation, brightness);
		return new Color(rgb[0], rgb[1], rgb[2], 1D);
	}

	/**
	 * Converts a RGB Color to HSB.
	 * 
	 * @param r the red component of the color
	 * @param g the green component of the color
	 * @param b the blue component of the color
	 * @return an array of size 3 with the HSV values
	 */
	public static double[] RGBtoHSB_(double r, double g, double b) {
		double hue, saturation, brightness;
		double[] hsbvals = new double[3];
		double cmax = (r > g) ? r : g;
		if (b > cmax)
			cmax = b;
		double cmin = (r < g) ? r : g;
		if (b < cmin)
			cmin = b;

		brightness = cmax;
		if (cmax != 0)
			saturation = (double) (cmax - cmin) / cmax;
		else
			saturation = 0;

		if (saturation == 0) {
			hue = 0;
		} else {
			double redc = (cmax - r) / (cmax - cmin);
			double greenc = (cmax - g) / (cmax - cmin);
			double bluec = (cmax - b) / (cmax - cmin);
			if (r == cmax)
				hue = bluec - greenc;
			else if (g == cmax)
				hue = 2.0 + redc - bluec;
			else
				hue = 4.0 + greenc - redc;
			hue = hue / 6.0;
			if (hue < 0)
				hue = hue + 1.0;
		}
		hsbvals[0] = hue * 360;
		hsbvals[1] = saturation;
		hsbvals[2] = brightness;
		return hsbvals;
	}

	/**
	 * Converts a RGB Color to HSB.
	 * 
	 * @param r the red component of the color
	 * @param g the green component of the color
	 * @param b the blue component of the color
	 * @return the converted Color
	 */
	public static Color RGBtoHSB(double r, double g, double b) {
		double[] hsv = RGBtoHSB_(r, g, b);
		return Color.hsb(hsv[0], hsv[1], hsv[2]);
	}

	/**
	 * @return a float array with r, g, b, a stored inside
	 */
	public float[] toFloatArray() {
		return new float[] { (float) getRed(), (float) getGreen(), (float) getBlue(), (float) getAlpha() };
	}

	/**
	 * @return a double array with r, g, b, a stored inside
	 */
	public double[] toDoubleArray() {
		double[] f = { getRed(), getGreen(), getBlue(), getAlpha() };
		return f;
	}

	private Color deriveColor(double hueShift, double saturationFactor, double brightnessFactor, double opacityFactor) {

		double[] hsb = RGBtoHSB_(red, green, blue);

		/* Allow brightness increase of black color */
		double b = hsb[2];
		if (b == 0 && brightnessFactor > 1.0) {
			b = 0.05;
		}

		/* the tail "+ 360) % 360" solves shifts into negative numbers */
		double h = (((hsb[0] + hueShift) % 360) + 360) % 360;
		double s = Math.max(Math.min(hsb[1] * saturationFactor, 1.0), 0.0);
		b = Math.max(Math.min(b * brightnessFactor, 1.0), 0.0);
		double a = Math.max(Math.min(alpha * opacityFactor, 1.0), 0.0);
		return hsb(h, s, b, a);
	}

	private static void checkSB(double saturation, double brightness) {
		if (saturation < 0.0 || saturation > 1.0) {
			SLogger.getSystemLogger().except(new IllegalArgumentException(
					"Color.hsb's saturation parameter (" + saturation + ") expects values 0.0-1.0"));
		}
		if (brightness < 0.0 || brightness > 1.0) {
			SLogger.getSystemLogger().except(new IllegalArgumentException(
					"Color.hsb's brightness parameter (" + brightness + ") expects values 0.0-1.0"));
		}
	}

	private static void checkRGB(int red, int green, int blue) {
		if (red < 0 || red > 255) {
			SLogger.getSystemLogger().except(
					new IllegalArgumentException("Color.rgb's red parameter (" + red + ") expects color values 0-255"));
		}
		if (green < 0 || green > 255) {
			SLogger.getSystemLogger().except(new IllegalArgumentException(
					"Color.rgb's green parameter (" + green + ") expects color values 0-255"));
		}
		if (blue < 0 || blue > 255) {
			SLogger.getSystemLogger().except(new IllegalArgumentException(
					"Color.rgb's blue parameter (" + blue + ") expects color values 0-255"));
		}
	}

	@Override
	public String toString() {
		return "Color [red=" + red + ", green=" + green + ", blue=" + blue + ", opacity=" + alpha + "]";
	}

}
