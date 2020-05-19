package at.flockenberger.sirius.map.tilted;

public class TiledMap
{
	private int compressionlevel;
	private int height;
	private boolean infinite;
	private MapLayer[] layers;
	private int nextlayerid;
	private int nextobjectid;
	private String orientation;
	private String renderorder;
	private String tiledversion;
	private int tileheight;
	private MapTileSet[] tilesets;
	private int tilewidth;
	private String type;
	private float version;
	private int width;

	public TiledMap(int compressionlevel, int height, boolean infinite, MapLayer[] layers, int nextlayerid, int nextobjectid,
			String orientation, String renderorder, String tiledversion, int tileheight, MapTileSet[] tilesets,
			int tilewidth, String type, float version, int width)
	{
		super();
		this.compressionlevel = compressionlevel;
		this.height = height;
		this.infinite = infinite;
		this.layers = layers;
		this.nextlayerid = nextlayerid;
		this.nextobjectid = nextobjectid;
		this.orientation = orientation;
		this.renderorder = renderorder;
		this.tiledversion = tiledversion;
		this.tileheight = tileheight;
		this.tilesets = tilesets;
		this.tilewidth = tilewidth;
		this.type = type;
		this.version = version;
		this.width = width;
	}

	public int getCompressionlevel()
	{
		return compressionlevel;
	}

	public int getHeight()
	{
		return height;
	}

	public boolean isInfinite()
	{
		return infinite;
	}

	public MapLayer[] getLayers()
	{
		return layers;
	}

	public int getNextlayerid()
	{
		return nextlayerid;
	}

	public int getNextobjectid()
	{
		return nextobjectid;
	}

	public String getOrientation()
	{
		return orientation;
	}

	public String getRenderorder()
	{
		return renderorder;
	}

	public String getTiledversion()
	{
		return tiledversion;
	}

	public int getTileheight()
	{
		return tileheight;
	}

	public MapTileSet[] getTilesets()
	{
		return tilesets;
	}

	public int getTilewidth()
	{
		return tilewidth;
	}

	public String getType()
	{
		return type;
	}

	public float getVersion()
	{
		return version;
	}

	public int getWidth()
	{
		return width;
	}

}
