CREATE TABLE TActivity (
  ActivityID bigint(20) NOT NULL AUTO_INCREMENT,
  CreateDate datetime NOT NULL,
  Description varchar(1000) NOT NULL,
  Points int NOT NULL,
  UserID bigint(20) NOT NULL,
  PRIMARY KEY (ActivityID),
  KEY FK_Activity_User (UserID),
  CONSTRAINT FK_Activity_User FOREIGN KEY (UserID) REFERENCES TUser (UserID)
);

ALTER TABLE TEventInvite
	ADD COLUMN CreatedByUserID int NULL;

INSERT INTO TActivity (CreateDate, Description, Points, UserID) SELECT CURRENT_TIMESTAMP, "Joined PushSignal before points subsystem was added", 250, UserID FROM TUser;
