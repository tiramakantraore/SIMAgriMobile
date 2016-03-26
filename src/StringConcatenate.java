/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiramakan
 */
public class StringConcatenate {
   String[] arr1;
   String[] arr2 ; 
   String[] both ; 
   StringConcatenate(String[] parr1,String[] parr2)  {
       arr1=parr1;
       arr2=parr2;   
        //Create two arrays to concatenate and one array to hold both
	
	String[] arrBoth = new String[arr1.length+arr2.length];

    //Copy elements from first array into first part of new array
	for(int i = 0; i < arr1.length; i++){
		arrBoth[i] = arr1[i];
	}

    //Copy elements from second array into last part of new array
	for(int j = arr1.length;j < arrBoth.length;j++){
		arrBoth[j] = arr2[j-arr1.length];
	}

 
       both=new String[arrBoth.length];

   
   }  

}