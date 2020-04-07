package com.pkt.Common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Test {
    public static String[] createByRandomGenes(String[] genes)
    {
        //初始化基因为1-CITY_NUM序列
        for(int i = 0;i < genes.length;i++)
        {
            genes[i]=Integer.toString(i);
        }

        //获取随机种子
        Random rand=new Random();

        for(int j=0;j<genes.length;j++)
        {
            int num= j + rand.nextInt(genes.length-j);

            //交换
            String tmp;
            tmp=genes[num];
            genes[num]=genes[j];
            genes[j]=tmp;
        }
        return genes;
    }

        public static float calFitness(List<Map> pointList, List<Map> suiteList, int SUITE_NUM, int POINT_NUM, String[] genes)
        {
            float fitness;


            int[] f = new int[POINT_NUM]; //测试点的重要程度
            int numerator = 0;   //分子
            float denominator = 0;   //分母
//		System.out.println("%%%%%%%%%" + TSPData.POINT_NUM + TSPData.pointList + "\n" + "&&&&&&&&&&" + TSPData.SUITE_NUM + TSPData.suiteList);
            int sum_f_all=0;
            for(int i=0; i<POINT_NUM; i++){
                int ord=0;
                Map pointData = pointList.get(i);
                System.out.println("pointData: " + pointData);
                f[i] = Integer.valueOf(pointData.get("level").toString());
//                System.out.println("ffffffffff"+f[i]);
                for(int k = 0;k < SUITE_NUM; k++){
//                    System.out.println("!!!!!!!!" + (List)suiteList.get(Integer.valueOf(genes[k])).get("testpointList") + "\n *********" + pointData.get("testpoint_id"));
                    if(((List)suiteList.get(Integer.valueOf(genes[k])).get("testpointList")).contains(Long.valueOf(pointData.get("testpoint_id").toString()))){
//                        System.out.println("KKKKKKK: "+k);
                        ord = k;
                        break;
                    }
                }
                float sum_cost = 0;
                for(int m=ord; m<SUITE_NUM; m++){
                    Map suiteData = suiteList.get(Integer.valueOf(genes[m]));
                    System.out.println("suiteData_id: "+suiteData.get("suite_id")+ "run_time"+ suiteData.get("run_time").toString());
                    sum_cost += Float.valueOf(suiteData.get("run_time").toString());
                }
                float ord_cost = Float.valueOf(suiteList.get(Integer.valueOf(genes[ord])).get("run_time").toString());  //ord suite的成本
                numerator += f[i]*(sum_cost-(0.5)*ord_cost);
                sum_f_all += f[i];
            }
            float sum_cost_all = 0;
            for(int j = 0;j < SUITE_NUM;j++){
                Map suiteData = suiteList.get(Integer.valueOf(genes[j]));
                sum_cost_all += Float.valueOf(suiteData.get("run_time").toString());
            }
            denominator = sum_cost_all * sum_f_all;
		System.out.println("@@@@@@" + denominator + "$$$$$$$$" + numerator +"fff:" +sum_f_all +"ccc"+sum_cost_all);
            fitness = ((float)numerator)/denominator;
            System.out.println("#######"+fitness);
            return fitness;
        }

        public static void test(List<Map> pointList, List<Map> suiteList, int SUITE_NUM, int POINT_NUM){
            String[] genes = {"4","3", "2","1", "0"};
//            float sum_f = 0;
//            for(int k=0; k<10; k++){
//                genes = createByRandomGenes(genes);
//                System.out.println("genes: " + genes);
//                float fitness = calFitness(pointList, suiteList, SUITE_NUM, POINT_NUM,  genes);
//                sum_f += fitness;
//            }
//            System.out.println("平均值：" + sum_f/10);

            float fitness = calFitness(pointList, suiteList, SUITE_NUM, POINT_NUM,  genes);


        }

}
