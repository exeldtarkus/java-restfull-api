create table tr_blast_promo
(
    id         BIGINT auto_increment,
    promo_id   BIGINT       null,
    bengkel_id BIGINT       null,
    process_id VARCHAR(100) null,
    created                 timestamp  default CURRENT_TIMESTAMP null,
    updated                 timestamp                            null on update CURRENT_TIMESTAMP,
    constraint table_name_pk
        primary key (id),
    constraint tr_blast_promo_promo_id_fk
        foreign key (promo_id) references mst_promo (id)
);

create unique index tr_blast_promo_process_id_uindex
    on tr_blast_promo (process_id);


create table tr_blast_promo_detail
(
    id                BIGINT auto_increment,
    customer_name     VARCHAR(255) null,
    phonenumber       VARCHAR(30)  null,
    mobil_model       VARCHAR(100) null,
    mobil_plate_no    VARCHAR(100) null,
    tr_blast_promo_id BIGINT       not null,
    tr_promo_user_id  BIGINT       null,
    created                 timestamp  default CURRENT_TIMESTAMP null,
    updated                 timestamp                            null on update CURRENT_TIMESTAMP,
    constraint tr_blast_promo_detail_pk
        primary key (id),
    constraint tr_blast_promo_detail_tr_blast_promo_id_fk
        foreign key (tr_blast_promo_id) references tr_blast_promo (id),
    constraint tr_blast_promo_detail_tr_promo_user_id_fk
        foreign key (tr_promo_user_id) references tr_promo_user (id)
);