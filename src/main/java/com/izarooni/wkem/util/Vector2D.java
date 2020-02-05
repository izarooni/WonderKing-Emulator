package com.izarooni.wkem.util;

import java.awt.*;
import java.util.Objects;

/**
 * @author izarooni
 */
public class Vector2D {

    private int x, y;

    public Vector2D() {
        x = y = 0;
    }

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    @Override
    public String toString() {
        return String.format("Vector2D{x=%d, y=%d}", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return x == vector2D.x && y == vector2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int distance(Vector2D v2) {
        int dx = v2.getX() - getX();
        int dy = v2.getY() - getY();
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    public Point toPoint() {
        return new Point(x, y);
    }

    public Vector2D set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2D set(Vector2D v2) {
        this.x = v2.x;
        this.y = v2.y;
        return this;
    }

    public Vector2D add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2D subtract(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public int getX() {
        return x;
    }

    public Vector2D setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2D addX(int x) {
        this.x += x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Vector2D setY(int y) {
        this.y = y;
        return this;
    }

    public Vector2D addY(int y) {
        this.y += y;
        return this;
    }
}
