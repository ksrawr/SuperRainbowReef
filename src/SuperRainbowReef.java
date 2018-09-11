import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class SuperRainbowReef extends JPanel {
	
	private final GameEventObservable geobv;
	//public static final int world_width  = 1920;
    //public static final int world_height = 1440;
    public static final int screen_width = 800;//1280;
    public static final int screen_height = 800;//960;
    private int[][] map_level;
    private int[][] map_level2;
    private int[][] map_level3;
    private boolean win = false;
    private boolean lose = false;
    private int currentLevel = 1;
    private boolean loadLevel = false;
	
    BufferedImage world;
    Graphics2D buffer;
    BufferedImage strFishImg, katchImg, smJellyImg, bgJellyImg, bimg, lifeImg, blSplit, wallImg, winImg;
    BufferedImage blSolid, blPurple, blYellow, blRed, blGreen, blCyan, blBlue, blWhite, blDouble, blLife;
    BufferedImage loseImg;
    JFrame frame;
    Katch k;
    MapLevel level;
    ArrayList<Block> blockList;
    ArrayList<WallTile> wallList;
    ArrayList<Squid> squidList;
    LifeStock lifePanel;
    ScoreStock scorePanel;
    FloorTile ft;
    StarFish pop;
    WallTile wt;
    Block block;
    Squid squid;
    SoundPlayer sp;
    
    public static void main(String[] args) {
    	Thread x;
    	SuperRainbowReef srb = new SuperRainbowReef();
    	srb.init();
    	
    	try {
    		while (true) {
    			srb.geobv.setChanged();
    			srb.geobv.notifyObservers();
    			srb.repaint();
    			Thread.sleep(1000/144);
    		}
    	} catch (InterruptedException ex) {
    		Logger.getLogger(SuperRainbowReef.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }
    
    public SuperRainbowReef() {
    	this.geobv = new GameEventObservable();
    }
    
    private void init() {
    	blockList = new ArrayList<Block>();
    	wallList = new ArrayList<WallTile>();
    	squidList = new ArrayList<Squid>();
    	level = new MapLevel();
    	map_level = MapLevel.getMap_level().clone();
    	this.frame = new JFrame("Super Rainbow Reef");
    	try {
    		world = new BufferedImage(screen_width, screen_height, BufferedImage.TYPE_3BYTE_BGR);
    		bimg = ImageIO.read(getClass().getResource("/Background1.bmp"));
    		strFishImg = ImageIO.read(getClass().getResource("/Pop.gif"));
    		katchImg = ImageIO.read(getClass().getResource("/Katch.gif"));
    		smJellyImg = ImageIO.read(getClass().getResource("/Bigleg_small.gif"));
    		bgJellyImg = ImageIO.read(getClass().getResource("/Bigleg.gif"));
    		lifeImg = ImageIO.read(getClass().getResource("/Katch_small.gif"));
    		wallImg = ImageIO.read(getClass().getResource("/Wall.gif"));
    		blSolid = ImageIO.read(getClass().getResource("/Block_solid.gif"));
    		blPurple = ImageIO.read(getClass().getResource("/Block1.gif"));
    		blYellow = ImageIO.read(getClass().getResource("/Block2.gif"));
    		blRed = ImageIO.read(getClass().getResource("/Block3.gif"));
    		blGreen = ImageIO.read(getClass().getResource("/Block4.gif"));
    		blCyan = ImageIO.read(getClass().getResource("/Block5.gif"));
    		blBlue = ImageIO.read(getClass().getResource("/Block6.gif"));
    		blWhite = ImageIO.read(getClass().getResource("/Block7.gif"));
    		blDouble = ImageIO.read(getClass().getResource("/Block_double.gif"));
    		blLife = ImageIO.read(getClass().getResource("/Block_life.gif"));
    		blSplit = ImageIO.read(getClass().getResource("/Block_split.gif"));
    		winImg = ImageIO.read(getClass().getResource("/Congratulation.gif"));
    		loseImg = ImageIO.read(getClass().getResource("/lose.png"));
    		
    		//Note: map_level 40 x 40
    		//x: 20, y: 20
    		for (int x = 0; x < map_level.length; x++) {
    			for(int y = 0; y < map_level[x].length; y++) {
    				if(map_level[x][y] == 2) {
    					wallList.add(new WallTile(wallImg, x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 11) {
    					blockList.add(new Block(blBlue, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 4) {
    					blockList.add(new Block(blGreen, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 5) {
    					blockList.add(new Block(blYellow, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 6) {
    					blockList.add(new Block(blDouble, this, "Double", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 7) {
    					blockList.add(new Block(blPurple, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 8) {
    					blockList.add(new Block(blRed, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 9) {
    					blockList.add(new Block(blWhite, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 13) {
    					blockList.add(new Block(blLife, this, "Life", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 21) {
    					blockList.add(new Block(blSolid, this, "UnBreakable", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 20) {
    					squidList.add(new Squid(this, bgJellyImg, "Grand", x * wallImg.getWidth() - 20, y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 10) {
    					blockList.add(new Block(blCyan, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				}
    			}
    		}
    	} catch (IOException ex) {
    		System.out.print(ex.getMessage());
    	}
    	k = new Katch(this, katchImg, screen_width/2 - 30, screen_height / 2 + screen_height / 4);
    	KatchControl kc = new KatchControl(k, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_ENTER);
    	pop = new StarFish(this, k, strFishImg, this.k.getX() + 20, this.k.getY() - 40 );
    	ft = new FloorTile(this);
    	lifePanel = new LifeStock(this,lifeImg);
    	scorePanel = new ScoreStock(this);
    	sp = new SoundPlayer(1, "/Music.wav");
    	this.frame.add(this);
    	this.frame.addKeyListener(kc);
    	
    	this.geobv.addObserver(k);
    	
    	this.frame.setSize(screen_width, screen_height);
    	this.frame.setResizable(false);
    	this.frame.setLocationRelativeTo(null);
    	
    	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.frame.setVisible(true);
    }
    
    public void updateWin() {
    	this.win = true;
    }
    
    public void updateLose() {
    	this.lose = true;
    }
    
    public int getCurrentLevel() {
    	return this.currentLevel;
    }
    
    public void setCurrentLevel(int cl) {
    	this.currentLevel = cl;
    }
    
    public void updateLoadLevel() {
    	this.loadLevel = true;
    }
    
    public void updateLevel() {
    	if(currentLevel == 2 && loadLevel) {
	    	squidList.clear();
	    	blockList.clear();
	    	wallList.clear();
	    	this.k.respawn();
	    	this.pop.respawn();
	    	map_level = MapLevel.getMap_level2().clone();
	    	for (int x = 0; x < map_level.length; x++) {
    			for(int y = 0; y < map_level[x].length; y++) {
    				if(map_level[x][y] == 2) {
    					wallList.add(new WallTile(wallImg, x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 11) {
    					blockList.add(new Block(blBlue, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 4) {
    					blockList.add(new Block(blGreen, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 5) {
    					blockList.add(new Block(blYellow, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 6) {
    					blockList.add(new Block(blDouble, this, "Double", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 7) {
    					blockList.add(new Block(blPurple, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 8) {
    					blockList.add(new Block(blRed, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 9) {
    					blockList.add(new Block(blWhite, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 13) {
    					blockList.add(new Block(blLife, this, "Life", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 21) {
    					blockList.add(new Block(blSolid, this, "UnBreakable", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 20) {
    					squidList.add(new Squid(this, bgJellyImg, "Grand", x * wallImg.getWidth() - 20, y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 10) {
    					blockList.add(new Block(blCyan, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				}
    			}
    		loadLevel = false;
    		}
    	} else if(currentLevel == 3 && loadLevel) {
	    	squidList.clear();
	    	blockList.clear();
	    	wallList.clear();
	    	this.k.respawn();
	    	this.pop.respawn();
	    	map_level = MapLevel.getMap_level3().clone();
	    	for (int x = 0; x < map_level.length; x++) {
    			for(int y = 0; y < map_level[x].length; y++) {
    				if(map_level[x][y] == 2) {
    					wallList.add(new WallTile(wallImg, x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 11) {
    					blockList.add(new Block(blBlue, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 4) {
    					blockList.add(new Block(blGreen, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 5) {
    					blockList.add(new Block(blYellow, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 6) {
    					blockList.add(new Block(blDouble, this, "Double", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 7) {
    					blockList.add(new Block(blPurple, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 8) {
    					blockList.add(new Block(blRed, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 9) {
    					blockList.add(new Block(blWhite, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 13) {
    					blockList.add(new Block(blLife, this, "Life", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 21) {
    					blockList.add(new Block(blSolid, this, "UnBreakable", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 20) {
    					squidList.add(new Squid(this, bgJellyImg, "Grand", x * wallImg.getWidth() - 20, y * wallImg.getHeight()));
    				} else if(map_level[x][y] == 10) {
    					blockList.add(new Block(blCyan, this, "none", x * wallImg.getWidth(), y * wallImg.getHeight()));
    				}
    			}
    		}
	    	loadLevel = false;
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
    	buffer = world.createGraphics();
    	super.paintComponent(g2);
    	
    	g2.drawImage(bimg, 0, 0, screen_width, screen_height, null);
    	
    	this.updateLevel();
    	
    	this.k.drawImage(g2);
    	this.pop.drawImage(g2);
    	
    	this.lifePanel.drawImage(g2);
    	this.scorePanel.drawImage(g2);
    	
    	for (int x = 0; x < squidList.size(); x++) {
    		squid = squidList.get(x);
    		squidList.get(x).drawImage(g2);
    		squid.CollisionWithPop();
    	}
    	
    	for(int y = 0; y < blockList.size(); y++) {
    		block = blockList.get(y);
    		blockList.get(y).drawImage(g2);
    		block.CollisionWithPop();
    	}
    	
    	for (int z = 0; z < wallList.size(); z++) {
    		wallList.get(z).drawImage(g2);
    	}
    	this.pop.update();
    	
    	if(win) {
    		g2.drawImage(winImg, 0, 0, screen_width, screen_height, null);
    	} else if (lose) {
    		g2.drawImage(loseImg, 0, 0, screen_width, screen_height, null);
    		g2.setColor(Color.WHITE);
    		g2.setFont(new Font("Helvetica", Font.BOLD, 98));
    		g2.drawString("GAME OVER", screen_width/4, screen_height/4);
    		g2.setFont(new Font("Helvetica", Font.BOLD, 40));
    		g2.drawString(this.pop.toStringScore(), screen_width/4, screen_height/4 + 100);
    		
    		////////////////
    		// Here's my attempt at having a highscores table... 
    		/*
    		ArrayList<Integer> Scores = new ArrayList<>();
    		try {
    			FileInputStream fstream = new FileInputStream("scores.txt");
    			DataInputStream dstream = new DataInputStream(fstream);
    			BufferedReader reader = new BufferedReader(new InputStreamReader(dstream));
    			String token = reader.readLine();
    			while (token != null) { 
    				try {
    					//Scores.add(Integer.parseInt(token.trim()));
    					System.out.println("Score.txt: " + token);
    				} catch (NumberFormatException e){
    					//System.out.println(e.getMessage());
    				}
    				token = reader.readLine();
    			}
    			dstream.close();
    		} catch (IOException e) {
    			System.out.println(e.getMessage());
    		}
    		Collections.sort(Scores);
    		Collections.reverse(Scores);
    		
    		/////////////////////
    		// Show top 3 scores;
    		g2.setColor(Color.YELLOW);
    		g2.setFont(new Font("Helvetica", Font.BOLD, 30));
    		int offset = 0;
    		for (int i = 0; i < 2; i++) {
    			//g2.drawString("1." + Scores.get(i).toString(), screen_width/2, screen_height/2 + offset);
    			offset += 100;
    		}
    		
    		try {
    			BufferedWriter output = new BufferedWriter(new FileWriter("scores.txt", true));
    			output.newLine();
    			output.append("" + this.pop.getScore());
    			output.close();
    		} catch (IOException e) {
    			System.out.println(e.getMessage());
    		}
    		*/
    	}
    }
}
