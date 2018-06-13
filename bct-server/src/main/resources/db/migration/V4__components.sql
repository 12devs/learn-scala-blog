CREATE TABLE components (
  component_id   TEXT    NOT NULL PRIMARY KEY,
  component_name TEXT    NOT NULL,
  app_id         TEXT    NOT NULL REFERENCES apps (app_id),
  url            TEXT    NOT NULL,
  logo           TEXT    NOT NULL,
  enabled        BOOLEAN NOT NULL,
  UNIQUE (component_name, app_id)
);

INSERT INTO apps (app_id, app_name, url, logo, enabled)
VALUES ('c011f6aa-039f-4289-b4fa-71fed380889e', 'BCT API Demo', 'http://bct:3001', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('2e9de0ee-4709-4e9e-8628-1b412047b20b', 'Chat Client', 'c011f6aa-039f-4289-b4fa-71fed380889e',
   'http://bct:3001/chatClient', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('28ff3220-abbe-4382-9df2-0189caf26100', 'Chat Supervisor', 'c011f6aa-039f-4289-b4fa-71fed380889e',
   'http://bct:3001/chatSupervisor', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('5633ef14-f037-4eea-8a18-287b608ff7e2', 'Chat Log', 'c011f6aa-039f-4289-b4fa-71fed380889e',
   'http://bct:3001/chatLog', '', TRUE);
