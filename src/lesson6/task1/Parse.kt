@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.NumberFormatException
import java.util.*

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val monthToNumber = mapOf(
            "января" to 1, "февраля" to 2, "марта" to 3,
            "апреля" to 4, "мая" to 5, "июня" to 6,
            "июля" to 7, "августа" to 8, "сентября" to 9,
            "октября" to 10, "ноября" to 11, "декабря" to 12)
    val data = str.split(" ")
    val numberRegex = Regex("\\d+")
    if (data.size == 3 && numberRegex.matches(data[0]) && numberRegex.matches(data[2])
            && data[1] in monthToNumber.keys
            && data[0].toInt() <= daysInMonth(monthToNumber[data[1]]!!, data[2].toInt())) {
        return "${twoDigitStr(data[0].toInt())}.${twoDigitStr(monthToNumber[data[1]]!!)}.${data[2]}"
    }
    return ""
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val numbers = digital.split(".")
    val numberRegex = Regex("\\d+")
    if (numbers.size == 3 && numbers.all { numberRegex.matches(it) }) {
        if (numbers[0].toInt() <= daysInMonth(numbers[1].toInt(), numbers[2].toInt()) && numbers[1].toInt() in 1..12) {
            return "${numbers[0].replace(Regex("0(?=\\d)"), "")} ${when (numbers[1]) {
                "01" -> "января"
                "02" -> "февраля"
                "03" -> "марта"
                "04" -> "апреля"
                "05" -> "мая"
                "06" -> "июня"
                "07" -> "июля"
                "08" -> "августа"
                "09" -> "сентября"
                "10" -> "октября"
                "11" -> "ноября"
                "12" -> "декабря"
                else -> ""
            }} ${numbers[2]}"
        }
    }
    return ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val number = phone.replace(Regex("[()\\-\\s]+"), "")
    return if (Regex("[+]?[0-9]+").matches(number)) {
        number
    } else {
        ""
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val entries = jumps.split(" ")
    var max = -1
    for (i in entries) {
        try {
            val d = i.toInt()
            if (d > max) max = d
        } catch (ex: NumberFormatException) {
        }
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val correctResultSegmentRegex = Regex("(?:\\d+\\s[%\\-+]+\\s?)")
    var maxHeight = 0
    var currentStartIndex = 0
    if (jumps.isNotEmpty() && jumps.contains("+")) {
        while (currentStartIndex < jumps.length) {
            val result = correctResultSegmentRegex.find(jumps, currentStartIndex)
            if (result != null && result.range.start == currentStartIndex) {
                currentStartIndex = result.range.endInclusive + 1
                if (result.value.contains("+")) {
                    val tmp = Regex("\\d+").find(result.value)?.value?.toInt()
                    if (tmp ?: 0 > maxHeight) {
                        maxHeight = tmp!!
                    }
                }
            } else return -1
        }
        return maxHeight
    }
    return -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (Regex("(\\d+\\s+[+-]\\s+)*(\\d+)\$").matches(expression)) {
        val entries = expression.split(Regex("\\s+"))
        var sum = 0
        var sign = 1
        for ((i, entry) in entries.withIndex()) {
            if (i % 2 == 0) {
                sum += entry.toInt() * sign
            } else {
                sign = if (entry == "+") 1
                else -1 // if "-"
            }
        }
        return sum
    }
    throw IllegalArgumentException()
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val entries = str.toLowerCase().split(" ")
    var length = 0
    for (i in 0..entries.size - 2) {
        if (entries[i] == entries[i + 1]) {
            return length
        }
        length += entries[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (Regex("(?:[^;]+\\s\\d+(?:.\\d+)?;\\s)*(?:[^;]+\\s\\d+(?:.\\d+)?)")
                    .matches(description)) {
        var max = 0.0f
        var maxName = ""
        for (product in description.split("; ")) {
            val tmpList = product.split(" ")
            val tmp = tmpList[1].toFloat()
            if (tmp >= max) {
                max = tmp
                maxName = tmpList[0]
            }
        }
        return maxName
    }
    return ""
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (Regex("[IVXLCDM]+").matches(roman)) {
        val values: Map<Char, Int> = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)
        var number = 0
        var last = 0
        for (i in 0 until roman.length) {
            if (roman[i] !in values.keys) return -1
            if (values[roman[i]]!! > last) {
                number -= last * 2
            }
            last = values[roman[i]]!!
            number += last
        }
        return number
    }
    return -1
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val field: Array<Int> = Array(cells) { 0 }
    if (commandsIsCorrect(commands)) {
        var completedCommandsCount = 0
        var curPos = cells / 2
        var curComI = 0
        val openBracketIndex = Stack<Int>()
        while (completedCommandsCount < limit && curComI < commands.length) {
            when (commands[curComI]) {
                '+' -> field[curPos] += 1
                '-' -> field[curPos] -= 1
                '>' -> {
                    curPos += 1
                    if (curPos == cells) throw IllegalStateException()
                }
                '<' -> {
                    curPos -= 1
                    if (curPos < 0) throw IllegalStateException()
                }
                '[' -> {
                    if (field[curPos] == 0) {
                        var tmp = 1
                        while (tmp != 0) {
                            curComI += 1
                            tmp += when (commands[curComI]) {
                                '[' -> 1
                                ']' -> -1
                                else -> 0
                            }
                        }
                    } else openBracketIndex.push(curComI)
                }
                ']' -> {
                    if (field[curPos] != 0) {
                        curComI = openBracketIndex.peek()
                    } else {
                        openBracketIndex.pop()
                    }
                }
            }
            curComI++
            completedCommandsCount++
        }
        return field.toList()
    } else {
        throw IllegalArgumentException()
    }
}

fun commandsIsCorrect(commands: String): Boolean {
    if (Regex("[<>+\\-\\[\\]\\s]*").matches(commands)) {
        var bracketK = 0
        for (i in commands) {
            bracketK += when (i) {
                '[' -> 1
                ']' -> -1
                else -> 0
            }
            if (bracketK < 0) return false
        }
        if (bracketK == 0) return true
    }
    return false
}