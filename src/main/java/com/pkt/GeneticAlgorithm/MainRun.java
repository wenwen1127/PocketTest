package com.pkt.GeneticAlgorithm;
/**
 * ������������
 */

public class MainRun {

	public static String[] main() {
		// TODO Auto-generated method stub
		
		//�����Ŵ��㷨��������
		GeneticAlgorithm GA=new GeneticAlgorithm();
		
		//������ʼ��Ⱥ
		SpeciesPopulation speciesPopulation = new SpeciesPopulation();

		//��ʼ�Ŵ��㷨��ѡ�����ӡ��������ӡ��������ӣ�
		SpeciesIndividual bestRate=GA.run(speciesPopulation);

		//��ӡ·������̾���
		bestRate.printRate();
		return bestRate.genes;

	}

}
