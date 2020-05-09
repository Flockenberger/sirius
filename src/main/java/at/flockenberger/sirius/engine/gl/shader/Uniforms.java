package at.flockenberger.sirius.engine.gl.shader;

/**
 * <h1>Uniforms</h1><br>
 * The Uniforms enum contains names for uniforms which are commonly used in
 * shaders.
 * 
 * @author Florian Wagner
 *
 */
public enum Uniforms
{

	MATRIX_TRANSFORMATION("transformationMatrix"), 
	MATRIX_VIEW("viewMatrix"),
	MATRIX_PROJECTION("projectionMatrix"),
	COLOR_OBJECT("objectColor"), 
	COLOR_POSITION("light.lightPosition"),
	COLOR_LIGHT_DIFFUSE("light.colorDiffuse"),
	COLOR_LIGHT_AMBIENT("light.colorAmbient"),
	COLOR_LIGHT("lightColor"),
	COLOR_LIGHT_SPECULAR("light.colorSpecular"),
	VIEW_POSITION("viewPos"),
	MATERIAL_AMBIENT("material.colorAmbient"),
	MATERIAL_DIFFUSE("material.colorDiffuse"),
	MATERIAL_SPECULAR("material.colorSpecular"),
	MATERIAL_SHININESS("material.shininess"),
	TEXTURE("texture");

	private String id;

	Uniforms(String id)
	{
		this.id = id;
	}

	public String getID()
	{
		return this.id;
	}
}
