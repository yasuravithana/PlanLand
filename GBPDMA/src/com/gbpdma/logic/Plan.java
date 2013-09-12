package com.gbpdma.logic;

import java.util.LinkedList;

/**
 * @author yasuravithana
 *
 */
public class Plan {
//this class is the template for a plan which consists of a boundary and a set of landmarks
    
    public String name;//name of the plan
    public Polygon boundary;//polygon which holds points of the boundary
    public LinkedList<Polygon> landmarks;//set of landmarks in the plan

    //constructor
    public Plan(String name) {
	this.name = name;//set name of plan
	boundary = new Polygon("boundary");//initializes a boundary
	landmarks = new LinkedList<Polygon>();//initializes a landmarks list
    }

}
