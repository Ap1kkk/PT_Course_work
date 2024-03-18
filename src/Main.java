public class Main {
    public static void main(String[] args) {
        // Создаем игровую доску размером 5x5
        GameBoard gameBoard = new GameBoard(5);

        // Предположим, что на доске уже есть размещенные камни
        // Здесь представлена простая ситуация для демонстрации
        // В реальной игре доска может быть любой инициализированной позиции
        Stone blackStone = new Stone(Color.BLACK);
        Stone whiteStone = new Stone(Color.WHITE);

        // Размещаем камни на доске
        gameBoard.placeStone(1, 2, blackStone); // Черный камень
        gameBoard.placeStone(2, 1, blackStone); // Черный камень
        gameBoard.placeStone(2, 3, blackStone); // Черный камень
        gameBoard.placeStone(3, 2, blackStone); // Черный камень
        gameBoard.placeStone(2, 0, whiteStone); // Белый камень
        gameBoard.placeStone(3, 1, whiteStone); // Белый камень
        gameBoard.placeStone(4, 2, whiteStone); // Белый камень
        gameBoard.placeStone(4, 3, whiteStone); // Белый камень

        // Задаем текущий цвет игрока
        Color playerColor = Color.BLACK; // Предположим, что текущий игрок - черные камни

        // Вычисляем оценку позиции
        int evaluation = EvaluationFunction.evaluatePosition(gameBoard, playerColor);

        // Выводим результат оценки
        System.out.println("Оценка позиции для текущего игрока: " + evaluation);
    }
}