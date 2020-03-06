package eg.edu.alexu.csd.datastructure.iceHockey.cs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class implementation implements IPlayersFinder{

	Point [] points;  //array to collect points
	Point [] finalPoints;  // to collect points in their right size
	int [][] sparseArray;
	private static int ymin;  
	private static int ymax; 		//to know the size of the shape
	private static int xmin;
	private static int xmax;
	private static int  totalPoint=0;  //intial collect point
	private static int cells=0;
	private static int xSize=0;  //size of array
	private static int ySize=0;
	
	@Override
	
	public Point[] findPlayers(String[] photo, int team, int threshold) {
		points = new Point [2500];
		// TODO Auto-generated method stub
		if (photo.length==0||threshold>10000||threshold<1||team>9||team<1) 
			return null;
		xSize=photo[0].length();
		ySize=photo.length;
		//System.out.println(xSize);
		//System.out.println(ySize);
		char[][] myArray = new char[ySize][xSize];
		myArray=toChar(photo);
		sparseArray= new int[ySize][xSize];
		for(int i=0; i<ySize;i++) {
			for(int j=0 ;j<xSize; j++) {
				if(myArray[i][j]-'0'==team) { 		//to change char into int
					sparseArray[i][j]=team;
				}
				else {
					sparseArray[i][j]=10;
				}
			}
		}
	
		
		for(int i=0; i<ySize;i++) {
			for(int j=0 ;j<xSize; j++) {  	//searching shapes
				if(sparseArray[i][j]==team) {
					searching(sparseArray,i,j,team);
				}
				if((4*cells)>=threshold) {
					points [totalPoint++] = new Point (xmin+xmax+1,ymin+ymax+1);
					
				}
				cells=0;
				ymax=0;
				ymin=50;		
				xmax=0;
				xmin=50;
				
			}
		}
	
		if(totalPoint==0) return null; //if there isn't player want to searched
		
		for(int i=0; i<totalPoint-1;i++) {
			for(int j=1+i; j<totalPoint;j++) {
				if(points[i].x>points[j].x) {		//arrange points
					Point temp=points[i];
					points[i]=points[j];
					points[j]=temp;
				}
				else if(points[i].x==points[j].x) {
					if(points[i].y>points[j].y) {
						Point temp=points[i];
						points[i]=points[j];
						points[j]=temp;
					}
				}
			}
		}
		
		
		
		finalPoints=new Point [totalPoint];
		for(int i=0; i<totalPoint;i++) {		//final points
			finalPoints[i]=points[i];
		}
	/*for(int i=0; i<totalPoint;i++) {
			System.out.println(finalPoints[i]);
		}*/
		//System.out.println(finalPoints);
		
		totalPoint=0; //to return points to zero
		return finalPoints;
	}
	public static char[][] toChar(String[] str) {
	    char[][] charArray = new char[ySize][xSize];
	    //System.out.println(str.length);

	    for (int i = 0; i < str.length; i++) {
	    	
	    	
	    		charArray[i]=str[i].toCharArray(); 		//fun to change string into 2-d array 
	    }
	    return charArray;
	}
	
	public static void  searching(int arr[][],int yindix,int xindex,int searched) {
		 if(arr[yindix][xindex]==searched){
			 
			 cells++;
			 if (yindix<ymin)
				 ymin=yindix;
			 if (yindix>ymax)
				 ymax=yindix;
			 if (xindex<xmin)
				 xmin=xindex;
			 if (xindex>xmax)
				 xmax=xindex;  						//recursion function to know shapes
			 arr[yindix][xindex]=10;
                 if(yindix+1<ySize) {
                	 searching(arr, yindix + 1, xindex,searched);
                 }
                 if(yindix-1>-1) {
                	 searching(arr, yindix-1, xindex,searched);
                 }
                 if( xindex+1<xSize) {
                	 searching(arr, yindix, xindex+1,searched);
                 }
                 if( xindex-1>-1) {
                	 searching(arr, yindix , xindex-1,searched);
                 }
                
		 }

	}
	
	
}	
