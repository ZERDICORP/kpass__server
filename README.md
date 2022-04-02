# kpass__server :point_right::point_left:
#### Application for keeping logins and passwords ([client](https://github.com/ZERDICORP/kpass__client)).
## Launch Guide :monkey:
#### Clone repository
```
$ git clone https://github.com/ZERDICORP/kpass__server.git
```

#### Check if the following dependencies are installed
```
$ java --version
openjdk 17.0.3 2022-04-19
OpenJDK Runtime Environment (build 17.0.3+3)
OpenJDK 64-Bit Server VM (build 17.0.3+3, mixed mode)
$ jar --vecrion
jar 17.0.3
$ mariadb --version
mariadb  Ver 15.1 Distrib 10.7.3-MariaDB, for Linux (x86_64) using readline 5.1
```

#### Run the following command
```
$ cd kpass__server/src && ./build && cd ../build
```

#### Create a database
```
$ mysql -u root -p
MariaDB> create database kpass;
MariaDB> exit;
```

#### Now you need to load the sql dump
```
$ mysql -u root -p kpass < resources/mysql_dump/kpass.sql
```

#### Setup config
> Example setup
```
$ vim resources/app.cfg
port = 8000
databaseName = kpass
databasePassword = Qwerty123
emailSender = my.kpass.server@gmail.com
emailPassword = Qwerty123
```

#### Now everything is ready to run
```
$ ./run
```
