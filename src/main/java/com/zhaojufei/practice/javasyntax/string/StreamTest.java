package com.zhaojufei.practice.javasyntax.string;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {

        List<String> allList = Lists.newLinkedList();
        List<CompletableFuture<List<String>>> futureList = Lists.newArrayList();
        int count = 4;

        for (int i = 0; i < count; i++) {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
                return getOrgByStoreIdsByPage();
            });
            futureList.add(future);
        }
        List<List<String>> collect = futureList.stream().map(v -> {
            try {
                return v.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).filter(Objects::nonNull).map(
                list -> {
                    System.out.println(list);
                    allList.addAll(list);
                    return list;
                }).collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(allList);
    }

    public static List<String> getOrgByStoreIdsByPage() {
        List<String> list = Lists.newArrayList();
        list.add(String.valueOf(System.currentTimeMillis()));
        return list;
    }


}
