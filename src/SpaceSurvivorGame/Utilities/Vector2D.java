package SpaceSurvivorGame.Utilities;

public final class Vector2D {
    public double x, y;


    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2D set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;

        return this;
    }

    public boolean equals(Object o) {

        if (o instanceof Vector2D) {
            Vector2D v = (Vector2D) o;
            return (this.x == v.x) && (this.y == v.y);
        }

        return false;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public double mag() {
       double square_x = this.x * this.x;
       double square_y = this.y * this.y;

       return Math.sqrt(square_x + square_y);
    }


    public double angle() {
        return Math.atan2(this.y,this.x);
    }

    public double angle(Vector2D other) {
        double dotProduct = this.dot(other);
        double determinant = this.x * other.y - this.y * other.x;
        return Math.atan2(determinant, dotProduct);
    }

    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

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

    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2D mult(double fac) {
        this.x *= fac;
        this.y *= fac;
        return this;
    }

    public Vector2D rotate(double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);

        double newX = this.x * cosTheta - this.y * sinTheta;
        double newY = this.x * sinTheta + this.y * cosTheta;

        this.x = newX;  
        this.y = newY;
        return this;
    }

    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    public double dist(Vector2D v) {
        Double distanceX = this.x - v.x;
        Double distanceY  = this.y - v.y;
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public Vector2D normalise() {
        double square_rooted = Math.sqrt(this.x * this.x + y * y);
        this. x = x / square_rooted;
        this. y = y / square_rooted;
        return this;
    }


    public Vector2D wrap(double w, double h) {

        this.x = ((this.x % w) + w) % w;
        this.y = ((this.y % h) + h) % h;
        return this;
    }

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