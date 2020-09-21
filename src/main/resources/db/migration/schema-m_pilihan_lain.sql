create table content.mst_pilihan_lain (
  id bigint not null AUTO_INCREMENT,
  created_at datetime,
  updated_at datetime,
  is_active bit,
  'desc' varchar(255),
  image_path varchar(255),
  image_path_mobile varchar(255),
  name varchar(255),
  title varchar(255),
  primary key (id)) engine = MyISAM