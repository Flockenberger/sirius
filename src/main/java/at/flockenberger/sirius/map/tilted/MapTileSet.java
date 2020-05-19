package at.flockenberger.sirius.map.tilted;

public class MapTileSet
{
	private int columns;
	private int firstgid;
	private String image;
	private int imageheight;
	private int imagewidth;
	private int margin;
	private String name;
	private int spacing;
	private int tilecount;
	private int tileheight;
	private int tilewidth;

	public MapTileSet(int columns, int firstgid, String image, int imageheight, int imagewidth, int margin, String name,
			int spacing, int tilecount, int tileheight, int tilewidth)
	{
		super();
		this.columns = columns;
		this.firstgid = firstgid;
		this.image = image;
		this.imageheight = imageheight;
		this.imagewidth = imagewidth;
		this.margin = margin;
		this.name = name;
		this.spacing = spacing;
		this.tilecount = tilecount;
		this.tileheight = tileheight;
		this.tilewidth = tilewidth;
	}

	public int getColumns()
	{
		return columns;
	}

	public int getFirstgid()
	{
		return firstgid;
	}

	public String getImage()
	{
		return image;
	}

	public int getImageheight()
	{
		return imageheight;
	}

	public int getImagewidth()
	{
		return imagewidth;
	}

	public int getMargin()
	{
		return margin;
	}

	public String getName()
	{
		return name;
	}

	public int getSpacing()
	{
		return spacing;
	}

	public int getTilecount()
	{
		return tilecount;
	}

	public int getTileheight()
	{
		return tileheight;
	}

	public int getTilewidth()
	{
		return tilewidth;
	}

}
