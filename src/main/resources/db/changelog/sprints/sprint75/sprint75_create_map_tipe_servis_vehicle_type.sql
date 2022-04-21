use servis;
create table map_tipe_servis_vehicle_type
(
    id              BIGINT auto_increment,
    tipe_servis_id  BIGINT                              not null,
    vehicle_type_id BIGINT                              not null,
    created         timestamp default CURRENT_TIMESTAMP null,
    updated         timestamp                           null on update CURRENT_TIMESTAMP,
    constraint map_tipe_servis_vehicle_type_pk
        primary key (id),
    constraint map_tipe_servis_vehicle_type_ref_model_fk
        foreign key (tipe_servis_id) references ref_tipe_servis (tipe_servis_id)
);