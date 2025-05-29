--建立驗證函式--

CREATE OR REPLACE FUNCTION check_invoice_main_exists()
RETURNS TRIGGER AS $$
BEGIN
    -- 嘗試查找 invoice_main 分區表中是否有對應 id
    IF NOT EXISTS (
        SELECT 1 FROM ONLY invoice_main WHERE id = NEW.invoice_main_id
    ) THEN
        RAISE EXCEPTION 'InvoiceMain ID % does not exist.', NEW.invoice_main_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--綁定觸發器--

CREATE TRIGGER trg_check_invoice_main_id
BEFORE INSERT OR UPDATE ON invoice_detail
FOR EACH ROW
EXECUTE FUNCTION check_invoice_main_exists();



--函式--

CREATE OR REPLACE FUNCTION check_allowance_main_exists()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS(
        SELECT 1 FROM ONLY allowance_main WHERE id = NEW.allowance_main_id
    ) THEN
        RAISE EXCEPTION 'AllowanceMain ID % dose not exist.', NEW.allowance_main_id;
    END IF;

    RETURN NEW;
END
$$ LANGUAGE plpgsql;

--綁定觸發器--

CREATE TRIGGER trg_check_allowance_main_id
BEFORE INSERT OR UPDATE ON allowance_detail
FOR EACH ROW
EXECUTE FUNCTION check_allowance_main_exists();


--函式--

CREATE OR REPLACE FUNCTION check_user_exists()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS(
        SELECT 1 FROM ONLY user_account WHERE id = NEW.modify_user_id
    ) THEN
        RAISE EXCEPTION 'UserAccount ID % dose not exist.', NEW.modify_user_id;
    END IF;

    RETURN NEW;
END
$$ LANGUAGE plpgsql;

--綁定觸發器--

CREATE TRIGGER trg_check_user_account_id_invoice_main
BEFORE INSERT OR UPDATE ON invoice_main
FOR EACH ROW
EXECUTE FUNCTION check_user_exists();

CREATE TRIGGER trg_check_user_account_id_invoice_detail
BEFORE INSERT OR UPDATE ON invoice_detail
FOR EACH ROW
EXECUTE FUNCTION check_user_exists();

CREATE TRIGGER trg_check_user_account_id_allowance_main
BEFORE INSERT OR UPDATE ON canceled_allowance
FOR EACH ROW
EXECUTE FUNCTION check_user_exists();

CREATE TRIGGER trg_check_user_account_id_allowance_detail 
BEFORE INSERT OR UPDATE ON allowance_detail 
FOR EACH ROW
EXECUTE FUNCTION check_user_exists();


--函式--

CREATE OR REPLACE FUNCTION check_company_exists()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS(
        SELECT 1 FROM ONLY company WHERE company_id = NEW.company_id
    ) THEN
        RAISE EXCEPTION 'Company ID % dose not exist.', NEW.company_id;
    END IF;

    RETURN NEW;
END
$$ LANGUAGE plpgsql;

--綁定觸發器--

CREATE TRIGGER trg_check_company_id_invoice_main
BEFORE INSERT OR UPDATE ON invoice_main
FOR EACH ROW
EXECUTE FUNCTION check_company_exists();

CREATE TRIGGER trg_check_company_id_invoice_detail
BEFORE INSERT OR UPDATE ON invoice_detail
FOR EACH ROW
EXECUTE FUNCTION check_company_exists();

CREATE TRIGGER trg_check_company_id_allowance_main
BEFORE INSERT OR UPDATE ON canceled_allowance
FOR EACH ROW
EXECUTE FUNCTION check_company_exists();

CREATE TRIGGER trg_check_company_id_allowance_detail 
BEFORE INSERT OR UPDATE ON allowance_detail 
FOR EACH ROW
EXECUTE FUNCTION check_company_exists();



