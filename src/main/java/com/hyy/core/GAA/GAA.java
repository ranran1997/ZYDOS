package com.hyy.core.GAA;

import com.hyy.utils.NumberUtil;
import com.hyy.utils.ObjectUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HengYanYan on 2016/5/12  0:33
 */
public class GAA {

    private static double referFitness;

    private int popsize;  //Todo popsize种群规模
    private List<Chromsome> currentPop;//种群
    private LinkedList<Chromsome> childPop;//子代种群
    private List<Double> childPopFitness;

    private int MAX_GEN;
    private double[][] distance;
    private int chromSize;
    private double p_c_t; //Todo p_c_t交叉率
    private double p_m_t; //Todo p_m_t变异率
    private double p_s_t;//Todo 选择概率

    /********************************
     * Step  1   初始化参数及种群
     **********************************/

    public GAA() {

    }

    /**
     * @param p_m_t     变异概率
     * @param p_c_t     交叉概率
     * @param popsize   种群规模
     * @param MAX_GEN   运行代数
     * @param chromSize 染色体长度
     * @param distance  基因间距
     */
    public GAA(int chromSize, int popsize, double[][] distance, int MAX_GEN, double p_s_t, double p_c_t, double p_m_t, String referPath) {
        this.popsize = popsize;
        this.MAX_GEN = MAX_GEN;
        this.chromSize = chromSize;
        this.distance = distance;
        this.p_c_t = p_c_t;
        this.p_m_t = p_m_t;
        this.p_s_t = p_s_t;
        inialChromsome(chromSize, popsize, distance, referPath);
    }

    /********************************    Step  1.1   初始化种群具体实现     ******************************/
    /**
     * 初始化种群
     *
     * @param chromSize 基因个数，染色体长度
     * @param popsize   种群规模
     */
    private void inialChromsome(int chromSize, int popsize, double[][] distance, String referPath) {
        currentPop = new ArrayList<Chromsome>();
        LinkedList<Chromsome> chromsomes = new LinkedList<Chromsome>();  //初始化为链表，提高插入新个体效率
        new Chromsome(chromSize, distance, referPath); //初始化静态成员变量
        Chromsome chromsome;
        //采用无重串的稳定繁殖，即种群个体不重复
        for (int i = 0; i < popsize; i++) {
            do {
                chromsome = new Chromsome(chromSize);
            } while (chromsomes.contains(chromsome));//种群个体互不相同,适应度可能不同;
            addInOrder(chromsomes, chromsome);
        }
        this.currentPop = chromsomes;
    }


    /*******************************
     * Core **  Step 2   迭代求解
     ****************************/
    private static int mutationTimes = 1;

    public List<Integer> solve() {

        int gen = 0;
        do {


            evolve(gen); //遗传操作
            gen++;
            for(int i =0;i<10;i++){
                //System.out.println(currentPop.get(i).getPath()+"  "+currentPop.get(i).getFitness());
            }

            System.out.println("第" + gen + "代,种群规模：" + popsize + " ");

            //Todo  迭代结束条件：适应度达到某值（如：0.987）。仅为测试假设值 可行性待定
        } while (gen < MAX_GEN && currentPop.get(0).getFitness() < 0.987);

        //选出最优个体
        Chromsome bestChrom = currentPop.get(0);
        //解码
        List<Integer> bestPath = Chromsome.decodePath(bestChrom.getPath());

        System.out.println("最优为" + bestPath);

        return bestPath;
    }

    /*******************************
     * Core **遗传操作 串并行结合+稳定繁殖策略 ，获得新种群
     ****************************/
    private void evolve(int gen) {
        int times = 0,mutateTimes=0;

        //初始化
        this.childPopFitness = new ArrayList<Double>();
        this.childPop = new LinkedList<Chromsome>();

        //迭代产生popSize个子代
        Double[] cumulateP = this.selectedP(currentPop);

//选择一定数量的父代优秀个体，直接复制进子代种群
            childPop.addAll(selection(currentPop, cumulateP, null));
            for (Chromsome chromsome : childPop) {
                System.out.println(chromsome.getPath() + "  " + chromsome.getFitness());
                childPopFitness.add(chromsome.getFitness());
            }




        //继续遗传至合适规模
        boolean flag = false; //是否变更父代种群
        while (this.childPop.size() < popsize) {
            double random = Math.random();

            //顺序交叉：。如果生成的随机数小于交叉概率,则进行交叉操作,随机生成两个交叉点
            if (random < p_c_t) {
                if (flag){
                    times++;
                    cumulateP = this.selectedP(currentPop);
                }

                //父代继续进行交叉操作
                flag = crossover(currentPop, cumulateP);
            }

           /*如果生成的随机数小于变异概率,则对个体进行启发式变异操作。随机选择
           三个变异点、和,并对其位置排序,任意交换三个城市位置可以得
           到个不同个体,从中选择适应度最好的个体作为新的个体。*/
            if (random < p_m_t) {
                if (flag){
                    mutateTimes++;
                    cumulateP = this.selectedP(currentPop);
                }


                flag = mutation(currentPop,cumulateP);
            }
        }
        currentPop = childPop.subList(0,popsize);

        System.out.println("遗传结束，交叉次数"+times+"上代变异次数"+mutateTimes);
    }

    //计算种群个体选择概率,
    private Double[] selectedP(List<Chromsome> pop) {
        //计算当前时代种群中个体的适应度之和
        Double sumfitness = (double) 0;

        for (Chromsome chromosome : pop) {
            sumfitness += chromosome.getFitness();
        }

        //计算个体在当前世代种群中个体被选择的概率
        for (Chromsome chromosome : pop) {
            chromosome.setP(chromosome.getFitness() / sumfitness);
        }

        //计算当前世代种群的累积概率
        Double[] cumulateP = new Double[popsize];
        int i = 0;
        double temp = 0;
        for (Chromsome chromosome : pop) {
            cumulateP[i] = temp + chromosome.getP();
            temp = cumulateP[i];
            i++;
        }

        //采用轮盘选择方式进行选择，选出的2个个体进行变异交叉操作
       /* List<Integer> selectedChrom = new ArrayList<Integer>();
        int[] selected = new int[num];
        for (int j = 0; j < num; j++) {
            int selectChrom = this.select(cumulateP);
            while (selectedChrom.contains(selectChrom)) {
                selectChrom = this.select(cumulateP);
            }
            selectedChrom.add(selectChrom);
            selected[j] = selectChrom;
        }*/
        return cumulateP;

    }

    /********************************************
     * Core **  Step 2.1    选择操作  threshold为阀值，默认10%
     ********************************************/
    private LinkedList<Chromsome> selection(List<Chromsome> pop, Double[] cumulateP, Double threshold) {
        LinkedList<Chromsome> selectedChrom = new LinkedList<Chromsome>();

        if (ObjectUtil.isEmpty(threshold)) threshold = 0.1;
        int selectedChromSize = (int) (pop.size() * threshold);

        //截断选择
        selectedChrom.addAll(pop.subList(0, selectedChromSize));

        //轮盘赌选择
        //List<Integer> popPositon = new ArrayList<Integer>();
        /*for (int i = 0; i < selectedChromSize; i++) {
            int index;
            do {
                index = this.select(cumulateP);
            } while (popPositon.contains(index));
            popPositon.add(index);
        }

        for (Integer i : popPositon) {
            selectedChrom.add(pop.get(i));
        }*/
        return selectedChrom;
    }

    //采用轮盘赌选择方式进行选择，
    private int select(Double[] cumulateP) {
        double r = Math.random();
        int temp1 = 0;
        //TODO 为什么这样写
        if (r <= cumulateP[0]) {
            temp1 = 0;
        }else{
            for (int k = 1; k < popsize; k++) {
                if (r < cumulateP[k]) {
                    temp1 = k;
                    break;
                }
            }//第k个染色体被选中
        }

        return temp1;
    }

    /*******************************
     * Core **  Step 2.1    顺序交叉操作
     ****************************/
    private boolean crossover(List<Chromsome> pop, Double[] cumulateP) {

        boolean flag = false;//产生的新子代是否更新了父代种群

        //轮盘赌选择两个父亲
        int farther1 = select(cumulateP);
        int farther2 = farther1;
        while (farther2 == farther1){
            farther2 = select(cumulateP);
        }

        //随机选择两个交叉点位置,chromSize个基因位，有chromSize-1个交叉点
        Integer[] pos = NumberUtil.getRandonNumber(2, chromSize-1);//两个交叉点的位置
        int pos1 = pos[0];
        int pos2 = pos[1];
        //保证pos1为第一个交叉点


        //两个交叉点位置分割两个父个体,、、存放父个体分割后的三段字符串路径,、、存放父个体分割后的三段字符串路径。
        Chromsome f1 = currentPop.get(farther1);
        String f1temp1 = f1.getPath().substring(0, pos1+1);
        String f1temp2 = f1.getPath().substring(pos1+1, pos2+1);
        String f1temp3 = f1.getPath().substring(pos2+1);

        Chromsome f2 = currentPop.get(farther2);
        String f2temp1 = f2.getPath().substring(0, pos1+1);
        String f2temp2 = f2.getPath().substring(pos1+1, pos2+1);
        String f2temp3 = f2.getPath().substring(pos2+1);

        //按OX交叉操作规则生成子个体child1和child2
        String childPath1 = this.OX(f2temp1, f2temp2, f2temp3, f1temp2);
        String childPath2 = this.OX(f1temp1, f1temp2, f1temp3, f2temp2);

        Chromsome childChrom1 = new Chromsome(childPath1);
        Chromsome childChrom2 = new Chromsome(childPath2);

        double childFitness1 = childChrom1.getFitness();
        double childFitness2 = childChrom2.getFitness();

        //若子代中已有相同适应度个体，则不添加进子代，以加速收敛
        if (this.childPopFitness == null || !this.childPopFitness.contains(childFitness1)) {
            addInOrder(childPop, childChrom1);
            childPopFitness.add(childFitness1);
        }
        if (!this.childPopFitness.contains(childFitness2)) {
            addInOrder(childPop, childChrom2);
            childPopFitness.add(childFitness2);
        }

        /***************     稳定繁殖策略      ************************/
        //若子代的适应度高则替换父代继续进行遗传操作,否则舍弃生成的子代。
        if (childChrom1.getFitness() > f1.getFitness()) {
            pop.remove(f1);
            pop.add(childChrom1);//避免截断进子代的个体中出现重复
            /*f1.setPath(childPath1);
            f1.setFitness(childFitness1);*/
            flag = true;
            //chromosomes.set(farther1, childChrom1);
        }

        if (childChrom2.getFitness() > f2.getFitness()) {
            pop.remove(f2);
            pop.add(childChrom2);//避免截断进子代的个体中出现重复
            /*f2.setPath(childPath2);
            f2.setFitness(childFitness2);*/
            //chromosomes.set(farther2, childChrom2);
            flag = true;
        }

        return flag;
    }

    /***************
     * 顺序交叉操作具体实现
     ************************/
    private String OX(String str1, String str2, String str3, String str) {

        String temp = str3.concat(str1).concat(str2);

        for (int i = 0; i < str.length(); i++) {
            //int k = temp.indexOf(str.charAt(i));
            temp = temp.replace(str.charAt(i), ' ');
        }
        temp = temp.replace(" ", "");
        //temp = temp.trim();  //trim()剔除首尾空格

        str3 = temp.substring(0, str3.length());
        str1 = temp.substring(str3.length());

        return str1.concat(str).concat(str3);
    }

    /*******************************
     * Core **  Step 2.1    启发式变异操作
     ****************************/
    private boolean mutation(List<Chromsome> pop,Double[] cumulateP) {
        boolean flag = false;

        //
         int index = (int) ((Math.random() * 100) % (popsize));

        //int index = select(cumulateP);
        Chromsome chromosome = pop.get(index);

        Integer[] pos = NumberUtil.getRandonNumber(3, chromSize);//生成三个变异位置
        List<String> mutationPaths = mutate(pos, chromosome.getPath());

        //遍历变异,取最优变异个体
        String bestMutation = mutationPaths.get(0);
        double bestFit = Chromsome.getFitness(bestMutation);
        for (int i = 1; i < mutationPaths.size(); i++) {
            double tempFit = Chromsome.getFitness(mutationPaths.get(i));//不放入if判断，减少计算量
            if (bestFit < tempFit) {
                bestFit = tempFit;
                bestMutation = mutationPaths.get(i);
            }
        }

        double newFitness = Chromsome.getFitness(bestMutation);




        //与变异前个体比较，
        if (newFitness > chromosome.getFitness()) {
            if (!this.childPopFitness.contains(newFitness)) {
                addInOrder(childPop, new Chromsome(bestMutation, newFitness));
                childPopFitness.add(newFitness);
            }
            pop.remove(chromosome);
            pop.add(new Chromsome(bestMutation,newFitness));//避免截断进子代的个体中出现重复
            /*chromosome.setPath(bestMutation);
            chromosome.setFitness(newFitness);*/
            System.out.println("变异了"+bestMutation);
            mutationTimes++;
            flag = true;
        }
        return flag;
    }

    private List<String> mutate(Integer[] pos, String oldPath) {
        List<String> mutationPaths = new ArrayList<String>(); //保存所有变异可能的结果路径

        int num = pos.length;  //num个变异位置
        List<String> mutateRole = getMutateRole(num);

        String baseStr = "";
        for (int i = 1; i <= num; i++) {
            baseStr = baseStr.concat(String.valueOf(i));
        }
        mutateRole.remove(baseStr); //剔除变异点正常顺序

        for (String role : mutateRole) { //根据变异格式循环获得变异结果路径
            String mutationPath = "";
            for (int i = 0; i < role.length(); i++) {
                if (i == 0) {
                    mutationPath = mutationPath.concat(oldPath.substring(0, pos[i]));
                } else {

                    mutationPath = mutationPath.concat(oldPath.substring(pos[i - 1] + 1, pos[i]));
                }
                Integer index = Integer.parseInt(role.charAt(i) + "");
                mutationPath = mutationPath.concat(String.valueOf(oldPath.charAt(pos[index - 1])));
            }
            if (pos[role.length() - 1] < oldPath.length() - 1) {
                mutationPath = mutationPath.concat(oldPath.substring(pos[role.length() - 1] + 1));
            }
            mutationPaths.add(mutationPath);
        }
        return mutationPaths;
    }

    /*根据变异点个数，给变异点编码："12..n",循环得出可能的变异情况，以点的排列顺序展示：
    如3个变异点，编码为“123”；可能的变异情况有"132" "213" "231" "312" "321"
    该方法返回包括正常情况的*/
    private List<String> getMutateRole(int n) {
        List<String> mutateRole = new ArrayList<String>();
        if (n == 2) {
            mutateRole.add("12");
            mutateRole.add("21");
        } else if (n > 2) {

                List<String> strings = getMutateRole(n - 1);

                for (String str : strings) {

                    for (int j = 0; j <= str.length(); j++) {
                        String newStr = "";
                        newStr = newStr.concat(str.substring(0, j)).concat(String.valueOf(n)).concat(str.substring(j, str.length()));
                        mutateRole.add(newStr);
                    }
                }

        }
        return mutateRole;
    }

    /**
     * 向种群中添加新个体，并按适应度降序排序
     *
     * @param pop
     * @param chromsome
     */
    private void addInOrder(LinkedList<Chromsome> pop, Chromsome chromsome) {
        double chromFit = chromsome.getFitness();
        if(pop.size() == 0 || chromFit < pop.getLast().getFitness() ){
            pop.add(chromsome);
        } else{
            //显示调用集合迭代器，优化遍历效率
            Iterator<Chromsome> iterator = pop.iterator();
            int i = 0; //计数
            while (iterator.hasNext()) {
                Chromsome chrom = iterator.next();
                //适应度由大到小排列
                if (chromFit > chrom.getFitness()) {
                    pop.add(i, chromsome);
                    break;
                }
                i++;
            }
        }

    }

    /**
     * 判断新个体是否已存在于种群向种群中添加个体，
     *
     * @param pop
     * @param chromsome
     * @return
     */
    private boolean newAdd(LinkedList<Chromsome> pop, Chromsome chromsome) {
        boolean flag = false;
        //Todo 设置参照适应度 ????
        //if(chromsome.getFitness()>referFitness)
        return flag;
    }

    public int getPopsize() {
        return popsize;
    }

    public void setPopsize(int popsize) {
        this.popsize = popsize;
    }

    public List<Chromsome> getCurrentPop() {
        return currentPop;
    }

    public void setCurrentPop(List<Chromsome> currentPop) {
        this.currentPop = currentPop;
    }

    public List<Chromsome> getChildPop() {
        return childPop;
    }

    public void setChildPop(LinkedList<Chromsome> childPop) {
        this.childPop = childPop;
    }

    public List<Double> getChildPopFitness() {
        return childPopFitness;
    }

    public void setChildPopFitness(List<Double> childPopFitness) {
        this.childPopFitness = childPopFitness;
    }

    public int getMAX_GEN() {
        return MAX_GEN;
    }

    public void setMAX_GEN(int MAX_GEN) {
        this.MAX_GEN = MAX_GEN;
    }

    public double[][] getDistance() {
        return distance;
    }

    public void setDistance(double[][] distance) {
        this.distance = distance;
    }

    public int getChromSize() {
        return chromSize;
    }

    public void setChromSize(int chromSize) {
        this.chromSize = chromSize;
    }

    public double getP_c_t() {
        return p_c_t;
    }

    public void setP_c_t(double p_c_t) {
        this.p_c_t = p_c_t;
    }

    public double getP_m_t() {
        return p_m_t;
    }

    public void setP_m_t(double p_m_t) {
        this.p_m_t = p_m_t;
    }

    public double getP_s_t() {
        return p_s_t;
    }

    public void setP_s_t(double p_s_t) {
        this.p_s_t = p_s_t;
    }
}
