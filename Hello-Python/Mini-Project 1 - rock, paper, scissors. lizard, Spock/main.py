__author__ = 'daniel'
import random

#mimi_project_2: Rok-paper-scissors-lizard-Spock
#jdc 2014.09.24

#Rules:
# 1 - scissors cuts paper
# 2 - paper covers rock
# 3 - rock crushes lizard
# 4 - lizard poisons Spock
# 5 - Spock smashes scissors
# 6 - scissors decapitate lizard
# 7 - lizard eats paper
# 8 - paper disprove Spock
# 9 - Spock vaporizes rock
# 10 - rock crushes scissors

#0 - rock
#1 - Spock
#2 - paper
#3 - lizard
#4 - scissors


def name_to_number(name):
    if name == 'rock':
        return 0
    elif name == 'Spock':
        return 1
    elif name == 'paper':
        return 2
    elif name == 'lizard':
        return 3
    elif name == 'scissors':
        return 4


def number_to_name(number):
    if number == 0:
        return 'rock'
    elif number == 1:
        return 'Spock'
    elif number == 2:
        return 'paper'
    elif number == 3:
        return 'lizard'
    elif number == 4:
        return 'scissors'


#Phrases to display
#'It\'s a tie!'  or  'Computer wins!'  or  'Player wins!'


def rpsls(player_choice):
    print 'Player chooses', player_choice
    player_number = name_to_number(player_choice)

    comp_number = random.randrange(0, 4)
    print 'Computer chooses', number_to_name(comp_number)

    if player_number == 0:
        if comp_number == 0:
            print 'It\'s a tie!'
        elif comp_number == 1:
            print 'Computer wins!'
        elif comp_number == 2:
            print 'Computer wins!'
        elif comp_number == 3:
            print 'Player wins!'
        elif comp_number == 4:
            print 'Player wins!'

    elif player_number == 1:
        if comp_number == 0:
            print 'Player wins!'
        elif comp_number == 1:
            print 'It\'s a tie!'
        elif comp_number == 2:
            print 'Computer wins!'
        elif comp_number == 3:
            print 'Computer wins!'
        elif comp_number == 4:
            print 'Player wins!'

    elif player_number == 2:
        if comp_number == 0:
            print 'Player wins!'
        elif comp_number == 1:
            print 'Player wins!'
        elif comp_number == 2:
            print 'It\'s a tie!'
        elif comp_number == 3:
            print 'Computer wins!'
        elif comp_number == 4:
            print 'Computer wins!'

    elif player_number == 3:
        if comp_number == 0:
            print 'Computer wins!'
        elif comp_number == 1:
            print 'Player wins!'
        elif comp_number == 2:
            print 'Player wins!'
        elif comp_number == 3:
            print 'It\'s a tie!'
        elif comp_number == 4:
            print 'Computer wins!'

    else:
        if comp_number == 0:
            print 'Computer wins!'
        elif comp_number == 1:
            print 'Computer wins!'
        elif comp_number == 2:
            print 'Player wins!'
        elif comp_number == 3:
            print 'Player wins!'
        elif comp_number == 4:
            print 'It\'s a tie!'

    return ''

#Testing the main function
print rpsls("rock")
print rpsls("paper")
print rpsls("scissors")
print rpsls("lizard")
print rpsls("Spock")
