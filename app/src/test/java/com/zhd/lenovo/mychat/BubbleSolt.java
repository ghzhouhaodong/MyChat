package com.zhd.lenovo.mychat;

/**
 * Created by lenovo on 2017/8/8.
 */

public class BubbleSolt {

       public static void main(String[] args){
          int [] ints=new int[]{4,2,5,14,73,25};
            Bsolt(ints);
         for(int x=0; x< ints.length; x++){
             System.out.println("x = " + ints[x]);

         }
       }


    public static void Bsolt(int [] s){
       int temp;
      for(int x=0; x<s.length-1 ; x++){
           for(int j= 0; j < s.length-1-x; j++){
                 if(s[x]>s[j]){
                     temp = s[x] ;
                     s[x]=s[j];
                     s[j]=temp;

                 }

           }


      }




    }




}
