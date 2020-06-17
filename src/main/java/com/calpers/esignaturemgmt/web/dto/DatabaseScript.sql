-- Database Script  --

-- Change Signature table column types ----------

Alter Table signature
Modify Column type int;

Alter Table signature
Modify Column status int;