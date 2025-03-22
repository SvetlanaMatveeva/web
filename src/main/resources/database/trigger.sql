--Создание триггера
CREATE OR REPLACE FUNCTION check_rights() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM web_user WHERE login = NEW.user_id AND rights != 1) THEN
        RAISE EXCEPTION 'user_id must reference a user with rights = 1';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--DROP TRIGGER trigger_check_rights ON web_message
CREATE TRIGGER trigger_check_rights
    BEFORE INSERT ON web_message
    FOR EACH ROW EXECUTE FUNCTION check_rights();
