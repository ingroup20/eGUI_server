CREATE TABLE IF NOT EXISTS invoice_main_114
PARTITION OF invoice_main
FOR VALUES FROM ('2025-01-01') TO ('2025-12-31');

CREATE TABLE IF NOT EXISTS invoice_detail_114
PARTITION OF invoice_detail
FOR VALUES FROM ('2025-01-01') TO ('2025-12-31');

CREATE TABLE IF NOT EXISTS allowance_main_114
PARTITION OF allowance_main
FOR VALUES FROM ('2025-01-01') TO ('2025-12-31');

CREATE TABLE IF NOT EXISTS allowance_detail_114
PARTITION OF allowance_detail
FOR VALUES FROM ('2025-01-01') TO ('2025-12-31');




INSERT INTO company (
    identifier,
    name,
    address,
    person_in_charge,
    telephone_number,
    facsimile_number,
    email_address,
    role_remark,
    create_date,
    modify_date,
    modify_user_id
) VALUES (
    '12345678',
    '範例公司',
    '台灣地址',
    '王大明',
    '02-12345678',
    '02-87654321',
    'example@company.com',
    '買受人',
    '2025-05-26',
    '2025-05-26',
    NULL
);





INSERT INTO user_account (
    company_id,
    account,
    password,
    name,
    email,
    phone,
    role,
    printer_id,
    status,
    pw_safe_date
) VALUES (
    1, 
    'test_user',
    'password123',
    '測試用戶',
    'test@example.com',
    '0912345678',
    1, 
    NULL,
    1, 
    '2025-05-26'
);


-- 非固定資料-----

INSERT INTO assign_group (
    year_month,
    invoice_track,
    company_id,
    printer_id,
    start_no,
    used_count,
    last_used_no,
    status ,
    create_date ,
    modify_date ,
    modify_user_id
) VALUES (
    '11406', 
    'AA',
    1,
    NULL,
    '00000000',
    0,
    NULL, 
    0,
    NULL, 
    null,
    NULL
);

INSERT INTO printer (
    company_id,
    printer_name,
    print_type,
    printer_status
) VALUES (
    1,
    'test-printer',
    '1',
    1
);


