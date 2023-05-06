package app.mathnek.talesofvarmithore.util;

import java.util.Random;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MathB {
   public static final double PI_D = java.lang.Math.PI;
   public static final float PI_F = 3.1415927F;
   public static boolean useLUT = true;
   public static final double MINIMUM_SIGNIFICANT_DIFFERENCE = 0.001;

   private MathB() {
   }

   public static float sin(float a) {
      return (float) java.lang.Math.sin((double)a);
   }

   public static float cos(float a) {
      return (float) java.lang.Math.cos((double)a);
   }

   public static float tan(float a) {
      return (float) java.lang.Math.tan((double)a);
   }

   public static float atan2(float y, float x) {
      return (float) java.lang.Math.atan2((double)y, (double)x);
   }

   public static float toRadians(float angdeg) {
      return (float) java.lang.Math.toRadians((double)angdeg);
   }

   public static float toDegrees(float angrad) {
      return (float) java.lang.Math.toDegrees((double)angrad);
   }

   public static float normDeg(float a) {
      a %= 360.0F;
      if (a >= 180.0F) {
         a -= 360.0F;
      }

      if (a < -180.0F) {
         a += 360.0F;
      }

      return a;
   }

   public static double normDeg(double a) {
      a %= 360.0;
      if (a >= 180.0) {
         a -= 360.0;
      }

      if (a < -180.0) {
         a += 360.0;
      }

      return a;
   }

   public static float normRad(float a) {
      a %= 6.2831855F;
      if (a >= 3.1415927F) {
         a -= 6.2831855F;
      }

      if (a < -3.1415927F) {
         a += 6.2831855F;
      }

      return a;
   }

   public static double normRad(double a) {
      a %= 6.283185307179586;
      if (a >= java.lang.Math.PI) {
         a -= 6.283185307179586;
      }

      if (a < -3.141592653589793) {
         a += 6.283185307179586;
      }

      return a;
   }

   public static double getTruncatedGaussian(Random rand, double mean, double threeSigma) {
      double rawValue = rand.nextGaussian();
      rawValue = clamp(rawValue, -3.0, 3.0);
      return mean + rawValue * threeSigma / 3.0;
   }

   public static float sqrtf(float f) {
      return (float) java.lang.Math.sqrt((double)f);
   }

   public static float clamp(float value, float min, float max) {
      return value < min ? min : (value > max ? max : value);
   }

   public static double clamp(double value, double min, double max) {
      return value < min ? min : (value > max ? max : value);
   }

   public static int clamp(int value, int min, int max) {
      return value < min ? min : (value > max ? max : value);
   }

   public static byte clampByte(int value, int min, int max) {
      return (byte)(value < min ? min : (value > max ? max : value));
   }

   public static int clamps(int value, int min, int max) {
      return value < min ? min : (value >= max ? max : value);
   }

   public static float updateRotation(float r1, float r2, float step) {
      return r1 + clamp(normDeg(r2 - r1), -step, step);
   }

   public static float lerp(float a, float b, float x) {
      return a * (1.0F - x) + b * x;
   }

   public static double lerp(double a, double b, double x) {
      return a * (1.0 - x) + b * x;
   }

   public static float slerp(float a, float b, float x) {
      if (x <= 0.0F) {
         return a;
      } else {
         return x >= 1.0F ? b : lerp(a, b, x * x * (3.0F - 2.0F * x));
      }
   }

   public static float terp(float a, float b, float x) {
      if (x <= 0.0F) {
         return a;
      } else if (x >= 1.0F) {
         return b;
      } else {
         float mu2 = (1.0F - cos(x * 3.1415927F)) / 2.0F;
         return a * (1.0F - mu2) + b * mu2;
      }
   }

   public static double terp(double a, double b, double x) {
      if (x <= 0.0) {
         return a;
      } else if (x >= 1.0) {
         return b;
      } else {
         double mu2 = (1.0 - java.lang.Math.cos(x * java.lang.Math.PI)) / 2.0;
         return a * (1.0 - mu2) + b * mu2;
      }
   }

   public static float constrainAngle(float targetAngle, float centreAngle, float maximumDifference) {
      return centreAngle + clamp(normDeg(targetAngle - centreAngle), -maximumDifference, maximumDifference);
   }

   public static Vec3 multiply(Vec3 source, double multiplier) {
      return new Vec3(source.x * multiplier, source.y * multiplier, source.z * multiplier);
   }

   public static boolean isApproximatelyEqual(double x1, double x2) {
      return java.lang.Math.abs(x1 - x2) <= 0.001;
   }

   public static boolean isSignificantlyDifferent(double x1, double x2) {
      return java.lang.Math.abs(x1 - x2) > 0.001;
   }

   public static int modulus(int numerator, int divisor) {
      return (numerator % divisor + divisor) % divisor;
   }

   public static float wrapAngleTo180(float angle) {
      angle %= 360.0F;
      if (angle >= 180.0F) {
         angle -= 360.0F;
      }

      if (angle < -180.0F) {
         angle += 360.0F;
      }

      return angle;
   }

   public static double wrapAngleTo180(double angle) {
      angle %= 360.0;
      if (angle >= 180.0) {
         angle -= 360.0;
      }

      if (angle < -180.0) {
         angle += 360.0;
      }

      return angle;
   }

   public static float invSqrt(float x) {
      float xhalf = 0.5F * x;
      int i = Float.floatToIntBits(x);
      i = 1597463007 - (i >> 1);
      x = Float.intBitsToFloat(i);
      x *= 1.5F - xhalf * x * x;
      return x;
   }

   public static double calculateYaw(Vec3 direction) {
      double yaw = java.lang.Math.atan2(direction.z, direction.x) * 180.0 / java.lang.Math.PI - 90.0;
      yaw = normDeg(yaw);
      return yaw;
   }

   public static double calculatePitch(Vec3 direction) {
      double xz_norm = java.lang.Math.sqrt(direction.x * direction.x + direction.z * direction.z);
      double pitch = -(java.lang.Math.atan2(direction.y, xz_norm) * 180.0 / java.lang.Math.PI);
      return pitch;
   }

   public static int getRandomInRange(Random random, int minValue, int maxValue) {
      return random.nextInt(maxValue - minValue + 1) + minValue;
   }

   public static float getRandomInRange(Random random, float minValue, float maxValue) {
      return random.nextFloat() * (maxValue - minValue) + minValue;
   }

   public static double getClosestDistanceSQ(AABB aabb, Vec3 point) {
      double dx = java.lang.Math.max(java.lang.Math.max(0.0, aabb.minX - point.x), point.x - aabb.maxX);
      double dy = java.lang.Math.max(java.lang.Math.max(0.0, aabb.minY - point.y), point.y - aabb.maxY);
      double dz = java.lang.Math.max(java.lang.Math.max(0.0, aabb.minZ - point.z), point.z - aabb.maxZ);
      return dx * dx + dy * dy + dz * dz;
   }

   public static float wrapDegrees(float value) {
      value %= 360.0F;
      if (value >= 180.0F) {
         value -= 360.0F;
      }

      if (value < -180.0F) {
         value += 360.0F;
      }

      return value;
   }
}
