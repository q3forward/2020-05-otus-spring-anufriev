# Проектная работа "Агрегатор инновационных медицинских разработок"

## Описание
Приложения для агрегации, хранения и управления медицинскими препаратами, которые разрабатывают биотехнологичские компании.<br/>
При старте приложения в БД попадают только поддерживаемые компании.<br/>
Выгрузка препаратов возможна со страницы списка компаний по ссылке "Выгрузить данные" для конкретной компании, либо вызовом АПИ <br/>
http://localhost:8080/api/update/abbvie<br/>
http://localhost:8080/api/update/bms
 

#### Запуск
Запуск сервисов осуществляется в следующей последовательности:<br/>
1) discovery-server
2) drug, drug-upd
3) drug-ui
4) hystrix-dashboard

#### Сервис Drug
Основной сервис хранения и CRUD операций для препаратов и компаний<br/>
http://localhost:8080/swagger-ui.html 

#### Сервис Updater (drug-upd)
Cервис для выгрузки препаратов с сайтов компаний<br/>
http://localhost:8081/swagger-ui.html

#### Сервис Drug-ui
Cервис для визуального отображения и управления содержимым БД<br/>
http://localhost:8082

#### Service discovery
http://localhost:8001

#### Hystrix dashboard
http://localhost:8002/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8002%2Fturbine.stream
