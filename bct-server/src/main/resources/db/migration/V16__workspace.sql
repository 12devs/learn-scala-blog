UPDATE default_templates SET initial_template = true;

INSERT INTO user_templates (
  select
    dt.default_template_id || '-' || u.user_id as user_template_id,
    u.user_id                                as user_id,
    dt.template_name                         as template_name,
    dt.json                                  as json,
    dt.default_template_id                   as based_on
  from default_templates as dt cross join users as u
) ON CONFLICT DO NOTHING;
