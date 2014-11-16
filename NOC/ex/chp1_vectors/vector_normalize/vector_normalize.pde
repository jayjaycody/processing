// The Nature of Code
// Daniel Shiffman
// Draft book

// Example 1-3: Normalizing a Vector

void setup() {
  size(200,200);
  smooth();
}

void draw() {
  background(255);
  
  PVector mouse = new PVector(mouseX,mouseY);
  PVector center = new PVector(width/2,height/2);
  mouse.sub(center);
  
  mouse.normalize();
  mouse.mult(50);

  translate(width/2,height/2);
  line(0,0,mouse.x,mouse.y);
  
}


