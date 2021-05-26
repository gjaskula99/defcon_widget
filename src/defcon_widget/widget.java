package defcon_widget;

//Warning! Military Software Detected!
//TOP SECRET CLEARANCE REQUIRED

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class widget extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Global Objects
	JFrame panel = new JFrame();
	JButton exit = new JButton("EXIT");
	JButton switch_left = new JButton("<<");
	JButton switch_right = new JButton(">>");
	JLabel banner = new JLabel("DEFCON");
	int defcon_level;
	
	//DEFCON Display
	ImageIcon [] levels = new ImageIcon[5];
	ImageIcon [] levels_act = new ImageIcon[5];
	JLabel [] defcon = new JLabel[5];
	BufferedImage img = null;
	
	int posX=0,posY=0;
	
	
	public widget()
	{
		//Frame
		System.out.println("Establishing battlefield control. Standby...");
		setSize(400, 100);
		setResizable(false);
		setTitle("DEFCON");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
	    //int screenWidth = screenSize.width;
		//setLocation(screenWidth / 3, 0);
	    int w = getSize().width;
	    //int h = getSize().height;
	    int x = (screenSize.width - w) / 2;
	    //int y = (screenSize.height - h) / 2;
	    setLocation(x, 0);
	    
		getContentPane().setBackground(Color.black);
		
		/*this.addMouseListener(new MouseAdapter()
		{
		   public void mousePressed(MouseEvent e)
		   {
		      posX=e.getX();
		      posY=e.getY();
		   }
		});
		
		this.addMouseMotionListener(new MouseAdapter()
		{
		     public void mouseDragged(MouseEvent evt)
		     {
				//sets frame position when mouse dragged			
				setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
							
		     }
		});*/
		
		//Maganegemtn Buttons
		exit.addActionListener(this);
		exit.setBounds(160, 80, 80, 20);
		exit.setBackground(Color.black);
		exit.setForeground(Color.white);
		exit.setFocusPainted(false);
		
		switch_left.addActionListener(this);
		switch_left.setBounds(0, 80, 80, 20);
		switch_left.setBackground(Color.black);
		switch_left.setForeground(Color.white);
		switch_left.setFocusPainted(false);
		switch_right.addActionListener(this);
		switch_right.setBounds(320, 80, 80, 20);
		switch_right.setBackground(Color.black);
		switch_right.setForeground(Color.white);
		switch_right.setFocusPainted(false);
		
		banner.setForeground(Color.white);
		banner.setBounds(165, 0, 80, 20);
		banner.setFont(new Font("Serif", Font.PLAIN, 18));
		
		add(exit);
		add(switch_left);
		add(switch_right);
		add(banner);
		
		//DEFCON
		levels[0] = new ImageIcon("pic/1.png");
		levels[1] = new ImageIcon("pic/2.png");
		levels[2] = new ImageIcon("pic/3.png");
		levels[3] = new ImageIcon("pic/4.png");
		levels[4] = new ImageIcon("pic/5.png");
		levels_act[0] = new ImageIcon("pic/1a.png");
		levels_act[1] = new ImageIcon("pic/2a.png");
		levels_act[2] = new ImageIcon("pic/3a.png");
		levels_act[3] = new ImageIcon("pic/4a.png");
		levels_act[4] = new ImageIcon("pic/5a.png");
		
		try
		{
			FileInputStream config = new FileInputStream("config.txt");
			defcon_level = config.read();
			config.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
			defcon_level = 5;
		}
		
		for(int i=0; i<defcon.length; i++)
		{
			defcon[i] = new JLabel(levels[i]);
			defcon[i].setBounds(20 + i*80, 30, 40, 40);
		}
		defcon[4].setIcon(levels[4]);
		defcon[defcon_level-1].setIcon(levels_act[defcon_level-1]);
		
		add(defcon[0]);
		add(defcon[1]);
		add(defcon[2]);
		add(defcon[3]);
		add(defcon[4]);
		
		//Start up
		if(defcon_level==5) switch_right.setEnabled(false);
		if(defcon_level==1) switch_left.setEnabled(false);
		play_announcement("snd/online.wav");
		System.out.println("Battle Control Online");
		System.out.println("We are now at DEFCON " + defcon_level);
		setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		new widget();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==exit)
		{
			System.exit(0);
		}
		if(event.getSource()==switch_left)
		{
			if(defcon_level>1)
			{
				defcon[defcon_level-1].setIcon(levels[defcon_level-1]);
				defcon[defcon_level-2].setIcon(levels_act[defcon_level-2]);
				defcon_level--;
				play_announcement("snd/alarm_new.wav");
				try
				{
					FileOutputStream config = new FileOutputStream("config.txt");
					config.write(defcon_level);
					config.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				
				System.out.println("We are now at DEFCON " + defcon_level);
				play_announcement("snd/defcon_"+defcon_level+".wav");
			}
			check_defcon();
		}
		if(event.getSource()==switch_right)
		{
			if(defcon_level<5)
			{
				defcon[defcon_level-1].setIcon(levels[defcon_level-1]);
				defcon[defcon_level].setIcon(levels_act[defcon_level]);
				defcon_level++;
				play_announcement("snd/alarm_new.wav");
				try
				{
					FileOutputStream config = new FileOutputStream("config.txt");
					config.write(defcon_level);
					config.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				System.out.println("We are now at DEFCON " + defcon_level);
				play_announcement("snd/defcon_"+defcon_level+".wav");
			}
			check_defcon();
		}
	}
	
	public void play_announcement(String path)
	{
		try {
		    //File yourFile = new File("snd/l_ar2alrm5.wav");
			File yourFile = new File(path);
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		    Clip clip;

		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		}
		catch (Exception e) {
		    System.out.println(e);
		}
	}
	
	public void check_defcon()
	{
		if(defcon_level>5) defcon_level=5;
		if(defcon_level<1) defcon_level=1;
		if(defcon_level==5) switch_right.setEnabled(false);
		else switch_right.setEnabled(true);
		if(defcon_level==1) switch_left.setEnabled(false);
		else switch_left.setEnabled(true);
	}
}
