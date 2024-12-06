package com.travelo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "activities")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String activityName;

	@Column(name = "duration")
	private String activityDuration;

	// Many-to-one relationship with PackageDetails
//	Bidirectional Relationship:
//	Before saving the package,
//	we loop
//	through the
//	activities and
//	set their
//	packageDetails to
//	the current
//	Packages instance.
//	This maintains
//	the integrity
//	of the relationship.
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "package_id", nullable = false)
	@JsonIgnore // @concept - Prevents infinite loops or unintended data loading.
	private Packages packageDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDuration() {
		return activityDuration;
	}

	public void setActivityDuration(String activityDuration) {
		this.activityDuration = activityDuration;
	}

	public Packages getPackageDetails() {
		return packageDetails;
	}

	public void setPackageDetails(Packages packageDetails) {
		this.packageDetails = packageDetails;
	}

}
