package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        this.year != other.year -> this.year - other.year
        this.month != other.month -> this.month - other.month
        else -> this.dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval): MyDate = this.addTimeIntervals(interval, 1)
operator fun MyDate.plus(interval: RepeatedTimeInterval): MyDate = this.addTimeIntervals(interval.ti, interval.n)


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR

}
operator fun TimeInterval.times(n: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, n)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            private var current = start
            override fun hasNext(): Boolean = current <= endInclusive

            override fun next(): MyDate {
                val next = current
                current = current.nextDay()
                return next
            }

        }
    }

}
