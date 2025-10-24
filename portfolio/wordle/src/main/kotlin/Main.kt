const val MAX_ATTEMPTS = 6
const val WORDS_FILE_PATH = "data/words.txt"

fun main() {
    // Read words from the file
    val words = readWordList(WORDS_FILE_PATH)

    // Choose a target word
    val targetWord = pickRandomWord(words)

    // Make up to 10 attempts at guessing the chosen word
    var isMatch = false
    for (i in 1..MAX_ATTEMPTS) {
        // Guess the word
        val guessWord = obtainGuess(i)
        // Check if the words matches
        val matches = evaluateGuess(guessWord, targetWord)
        if (matches.all { it == 1 }) {
            // Guess correct word
            isMatch = true
            break
        }
        println("Your guess is incorrect!")
        print("Match status: ")
        displayGuess(guessWord, matches)
    }

    if (isMatch) {
        println("Your guess is correct!")
    } else {
        // Run out of guesses
        println("You run out of guesses!")
        println("Correct word should be $targetWord!")
    }
}
