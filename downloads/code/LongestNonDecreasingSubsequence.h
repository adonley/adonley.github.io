#ifndef LONGESTNONDECREASINGSUBSEQUENCE_H
#define LONGESTNONDECREASINGSUBSEQUENCE_H

#include<vector>

class Position {
  private:
    int number;
    int max_sub_sequence;
    Position *Parent;
    int index;
  public:
    void update_number(int temp) {number = temp;}
    void update_max(int temp) {max_sub_sequence = temp;}
    void update_parent(Position *temp) {Parent = temp;}
    void update_index(int temp) {index = temp;}
    int get_index() {return index;}
    Position * get_parent() { return Parent;}
    int get_max() {return max_sub_sequence;}
    int get_number() {return number;}
    Position(int temp_number);
    void display();
};

void getInput();
void computeMaxSub();
void displayResults();

std::vector<Position> sequence;

#endif
