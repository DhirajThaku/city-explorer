ALTER TABLE itineraries DROP CONSTRAINT chk_itineraries_dates;

ALTER TABLE itineraries ADD CONSTRAINT chk_itineraries_dates CHECK(end_date >= start_date);