// Daniel Shiffman
// The Nature of Code
// ITP, Spring 2006
// http://www.shiffman.net/

int x,y;

void setup() {
  size(400,400);
  background(0);
  colorMode(RGB,255,255,255,255);
  smooth();
  //framerate(30);
}

void draw() {
  //create an alpha blended background
  fill(0,1);
  rect(0,0,width,height);
  
  //calculate a probability between 0 and 100% based on mouseX location
  float prob = (mouseX / (float) width);     
  
  //get a random floating point value between 0 and 1
  float r = random(1);   
  
  //test the random value against the probability and trigger an event
  if (r < prob) {
    noStroke();
    fill(255);
    ellipse(x,y,10,10);
  }
  
  // X and Y walk through a grid
  x = (x + 10) % width;
  if (x == 0) y = (y + 10) % width;
}
