import java.awt.Point
import java.awt.geom.AffineTransform
import java.awt.geom.Point2D

Point2D point = new Point2D.Double();
AffineTransform.getRotateInstance(Math.toRadians(135), 0, 0).transform(new Point2D.Double(-0.5, 0.5), point)

println point
