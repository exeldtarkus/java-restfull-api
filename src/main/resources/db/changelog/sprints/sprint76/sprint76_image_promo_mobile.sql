UPDATE  content.mst_promo a
SET     a.image_path_mobile = a.image_path 
WHERE   a.image_path IS NOT NULL 
AND     a.image_path_mobile IS NULL;
;

UPDATE  content.mst_promo a
SET     a.image_path_mobile_2 = a.image_path_2 
WHERE   a.image_path_2 IS NOT NULL 
AND     a.image_path_mobile_2 IS NULL;
;