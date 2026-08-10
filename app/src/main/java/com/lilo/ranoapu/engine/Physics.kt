package com.lilo.ranoapu.engine

data class Vector3 (
    val X : Float, 
	val Y : Float,
	val Z : Float
) {
	
    opera                                                                                tor fun plus(other : Vector3) = Vector3 (
	    X + other.X,
		Y + other.Y,
		Z + other.Z
	)
	
	operator fun minus(other : Vector3) = Vector3 (
	    X - other.X,
		Y - other.Y,
		Z - other.Z
	)
	
	operator fun times(other : Vector3) = Vector3 (
	    X * other.X,
		Y * other.Y,
		Z * other.Z
	)
	
	operator fun times(value : Float) = Vector3 (
	    X * value,
		Y * value,
		Z * value
	)
	
	operator fun div(other : Vector3) = Vector3 (
	    X / other.X,
		Y / other.Y,
		Z / other.Z
	)
	
	operator fun div(value : Float) = Vector3 (
	    X / value,
		Y / value,
		Z / value
	)
	
	fun magnitude(): Float {
        return kotlin.math.sqrt(
            x * x + y * y + z * z
        )
    }
	
	fun dot(other: Vector3): Float {
        return X * other.X +
               Y * other.Y +
               Z * other.Z
    }
	
	fun cross(other: Vector3): Vector3 {
        return Vector3(
            Y * other.Z - Z * other.Y,
            Z * other.X - X * other.Z,
            X * other.Y - Y * other.X
        )
    }
}