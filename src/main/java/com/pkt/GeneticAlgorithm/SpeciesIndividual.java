package com.pkt.GeneticAlgorithm;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 个体类
 * 包含：
 * 		1.createByRandomGenes 初始物种基因(随机) 基因直接用城市序列编码
 * 		2.calFitness 计算物种适应度
 * 		3.printRate 打印路径
 */

public class SpeciesIndividual {
	
	String[] genes;//基因序列
	float fitness;//适应度
	float rate;
	SpeciesIndividual next;

	TSPData tspData = new TSPData();
	
	SpeciesIndividual()
	{
		//初始化
		this.genes=new String[TSPData.SUITE_NUM];
		this.fitness=0.0f;
		this.next=null;
		rate=0.0f;
	}
	
	//初始物种基因(随机)
	void createByRandomGenes()
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
	}

	
	//计算物种适应度
	void calFitness()
	{
//		float totalDis=0.0f;
//		for(int i = 0;i < TSPData.SUITE_NUM;i++)
//		{
//			int curCity=Integer.parseInt(this.genes[i])-1;
//			int nextCity=Integer.parseInt(this.genes[(i+1) % TSPData.SUITE_NUM])-1;
//
//			totalDis += TSPData.disMap[curCity][nextCity];
//		}
//
//		this.distance=totalDis;
//		this.fitness=1.0f/totalDis;
		int[] f = new int[TSPData.POINT_NUM]; //测试点的重要程度
		int numerator = 0;   //分子
		float denominator = 0;   //分母
//		System.out.println("%%%%%%%%%" + TSPData.POINT_NUM + TSPData.pointList + "\n" + "&&&&&&&&&&" + TSPData.SUITE_NUM + TSPData.suiteList);
		int sum_f_all=0;
		for(int i=0; i<TSPData.POINT_NUM; i++){
			int ord=0;
			Map pointData = TSPData.pointList.get(i);
			f[i] = Integer.valueOf(pointData.get("level").toString());
//			System.out.println("ffffffffff"+f[i]);
			for(int k = 0;k < TSPData.SUITE_NUM; k++){
				if(((List)TSPData.suiteList.get(Integer.valueOf(this.genes[k])).get("testpointList")).contains(Long.valueOf(pointData.get("testpoint_id").toString()))){
					ord = k;
					break;
				}
			}
			float sum_cost = 0;
			for(int m=ord; m<TSPData.SUITE_NUM; m++){
				Map suiteData = TSPData.suiteList.get(Integer.valueOf(this.genes[m]));
//				System.out.println("suiteData_id"+suiteData.get("suite_id"));
				sum_cost += Float.valueOf(suiteData.get("run_time").toString());
			}
			float ord_cost = Float.valueOf(TSPData.suiteList.get(Integer.valueOf(this.genes[ord])).get("run_time").toString());  //ord suite的成本
			numerator += f[i]*(sum_cost-(0.5)*ord_cost);
			sum_f_all += f[i];
		}
		int sum_cost_all = 0;
        for(int j = 0;j < TSPData.SUITE_NUM;j++){
			Map suiteData = TSPData.suiteList.get(Integer.valueOf(this.genes[j]));
			sum_cost_all += Float.valueOf(suiteData.get("run_time").toString());
        }
		denominator = sum_cost_all * sum_f_all;
//		System.out.println("@@@@@@" + denominator + "$$$$$$$$" + numerator);
		this.fitness = ((float)numerator)/denominator;
//		System.out.println("#######"+this.fitness);
	}
	
	//深拷贝
	public SpeciesIndividual clone()
	{	
		SpeciesIndividual species=new SpeciesIndividual();
		
		//复制值
		for(int i=0;i<this.genes.length;i++)
			species.genes[i]=this.genes[i];
		species.fitness=this.fitness;
		return species;	
	}
	
	//打印路径
	void printRate()
	{
		System.out.print("执行顺序为：");
		for(int i=0;i<this.genes.length;i++)
			System.out.print(this.genes[i]+"->");
		System.out.print("\n"+this.fitness);
	}

}
