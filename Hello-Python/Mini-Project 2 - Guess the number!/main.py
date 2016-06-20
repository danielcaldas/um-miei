#Guess the number!
import simplegui
import random
import math

#Global variables
secret_number=0
attempts=0
range_of_game=100 #for saving current range in the game, default value 100

#These function computes the n minimum attempts (mathematically speaking) for winning
#"Guess the number!"
def number_of_attempts(range):
    global attempts
    x = math.log(range+1,2)
    attempts = int(math.ceil(x))

def upgrade_attempts():
    global attempts
    
    if(attempts==0):
        print "\n\nYou run out of attempts!\n\n"
        new_game()
    
    attempts-=1
        
    print "Attempts left", attempts
    print""

def input_guess(guess):
    global secret_number
    global attempts
    
    if(attempts==0):
        upgrade_attempts()
    
    print "Guess was", guess
    x=int(guess)
        
    if x > secret_number:
       print "Lower"
       upgrade_attempts()
    elif x < secret_number:
       print "Higher"
       upgrade_attempts()
    else:
       #Create new game with previous range
       print "Correct\n"
       new_game()

def new_game():
    global secret_number
    global attempts
    global range_of_game
    
    number_of_attempts(range_of_game)
    secret_number = random.randrange(0, range_of_game)
    print "\nNew Game started! Guess the number between 0 and", range_of_game, "\n"
    
def range100():
    global range_of_game
    range_of_game=100
    new_game()

def range1000():
    global range_of_game
    range_of_game=1000
    new_game()

    
#GUI code
frame = simplegui.create_frame("Guess the number", 200, 200)
frame.add_input("Enter a guess", input_guess, 100)

#button for range: 0-100
frame.add_button("Range:0-100", range100, 200)

#button for range: 0-1000
frame.add_button("Range:0-1000", range1000, 200)

new_game() #default start game
frame.start()