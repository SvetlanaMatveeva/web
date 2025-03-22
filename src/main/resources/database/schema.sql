-- upd UNIQUE

CREATE TABLE web_user (
    login varchar(50) NOT NULL,
    acc_password varchar(50) NOT NULL,
    registration_date date DEFAULT current_date NOT NULL,
    rights int DEFAULT 1 NOT NULL CONSTRAINT rights_value CHECK (rights = 0 OR rights = 1),  -- 0-mod, 1-user
    PRIMARY KEY (login)
);

CREATE TABLE web_section (
    sec_id SERIAL,
    sec_name varchar(200) NOT NULL,
    PRIMARY KEY (sec_id),
    UNIQUE (sec_name)
);

CREATE TABLE web_theme (
    th_id SERIAL,
    th_name varchar(200) NOT NULL,
    sec_id bigint NOT NULL,
    PRIMARY KEY (th_id),
    FOREIGN KEY (sec_id) REFERENCES web_section ON DELETE CASCADE,
    UNIQUE (sec_id, th_name)
);

CREATE TABLE web_message (
    mes_id SERIAL,
    mes_text text,
    user_id varchar(50),
    theme_id bigint NOT NULL,
    mes_header varchar(200) NOT NULL,
    receipt timestamp DEFAULT current_timestamp NOT NULL,
    PRIMARY KEY (mes_id),
    FOREIGN KEY (user_id) REFERENCES web_user ON DELETE SET NULL,
    FOREIGN KEY (theme_id) REFERENCES web_theme ON DELETE CASCADE
);

CREATE TABLE web_file (
    file_id SERIAL,
    save_path text NOT NULL,
    mes_id bigint NOT NULL,
    PRIMARY KEY (file_id),
    FOREIGN KEY (mes_id) REFERENCES web_message ON DELETE CASCADE,
    UNIQUE (mes_id, save_path)
);
