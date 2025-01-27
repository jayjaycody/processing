import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hypermedia.video.*; 
import java.awt.*; 
import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_dec12RPT_BLOB extends PApplet {

//Jason Stephens
//ICM Fall 2010
// Final Project
// Intelligent Healing Space



 //part of the recording process


OpenCV opencv;  

MovieMaker mm; //part of the recording process

int w = 320;
int h = 240;
int threshold = 80;

boolean find=true;

PFont font;


public void setup() {

  size( w*2+30, h*2+30 );

  opencv = new OpenCV( this );
  opencv.capture(w,h);



  //part of the movie capture process
  // Save uncompressed, at 15 frames per second
  //mm = new MovieMaker(this, width, height, "drawing.mov");

  // Or, set specific compression and frame rate options
  mm = new MovieMaker(this, width, height, "drawing_06.mov", 10, 
                     MovieMaker.ANIMATION, MovieMaker.HIGH);



  font = loadFont( "AndaleMono.vlw" );
  textFont( font );

  println( "Drag mouse inside sketch window to change threshold" );
  println( "Press space bar to record background image" );
  println( "Press 's' to stop recording");
}



public void draw() {

  background(0);
  opencv.read();
  //opencv.flip( OpenCV.FLIP_HORIZONTAL );

  image( opencv.image(), 10, 10 );	            // RGB image
  image( opencv.image(OpenCV.GRAY), 20+w, 10 );   // GRAY image
  image( opencv.image(OpenCV.MEMORY), 10, 20+h ); // image in memory

  opencv.absDiff();
  opencv.threshold(threshold);
  image( opencv.image(OpenCV.GRAY), 20+w, 20+h ); // absolute difference image


  // working with blobs
  Blob[] blobs = opencv.blobs( 100, w*h/3, 20, true );

  noFill();

  pushMatrix();
  translate(20+w,20+h);

  for( int i=0; i<blobs.length; i++ ) {

    Rectangle bounding_rect	= blobs[i].rectangle;
    float area = blobs[i].area;
    float circumference = blobs[i].length;
    Point centroid = blobs[i].centroid;
    Point[] points = blobs[i].points;

    // rectangle
    noFill();
    stroke( blobs[i].isHole ? 128 : 64 );
    rect( bounding_rect.x, bounding_rect.y, bounding_rect.width, bounding_rect.height );


    // centroid
    stroke(0,0,255);
    line( centroid.x-5, centroid.y, centroid.x+5, centroid.y );
    line( centroid.x, centroid.y-5, centroid.x, centroid.y+5 );
    noStroke();
    fill(0,0,255);
    text( area,centroid.x+5, centroid.y+5 );


    fill(255,0,255,64);
    stroke(255,0,255);
    if ( points.length>0 ) {
      beginShape();
      for( int j=0; j<points.length; j++ ) {
        vertex( points[j].x, points[j].y );
      }
      endShape(CLOSE);
    }

    noStroke();
    fill(255,0,255);
    text( circumference, centroid.x+5, centroid.y+15 );
  }
  popMatrix();

  // add window's pixels to movie recording
  mm.addFrame();
}

// stop the video recording with a push of ????
public void keyPressed() {
  if (key == 's') {
    // Finish the movie if space bar is pressed
    mm.finish();
  }
if ( key == ' ') opencv.remember(); }

    
//void keyPressed() {
//  if ( key==' ' ) opencv.remember();
//}

public void mouseDragged() {
  threshold = PApplet.parseInt( map(mouseX,0,width,0,255) );
}


    
    
public void stop() {
  opencv.stop();
  super.stop();
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_dec12RPT_BLOB" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
