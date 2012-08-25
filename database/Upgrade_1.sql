ALTER TABLE TEvent
	ADD COLUMN UrlGuid VARCHAR(50),
	ADD COLUMN TriggerPermission VARCHAR(50) NOT NULL DEFAULT 'ALL_MEMBERS',
	ADD COLUMN PublicFlag BOOLEAN NOT NULL DEFAULT 0;

ALTER TABLE TTrigger
	CHANGE COLUMN CreateUserID CreateUserID BIGINT NULL;