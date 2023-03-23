Контрольна робота №2
Вебсайт по оренді автівок

Дана котрольна робота складаеться з спроєктованої бази даних, бекєнду розробленного на мові програмування Java використовуючи Spring framewoork з шаблонізатором thymeleaf, а також вебсайту.

База даних складаеться з 5 таблиць
Clients - таблиця користувачів
id, Full_Name, Address, Photo, Email, Phone_Number, Rating, Driver_Category
id - ключове автогенероване поле
у полі Photo зберігаеться веб адресса зображення
Rating - рейтинг користувача, відповідно до якого обмежуеться доступ до деяких автівок(1-5)
Driver_category - зберігае категорію водійських прав (A,B,C,D)

Categories - таблиця категорій автівок
id, name, Required_category, Required_raiting, Rental_Rate_Per_Hour
id - ключове автогенероване поле
Required_category -  зберігае категорію водійських прав (A,B,C,D) необхідних для даної категорії авто
Required_raiting -  зберігае необхідний рейтинг кліента (1-5) необхідний для даної категорії авто
Rental_Rate_Per_Hour - рекомендована ціна одніеї години користування

Cars - таблиця автівок
id, Brand, Model, Plate_Number, Category_id
id - ключове автогенероване поле
Category_id - посилання на категорію авто до якої належить ця автівка

Orders - таблиця замовлень
id, client_id, car_id, Start_Date, End_date, cost
id - ключове автогенероване поле
client_id - посиланя на кліента
car_id - посилання на автівку
Start_Date - дата та час початку оренди
End_date - дата та час закінчення оренди
cost - вартість замовлення

payments - таблиця платежів
id, client_id, date, Cheque_id, Payment_sum
id - ключове автогенероване поле
client_id - посилання на кліента
Cheque_id - номер чеку
Payment_sum - сплачена сума

