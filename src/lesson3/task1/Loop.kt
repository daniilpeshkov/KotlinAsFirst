@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1


import lesson1.task1.sqr
import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    return if (n == 0) {
        1
    } else {
        var a = 0
        var tmp = n
        while (tmp != 0) {
            a++
            tmp /= 10
        }
        a
    }
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    val last_numbers = IntArray(2)
    last_numbers[0] = 1
    last_numbers[1] = 1
    var i = 0
    var cur = 2
    while (cur < n) {
        i++
        last_numbers[i % 2] = last_numbers[0] + last_numbers[1]
        cur++
    }
    return last_numbers[i % 2]
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var a = m
    var b = n
    while (a != b) {
        if (a > b) {
            a -= b
        } else {
            b -= a
        }
    }
    return m * n / a
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var div = 2
    while (n % div > 0) div++
    return div
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var div = n - 1
    while (n % div > 0) div--
    return div
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var a = m
    var b = n
    while (a != b) {
        if (a > b) {
            a -= b
        } else {
            b -= a
        }
    }
    return a == 1
}


/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean = (floor(sqrt(n.toDouble())) - ceil(sqrt(m.toDouble()))) >= 0

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    return if (x == 1) {
        0
    } else {
        1 + if (x % 2 == 0) {
            collatzSteps(x / 2)
        } else {
            collatzSteps(x * 3 + 1)
        }
    }

}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var eq_x = x
    while (eq_x !in 0.0..PI * 2.0) {
        if (eq_x < 0) eq_x += PI * 2.0
        else if (eq_x >= PI * 2.0) eq_x -= PI * 2.0
    }
    if (eq_x > PI / 2.0) {
        eq_x = PI - eq_x
    }
    var sin: Double = eq_x
    var a: Double = eq_x
    var n = 1.0
    while (abs(a) > eps) {
        a *= (-1) * eq_x * eq_x / ((n + 1.0) * (n + 2.0))
        n += 2
        sin += a
    }
    return sin
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var eq_x = x
    var cos = 1.0
    var a = 1.0
    var n = 0.0
    while (eq_x !in 0.0..PI * 2.0) {
        if (eq_x < 0) eq_x += PI * 2.0
        else if (eq_x >= PI * 2.0) eq_x -= PI * 2.0
    }
    if (eq_x > PI) {
        eq_x = 2 * PI - eq_x
    }
    while (abs(a) > eps) {
        a *= (-1.0) * eq_x * eq_x / ((n + 1.0) * (n + 2.0))
        n += 2
        cos += a
    }

    return cos
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var a = n
    var new = 0
    while (a > 0) {
        new *= 10
        new += a % 10
        a /= 10
    }
    return new
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    val len_n = digitNumber(n)
    var left_border: Long = 1
    var right_border: Long = 10
    for (i in 1..len_n) left_border *= 10
    while (left_border > right_border) {
        if ((n % left_border / (left_border / 10)) != (n % right_border / (right_border / 10)))
            return false
        left_border /= 10
        right_border *= 10
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var num = n / 10
    val first_digit = n % 10
    while (num > 0) {
        if (first_digit != num % 10) return true
        num /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var k = 1
    var prev_len = 1
    while (prev_len < n) {
        k += 1
        prev_len += digitNumber(sqr(k))
    }
    var cur_square = sqr(k)
    val dif = prev_len - n
    for (i in 1..dif) {
        cur_square /= 10
    }
    return cur_square % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    // Я не использовал уже написанную функцию для поиска числа фибоначи для того,
    // чтобы не искать каждое число начиная с 1
    if (n in 1..2) return 1
    val last_numbers = IntArray(2)
    last_numbers[0] = 1
    last_numbers[1] = 1
    var i = 0
    var prev_len = 2
    while (prev_len < n) {
        i++
        last_numbers[i % 2] = last_numbers[0] + last_numbers[1]
        prev_len += digitNumber(last_numbers[i % 2])
    }
    var cur_fib = last_numbers[i % 2]
    val dif = prev_len - n
    for (j in 1..dif) {
        cur_fib /= 10
    }
    return cur_fib % 10
}
