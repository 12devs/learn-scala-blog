ALTER TABLE apps ADD COLUMN default_instance_configuration TEXT NOT NULL DEFAULT '';
ALTER TABLE components ADD COLUMN default_instance_configuration TEXT NOT NULL DEFAULT '';

update components set default_instance_configuration = '{"kind": "chatClient", "name": "Unnamed"}' WHERE component_id = '2e9de0ee-4709-4e9e-8628-1b412047b20b';
update components set default_instance_configuration = '{"kind": "chatSupervisor"}' WHERE component_id = '28ff3220-abbe-4382-9df2-0189caf26100';
update components set default_instance_configuration = '{"kind": "chatLog"}' WHERE component_id = '5633ef14-f037-4eea-8a18-287b608ff7e2';
