package aoc;

import com.google.common.base.Objects;

public class Coordinate {
    public int x;
    public int y;
    public int z;
    public int w;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 0;
    }

    public Coordinate(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() &&
            ((Coordinate)obj).x == this.x &&
            ((Coordinate)obj).y == this.y &&
            ((Coordinate)obj).z == this.z &&
            ((Coordinate)obj).w == this.w;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.x, this.y, this.z, this.w);
    }
}