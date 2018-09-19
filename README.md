# COMP3004_A1
COMP3004 Assignment 1, Individual assignment 
- Player can start the game from BlackJackGame
  - Player can choose to play the game with Computer player (C) or import a pre-programmed file input (F).
    - If Player type in (C), random cards will be assigned to Player and Dealer. Player can choose to Hit(H) or Stand(S). 
    - If the two initial card Player receives are having the same rank, using will be provided Split(D) option. 
    - If Player type in (F), user can specify the path of pre-programmed play instruction file input. 
      - All the test files are in the path: src/test/resources/
- Player can choose to start the game from HomeScreen:
  - Player receives two initial cards and can click on Hit or Stand buttons. 
  - Player can start a new game by clicking New Game button.
  - Player will be able to see both Player's cards and Dealer's cards on hand.
  - Split option is not implemented yet, although the button is visible. 

Credential information:
- The JavaFX UI implementation uses D. Parsons, Z. Samuelson, A. Wollschlager, D. Thompson's BlackJack game([https://github.com/dylanparsons/BlackJack]) cards and background files
- The JavaFX UI implementation follows D. Parsons, Z. Samuelson, A. Wollschlager, D. Thompson's BlackJack game([https://github.com/dylanparsons/BlackJack])'s design example, includes:
  - Using of JavaFX GridPane
  - Set texts' Font and Colors
  - Methods such as draw and add cards into the FlowPane, and remove the cards
- The main work flow has been changed to compile with my own BlackJackGame design and function.
- Thanks to this example, I have the chance to start checking other references and to learn the basic JavaFX knowledge
