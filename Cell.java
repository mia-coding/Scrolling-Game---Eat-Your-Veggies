public class Cell
{
  private Color color;
  private String imageFileName;
  final Color BLUE = new Color(173, 216, 230);
  
  public Cell()
  {
    color = BLUE;
    imageFileName = null;
  }
  
  public void setColor(Color c)
  {
    color = c;
  }
  
  public Color getColor()
  {
    return color;
  }
  
  public String getImageFileName()
  {
    return imageFileName;
  }
  
  public void setImageFileName(String fileName)
  {
    imageFileName = fileName;
  }
}