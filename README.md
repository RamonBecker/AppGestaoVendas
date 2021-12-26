# Sales Management
 
## :information_source: Information 

The aim of the project is to develop an application for managing sales. During development we used some frameworks that are listed below.


## ⚠️ Prerequisite
[![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/br/java/technologies/javase-downloads.html) >= 11 

![Mysql Badge](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)

![Spring Badge](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

![Docker Badge](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

![jaeger-logo](https://user-images.githubusercontent.com/44611131/122459537-6bbcc500-cf87-11eb-9a77-4415df12cb9c.png)

![swagger](https://user-images.githubusercontent.com/44611131/122459677-9444bf00-cf87-11eb-8d12-e62969a3de1a.png)

![aptira-grafana-prometheus-training](https://user-images.githubusercontent.com/44611131/122459691-97d84600-cf87-11eb-9e99-4afc34cf26bf.png)


## :rocket: Installation

To install the project you must install mysql on your machine.

## ⚙️ Installing MySQL

Enter the following commands in the terminal.

```
sudo apt update
sudo apt install mysql-server

```
### Configuring MySQL

For new installations, you will want to run the security script that is included. This changes some of the less secure default options for things like root logins and example users. Enter the command below.

```
sudo mysql_secure_installation
```
This will take you through a series of prompts where you can make some changes to the security options of your MySQL installation. The first prompt will ask you if you want to configure the Validate Password Plugin, which can be used to test the strength of your MySQL password. Regardless of your choice, the next prompt will be to set the password for the MySQL root user. Sign in and then confirm a secure password of your choice.

From there, you can press Y and then ENTER to accept the default answers for all subsequent questions. This will remove some anonymous users and the test database, disable remote login for root, and load all of these new rules so that MySQL immediately respects the changes you made.

### Testing MySQL

To see if MYSQL is running, type the following command.

```
systemctl status mysql.service
```

If MySQL is not running, you can start it with the following command.
```
sudo systemctl start mysql

Now try to connect your root user to MySQL.
```
mysql -u root -p



## Project execution

In order to run the project, you must go into the project folder where the docker-compose file is and run the following command to start the docker.
In the file of prometheus.yml you must put your ip of your machine.

```
Where is it - targets: ['0.0.0.0:8080'] replace the zeros with your ip address
```

Before running docker-compose you must stop the MYSQL service, as the port that was configured in the docker for MYSQL is 3000. If the MYSQL service is not stopped there will be a conflict and the docker will not be executed. Enter the following command:

```
sudo service mysql stop
sudo service mysql status
sudo docker-compose up -d

```
To enter data in the application you must access the swagger in your browser. Enter the following address: 
```
localhost:8080/swagger-ui.html or http://127.0.0.1:8080/swagger-ui.html
```
If you want to collect application metrics with prometheus or grafana type the following into your browser:

```
For Prometheus type: 127.0.0.1:9090
For Grafana: 127.0.0.1:3000
```

![](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

```
git clone https://github.com/RamonBecker/AppSalesManagement.git
```

![](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)


```sh
git clone https://github.com/RamonBecker/AppSalesManagement.git
or install github https://desktop.github.com/ 

```

## :zap: Technologies	

- Java
- Docker
- Prometheus
- Grafana
- Spring boot

## :memo: Developed features

- [x] The user will be able to perform a product CRUD
- [x] The user will be able to perform a client CRUD
- [x] The user will be able to perform a category CRUD
- [x] The user will be able to perform a sales CRUD


## :technologist:	 Author

By Ramon Becker 👋🏽 Get in touch!



[<img src='https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/github.svg' alt='github' height='40'>](https://github.com/RamonBecker)  [<img src='https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/linkedin.svg' alt='linkedin' height='40'>](https://www.linkedin.com/in/https://www.linkedin.com/in/ramon-becker-da-silva-96b81b141//)
![Gmail Badge](https://img.shields.io/badge/-ramonbecker68@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:ramonbecker68@gmail.com)

