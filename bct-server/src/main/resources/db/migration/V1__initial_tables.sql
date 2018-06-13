CREATE TABLE default_templates (
  default_template_id TEXT    NOT NULL PRIMARY KEY,
  template_name       TEXT    NOT NULL UNIQUE,
  json                TEXT    NOT NULL,
  enabled             BOOLEAN NOT NULL
);

CREATE TABLE user_templates (
  user_template_id TEXT NOT NULL PRIMARY KEY,
  user_id         TEXT NOT NULL,
  template_name   TEXT NOT NULL,
  json           TEXT NOT NULL,
  UNIQUE (user_id, template_name)
);

CREATE TABLE apps (
  app_id   TEXT    NOT NULL PRIMARY KEY,
  app_name TEXT    NOT NULL UNIQUE,
  url     TEXT    NOT NULL,
  logo    TEXT    NOT NULL,
  enabled BOOLEAN NOT NULL
);

CREATE TABLE article (
  article_id   TEXT    NOT NULL PRIMARY KEY,
  title TEXT    NOT NULL UNIQUE,
  main_text     TEXT    NOT NULL
);


CREATE TABLE comment (
  comment_id   TEXT    NOT NULL PRIMARY KEY,
  main_text TEXT    NOT NULL UNIQUE
);

INSERT INTO article (article_id, title, main_text) VALUES (
  'id1',
  'news ololo',
  'IUG ur giue rgiuer euryg.'
);

INSERT INTO article (article_id, title, main_text) VALUES (
  'id2',
  'neesrgtesrgo',
  'IUesrtG ur giueesrtserte rgiuer euryg.'
);

INSERT INTO article (article_id, title, main_text) VALUES (
  'id3',
  'nsertseo',
  'IesrtUGsertr giue rgiuer eerdteyg.'
);
INSERT INTO comment (comment_id, main_text) VALUES (
  'id1',
  'nser121212tseo'
);

INSERT INTO comment (comment_id, main_text) VALUES (
  'id2',
  'nsertseo'
);
INSERT INTO comment (comment_id, main_text) VALUES (
  'id3',
  'nseriuytseo'
);
