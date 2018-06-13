ALTER TABLE users ADD COLUMN client_cn TEXT UNIQUE NOT NULL DEFAULT random();
UPDATE users SET client_cn = 'admin_cn' where user_id = 'id3';
