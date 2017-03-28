drop table if exists Trip;

CREATE TABLE Trip(Id text, cost integer, cap integer, desc text, time DATE, duration INTEGER, start FLOAT, name text);

INSERT INTO Trip values ('Gt7fO', 10000, 12, 'Jeep-tours around some famous Icelandic glaciers', 2017-08-12, 9, 10.00, 'Ultrajepp Glaciertours');
INSERT INTO Trip values ('W3jgY', 15000, 12, 'Jeep-tour around Icelandic highland, stopping at POI', 2017-08-20, 3, 4.00, 'Ultrajepp Northern Lights tour');
INSERT INTO Trip values ('MxuCe', 12000, 12, 'Drive-around of the Golden Circle', 2017-09-09, 9, 9.00, 'Golden Circle tour');
INSERT INTO Trip values ('yoGWU', 20000, 36, 'Sailing out to sea to look at some whales.', 2017-08-12, 7, 8.00, 'Whale Extravaganza');
INSERT INTO Trip values ('zekKd', 100000, 18, 'Horseback riding of Icelandic highlands', 2017-07-12, 3, 14.00, 'Horsing around(short)');
INSERT INTO Trip values ('Jz7F1', 25000, 8, 'Horseback riding of Icelandic highlands', 2017-09-24, 7, 14.00, 'Horsing around(medium)');
INSERT INTO Trip values ('zSdPS', 30000, 8, 'Horseback riding of Icelandic highlands', 2017-08-25, 12, 14.00, 'Horsing around(long)');
INSERT INTO Trip values ('EM2vx', 40000, 50, 'Take part in a reenactment of viking era Iceland', 2017-11-03, 3, 14.00, 'The viking experience');
INSERT INTO Trip values ('E0GV6', 50000, 1000, 'Hop on a tour going through the more famous museums in Reykjavik', 2017-10-12, 3, 14.00, 'Museum ride');
INSERT INTO Trip values ('cCszZ', 60000, 12, 'Select a museum and get a tourguide to walk you through it.', 2017-08-02, 3, 14.00, 'Tourguides');
