package com.zhaojufei.practice.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StreamTest {

    public static void main(String[] args) {

        Order o1 = new Order("hg", "黄瓜", "5斤/箱", "箱", "沙县小吃", "b001", 12);

        Order o2 = new Order("qz", "茄子", "6个/袋", "袋", "沙县小吃", "b001", 8);

        Order o3 = new Order("qz", "茄子", "6个/袋", "袋", "沙县小吃", "b002", 3);

        Order o4 = new Order("yc", "洋葱", "5个/打", "打", "沙县小吃", "b002", 22);

        Order o5 = new Order("qz", "茄子", "6个/袋", "袋", "肯德基", "b003", 4);

        Order o6 = new Order("hg", "黄瓜", "5斤/箱", "箱", "麦当劳", "b004", 8);

        Order o7 = new Order("qz", "茄子", "5斤/箱", "斤", "麦当劳", "b004", 50);

        List<Order> orders = Lists.newArrayList();

        orders.add(o1);
        orders.add(o2);
        orders.add(o3);
        orders.add(o4);
        orders.add(o5);
        orders.add(o6);
        orders.add(o7);

        // 0.1 通过max取最大值
        Optional<Order> opt = orders.stream().max(Comparator.comparing(Order::getUnDeliveredNum));

        // System.out.println(opt.get());

        // 0.2 通过排序取最大值
        Optional<Order> opt2 = orders.stream().sorted(Comparator.comparingInt(Order::getUnDeliveredNum).reversed())
                .findFirst();
        // System.out.println(opt2.get());

        // 0.3 排序
        Optional<Order> opt3 = orders.stream()
                .sorted((p1, p2) -> (p2.getUnDeliveredNum().compareTo(p1.getUnDeliveredNum()))).findFirst();

        // System.out.println(opt3.get());

        // 0.4 收集器求和（也可以求平均值，但是平均值是浮点型）
        IntSummaryStatistics summaryStatistics = orders.stream()
                .collect(Collectors.summarizingInt(Order::getUnDeliveredNum));

        // System.out.println(summaryStatistics.getAverage());

        // 0.5
        String names = orders.stream().map(Order::getGoodsName).collect(Collectors.joining(","));

        // System.out.println(names);

        long ordernum = orders.stream().collect(Collectors.counting());
        // System.out.println(ordernum);

        // 0.6 set可以去重，distinet 也可以去重
        // Set<Order> set = orders.stream().collect(Collectors.toSet());
        // System.out.println(orders.size() +"-"+ set.size());

        // 0.7 Map key值冲突
        Map<String, String> maps = orders.stream()
                .collect(Collectors.toMap(Order::getGoodsCode, Order::getGoodsName, (oldValue, newValue) -> newValue));

        // System.out.println(maps);

        // 0.8
        Map<String, Order> maps2 = orders.stream()
                .collect(Collectors.toMap(Order::getGoodsCode, Function.identity(), (oldValue, newValue) -> newValue));

        // System.out.println(maps2);

        // 0.9 reduce
        Integer total = orders.stream().collect(Collectors.reducing(0, Order::getUnDeliveredNum, (i, j) -> i + j));
        Integer total2 = orders.stream().collect(Collectors.reducing(0, Order::getUnDeliveredNum, Integer::sum));
        Integer total3 = orders.stream().map(Order::getUnDeliveredNum).reduce(Integer::sum).get();

        // System.out.println(total + "-" + total2 + "-" + total3);

        // 0.91 reduce 三个参数

        ArrayList<Integer> numList = orders.stream().map(Order::getUnDeliveredNum).reduce(new ArrayList<Integer>(),
                (ArrayList<Integer> l, Integer f) -> {
                    l.add(f);
                    return l;
                }, (ArrayList<Integer> l1, ArrayList<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1;
                });

        // System.out.println(numList);

        // 0.92 基本group by
        Map<String, List<Order>> goodsGroup = orders.stream().collect(groupingBy(Order::getGoodsCode));

        // System.out.println(goodsGroup);

        // 分组求最大值
        Map<String, Optional<Order>> goodsGroup2 = orders.stream().collect(Collectors.groupingBy(Order::getGoodsCode,
                Collectors.maxBy(Comparator.comparingInt(Order::getUnDeliveredNum))));

        // System.out.println(goodsGroup2);

        // 分组求和
        Map<String, Long> summ = orders.stream()
                .collect(Collectors.groupingBy(Order::getGoodsCode, Collectors.summingLong(Order::getUnDeliveredNum)));

        // System.out.println(summ);

        Map<String, Long> finalMap = new LinkedHashMap<>();

        summ.entrySet().stream().sorted(Map.Entry.<String, Long> comparingByValue())
                .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

        // System.out.println(finalMap);

        Map<String, String> namess = orders.stream()
                .collect(groupingBy(Order::getGoodsCode, mapping(Order::getAcceptanceOrgName, joining(",", "[", "]"))));

        // System.out.println(namess);

        // 使用对象分组
        Map<Order, List<Order>> tuple = orders.stream()
                .collect(groupingBy(order -> new Order(order.getGoodsCode(), order.getAcceptanceOrgName())));

        // System.out.println(tuple);

        // 使用两个成员分组List

        Map<String, Map<String, List<Order>>> doubleg = orders.stream()
                .collect(groupingBy(Order::getGoodsCode, groupingBy(Order::getAcceptanceOrgName)));
        // System.out.println(doubleg);

        Map<String, Integer> goodsTotal = orders.stream()
                .collect(groupingBy(Order::getGoodsCode, reducing(0, Order::getUnDeliveredNum, Integer::sum)));

        Map<String, Integer> orgTotal = orders.stream()
                .collect(groupingBy(o -> o.getGoodsCode().concat(o.getAcceptanceOrgName()),
                        reducing(0, Order::getUnDeliveredNum, Integer::sum)));

        List<String> goodsCodelist = orders.stream().map(Order::getGoodsCode).distinct()
                .sorted((x, y) -> (x.compareTo(y))).collect(Collectors.toList());

        Map<String, Integer> goodsCodeMap = Maps.newHashMap();

        for (int i = 0; i < goodsCodelist.size(); i++) {
            goodsCodeMap.put(goodsCodelist.get(i), i + 1);
        }

        // 设置小计值
        orders = orders.stream().peek(o -> {
            o.setOrgTotal(orgTotal.get(o.getGoodsCode().concat(o.getAcceptanceOrgName())));
        }).collect(toList());

        // 设置小计值
        orders = orders.stream().peek(o -> {
            o.setGoodsTotal(goodsTotal.get(o.getGoodsCode()));
            o.setRownum(goodsCodeMap.get(o.getGoodsCode()));
        }).collect(toList());

        // 排序
        orders = orders.stream().sorted(Comparator.comparing(Order::getGoodsCode)
                .thenComparing(Comparator.comparing(Order::getUnDeliveredNum).reversed())).collect(toList());

        orders.forEach(o -> System.out.println(o));

        // 1.0 Map : 基本的映射操作
        List<Integer> nums = orders.stream().map(order -> order.getUnDeliveredNum()).collect(Collectors.toList());

        // System.out.println(nums);

        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

        // 2.0 flatMap展开操作
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());

        // System.out.println(outputStream.collect(Collectors.toList()));

        // 3.0 filter 过滤
        List<Order> hglist = orders.stream().filter(order -> "hg".equals(order.getGoodsCode()))
                .collect(Collectors.toList());

        // System.out.println(hglist);

        // 4.0 forEach 迭代，结合lambda，代码更简练
        orders.stream().forEach((order) -> {
            order.setBillNo(order.getBillNo().concat("订单"));
        });

        // System.out.println(orders);

        // 终止操作会改变原数据
        orders.forEach(order -> {
            order.setGoodsCode(order.getGoodsCode().toUpperCase());
        });

        // System.out.println(orders);

        // 5.0 这里map用peek更合适
        List<String> goodscodess1 = orders.stream().map(order -> {
            order.setGoodsCode(order.getGoodsCode().toLowerCase().concat("G-"));
            return order;
        }).map(order -> order.getGoodsCode()).collect(Collectors.toList());

        // System.out.println(orders);

        // 5.1 peek
        List<String> goodscodes2 = orders.stream().peek(order -> {
            order.setGoodsCode(order.getGoodsCode().toLowerCase().concat("Peek-"));
        }).map(order -> order.getGoodsCode()).collect(Collectors.toList());

        // System.out.println(orders);

        // 6.0 reduce 计算
        String concat = Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("Z") > 0).reduce("",
                String::concat);

        // System.out.println(concat);

        // 7.0 limit skip sort
        List<Order> sortList = orders.stream().limit(2)
                .sorted((p1, p2) -> p1.getUnDeliveredNum().compareTo(p2.getUnDeliveredNum()))
                .collect(Collectors.toList());

        // System.out.println(sortList);

    }
}

@Data
class Order {
    public Order(String goodsCode, String goodsName, String goodsSpec, String procUnitName, String acceptanceOrgName,
            String billNo, Integer unDeliveredNum) {
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsSpec = goodsSpec;
        this.procUnitName = procUnitName;
        this.acceptanceOrgName = acceptanceOrgName;
        this.billNo = billNo;
        this.unDeliveredNum = unDeliveredNum;
    }

    public Order(String goodsCode, String acceptanceOrgName) {
        this.goodsCode = goodsCode;
        this.acceptanceOrgName = acceptanceOrgName;
    }

    /**
     * 物品编码
     */
    private Integer rownum;

    /**
     * 物品编码
     */
    private String goodsCode;

    /**
     * 物品名称
     */
    private String goodsName;

    /**
     * 物品规格
     */
    private String goodsSpec;

    /**
     * 采购单位
     */
    private String procUnitName;

    /**
     * 验收机构
     */
    private String acceptanceOrgName;

    /**
     * 订单编码
     */
    private String billNo;

    /**
     * 未发货数量
     */
    private Integer unDeliveredNum;

    private Integer orgTotal;

    private Integer goodsTotal;
}