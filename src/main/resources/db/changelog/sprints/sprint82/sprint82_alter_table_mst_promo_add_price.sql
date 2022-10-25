ALTER TABLE content.mst_promo 
    ADD price DECIMAL(15,2)  NULL  DEFAULT NULL  AFTER vehicle_type_id;