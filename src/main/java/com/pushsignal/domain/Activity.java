package com.pushsignal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(catalog = "PushSignal", name = "TActivity")
@NamedQueries({
	@NamedQuery(name = "findActivitiesByUserId", query = "select myActivity from Activity myActivity join myActivity.user myUser where myUser.userId = ?1 order by myActivity.createDate DESC")
})
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;

    @Column(name = "ActivityID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long activityId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date createDate;

	@Column(name = "Description", length = 1000, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String description;

	@Column(name = "Points", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private int points;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false) })
	@ForeignKey(name = "FK_Activity_User")
	private User user;

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("activityId", activityId)
			.append("createDate", createDate)
			.append("description", description)
			.append("points", points)
			.append("user", user)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(createDate)
			.append(description)
			.append(points)
			.append(user)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Activity)) {
			return false;
		}
		Activity other = (Activity) obj;
		return new EqualsBuilder()
			.append(createDate, other.createDate)
			.append(description, other.description)
			.append(points, other.points)
			.append(user, other.user)
			.isEquals();
	}
}
