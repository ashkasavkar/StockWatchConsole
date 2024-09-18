package com.tradeconsole.swc.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@Entity
@Table(name = "market_news")
public class MarketNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "headline")
    @JsonProperty("headline")
    private String headline;

    @Column(name = "content")
    @JsonProperty("content")
    private String content;

    @Column(name = "published_at")
    @JsonProperty("datetime")
    private Date publishedAt;

    @Column(name = "url")
    @JsonProperty("url")
    private String url;

    // Getters and setters
    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}