# Bulls and Cows - The Game

A classic code-breaking mind game, now with a graphical user interface!

## Description

Bulls and Cows is a game where you try to guess a secret code composed of unique digits (and letters, depending on the chosen difficulty).
For each guess you make, the game will provide feedback:

- **Bulls**: Correct characters in the correct position.
- **Cows**: Correct characters but in the wrong position.

The goal is to guess the secret code with the fewest number of turns.

## How to Play

1.  **Setup**:
    - Enter the desired **length** of the secret code.
    - Enter the number of **possible symbols** that can be used in the code (e.g., 10 for digits 0-9, or up to 36 for 0-9 and a-z). The number of possible symbols must be greater than or equal to the code length.
    - Click "**Start Game**".
2.  **Gameplay**:
    - The game will indicate that the secret code is ready (e.g., `The secret is prepared: **** (0-9, a-f)`).
    - Enter your guess into the "Your Guess" field. The guess must be the same length as the secret code and use characters from the allowed symbol range.
    - Click "**Submit Guess**" or press Enter.
3.  **Feedback**:
    - The game will show you the number of "bulls" and "cows" for your guess.
4.  **Winning**:
    - Continue guessing until you get all bulls (e.g., for a 4-digit code, "Grade: 4 bull(s)").
    - The game will congratulate you and reveal the secret code.
5.  **Play Again**:
    - After a game ends, the setup fields will be re-enabled, allowing you to start a new game with potentially different settings.

## How to Run the Game (Using the JAR)

1.  **Prerequisites**: Ensure you have Java Runtime Environment (JRE) version 11 or newer installed on your system. You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [Adoptium](https://adoptium.net/).
2.  **Download**: Download the `BullsAndCowsGame-1.0.0.jar` file from the [Releases](../../releases) page of this repository (once you create a release).
3.  **Run**:
    - **Double-click**: On most systems, you can simply double-click the `.jar` file to run the game.
    - **Command Line**: Alternatively, open your terminal or command prompt, navigate to the directory where you downloaded the JAR, and run the command:
      ```bash
      java -jar BullsAndCowsGame-1.0.0.jar
      ```

## How to Build from Source (For Developers)

1.  **Prerequisites**:
    - Java Development Kit (JDK) version 11 or newer.
    - Git (to clone the repository).
2.  **Clone the Repository**:
    ```bash
    git clone https://github.com/your-username/your-repository-name.git
    cd your-repository-name
    ```
    (Replace `your-username/your-repository-name` with the actual URL of your repository)
3.  **Build the JAR**:
    - The project uses Gradle. The included Gradle Wrapper (`gradlew`) will download the correct Gradle version automatically.
    - To build the executable JAR, run the following command from the project root directory:
      ```bash
      ./gradlew :Bulls_and_Cows-task:jar
      ```
    - The JAR file will be created in `Bulls and Cows/task/build/libs/BullsAndCowsGame-1.0.0.jar`.

## Technologies Used

- **Java**: Core programming language.
- **Java Swing**: For the graphical user interface (GUI).
- **Gradle**: For project build automation.

---

Enjoy the game!
