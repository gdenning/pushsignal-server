ALTER TABLE TEvent
	ADD COLUMN LastTriggerDate DATETIME NULL;

UPDATE TEvent SET LastTriggerDate = (SELECT MAX(CreateDate) FROM TTrigger WHERE TTrigger.EventID = TEvent.EventID);