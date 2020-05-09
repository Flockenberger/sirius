package at.flockenberger.sirius.utillity;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * Utillity class.
 * 
 * @author Florian Wagner
 *
 */
public class SUtils
{

	/**
	 * 
	 * Checks if the given {@link Object} <code> t </code> is null. <br>
	 * If the given object is null a new {@link NullPointerException} will be
	 * thrown.
	 * 
	 * @param t    the object to check for null
	 * @param what a parameter to describe what object was null
	 */
	public static boolean checkNull(Object t, String what)
	{
		if (t == null)
		{
			NullPointerException ex = new NullPointerException("The object of type: " + what + " was null!");
			SLogger.getSystemLogger().except(ex);
			SLogger.getSystemLogger().trace(ex.getStackTrace());
			return true;
		}
		return false;
	}

	/**
	 * Checks if the given float value <code> f </code> is within the bounds of
	 * <code> min, max </code>. <br>
	 * If f is greater than max, f will be set to max. <br>
	 * If f is less than min, f will be set to min.
	 * 
	 * @param f   the value to check the bounds
	 * 
	 * @param min the inclusive min
	 * 
	 * @param max the inclusive max
	 * 
	 * @return the bound value or the value itself if it is within range
	 */
	public static float putInBounds(float f, float min, float max)
	{
		if (f > max)
			f = max;
		if (f < min)
			f = min;
		return f;
	}

	/**
	 * Clones the given {@link ByteBuffer} into a new {@link ByteBuffer}. <br>
	 * The original buffer is preserved.
	 * 
	 * @param original the {@link ByteBuffer} to copy
	 * @return a clone of the given buffer
	 */
	public static ByteBuffer clone(ByteBuffer original)
	{
		ByteBuffer clone = BufferUtils.createByteBuffer(original.capacity());
		original.rewind();// copy from the beginning
		clone.put(original);
		original.rewind();
		clone.flip();
		return clone;
	}

	/**
	 * Checks if the given index <code> index </code>is within 0 and max.
	 * 
	 * @param index
	 * @param max
	 */
	public static void checkIndex(int index, int max)
	{
		if (index < 0 || index >= max)
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Sets the given matrix to an orthographic 2D projection matrix, and returns
	 * it. If the given matrix is null, a new one will be created and returned.
	 * 
	 * @param m      the matrix to re-use, or null to create a new matrix
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return the given matrix, or a newly created matrix if none was specified
	 */
	public static Matrix4f toOrtho2D(Matrix4f m, float x, float y, float width, float height)
	{
		return toOrtho(m, x, x + width, y + height, y, 1, -1);
	}

	/**
	 * Sets the given matrix to an orthographic 2D projection matrix, and returns
	 * it. If the given matrix is null, a new one will be created and returned.
	 * 
	 * @param m      the matrix to re-use, or null to create a new matrix
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param near   near clipping plane
	 * @param far    far clipping plane
	 * @return the given matrix, or a newly created matrix if none was specified
	 */
	public static Matrix4f toOrtho2D(Matrix4f m, float x, float y, float width, float height, float near, float far)
	{
		return toOrtho(m, x, x + width, y, y + height, near, far);
	}

	/**
	 * Sets the given matrix to an orthographic projection matrix, and returns it.
	 * If the given matrix is null, a new one will be created and returned.
	 * 
	 * @param m      the matrix to re-use, or null to create a new matrix
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near   near clipping plane
	 * @param far    far clipping plane
	 * @return the given matrix, or a newly created matrix if none was specified
	 */
	public static Matrix4f toOrtho(Matrix4f m, float left, float right, float bottom, float top, float near, float far)
	{
		if (m == null)
			m = new Matrix4f();
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);
		float z_orth = -2 / (far - near);

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		m.m00(x_orth);
		m.m10(0);
		m.m20(0);
		m.m30(0);
		m.m01(0);
		m.m11(y_orth);
		m.m21(0);
		m.m31(0);
		m.m02(0);
		m.m12(0);
		m.m22(z_orth);
		m.m32(0);
		m.m03(tx);
		m.m13(ty);
		m.m23(tz);
		m.m33(1);
		return m;
	}

	/**
	 * Sets the matrix to a projection matrix with a near- and far plane, a field of
	 * view in degrees and an aspect ratio.
	 * 
	 * @param near        The near plane
	 * @param far         The far plane
	 * @param fov         The field of view in degrees
	 * @param aspectRatio The aspect ratio
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public static Matrix4f setToProjection(Matrix4f m, float near, float far, float fov, float aspectRatio)
	{
		if (m == null)
			m = new Matrix4f();
		m.identity();
		float l_fd = (float) (1.0 / Math.tan((fov * (Math.PI / 180)) / 2.0));
		float l_a1 = (far + near) / (near - far);
		float l_a2 = (2 * far * near) / (near - far);
		m.m00(l_fd / aspectRatio);
		m.m10(0);
		m.m20(0);
		m.m30(0);
		m.m01(0);
		m.m11(l_fd);
		m.m21(0);
		m.m31(0);
		m.m02(0);
		m.m12(0);
		m.m22(l_a1);
		m.m32(-1);
		m.m03(0);
		m.m13(0);
		m.m23(l_a2);
		m.m33(0);
		return m;

	}

	/**
	 * Calculates degrees to rad
	 * 
	 * @param deg the value to convert
	 * @return the converted value
	 */
	public static float degToRad(float deg)
	{
		return (float) (deg * Math.PI / (float) 180.f);
	}

	/**
	 * Calculates rad to degrees
	 * 
	 * @param rad the rad value to convert
	 * @return the converted value
	 */
	public static float radToDeg(float rad)
	{
		return (float) (rad * 180.0f / (float) Math.PI);
	}

	public static double map(double magnitude, double d, double e, double out_min, double out_max)
	{
		return (magnitude - d) * (out_max - out_min) / (e - d) + out_min;

	}

	public static FloatBuffer matrixToBuffer(Matrix4f m, FloatBuffer dest)
	{
		return matrixToBuffer(m, 0, dest);
	}

	public static FloatBuffer matrixToBuffer(Matrix4f m, int offset, FloatBuffer dest)
	{
		dest.put(offset, m.m00());
		dest.put(offset + 1, m.m01());
		dest.put(offset + 2, m.m02());
		dest.put(offset + 3, m.m03());
		dest.put(offset + 4, m.m10());
		dest.put(offset + 5, m.m11());
		dest.put(offset + 6, m.m12());
		dest.put(offset + 7, m.m13());
		dest.put(offset + 8, m.m20());
		dest.put(offset + 9, m.m21());
		dest.put(offset + 10, m.m22());
		dest.put(offset + 11, m.m23());
		dest.put(offset + 12, m.m30());
		dest.put(offset + 13, m.m31());
		dest.put(offset + 14, m.m32());
		dest.put(offset + 15, m.m33());
		return dest;
	}

	public static void matrixToBuffer(Matrix3f m, FloatBuffer dest)
	{
		matrixToBuffer(m, 0, dest);
	}

	public static void matrixToBuffer(Matrix3f m, int offset, FloatBuffer dest)
	{
		dest.put(offset, m.m00());
		dest.put(offset + 1, m.m01());
		dest.put(offset + 2, m.m02());
		dest.put(offset + 3, m.m10());
		dest.put(offset + 4, m.m11());
		dest.put(offset + 5, m.m12());
		dest.put(offset + 6, m.m20());
		dest.put(offset + 7, m.m21());
		dest.put(offset + 8, m.m22());

	}

}
