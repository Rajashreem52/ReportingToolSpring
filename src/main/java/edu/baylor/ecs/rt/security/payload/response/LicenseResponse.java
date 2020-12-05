package edu.baylor.ecs.rt.security.payload.response;

import edu.baylor.ecs.rt.model.LicenseType;

import java.util.Date;

public class LicenseResponse {
    private LicenseType type;
    private long saleid;
    private double price;
    private int year;
    private Date purchaseDate;
    public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	private String username;
    private boolean isActive;
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getSaleid() {
		return saleid;
	}

	public void setSaleid(long saleid) {
		this.saleid = saleid;
	}

	private Date expiredDate;

    public LicenseType getType() {
        return type;
    }

    public void setType(LicenseType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }


}
