CREATE TABLE IF NOT EXISTS cards_session (
  id BIGINT NOT null primary key,
  session_type varchar(10),
  session_name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS retro_lane (
  id BIGSERIAL PRIMARY KEY,
  cards_session_id BIGINT NOT NULL,
  retro_lane_name varchar(50),
  retro_lane_color INT DEFAULT 0 NOT NULL CHECK (retro_lane_color >= 0 AND retro_lane_color <= 16777215),
  CONSTRAINT fk_retro_lane_session
    FOREIGN KEY (cards_session_id)
	REFERENCES cards_session(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
  constraint uq_name_per_board 
  	unique (id, retro_lane_name)
);

CREATE TABLE IF NOT EXISTS retro_card (
  id BIGSERIAL PRIMARY KEY,
  retro_lane_id BIGINT NOT NULL,
  retro_card_text varchar(250) NOT NULL,
  retro_card_color INT DEFAULT 16777113 NOT NULL CHECK (retro_card_color >= 0 AND retro_card_color <= 16777215),
  retro_votes INT DEFAULT 0 NOT NULL CHECK( retro_votes >= 0),
  CONSTRAINT fk_retro_card_retro_lane
    FOREIGN KEY (retro_lane_id)
	REFERENCES retro_lane(id)
	ON DELETE CASCADE
	ON UPDATE cascade
);

CREATE OR REPLACE FUNCTION insert_default_lane() RETURNS TRIGGER AS
$BODY$
BEGIN
	INSERT INTO retro_lane(cards_session_id) VALUES(NEW.id);
	RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER default_lane
	AFTER INSERT ON cards_session 
	FOR EACH ROW 
	WHEN (NEW.session_type = 'retro')
	EXECUTE PROCEDURE insert_default_lane();


CREATE OR REPLACE FUNCTION detect_card_move_between_sessions() RETURNS TRIGGER AS
$BODY$
DECLARE old_session BIGINT;
DECLARE new_session BIGINT;
BEGIN
  SELECT cards_session_id INTO old_session
  FROM retro_lane WHERE id = OLD.retro_lane_id;

  SELECT cards_session_id INTO new_session
  FROM retro_lane WHERE id = NEW.retro_lane_id;

  IF old_session = new_session THEN
    RETURN NEW;
  ELSE
    RAISE EXCEPTION 'Cannot move cards between sessions';
  END IF;
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER prevent_card_move_between_sessions
  BEFORE UPDATE ON retro_card
  FOR EACH ROW
  WHEN (NEW.retro_lane_id <> OLD.retro_lane_id)
  EXECUTE PROCEDURE detect_card_move_between_sessions();


-- DEMO Retro
DO $$
BEGIN
  IF NOT EXISTS (SELECT * FROM cards_session  WHERE id = 0) THEN
		INSERT INTO cards_session(id, session_type, session_name)
		VALUES (0, 'retro', 'Demo Retrospective');
	END IF;

  IF NOT EXISTS (SELECT * FROM retro_lane WHERE id = 0) THEN
    INSERT INTO retro_lane(id, cards_session_id, retro_lane_name)
    VALUES (0, 0, 'Demo Lane');
  END IF;

  IF NOT EXISTS (SELECT * FROM retro_card WHERE id = 0) THEN
    INSERT INTO retro_card(id, retro_lane_id, retro_card_text, retro_votes)
    VALUES (0, 0, 'Feedback text to provide feedback to stuff in the past', 8);
  END IF;

  IF NOT EXISTS (SELECT * FROM cards_session  WHERE id = 1) THEN
		INSERT INTO cards_session(id, session_type, session_name)
		VALUES (1, 'retro', 'Sprint 28 Retrospective');
	END IF;

  IF NOT EXISTS (SELECT * FROM retro_lane WHERE id = 3) THEN
    INSERT INTO retro_lane(id, cards_session_id, retro_lane_name)
    VALUES (3, 1, 'What went well');
  END IF;

  IF NOT EXISTS (SELECT * FROM retro_card WHERE id = 1) THEN
    INSERT INTO retro_card(id, retro_lane_id, retro_card_text, retro_votes)
    VALUES (1, 3, 'The coordination between teams greatly improved', 2);
  END IF;
END$$;
