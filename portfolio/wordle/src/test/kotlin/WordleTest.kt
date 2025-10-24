import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

const val PICK_WORDS_COUNT = 10
const val WORDS_FILE_PATH = "data/words.txt"

@Suppress("unused")
class WordleTest :
    StringSpec({
        // Write your tests here
        "isValid: A word whose length is not 5 is invalid" {
            isValid("abcdef") shouldBe false
        }

        "isValid: A word that contains non-letter characters is invalid" {
            isValid("abcd1") shouldBe false
        }

        "isValid: A word that contains exactly 5 letters  is valid" {
            isValid("AbcDe") shouldBe true
        }

        "readWordList: read from a non-existing file" {
            readWordList("non-existing") shouldBe emptyList()
        }

        "readWordList: read from words.txt" {
            val words = readWordList(WORDS_FILE_PATH)
            words.size shouldBeGreaterThan 0
        }

        "pickRandomWord: pick a random word" {
            val words = readWordList(WORDS_FILE_PATH)
            val oldSize = words.size
            val randomWord = pickRandomWord(words)
            words.contains(randomWord) shouldBe false
            words.size shouldBe oldSize - 1
        }

        "evaluateGuess: Fully matched words" {
            evaluateGuess("ABODE", "ABODE") shouldBe listOf(2, 2, 2, 2, 2)
        }

        "evaluateGuess: Partially matched words" {
            evaluateGuess("ADBYE", "ABODE") shouldBe listOf(2, 1, 1, 0, 2)
        }

        "evaluateGuess: No matches at all" {
            evaluateGuess("ABODE", "GHTFM") shouldBe listOf(0, 0, 0, 0, 0)
        }
    })
