import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Подключаемся к БД
        DatabaseConnection.getConnection();
        System.out.println("🔌 Подключение к базе данных успешно!");

        // Тестируем UserDAO
        try {
            // 1. Создаём нового пользователя (ID = 0 — временный)
            User newUser = new User( "bunny_lover5647", "carrot128673", 1, User.ADMIN);
            System.out.println("\n🎀 Создан пользователь: " + newUser);

            User newUser2 = new User("bunny_lover8822", "carrot7712333", 3, User.ADMIN);
            System.out.println("\n🎀 Создан пользователь: " + newUser2);

            // 2. Сохраняем в БД (получаем реальный ID)
            User savedUser = UserDAO.createUser(newUser);
            System.out.println("💾 Сохранён в БД: " + savedUser);

            User savedUser2 = UserDAO.createUser(newUser2);
            System.out.println("💾 Сохранён в БД: " + savedUser2);

//            // 3. Ищем пользователя по ID
//            QueryResultWrapper wrapper = UserDAO.findById(savedUser.getUserId());
//            User foundUser = (User) wrapper.unwrap();
//            System.out.println("🔍 Найден в БД: " + foundUser);
//
//            // 4. Меняем пароль и обновляем
//            foundUser.setPassword("superCarrot2024");
//            UserDAO.update(foundUser);
//            System.out.println("🔄 Пароль обновлён!");
//
//            // 5. Проверяем обновлённые данные
//            wrapper = UserDAO.findById(foundUser.getUserId());
//            User updatedUser = (User) wrapper.unwrap();
//            System.out.println("🔍 Обновлённые данные: " + updatedUser);
//
//            // 6. Удаляем пользователя
//            UserDAO.delete(updatedUser.getUserId());
//            System.out.println("🗑️ Пользователь удалён!");
//
//            // 7. Проверяем, что пользователь удалён
//            wrapper = UserDAO.findById(updatedUser.getUserId());
//            if (wrapper.unwrap() == null) {
//                System.out.println("✅ Пользователь успешно удалён из БД!");
//            }

        } catch (SQLException e) {
            System.err.println("💔 Ошибка при работе с БД: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Закрываем соединение (если нужно)
            if (DatabaseConnection.getConnection() != null) {
                DatabaseConnection.getConnection().close();
                System.out.println("\n🔌 Соединение с БД закрыто.");
            }
        }
    }
}