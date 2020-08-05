CREATE OR REPLACE TRIGGER pizzas_tb_insert
            BEFORE INSERT ON pizzas
            FOR EACH ROW
            BEGIN
            SELECT pizzas_tb_seq.nextval
            INTO :new.id
            FROM dual;
            END;