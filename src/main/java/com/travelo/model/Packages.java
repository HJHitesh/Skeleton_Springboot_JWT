package com.travelo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "package_details")
public class Packages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_image")
    private String packageImage;

    //@concept - Owning Side and Inverse Side: JPA
    @OneToMany(mappedBy = "packageDetails",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Activity> activities;

    @Column(name = "package_id", unique = true)
    private String packageId;

    @Column(name = "package_duration")
    private String packageDuration;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "stay_details")
    private String stayDetails;

    @Column(name = "country")
    private String country;

    @Column(name = "flight_details")
    private String flightDetails;

    @Column(name = "package_price")
    private Double packagePrice;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageImage() {
        return packageImage;
    }

    public void setPackageImage(String packageImage) {
        this.packageImage = packageImage;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageDuration() {
        return packageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        this.packageDuration = packageDuration;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStayDetails() {
        return stayDetails;
    }

    public void setStayDetails(String stayDetails) {
        this.stayDetails = stayDetails;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFlightDetails() {
        return flightDetails;
    }

    public void setFlightDetails(String flightDetails) {
        this.flightDetails = flightDetails;
    }

	public Double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(Double packagePrice) {
		this.packagePrice = packagePrice;
	}
}

