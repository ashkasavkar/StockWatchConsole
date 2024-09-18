package com.tradeconsole.swc.entity;
import javax.persistence.*;

@Entity
@Table(name = "fav_stock")
public class FavStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favId;
    
    @ManyToOne
    @JoinColumn(name = "stockId", referencedColumnName = "stockId", nullable = false)
    private StockDisplay stock;
    
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    // Additional attributes for actions
    private Boolean addStock;
    private Boolean removeStock;

    // Getters and setters
    public Long getFavId() {
        return favId;
    }

    public void setFavId(Long favId) {
        this.favId = favId;
    }

    public StockDisplay getStock() {
        return stock;
    }

    public void setStock(StockDisplay stock) {
        this.stock = stock;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAddStock() {
        return addStock;
    }

    public void setAddStock(Boolean addStock) {
        this.addStock = addStock;
    }

    public Boolean getRemoveStock() {
        return removeStock;
    }

    public void setRemoveStock(Boolean removeStock) {
        this.removeStock = removeStock;
    }
}