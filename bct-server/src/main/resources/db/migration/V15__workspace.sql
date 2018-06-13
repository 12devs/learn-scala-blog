ALTER TABLE default_templates ADD COLUMN initial_template BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE user_templates ADD COLUMN based_on TEXT;
