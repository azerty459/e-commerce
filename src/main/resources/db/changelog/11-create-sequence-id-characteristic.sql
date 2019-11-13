CREATE SEQUENCE my_serial AS integer START 1 OWNED BY characteristic_type.id_characteristic_type;

ALTER TABLE characteristic_type ALTER COLUMN id_characteristic_type SET DEFAULT nextval('my_serial');