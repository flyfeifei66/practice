package com.zhaojufei.practice.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TESt {
    public static void main(String[] args) throws ParseException {

        Date dateNow = new Date();
        Date mydate = new Date(2019,6,27);

        //判断是否是补盘操作
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = sdf.parse(sdf.format(dateNow));

        System.out.println("nowDate="+nowDate);
        System.out.println("mydate="+mydate);

        System.out.println(mydate.before(nowDate));

    }



    public static boolean isLager(Date large, Date small) {
        return large != null && (small == null || large.getTime() > small.getTime());
    }


}
