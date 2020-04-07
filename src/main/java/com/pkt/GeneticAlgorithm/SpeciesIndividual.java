package com.pkt.GeneticAlgorithm;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ������
 * ������
 * 		1.createByRandomGenes ��ʼ���ֻ���(���) ����ֱ���ó������б���
 * 		2.calFitness ����������Ӧ��
 * 		3.printRate ��ӡ·��
 */

public class SpeciesIndividual {
	
	String[] genes;//��������
	float fitness;//��Ӧ��
	float rate;
	SpeciesIndividual next;

	TSPData tspData = new TSPData();
	
	SpeciesIndividual()
	{
		//��ʼ��
		this.genes=new String[TSPData.SUITE_NUM];
		this.fitness=0.0f;
		this.next=null;
		rate=0.0f;
	}
	
	//��ʼ���ֻ���(���)
	void createByRandomGenes()
	{
		//��ʼ������Ϊ1-CITY_NUM����
		for(int i = 0;i < genes.length;i++)
		{
			genes[i]=Integer.toString(i);
		}
		
		//��ȡ�������
		Random rand=new Random();

		for(int j=0;j<genes.length;j++)
		{
			int num= j + rand.nextInt(genes.length-j);
			
			//����
			String tmp;
			tmp=genes[num];
			genes[num]=genes[j];
			genes[j]=tmp;
		}
	}

	
	//����������Ӧ��
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
		int[] f = new int[TSPData.POINT_NUM]; //���Ե����Ҫ�̶�
		int numerator = 0;   //����
		float denominator = 0;   //��ĸ
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
			float ord_cost = Float.valueOf(TSPData.suiteList.get(Integer.valueOf(this.genes[ord])).get("run_time").toString());  //ord suite�ĳɱ�
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
	
	//���
	public SpeciesIndividual clone()
	{	
		SpeciesIndividual species=new SpeciesIndividual();
		
		//����ֵ
		for(int i=0;i<this.genes.length;i++)
			species.genes[i]=this.genes[i];
		species.fitness=this.fitness;
		return species;	
	}
	
	//��ӡ·��
	void printRate()
	{
		System.out.print("ִ��˳��Ϊ��");
		for(int i=0;i<this.genes.length;i++)
			System.out.print(this.genes[i]+"->");
		System.out.print("\n"+this.fitness);
	}

}
