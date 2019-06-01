package com.example.demo.amap;

import org.junit.Test;

public class AMapTest{
   @Test
   public void Test() {
      LngLat start = new LngLat(113.92464,22.50432);
      LngLat end = new LngLat(113.923267,22.500018);
      System.err.println(AMapUtil.calculateLineDistance(start, end));
   }
}