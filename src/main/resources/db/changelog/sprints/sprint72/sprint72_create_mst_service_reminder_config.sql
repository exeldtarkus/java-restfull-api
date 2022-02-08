use servis;
create table mst_service_reminder_config
(
    id             BIGINT auto_increment,
    tipe_servis_id BIGINT                              null,
    title          VARCHAR(255)                        null,
    day            INT                                 null,
    email_body     TEXT                                null,
    created        timestamp default CURRENT_TIMESTAMP null,
    updated        timestamp                           null on update CURRENT_TIMESTAMP,
    constraint table_name_pk
        primary key (id),
    constraint mst_service_reminder_config_tipe_servis_id_fk
        foreign key (tipe_servis_id) references ref_tipe_servis (tipe_servis_id)
);