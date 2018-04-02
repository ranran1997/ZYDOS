package com.hyy.core.GA;

import com.hyy.base.WithOutModelAction;
import com.hyy.config.ViewConfig;
import com.hyy.core.GAA.GAA;
import com.hyy.core.ZYDOS;
import com.hyy.sys.user.User;
import com.hyy.zydos.orderInfo.OrderInfo;
import com.hyy.zydos.orderInfo.OrderInfoService;
import com.hyy.zydos.store.Store;
import com.hyy.zydos.store.StoreService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HengYanYan on 2016/4/19  15:29
 */
@Controller
@RequestMapping("rout")
public class RoutMap extends WithOutModelAction {
    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    StoreService storeService;

    @RequestMapping("optimize")
    public String optimize(HttpServletRequest request, Model model) {

        User user = this.getUserPermission().getCurrentUser();

        List<OrderInfo> undoOrders = orderInfoService.findUndoOrderInfoByUser(user.getId());


        Map location = this.getAddressMes(request);
        if (location != null && location.get("status") == "0")
            return error("网络连接失败");
        assert location != null;
        String lnglat = (String) location.get("locations");
        //String lnglat = "112.944106,27.833098";
        List<String> lnglats = new ArrayList<String>();
        lnglats.add(lnglat);
        for (OrderInfo undoOrder : undoOrders) {
            Store store = storeService.findById(undoOrder.getStoreId());
            undoOrder.setStore(store);
            lnglats.add(store.getLng() + "," + store.getLat());
        }

        double[][] distance = this.getDistanceArray(lnglats);

        if (distance == null) return error("网络连接失败");

        //贪心策略 模拟常人思维进行路径选择,参考路径
        String referPath = this.greedy(distance, lnglats.size());


        int popsize, MAX_GEN;
        if (undoOrders.size() < 5) {
            popsize = (undoOrders.size() - 1) * undoOrders.size() / 2;
            MAX_GEN = 3;
        } else {
            popsize = 120;
            MAX_GEN = 600;
        }

        //Todo 种群规模，变异率，交叉率数值的确定！！！
        double p_c = 0.75;//交叉概率
        double p_m = 0.17;//变异概率
        double p_s =1;
        GAA ga = new GAA(undoOrders.size(), popsize, distance, MAX_GEN,p_s, p_c, p_m, referPath);
        List<Integer> bestPath = ga.solve();

        List<OrderInfo> GAOrders = new ArrayList<OrderInfo>();
        for (int i : bestPath) {
            GAOrders.add(undoOrders.get(i));
        }
        model.addAttribute("path2", JSONArray.fromObject(GAOrders).toString());
        model.addAttribute("path", GAOrders);
        model.addAttribute("self", lnglat);
        model.addAttribute("jsAPI", ZYDOS.mapAPI.JSAPI);

        return forward("rout", ViewConfig.mainviews);

    }

    private String greedy(double[][] distance, int size) {
        List<Integer> choosed = new ArrayList<Integer>();
        List<Integer> toChoose = new ArrayList<Integer>();
        for (int c = 1; c < size; c++) { //初始化待选路径节点，根据其排列顺序编码
            toChoose.add(c);
        }
        //起点已知
        int first = 0, temp;
        do {
            temp = 0;
            for (int j = 0; j < toChoose.size() - 1; j++) {
                if (distance[first][toChoose.get(j)] > distance[first][toChoose.get(j) + 1]) {
                    temp = j + 1;
                }
            }
            first = toChoose.get(temp);
            choosed.add(toChoose.get(temp));
            toChoose.remove(temp);

        } while (toChoose.size() > 1);

        choosed.add(toChoose.get(0));

        String ch = "";
        for(Integer i:choosed){
            ch +=i;
        }
        return ch;
    }


    private double[][] getDistanceArray(List<String> lnglats) {
        int num = lnglats.size();
        double[][] distance = new double[num][num];
        for (int i = 0; i < num; i++) {
            String origins = "";//出发点序列，高德地图支持100个坐标对
            String destination = "";
            for (int j = 0; j <= i; j++) {
                origins = origins.concat("|" + lnglats.get(j));
            }
            destination = lnglats.get(i);
            try {
                URL url = new URL("http://restapi.amap.com/v3/distance?origins=" + origins.substring(1) + "&destination=" + destination + "&key=" + ZYDOS.mapAPI.WEBAPI);
                //读取
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
                String res;
                StringBuffer sb = new StringBuffer();
                while ((res = in.readLine()) != null) {
                    sb.append(res.trim());
                }
                in.close();
                String str = sb.toString();
                //String to map
                if (Integer.parseInt((String) JSONObject.fromObject(str).get("status")) != 1) {
                    return null;
                }
                List results = (List) JSONObject.fromObject(str).get("results");

                for (int j = 0; j <= i; j++) {
                    Map result = (Map) results.get(j);
                    distance[i][j] = Double.parseDouble(result.get("distance").toString());
                    distance[j][i] = distance[i][j];
                }
                distance[i][i] = 0;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return distance;
    }

}


