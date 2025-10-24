import java.io.File
import kotlin.random.Random

// Implement the six required functions here

const val WORDLE_LENGTH = 5
const val RESET = "\u001B[0m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"

/**
 * Returns true if the given word is valid in Wordle
 * (i.e., if it consists of exactly 5 letters)
 */
fun isValid(word: String): Boolean = word.length == WORDLE_LENGTH && word.all { it.isLetter() }

/**
 * Reads Wordle target words from the specified file, returning them as a list of strings
 */
fun readWordList(filename: String): MutableList<String> {
    // Open the input file
    val file = File(filename)
    if (!file.exists()) {
        return mutableListOf()
    }
    // Read all words in the file
    return file.readLines().toMutableList()
}

/**
 * Chooses a random word from the given list, removes that word from the list, then
 * returns it.
 */
fun pickRandomWord(words: MutableList<String>): String {
    // Choose a random word index
    val wordIndex = Random.nextInt(0, words.size)

    // Remove the word from the list
    val word = words.removeAt(wordIndex)

    return word
}

/**
 * Prints a prompt using the given attempt number (e.g. "Attempt 1: "), then reads a word
 * from stdin. The word should be returned if valid, otherwise the user should be prompted
 * to try again.
 */
fun obtainGuess(attempt: Int): String {
    // Print a prompt
    print("Attempt $attempt: ")

    var guess = ""
    var firstGuess = true
    do {
        if (!firstGuess) {
            // Prompt the error
            println("Invalid word! Word consists of exactly 5 letters.")
            print("Try again: ")
        }

        // Read a word from stdin
        val word = readlnOrNull()
        if (word == null) {
            break
        }
        guess = word
        firstGuess = false
        // Check if the word is valid
    } while (!isValid(guess))
    return guess
}

/**
 * Compares a guess with the target word. Returns a list containing 5 integers,
 * representing the result of comparison at each letter position.
 * 0 for guess letters that are not present in the target word
 * 1 for letters that are in the target word but at a different position
 * 2 for letters that have been guessed correctly
 */
fun evaluateGuess(
    guess: String,
    target: String,
): List<Int> {
    val matches = MutableList(guess.length) { 0 }
    for (i in guess.indices) {
        if (guess[i] == target[i]) {
            // 2 indicates a correct guess
            matches[i] = 2
        } else if (target.contains(guess[i])) {
            // 1 indicates a wrong position
            matches[i] = 1
        }
    }
    return matches
}

/**
 * Displays the letters of a guess that match target word, or a ‘?’ character
 * where there is no match.
 */
fun displayGuess(
    guess: String,
    matches: List<Int>,
) {
    var matchingResult = ""
    for (i in guess.indices) {
        if (matches[i] == 2) {
            // Show matching characters
            matchingResult += "$GREEN${guess[i]}$RESET"
        } else if (matches[i] == 1) {
            // Show wrong position
            matchingResult += "$YELLOW${guess[i]}$RESET"
        } else {
            // Not present
            matchingResult += "$RED${guess[i]}$RESET"
        }
    }
    println(matchingResult)
}
