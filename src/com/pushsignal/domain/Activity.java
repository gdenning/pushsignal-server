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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + points;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
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
		if (createDate == null) {
			if (other.createDate != null) {
				return false;
			}
		} else if (!createDate.equals(other.createDate)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (points != other.points) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}
}
