ALTER TABLE web_user ADD COLUMN tsv tsvector;
UPDATE web_user SET tsv = to_tsvector('english', login);

ALTER TABLE web_section ADD COLUMN tsv tsvector;
UPDATE web_section SET tsv = to_tsvector('russian', sec_name);

ALTER TABLE web_theme ADD COLUMN tsv tsvector;
UPDATE web_theme SET tsv = to_tsvector('russian', th_name);

ALTER TABLE web_message ADD COLUMN tsv_head tsvector;
UPDATE web_message SET tsv_head = to_tsvector('russian', mes_header);

ALTER TABLE web_message ADD COLUMN tsv_text tsvector;
UPDATE web_message SET tsv_text = to_tsvector('russian', mes_text);
