package com.tomtom.lejos.stefan.gotoxy;

public class Transformer {
	
	
	public Parameters transform(Coordinate a, Coordinate b, Coordinate c){
		
		
		/*Parameters transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(0,1));
		transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(1,1));
		transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(1,0));
		transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(1,-1));
		transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(0,-1));
		transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(-1,-1));
		transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(-1,0));
		transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(-1,1));*/
		
		Vector vectorA = new Vector(b.getX()-a.getX(), b.getY()-a.getY());
		Vector vectorB = new Vector(c.getX()-b.getX(),c.getY()-b.getY());

		
		return transform(vectorA, vectorB);
	}
	
	public Parameters transform(Vector vectorA, Vector vectorB){
		
		double vectorALength = Math.sqrt(Math.pow(vectorA.getX(), 2)+Math.pow(vectorA.getY(), 2));
		double vectorBLength = Math.sqrt(Math.pow(vectorB.getX(), 2)+Math.pow(vectorB.getY(), 2));
		double angle =  (Math.atan2(vectorB.getY(),vectorB.getX()) - Math.atan2(vectorA.getY(), vectorA.getX()))*(180/Math.PI);
		//double angle = Math.acos(((vectorB.getX()*vectorA.getX())+(vectorB.getY()*vectorA.getY()))/(vectorALength*vectorBLength))*(180/Math.PI);
		//angle = 180-angle;
		if (angle<-180){
			angle+=360;
		}
		if (angle>180){
			angle-=360;
		}
				//Math.atan(((from.getY()-to.getY())/(from.getX()-to.getX())))*(180/Math.PI); 
		System.out.println("Distance:"+vectorBLength+" angle:"+angle);
		return new Parameters(vectorBLength,angle);
	}
	
}
