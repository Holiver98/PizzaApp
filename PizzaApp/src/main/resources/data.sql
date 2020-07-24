INSERT INTO user (email, username, password) VALUES('bobby@gmail.com', 'Bobby', '123456')
INSERT INTO user (email, username, password) VALUES('admin@gmail.com', 'admin', 'admin')
INSERT INTO user (email, username, password) VALUES('max@something.com', 'Max', '000000')

INSERT INTO pizza (id, name, size, rating_average, price, custom) VALUES(1, 'Pepperoni pizza', 'NORMAL', 4, 34, false)
INSERT INTO pizza (id, name, size, rating_average, price, custom) VALUES(2, 'Meat lover', 'SMALL', 3.5, 18.4, false)

INSERT INTO ingredient (name, price, type) VALUES('Onion', 2.4, 'PIZZA_TOPPING')
INSERT INTO ingredient (name, price, type) VALUES('Tomato', 2.2, 'PIZZA_BASESAUCE')
INSERT INTO ingredient (name, price, type) VALUES('Cheese', 6.5, 'PIZZA_TOPPING')
INSERT INTO ingredient (name, price, type) VALUES('Pepperoni', 13.2, 'PIZZA_TOPPING')
INSERT INTO ingredient (name, price, type) VALUES('Ham', 16.2, 'PIZZA_TOPPING')

INSERT INTO pizza_ingredients (pizza_id, ingredients_name) VALUES(1, 'Onion')
INSERT INTO pizza_ingredients (pizza_id, ingredients_name) VALUES(1, 'Tomato')
INSERT INTO pizza_ingredients (pizza_id, ingredients_name) VALUES(1, 'Pepperoni')
INSERT INTO pizza_ingredients (pizza_id, ingredients_name) VALUES(1, 'Ham')
INSERT INTO pizza_ingredients (pizza_id, ingredients_name) VALUES(2, 'Tomato')
INSERT INTO pizza_ingredients (pizza_id, ingredients_name) VALUES(2, 'Ham')

INSERT INTO rating (pizza_id, rating, user_email_address) VALUES(1, 2, 'bobby@gmail.com')
INSERT INTO rating (pizza_id, rating, user_email_address) VALUES(2, 4, 'bobby@gmail.com')
INSERT INTO rating (pizza_id, rating, user_email_address) VALUES(1, 5, 'max@something.com')