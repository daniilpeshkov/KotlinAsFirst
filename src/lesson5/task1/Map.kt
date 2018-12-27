@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import java.lang.StringBuilder
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val mergedBook = mutableMapOf<String, MutableSet<String?>>()
    for (key in (mapB.keys + mapA.keys)) {
        mergedBook.getOrPut(key) { mutableSetOf(mapA[key], mapB[key]) }.addAll(setOf(mapA[key], mapB[key]))
    }
    val mergedBookWithStrings = mutableMapOf<String, String>()
    mergedBook.forEach {
        mergedBookWithStrings[it.key] = it.value.filterNot { it == null }.joinToString(", ")
    }
    return mergedBookWithStrings
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val gradesToStudents: MutableMap<Int, MutableSet<String>> = mutableMapOf()
    for ((student, mark) in grades)
        gradesToStudents.getOrPut(mark) { mutableSetOf(student) }.add(student)

    val a: MutableMap<Int, List<String>> = mutableMapOf()
    gradesToStudents.forEach { t, u -> a[t] = u.toList() }
    return a
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {

    for (keyA in a.keys) {
        if (b.containsKey(keyA) && b[keyA] != a[keyA]) {
            return false
        }
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val k: MutableMap<String, Int> = mutableMapOf()
    val averagePrice: MutableMap<String, Double> = mutableMapOf()
    for ((name, worth) in stockPrices) {
        k[name] = (k[name] ?: 0) + 1
        averagePrice[name] = (averagePrice[name] ?: 0.0) + worth
    }
    for (i in averagePrice.keys) {
        averagePrice[i] = averagePrice[i]!! / k[i]!!.toDouble()
    }
    return averagePrice
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var min: Double? = null
    var name: String? = null
    for ((curName, typeAndPrice) in stuff) {
        if (typeAndPrice.first == kind && (min == null || typeAndPrice.second <= min)) {
            min = typeAndPrice.second
            name = curName
        }
    }
    return name
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val dating = mutableMapOf<String, Set<String>>()
    val allPersons = mutableSetOf<String>()
    for ((name, currentFriends) in friends) {
        allPersons += currentFriends
        allPersons += name
    }
    for (currentName in allPersons) {
        val currentPersonDating = mutableSetOf<String>()
        val handshakesWay = Stack<String>()
        handshakesWay.push(currentName)
        val passedPersons = mutableMapOf<String, Boolean>()
        while (handshakesWay.isNotEmpty()) {
            val top = handshakesWay.peek()
            passedPersons[top] = true
            var foundNext = false
            if (friends[top] != null) {
                for (next in friends.getOrDefault(top, setOf())) {
                    if (!(passedPersons[next] ?: false)) {
                        foundNext = true
                        currentPersonDating.add(next)
                        handshakesWay.push(next)
                        break
                    }
                }
            }
            if (!foundNext) {
                handshakesWay.pop()
            }
        }
        dating[currentName] = currentPersonDating.toSet()
    }
    return dating
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for (i in b.keys) {
        if (a[i] == b[i]) {
            a.remove(i)
        }
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.toSet().intersect(b).toList()


/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
        if (word == "") true
        else word.toLowerCase().toCharArray().toSet() == chars.map { it.toLowerCase() }.toSet()


/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val k: MutableMap<String, Int> = mutableMapOf()
    for (i in list) {
        if (k[i] == null) k[i] = 0
        k[i] = k[i]!!.toInt() + 1
    }
    for (i in list) {
        if (k[i] == 1) k.remove(i)
    }
    return k.toMap()
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    for (i in 0 until words.lastIndex) {
        for (j in i + 1..words.lastIndex) {
            if (words[i].toList().sortedDescending() == words[j].toList().sortedDescending()) {
                return true
            }
        }
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val a = list.mapIndexed { index, i -> Pair(i, index) }
            .sortedBy { it.first }.groupBy({ it.first }, { it.second })
    for ((key, indices) in a) {
        val tmpList = a[number - key]
        if (indices === tmpList && tmpList.size > 1) {
            return Pair(indices[0], indices[1])
        } else if (tmpList != null && indices !== tmpList) {
            return Pair(indices[0], tmpList[0])
        }

    }
    return Pair(-1, -1)
}


/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val massToSet = mutableMapOf<Int, Set<String>>()
    val massToPrice = mutableMapOf<Int, Int>()
    val itemUsed = mutableMapOf<String, Boolean>()
    var deltaM = Int.MAX_VALUE
    var nameOfMin: String = ""

    for ((name, massPrice) in treasures) {
        if (massPrice.first < deltaM) {
            deltaM = massPrice.first
            nameOfMin = name
        }
    }
    massToPrice[0] = 0
    massToSet[0] = setOf()

    var currentMass = deltaM
    if (currentMass <= capacity) {
        massToPrice[currentMass] = treasures[nameOfMin]!!.second
        massToSet[currentMass] = setOf(nameOfMin)
        itemUsed[nameOfMin] = true
        currentMass += deltaM
    }

    while (currentMass < capacity) {
        var maxSuitableMass = 0
        var maxPrice = 0
        var maxName = ""
        for ((name, massPrice) in treasures) {
            if (!(itemUsed[name] ?: false)) {
                if (massPrice.first <= currentMass) {
                    if (massPrice.second > maxPrice) {
                        maxSuitableMass = massPrice.first
                        maxPrice = massPrice.second
                        maxName = name
                    }
                }
            }
        }

        if (maxPrice == 0) {
            massToPrice[currentMass] = massToPrice[currentMass - deltaM]!!
            massToSet[currentMass] = massToSet[currentMass - deltaM]!!
        } else {
            itemUsed[maxName] = true
            val massSurplus = ((currentMass - maxSuitableMass) / deltaM) * deltaM
            massToPrice[currentMass] = massToPrice[massSurplus]!! + maxPrice
            massToSet[currentMass] = massToSet[massSurplus]!! + maxName
        }
        for (i in itemUsed.keys) {
            itemUsed[i] = i in massToSet[currentMass]!!
        }
        currentMass += deltaM
    }
    return massToSet[currentMass - deltaM]!!
}




