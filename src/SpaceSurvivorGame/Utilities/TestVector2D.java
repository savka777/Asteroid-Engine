package SpaceSurvivorGame.Utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;


public class TestVector2D {

	public static final double APPROX_ZERO = 1E-10;
	public static final int WIDTH = 200;
	public static final int HEIGHT = 100;
	public static final double RADIUS = Math.random() * 0.1 * WIDTH;
	public static final double vx = Math.random() * WIDTH;
	public static final double vy = Math.random() * HEIGHT;
	public static final double wx = Math.random() * WIDTH;
	public static final double wy = Math.random() * HEIGHT;
	public Vector2D v, w, vCopy, wCopy;

	// reset v and w to be equal to V and W before every test
	@Before
	public void reset() {
		v = new Vector2D(vx, vy);
		vCopy = new Vector2D(vx, vy);
		w = new Vector2D(wx, wy);
		wCopy = new Vector2D(wx, wy);
	}

	// approximate equality of two double values
	public static void approxEquals(double d1, double d2) {
		assertEquals(d1, d2, APPROX_ZERO);
	}

	// approximate equality of two Vector2D objects
	public static void approxEquals(Vector2D a, Vector2D b) {
		approxEquals(a.x, b.x);
		approxEquals(a.y, b.y);
	}

	@Test
	public void testConstructor0() {
		v = new Vector2D();
		approxEquals(v.x, 0);
		approxEquals(v.y, 0);
	}

	@Test
	public void testConstructor1() {
		w = new Vector2D(v);
		approxEquals(v, w);
		approxEquals(v, vCopy);
	}

	@Test
	public void testConstructor2() {
		w = new Vector2D(vx, vy);
		approxEquals(v, w);
	}

	@Test
	public void testSet2() {
		Vector2D z = v.set(wx, wy);
		approxEquals(v, w);
		assertSame(z, v);
	}

	@Test
	public void testSet1() {
		Vector2D z = v.set(w);
		approxEquals(v, w);
		assertSame(z, v);
		approxEquals(w, wCopy);
	}

	@Test
	public void testEquals() {
		assertTrue(v.equals(vCopy));
		assertTrue(vCopy.equals(v));
		assertFalse(v.equals(new Vector2D(vx - 1, vy)));
		assertFalse(v.equals(new Vector2D(vx, vy - 1)));
	}

	@Test
	public void testMag() {
		approxEquals(v.mag(), Math.hypot(vx, vy));
		approxEquals(v, vCopy);
	}

	@Test
	public void testAngle() {
		for (int i : new int[] { -1, 0, 1 }) {
			for (int j : new int[] { -1, 0, 1 }) {
				v = new Vector2D(vx * i, vy * j);
				vCopy = new Vector2D(vx * i, vy * j);
				approxEquals(v.angle(), Math.atan2(vy * j, vx * i));
				approxEquals(v, vCopy);
			}
		}
	}

	@Test
	public void testAngle2() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				double vangle = -Math.PI + i * 0.24 * Math.PI;
				double wangle = -Math.PI + j * 0.24 * Math.PI;
				double vmag = 1 + Math.random() * 10;
				double wmag = 1 + Math.random() * 10;
				v = new Vector2D(vmag * Math.cos(vangle), vmag * Math.sin(vangle));
				w = new Vector2D(wmag * Math.cos(wangle), wmag * Math.sin(wangle));
				Vector2D vCopy = new Vector2D(v.x, v.y);
				Vector2D wCopy = new Vector2D(w.x, w.y);
				double result = wangle - vangle;
				if (result < -Math.PI)
					result += 2 * Math.PI;
				if (result > Math.PI)
					result -= 2 * Math.PI;
				approxEquals(v.angle(w), result);
				assertEquals(v, vCopy);
				assertEquals(w, wCopy);
			}
	}

	@Test
	public void testAdd1() {
		Vector2D z = v.add(w);
		approxEquals(v, new Vector2D(vx + wx, vy + wy));
		assertSame(z, v);
		approxEquals(w, wCopy);
	}

	@Test
	public void testAdd2() {
		Vector2D z = v.add(wx, wy);
		approxEquals(v, new Vector2D(vx + wx, vy + wy));
		assertSame(z, v);
	}

	@Test
	public void testAddScaled() {
		double factor = Math.random();
		Vector2D z = v.addScaled(w, factor);
		approxEquals(v, new Vector2D(vx + factor * wx, vy + factor * wy));
		assertSame(z, v);
		approxEquals(w, wCopy);
	}

	@Test
	public void testSubtract1() {
		Vector2D z = v.subtract(w);
		approxEquals(v, new Vector2D(vx - wx, vy - wy));
		assertSame(z, v);
		approxEquals(w, wCopy);
	}

	@Test
	public void testSubtract2() {
		Vector2D z = v.subtract(wx, wy);
		approxEquals(v, new Vector2D(vx - wx, vy - wy));
		assertSame(z, v);
	}

	@Test
	public void testMult() {
		Vector2D z = v.mult(RADIUS);
		approxEquals(v, new Vector2D(vx * RADIUS, vy * RADIUS));
		assertSame(z, v);
	}

	@Test
	public void testRotate() {
		Vector2D z = v.rotate(RADIUS);
		double x = vx * Math.cos(RADIUS) - vy * Math.sin(RADIUS);
		double y = vx * Math.sin(RADIUS) + vy * Math.cos(RADIUS);
		approxEquals(v, new Vector2D(x, y));
		assertSame(z, v);
	}

	@Test
	public void testDot() {
		approxEquals(v.dot(w), vx * wx + vy * wy);
		approxEquals(v, vCopy);
		approxEquals(w, wCopy);
	}

	@Test
	public void testDist() {
		approxEquals(v.dist(w), Math.hypot(wx - vx, wy - vy));
		approxEquals(v, vCopy);
		approxEquals(w, wCopy);
	}

	@Test
	public void testNormalise() {
		double len = Math.hypot(vx, vy);
		Vector2D z = v.normalise();
		approxEquals(Math.hypot(v.x, v.y), 1.0);
		approxEquals(v.x, vx / len);
		approxEquals(v.y, vy / len);
		assertSame(z, v);
	}

	@Test
	public void testWrap() {
		for (int i : new int[] { -1, 0, 1 }) {
			for (int j : new int[] { -1, 0, 1 }) {
				double dx = Math.random();
				double dy = Math.random();
				v.set((i + dx) * WIDTH, (j + dy) * HEIGHT);
				w.set(dx * WIDTH, dy * HEIGHT);
				Vector2D z = v.wrap(WIDTH, HEIGHT);
				approxEquals(v, w);
				assertSame(z, v);
			}
		}
	}

	@Test
	public void testPolar() {
		Vector2D vCopy = new Vector2D(Vector2D.polar(Math.atan2(vy, vx), Math.hypot(vx, vy)));
		Vector2D wCopy = new Vector2D(Vector2D.polar(Math.atan2(wy, wx), Math.hypot(wx, wy)));
		approxEquals(vCopy, v);
		approxEquals(wCopy, w);

	}

}