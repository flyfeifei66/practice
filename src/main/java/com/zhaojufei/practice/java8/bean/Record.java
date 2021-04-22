package com.zhaojufei.practice.java8.bean;

import java.math.BigDecimal;

/**
 * @author zhaojufei
 * @description
 * @Date 2019-03-29
 **/

public class Record {
    public Record(String goodsCode, String acceptanceOrgName, BigDecimal unDeliveredNum) {
        this.goodsCode = goodsCode;
        this.acceptanceOrgName = acceptanceOrgName;
        this.unDeliveredNum = unDeliveredNum;
    }

    /**
     * 物品编码
     */
    private String goodsCode;

    /**
     * 验收机构
     */
    private String acceptanceOrgName;

    /**
     * 未发货数量
     */
    private BigDecimal unDeliveredNum;

    private BigDecimal orgTotal;

    private BigDecimal goodsTotal;


    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getAcceptanceOrgName() {
        return acceptanceOrgName;
    }

    public void setAcceptanceOrgName(String acceptanceOrgName) {
        this.acceptanceOrgName = acceptanceOrgName;
    }

    public BigDecimal getUnDeliveredNum() {
        return unDeliveredNum;
    }

    public void setUnDeliveredNum(BigDecimal unDeliveredNum) {
        this.unDeliveredNum = unDeliveredNum;
    }

    public BigDecimal getOrgTotal() {
        return orgTotal;
    }

    public void setOrgTotal(BigDecimal orgTotal) {
        this.orgTotal = orgTotal;
    }

    public BigDecimal getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(BigDecimal goodsTotal) {
        this.goodsTotal = goodsTotal;
    }
}
