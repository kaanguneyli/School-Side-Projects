
import java.io.*;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

import java.awt.Font;
/**
 * @author legýon
 *
 */
public class MarketNavigator
{  
	
	/* Method that gives the distance between two points */
	public static double distanceFinder(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public static int pathFinder(String filename) {
		
		/* Find the smallestTotalDistance */
		double smallestTotalDistance = 0;
		
		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
// READ THE FILE. THEN WE CREATE THE HOUSE OBJECTS AND STORE THEM IN AN ARRAYLIST
		ArrayList<House> houses = new ArrayList<>();
		try {
			File file = new File(filename);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String data = scan.nextLine();
				String[] dataArray = data.split(" ");
				House house = new House(dataArray[0], Integer.valueOf(dataArray[1]), Integer.valueOf(dataArray[2]));
				houses.add(house);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
// CREATE A 2-DIMENSIONAL ARRAYLIST TO STORE ALL THE PERMUTATIONS INSIDE 
		ArrayList<ArrayList<House>> paths = new ArrayList<ArrayList<House>>();
		paths = permutations(houses.get(0), paths, houses, houses.size());
// ITERATE THROUGH PATHS TO FIND WHICH ONE IS THE SHORTHEST
		for (int i=0; i<paths.size(); i++) {
			ArrayList<House> currentPath = paths.get(i);
			double currentTotalDistance = 0;
// ITERATE INSIDE A PATH AND TAKE THE SUM OF DISTANCES
			for (int j=0; j<currentPath.size()-1; j++) {
				currentTotalDistance += distanceFinder(currentPath.get(j).getX(), currentPath.get(j).getY(), currentPath.get(j+1).getX(), currentPath.get(j+1).getY());
			}
// CHECK WHETHER THE VALUE FOUND IS THE SMALLEST OR NOT
			if (smallestTotalDistance == 0) {
				smallestTotalDistance = currentTotalDistance;
				StdDraw.clear(StdDraw.WHITE);
				drawLines(currentPath);
			} else if (currentTotalDistance < smallestTotalDistance) {
				smallestTotalDistance = currentTotalDistance;
				StdDraw.clear(StdDraw.WHITE);
				drawLines(currentPath);
			}
		}
			
		
		//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
		
		int roundedValue = (int) Math.round(smallestTotalDistance);
		return roundedValue;
	}	
	
/*
 * Swaps the items in the given index numbers in an array list that contains houses
 * @param market the item that w)on't be included in the permutation but be added to the beginning an the ending
 * @param result the array list that will be returned 
 * @param list array list to be worked on
 * @param n size of the array list
 * @return 
 */
	public static ArrayList<ArrayList<House>> permutations(House market, ArrayList<ArrayList<House>> result, ArrayList<House> list, int n) {
// CREATE AN ARRAY LIST TO KEEP THE CURRENT PERMUTATION
		ArrayList<House> item = new ArrayList<House>();
// IF N IS 1, NEW PERMUTATION IS FOUND
		if (n==1) {
// ADD MIGROS AT THE BEGINNING AND THE END. THEN ADD THE PERMUTATION IN THE RESULT ARRAY LIST
			item.add(market);
			for (int a=1; a< list.size(); a++) {
				item.add(list.get(a));
				}
			item.add(market);
			result.add(item);
// IF N IS NOT 1, PERMUTATION HAS TO BE FOUND
		} else {
// DO RECURSIONS WITH N GETTING SMALLER
// START THE LOOP FROM i=1 TO EXCLUDE THE MARKET FROM THE PERMUTATION
			for(int i = 1; i < n; i++) {
				permutations(market, result, list, n-1);
// IF N IS ODD SWAP THE FIRST AND (N-1)TH ELEMENTS
				if (n % 2 == 1) {
					swapItems(list, 1, n-1);
// IF N IS EVEN SWAP (I)TH AND (N-1)TH ELEMENTS
				} else {
					swapItems(list, i, n-1);
	        	}
			} 
		}
		return result;	
	}
/*
 * Swaps the items in the given index numbers in an array list that contains houses
 * @param list the array list to be worked on
 * @param index1 first index to be swapped
 * @param index2 second index to be swapped
 * @return the new array list
 */
	public static ArrayList<House> swapItems(ArrayList<House> list, int index1, int index2) {
		House temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
		return list;
	}
	
	public static void createCanvas() {
		StdDraw.setCanvasSize(400, 400);
		StdDraw.setXscale(0, 100);
		StdDraw.setYscale(0, 100);
		StdDraw.enableDoubleBuffering();
	}
	
	public static void drawPoints(ArrayList<House> list) {
		for (int i=0; i<list.size()-1; i++) {
			
		}
	}
	
	public static void drawLines(ArrayList<House> list) {
		StdDraw.setPenRadius(0.01);
		for (int i=0; i<list.size()-1; i++) {
			StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
			StdDraw.filledCircle(list.get(i).getX(), list.get(i).getY(), 3);
			StdDraw.setPenColor(StdDraw.DARK_GRAY);
			StdDraw.setFont(new Font("Times New Roman", Font.BOLD, 12));
			StdDraw.textLeft(list.get(i).getX(), list.get(i).getY(), list.get(i).getName());
			StdDraw.show();
		}
		
		for (int i=0; i<list.size(); i++) {
			if (i<list.size()-1) {
				StdDraw.line(list.get(i).getX(), list.get(i).getY(), list.get(i+1).getX(), list.get(i+1).getY());
				StdDraw.pause(100);
				StdDraw.show();
			} else {
				StdDraw.line(list.get(i).getX(), list.get(i).getY(), list.get(0).getX(), list.get(0).getY());
				StdDraw.pause(100);
				StdDraw.show();
			}
		}
	}
	
	public static void main(String[] args) {
		/* This part is for you to test your method, no points will be given from here */
		//String path = MarketNavigator.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator+"coordinates.txt";
		createCanvas();
		
		String path = "coordinates3.txt";
		int distance = pathFinder(path);
		System.out.println("Smallest distance:" + distance);
		
	}
}  

