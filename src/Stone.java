public class Stone {
    private Color color; // Цвет камня
    private int x; // Координата x
    private int y; // Координата y

    public Stone(Color color) {
        this.color = color;
    }

    // Конструктор
    public Stone(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    // Геттеры и сеттеры для цвета и координат
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
