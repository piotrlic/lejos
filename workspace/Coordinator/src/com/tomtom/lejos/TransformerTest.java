package com.tomtom.lejos;

import org.junit.Assert;
import org.junit.Test;


public class TransformerTest {
	@Test
	public void testTransformer(){
		Transformer transformer = new Transformer();
		Parameters transform = transformer.transform(new Coordinate(0,-1),new Coordinate(0,0), new Coordinate(1,1));
		//Assert.assertEquals("", transform.getDistance(), Math.sqrt(2), 0.001);
		//Assert.assertEquals("", -45,(transform.getAngle()), 0.001);
		
		transform = transformer.transform(new Coordinate(0,0),new Coordinate(1,1), new Coordinate(1,-1));
		//Assert.assertEquals("", transform.getDistance(), 2, 0.001);
		//Assert.assertEquals("", -135, transform.getAngle(), 0.001);

		transform = transformer.transform(new Coordinate(1,1),new Coordinate(1,-1), new Coordinate(0,0));
		//Assert.assertEquals("", transform.getDistance(), Math.sqrt(2), 0.001);
		//Assert.assertEquals("", -135, transform.getAngle(), 0.001);
		/*
		transform = transformer.transform(new Coordinate(0,0), new Coordinate(-1,1));
		Assert.assertEquals("", transform.getDistance(), Math.sqrt(2), 0.001);
		Assert.assertEquals("", 135, transform.getAngle(), 0.001);*/
		
		
		
		/*Parameters transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(0,1));
		transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(1,1));
		transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(1,0));
		transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(1,-1));
		transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(0,-1));
		transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(-1,-1));
		transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(-1,0));
		transform = transformer.transform(new Coordinate(0,1),new Coordinate(0,0), new Coordinate(-1,1));*/
		
		
		
	}
}
