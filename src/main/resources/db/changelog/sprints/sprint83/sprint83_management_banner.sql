-- CREATE TABLE
create table map_banner_bengkel
(
    id          BIGINT auto_increment,
    banner_id   BIGINT       null,
    bengkel_id  BIGINT       null,
    created     timestamp  default CURRENT_TIMESTAMP null,
    constraint map_banner_bengkel_pk
        primary key (id),
    constraint map_banner_bengkel_bengkel_id_fk
        foreign key (bengkel_id) references bengkel.mst_bengkel (bengkel_id)
);

create unique index map_banner_bengkel_id_uindex
    on map_banner_bengkel (id);

-- ALTER TABLE 
alter table mst_banner
    add using_on int null after position,
    add area json null after using_on;

-- UPDATE DATA OLD BANNER
UPDATE  mst_banner
SET     using_on = 1
WHERE   using_on IS NULL;

-- CHANGE POSITION TYPE TO INTERGER
ALTER TABLE mst_banner modify position int(11)