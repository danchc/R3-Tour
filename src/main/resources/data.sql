INSERT INTO continente VALUES
                           (1, 'EUROPA'),
                           (2, 'AFRICA'),
                           (3, 'AMERICA'),
                           (4, 'ASIA');

INSERT INTO utente VALUES
                       (99999, 'cognome_utente', '2022-06-23 19:10:25-07', 'nome_utente', '123456');

INSERT INTO utente VALUES
                        (999991, 'Amministratore', '2022-06-23 19:10:25-07', 'R3Tour', '00000');

INSERT INTO credentials VALUES
                            (12345, 'test@email.com', true, '$2a$12$g1zyFaYti3uNtoW2NAJE4O6QJbHFOS2J0CbRnb6d/4toWCV9ww0tO', 'LOCAL' ,'DEFAULT', 'user', 99999);

INSERT INTO credentials VALUES
                            (1234567, 'r3.cate@gmail.com', true, '$2a$12$u05KxnLT1TDgBP.8xZMzDOY7e86e/FwKEYEI9ChcikhgoSMD7Fd0q', 'LOCAL', 'ADMIN', 'admin', 999991);