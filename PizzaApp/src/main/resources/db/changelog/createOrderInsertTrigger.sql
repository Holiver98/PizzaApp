CREATE OR REPLACE TRIGGER orders_tb_insert
            BEFORE INSERT ON orders
            FOR EACH ROW
            BEGIN
            SELECT orders_tb_seq.nextval
            INTO :new.id
            FROM dual;
            END;