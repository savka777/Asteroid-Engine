package Utilities;

// mutable 2D vectors
public final class Vector2D {
    public double x, y;

    // constructor for zero vector
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    // set coordinates
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;

        return this;
    }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {

        if (o instanceof Vector2D) {
            Vector2D v = (Vector2D) o;
            return (this.x == v.x) && (this.y == v.y);
        }

        return false;
    }

    // String for displaying vector as text
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    //  magnitude, distance from the ogigin (length of the vector)
    public double mag() {
       double square_x = this.x * this.x;
       double square_y = this.y * this.y;

       return Math.sqrt(square_x + square_y);
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
// can be calculated using Math.atan2 
    public double angle() {
        return Math.atan2(this.y,this.x);
    }

    // angle between this vector and another vector in range [-PI,PI]
    public double angle(Vector2D other) {
        double dotProduct = this.dot(other);
        double determinant = this.x * other.y - this.y * other.x;
        return Math.atan2(determinant, dotProduct);
    }

    // add argument vector
    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    // add values to coordinates
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    // adding a scaled version of another vector
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += v.x * fac;
        this.y += v.y * fac;
        return this;
    }

    // subtract argument vector
    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    // subtract values from coordinates
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    // multiply with factor (scaler multiplication)
    public Vector2D mult(double fac) {
        this.x *= fac;
        this.y *= fac;
        return this;
    }

    // rotate by angle given in radians
    public Vector2D rotate(double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);

        double newX = this.x * cosTheta - this.y * sinTheta;
        double newY = this.x * sinTheta + this.y * cosTheta;

        this.x = newX;  
        this.y = newY;
        return this;
    }

    // "dot product" ("scalar product") with argument vector
    // Ax * Bx + Ay * By
    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    // distance to argument vector (use euclidean distance formula)
    public double dist(Vector2D v) {
        // Vector2D w = this.subtract(v);
        // return w.mag();
        Double distanceX = this.x - v.x;
        Double distanceY  = this.y - v.y;
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    // normalise vector so that magnitude becomes 1 (we dont care about speed just direction)
    public Vector2D normalise() {
        double square_rooted = Math.sqrt(this.x * this.x + y * y);
        this. x = x / square_rooted;
        this. y = y / square_rooted;
        return this;
    }

    // wrap-around operation, assumes w> 0 and h>0
    // remember to manage negative values of the coordinates
    public Vector2D wrap(double w, double h) {

        if(this.x < 0) this.x += w;
        else if(this.x > w) this.x -= w;

        if(this.y < 0) this.y += h;
        else if (this.y > h) this.y -= h;

        return this;
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {
        Vector2D v = new Vector2D();

        v.x = mag * Math.cos(angle);
        v.y = mag * Math.sin(angle);

        return v;
    }

    public double[] toArray() {
        return new double[]{x, y};
    }

}