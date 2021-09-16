package com.zhaojufei.practice.javasyntax.string;

import javax.sound.midi.Soundbank;

public class RegixTest {

    public static void main(String[] args) {

        String ignorePattern = "/gatewaybui/api/user/userInfo | /gatewaybui/info|/gatewaybui/health | /gatewaybui/api/core/thirdPartMatch/shop/list | /gatewaybui/api/retail/ele/bind/shop/url";

        String url = "http://localhost:9526/gatewaybui/api/core/thirdPartMatch/shop/list";

        System.out.println(url.matches(ignorePattern));
    }




}
