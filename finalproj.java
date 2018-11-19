/* Name:    Elia Anagnostou
 * File:    finalproj.java
 * Desc:
 *
 * An interactive 2D map of Park Science Center at Bryn Mawr College. Map allows users to search rooms and
 * get a general picture of where each department can be found on the map.
 * 
 * Note: I didn't have time to make the map of Floor 1, as I found out map making with mathematical accuracy is a process that needs a lot more work
 * than I expected. I did, however, present Floor 1 as an approximation using the map I created for Floor 2, and am willing to complete it
 * when I find the time, hopefully before the presentation.
 *
 */

package attemptfinal;
import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import acm.util.*;

public class finalproj extends GraphicsProgram
{
	// Here I initiate all the colors I will use
	Color yellow = new Color(255,255,0);
	Color white = new Color(255,255,255);
	Color red = new Color(255,0,0);
	Color grey = new Color(128,128,128);
	Color greyish = new Color(211,211,211);
	Color green = new Color(0,128,0);
	Color blue = new Color(0,255,255);
	Color darkbrown = new Color(30,20,10);
	
	// Here I initiate all the fonts I will use
	Font font = new Font("Times New Roman",Font.BOLD,20);
	Font bigfont = new Font("Default",Font.BOLD,70);
	Font labfont = new Font("Default",Font.BOLD,30);
	Font minilabfont = new Font("Default",Font.BOLD,15);
	Font medlabfont = new Font("Default",Font.BOLD,20);
	
	// These are the array lists used for floor 2 that store:
	// a) the rectangles which represent the room 
	ArrayList<GRect> mapTwo = new ArrayList<>();
	// b) the numbers of the rooms in strings (to be used for search function)
	ArrayList<String> tagsTwo = new ArrayList<>();
	// c) the colors of the rooms (that represent the department)
	ArrayList<Color> colorsTwo = new ArrayList<>();
	// d) the labels of the rooms
	ArrayList<GLabel> labelsTwo = new ArrayList<>();
	
	//These are they array lists used for floor 1 that again store:
	// a) rectangles/rooms
	ArrayList<GRect> mapOne = new ArrayList<>();
	// b) tags/numbers of rooms
	ArrayList<String> tagsOne = new ArrayList<>();
	// c) colors of rooms
	ArrayList<Color> colorsOne = new ArrayList<>();
	// d) labels of rooms
	ArrayList<GLabel> labelsOne = new ArrayList<>();
	
	// The following are the buttons used for demonstrating the departments in the map
	GRect optionyellow = new GRect(10,10,30,30);
	GRect optionred = new GRect(10,50,30,30);
	GRect optiongrey = new GRect(10,90,30,30);
	GRect optiongreen = new GRect(10,130,30,30);
	GRect optionblue = new GRect(10,170,30,30);
	
	// These are buttons that help transition between states
	GRect back;
	GRect backmap;
	GRect beta;
	GRect backmapbeta;
	
	// The buttons that will lead the user to the maps of the corresponding floors and their labels
	GRect map1 = new GRect(400,300,400,100);
	GRect map2 = new GRect(400,410,400,100);
	GLabel map2lab = new GLabel("Search in 2nd Floor",450,470);
	GLabel map1lab = new GLabel("Search in 1st Floor",455,360);
	
	// The "clicked" boolean helps with pressing the mouse and having the effect not disappear
	boolean clicked=false;
	// The "kati" boolean helps with pressing the mouse and not affecting the mouseMoved functions
	boolean kati=false;
	
	// The input string
	String str;
	
	// The default font
	Font def;
	
	// The state in which the program starts
	int state = 0;
	
	// The two counts used for identifying rooms that are not found
	int count = 0;
	int count2 = 0;
	
	
	Scanner in = new Scanner(System.in);
	
			@Override
			public void run()
			{
					setSize(1200,1000);
					goToState(0); // We start from state 0
					addMouseListeners();
			}
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				if(state==0) // The buttons in the home screen change colors when the user hovers their mouse over them
				{
						if(map1.contains(e.getX(),e.getY()))
						{
								map1.setFillColor(greyish);
						}
						if(map2.contains(e.getX(),e.getY()))
						{
								map2.setFillColor(greyish);
						}
						if(!map1.contains(e.getX(),e.getY()))
						{
								map1.setFillColor(Color.WHITE);
						}
						if(!map2.contains(e.getX(),e.getY()))
						{
								map2.setFillColor(Color.WHITE);
						}
				}
				
				else if(state==1) //If "kati" is false, meaning the rooms the mouse hovers over are not already colored because of the department buttons,
				{								// the rooms the mouse hovers over, except the one that has been searched, change colors based on their departments
					int i = 0;
					if(kati==false)
					{
							while(i<mapTwo.size())
							{
									mapTwo.get(i).setFilled(true);
									if(mapTwo.get(i).contains(e.getX(),e.getY()))
									{
											mapTwo.get(i).setFillColor(colorsTwo.get(i));
											//tags.get(i).setFont(font);
											labelsTwo.get(i).setFont(font);
									}
									else
									{
										if(!str.equals(tagsTwo.get(i)))
										{
											mapTwo.get(i).setFillColor(white);
										}
										labelsTwo.get(i).setFont(def);
									}
									
									i++;
							}
					}
					// if the back button is hovered over, it changes colors
					if(backmap.contains(e.getX(),e.getY()))
					{
							backmap.setColor(grey);
					}
					else
					{
							backmap.setColor(greyish);
					}
					
				}
				
				else if(state==2) // In state 2, buttons change colors when being hovered over
				{
						if(back.contains(e.getX(),e.getY()))
						{
								back.setColor(grey);
						}
						else
						{
								back.setColor(greyish);
						}
						
						if(beta.contains(e.getX(),e.getY()))
						{
								beta.setColor(grey);
						}
						
						else
						{
								beta.setColor(greyish);
						}
				}
				
				else if(state==3) // State 3 is the same as state 1, only it covers a different map
				{
					int i = 0;
					if(kati==false)
					{
							while(i<mapOne.size())
							{
									mapOne.get(i).setFilled(true);
									if(mapOne.get(i).contains(e.getX(),e.getY()))
									{
											mapOne.get(i).setFillColor(colorsOne.get(i));
											labelsOne.get(i).setFont(font);
									}
									else
									{
										if(!str.equals(tagsOne.get(i)))
										{
											mapOne.get(i).setFillColor(white);
										}
										labelsOne.get(i).setFont(def);
									}
									
									i=i+1;
							}
					}
					
					// buttons change colors when being hovered over again
					if(backmapbeta.contains(e.getX(),e.getY()))
					{
							backmapbeta.setColor(grey);
					}
					else
					{
							backmapbeta.setColor(greyish);
					}
				}
			}
			
			
			@Override
			public void mousePressed(MouseEvent g)
			{
				if(state==0) // In state 0, the user is directed to the floor they wish to see through these buttons
				{
						if(map2.contains(g.getX(),g.getY()))
						{
								goToState(1);
								state=1;
						}
						if(map1.contains(g.getX(),g.getY()))
						{
								goToState(2);
								state=2;
						}
				}
				
				else if(state==1) // In state 1, I control the demonstration of the color of each department on the map
				{
					if(backmap.contains(g.getX(),g.getY())) // if the back button is not clicked
					{
							goToState(0);
							state=0;
						}
					else // if the back button is not clicked
					{
						backmap.setColor(greyish);
						
						if(clicked)
						{
							// if yellow button is clicked
							if(optionyellow.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapTwo.size()) // scans the rooms of which colors correspond to yellow
								{
										if((colorsTwo.get(i).getRed()==255)&&(colorsTwo.get(i).getGreen()==255)&&(colorsTwo.get(i).getBlue()==0))
										{
												mapTwo.get(i).setFillColor(colorsTwo.get(i));
										}
										
										i++;
								}
							}
							
							// same with red
							if(optionred.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapTwo.size())
								{
										if((colorsTwo.get(i).getRed()==255)&&(colorsTwo.get(i).getGreen()==0)&&(colorsTwo.get(i).getBlue()==0))
										{
												mapTwo.get(i).setFillColor(colorsTwo.get(i));
										}
										
										i++;
								}
								
							}
							
							//grey
							if(optiongrey.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapTwo.size())
								{
										if((colorsTwo.get(i).getRed()==128)&&(colorsTwo.get(i).getGreen()==128)&&(colorsTwo.get(i).getBlue()==128))
										{
												mapTwo.get(i).setFillColor(colorsTwo.get(i));
										}
										
										i++;
								}
							}
							
							//green
							if(optiongreen.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapTwo.size())
								{
										if((colorsTwo.get(i).getRed()==0)&&(colorsTwo.get(i).getGreen()==128)&&(colorsTwo.get(i).getBlue()==0))
										{
												mapTwo.get(i).setFillColor(colorsTwo.get(i));
										}
										
										i++;
								}
							}
							
							//blue
							if(optionblue.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapTwo.size())
								{
										if((colorsTwo.get(i).getRed()==0)&&(colorsTwo.get(i).getGreen()==255)&&(colorsTwo.get(i).getBlue()==255))
										{
												mapTwo.get(i).setFillColor(colorsTwo.get(i));
										}
										
										i++;
								}
							}
							
						}
						else //if none of these is clicked then rooms remain white
						{
							clicked=true;
							kati=false;
							int i = 0;
							while(i<mapTwo.size())
							{
								if(!str.equals(tagsTwo.get(i)))
								{
									mapTwo.get(i).setFillColor(Color.WHITE);
								}
									i++;
								
							}
						}
					}
				}
				
				else if(state==2) // In state 2, user has two options again
				{
						if(back.contains(g.getX(),g.getY()))
						{
								goToState(0);
								state=0;
						}
						if(beta.contains(g.getX(),g.getY()))
						{
								goToState(3);
								state=3;
						}
				}
				
				else if(state==3) // State 3 is exactly the same as state 1, only for a different map
				{
						if(backmapbeta.contains(g.getX(),g.getY()))
						{
								goToState(2);
								state=2;
						}
						if(clicked)
						{
							if(optionyellow.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapOne.size())
								{
										if((colorsOne.get(i).getRed()==255)&&(colorsOne.get(i).getGreen()==255)&&(colorsOne.get(i).getBlue()==0))
										{
												mapOne.get(i).setFillColor(colorsOne.get(i));
										}
										
										i++;
								}
							}
							
							if(optionred.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapOne.size())
								{
										if((colorsOne.get(i).getRed()==255)&&(colorsOne.get(i).getGreen()==0)&&(colorsOne.get(i).getBlue()==0))
										{
												mapOne.get(i).setFillColor(colorsOne.get(i));
										}
										
										i++;
								}
								
							}
							
							if(optiongrey.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapOne.size())
								{
										if((colorsOne.get(i).getRed()==128)&&(colorsOne.get(i).getGreen()==128)&&(colorsOne.get(i).getBlue()==128))
										{
												mapOne.get(i).setFillColor(colorsOne.get(i));
										}
										
										i++;
								}
							}
							
							if(optiongreen.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapOne.size())
								{
										if((colorsOne.get(i).getRed()==0)&&(colorsOne.get(i).getGreen()==128)&&(colorsOne.get(i).getBlue()==0))
										{
												mapOne.get(i).setFillColor(colorsOne.get(i));
										}
										
										i++;
								}
							}
							
							if(optionblue.contains(g.getX(),g.getY()))
							{
								clicked=false;
								kati=true;
								int i = 0;
								while(i<mapOne.size())
								{
										if((colorsOne.get(i).getRed()==0)&&(colorsOne.get(i).getGreen()==255)&&(colorsOne.get(i).getBlue()==255))
										{
												mapOne.get(i).setFillColor(colorsOne.get(i));
										}
										
										i++;
								}
							}
							
						}
						else
						{
							clicked=true;
							kati=false;
							int i = 0;
							while(i<mapOne.size())
							{
								if(!str.equals(tagsOne.get(i)))
								{
									mapOne.get(i).setFillColor(Color.WHITE);
								}
									i++;
								
							}
						}
						
				}
			}

			
			public void goToState(int newState)
			{
					removeAll();
					
					if(newState==0)
					{
							//The designing elements of state 1:
							add(map1);
							map1.setFilled(true);
							map2.setFilled(true);
							map1.setFillColor(Color.WHITE);
							map2.setFillColor(Color.WHITE);
							add(map2);
							add(map1lab);
							add(map2lab);
							map1lab.setFont(labfont);
							map2lab.setFont(labfont);
							GImage park = new GImage(MediaTools.loadImage("park.jpeg"), 0, 0);
							add(park);
							park.sendToBack();
							
							GLabel welcome = new GLabel("Interactive 2D Map of Park",130,200);
							add(welcome);
							welcome.setFont(bigfont);
							GLabel hi = new GLabel("Choose a floor and type a room number on the console",180,250);
							add(hi);
							hi.setFont(labfont);
							
							GImage hiol = new GImage(MediaTools.loadImage("hiol.gif"), 20, 500);
							add(hiol);
							hiol.scale(0.5);
					}
				
					else if(newState==1)
					{
						draw("Second"); // State 1, and also state 3 a bit down below, call the function "draw", which basically draws the designing elements of the map screens despite the maps themselves
						str = in.next(); // here is the input of the class that is searched
						
						try(Scanner filename = new Scanner(new FileReader("cords.txt")))
						{
								int line = 1;
								while(filename.hasNextLine())
								{
										String linestring = filename.nextLine();
										String words[] = linestring.split(",");
										//program reads the coordinates of the rooms first
										GRect room = new GRect(Integer.parseInt(words[0]),Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]));
										add(room);
										room.setFilled(true);
										room.setFillColor(Color.WHITE);
										//then the labels of the rooms and their coordinates
										GLabel label = new GLabel(words[4],Integer.parseInt(words[5]),Integer.parseInt(words[6]));
										add(label);
										labelsTwo.add(label);
										mapTwo.add(room);
										//then the tag, which is the same as the label, but in string mode
										tagsTwo.add(words[4]);
										//then the color RGB values of each room
										Color happy = new Color(Integer.parseInt(words[7]),Integer.parseInt(words[8]),Integer.parseInt(words[9]));
										colorsTwo.add(happy);
										if(line<2) // I save the default font to use it later
										{
											def = label.getFont();
										}
										line++;
								}
						}
						catch(FileNotFoundException e)
						{
								System.out.println("File not found" + e.getMessage());
						}
						
						//the background image
						GImage picture = new GImage(MediaTools.loadImage("image.jpeg"), 0, 0);
						add(picture);
						picture.sendToBack();
						
						int i = 0;
						while(i<tagsTwo.size()) // This is the search function
						{
									if(str.equals(tagsTwo.get(i)))
									{
											mapTwo.get(i).setFilled(true); 
											mapTwo.get(i).setFillColor(colorsTwo.get(i)); // the room that was searched changes color (takes the one of its department)
											
											//System.out.println(i);
											colordeps(colorsTwo.get(i)); //function that draws a character that says which department the searched room belongs to
											count++; // if this is zero, then the number searched for wasn't found
									}
									
									i++;
							}
							//System.out.println(count);
							if(count==0)
							{
								notfound(2); //function that draws the same character saying that the room was not found
								
							}
									//buttons
									backmap = new GRect(620,140,100,40);
									add(backmap);
									backmap.setFilled(true);
									backmap.setFillColor(greyish);
									
									GLabel backlab = new GLabel("<---",630,170);
									add(backlab);
									backlab.setFont(labfont);
							
									backmapbeta = new GRect(620,140,100,40);
									add(backmapbeta);
									backmapbeta.setFilled(true);
									backmapbeta.setFillColor(greyish);
									
									GLabel backlabbeta = new GLabel("<---",630,170);
									add(backlabbeta);
									backlabbeta.setFont(labfont);
							
						
					}
						
				
					else if(newState==2) // design elements of state 2 page
					{
						GImage greenie = new GImage(MediaTools.loadImage("image.jpeg"), 0, 0);
						add(greenie);
						GLabel msg = new GLabel("Stay tuned! Map under construction.",330,350);
						add(msg);
						msg.setFont(labfont);
						GLabel msg2 = new GLabel("If you want, you can try the beta version.",395,380);
						add(msg2);
						msg2.setFont(medlabfont);
						
						GImage hap = new GImage(MediaTools.loadImage("constr.gif"), 450, 400);
						add(hap);
						
						back = new GRect(10,30,100,40);
						add(back);
						back.setFilled(true);
						back.setFillColor(greyish);
						
						GLabel backlab = new GLabel("<---",20,60);
						add(backlab);
						backlab.setFont(labfont);
						
						beta = new GRect(1010,30,100,40);
						add(beta);
						beta.setFilled(true);
						beta.setFillColor(greyish);
						
						GLabel betalab = new GLabel("BETA",1020,60);
						add(betalab);
						betalab.setFont(labfont);
					}
					
					else if(newState==3) // same as state 1, but different map
					{
						draw("First");
						str = in.next();
						
						try(Scanner filename = new Scanner(new FileReader("cords2.txt")))
						{	
								int line = 1;
								while(filename.hasNextLine())
								{
										String linestring = filename.nextLine();
										String words[] = linestring.split(",");
										GRect room = new GRect(Integer.parseInt(words[0]),Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]));
										add(room);
										room.setFilled(true);
										room.setFillColor(Color.WHITE);
										GLabel label = new GLabel(words[4],Integer.parseInt(words[5]),Integer.parseInt(words[6]));
										add(label);
												labelsOne.add(label);
												mapOne.add(room);
												tagsOne.add(words[4]);
												Color happy = new Color(Integer.parseInt(words[7]),Integer.parseInt(words[8]),Integer.parseInt(words[9]));
												colorsOne.add(happy);
												if(line<2)
												{
													def=label.getFont();
												}
								}
						}
						catch(FileNotFoundException e)
						{
								System.out.println("File not found" + e.getMessage());
						}
						
						GImage picture = new GImage(MediaTools.loadImage("image.jpeg"), 0, 0);
						add(picture);
						picture.sendToBack();
						
						int i = 0;
						while(i<tagsOne.size())
						{
								//System.out.println(tags.get(i));
						
								
									if(str.equals(tagsOne.get(i)))
									{
											mapOne.get(i).setFilled(true);
											mapOne.get(i).setFillColor(colorsOne.get(i));
											
											colordeps(colorsOne.get(i));
											
											count++;
									}
									
									i++;
							}
							if(count==0)
							{
								notfound(1);
								
							}
							
									backmapbeta = new GRect(620,140,100,40);
									add(backmapbeta);
									backmapbeta.setFilled(true);
									backmapbeta.setFillColor(greyish);
									
									GLabel backlabbeta = new GLabel("<---",630,170);
									add(backlabbeta);
									backlabbeta.setFont(labfont);
					}
				
					
			}
			
			public void colordeps(Color col)
			{
				GImage olaf = new GImage(MediaTools.loadImage("found.gif"), 20, 500);
				add(olaf);
				olaf.scale(0.5);
				GOval msg = new GOval(20,230,280,200);
				add(msg);
				GOval msgsm = new GOval(60,425,30,30);
				add(msgsm);
				GOval msgxsm = new GOval(65,460,20,20);
				add(msgxsm);
				GOval msgxxsm = new GOval(75,485,10,10);
				add(msgxxsm);
				msg.setFilled(true);
				msg.setFillColor(white);
				msgsm.setFilled(true);
				msgsm.setFillColor(white);
				msgxsm.setFilled(true);
				msgxsm.setColor(white);
				msgxxsm.setFilled(true);
				msgxxsm.setColor(white);
				
				GLabel found = new GLabel("Found '" + str + "' in the ",55,320);
				add(found);
				found.setFont(medlabfont);
				
				if((col.getRed()==255)&&(col.getGreen()==255)&&(col.getBlue()==0))
				{
					GLabel found2 = new GLabel("Chemistry Department.",45,350);
					add(found2);
					found2.setFont(medlabfont);
				}
				
				else if((col.getRed()==255)&&(col.getGreen()==0)&&(col.getBlue()==0))
				{
					GLabel found2 = new GLabel("Biology Department.",45,350);
					add(found2);
					found2.setFont(medlabfont);
				}
				
				else if((col.getRed()==128)&&(col.getGreen()==128)&&(col.getBlue()==128))
				{
					GLabel found2 = new GLabel("Geology Department.",45,350);
					add(found2);
					found2.setFont(medlabfont);
				}
				
				else if((col.getRed()==0)&&(col.getGreen()==128)&&(col.getBlue()==0))
				{
					GLabel found2 = new GLabel("Computer Science Department.",45,350);
					add(found2);
					found2.setFont(medlabfont);
				}
				
				else if((col.getRed()==0)&&(col.getGreen()==255)&&(col.getBlue()==255))
				{
					GLabel found2 = new GLabel("Physics Department.",45,350);
					add(found2);
					found2.setFont(medlabfont);
				}
				else
				{
					found.move(-10,0);
					GLabel found2 = new GLabel("building.",70,350);
					add(found2);
					found2.setFont(medlabfont);
				}
				
			}
				public void notfound(int floor)
				{
					GImage olaf = new GImage(MediaTools.loadImage("notfound.gif"), 20, 500);
					add(olaf);
					olaf.scale(0.5);
					GOval msg = new GOval(20,230,280,200);
					add(msg);
					GOval msgsm = new GOval(60,425,30,30);
					add(msgsm);
					GOval msgxsm = new GOval(65,460,20,20);
					add(msgxsm);
					GOval msgxxsm = new GOval(75,485,10,10);
					add(msgxxsm);
					msg.setFilled(true);
					msg.setFillColor(white);
					msgsm.setFilled(true);
					msgsm.setFillColor(white);
					msgxsm.setFilled(true);
					msgxsm.setColor(white);
					msgxxsm.setFilled(true);
					msgxxsm.setColor(white);
					
					GLabel notfound = new GLabel("Room '"+ str + "' does not ",55,320);
					add(notfound);
					GLabel notfound2 = new GLabel("appear to be on floor "+floor+".",45,350);
					add(notfound2);
					notfound.setFont(medlabfont);
					notfound2.setFont(medlabfont);
				}
				
				public void draw(String floor)
				{
					add(optionyellow);
					optionyellow.setFilled(true);
					optionyellow.setColor(yellow);
					add(optionred);
					optionred.setFilled(true);
					optionred.setColor(red);
					add(optiongrey);
					optiongrey.setFilled(true);
					optiongrey.setColor(grey);
					add(optiongreen);
					optiongreen.setFilled(true);
					optiongreen.setColor(green);
					add(optionblue);
					optionblue.setFilled(true);
					optionblue.setColor(blue);
					GLabel title = new GLabel("Park "+floor+" Floor",370,100);
					add(title);
					title.setFont(bigfont);
					title.setColor(darkbrown);
					
					GLabel chem = new GLabel("chemistry",50,30);
					add(chem);
					chem.setColor(Color.WHITE);
					chem.setFont(minilabfont);
					GLabel bio = new GLabel("biology",50,70);
					add(bio);
					bio.setColor(Color.WHITE);
					bio.setFont(minilabfont);
					GLabel geo = new GLabel("geology",50,110);
					add(geo);
					geo.setColor(Color.WHITE);
					geo.setFont(minilabfont);
					GLabel compsci = new GLabel("computer science",50,150);
					add(compsci);
					compsci.setColor(Color.WHITE);
					compsci.setFont(minilabfont);
					GLabel phys = new GLabel("physics",50,190);
					add(phys);
					phys.setColor(Color.WHITE);
					phys.setFont(minilabfont);
				}
				
		}
			

