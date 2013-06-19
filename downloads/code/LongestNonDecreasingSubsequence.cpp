/*
 *  Andrew Donley 
 *  Dynamic Programming Problem:
 *  Longest NonDecreasing Subsequence
 *  June 18th, 2013
 */

#include "LongestNonDecreasingSubsequence.h"
#include<iostream>
#include<string>
#include<sstream>

using namespace std;

int main() {
  getInput();
  computeMaxSub();
  displayResults();
  return 0;
}

Position::Position(int temp_number) {
  number = temp_number;
  max_sub_sequence = 1;
}

void Position::display() {
  cout << endl << "Number: " << number << ", Index: " << index << ", Max: " << max_sub_sequence;
  return;
}

/*
 *  Gets the sequence from user input
 */
void getInput() {

  string input;
  int val, index = 0;
  Position *temp;

  cout << endl;
  cout << "Longest Non-Decreasing Subsequence" << endl;
  cout << "==================================" << endl;
  cout << "Please enter a sequence of numbers deliniated by spaces." << endl;
  cout << "Press 'return' to submit the sequence." << endl;
  getline(cin, input); 

  istringstream iss(input);

  // Store the items
  while(iss >> val) {
    temp = new Position(val);
    temp->update_index(index);
    sequence.push_back(*temp);
    index++;
    delete temp;
  }

  return;
}

/*
 *  Computes the longest subsquence
 */
void computeMaxSub() {

  int largest_sub = 0;
  Position * temp_parent = NULL;

  for(vector<Position>::iterator i = sequence.begin(); i != sequence.end(); ++i) {
    for(int j = 0; j < i->get_index(); j++) {
      if(i->get_number() >= sequence.at(j).get_number() && sequence.at(j).get_max() > largest_sub) {
        largest_sub = sequence.at(j).get_max();
        temp_parent = &sequence.at(j);
      } 
    }
    // Update the largest increasing subsequence
    i->update_parent(temp_parent);
    i->update_max(largest_sub+1);
    // Reset the values
    largest_sub = 0;
    temp_parent = NULL;

  }
  return;
}

/* 
 *  Outputs the longest subsequence to stdout
 */
void displayResults() {
  int largest = 0;
  Position * temp_position;
  vector<Position> output;

  for(vector<Position>::iterator i = sequence.begin(); i != sequence.end(); i++) {
    if(i->get_max() > largest) {
      largest = i->get_max();
      temp_position = &*i;
    }
  }

  while(temp_position != NULL) {
    output.push_back(*temp_position);
    temp_position = temp_position->get_parent();
  }

  cout << endl << "The longest subsequence had length: " << output.front().get_max() << endl << endl;

  while(!output.empty()) {
    cout << output.back().get_number();
    if(output.size() > 1)
      cout << " -> ";
    else
      cout << endl << endl;

    output.pop_back();
  }

  return;
}
