package com.lilo.ranoapu.engine

data class Vector3 (
    var X : Double, 
	var Y : Double,
	var Z : Double,
) {
	
    operator fun plus(other : Vector3) = Vector3(
		X + other.X,
		Y + other.Y,
		Z + other.Z,
	)              
	
	
	operator fun minus(other : Vector3) = Vector3 (
	    X - other.X,
		Y - other.Y,
		Z - other.Z,
	)
	
	operator fun times(other : Vector3) = Vector3 (
	    X * other.X,
		Y * other.Y,
		Z * other.Z,
	)
	
	operator fun times(value : Double) = Vector3 (
	    X * value,
		Y * value,
		Z * value,
	)
	
	operator fun div(other : Vector3) = Vector3 (
	    X / other.X,
		Y / other.Y,
		Z / other.Z,
	)
	
	operator fun div(value : Double) = Vector3 (
	    X / value,
		Y / value,
		Z / value,
	)
	
	fun magnitude(): Double {
        return kotlin.math.sqrt(
            X * X + Y * Y + Z * Z
        )
    }
	
	fun dot(other: Vector3): Double {
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