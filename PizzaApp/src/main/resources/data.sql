INSERT INTO users (email, username, password, role) VALUES('bobby@gmail.com', 'Bobby', '123456', 'CUSTOMER')
INSERT INTO users (email, username, password, role) VALUES('admin@gmail.com', 'admin', 'admin', 'CHEF')
INSERT INTO users (email, username, password, role) VALUES('max@something.com', 'Max', '000000', 'CUSTOMER')

INSERT INTO pizzas (name, size, rating_average, price, custom) VALUES('Pepperoni pizza', 'NORMAL', 4, 34, false)
INSERT INTO pizzas (name, size, rating_average, price, custom) VALUES('Meat lover', 'SMALL', 3.5, 18.4, false)

INSERT INTO ingredients(name, price, type) VALUES('Onion', 2.4, 'PIZZA_TOPPING')
INSERT INTO ingredients (name, price, type) VALUES('Tomato', 2.2, 'PIZZA_BASESAUCE')
INSERT INTO ingredients (name, price, type) VALUES('Cheese', 6.5, 'PIZZA_TOPPING')
INSERT INTO ingredients (name, price, type) VALUES('Pepperoni', 13.2, 'PIZZA_TOPPING')
INSERT INTO ingredients (name, price, type) VALUES('Ham', 16.2, 'PIZZA_TOPPING')

INSERT INTO pizzas_ingredients (pizzas_id, ingredients_name) VALUES(1, 'Onion')
INSERT INTO pizzas_ingredients (pizzas_id, ingredients_name) VALUES(1, 'Tomato')
INSERT INTO pizzas_ingredients (pizzas_id, ingredients_name) VALUES(1, 'Pepperoni')
INSERT INTO pizzas_ingredients (pizzas_id, ingredients_name) VALUES(1, 'Ham')
INSERT INTO pizzas_ingredients (pizzas_id, ingredients_name) VALUES(2, 'Tomato')
INSERT INTO pizzas_ingredients (pizzas_id, ingredients_name) VALUES(2, 'Ham')

INSERT INTO ratings (pizza_id, rating, email) VALUES(1, 2, 'bobby@gmail.com')
INSERT INTO ratings (pizza_id, rating, email) VALUES(2, 4, 'bobby@gmail.com')
INSERT INTO ratings (pizza_id, rating, email) VALUES(1, 5, 'max@something.com')