import java.awt.Font;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Game
{
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private boolean won = false;
  final private int ENDFRAMELENGTH = 100;
  final private int ENDFRAMEWIDTH = 200;
  final private int WINNINGAMOUNT = 10;
  final Color BLUE = new Color(173, 216, 230);
  final Color RED = new Color(220, 20, 60);
  final Color GREEN = new Color(50, 205, 50);
  
  public Game()
  {
    grid = new Grid(10, 20);
    userRow = grid.getNumRows()/2;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), "user.png");
  }
  
  public void play()
  {
    while (!isGameOver())
    {
      grid.pause(80);
      handleKeyPress();
      if (msElapsed % 300 == 0)
      {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
    if (!won) {
	    grid.setImage(new Location(userRow, 0), "dead.png");
	    gameLostFrame();
    } else {
	    grid.setImage(new Location(userRow, 0), "win.png");
	    gameWonFrame();
    }
  }
  
  public void gameLostFrame() {
	    JFrame endFrame = new JFrame("GAME OVER...");
		endFrame.setSize(ENDFRAMEWIDTH, ENDFRAMELENGTH);
		endFrame.setLocation(grid.getX() + grid.getWidth()/2 - ENDFRAMEWIDTH/2, grid.getY() + grid.getHeight()/2 - ENDFRAMELENGTH/2);
		endFrame.setBackground(RED);
		String pts;
		if (timesGet == 1) {
			pts = "point";
		} else {
			pts = "points";
		}
		JLabel points = new JLabel(("      You got " + timesGet + " " + pts + "!!!"));
		points.setOpaque(true);
		points.setForeground(new Color(255, 255, 255));
		points.setBackground(RED);
		endFrame.add(points);
		endFrame.setVisible(true);
  }
  
  public void gameWonFrame() {
	    JFrame endFrame = new JFrame("YOU WON!!!");
		endFrame.setSize(ENDFRAMEWIDTH, ENDFRAMELENGTH);
		endFrame.setLocation(grid.getX() + grid.getWidth()/2 - ENDFRAMEWIDTH/2, grid.getY() + grid.getHeight()/2 - ENDFRAMELENGTH/2);
		endFrame.setBackground(GREEN);
		String temp;
		if (timesAvoid == 1) {
			temp = "time";
		} else {
			temp = "times";
		}
		JLabel deaths = new JLabel(("      You only died " + timesAvoid + " " + temp + "!"));
		deaths.setOpaque(true);
		deaths.setForeground(new Color(255, 255, 255));
		deaths.setBackground(GREEN);
		endFrame.add(deaths);
		endFrame.setVisible(true);
  }
  
  public void handleKeyPress()
  {
	  int key = grid.checkLastKeyPressed();
	  Location userLoc = new Location(userRow, 0);
	  grid.setImage(userLoc, null);
	  if (key == 40 && userRow != (grid.getNumRows() - 1)) {
		  userRow++;
	  } else if (key == 38 && userRow != 0) {
		  userRow--;
	  }
	  userLoc = new Location(userRow, 0);
	  handleCollision(userLoc);
	  grid.setImage(userLoc, "user.png");
  }
  
  public void populateRightEdge()
  {
	  int random = (int) (Math.random() * 10);
	  int random2 = (int) (Math.random() * grid.getNumRows());
	  if (random < 8) { //80% chance of a pizza
		  grid.setImage(new Location(random2, grid.getNumCols() - 1), "pizza.png");
	  } else if (random >= 8) { //20% chance of a brocolli
		  grid.setImage(new Location(random2, grid.getNumCols() - 1), "brocolli.png");
	  }
  }
  
  public void scrollLeft()
  {
	  Location collisionLocation = new Location(userRow, 1);
	  if (grid.getImage(collisionLocation) != null) {
		  handleCollision(collisionLocation);
	  }
	  for (int i = 0 ; i < grid.getNumRows() ; i++) {
		  scrollPerRow(i);
	  }
  }
  
  public void scrollPerRow(int row) {
	  for (int i = 1 ; i < grid.getNumCols() ; i++) {
		  Location currentRow = new Location(row, i);
		  Location previousRow = new Location(row, i - 1);
		  if (grid.getImage(previousRow) != "user.png") {
			  grid.setImage(previousRow, grid.getImage(currentRow));
			  grid.setImage(currentRow, null);
		  }
	  }
  }
  
  public void handleCollision(Location loc)
  {
	  String image = grid.getImage(loc);
	  if (image == "pizza.png") {
		  timesAvoid++;
	  } else if (image == "brocolli.png") {
		  timesGet++;
	  }
	  grid.setImage(loc, null);
  }
  
  public int getScore()
  {
    return timesGet;
  }
  
  public void updateTitle()
  {
    grid.setTitle("EAT YOUR VEGGIES          |          Veggies: " + getScore() + "          |          Lives: " + (3 - timesAvoid));
  }
  
  public boolean isGameOver()
  {
	if (timesGet == WINNINGAMOUNT) {
		won = true;
	}
    return timesAvoid >= 3 || timesGet >= WINNINGAMOUNT;
  }
  
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  
  public static void main(String[] args)
  {
	 Game.test();
  }
}