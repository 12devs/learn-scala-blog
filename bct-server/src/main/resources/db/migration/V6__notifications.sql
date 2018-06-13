CREATE TABLE users (
  user_id    TEXT NOT NULL PRIMARY KEY,
  first_name TEXT NOT NULL,
  last_name  TEXT NOT NULL,
  user_level TEXT NOT NULL
);

CREATE TABLE user_apps (
  user_id TEXT NOT NULL REFERENCES users (user_id),
  app_id  TEXT NOT NULL REFERENCES apps (app_id),
  data    TEXT NOT NULL,
  PRIMARY KEY (user_id, app_id)
);

CREATE TABLE notifications (
  notification_id TEXT                     NOT NULL PRIMARY KEY,
  user_id         TEXT                     NOT NULL REFERENCES users (user_id),
  app_id          TEXT                     NOT NULL REFERENCES apps (app_id),
  title           TEXT                     NOT NULL,
  message         TEXT                     NOT NULL,
  acknowledged    BOOLEAN                  NOT NULL,
  data            TEXT                     NOT NULL,
  created_date    TIMESTAMP WITH TIME ZONE NOT NULL
);
