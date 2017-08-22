/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.helper;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.model.QuadrantModel;
import com.optum.oss.model.VulnCatagoryModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author mrejeti
 */
@Component
public class ReportsAndDashboardHelper {
    
     private static final Logger logger = Logger.getLogger(ReportsAndDashboardHelper.class);
     
     public String quadrantSelection(String x1, String y1){
         double x= Double.parseDouble(x1);
         double y= Double.parseDouble(y1);
         String Q = ApplicationConstants.EMPTY_STRING;
           if(x>=0 && x<=4){
            if(y>=0 && y<=4){
                Q = "Q1";
            }else if(y>4 && y<=7){
                //q4
                Q = "Q4";
            }else{
                //q7
                 Q = "Q7";
            }
        }
         if(x>4 && x<=7){
            if(y>=0 && y<=4){
                //Q2
                 Q = "Q2";
            }else if(y>4 && y<=7){
                //q5
                 Q = "Q5";
            }else{
                //Q8
                 Q = "Q8";
            }
        }
         if(x>7 && x<=10){
            if(y>=0 && y<=4){
                //Q3
                 Q = "Q3";
            }else if(y>4 && y<=7){
                //q6
                 Q = "Q6";
            }else{
                //Q9
                 Q = "Q9";
            }
        }
         
         return Q;
     }
     
     public HashMap quadrantPositionsCreation(){
         List<QuadrantModel> qList = new ArrayList();
          QuadrantModel q1 = new QuadrantModel(0, 4, 0, 4);
          q1.setQuadrantNo("Q1");
          qList.add(q1);
          QuadrantModel q2 = new QuadrantModel(4, 8, 0, 4);
          q2.setQuadrantNo("Q2");
          qList.add(q2);
          QuadrantModel q3 = new QuadrantModel(8, 12, 0, 4);
          q3.setQuadrantNo("Q3");
          qList.add(q3);
          QuadrantModel q4 = new QuadrantModel(0, 4, 4, 8);
          q4.setQuadrantNo("Q4");
          qList.add(q4);
          QuadrantModel q5 = new QuadrantModel(4, 8, 4, 8);
          q5.setQuadrantNo("Q5");
          qList.add(q5);
          QuadrantModel q6 = new QuadrantModel(8, 12, 4, 8);
          q6.setQuadrantNo("Q6");
          qList.add(q6);
          QuadrantModel q7 = new QuadrantModel(0, 4, 8, 12);
          q7.setQuadrantNo("Q7");
          qList.add(q7);
          QuadrantModel q8 = new QuadrantModel(4, 8, 8, 12);
          q8.setQuadrantNo("Q8");
          qList.add(q8);
          QuadrantModel q9 = new QuadrantModel(8, 12, 8, 12);
          q9.setQuadrantNo("Q9");
          qList.add(q9);
          
         double xDiff = 0.5;
         double yDiff = 0.7;
         HashMap qMap = null;
         HashMap totalQuadMap = new HashMap();
         for (QuadrantModel qlist1 : qList) {
             int h = 1;
              qMap = new HashMap();
             for (double y = qlist1.getY1(); y < qlist1.getY2() - yDiff;) {
                 y = y + yDiff;
                 for (double x = qlist1.getX1(); x < qlist1.getX2() - 1;) {
                     x = x + xDiff;
                     VulnCatagoryModel vcModel = new VulnCatagoryModel();
                     vcModel.setxPosition(x);
                     vcModel.setyPosition(y);
                     qMap.put(h, vcModel);
                     h++;
                 }

             }
             totalQuadMap.put(qlist1.getQuadrantNo(), qMap);
         }
         return totalQuadMap;
     }

   
}
