## PostgreSQL database:

* Once you install the **PostgreSQL** you can connect to it by `sudo -u postgres psql`
* Create new user: `CREATE USER ontologio PASSWORD 'CrossW41k';`
* Create new database: `CREATE DATABASE ontologio owner ontologio;`
* Grant privileges: `GRANT ALL PRIVILEGES ON DATABASE ontologio TO ontologio;`