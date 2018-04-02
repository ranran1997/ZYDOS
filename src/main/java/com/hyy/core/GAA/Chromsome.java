package com.hyy.core.GAA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengYanYan on 2016/5/12  0:34
 */
public class Chromsome {

    //字符串A~Z,a~z表示单个城市
    private static final String genes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   /* private static final char[] chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P', 'Q','R','S',
            'T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z'};*/
    private static double referFitness;
    private static double[][] distance; //Double[1][2]的值表示城市1到城市2的距离
    private static int chromSize;


    private String path; //路径
    private double fitness; //染色体适应度
    private double p;//染色体在当前种群中的选择概率

    /********************************    Step  1.1.1   初始化染色体     ******************************/
    public Chromsome(Integer chromSize,double[][] distance,String referPath) { //初始化静态成员变量，所有对象共用
        Chromsome.distance = distance;
        Chromsome.chromSize = chromSize;
        if(referPath != null){
            Chromsome.referFitness = getFitness(referPath);
        }else Chromsome.referFitness = 0.50;
    }

    public Chromsome(int chromSize){
        this.path = inialChrom(chromSize);
        this.fitness = getFitness(path);
    }

    public Chromsome(String path){
        this.path = path;
        this.fitness = getFitness(path);
    }

    public Chromsome(String path,double fitness){
        this.path = path;
        this.fitness = fitness;
    }

    /********************************
     * Step  1.1.1.1   初始化染色体具体实现
     *******************************/
    public String inialChrom(int chromSize) {
        List<Character> allowedChroms = new ArrayList<Character>();
        for (int i = 0; i < chromSize; i++) {
            allowedChroms.add(genes.charAt(i));//备选基因组
        }

        String path = ""; //path表示整条路径
        for (int i = 0; i < chromSize; i++) {
            int index = (int) ((Math.random() * 100) % (allowedChroms.size()));
            path += allowedChroms.get(index);
            allowedChroms.remove(index); //确保基因不重复
        }
        return path;
    }

    /********************************    Step  1.1.1.1   计算染色体适应度     ******************************/
    /**
     * 适应度计算，适应度函数取路径长度Td的倒数 ，即 f = 1/（Td+x）
     * @param path 染色体编码
     * @return
     */
    public static Double getFitness(String path) {
        int i, j, temp = decodeGene(path.charAt(0));
        Double len = distance[0][temp+1];  //路径长加上起点到染色体0编码位置对应的地点的路程
        for (int index = 1; index < path.length(); index++) {
            i = temp;
            j = decodeGene(path.charAt(index));
            temp = j;
            len += distance[i+1][j+1];
        }
        return 1 / len;
    }

    //解码
    public static List<Integer> decodePath(String path) {
        List<Integer> result = new ArrayList<Integer>();
        for(int i =0;i<chromSize;i++){
            result.add(decodeGene(path.charAt(i)));
        }
        return result;
    }

    //根据编码基因获取其原本编号，对应基因库中该基因的位置
    public static int decodeGene(char gene){
        return Chromsome.genes.indexOf(gene);
    }



    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public String getPath() {
         return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getP() {
        return p;
    }

    public void setP(Double p) {
        this.p = p;
    }

}
