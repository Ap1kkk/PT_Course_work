public class GameBoard {
    private final int size; // Размер игрового поля
    private final Stone[][] board; // Массив для хранения камней

    // Конструктор
    public GameBoard(int size) {
        this.size = size;
        board = new Stone[size][size];
    }

    // Метод для размещения камня на доске
    public void placeStone(int x, int y, Stone stone) {
        if (isValidMove(x, y) && isCellEmpty(x, y) && stone != null) {
            board[x][y] = stone;
        } else {
            System.out.println("Invalid move!"); // Обработка ошибки
        }
    }

    // Метод для проверки правильности хода
    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    // Метод для проверки, пуста ли клетка
    private boolean isCellEmpty(int x, int y) {
        return board[x][y] == null;
    }

    // Метод для получения камня по координатам
    public Stone getStone(int x, int y) {
        return board[x][y];
    }

    public int getSize() {
        return this.size;
    }
}
