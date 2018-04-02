package com.hyy.core.GA;

import com.hyy.utils.NumberUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HengYanYan on 2016/5/2  14:55
 */
public class GA {

    private static double referFitness;

    private int popsize; //种群大小 //Todo popsize种群规模如何确定
    private List<Chromsome> currentPop;//种群
    private List<Chromsome> childPop;//子代种群
    private List<Double> childPopFitness;

    private int MAX_GEN;
    private double[][] distance;
    private int chromSize;
    private double p_c_t; //Todo p_c_t交叉率如何确定
    private double p_m_t; //Todo p_m_t变异率如何确定
    private double p_s_t;//选择概率
    //字符串A~Z,a~z表示单个城市
    private final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    /********************************    Step  1   初始化参数及种群        **********************************/
    /**
     * @param p_m_t     变异概率
     * @param p_c_t     交叉概率
     * @param popsize   种群规模
     * @param MAX_GEN   运行代数
     * @param chromSize 染色体长度
     * @param distance
     */
    public GA(int chromSize, int popsize, double[][] distance, int MAX_GEN, double p_c_t, double p_m_t, String referPath) {
        this.popsize = popsize;
        this.MAX_GEN = MAX_GEN;
        this.chromSize = chromSize;
        this.distance = distance;
        this.p_c_t = p_c_t;
        this.p_m_t = p_m_t;
        inialChromsome(chromSize, popsize, distance);
        getSelectProbability(referPath);
    }

    public GA() {
    }
    /********************************    Step  1.1   初始化种群具体实现     ******************************/
    /**
     * 初始化种群
     *
     * @param chromSize 基因个数，染色体长度
     * @param popsize   种群规模
     */
    private void inialChromsome(int chromSize, int popsize, double[][] distance) {
        List<Chromsome> chromosomes = new ArrayList<Chromsome>();
        new Chromsome(chromSize, distance); //初始化静态成员变量
        Chromsome chromsome;
        //采用无重串的稳定繁殖，即种群个体不重复
        for (int i = 0; i < popsize; i++) {
            do {
                chromsome = new Chromsome(chromSize);
            } while (chromosomes.contains(chromsome));
            chromosomes.add(chromsome);
        }
        this.currentPop = chromosomes;
    }

    /********************************
     * Step  1.2   设置染色体适应度对比值
     ******************************/

    private void getSelectProbability(String referPath) {
        GA.referFitness = Chromsome.getFitness("HGFDEABC");
        this.p_s_t = 1;
    }

    /******************************
     * Core **  Step 2   迭代求解
     ****************************/
    private static int mutationTimes =1;
    public List<Integer> solve() {
        for (int i = 0; i < MAX_GEN; i++) {
            evolve(i);

            //测试输出
            int k=1;
            List<Double> fits = new ArrayList<Double>();
            for(Chromsome pop :currentPop) {
                fits.add(pop.getFitness());
            }
            Arrays.sort(fits.toArray());

            System.out.println("第" + i + "代,种群规模：" + popsize + "最大适应度"+fits.get(0));

            for(Chromsome pop :currentPop.subList(0,20)){
                System.out.print(pop.getPath() + "  " + pop.getFitness());
                if(k%10==0){
                    System.out.println();
                }
                k++;
            }
            System.out.println();
            System.out.println("变异了" + mutationTimes + "  ");
        }
        //选出最优个体
        Chromsome bestChrom = currentPop.get(0);
        for (Chromsome chromsome : currentPop) {
            if (chromsome.getFitness() > bestChrom.getFitness())
                bestChrom = chromsome;
        }
        List<Integer> bestPath = new ArrayList<Integer>();
        for (int i = 0; i < chromSize; i++) {
            bestPath.add(bestChrom.getCity(i));
        }
        System.out.println("最优为" + bestPath);
        return bestPath;
    }


    private void evolve(int gen) {
        this.childPopFitness = new ArrayList<Double>();
        this.childPop = new ArrayList<Chromsome>();
        //迭代产生popSize个子代
        while (this.childPop.size() < popsize) {
            int[] farthers = selection(2);
            crossover(farthers[0], farthers[1]);
            //mutation(selection(1)[0]);
            // 05/11 变异算子由轮盘赌选择改为随机选择
            mutation((int) (Math.random()*10));
        }
        currentPop = childPop;
        this.popsize = childPop.size();
    }




    /*遗传操作  start*/

    /********************************************
     * Core **  Step 2.1    轮盘赌选择操作
     ********************************************/
    private int[] selection(int num) {
        //计算当前时代种群中个体的适应度之和
        Double sumfitness = (double) 0;

        for (Chromsome chromosome : currentPop) {
            sumfitness += chromosome.getFitness();
        }

        //计算个体在当前世代种群中个体被选择的概率
        for (Chromsome chromosome : currentPop) {
            chromosome.setP(chromosome.getFitness() / sumfitness);
        }

        //计算当前世代种群的累积概率
        Double[] cumulateP = new Double[popsize];
        int i = 0;
        double temp = 0;
        for (Chromsome chromosome : currentPop) {
            cumulateP[i] = temp + chromosome.getP();
            temp = cumulateP[i];
            i++;
        }
        //采用轮盘选择方式进行选择，选出的2个个体进行变异交叉操作
        List<Integer> selectedChrom = new ArrayList<Integer>();
        int[] selected = new int[num];
        for (int j = 0; j < num; j++) {
            int selectChrom = this.select(cumulateP);
            while (selectedChrom.contains(selectChrom)) {
                selectChrom = this.select(cumulateP);
            }
            selectedChrom.add(selectChrom);
            selected[j] = selectChrom;
        }
        return selected;
    }

    //采用轮盘赌选择方式进行选择，
    private int select(Double[] cumulateP) {
        double r = Math.random();
        int temp1 = 0;
        if (r <= cumulateP[0]) {
            temp1 = 0;
        }
        for (int k = 0; k < popsize; k++) {
            if (r < cumulateP[k]) {
                temp1 = k;
                break;
            }
        }//第k个染色体被选中
        return temp1;
    }

    /******************************* Core **  Step 2.1    顺序交叉操作       ****************************/
    //Todo 本段代码可简化
    private void crossover(int farther1, int farther2) {
        //顺序交叉：。如果生成的随机数小于交叉概率,则进行交叉操作,随机生成两个交叉点
        if (Math.random() < p_c_t) {
            Integer[] pos = NumberUtil.getRandonNumber(2, chromSize);//两个交叉点的位置
            int pos1 = pos[0];
            int pos2 = pos[1];
            //保证pos1为第一个交叉点

            //两个交叉点位置分割两个父个体,、、存放父个体分割后的三段字符串路径,、、存放父个体分割后的三段字符串路径。
            Chromsome f1 = currentPop.get(farther1);
            String f1temp1 = f1.getPath().substring(0, pos1);
            String f1temp2 = f1.getPath().substring(pos1, pos2);
            String f1temp3 = f1.getPath().substring(pos2);

            Chromsome f2 = currentPop.get(farther2);
            String f2temp1 = f2.getPath().substring(0, pos1);
            String f2temp2 = f2.getPath().substring(pos1, pos2);
            String f2temp3 = f2.getPath().substring(pos2);

            //按OX交叉操作规则生成子个体child1和child2
            String childPath1 = this.OX(f2temp1, f2temp2, f2temp3, f1temp2);
            String childPath2 = this.OX(f1temp1, f1temp2, f1temp3, f2temp2);

            /*String[] childStrings = new String[2];
            childStrings[0] = child1;
            childStrings[1] = child2;
            for (String childString : childStrings) {
                Chromsome childChrom = new Chromsome(childString);
                double childFitness = childChrom.getFitness();

                //若子代中已有相同适应度个体，则不添加进子代，以加速收敛
                if (!this.childPopFitness.contains(childFitness)) {
                    childPop.add(childChrom);
                    childPopFitness.add(childFitness);
                }

            }*/

            Chromsome childChrom1 = new Chromsome(childPath1);
            Chromsome childChrom2 = new Chromsome(childPath2);

            double childFitness1 = childChrom1.getFitness();
            double childFitness2 = childChrom2.getFitness();


            //若子代中已有相同适应度个体，则不添加进子代，以加速收敛
            if (this.childPopFitness== null||!this.childPopFitness.contains(childFitness1)) {
                childPop.add(childChrom1);
                childPopFitness.add(childFitness1);
            }
            if (!this.childPopFitness.contains(childFitness2)) {
                childPop.add(childChrom2);
                childPopFitness.add(childFitness2);
            }


            /***************     稳定繁殖策略      ************************/
            //若子代的适应度高则替换父代继续进行遗传操作,否则舍弃生成的子代。
            if (childChrom1.getFitness() > f1.getFitness()) {
                f1.setPath(childPath1);
                f1.setFitness(childFitness1);
                //chromosomes.set(farther1, childChrom1);
            }


            if (childChrom2.getFitness() > f2.getFitness()) {
                f2.setPath(childPath2);
                f2.setFitness(childFitness2);
                //chromosomes.set(farther2, childChrom2);
            }
        }
    }

    /***************     顺序交叉操作具体实现     ************************/
    private String OX(String str1, String str2, String str3, String str) {

        String temp = str3.concat(str1).concat(str2);
        for (int i = 0; i < str.length(); i++) {
            //int k = temp.indexOf(str.charAt(i));
            temp = temp.replace(str.charAt(i), ' ');
        }
        temp = temp.replace(" ", "");
        temp = temp.trim();  //trim()剔除首尾空格

        str3 = temp.substring(0, str3.length());
        str1 = temp.substring(str3.length());


        return str1.concat(str).concat(str3);
    }

    /******************************* Core **  Step 2.1    启发式变异操作       ****************************/
    private void mutation(int k) {
        /*如果生成的随机数小于变异概率,则对个体进行启发式变异操作。随机选择
         三个变异点、和,并对其位置排序,任意交换三个城市位置可以得
         到个不同个体,从中选择适应度最好的个体作为新的个体。*/
        if (Math.random() < p_m_t) {

            Chromsome chromosome = currentPop.get(k);

            Integer[] pos = NumberUtil.getRandonNumber(3, chromSize);//生成三个变异位置
            List<String> mutationPaths = mutate(pos,chromosome.getPath());

            //遍历变异,取最优变异个体
            String bestMutation = mutationPaths.get(0);
            for(int i=1;i<mutationPaths.size();i++){
                if(Chromsome.getFitness(bestMutation) < Chromsome.getFitness(mutationPaths.get(i))){
                    bestMutation = mutationPaths.get(i);
                }
            }

            //与变异前个体比较，优胜劣汰
            double newFitness = Chromsome.getFitness(bestMutation);
            if(newFitness > chromosome.getFitness()){
                chromosome.setPath(bestMutation);
                chromosome.setFitness(newFitness);
                mutationTimes++;
                if (!this.childPopFitness.contains(newFitness)) {
                    childPop.add(new Chromsome(bestMutation));
                    childPopFitness.add(newFitness);
                }
            }
        }
    }
    private List<String> mutate(Integer[] pos,String oldPath){
        List<String> mutationPaths = new ArrayList<String>(); //保存所有变异可能的结果路径

        int num = pos.length;  //num个变异位置
        List<String> mutateRole = getMutateRole(num);

        String baseStr = "";
        for(int i=1;i<=num;i++){
            baseStr = baseStr.concat(String.valueOf(i));
        }
        mutateRole.remove(baseStr); //剔除变异点正常顺序

        for(String role: mutateRole){ //根据变异格式循环获得变异结果路径
            String mutationPath = "";
            for(int i=0;i<role.length();i++){
                if(i==0){
                    mutationPath = mutationPath.concat(oldPath.substring(0,pos[i]));
                }else{
                    //+1 半小时！！！
                    mutationPath = mutationPath.concat(oldPath.substring(pos[i-1]+1,pos[i]));
                }
                Integer index = Integer.parseInt(role.charAt(i)+"");
                mutationPath = mutationPath.concat(String.valueOf(oldPath.charAt(pos[index-1])));
            }
            if(pos[role.length()-1] < oldPath.length()-1){
                mutationPath = mutationPath.concat(oldPath.substring(pos[role.length()-1]+1));
            }
            mutationPaths.add(mutationPath);
        }
        return mutationPaths;
    }
    /*根据变异点个数，给变异点编码："12..n",循环得出可能的变异情况，以点的排列顺序展示：
    如3个变异点，编码为“123”；可能的变异情况有"132" "213" "231" "312" "321"
    该方法返回包括正常情况的*/
    private List<String> getMutateRole(int n){
        List<String> mutateRole = new ArrayList<String>();
        if(n == 2){
            mutateRole.add("12");
            mutateRole.add("21");
        }else if(n > 2){
            for(int i = n;i >2;i--){
                List<String> strings = getMutateRole(n-1);
                for(String str:strings){

                    for(int j=0;j <= str.length();j++){
                        String newStr = "";
                        newStr = newStr.concat(str.substring(0,j)).concat(String.valueOf(i)).concat(str.substring(j,str.length()));
                        mutateRole.add(newStr);
                    }
                }
            }
        }
        return mutateRole;
    }

    public void setCurrentPop(List<Chromsome> currentPop) {
        this.currentPop = currentPop;
    }

    public List<Chromsome> getCurrentPop() {
        return currentPop;
    }

    public Integer getPopsize() {
        return popsize;
    }

    public void setPopsize(Integer popsize) {
        this.popsize = popsize;
    }

    public double[][] getDistance() {
        return distance;
    }

    public void setDistance(double[][] distance) {
        this.distance = distance;
    }

    public Integer getChromSize() {
        return chromSize;
    }

    public void setChromSize(Integer chromSize) {
        this.chromSize = chromSize;
    }

    public int getMAX_GEN() {
        return MAX_GEN;
    }

    public void setMAX_GEN(int MAX_GEN) {
        this.MAX_GEN = MAX_GEN;
    }

}
