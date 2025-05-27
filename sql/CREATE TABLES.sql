DROP TABLE IF EXISTS invoice_main CASCADE;
DROP TABLE IF EXISTS invoice_detail CASCADE;
DROP TABLE IF EXISTS allowance_main CASCADE;
DROP TABLE IF EXISTS allowance_detail CASCADE;
DROP TABLE IF EXISTS canceled_invoice CASCADE;
DROP TABLE IF EXISTS voided_invoice CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS canceled_allowance CASCADE;
DROP TABLE IF EXISTS company CASCADE;
DROP TABLE IF EXISTS assign_group CASCADE;
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS notification CASCADE;
DROP TABLE IF EXISTS notify_log CASCADE;
DROP TABLE IF EXISTS printer CASCADE;
DROP TABLE IF EXISTS product_item CASCADE;
DROP TABLE IF EXISTS user_account CASCADE;


-- === invoice_main base table ===

CREATE TABLE invoice_main (
    id BIGSERIAL,
    year_month VARCHAR,
    invoice_number VARCHAR,
    invoice_date DATE,
    invoice_time TIME,
    seller VARCHAR,
    buyer_identifier VARCHAR NOT NULL,
    buyer_name VARCHAR NOT NULL,
    buyer_address VARCHAR,
    buyer_person_in_charge VARCHAR,
    buyer_telephone_number VARCHAR,
    buyer_facsimile_number VARCHAR,
    buyer_email_address VARCHAR,
    buyer_customer_number VARCHAR,
    buyer_role_remark VARCHAR,
    buyer_remark VARCHAR,
    main_remark VARCHAR,
    customs_clearance_mark VARCHAR,
    category VARCHAR,
    relate_number VARCHAR,
    invoice_type VARCHAR,
    group_mark VARCHAR,
    donate_mark VARCHAR,
    carrier_type VARCHAR,
    carrier_id1 VARCHAR,
    carrier_id2 VARCHAR,
    print_mark VARCHAR,
    npoban VARCHAR,
    random_number VARCHAR,
    bonded_area_confirm BOOLEAN,
    zero_tax_rate_reason VARCHAR,
    reserved1 VARCHAR,
    reserved2 VARCHAR,
    sales_amount NUMERIC,
    free_tax_sales_amount NUMERIC,
    zero_tax_sales_amount NUMERIC,
    tax_type VARCHAR,
    tax_rate NUMERIC,
    tax_amount NUMERIC,
    total_amount NUMERIC,
    discount_amount NUMERIC,
    original_currency_amount NUMERIC,
    exchange_rate NUMERIC,
    currency VARCHAR,
    allowance_count INTEGER,
    total_allowance_amount NUMERIC,
    invoice_balance NUMERIC,
    tax_balance NUMERIC,
    upload_status VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER,
    PRIMARY KEY (id, invoice_date)
)PARTITION BY RANGE (invoice_date);



-- === Function to auto-create subtable yearly ===

-- Function to create subtable for invoice_main on October 1st
CREATE OR REPLACE FUNCTION create_invoice_main_subtable()
RETURNS void AS $$
DECLARE
    current_year INTEGER := EXTRACT(YEAR FROM CURRENT_DATE);
    next_taiwan_year INTEGER := current_year - 1911 + 1;
    subtable_name TEXT := 'invoice_main_' || next_taiwan_year;
BEGIN
    IF TO_CHAR(CURRENT_DATE, 'MM-DD') = '10-01' THEN
        EXECUTE format('
            CREATE TABLE IF NOT EXISTS %I (LIKE invoice_main INCLUDING ALL)
            PARTITION OF invoice_main FOR VALUES FROM (DATE %%L-01-01) TO (DATE %%L-12-31)',
            subtable_name, current_year, current_year);
    END IF;
END;
$$ LANGUAGE plpgsql;


-- === Additional tables ===


CREATE TABLE invoice_detail (
    id BIGSERIAL,
    invoice_date DATE,
    invoice_main_id BIGINT,
    invoice_number VARCHAR,
    description VARCHAR,
    quantity NUMERIC,
    unit VARCHAR,
    unit_price NUMERIC,
    tax_type VARCHAR,
    sales_amount NUMERIC,
    sequence_number INTEGER,
    remark VARCHAR,
    relate_number VARCHAR,
    is_enabled BOOLEAN,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER,
    PRIMARY KEY (id, invoice_date)
)PARTITION BY RANGE (invoice_date);



-- === Function to auto-create subtable yearly ===

-- Function to create subtable for invoice_detail on October 1st
CREATE OR REPLACE FUNCTION create_invoice_detail_subtable()
RETURNS void AS $$
DECLARE
    current_year INTEGER := EXTRACT(YEAR FROM CURRENT_DATE);
    next_taiwan_year INTEGER := current_year - 1911 + 1;
    subtable_name TEXT := 'invoice_detail_' || next_taiwan_year;
BEGIN
    IF TO_CHAR(CURRENT_DATE, 'MM-DD') = '10-01' THEN
        EXECUTE format('
            CREATE TABLE IF NOT EXISTS %I (LIKE invoice_detail INCLUDING ALL)
            PARTITION OF invoice_detail FOR VALUES FROM (DATE %%L-01-01) TO (DATE %%L-12-31)',
            subtable_name, current_year, current_year);
    END IF;
END;
$$ LANGUAGE plpgsql;



-- === Additional tables ===


CREATE TABLE allowance_main (
    id BIGSERIAL,
    allowance_number VARCHAR,
    allowance_date DATE,
    seller VARCHAR,
    buyer_identifier VARCHAR NOT NULL,
    buyer_name VARCHAR NOT NULL,
    buyer_address VARCHAR,
    buyer_person_in_charge VARCHAR,
    buyer_telephone_number VARCHAR,
    buyer_facsimile_number VARCHAR,
    buyer_email_address VARCHAR,
    buyer_customer_number VARCHAR,
    buyer_role_remark VARCHAR,
    allowance_type INTEGER,
    original_invoice_seller_id INTEGER,
    original_invoice_buyer_id INTEGER,
    tax_amount NUMERIC,
    total_amount NUMERIC,
    upload_status VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER,
    PRIMARY KEY (id, allowance_date)
)PARTITION BY RANGE (allowance_date);

-- === Function to auto-create subtable yearly ===

-- Function to create subtable for allowance_main on October 1st
CREATE OR REPLACE FUNCTION create_allowance_main_subtable()
RETURNS void AS $$
DECLARE
    current_year INTEGER := EXTRACT(YEAR FROM CURRENT_DATE);
    next_taiwan_year INTEGER := current_year - 1911 + 1;
    subtable_name TEXT := 'allowance_main_' || next_taiwan_year;
BEGIN
    IF TO_CHAR(CURRENT_DATE, 'MM-DD') = '10-01' THEN
        EXECUTE format('
            CREATE TABLE IF NOT EXISTS %I (LIKE allowance_main INCLUDING ALL)
            PARTITION OF allowance_main FOR VALUES FROM (DATE %%L-01-01) TO (DATE %%L-12-31)',
            subtable_name, current_year, current_year);
    END IF;
END;
$$ LANGUAGE plpgsql;


-- === Additional tables ===


CREATE TABLE allowance_detail (
    id BIGSERIAL,
    allowance_id BIGINT,
    allowance_date DATE,
    original_invoice_date DATE,
    original_invoice_number VARCHAR,
    original_sequence_number INTEGER,
    original_description VARCHAR,
    quantity NUMERIC,
    unit VARCHAR,
    unit_price NUMERIC,
    amount NUMERIC,
    tax NUMERIC,
    allowance_sequence_number INTEGER,
    tax_type VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER,
    PRIMARY KEY (id, allowance_date)
)PARTITION BY RANGE (allowance_date);

-- === Function to auto-create subtable yearly ===

-- Function to create subtable for allowance_detail on October 1st
CREATE OR REPLACE FUNCTION create_allowance_detail_subtable()
RETURNS void AS $$
DECLARE
    current_year INTEGER := EXTRACT(YEAR FROM CURRENT_DATE);
    next_taiwan_year INTEGER := current_year - 1911 + 1;
    subtable_name TEXT := 'allowance_detail_' || next_taiwan_year;
BEGIN
    IF TO_CHAR(CURRENT_DATE, 'MM-DD') = '10-01' THEN
        EXECUTE format('
            CREATE TABLE IF NOT EXISTS %I (LIKE allowance_detail INCLUDING ALL)
            PARTITION OF allowance_detail FOR VALUES FROM (DATE %%L-01-01) TO (DATE %%L-12-31)',
            subtable_name, current_year, current_year);
    END IF;
END;
$$ LANGUAGE plpgsql;




-- === Additional tables ===


CREATE TABLE company (
    company_id SERIAL PRIMARY KEY,
    identifier VARCHAR,
    name VARCHAR,
    person_in_charge VARCHAR,
    telephone_number VARCHAR,
    facsimile_number VARCHAR,
    email_address VARCHAR,
    company_code VARCHAR,
    role_remark VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER
);

CREATE TABLE printer (
    printer_id SERIAL PRIMARY KEY,
    company_id INTEGER REFERENCES company(company_id),
    printer_name VARCHAR,
    printer_key VARCHAR,
    print_type VARCHAR,
    printer_status INTEGER
);


CREATE TABLE user_account (
    id SERIAL PRIMARY KEY,
    company_id INTEGER REFERENCES company(company_id),
    account VARCHAR,
    password VARCHAR,
    name VARCHAR,
    email VARCHAR,
    phone VARCHAR,
    role INTEGER,
    printer_id INTEGER REFERENCES printer(printer_id),
    status INTEGER,
    pw_safe_date VARCHAR
);

CREATE TABLE customer (
    customer_id SERIAL PRIMARY KEY,
    customer_code VARCHAR,
    identifier VARCHAR,
    name VARCHAR,
    address VARCHAR,
    person_in_charge VARCHAR,
    telephone_number VARCHAR,
    facsimile_number VARCHAR,
    email_address VARCHAR,
    role_remark VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER REFERENCES user_account(id)
);    

CREATE TABLE canceled_invoice (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT,
    cancel_invoice_number VARCHAR,
    invoice_date DATE,
    buyer_id VARCHAR,
    seller_id VARCHAR,
    cancel_date DATE,
    cancel_time TIME,
    cancel_reason VARCHAR,
    return_tax_document_number VARCHAR,
    remark VARCHAR,
    reserved1 VARCHAR,
    reserved2 VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER REFERENCES user_account(id),
    FOREIGN KEY (invoice_id, invoice_date) REFERENCES invoice_main(id, invoice_date)
);


CREATE TABLE voided_invoice (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT,
    void_invoice_number VARCHAR,
    invoice_date DATE,
    buyer_id VARCHAR,
    seller_id VARCHAR,
    void_date DATE,
    void_time TIME,
    void_reason VARCHAR,
    remark VARCHAR,
    reserved1 VARCHAR,
    reserved2 VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER REFERENCES user_account(id),
    FOREIGN KEY (invoice_id, invoice_date) REFERENCES invoice_main(id, invoice_date)
);



CREATE TABLE assign_group (
    assign_id SERIAL PRIMARY KEY,
    year_month VARCHAR,
    invoice_track VARCHAR,
    company_id INTEGER REFERENCES company(company_id),
    printer_id INTEGER REFERENCES printer(printer_id),
    start_no VARCHAR,
    used_count INTEGER DEFAULT 0,
    last_used_no VARCHAR,
    status INTEGER DEFAULT 0,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER REFERENCES user_account(id)
);


CREATE TABLE canceled_allowance (
    id BIGSERIAL PRIMARY KEY,
    allowance_id BIGINT,
    cancel_allowance_number VARCHAR,
    allowance_date DATE,
    buyer_id VARCHAR,
    seller_id VARCHAR,
    cancel_date DATE,
    cancel_time TIME,
    cancel_reason VARCHAR,
    remark VARCHAR,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER REFERENCES user_account(id),
    FOREIGN KEY (allowance_id, allowance_date) REFERENCES allowance_main(id, allowance_date)
);


CREATE TABLE product_item (
    item_id BIGSERIAL PRIMARY KEY,
    description VARCHAR,
    quantity NUMERIC,
    unit VARCHAR,
    unit_price NUMERIC,
    tax_type VARCHAR,
    remark VARCHAR,
    is_enabled BOOLEAN,
    create_date TIMESTAMP,
    modify_date TIMESTAMP,
    modify_user_id INTEGER REFERENCES user_account(id)
);


CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    company_id INTEGER REFERENCES company(company_id),
    name VARCHAR,
    email VARCHAR,
    function INTEGER,
    is_enabled BOOLEAN
);

CREATE TABLE notify_log (
    id BIGSERIAL PRIMARY KEY,
    send INTEGER,
    email VARCHAR,
    subject VARCHAR
);


CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    year_month VARCHAR,
    lottery_number VARCHAR,
    lottery_prizes VARCHAR
);
