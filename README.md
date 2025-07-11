# Animal Shelter Web App

Веб-приложение приюта для животных, реализующее публикацию карточек животных, регистрацию и аутентификацию пользователей, возможность оставлять заявки на животных и лайкать их. Также в системе предусмотрена административная панель для управления карточками и заявками.

## Стек

**Backend**
  - Java 8
  - Spring Boot
  - Spring Security (JWT для аутентификации)
  - Hibernate + JPA
  - PostgreSQL

**Frontend**
  - Vue.js
  - Vite
  - Vue Router
  - Pinia
  - Ant Design Vue
  - Axios

Управление зависимостями: Maven (backend), npm (frontend)

## Требования к окружению

- Java 8 или выше  
- Node.js
- PostgreSQL  
- Docker (опционально)

## Сборка 1
   ```sh 
   docker-compose up --build
   ```

## Сборка 2
- **Backend**  
   ```sh 
   cd shelter-backend   
   mvn clean package   
   mvn spring-boot:run   
   ```
   или после сборки 

   ```sh 
   java -jar target/Shelter-1.0-SNAPSHOT.jar
   ```
- **Frontend**  
    ```sh 
    cd shelter-frontend
    npm install
    npm run build
    npm run serve
    ```
## Основные страницы

**Главная**
  - Список карточек животных по убыванию даты публикации
  - Кнопка «Показать больше» для длинных описаний
  - Лайки (для авторизованных пользователей)
  - Просмотр заявок по кнопке «Заявки»

**Аутентификация/авторизация**

**Регистрация**

**Страница заявок**
  - Пользователь (роль ROLE_USER) видит только свои заявки
  - Администратор (роль ROLE_ADMIN) видит все заявки

## Дополнительные возможности
**Админ-панель**
  - CRUD для карточек животных на главной странице
  - Удаление заявок

**Тематики и фильтрация**  
  - Администратор задаёт тематики карточкам (например, вид животного)
  - Пользователь выбирает любимые и запретные темы (животные которые он может взять)
  - Карточки выводятся в приоритете по совпадениям и исключаются по запретным тематикам

## Убедитесь
что  [http://localhost:8080](http://localhost:8080) свободен.
