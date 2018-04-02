package com.hyy.core.GA;

import java.util.Comparator;

/**
 * Created by HengYanYan on 2016/5/11  23:10
 */
public class ChromFitnessComparator implements Comparator<Chromsome>{

    public int compare(Chromsome o1, Chromsome o2) {
        return (int) (o1.getFitness() - o2.getFitness());
    }
}
