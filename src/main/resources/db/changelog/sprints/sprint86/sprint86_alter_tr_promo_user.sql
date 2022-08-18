alter table tr_promo_user
    add payment_status ENUM ("FREE", "PENDING", "PAID", "FAILED") default "FREE" not null,
    add payment_id VARCHAR(50) null,
    add payment_expired_at timestamp null;