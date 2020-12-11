package aoc;

import com.google.common.base.Objects;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() &&
            ((Coordinate)obj).x == this.x &&
            ((Coordinate)obj).y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.x, this.y);
    }
}