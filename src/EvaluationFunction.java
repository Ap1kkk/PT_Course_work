import java.util.ArrayDeque;
import java.util.Queue;

public class EvaluationFunction {

    // Весовые коэффициенты для различных компонентов оценки
    private static final int TERRITORY_WEIGHT = 20;
    private static final int STONE_COUNT_WEIGHT = 10;
    private static final int LIBERTY_COUNT_WEIGHT = 5;

    // Метод для оценки текущей позиции на доске
    public static int evaluatePosition(GameBoard board, Color playerColor) {
        int playerTerritory = countSurroundedCells(board, playerColor);
        int opponentTerritory = countSurroundedCells(board, getOpponentColor(playerColor));

        int playerStoneCount = countStones(board, playerColor);
        int opponentStoneCount = countStones(board, getOpponentColor(playerColor));

        int playerLiberties = calculateLiberties(board, playerColor);
        int opponentLiberties = calculateLiberties(board, getOpponentColor(playerColor));

        // Вычисляем оценку позиции с учетом всех компонентов
        int playerScore = playerTerritory * TERRITORY_WEIGHT
                + playerStoneCount * STONE_COUNT_WEIGHT
                + playerLiberties * LIBERTY_COUNT_WEIGHT;

        int opponentScore = opponentTerritory * TERRITORY_WEIGHT
                + opponentStoneCount * STONE_COUNT_WEIGHT
                + opponentLiberties * LIBERTY_COUNT_WEIGHT;

        // Возвращаем разницу между оценками игрока и противника
        return playerScore - opponentScore;
    }

    public static int countSurroundedCells(GameBoard board, Color color) {
        int surroundedCells = 0;
        boolean[][] visited = new boolean[board.getSize()][board.getSize()];
        boolean[][] surrounded = new boolean[board.getSize()][board.getSize()];
        Integer count = 0;

        // Обходим каждую клетку доски
        for (int x = 1; x < board.getSize() - 1; x++) {
            for (int y = 1; y < board.getSize() - 1; y++) {
                isSurrounded(board, color, x, y, visited, surrounded, count);
            }
        }

        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                if(surrounded[x][y])
                    surroundedCells++;
            }
        }

        return surroundedCells;
    }

    private static boolean isSurrounded(GameBoard board, Color color, int x, int y, boolean[][] visited, boolean[][] surrounded, Integer count) {
        if (x < 0 || x >= board.getSize() || y < 0 || y >= board.getSize()) {
            return false;
        }

        if(x == 0 || x == board.getSize() || y == 0 || y == board.getSize()) {
            if(board.getStone(x, y) != null && board.getStone(x, y).getColor() == color) {
                return true;
            }
            return false;
        }

        if(board.getStone(x, y) != null) {
            if(board.getStone(x, y).getColor() == color)
                return true;
            return false;
        }

        // Если клетка уже была посещена или принадлежит противоположному цвету, возвращаем 0
        if (visited[x][y])
            return true;

        // Отмечаем клетку как посещенную
        visited[x][y] = true;

        boolean isSurrounded = true;
        isSurrounded &= isSurrounded(board, color, x - 1, y, visited, surrounded, count);
        isSurrounded &= isSurrounded(board, color, x + 1, y, visited, surrounded, count);
        isSurrounded &= isSurrounded(board, color, x, y - 1, visited, surrounded, count);
        isSurrounded &= isSurrounded(board, color, x, y + 1, visited, surrounded, count);
        surrounded[x][y] = isSurrounded;

        return isSurrounded;
    }

    // Метод для подсчета количества камней указанного цвета на доске
    private static int countStones(GameBoard board, Color color) {
        int count = 0;
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                Stone stone = board.getStone(x, y);
                if (stone != null && stone.getColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    // Метод для подсчета количества свобод групп камней указанного цвета на доске
    private static int calculateLiberties(GameBoard board, Color color) {
        int liberties = 0;
        boolean[][] visited = new boolean[board.getSize()][board.getSize()];

        // Обходим каждую клетку доски
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                // Проверяем, принадлежит ли камень текущему игроку и не был ли он уже посещен
                if (!visited[x][y] && board.getStone(x, y) != null && board.getStone(x, y).getColor() == color) {
                    // Используем поиск в глубину для определения либертей группы камней
                    int groupLiberties = calculateGroupLiberties(board, color, x, y, visited);
                    liberties += groupLiberties;
                }
            }
        }

        return liberties;
    }

    // Рекурсивная функция для подсчета либертей группы камней
    private static int calculateGroupLiberties(GameBoard board, Color color, int x, int y, boolean[][] visited) {
        // Проверяем, что мы не вышли за границы доски
        if (x < 0 || x >= board.getSize() || y < 0 || y >= board.getSize()) {
            return 0;
        }

        // Если клетка уже была посещена или принадлежит противоположному цвету, возвращаем 0
        if (visited[x][y] || (board.getStone(x, y) != null && board.getStone(x, y).getColor() != color)) {
            return 0;
        }

        // Отмечаем клетку как посещенную
        visited[x][y] = true;

        // Проверяем, есть ли свободные клетки (либерти) вокруг текущей
        int liberties = 0;
        if (board.getStone(x, y) == null) {
            liberties++;
        } else {
            // Рекурсивно вызываем функцию для соседних клеток
            liberties += calculateGroupLiberties(board, color, x - 1, y, visited);
            liberties += calculateGroupLiberties(board, color, x + 1, y, visited);
            liberties += calculateGroupLiberties(board, color, x, y - 1, visited);
            liberties += calculateGroupLiberties(board, color, x, y + 1, visited);
        }

        return liberties;
    }

    // Метод для получения цвета противоположного игрока
    private static Color getOpponentColor(Color color) {
        return (color == Color.BLACK) ? Color.WHITE : Color.BLACK;
    }
}
