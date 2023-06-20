package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
public class GoodsVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long goodsId;
    private String title;
    private String picUrl;
    private Integer point;
    private Integer stocks;

    public GoodsVo() {
    }

    public GoodsVo(String title, String picUrl, Integer point, Integer stocks) {
        this.title = title;
        this.picUrl = picUrl;
        this.point = point;
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "goodsId=" + goodsId +
                ", title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", point=" + point +
                ", stocks=" + stocks +
                '}';
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getStocks() {
        return stocks;
    }

    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }
}
